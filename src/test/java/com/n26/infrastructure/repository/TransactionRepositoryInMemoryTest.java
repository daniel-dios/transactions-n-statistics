package com.n26.infrastructure.repository;

import com.n26.domain.Statistics;
import com.n26.domain.TimeService;
import com.n26.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static com.n26.utils.DomainFactoryUtils.createTransaction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TransactionRepositoryInMemoryTest {

  private static final OffsetDateTime OCCURRED_AT = OffsetDateTime.parse("2018-07-17T09:59:51.312Z");
  private static final OffsetDateTime TIMESTAMP = OCCURRED_AT.minus(Duration.ofSeconds(10));

  private final TimeService timeService = Mockito.mock(TimeService.class);

  @Test
  void shouldSaveOneTransaction() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createTransaction("200.000", TIMESTAMP, OCCURRED_AT);

    repositoryInMemory.save(transaction);

    final Statistics expected = createStatistics("200.000", "200.000", "200.000", 1);
    assertThat(repositoryInMemory.getStatistics())
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }

  @Test
  void shouldReturnStatisticsInRange() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(61)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createTransaction("200.000", TIMESTAMP, OCCURRED_AT);
    repositoryInMemory.save(transaction);

    final List<Statistics> actual = repositoryInMemory.getStatistics();

    assertThat(actual).isEmpty();
  }

  @Test
  void shouldMergeTransactionsWhenTheyAreInSameSecond() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createTransaction("200.000", TIMESTAMP, OCCURRED_AT);
    final Transaction otherTransaction =
        createTransaction("100.00", TIMESTAMP.minus(Duration.ofMillis(10)), OCCURRED_AT);

    repositoryInMemory.save(transaction);
    repositoryInMemory.save(otherTransaction);

    final Statistics expected = createStatistics("300.000", "200.000", "100.00", 2);
    assertThat(repositoryInMemory.getStatistics())
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }
}