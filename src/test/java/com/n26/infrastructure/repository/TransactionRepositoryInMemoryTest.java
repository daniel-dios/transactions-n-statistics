package com.n26.infrastructure.repository;

import com.n26.domain.Statistics;
import com.n26.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.OffsetDateTime;

import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static com.n26.utils.DomainFactoryUtils.createTransaction;
import static org.assertj.core.api.Assertions.assertThat;

class TransactionRepositoryInMemoryTest {

  @Test
  void shouldSaveOneTransaction() {
    final TransactionRepositoryInMemory repositoryInMemory = new TransactionRepositoryInMemory();
    final OffsetDateTime occurredAt = OffsetDateTime.parse("2018-07-17T09:59:51.312Z");
    final OffsetDateTime timestamp = occurredAt.minus(Duration.ofSeconds(10));
    final Transaction transaction = createTransaction("200.000", timestamp, occurredAt);

    repositoryInMemory.save(transaction);

    final Statistics expected = createStatistics("200.000", "200.000", "200.000", 1);
    assertThat(repositoryInMemory.getStatistics())
        .hasOnlyOneElementSatisfying(value ->
            assertThat(value).isEqualToComparingFieldByField(expected));
  }
}