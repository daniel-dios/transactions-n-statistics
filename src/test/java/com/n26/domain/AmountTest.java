package com.n26.domain;

import org.junit.jupiter.api.Test;

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
    final Amount b = new Amount(new BigDecimal("132.123"));

    final Amount expected = new Amount(new BigDecimal("123.123123"));
    assertThat(a.sum(b))
        .isEqualTo(b.sum(a))
        .isEqualTo(expected);
  }

  @Test
  void shouldReturnMax() {
    final Amount a = new Amount(new BigDecimal("0.000123"));
    final Amount b = new Amount(new BigDecimal("132.123"));

    assertThat(a.max(b))
        .isEqualTo(b.max(a))
        .isEqualTo(b);
  }

  @Test
  void shouldReturnMin() {
    final Amount a = new Amount(new BigDecimal("0.000123"));
    final Amount b = new Amount(new BigDecimal("132.123"));

    assertThat(a.min(b))
        .isEqualTo(b.min(a))
        .isEqualTo(a);
  }

  @Test
  void shouldDivide() {
    final Amount a = new Amount(new BigDecimal("13.00"));
    final Amount b = new Amount(new BigDecimal("2.0000"));

    assertThat(a.divideBy(b))
        .isEqualTo(new Amount(new BigDecimal("6.50")));
  }
}