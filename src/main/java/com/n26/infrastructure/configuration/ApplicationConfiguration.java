package com.n26.infrastructure.configuration;

import com.n26.domain.StatisticsRepository;
import com.n26.domain.TransactionRepository;
import com.n26.domain.service.TimeService;
import com.n26.infrastructure.repository.TransactionRepositoryInMemory;
import com.n26.usecase.deletetransactions.DeleteTransactions;
import com.n26.usecase.getstatistics.GetStatistics;
import com.n26.usecase.savetransaction.SaveTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public DeleteTransactions deleteTransactions(TransactionRepository transactionsRepository) {
    return new DeleteTransactions(transactionsRepository);
  }

  @Bean
  public TransactionRepositoryInMemory transactionRepository(TimeService timeService) {
    return new TransactionRepositoryInMemory(timeService);
  }

  @Bean
  public TimeService timeService() {
    return new TimeService();
  }

  @Bean
  public GetStatistics getStatistics(StatisticsRepository statisticsRepository) {
    return new GetStatistics(statisticsRepository);
  }

  @Bean
  public SaveTransaction saveTransaction(
      TimeService timeService,
      TransactionRepository transactionRepository) {
    return new SaveTransaction(
        timeService,
        transactionRepository);
  }
}
