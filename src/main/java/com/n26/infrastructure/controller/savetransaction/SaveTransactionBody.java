package com.n26.infrastructure.controller.savetransaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveTransactionBody {

  private final String amount;
  private final String timestamp;

  public SaveTransactionBody(
      @JsonProperty(value = "amount", required = true) String amount,
      @JsonProperty(value = "timestamp", required = true) String timestamp
  ) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  String getAmount() {
    return amount;
  }

  String getTimestamp() {
    return timestamp;
  }
}
