package org.sapia.ned.util;

/**
 * Holds methods that safeguard against null references.
 * 
 * @author yduchesne
 *
 */
public final class Safe {
 
  public static final int PRIME = 31;
  
  private Safe() {
    
  }
  
  /**
   * Computes a hash code for one or more integers.
   * 
   * @param values one or more integers.
   * @return a hash code.
   */
  public static int hashInts(int...values) {
    if (values == null || values.length == 0) {
      return 0;
    }
    int h = 0;
    for (int i = 0; i < values.length; i++) {
      h = h + values[i] * PRIME;
    }
    return h;
  }

  /**
   * Computes a hash code for one or more byte integers.
   * 
   * @param values one or more byte integers.
   * @return a hash code.
   */
  public static int hashBytes(byte...values) {
    if (values == null || values.length == 0) {
      return 0;
    }
    int h = 0;
    for (int i = 0; i < values.length; i++) {
      h = h + Binary.unsignedByteToInt(values[i]) * PRIME;
    }
    return h;
  }
  
  /**
   * @param a an object.
   * @param b another object.
   * @return <code>true</code> if both objects are deemed equal 
   * (that is, if both are null or a.equals(b) returns <code>true</code>).
   */
  public static boolean equals(Object a, Object b) {
    if (a == null && b == null) {
      return true;
    } else if (a == null || b == null) {
      return false;
    }
    return a.equals(b);
  }
  
  /**
   * @param obj one or more objects to get a hash code from.
   * @return the hash code resulting from this operation.
   */
  public static int hashCode(Object...obj) {
    if (obj == null) {
      return 0;
    }
    int hash = 0;
    for(int i = 0; i < obj.length; i++) {
      if (obj[i] != null) {
        hash = hash + obj[i].hashCode() * PRIME;
      }
    }
    return hash;
  }
}
