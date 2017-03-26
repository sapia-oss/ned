package org.sapia.ned;

import org.sapia.ned.bitset.ImmutableBitSet;

/**
 * Specifies the behavior common to network addresses.
 * 
 * @author yduchesne
 *
 */
public interface NetworkAddress {
  
  /**
   * @return this 
   */
  public ImmutableBitSet toBitSet();

  /**
   * @return the number of bytes in this address.
   */
  int length();
  
  /**
   * @return the number of bits in this address.
   */
  int size();
  
  /**
   * @return the {@link ImmutableBitSet} corresponding to the prefix part of this IP address.
   */
  ImmutableBitSet getPrefix();
  
}
