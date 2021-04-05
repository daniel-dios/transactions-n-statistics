package com.n26.domain;

import java.math.BigDecimal;

public final class Amount {
  private final BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
  }
}
