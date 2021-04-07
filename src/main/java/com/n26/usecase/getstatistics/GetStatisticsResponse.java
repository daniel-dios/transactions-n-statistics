package com.n26.usecase.getstatistics;

import com.n26.domain.Statistics;

import java.math.BigDecimal;

public class GetStatisticsResponse {
  private final BigDecimal sum;
  private final BigDecimal avg;
  private final BigDecimal max;
  private final BigDecimal min;
  private final long count;

  GetStatisticsResponse(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public static GetStatisticsResponse mapToGetStatisticsResponse(Statistics statistics) {
    return new GetStatisticsResponse(
        statistics.getSumRound(),
        statistics.getAvgRound(),
        statistics.getMaxRound(),
        statistics.getMinRound(),
        statistics.getCount()
    );
  }

  public BigDecimal getSum() {
    return sum;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getMin() {
    return min;
  }

  public long getCount() {
    return count;
  }
}
