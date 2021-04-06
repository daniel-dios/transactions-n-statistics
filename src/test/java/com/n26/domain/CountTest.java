package com.n26.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CountTest {
  @Test
  void shouldBeEqualsWhenValueIsTheSame() {
    Assertions
        .assertThat(new Count(1))
        .isEqualTo(new Count(1))
        .isNotEqualTo(new Count(2));
  }
}