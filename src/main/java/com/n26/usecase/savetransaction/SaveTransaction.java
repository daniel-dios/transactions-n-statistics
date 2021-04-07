package com.n26.usecase.savetransaction;

import com.n26.domain.Amount;
import com.n26.domain.service.TimeService;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.TransactionTimestamp;
import com.n26.domain.exception.FutureTransactionTimestampException;
import com.n26.domain.exception.OldTransactionTimestampException;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.FUTURE;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.PROCESSED;

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

      return PROCESSED;
    } catch (OldTransactionTimestampException ex) {
      return OLDER;
    } catch (FutureTransactionTimestampException ex) {
      return FUTURE;
    }
  }
}
