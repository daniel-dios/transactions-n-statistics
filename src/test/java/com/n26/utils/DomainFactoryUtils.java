package com.n26.utils;

import com.n26.domain.Amount;
import com.n26.domain.Count;
import com.n26.domain.Statistics;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public final class DomainFactoryUtils {

  private DomainFactoryUtils() {
    // Utility factory class for tests
  }

  public static Statistics createStatistics(String sum, String max, String min, int count) {
    return new Statistics(
        createAmount(sum),
        createAmount(max),
        createAmount(min),
        new Count(count)
    );
  }

  public static Amount createAmount(String value) {
    return new Amount(new BigDecimal(value));
  }

  public static Transaction createTransaction(String amountVal, OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    return new Transaction(createAmount(amountVal), new TransactionTimestamp(timeStamp, occurredAt));
  }
}
