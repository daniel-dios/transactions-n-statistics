package com.n26.domain;

import java.math.BigDecimal;

public final class Amount {
  public static final Amount ZERO = new Amount(BigDecimal.ZERO);

  private final BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
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
