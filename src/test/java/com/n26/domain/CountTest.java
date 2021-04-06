package com.n26.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CountTest {

  @Test
  void shouldBeEqualsWhenValueIsTheSame() {
    assertThat(new Count(1))
        .isEqualTo(new Count(1))
        .isNotEqualTo(new Count(2));
  }

  @Test
  void shouldReturnSum() {
    final Count a = new Count(1);
    final Count b = new Count(2);

    assertThat(a.add(b))
        .isEqualTo(b.add(a))
        .isEqualTo(new Count(3));
  }
}