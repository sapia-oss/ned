package org.sapia.ned.bitset;

import java.util.BitSet;

/**
 * Specifies the behavior of a set of bits: contrary to the JDK's {@link BitSet} class, this interface
 * implies an immutable behavior.
 * <p>
 * Implementations of this interface are expected to implement {@link Object#hashCode()} and {@link Object#equals(Object)},
 * as well as {@link Object#toString()}. The latter is expected to return a string that order the bits from 
 * MSB to LSB (MSB at index 0, LSB at the last index).
 * 
 * @author yduchesne
 *
 */
public interface ImmutableBitSet {

  /**
   * @param index the index of the bit to read.
   * @return the bit value at the given index.
   */
  boolean getBitAt(int index);
  
  /**
   * @return the number of bits that this instance holds.
   */
  int size();
  
  /**
   * @return a new mutable {@link BitSet}, holding a copy of this instance's bits.
   */
  BitSet toMutable();
  
  /**
   * @param other another {@link ImmutableBitSet}.
   * @return <code>true</code> if this instance' bits (from MSB to LSB) are all present
   * in the given bitset, in the same order.
   */
  boolean isPrefixOf(ImmutableBitSet other);

  /**
   * @param other another {@link ImmutableBitSet}.
   * @return a new {@link ImmutableBitSet}, resulting from a logical AND performed on this instance is the given bitset.
   */
  ImmutableBitSet and(ImmutableBitSet other);
  
  /**
   * @param other another {@link ImmutableBitSet}.
   * @return a new {@link ImmutableBitSet}, resulting from a logical OR performed on this instance is the given bitset.
   */
  ImmutableBitSet or(ImmutableBitSet other);
  
  /**
   * @return a new {@link ImmutableBitSet}, resulting from a logical NOT performed on this instance.
   */
  ImmutableBitSet not();
}
