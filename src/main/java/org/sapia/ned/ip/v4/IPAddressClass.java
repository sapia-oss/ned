package org.sapia.ned.ip.v4;

import org.sapia.ned.bitset.BooleanBitSet;
import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Check;
import org.sapia.ned.util.IntRange;

/**
 * Holds constants corresponding to the different IP address classes.
 * 
 * @author yduchesne
 *
 */
public enum IPAddressClass {

  CLASS_A(1, 1, new IntRange(0, 127)),
  CLASS_B(2, 2, new IntRange(128, 191)),
  CLASS_C(3, 3, new IntRange(192, 223)),
  CLASS_D(4, 0, new IntRange(224, 239)),
  CLASS_E(4, 0, new IntRange(240, 255)),
  CLASSLESS(0, 0, new IntRange(0, 0));
  
  private int      classIndicatorBitCount;
  private int      byteCount;
  private IntRange range;
  
  private IPAddressClass(int classIndicatorBitCount, int byteCount, IntRange range) {
    this.classIndicatorBitCount = classIndicatorBitCount;
    this.byteCount              = byteCount;
    this.range                  = range;
  }
 
  /**
   * @return the range of this {@link IPAddressClass}, corresponding to the min and max bytes determining the range
   * of the class.
   */
  public IntRange range() {
    return range;
  }
  
  /**
   * @param addr an {@link IPv4Address}.
   * @return <code>true</code> if this class corresponds to the class of the given {@link IPv4Address} instance.
   */
  public boolean isClassOf(IPv4Address addr) {
    return classOf(addr) == this;
  }
  
  /**
   * @param addr an {@link IPv4Address} instance.
   * @return the {@link IPAddressClass} corresponding to the given {@link IPv4Address}.
   */
  public static IPAddressClass classOf(IPv4Address addr) {
    if (addr.getPrefix().size() > 0) {
      return IPAddressClass.CLASSLESS;
    }
    IPAddressClass[] classes = IPAddressClass.values();
    for (int i = 0; i < classes.length; i ++) {
      if (classes[i].range.isWithin(addr.getIntAt(IPv4Address.LEN - 1))) {
        return classes[i];
      }
    }
    // the following should never happen
    throw new IllegalStateException("Could not determine class of address: " + addr);
  }
  
  static IPAddressClass classOf(byte firstByte) {
    IPAddressClass[] classes = IPAddressClass.values();
    int intValue = Binary.unsignedByteToInt(firstByte);
    for (int i = 0; i < classes.length; i ++) {
      if (classes[i].range.isWithin(intValue)) {
        return classes[i];
      }
    }
    // the following should never happen
    throw new IllegalStateException("Could not determine class of address for first byte: " + intValue);
  }
  
  /**
   * @return the potential number of address blocks in this IP address class.
   */
  public int addressBlockCount() {
    if (byteCount == 0) {
      return 1;
    }
    return (int) Math.pow(2, IPv4Address.SIZE - byteCount - classIndicatorBitCount);
  }
  
  /**
   * @return the potential number of addresses in this IP address class.
   */
  public int addressCount() {
    if (this == IPAddressClass.CLASSLESS) {
      Check.state(this != CLASSLESS, "Cannot determine address count for CLASSLESS type (invoke IPv4Address.getAddressCount())");
    } else if (byteCount == 0) {
      return (int) Math.pow(2, IPv4Address.SIZE - classIndicatorBitCount);
    } 
    return (int) Math.pow(2, IPv4Address.SIZE - byteCount * Byte.SIZE);
  }
  
  /**
   * @return an {@link IPv4SubnetMasks} corresponding to this class of IP address.
   */
  public IPv4SubnetMask getSubnetMask() {
    Check.state(this != CLASSLESS, "Cannot determine subnet mask for CLASSLESS type (invoke IPv4Address.getSubnetMask())");
    boolean[] bits = new boolean[IPv4Address.SIZE];
    int len = Byte.SIZE * byteCount;
    for (int i = 0; i < len; i++) {
      bits[i] = true;
    }
    return new IPv4SubnetMask(new BooleanBitSet(bits), 0);
  }

}
