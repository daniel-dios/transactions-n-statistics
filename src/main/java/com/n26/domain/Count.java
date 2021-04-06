package com.n26.domain;

public final class Count {
  public static final Count ZERO = new Count(0);
  private final long value;

  public Count(long value) {
    this.value = value;
  }

  public Count add(Count count) {
    return new Count(value + count.value);
  }

  public long getValue() {
    return value;
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
    return (int) (value ^ (value >>> 32));
  }
}
