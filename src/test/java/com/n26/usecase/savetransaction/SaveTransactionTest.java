package com.n26.usecase.savetransaction;

import com.n26.domain.Amount;
import com.n26.domain.TimeService;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.TransactionTimeStamp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.FUTURE;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.PROCESSED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveTransactionTest {

  private static final OffsetDateTime TIME_STAMP = OffsetDateTime.now();
  private static final BigDecimal AMOUNT = new BigDecimal("12.3343");
  private static final SaveTransactionRequest REQUEST = new SaveTransactionRequest(AMOUNT, TIME_STAMP);

  private final TimeService timeService = Mockito.mock(TimeService.class);
  private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
  private final SaveTransaction saveTransaction = new SaveTransaction(timeService, transactionRepository);

  @Test
  void shouldReturnOlderWhenTransactionIsOlder() {
    when(timeService.getCurrentTime()).thenReturn(TIME_STAMP.plus(Duration.ofSeconds(61)));

    final SaveTransactionResponse actual = saveTransaction.save(REQUEST);

    assertThat(actual).isSameAs(OLDER);
    verify(transactionRepository, never()).save(any(Transaction.class));
  }

  @Test
  void shouldReturnSuccessWhenTransactionIsInTheRange() {
    final OffsetDateTime timeStampInRange = TIME_STAMP.plus(Duration.ofSeconds(2));
    when(timeService.getCurrentTime()).thenReturn(timeStampInRange);

    final SaveTransactionResponse actual = saveTransaction.save(REQUEST);

    assertThat(actual).isSameAs(PROCESSED);
    final Amount expectedAmount = new Amount(AMOUNT);
    final TransactionTimeStamp expectedTimeStamp = new TransactionTimeStamp(TIME_STAMP, timeStampInRange);
    verify(transactionRepository).save(
        argThat(transaction -> {
          assertThat(transaction.getAmount()).isEqualToComparingFieldByField(expectedAmount);
          assertThat(transaction.getTimeStamp()).isEqualToComparingFieldByField(expectedTimeStamp);
          return true;
        }));
  }

  @Test
  void shouldReturnFutureWhenTransactionIsInTheFuture() {
    when(timeService.getCurrentTime()).thenReturn(TIME_STAMP.minus(Duration.ofSeconds(2)));

    final SaveTransactionResponse actual = saveTransaction.save(REQUEST);

    assertThat(actual).isSameAs(FUTURE);
    verify(transactionRepository, never()).save(any(Transaction.class));
  }
}