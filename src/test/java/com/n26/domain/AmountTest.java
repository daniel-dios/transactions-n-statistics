package com.n26.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AmountTest {

  @Test
  void shouldBeEqualsWhenValueIsTheSame() {
    assertThat(new Amount(new BigDecimal("1")))
        .isEqualTo(new Amount(new BigDecimal("1")))
        .isNotEqualTo(new Amount(new BigDecimal("2")));
  }

  @Test
  void shouldSum() {
    final Amount a = new Amount(new BigDecimal("0.000123"));
    final Amount b = new Amount(new BigDecimal("123.123"));

    final Amount expected = new Amount(new BigDecimal("123.123123"));
    assertThat(a.sum(b))
        .isEqualTo(b.sum(a))
        .isEqualTo(expected);
  }

  @Test
  void shouldReturnMax() {
    final Amount a = new Amount(new BigDecimal("0.000123"));
    final Amount b = new Amount(new BigDecimal("123.123"));

    assertThat(a.max(b))
        .isEqualTo(b.max(a))
        .isEqualTo(b);
  }

  @Test
  void shouldReturnMin() {
    final Amount a = new Amount(new BigDecimal("0.000123"));
    final Amount b = new Amount(new BigDecimal("123.123"));

    assertThat(a.min(b))
        .isEqualTo(b.min(a))
        .isEqualTo(a);
  }

  @ParameterizedTest
  @CsvSource({"10.345,10.35", "10.8,10.80"})
  void shouldReturnRoundValue(String val, String expected) {
    final Amount a = new Amount(new BigDecimal(val));

    final BigDecimal actual = a.getRoundValue();

    assertThat(actual)
        .isEqualTo(new BigDecimal(expected));
  }

  @Test
  void shouldDivide() {
    final Amount a = new Amount(new BigDecimal("5"));

    final BigDecimal actual = a.divide(3);

    assertThat(actual)
        .isEqualTo(new BigDecimal("1.67"));
  }

  @Test
  void shouldReturnZeroIfCountIsZero() {
    final Amount a = new Amount(new BigDecimal("5"));

    final BigDecimal actual = a.divide(0);

    assertThat(actual)
        .isEqualTo(new BigDecimal("0.00"));
  }
}