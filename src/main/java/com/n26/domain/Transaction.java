package com.n26.domain;

public final class Transaction {
  private final Amount amount;
  private final TransactionTimeStamp timeStamp;

  public Transaction(Amount amount, TransactionTimeStamp timeStamp) {
    this.amount = amount;
    this.timeStamp = timeStamp;
  }

  public Amount getAmount() {
    return amount;
  }

  public TransactionTimeStamp getTimeStamp() {
    return timeStamp;
  }
}
