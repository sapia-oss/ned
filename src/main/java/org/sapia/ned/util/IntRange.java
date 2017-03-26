package org.sapia.ned.util;

/**
 * Models a range of integers.
 * 
 * @author yduchesne
 */
public class IntRange {

  private int min, max;
  
  public IntRange(int min, int max) {
    Check.isTrue(min <= max, "Min value must be <= than max value. Got %s vs %s", min, max);
    this.min = min;
    this.max = max;
  }
  
  /**
   * @return this instance's min value.
   */
  public int getMin() {
    return min;
  }
  
  /**
   * @return this instance's max value.
   */
  public int getMax() {
    return max;
  }
  
  /**
   * @param value a value to test.
   * @return <code>true</code> if the given value is contained within this range.
   */
  public boolean isWithin(int value) {
    return min <= value && value <= max;
  }
  
  /**
   * @param value a value to test.
   * @return <code>true</code> if the given value is outside of this range. 
   */
  public boolean isOutside(int value) {
    return !isWithin(value);
  }
  
  // --------------------------------------------------------------------------
  // Object overrides

  @Override
  public String toString() {
    return "[" + min + "," + max + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IntRange) {
      IntRange other = (IntRange) obj;
      return min == other.min && max == other.max;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Safe.hashInts(min, max);
  }
}
