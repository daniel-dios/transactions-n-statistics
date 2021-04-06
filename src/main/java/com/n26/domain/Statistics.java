package com.n26.domain;

import com.n26.domain.exception.WrongStatisticsInputs;

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
    validateInputs(sum, max, min, count);
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

  private void validateInputs(Amount sum, Amount max, Amount min, Count count) {
    if (checkMaxIsGreaterThanMin(max, min)
        || checkSumIsGreaterThanMax(sum, max)
        || checkSumIsBiggerThanMaxMin(sum, max, min)
        || checkSumIsZeroWhenCountIsZero(sum, count)) {
      throw new WrongStatisticsInputs();
    }
  }

  private boolean checkSumIsZeroWhenCountIsZero(Amount sum, Count count) {
    return (count.equals(Count.ZERO) && !sum.equals(Amount.ZERO));
  }

  private boolean checkSumIsBiggerThanMaxMin(Amount sum, Amount max, Amount min) {
    final Amount total = max.sum(min);
    return (!total.equals(sum) && sum.min(total).equals(sum));
  }

  private boolean checkSumIsGreaterThanMax(Amount sum, Amount max) {
    return (!sum.equals(max) && sum.min(max).equals(sum));
  }

  private boolean checkMaxIsGreaterThanMin(Amount max, Amount min) {
    return (!max.equals(min) && max.min(min).equals(max));
  }
}
