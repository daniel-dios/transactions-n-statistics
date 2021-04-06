package com.n26.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountTest {

  @Test
  void shouldBeEqualsWhenValueIsTheSame() {
    assertThat(new Count(1))
        .isEqualTo(new Count(1))
        .isNotEqualTo(new Count(2));
  }
}