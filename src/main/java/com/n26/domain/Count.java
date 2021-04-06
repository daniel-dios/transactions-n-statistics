package com.n26.domain;

public class Count {
  private final int value;

  public Count(int value) {
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

    Count count = (Count) o;

    return value == count.value;
  }

  @Override
  public int hashCode() {
    return value;
  }
}
