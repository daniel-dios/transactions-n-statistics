package com.n26.usecase.deletetransactions;

import com.n26.domain.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class DeleteTransactionsTest {

  @Test
  void shouldCallDeleteTransactions() {
    final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    final DeleteTransactions deleteTransactions = new DeleteTransactions(transactionRepository);

    deleteTransactions.deleteTransactions();

    verify(transactionRepository).deleteAllTransactions();
  }
}