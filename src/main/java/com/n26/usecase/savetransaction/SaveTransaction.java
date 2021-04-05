package com.n26.usecase.savetransaction;

import com.n26.domain.TransactionTimeStamp;
import com.n26.domain.exception.WrongTransactionTimeStampException;

import java.time.OffsetDateTime;

public class SaveTransaction {
  public SaveTransactionResponse save(SaveTransactionRequest request) {
    try {
      new TransactionTimeStamp(request.getTimeStamp(), OffsetDateTime.now());
      return SaveTransactionResponse.PROCESSED;
    } catch (WrongTransactionTimeStampException ex) {
      return SaveTransactionResponse.OLDER;
    }
  }
}
