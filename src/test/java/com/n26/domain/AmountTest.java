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
}