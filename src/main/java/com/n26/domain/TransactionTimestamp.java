package com.n26.domain;

import com.n26.domain.exception.FutureTransactionTimestampException;
import com.n26.domain.exception.OldTransactionTimestampException;

import java.time.Duration;
import java.time.OffsetDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class TransactionTimestamp {
  private static final Duration OLD_RANGE = Duration.ofSeconds(60);
  private final OffsetDateTime timestamp;

  public TransactionTimestamp(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    validateInputs(timestamp, occurredAt);
    this.timestamp = timestamp;
  }

  public static boolean isInRange(OffsetDateTime currentTime, OffsetDateTime dateTime) {
    return dateTime.isAfter(currentTime.minus(OLD_RANGE));
  }

  public OffsetDateTime getValueSecondsTruncated() {
    return timestamp.truncatedTo(SECONDS);
  }

  private void validateInputs(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    checkOldRange(timestamp, occurredAt);
    checkFutureRange(timestamp, occurredAt);
  }

  private void checkOldRange(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    if (isOlderRange(timestamp, occurredAt)) {
      throw new OldTransactionTimestampException();
    }
  }

  private boolean isOlderRange(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    return timestamp.isBefore(occurredAt.minus(OLD_RANGE));
  }

  private void checkFutureRange(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    if (isFutureRange(timestamp, occurredAt)) {
      throw new FutureTransactionTimestampException();
    }
  }

  private boolean isFutureRange(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    return occurredAt.isBefore(timestamp);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TransactionTimestamp that = (TransactionTimestamp) o;

    return timestamp.equals(that.timestamp);
  }

  @Override
  public int hashCode() {
    return timestamp.hashCode();
  }
}
