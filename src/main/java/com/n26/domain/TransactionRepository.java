package com.n26.domain;

public interface TransactionRepository {
  void save(Transaction transaction);

  void deleteAllTransactions();
}
