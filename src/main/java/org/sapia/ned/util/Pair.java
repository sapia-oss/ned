package org.sapia.ned.util;

/**
 * Models a pair of arbitrary objects.
 * 
 * @author yduchesne
 */
public class Pair<A, B> {
 
  private A a;
  private B b;
  
  public Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }
  
  public A getA() {
    return a;
  }
  
  public B getB() {
    return b;
  }
  
  // --------------------------------------------------------------------------
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Pair) {
      Pair<A, B> other = Pair.class.cast(obj);
      return Safe.equals(a, other.a) && Safe.equals(b,  other.b);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return Safe.hashCode(a, b);
  }
  
  @Override
  public String toString() {
    return "[" + a + ", " + b + "]";
  }

}
