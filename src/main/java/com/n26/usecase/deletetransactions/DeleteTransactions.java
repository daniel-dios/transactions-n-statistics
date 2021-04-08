package com.n26.usecase.deletetransactions;

import com.n26.domain.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTransactions {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTransactions.class);

  private final TransactionRepository transactionRepository;

  public DeleteTransactions(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public void deleteTransactions() {
    LOGGER.info("Delete transaction.");

    transactionRepository.deleteAllTransactions();
  }
}
