package com.n26.domain;

import com.n26.domain.exception.WrongStatisticsInputs;
import com.sun.org.apache.xpath.internal.Arg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatisticsTest {

  @ParameterizedTest
  @MethodSource("getNotPossibleInputs")
  void shouldThrowWrongStatisticsInputs(String sum, String max, String min, int count) {
    assertThatThrownBy(() -> createStatistics(sum, max, min, count))
        .isInstanceOf(WrongStatisticsInputs.class);
  }

  private static Stream<Arguments> getNotPossibleInputs() {
    return Stream.of(
        Arguments.of("5", "2", "3", 2),
        Arguments.of("5", "7", "1", 2),
        Arguments.of("5", "4", "4", 2),
        Arguments.of("0", "0", "1", 0),
        Arguments.of("0", "1", "0", 0),
        Arguments.of("1", "0", "0", 0),
        Arguments.of("1", "1", "1", 1)
    );
  }

  @Test
  void shouldMergeCorrectly() {
    final Statistics a = createStatistics("4.00", "3.00", "1.00", 2);
    final Statistics b = createStatistics("123.75", "122.00", "2.00", 10);

    final Statistics merge = Statistics.merge(a, b);

    final Statistics expected = createStatistics("127.75", "122.00", "1.00", 12);
    assertThat(merge)
        .isEqualToComparingFieldByField(expected);
  }

  private Statistics createStatistics(String sum, String max, String min, int count) {
    return new Statistics(
        createAmount(sum),
        createAmount(max),
        createAmount(min),
        new Count(count)
    );
  }

  private Amount createAmount(String value) {
    return new Amount(new BigDecimal(value));
  }
}