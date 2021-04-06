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

  public static Statistics merge(Statistics a, Statistics b) {
    final Amount sum = a.sum.sum(b.sum);
    final Amount max = a.max.max(b.max);
    final Amount min = a.min.min(b.max);
    final Count count = a.count.add(b.count);
    final Amount avg = sum.divideBy(count.toAmount());
    return new Statistics(
        sum,
        avg,
        max,
        min,
        count
    );
  }
}
