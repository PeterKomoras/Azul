package sk.uniba.fmph.dcs;

public final class Points {
  public static Points sum(final Iterable<Points> pts) {
    int sum = 0;
    for (final Points points : pts) {
      sum += points.getValue();
    }
    return new Points(sum);
  }

  private int value;

  public Points(final int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + value;
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Points other = (Points) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Points [value=" + value + "]";
  }
}
