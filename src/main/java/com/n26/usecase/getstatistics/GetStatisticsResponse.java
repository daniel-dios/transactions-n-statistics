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
        statistics.getSum(),
        statistics.getAvg(),
        statistics.getMax(),
        statistics.getMin(),
        statistics.getCount()
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GetStatisticsResponse that = (GetStatisticsResponse) o;

    if (count != that.count) {
      return false;
    }
    if (!sum.equals(that.sum)) {
      return false;
    }
    if (!avg.equals(that.avg)) {
      return false;
    }
    if (!max.equals(that.max)) {
      return false;
    }
    return min.equals(that.min);
  }

  @Override
  public int hashCode() {
    int result = sum.hashCode();
    result = 31 * result + avg.hashCode();
    result = 31 * result + max.hashCode();
    result = 31 * result + min.hashCode();
    result = 31 * result + (int) (count ^ (count >>> 32));
    return result;
  }
}
