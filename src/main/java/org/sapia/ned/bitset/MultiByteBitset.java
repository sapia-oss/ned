package org.sapia.ned.bitset;

import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Check;
import org.sapia.ned.util.Safe;

/**
 * Implements the {@link ImmutableBitSet} interface over multiple byte arrays.
 * 
 * @author yduchesne
 *
 */
public class MultiByteBitset extends ImmutableBitSetSupport {
  
  private byte[] bytes;
  
  public MultiByteBitset(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override
  public boolean getBitAt(int index) {
    Check.isTrue(index >= 0, "Index must be positive. Got %s", index);
    Check.isFalse(bytes.length == 0, "Bitset is empty");
    Check.isTrue(index >= 0 && index < bytes.length * Byte.SIZE, "Invalid index: %s. Index is expected to be in range [0, %s]", index, bytes.length * Byte.SIZE - 1);
    int partIndex = index / Byte.SIZE;
    int offset    = index - partIndex * Byte.SIZE;
    return Binary.getBitAt(offset, bytes[bytes.length - partIndex - 1]);
  }

  @Override
  public int size() {
    return bytes.length * Byte.SIZE;
  }
  
  @Override
  public int hashCode() {
    return Safe.hashBytes(bytes);
  }

}
