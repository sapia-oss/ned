package org.sapia.ned.bitset;

import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Check;

/**
 * Implements the {@link ImmutableBitSet} interface over the <code>byte</code> type.
 * 
 * @author yduchesne
 *
 */
public class ByteBitSet extends ImmutableBitSetSupport implements ImmutableBitSet {
  
  private byte bits;
  
  public ByteBitSet(byte bits) {
    this.bits = bits;
  }
 
  @Override
  public boolean getBitAt(int index) {
    Check.isTrue(index >= 0, "Index must be positive. Got %s", index);
    Check.isTrue(index >= 0 && index < Byte.SIZE, "Invalid index: %s. Index is expected to be in range [0, %s]", index, Byte.SIZE - 1);
    return Binary.getBitAt(index, bits);
  }
  
  @Override
  public int size() {
    return Byte.SIZE;
  }
   
  // --------------------------------------------------------------------------
  // Object overrides
  
  @Override
  public String toString() {
    return Binary.toBitString(bits);
  }
  
  @Override
  public int hashCode() {
    return Binary.unsignedByteToInt(bits);
  }

}
