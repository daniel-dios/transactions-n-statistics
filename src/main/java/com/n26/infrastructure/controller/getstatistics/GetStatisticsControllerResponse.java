package com.n26.infrastructure.controller.getstatistics;

import com.n26.usecase.getstatistics.GetStatisticsResponse;

public class GetStatisticsControllerResponse {

  private final String sum;
  private final String avg;
  private final String max;
  private final String min;
  private final long count;

  GetStatisticsControllerResponse(
      String sum,
      String avg,
      String max,
      String min,
      long count) {
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public static GetStatisticsControllerResponse mapFrom(GetStatisticsResponse statistics) {
    return new GetStatisticsControllerResponse(
        statistics.getSum().toPlainString(),
        statistics.getAvg().toPlainString(),
        statistics.getMax().toPlainString(),
        statistics.getMin().toPlainString(),
        statistics.getCount()
    );
  }

  public String getSum() {
    return sum;
  }

  public String getAvg() {
    return avg;
  }

  public String getMax() {
    return max;
  }

  public String getMin() {
    return min;
  }

  public long getCount() {
    return count;
  }
}
