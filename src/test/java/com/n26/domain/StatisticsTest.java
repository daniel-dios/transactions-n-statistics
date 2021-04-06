package com.n26.domain;

import com.n26.domain.exception.WrongStatisticsInputs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static com.n26.utils.DomainFactoryUtils.createTransaction;
import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatisticsTest {

  @ParameterizedTest
  @MethodSource("getNotValidInputs")
  void shouldThrowWrongStatisticsInputs(String sum, String max, String min, int count) {
    assertThatThrownBy(() -> createStatistics(sum, max, min, count))
        .isInstanceOf(WrongStatisticsInputs.class);
  }

  @Test
  void shouldMergeCorrectly() {
    final Statistics a = createStatistics("4.00", "3.00", "1.00", 2);
    final Statistics b = createStatistics("123.75", "122.00", "1.05", 10);

    final Statistics merge = Statistics.merge(a, b);

    final Statistics expected = createStatistics("127.75", "122.00", "1.00", 12);
    assertThat(merge)
        .isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldAggregateTransaction() {
    final Transaction transaction = createTransaction("1.123", now().minus(Duration.ofSeconds(1)), now());
    final Statistics statistics = createStatistics("2.0000", "2.0000", "2.0000", 1);

    final Statistics actual = statistics.aggregate(transaction);

    final Statistics expected = createStatistics("3.1230", "2.0000", "1.123", 2);
    assertThat(actual)
        .isEqualToComparingFieldByField(expected);
  }

  private static Stream<Arguments> getNotValidInputs() {
    return Stream.of(
        Arguments.of("5", "2", "3", 2),
        Arguments.of("5", "7", "1", 2),
        Arguments.of("5", "4", "4", 2),
        Arguments.of("0", "0", "1", 0),
        Arguments.of("0", "1", "0", 0),
        Arguments.of("1", "0", "0", 0)
    );
  }
}