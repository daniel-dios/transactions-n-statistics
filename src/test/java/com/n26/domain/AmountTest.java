package com.n26.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static com.n26.utils.DomainFactoryUtils.createAmount;
import static org.assertj.core.api.Assertions.assertThat;

class AmountTest {

  @Test
  void shouldBeEqualsWhenValueIsTheSame() {
    assertThat(createAmount("1"))
        .isEqualTo(createAmount("1"))
        .isNotEqualTo(createAmount("2"));
  }

  @Test
  void shouldSum() {
    final Amount a = createAmount("0.000123");
    final Amount b = createAmount("123.123");

    final Amount expected = createAmount("123.123123");
    assertThat(a.sum(b))
        .isEqualTo(b.sum(a))
        .isEqualTo(expected);
  }

  @Test
  void shouldReturnMax() {
    final Amount a = createAmount("0.000123");
    final Amount b = createAmount("123.123");

    assertThat(a.max(b))
        .isEqualTo(b.max(a))
        .isEqualTo(b);
  }

  @Test
  void shouldReturnMin() {
    final Amount a = createAmount("0.000123");
    final Amount b = createAmount("123.123");

    assertThat(a.min(b))
        .isEqualTo(b.min(a))
        .isEqualTo(a);
  }

  @ParameterizedTest
  @CsvSource({"10.345,10.35", "10.8,10.80"})
  void shouldReturnRoundValue(String val, String expected) {
    final Amount a = createAmount(val);

    final BigDecimal actual = a.getRoundValue();

    assertThat(actual)
        .isEqualTo(new BigDecimal(expected));
  }

  @Test
  void shouldDivide() {
    final Amount a = createAmount("5");

    final Amount actual = a.divide(3);

    assertThat(actual).isEqualTo(createAmount("1.67"));
  }

  @Test
  void shouldReturnZeroIfCountIsZero() {
    final Amount a = createAmount("5");

    final Amount actual = a.divide(0);

    assertThat(actual).isEqualTo(createAmount("0.00"));
  }

  @Test
  void shouldCompare() {
    assertThat(createAmount("1.00"))
        .usingComparator(Amount::compare)
        .isEqualTo(createAmount("1"));

    final Amount small = createAmount("1");
    final Amount big = createAmount("1000.000");

    assertThat(small.compare(big)).isEqualTo(-1);
    assertThat(big.compare(small)).isEqualTo(1);
  }
}