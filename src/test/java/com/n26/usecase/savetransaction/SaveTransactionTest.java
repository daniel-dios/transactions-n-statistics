package com.n26.usecase.savetransaction;

import com.n26.domain.TimeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.FUTURE;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.PROCESSED;
import static org.mockito.Mockito.when;

class SaveTransactionTest {

  private static final BigDecimal AMOUNT = new BigDecimal("12.3343");
  private static final OffsetDateTime TIME_STAMP = OffsetDateTime.now();
  private static final SaveTransactionRequest REQUEST = new SaveTransactionRequest(AMOUNT, TIME_STAMP);

  private final TimeService timeService = Mockito.mock(TimeService.class);
  private final SaveTransaction saveTransaction = new SaveTransaction(timeService);

  @Test
  void shouldReturnOlderWhenTransactionIsOlder() {
    when(timeService.getCurrentTime()).thenReturn(TIME_STAMP.plus(Duration.ofSeconds(61)));

    final SaveTransactionResponse actual = saveTransaction.save(REQUEST);

    Assertions.assertThat(actual).isSameAs(OLDER);
  }

  @Test
  void shouldReturnSuccessWhenTransactionIsInTheRange() {
    when(timeService.getCurrentTime()).thenReturn(TIME_STAMP.plus(Duration.ofSeconds(2)));

    final SaveTransactionResponse actual = saveTransaction.save(REQUEST);

    Assertions.assertThat(actual).isSameAs(PROCESSED);
  }

  @Test
  void shouldReturnFutureWhenTransactionIsInTheFuture() {
    when(timeService.getCurrentTime()).thenReturn(TIME_STAMP.minus(Duration.ofSeconds(2)));

    final SaveTransactionResponse actual = saveTransaction.save(REQUEST);

    Assertions.assertThat(actual).isSameAs(FUTURE);
  }
}