package com.n26.domain;

import java.math.BigDecimal;

public final class Amount {
  public static final Amount ZERO = new Amount(BigDecimal.ZERO);
  private static final int ROUND_POLICY = BigDecimal.ROUND_HALF_UP;

  private final BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal getRoundValue() {
    return value.setScale(2, ROUND_POLICY);
  }

  public Amount sum(Amount toSum) {
    return new Amount(value.add(toSum.value));
  }

  public Amount divide(long divisor) {
    if (divisor == 0) {
      return new Amount(new BigDecimal("0.00"));
    } else {
      return new Amount(value.divide(new BigDecimal(divisor), 2, ROUND_POLICY));
    }
  }

  public Amount max(Amount toCompare) {
    return new Amount(value.max(toCompare.value));
  }

  public Amount min(Amount toCompare) {
    return new Amount(value.min(toCompare.value));
  }

  public int compare(Amount b) {
    return this.value.compareTo(b.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    return this.compare((Amount) o) == 0;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
