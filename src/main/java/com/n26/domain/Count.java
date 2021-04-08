package com.n26.domain;

public final class Count {
  public static final Count ZERO = new Count(0);
  public static final Count ONE = new Count(1);

  private final long value;

  public Count(long value) {
    this.value = value;
  }

  public Count add(Count count) {
    return new Count(value + count.value);
  }

  public Count increment() {
    return new Count(this.value + 1);
  }

  public long getValue() {
    return value;
  }

  public int compare(Count count) {
    if (this.value == count.value) {
      return 0;
    }
    return this.value > count.value ? 1 : -1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    return this.compare((Count) o) == 0;
  }

  @Override
  public int hashCode() {
    return (int) (value ^ (value >>> 32));
  }
}
