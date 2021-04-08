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
    final Amount amount = transaction.getAmount();
    return new Statistics(
        sum.sum(amount),
        max.max(amount),
        isEmpty() ? amount : min.min(amount),
        count.increment()
    );
  }

  public BigDecimal getSumRound() {
    return sum.getRoundValue();
  }

  public BigDecimal getAvgRound() {
    return sum.divide(count.getValue()).getRoundValue();
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
    if (minIsGreaterThanMax(max, min)
        || maxIsGreaterThanSum(sum, max)
        || maxPlusMinAreGreaterThanSumWhenMoreThanOneCount(sum, min.sum(max), count)
        || sumIsZeroWhenCountIsNotZero(sum, count)) {
      throw new WrongStatisticsInputs();
    }
  }

  private boolean sumIsZeroWhenCountIsNotZero(Amount sum, Count count) {
    return checkSumIsZeroWhenCountIsZero(sum, count);
  }

  private boolean maxPlusMinAreGreaterThanSumWhenMoreThanOneCount(Amount sum, Amount total, Count count) {
    return theresIsOnlyOne(count) && secondGreaterThanFirst(sum, total);
  }

  private boolean maxIsGreaterThanSum(Amount sum, Amount max) {
    return secondGreaterThanFirst(sum, max);
  }

  private boolean minIsGreaterThanMax(Amount max, Amount min) {
    return secondGreaterThanFirst(max, min);
  }

  private boolean secondGreaterThanFirst(Amount a, Amount b) {
    return b.compare(a) > 0;
  }

  private boolean theresIsOnlyOne(Count count) {
    return count.compare(Count.ONE) > 0;
  }

  private boolean checkSumIsZeroWhenCountIsZero(Amount sum, Count count) {
    return (count.compare(Count.ZERO) == 0 && sum.compare(Amount.ZERO) != 0);
  }
}
