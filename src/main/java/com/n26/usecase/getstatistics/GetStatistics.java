package com.n26.usecase.getstatistics;

import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;

public class GetStatistics {
  private final StatisticsRepository statisticsRepository;

  public GetStatistics(StatisticsRepository statisticsRepository) {
    this.statisticsRepository = statisticsRepository;
  }

  public Statistics getStatistics() {
    return statisticsRepository
        .getStatistics()
        .stream()
        .reduce(Statistics::merge)
        .orElse(Statistics.EMPTY_STATISTICS);
  }
}
