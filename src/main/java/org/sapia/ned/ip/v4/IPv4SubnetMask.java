package org.sapia.ned.ip.v4;

import org.sapia.ned.SubnetMask;
import org.sapia.ned.bitset.ImmutableBitSet;
import org.sapia.ned.util.Binary;
import org.sapia.ned.util.ExternalizationOnly;

/**
 * Corresponds to an IPv4 subnet mask.
 * 
 * @author yduchesne
 *
 */
public class IPv4SubnetMask extends IPv4Address implements SubnetMask {
   
  @ExternalizationOnly
  public IPv4SubnetMask() {
  }
  
  /**
   * @param from the {@link IPv4Address} to create an instance of this class from.
   */
  public IPv4SubnetMask(IPv4Address from) {
    this(Binary.unsignedBytesToInts(from.getBytes()), from.getPrefixLength());
  }
  
  /**
   * @param parts the bytes composing the new mask.
   * @param prefixLen the new mask's prefix length.
   */
  public IPv4SubnetMask(int[] parts, int prefixLen) {
    super(parts, prefixLen);
  }
  
  /**
   * @param parts the bytes composing the new mask.
   * @param prefixLen the new mask's prefix length.
   */
  public IPv4SubnetMask(byte[] parts, int prefixLen) {
    super(parts, prefixLen);
  }
  
  /**
   * @param bitset an {@link ImmutableBitSet} to create new instance of this class from.
   * @param prefixLen the prefix length of this address.
   */
  public IPv4SubnetMask(ImmutableBitSet bitset, int prefixLen) {
    super(bitset, prefixLen);
  }
  
  /**
   * Returns the first address of the IP address block to which the given address belongs. The given 
   * address is expected to correspond to the network to which this subnet mask itself corresponds.
   * 
   * @param arbitraryAddress an {@link IPv4Address} assumed to correspond to this subnet mask.
   * @return the {@link IPv4Address} consisting of the first address of the block to which the passed in IP address belongs.
   */
  public IPv4Address getFirstBlockAddress(IPv4Address arbitraryAddress) {    
    return new IPv4Address(toBitSet().and(arbitraryAddress.toBitSet()), getPrefixLength());
  }

  /**
   * Returns the last address of the IP address block to which the given address belongs. The given 
   * address is expected to correspond to the network to which this subnet mask itself corresponds.
   * 
   * @param arbitraryAddress an {@link IPv4Address} assumed to correspond to this subnet mask.
   * @return the {@link IPv4Address} consisting of the last address of the block to which the passed in IP address belongs.
   */
  public IPv4Address getLastBlockAddress(IPv4Address arbitraryAddress) {
    return new IPv4Address(arbitraryAddress.toBitSet().or(toBitSet().not()), getPrefixLength());
  }
  
  /**
   * @return this instance's IP address representation (i.e: without a prefix).
   */
  public IPv4Address toAddress() {
    return new IPv4Address(getBytes(), getPrefixLength());
  }


  // --------------------------------------------------------------------------
  // Object overrides
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(LEN * 3 + 3);
    byte[] tmp = doGetBytes();
    for (int i = 0; i < tmp.length; i++) {
      if (i > 0) {
        s.append('.');
      }
      s.append(Integer.toString(Binary.unsignedByteToInt(tmp[i])));
    }
    return s.toString();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IPv4SubnetMask) {
      return super.equals(obj);
    }
    return false;
  }
  

}
