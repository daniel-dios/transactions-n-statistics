package com.n26.usecase.deletetransactions;

import com.n26.domain.TransactionRepository;

public class DeleteTransactions {
  private final TransactionRepository transactionRepository;

  public DeleteTransactions(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public void deleteTransactions() {
    transactionRepository.deleteTransactions();
  }
}
