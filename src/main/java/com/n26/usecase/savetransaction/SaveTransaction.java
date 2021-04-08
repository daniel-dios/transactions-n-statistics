package com.n26.usecase.savetransaction;

import com.n26.domain.Amount;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.TransactionTimestamp;
import com.n26.domain.exception.FutureTransactionTimestampException;
import com.n26.domain.exception.OldTransactionTimestampException;
import com.n26.domain.service.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.FUTURE;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.PROCESSED;

public class SaveTransaction {
  private static final Logger LOGGER = LoggerFactory.getLogger(SaveTransaction.class);

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
      final OffsetDateTime currentTime = timeService.getCurrentTime();
      LOGGER.info("Save transaction at {}.", currentTime);

      final TransactionTimestamp transactionTimeStamp = new TransactionTimestamp(request.getTimestamp(), currentTime);
      final Transaction transaction = new Transaction(new Amount(request.getAmount()), transactionTimeStamp);

      transactionRepository.save(transaction);

      return PROCESSED;
    } catch (OldTransactionTimestampException ex) {
      LOGGER.info("Timestamp is older.");
      return OLDER;
    } catch (FutureTransactionTimestampException ex) {
      LOGGER.info("Timestamp is in future");
      return FUTURE;
    }
  }
}
