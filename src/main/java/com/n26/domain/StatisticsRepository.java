package com.n26.domain;

import java.util.Set;

public interface StatisticsRepository {
  Set<Statistics> getStatistics();
}
