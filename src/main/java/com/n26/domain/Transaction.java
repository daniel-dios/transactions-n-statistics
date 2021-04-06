package com.n26.domain;

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
}
