package com.n26.domain;

import com.n26.domain.exception.WrongTransactionTimeStampException;

import java.time.Duration;
import java.time.OffsetDateTime;

public class TransactionTimeStamp {
  private static final Duration OLD_RANGE = Duration.ofSeconds(60);
  private final OffsetDateTime timeStamp;

  public TransactionTimeStamp(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    checkOldRange(timeStamp, occurredAt);
    this.timeStamp = timeStamp;
  }

  private void checkOldRange(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    if (isOutOfRange(timeStamp, occurredAt)) {
      throw new WrongTransactionTimeStampException();
    }
  }

  private boolean isOutOfRange(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    return timeStamp.isBefore(occurredAt.minus(OLD_RANGE));
  }
}
