package com.n26.domain;

import com.n26.domain.exception.FutureTransactionTimestampException;
import com.n26.domain.exception.OldTransactionTimestampException;

import java.time.Duration;
import java.time.OffsetDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class TransactionTimestamp {
  private static final Duration MAX_RANGE = Duration.ofSeconds(60);
  private final OffsetDateTime value;

  public TransactionTimestamp(OffsetDateTime value, OffsetDateTime occurredAt) {
    validateInputs(value, occurredAt);
    this.value = value;
  }

  public static boolean isInRange(OffsetDateTime currentTime, OffsetDateTime dateTime) {
    return dateTime.isAfter(currentTime.minus(MAX_RANGE));
  }

  public OffsetDateTime getValueSecondsTruncated() {
    return value.truncatedTo(SECONDS);
  }

  private void validateInputs(OffsetDateTime value, OffsetDateTime occurredAt) {
    validateOlderThanMaxRange(value, occurredAt);
    validateFuture(value, occurredAt);
  }

  private void validateOlderThanMaxRange(OffsetDateTime value, OffsetDateTime occurredAt) {
    if (value.isBefore(occurredAt.minus(MAX_RANGE))) {
      throw new OldTransactionTimestampException();
    }
  }

  private void validateFuture(OffsetDateTime value, OffsetDateTime occurredAt) {
    if (occurredAt.isBefore(value)) {
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

    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
