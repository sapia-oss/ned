package org.sapia.ned.bitset;

import java.util.BitSet;

import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Check;

/**
 * Bitset-related utility methods.
 * 
 * @author yduchesne
 *
 */
public final class BitSets {

  private BitSets() {
  }
  
  /**
   * Returns an {@link ImmutableBitSet} that consists of a view of the given bitset.
   * 
   * @param delegate the {@link ImmutableBitSet} to return a view of.
   * @param offset the offset of the start bit in the returned view.
   * @param size the number of bits that the view should make available, starting at the given offset.
   * @return a new {@link ImmutableBitSet}, consisting of a view of the given delegate one.
   */
  public static ImmutableBitSet view(ImmutableBitSet delegate, int offset, int size) {
    return new BitSetView(delegate, offset, size);
  }
  
  /**
   * Parses a binary literal, returning its corresponding bitset.
   * 
   * @param binaryLiteral a binary literal.
   * @return a new {@link ImmutableBitSet} resulting from the parsing of the given literal.
   */
  public static ImmutableBitSet valueOf(String binaryLiteral) {
    if (binaryLiteral.length() <= Byte.SIZE) {
      return new ByteBitSet(Binary.parseByte(binaryLiteral));
    } else if (binaryLiteral.length() % Byte.SIZE == 0) {
      byte[] bytes = new byte[binaryLiteral.length() / Byte.SIZE];
      for (int i = 0; i < bytes.length; i++) {
        StringBuilder byteLiteral = new StringBuilder(Byte.SIZE);
        for (int j = 0; j < Byte.SIZE; j++) {
          int index = i * Byte.SIZE + j;
          char c    = binaryLiteral.charAt(index);
          Check.isTrue(c == '0' || c == '1', "Invalid binary literal. Expecting only 0s and 1s, got %s at index %s", c, index);
          byteLiteral.append(c);
        }
        bytes[i] = Binary.parseByte(byteLiteral.toString());
      }
      return new MultiByteBitset(bytes);
    } else {
      return new BooleanBitSet(binaryLiteral);
    }
  }
  
  /**
   * Returns an {@link ImmutableBitSet} for the given {@link BitSet}. Copies the bit of that bitset into the newly
   * returned one (which won't be altered by modifications to the original {@link BitSet}).
   * 
   * @param bitset an {@link ImmutableBitSet} corresponding to the given {@link BitSet}.
   * @return a new {@link ImmutableBitSet}.
   */
  public static ImmutableBitSet valueOf(BitSet bitset) {
    boolean[] bits = new boolean[bitset.size()];
    for (int i = 0; i < bitset.size(); i++) {
       bits[bits.length - i - 1]  = bitset.get(i);
    }
    
    return new BooleanBitSet(bits);
  }
}
