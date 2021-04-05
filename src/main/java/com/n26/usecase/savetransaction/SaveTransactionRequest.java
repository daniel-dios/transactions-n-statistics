package com.n26.usecase.savetransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class SaveTransactionRequest {
  private final BigDecimal amount;
  private final OffsetDateTime timeStamp;

  public SaveTransactionRequest(BigDecimal amount, OffsetDateTime timeStamp) {
    this.amount = amount;
    this.timeStamp = timeStamp;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OffsetDateTime getTimeStamp() {
    return timeStamp;
  }
}
