package com.n26.infrastructure.repository;

import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import com.n26.domain.TimeService;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.TransactionTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.n26.domain.Statistics.EMPTY_STATISTICS;

public class TransactionRepositoryInMemory implements TransactionRepository, StatisticsRepository {

  private final Map<OffsetDateTime, Statistics> statisticsMap = new ConcurrentHashMap<>();
  private final TimeService timeService;

  public TransactionRepositoryInMemory(TimeService timeService) {
    this.timeService = timeService;
  }

  @Override
  public List<Statistics> getStatistics() {
    final OffsetDateTime currentTime = timeService.getCurrentTime();

    return statisticsMap
        .entrySet()
        .stream()
        .filter(entry -> TransactionTimestamp.isInRange(currentTime, entry.getKey()))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }

  @Override
  public void save(Transaction transaction) {
    statisticsMap.merge(
        transaction.getTimestamp(),
        EMPTY_STATISTICS.aggregate(transaction),
        Statistics::merge);

    statisticsMap
        .keySet()
        .stream()
        .filter(entry -> !TransactionTimestamp.isInRange(timeService.getCurrentTime(), entry))
        .forEach(statisticsMap::remove);
  }

  @Override
  public void deleteTransactions() {
    statisticsMap.clear();
  }
}
