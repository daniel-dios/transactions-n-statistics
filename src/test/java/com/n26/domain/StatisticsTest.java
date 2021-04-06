package com.n26.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class StatisticsTest {

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