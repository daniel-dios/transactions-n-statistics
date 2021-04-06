package com.n26.infrastructure.repository;

import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionRepositoryInMemory implements TransactionRepository, StatisticsRepository {

  private final Map<OffsetDateTime, Statistics> statisticsMap = new ConcurrentHashMap<>();

  @Override
  public Set<Statistics> getStatistics() {
    return new HashSet<>(statisticsMap.values());
  }

  @Override
  public void save(Transaction transaction) {
    statisticsMap.put(transaction.getTimestamp(), Statistics.EMPTY_STATISTICS.aggregate(transaction));
  }

  @Override
  public void deleteTransactions() {
    throw new UnsupportedOperationException();
  }
}
