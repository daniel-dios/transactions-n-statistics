package com.n26.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  @Test
  void shouldCompare() {
    final Count small = new Count(1);
    final Count big = new Count(1000);

    assertThat(small).usingComparator(Count::compare).isEqualTo(small);
    assertThat(small.compare(big)).isEqualTo(-1);
    assertThat(big.compare(small)).isEqualTo(1);
  }

  @Test
  void shouldIncrement() {
    final Count oneValue = new Count(1);

    final Count actual = oneValue.increment();

    assertThat(actual).usingComparator(Count::compare).isEqualTo(new Count(2));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenNegativeNumber() {

    assertThatThrownBy(() -> new Count(-1))
        .isInstanceOf(IllegalArgumentException.class);
  }
}