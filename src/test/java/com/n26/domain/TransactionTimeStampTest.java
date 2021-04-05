package com.n26.domain;

import com.n26.domain.exception.WrongTransactionTimeStampException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTimeStampTest {

  @ParameterizedTest
  @MethodSource("getWrongTimeStamps")
  void shouldFailWhenOffsetIsOlderThan60s(OffsetDateTime wrongTimeStamp) {

    assertThatThrownBy(() -> new TransactionTimeStamp(wrongTimeStamp))
        .isInstanceOf(WrongTransactionTimeStampException.class);
  }

  @Test
  void shouldCreateTransactionTimeStampFromOkTime() {
    final TransactionTimeStamp transactionTimeStamp = new TransactionTimeStamp(OffsetDateTime.now());

    assertThat(transactionTimeStamp)
        .isEqualToComparingFieldByField(transactionTimeStamp)
        .isNotEqualTo(new TransactionTimeStamp(OffsetDateTime.now()));
  }

  private static Stream<Arguments> getWrongTimeStamps() {
    return Stream.of(
        Arguments.of(OffsetDateTime.parse("2018-07-17T09:59:51.312Z")),
        Arguments.of(OffsetDateTime.now().minus(61, SECONDS)),
        Arguments.of(OffsetDateTime.now().minus(1, MINUTES))
    );
  }
}