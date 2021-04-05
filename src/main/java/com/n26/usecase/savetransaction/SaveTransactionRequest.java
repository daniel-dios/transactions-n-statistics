package com.n26.usecase.savetransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class SaveTransactionRequest {
  private final BigDecimal amount;
  private final OffsetDateTime timestamp;

  public SaveTransactionRequest(BigDecimal amount, OffsetDateTime timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }
}
