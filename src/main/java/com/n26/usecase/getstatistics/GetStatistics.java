package com.n26.usecase.getstatistics;

import com.n26.domain.Amount;
import com.n26.domain.Count;
import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;

import java.math.BigDecimal;

public class GetStatistics {
  private final StatisticsRepository statisticsRepository;

  public GetStatistics(StatisticsRepository statisticsRepository) {
    this.statisticsRepository = statisticsRepository;
  }

  public Statistics getStatistics() {
    final Amount zero = new Amount(new BigDecimal("0"));
    return statisticsRepository.getStatistics().isEmpty()
        ? new Statistics(zero, zero, zero, zero, new Count(0))
        : null;
  }
}
