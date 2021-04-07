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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SaveTransactionRequest that = (SaveTransactionRequest) o;

    if (!amount.equals(that.amount)) {
      return false;
    }
    return timestamp.equals(that.timestamp);
  }

  @Override
  public int hashCode() {
    int result = amount.hashCode();
    result = 31 * result + timestamp.hashCode();
    return result;
  }
}
