package com.n26.domain;

import com.n26.domain.exception.OldTimeStampException;

import java.time.Duration;
import java.time.OffsetDateTime;

public class TransactionTimeStamp {
  private static final Duration OLD_RANGE = Duration.ofSeconds(60);
  private final OffsetDateTime timeStamp;

  public TransactionTimeStamp(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    checkOldRange(timeStamp, occurredAt);
    checkFutureRange(timeStamp, occurredAt);
    this.timeStamp = timeStamp;
  }

  private void checkOldRange(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    if (isOlderRange(timeStamp, occurredAt)) {
      throw new OldTimeStampException();
    }
  }

  private boolean isOlderRange(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    return timeStamp.isBefore(occurredAt.minus(OLD_RANGE));
  }

  private void checkFutureRange(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    if (isFutureRange(timeStamp, occurredAt)) {
      throw new FutureTimeStampException();
    }
  }

  private boolean isFutureRange(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    return occurredAt.isBefore(timeStamp);
  }
}
