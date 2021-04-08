package com.n26.usecase.getstatistics;

import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetStatistics {
  private static final Logger LOGGER = LoggerFactory.getLogger(GetStatistics.class);

  private final StatisticsRepository statisticsRepository;

  public GetStatistics(StatisticsRepository statisticsRepository) {
    this.statisticsRepository = statisticsRepository;
  }

  public GetStatisticsResponse getStatistics() {
    LOGGER.info("Get statistics.");

    return
        GetStatisticsResponse.mapToGetStatisticsResponse(
            statisticsRepository
                .getStatistics()
                .stream()
                .reduce(Statistics::merge)
                .orElse(Statistics.EMPTY_STATISTICS));
  }
}
