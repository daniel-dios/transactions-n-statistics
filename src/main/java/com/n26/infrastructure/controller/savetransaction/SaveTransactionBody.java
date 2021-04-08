package com.n26.infrastructure.controller.savetransaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveTransactionBody {

  @JsonProperty(value = "amount", required = true)
  private final String amount;

  @JsonProperty(value = "timestamp", required = true)
  private final String timestamp;

  public SaveTransactionBody(
      String amount,
      String timestamp
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
