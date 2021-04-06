package com.n26.domain;

import java.math.BigDecimal;

public final class Statistics {
  public static final Statistics EMPTY_STATISTICS = new Statistics(
      Amount.ZERO,
      Amount.ZERO,
      Amount.ZERO,
      Count.ZERO);

  private final Amount sum;
  private final Amount max;
  private final Amount min;
  private final Count count;

  public Statistics(
      Amount sum,
      Amount max,
      Amount min,
      Count count) {
    this.sum = sum;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public static Statistics merge(Statistics a, Statistics b) {
    final Amount sum = a.sum.sum(b.sum);
    final Amount max = a.max.max(b.max);
    final Amount min = a.min.min(b.max);
    final Count count = a.count.add(b.count);
    return new Statistics(
        sum,
        max,
        min,
        count
    );
  }

  public BigDecimal getSum() {
    return sum.getRoundValue();
  }

  public BigDecimal getAvg() {
    return sum.divide(count.getValue());
  }

  public BigDecimal getMax() {
    return max.getRoundValue();
  }

  public BigDecimal getMin() {
    return min.getRoundValue();
  }

  public long getCount() {
    return count.getValue();
  }
}
