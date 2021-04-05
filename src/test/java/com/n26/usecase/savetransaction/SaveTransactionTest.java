package com.n26.usecase.savetransaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;

class SaveTransactionTest {

  @Test
  void shouldReturnOlderWhenTransactionIsOlderThan60s() {
    final BigDecimal amount = new BigDecimal("12.3343");
    final OffsetDateTime timeStamp = OffsetDateTime.parse("2018-07-17T09:59:51.312Z");
    final SaveTransactionRequest request = new SaveTransactionRequest(amount, timeStamp);
    final SaveTransaction saveTransaction = new SaveTransaction();

    final SaveTransactionResponse actual = saveTransaction.save(request);

    Assertions.assertThat(actual).isSameAs(OLDER);
  }
}