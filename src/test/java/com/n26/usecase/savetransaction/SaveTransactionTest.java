package com.n26.usecase.savetransaction;

import com.n26.domain.Transaction;
import com.n26.domain.TransactionRepository;
import com.n26.domain.service.TimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.FUTURE;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.PROCESSED;
import static com.n26.utils.DomainFactoryUtils.createTransaction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveTransactionTest {

  private static final OffsetDateTime OCCURRED_AT = OffsetDateTime.now();
  private static final String AMOUNT_VAL = "12.3343";
  private static final BigDecimal AMOUNT = new BigDecimal(AMOUNT_VAL);

  private final TimeService timeService = Mockito.mock(TimeService.class);
  private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
  private final SaveTransaction saveTransaction = new SaveTransaction(timeService, transactionRepository);

  @ParameterizedTest
  @MethodSource("getInputOutPut")
  void shouldReturnExpectedAndNotPersistWhenRequestIsOutOfRange(
      SaveTransactionRequest outOfRangeRequest,
      SaveTransactionResponse expected) {
    when(timeService.getCurrentTime()).thenReturn(OCCURRED_AT);

    final SaveTransactionResponse actual = saveTransaction.save(outOfRangeRequest);

    assertThat(actual).isSameAs(expected);
    verify(transactionRepository, never()).save(any(Transaction.class));
  }

  @Test
  void shouldReturnProcessedAndPersistTransactionWhenTransactionIsInTheRange() {
    when(timeService.getCurrentTime()).thenReturn(OCCURRED_AT);
    final OffsetDateTime inRangeTimestamp = OCCURRED_AT.minus(Duration.ofSeconds(1));

    final SaveTransactionResponse actual = saveTransaction.save(new SaveTransactionRequest(AMOUNT, inRangeTimestamp));

    assertThat(actual).isSameAs(PROCESSED);
    verify(transactionRepository).save(
        argThat(transaction -> {
          assertThat(transaction)
              .isEqualToComparingFieldByField(createTransaction(AMOUNT_VAL, inRangeTimestamp, OCCURRED_AT));
          return true;
        }));
  }

  private static Stream<Arguments> getInputOutPut() {
    return Stream.of(
        Arguments.of(new SaveTransactionRequest(AMOUNT, OCCURRED_AT.minus(Duration.ofSeconds(61))), OLDER),
        Arguments.of(new SaveTransactionRequest(AMOUNT, OCCURRED_AT.plus(Duration.ofSeconds(1))), FUTURE)
    );
  }
}