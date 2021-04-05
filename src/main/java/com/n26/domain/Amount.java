package com.n26.domain;

import java.math.BigDecimal;

public class Amount {
  private final BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
  }
}
