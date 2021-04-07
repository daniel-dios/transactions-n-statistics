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

  private static final OffsetDateTime TIMESTAMP = OffsetDateTime.parse("2018-07-17T09:59:51.312Z");

  private final TimeService timeService = Mockito.mock(TimeService.class);

  @Test
  void shouldSaveOneTransaction() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createValidTransaction("200.000", TIMESTAMP, 10);

    repositoryInMemory.save(transaction);

    final Statistics expected = createStatistics("200.000", "200.000", "200.000", 1);
    assertThat(repositoryInMemory.getStatistics())
        .hasSize(1)
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }

  @Test
  void shouldReturnStatisticsInRange() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(61)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createValidTransaction("200.000", TIMESTAMP, 10);
    repositoryInMemory.save(transaction);

    final List<Statistics> actual = repositoryInMemory.getStatistics();

    assertThat(actual).isEmpty();
  }

  @Test
  void shouldMergeTransactionsWhenTheyAreInSameSecond() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createValidTransaction("200.000", TIMESTAMP, 10);
    final Transaction otherTransaction = createValidTransaction("100.00", TIMESTAMP.minus(Duration.ofMillis(10)), 10);

    repositoryInMemory.save(transaction);
    repositoryInMemory.save(otherTransaction);

    final Statistics expected = createStatistics("300.000", "200.000", "100.00", 2);
    assertThat(repositoryInMemory.getStatistics())
        .hasSize(1)
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }

  @Test
  void shouldDeleteOutOfRangeStoredEntries() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createValidTransaction("200.000", TIMESTAMP, 10);
    repositoryInMemory.save(transaction);

    final Transaction otherTransaction = createValidTransaction("100.00", TIMESTAMP.plus(Duration.ofHours(1)), 30);
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30).plus(Duration.ofHours(1))));
    repositoryInMemory.save(otherTransaction);

    final Statistics expected = createStatistics("100.00", "100.00", "100.00", 1);
    assertThat(repositoryInMemory.getStatistics())
        .hasSize(1)
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));

    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    assertThat(repositoryInMemory.getStatistics())
        .hasSize(1)
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }

  @Test
  void shouldDeleteAllTransactions() {
    when(timeService.getCurrentTime()).thenReturn(TIMESTAMP.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createValidTransaction("200.000", TIMESTAMP, 10);

    repositoryInMemory.save(transaction);

    final Statistics expected = createStatistics("200.000", "200.000", "200.000", 1);
    assertThat(repositoryInMemory.getStatistics())
        .hasSize(1)
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));

    repositoryInMemory.deleteTransactions();
    assertThat(repositoryInMemory.getStatistics())
        .isEmpty();
  }

  private Transaction createValidTransaction(String s, OffsetDateTime plus, int afterSeconds) {
    return createTransaction(s, plus, plus.plus(Duration.ofSeconds(afterSeconds)));
  }
}