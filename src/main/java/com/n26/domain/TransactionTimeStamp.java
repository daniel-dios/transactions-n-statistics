package com.n26.domain;

import com.n26.domain.exception.WrongTransactionTimeStampException;

import java.time.Duration;
import java.time.OffsetDateTime;

public class TransactionTimeStamp {
  private static final Duration OLD_RANGE = Duration.ofSeconds(60);
  private final OffsetDateTime timeStamp;

  public TransactionTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
    checkOldRange(timeStamp);
  }

  private void checkOldRange(OffsetDateTime timeStamp) {
    if (isOutOfRange(timeStamp)) {
      throw new WrongTransactionTimeStampException();
    }
  }

  private boolean isOutOfRange(OffsetDateTime timeStamp) {
    return timeStamp.isBefore(OffsetDateTime.now().minus(OLD_RANGE));
  }
}
