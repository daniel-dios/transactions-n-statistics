package com.n26.domain;

import com.n26.domain.exception.FutureTransactionTimestampException;
import com.n26.domain.exception.OldTransactionTimestampException;

import java.time.Duration;
import java.time.OffsetDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class TransactionTimestamp {
  private static final Duration MAX_RANGE = Duration.ofSeconds(60);
  private final OffsetDateTime timestamp;

  public TransactionTimestamp(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    validateInputs(timestamp, occurredAt);
    this.timestamp = timestamp;
  }

  public static boolean isInRange(OffsetDateTime currentTime, OffsetDateTime dateTime) {
    return dateTime.isAfter(currentTime.minus(MAX_RANGE));
  }

  public OffsetDateTime getValueSecondsTruncated() {
    return timestamp.truncatedTo(SECONDS);
  }

  private void validateInputs(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    validateOlderThanMaxRange(timestamp, occurredAt);
    validateFuture(timestamp, occurredAt);
  }

  private void validateOlderThanMaxRange(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    if (timestamp.isBefore(occurredAt.minus(MAX_RANGE))) {
      throw new OldTransactionTimestampException();
    }
  }

  private void validateFuture(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    if (occurredAt.isBefore(timestamp)) {
      throw new FutureTransactionTimestampException();
    }
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
