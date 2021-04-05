package com.n26.domain;

import com.n26.domain.exception.OldTimeStampException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static java.time.OffsetDateTime.parse;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTimeStampTest {

  @ParameterizedTest
  @MethodSource("getInputsOutOf60Seconds")
  void shouldFailWhenOffsetIsOlderThan60s(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {

    assertThatThrownBy(() -> new TransactionTimeStamp(timeStamp, occurredAt))
        .isInstanceOf(OldTimeStampException.class);
  }

  @ParameterizedTest
  @MethodSource("getInputsInside60sRange")
  void shouldCreateTransactionTimeStampFromOkTime(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {
    final TransactionTimeStamp transactionTimeStamp = new TransactionTimeStamp(timeStamp, occurredAt);

    assertThat(transactionTimeStamp)
        .isEqualToComparingFieldByField(new TransactionTimeStamp(timeStamp, occurredAt))
        .isNotEqualTo(new TransactionTimeStamp(OffsetDateTime.now(), OffsetDateTime.now()));
  }

  @ParameterizedTest
  @MethodSource("getInputsWithTimeStampAfterOccurredAt")
  void shouldFailWhenOffsetIsAfterOccurredAt(OffsetDateTime timeStamp, OffsetDateTime occurredAt) {

    assertThatThrownBy(() -> new TransactionTimeStamp(timeStamp, occurredAt))
        .isInstanceOf(FutureTimeStampException.class);
  }

  private static Stream<Arguments> getInputsOutOf60Seconds() {
    final OffsetDateTime now = OffsetDateTime.now();
    return Stream.of(
        Arguments.of(parse("2018-07-17T09:59:51.312Z"), parse("2018-07-17T10:00:52.312Z")),
        Arguments.of(now.minus(62, SECONDS), now.minus(1, SECONDS)),
        Arguments.of(now.minus(1, MINUTES), now.plus(1, SECONDS))
    );
  }

  private static Stream<Arguments> getInputsInside60sRange() {
    final OffsetDateTime now = OffsetDateTime.now();
    return Stream.of(
        Arguments.of(parse("2018-07-17T09:59:51.312Z"), parse("2018-07-17T10:00:49.312Z")),
        Arguments.of(now.minus(61, SECONDS), now.minus(1, SECONDS)),
        Arguments.of(now.minus(1, MINUTES), now.minus(1, SECONDS))
    );
  }

  private static Stream<Arguments> getInputsWithTimeStampAfterOccurredAt() {
    final OffsetDateTime now = OffsetDateTime.now();
    return Stream.of(
        Arguments.of(parse("2018-07-17T10:00:49.312Z"), parse("2018-07-17T09:59:51.312Z")),
        Arguments.of(now.minus(1, SECONDS), now.minus(61, SECONDS)),
        Arguments.of(now.minus(1, SECONDS), now.minus(1, MINUTES))
    );
  }
}