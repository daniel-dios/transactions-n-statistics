package com.n26.usecase.savetransaction;

import com.n26.domain.TransactionTimeStamp;
import com.n26.domain.exception.WrongTimeStampException;

public class SaveTransaction {
  public SaveTransactionResponse save(SaveTransactionRequest request) {
    try {
      new TransactionTimeStamp(request.getTimeStamp());
      return SaveTransactionResponse.SUCCESS;
    } catch (WrongTimeStampException ex) {
      return SaveTransactionResponse.OLDER;
    }
  }
}
