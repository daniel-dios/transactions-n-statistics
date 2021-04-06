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

  @Test
  void shouldSaveOneTransaction() {
    final OffsetDateTime occurredAt = OffsetDateTime.parse("2018-07-17T09:59:51.312Z");
    final OffsetDateTime timestamp = occurredAt.minus(Duration.ofSeconds(10));
    final TimeService timeService = Mockito.mock(TimeService.class);
    when(timeService.getCurrentTime()).thenReturn(occurredAt.plus(Duration.ofSeconds(30)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createTransaction("200.000", timestamp, occurredAt);

    repositoryInMemory.save(transaction);

    final Statistics expected = createStatistics("200.000", "200.000", "200.000", 1);
    assertThat(repositoryInMemory.getStatistics())
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }

  @Test
  void shouldReturnStatisticsInRange() {
    final OffsetDateTime occurredAt = OffsetDateTime.parse("2018-07-17T09:59:51.312Z");
    final OffsetDateTime timestamp = occurredAt.minus(Duration.ofSeconds(10));
    final TimeService timeService = Mockito.mock(TimeService.class);
    when(timeService.getCurrentTime()).thenReturn(occurredAt.plus(Duration.ofSeconds(61)));
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory(timeService);
    final Transaction transaction = createTransaction("200.000", timestamp, occurredAt);
    repositoryInMemory.save(transaction);

    final List<Statistics> actual = repositoryInMemory.getStatistics();

    assertThat(actual).isEmpty();
  }
}