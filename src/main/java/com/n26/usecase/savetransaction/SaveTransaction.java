package com.n26.usecase.savetransaction;

import com.n26.domain.FutureTimeStampException;
import com.n26.domain.TimeService;
import com.n26.domain.TransactionTimeStamp;
import com.n26.domain.exception.OldTimeStampException;

public class SaveTransaction {
  private final TimeService timeService;

  public SaveTransaction(TimeService timeService) {
    this.timeService = timeService;
  }

  public SaveTransactionResponse save(SaveTransactionRequest request) {
    try {
      new TransactionTimeStamp(request.getTimeStamp(), timeService.getCurrentTime());
      return SaveTransactionResponse.PROCESSED;
    } catch (OldTimeStampException ex) {
      return SaveTransactionResponse.OLDER;
    } catch (FutureTimeStampException ex) {
      return SaveTransactionResponse.FUTURE;
    }
  }
}
