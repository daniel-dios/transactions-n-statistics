package com.n26.usecase.savetransaction;

import com.n26.domain.FutureTimeStampException;
import com.n26.domain.TransactionTimeStamp;
import com.n26.domain.exception.OldTimeStampException;

import java.time.OffsetDateTime;

public class SaveTransaction {
  public SaveTransactionResponse save(SaveTransactionRequest request) {
    try {
      new TransactionTimeStamp(request.getTimeStamp(), OffsetDateTime.now());
      return SaveTransactionResponse.PROCESSED;
    } catch (OldTimeStampException ex) {
      return SaveTransactionResponse.OLDER;
    } catch (FutureTimeStampException ex) {
      return SaveTransactionResponse.FUTURE;
    }
  }
}
