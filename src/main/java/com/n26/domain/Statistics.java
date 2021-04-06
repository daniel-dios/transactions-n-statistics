package com.n26.domain;

public final class Statistics {
  public static final Statistics EMPTY_STATISTICS = new Statistics(
      Amount.ZERO,
      Amount.ZERO,
      Amount.ZERO,
      Amount.ZERO,
      Count.ZERO);

  private final Amount sum;
  private final Amount avg;
  private final Amount max;
  private final Amount min;
  private final Count count;

  public Statistics(
      Amount sum,
      Amount avg,
      Amount max,
      Amount min,
      Count count) {

    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }
}
