package com.n26.domain;

import com.n26.domain.exception.FutureTransactionTimestampException;
import com.n26.domain.exception.OldTransactionTimestampException;
import org.junit.jupiter.api.Test;
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

class TransactionTimestampTest {

  @ParameterizedTest
  @MethodSource("getInputsOutOf60Seconds")
  void shouldFailWhenOffsetIsOlderThan60s(OffsetDateTime timestamp, OffsetDateTime occurredAt) {

    assertThatThrownBy(() -> new TransactionTimestamp(timestamp, occurredAt))
        .isInstanceOf(OldTransactionTimestampException.class);
  }

  @ParameterizedTest
  @MethodSource("getInputsInside60sRange")
  void shouldCreateTransactionTimeStampFromOkTime(OffsetDateTime timestamp, OffsetDateTime occurredAt) {
    final TransactionTimestamp transactionTimeStamp = new TransactionTimestamp(timestamp, occurredAt);

    assertThat(transactionTimeStamp)
        .isEqualToComparingFieldByField(new TransactionTimestamp(timestamp, occurredAt))
        .isNotEqualTo(new TransactionTimestamp(OffsetDateTime.now(), OffsetDateTime.now()));
  }

  @ParameterizedTest
  @MethodSource("getInputsWithTimeStampAfterOccurredAt")
  void shouldFailWhenOffsetIsAfterOccurredAt(OffsetDateTime timestamp, OffsetDateTime occurredAt) {

    assertThatThrownBy(() -> new TransactionTimestamp(timestamp, occurredAt))
        .isInstanceOf(FutureTransactionTimestampException.class);
  }

  @Test
  void shouldReturnValueTruncatedToSeconds() {
    final TransactionTimestamp transactionTimestamp =
        new TransactionTimestamp(parse("2018-07-17T09:59:51.312Z"), parse("2018-07-17T10:00:49.312Z"));

    assertThat(transactionTimestamp.getValueSecondsTruncated())
        .isEqualTo(parse("2018-07-17T09:59:51.000Z"))
        .isNotEqualTo(parse("2018-07-17T09:59:51.312Z"));
  }

  @Test
  void shouldReturnTrueWhenDateIsInRange() {
    assertThat(TransactionTimestamp.isInRange(parse("2018-07-17T09:59:51.312Z"), parse("2018-07-17T09:59:01.312Z")))
        .isTrue();

    assertThat(TransactionTimestamp.isInRange(parse("2018-07-17T09:59:51.312Z"), parse("2018-07-17T09:58:50.312Z")))
        .isFalse();
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