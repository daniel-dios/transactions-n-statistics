package com.n26.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AmountTest {

  @Test
  void shouldBeEqualsWhenValueIsTheSame() {
    Assertions
        .assertThat(new Amount(new BigDecimal("1")))
        .isEqualTo(new Amount(new BigDecimal("1")))
        .isNotEqualTo(new Amount(new BigDecimal("2")));
  }
}