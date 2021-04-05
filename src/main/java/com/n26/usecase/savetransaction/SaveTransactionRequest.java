package com.n26.usecase.savetransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class SaveTransactionRequest {
  private final OffsetDateTime timeStamp;

  public SaveTransactionRequest(BigDecimal amount, OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public OffsetDateTime getTimeStamp() {
    return timeStamp;
  }
}
