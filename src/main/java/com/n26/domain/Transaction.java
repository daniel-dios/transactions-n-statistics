package com.n26.domain;

import java.time.OffsetDateTime;

public final class Transaction {
  private final Amount amount;
  private final TransactionTimestamp timestamp;

  public Transaction(Amount amount, TransactionTimestamp timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public Amount getAmount() {
    return amount;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp.getValueSecondsTruncated();
  }
}
