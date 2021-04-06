package com.n26.domain;

import java.math.BigDecimal;

public final class Amount {
  public static final Amount ZERO = new Amount(BigDecimal.ZERO);

  private final BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
  }

  public Amount sum(Amount toSum) {
    return new Amount(value.add(toSum.value));
  }

  public Amount max(Amount toCompare) {
    return new Amount(value.max(toCompare.value));
  }

  public Amount min(Amount toCompare) {
    return new Amount(value.min(toCompare.value));
  }

  public Amount divideBy(Amount amount) {
    final BigDecimal divide = value.divide(amount.value, BigDecimal.ROUND_HALF_UP);
    return new Amount(divide);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Amount amount = (Amount) o;

    return value.equals(amount.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
