package com.n26.domain;

import com.n26.domain.exception.WrongStatisticsInputs;

import java.math.BigDecimal;

public final class Statistics {
  public static final Statistics EMPTY_STATISTICS = new Statistics(Amount.ZERO, Amount.ZERO, Amount.ZERO, Count.ZERO);

  private final Amount sum;
  private final Amount max;
  private final Amount min;
  private final Count count;

  public Statistics(Amount sum, Amount max, Amount min, Count count) {
    validateInputs(sum, max, min, count);
    this.sum = sum;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public static Statistics merge(Statistics a, Statistics b) {
    return new Statistics(
        a.sum.sum(b.sum),
        a.max.max(b.max),
        a.min.min(b.min),
        a.count.add(b.count)
    );
  }

  public Statistics aggregate(Transaction transaction) {
    return new Statistics(
        sum.sum(transaction.getAmount()),
        max.max(transaction.getAmount()),
        isEmpty() ? transaction.getAmount() : min.min(transaction.getAmount()),
        count.add(new Count(1))
    );
  }

  public BigDecimal getSumRound() {
    return sum.getRoundValue();
  }

  public BigDecimal getAvgRound() {
    return sum.divide(count.getValue());
  }

  public BigDecimal getMaxRound() {
    return max.getRoundValue();
  }

  public BigDecimal getMinRound() {
    return min.getRoundValue();
  }

  public long getCount() {
    return count.getValue();
  }

  private boolean isEmpty() {
    return count.equals(Count.ZERO)
        && sum.equals(Amount.ZERO)
        && max.equals(Amount.ZERO)
        && min.equals(Amount.ZERO);
  }

  private void validateInputs(Amount sum, Amount max, Amount min, Count count) {
    if (checkMaxIsGreaterThanMin(max, min)
        || checkSumIsGreaterThanMax(sum, max)
        || checkSumIsBiggerThanMaxMin(sum, max, min, count)
        || checkSumIsZeroWhenCountIsZero(sum, count)) {
      throw new WrongStatisticsInputs();
    }
  }

  private boolean checkSumIsZeroWhenCountIsZero(Amount sum, Count count) {
    return (count.equals(Count.ZERO) && !sum.equals(Amount.ZERO));
  }

  private boolean checkSumIsBiggerThanMaxMin(Amount sum, Amount max, Amount min, Count count) {
    final Amount total = max.sum(min);
    return (count.getValue() > 1 && !total.equals(sum) && sum.min(total).equals(sum));
  }

  private boolean checkSumIsGreaterThanMax(Amount sum, Amount max) {
    return (!sum.equals(max) && sum.min(max).equals(sum));
  }

  private boolean checkMaxIsGreaterThanMin(Amount max, Amount min) {
    return (!max.equals(min) && max.min(min).equals(max));
  }
}
