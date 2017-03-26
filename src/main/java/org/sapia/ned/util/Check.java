package org.sapia.ned.util;

/**
 * Offers various assertion methods.
 * 
 * @author yduchesne
 *
 */
public final class Check {

  private Check() {
    
  }
  
  public static void isTrue(boolean condition, String msg, Object...args) {
    if (!condition) {
      throw new IllegalArgumentException(String.format(msg, args));
    }
  }
  
  public static void isFalse(boolean condition, String msg, Object...args) {
     isTrue(!condition, msg, args);
  }
 
  public static void state(boolean condition, String msg, Object...args) {
    if (!condition) {
      throw new IllegalStateException(String.format(msg, args));
    }
  }
  
  public static <T> T isNotNull(T value, String msg, Object...args) throws IllegalArgumentException {
    isTrue(value != null, msg, args);
    return value;
  }
  
  public static String isNotNullOrEmpty(String value, String msg, Object...args) throws IllegalArgumentException {
    isTrue(value != null, msg, args);
    for (int i = 0; i < value.length(); i++) {
      if (!Character.isWhitespace(value.charAt(i))) {
        return value;
      }
    }
    throw new IllegalArgumentException(String.format(msg, args));
  }
  
  public static String isInteger(String value, String msg, Object...args) throws IllegalArgumentException {
    isNotNullOrEmpty(value, msg, args);
    
    for (int i = 0; i < value.length(); i++) {
      if (!Character.isDigit(value.charAt(i))) {
        throw new IllegalArgumentException(String.format(msg, args));
      }
    }
    
    return value;
  }
  
}
