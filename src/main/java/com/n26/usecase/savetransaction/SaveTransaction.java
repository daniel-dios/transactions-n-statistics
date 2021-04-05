package com.n26.usecase.savetransaction;

import com.n26.domain.Amount;
import com.n26.domain.FutureTimeStampException;
import com.n26.domain.TimeService;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.TransactionTimeStamp;
import com.n26.domain.exception.OldTimeStampException;

public class SaveTransaction {
  private final TimeService timeService;
  private final TransactionRepository transactionRepository;

  public SaveTransaction(TimeService timeService, TransactionRepository transactionRepository) {
    this.timeService = timeService;
    this.transactionRepository = transactionRepository;
  }

  public SaveTransactionResponse save(SaveTransactionRequest request) {
    try {
      final TransactionTimeStamp transactionTimeStamp = new TransactionTimeStamp(request.getTimeStamp(), timeService.getCurrentTime());
      final Amount amount = new Amount(request.getAmount());
      final Transaction transaction = new Transaction(amount, transactionTimeStamp);
      transactionRepository.save(transaction);

      return SaveTransactionResponse.PROCESSED;
    } catch (OldTimeStampException ex) {
      return SaveTransactionResponse.OLDER;
    } catch (FutureTimeStampException ex) {
      return SaveTransactionResponse.FUTURE;
    }
  }
}
