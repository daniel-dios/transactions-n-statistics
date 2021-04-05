package com.n26.domain;

import com.n26.domain.exception.WrongTimeStampException;

import java.time.OffsetDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TransactionTimeStamp {
  private final OffsetDateTime timeStamp;

  public TransactionTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
    if (timeStamp.isBefore(OffsetDateTime.now().minus(60, SECONDS))) {
      throw new WrongTimeStampException();
    }
  }
}
