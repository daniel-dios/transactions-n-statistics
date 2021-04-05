package com.n26.usecase.savetransaction;

import com.n26.domain.Amount;
import com.n26.domain.exception.FutureTimestampException;
import com.n26.domain.TimeService;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.TransactionTimestamp;
import com.n26.domain.exception.OldTimestampException;

public class SaveTransaction {
  private final TimeService timeService;
  private final TransactionRepository transactionRepository;

  public SaveTransaction(
      TimeService timeService,
      TransactionRepository transactionRepository) {
    this.timeService = timeService;
    this.transactionRepository = transactionRepository;
  }

  public SaveTransactionResponse save(SaveTransactionRequest request) {
    try {
      final TransactionTimestamp transactionTimeStamp =
          new TransactionTimestamp(request.getTimestamp(), timeService.getCurrentTime());
      final Transaction transaction =
          new Transaction(new Amount(request.getAmount()), transactionTimeStamp);

      transactionRepository.save(transaction);

      return SaveTransactionResponse.PROCESSED;
    } catch (OldTimestampException ex) {
      return SaveTransactionResponse.OLDER;
    } catch (FutureTimestampException ex) {
      return SaveTransactionResponse.FUTURE;
    }
  }
}
