package org.sapia.ned.bitset;

import org.sapia.ned.util.Check;

/**
 * {@link ImmutableBitSet} implementation backed by an array of <code>boolean</code> values.
 * 
 * @author yduchesne
 *
 */
public class BooleanBitSet extends ImmutableBitSetSupport {
  
  private boolean[] bits;
  
  /**
   * @param binaryLiteral a binary literal, consisting of a sequence of 1s and 0s.
   */
  public BooleanBitSet(String binaryLiteral) {
    this(doParse(binaryLiteral));
  }
  
  /**
   * This constructor accepts an array of booleans, with is expected to have the MSB
   * as the first element, and the LSB as the last.
   * 
   * @param bits an array of booleans that this instance should encapsulate.
   */
  public BooleanBitSet(boolean[] bits) {
    this.bits = bits;
  }
  
  /**
   * @param binaryLiteral a binary literal, consisting of a sequence of 1s and 0s.
   * @return a new instance of this class.
   */
  public static BooleanBitSet valueOf(String binaryLiteral) {
    return new BooleanBitSet(binaryLiteral);
  }
  
  @Override
  public boolean getBitAt(int index) {
    Check.isTrue(index >= 0, "Index must be positive. Got %s", index);
    Check.isFalse(bits.length == 0, "Bitset is empty");
    Check.isTrue(index >= 0 && index < bits.length, "Invalid index: %s. Index is expected to be in range [0, %s]", index, bits.length - 1);
    return bits[bits.length - index - 1];
  }
  
  @Override
  public int size() {
    return bits.length;
  }
  
  // ----------------------------------------------------------------
  
  private static boolean[] doParse(String literal) {
    Check.isNotNullOrEmpty(literal, "Invalid binary literal: input null or empty"); 
    boolean[] bits = new boolean[literal.length()];
    
    for (int i = 0; i < bits.length; i++) {
      char boolValue = literal.charAt(i);
      Check.isTrue(
          Character.isDigit(boolValue) && (boolValue == '1' || boolValue == '0'), 
          "Invalid binary literal: expected sequence of 0s and 1s, got: %s (invalid character at index %s: %s)", 
          literal, i, boolValue
      );
      // storing from right to left (from LSB to MSB).
      bits[i] = boolValue == '1' ? true : false;
    }
    return bits;
  } 

}
