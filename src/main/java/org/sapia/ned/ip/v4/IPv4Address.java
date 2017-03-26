package org.sapia.ned.ip.v4;

import static org.sapia.ned.util.Binary.unsignedByteToInt;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.BitSet;

import org.sapia.ned.NetworkAddress;
import org.sapia.ned.SubnetMask;
import org.sapia.ned.bitset.BooleanBitSet;
import org.sapia.ned.bitset.ByteBitSet;
import org.sapia.ned.bitset.ImmutableBitSet;
import org.sapia.ned.bitset.MultiByteBitset;
import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Check;
import org.sapia.ned.util.ExternalizationOnly;
import org.sapia.ned.util.Pair;
import org.sapia.ned.util.Safe;

/**
 * Models an IPv4 address.
 * 
 * @author yduchesne
 *
 */
public class IPv4Address implements NetworkAddress, Externalizable {

  /**
   * The number of bytes in an IPv4 address.
   */
  public static final int LEN  = 4;
  
  /**
   * The total number of bits in an IPv4 address.
   */
  public static final int SIZE = LEN * Byte.SIZE; 
  
  private static final int MIN = 0;
  private static final int MAX = 255;
  
  private byte[]         bytes      = new byte[LEN];
  private int            prefixLen;
  private IPAddressClass clazz;
  
  // lazily initialized
  private transient ImmutableBitSet prefix;

  // lazily initialized
  private transient MultiByteBitset bitset;
  
  // lazily initialized
  private transient IPv4SubnetMask subnetMask;
    
  @ExternalizationOnly
  public IPv4Address() {
  }
  
  protected IPv4Address(Pair<int[], Integer> data) {
    int[] parts = data.getA();
    Check.isTrue(parts.length == LEN, "Invalid input for IPv4 address: expected 4 parts, got %s", parts.length);
    Check.isTrue(data.getB() >= 0, "Invalid IP address prefix length: %s. Must be positive", data.getB());
    Check.isTrue(data.getB() <= SIZE, "Invalid IP address prefix length: %s. Must be <= %s", data.getB(), SIZE);
    for (int i = 0; i < parts.length; i++) {
      Check.isTrue(parts[i] >= MIN, "Invalid value for IPv4 address part at index %s. Expected >= 0, got: %s", i, parts[i]);
      Check.isTrue(parts[i] <= MAX, "Invalid value for IPv4 address part at index %s. Expected <= 255, got: %s", i, parts[i]);
      bytes[i] = (byte) parts[i];
    }
    prefixLen = data.getB();
    guessClass();
  }
  
  /**
   * @param parts the IP address bytes (consisting of 8-bit integers) that this address should hold.
   * @param prefixLen the length of the prefix to use in case of classless addressing (0 if it doesn't apply).
   */
  public IPv4Address(int[] parts, int prefixLen) {
    this(new Pair<>(parts, 0));
  }
  
  /**
   * @param parts the IP address bytes (consisting of 8-bit integers) that this address should hold.
   * @param prefixLen the length of the prefix to use in case of classless addressing (0 if it doesn't apply).
   */
  public IPv4Address(byte[] parts, int prefixLen) {
    Check.isTrue(parts.length == LEN, "Invalid input for IPv4 address: expected 4 parts, got %s", parts.length);
    Check.isTrue(prefixLen >= 0, "Invalid IP address prefix length: %s. Must be positive", prefixLen);
    Check.isTrue(prefixLen <= SIZE, "Invalid IP address prefix length: %s. Must be <= %s", prefixLen, SIZE);

    for (int i = 0; i < parts.length; i++) {
      Check.isTrue(
          unsignedByteToInt(parts[i]) >= MIN, 
          "Invalid value for IPv4 address part at index %s. Expected >= 0, got: %s", 
          i, unsignedByteToInt(parts[i])
      );
      Check.isTrue(
          unsignedByteToInt(parts[i]) <= MAX, 
          "Invalid value for IPv4 address part at index %s. Expected <= 255, got: %s", 
          i, unsignedByteToInt(parts[i])
      );
      bytes[i] = parts[i];
    }
    this.prefixLen = prefixLen;
    guessClass();
  }
  
  /**
   * @param part0 the first IP address.
   * @param part1 the second IP address.
   * @param part2 the third IP address.
   * @param part3 the fourth IP address.
   */
  public IPv4Address(int part0, int part1, int part2, int part3) {
    this(new int[] {part0, part1, part2, part3}, 0);
  }
  
  /**
   * @param literal an IPv4 address literal (example: 192.168.0.100).
   */
  public IPv4Address(String literal) {
    this(doParse(literal));
  }
  
  /**
   * @param bitset an {@link ImmutableBitSet} to create new instance of this class from.
   * @param prefixLen the prefix length of this address.
   */
  public IPv4Address(ImmutableBitSet bitset, int prefixLen) {
    Check.isTrue(bitset.size() == SIZE, "Bitset expected to have size of 32 bits. Got %s bits", bitset.size());
    int bitIndex = 0;
    for (int i = 0; i < LEN; i++) {
      byte part = 0;
      for (int j = 0; j < Byte.SIZE; j++) {
        boolean bit = bitset.getBitAt(bitIndex++);
        int unsigned = Binary.setBitAt(j, part, bit);
        part = (byte) unsigned;
      }
      bytes[LEN - i - 1] = part;
    }
    this.prefixLen = prefixLen;
    guessClass();
  }
  
  /**
   * @return the class of this IP address.
   */
  public IPAddressClass getAddressClass() {
    return clazz;
  }
  
  /**
   * @return the prefix length of this address.
   */
  public int getPrefixLength() {
    return prefixLen;
  }
  
  /**
   * @param index the index of the byte to return.
   * @return the IP address byte corresponding to the given index. 
   */
  public byte getByteAt(int index) {
    Check.isTrue(index >= 0 && index < LEN , "Invalid index: %s (expected value within [0, %s]", index, LEN - 1);
    return bytes[LEN - index - 1];
  }
  
  /**
   * @param index the index of the byte to return.
   * @return the IP address byte corresponding to the given index. 
   */
  public int getIntAt(int index) {
    return Binary.unsignedByteToInt(getByteAt(index));
  }
  
  /**
   * @param index the index of the byte to return.
   * @return the {@link BitSet} corresponding to the byte at the given index.
   */
  public ImmutableBitSet getBitSetAt(int index) {
    Check.isTrue(index >= 0 && index < LEN , "Invalid index: %s (expected value within [0, %s]", index, LEN - 1);
    byte part = getByteAt(index);
    return new ByteBitSet(part);
  }
  
  /**
   * @param index the index of the bit to return.
   * @return the bit corresponding to the given index.
   */
  public boolean getBitAt(int index) {
    Check.isTrue(index >= 0 && index < SIZE, "Invalid index: %s. Index is expected to be in range [0, %s]", index, SIZE - 1);
    int partIndex = index / Byte.SIZE;
    int offset    = index - partIndex * Byte.SIZE;
    return Binary.getBitAt(offset, bytes[bytes.length - partIndex - 1]);
  }

  /**
   * @return a copy of this instance's bytes.
   */
  public byte[] getBytes() {
    byte[] copy = new byte[LEN];
    System.arraycopy(bytes, 0, copy, 0, LEN);
    return copy;
  }
  
  /**
   * A copy of this instance, but with the given prefix length.
   * 
   * @param prefixLen the prefix length that the copy should have.
   * @return a copy of this instance, with the given prefix length.
   */
  public IPv4Address withPrefix(int prefixLen) {
    return new IPv4Address(bytes, prefixLen);
  }
  
  /**
   * @return the number of addresses in the network to which this address belongs.
   */
  public int getAddressCount() {
    if (clazz == IPAddressClass.CLASSLESS) {
      return (int) Math.pow(2, SIZE - prefixLen);
    } else {
      return clazz.addressBlockCount();
    }
  }
  
  /**
   * @return the {@link SubnetMask} corresponding to this address.
   */
  public IPv4SubnetMask getSubnetMask() {
    if (subnetMask == null) {
      if (clazz == IPAddressClass.CLASSLESS) {
        boolean[] bits = new boolean[SIZE];
        for (int i = 0; i < prefixLen; i++) {
          bits[i] = true;
        }
        subnetMask = new IPv4SubnetMask(new BooleanBitSet(bits), prefixLen);
      } else {
        subnetMask = clazz.getSubnetMask();
      }
    } 
    return subnetMask;
  }
  
  /**
   * @param toAdd the value to add to this instance.
   * @return a new {@link IPv4Address} resulting from this operation.
   */
  public IPv4Address plus(int toAdd) {
    Check.isTrue(toAdd >= 0, "Value to add to IPv4 address %s must be >= 0. Got %s", this, toAdd);

    long currentAddressValue = Binary.unnsignedIntToLong(Binary.getInt(bytes)); 
    long newAddressValue     = currentAddressValue + toAdd;
    
    //Check.isTrue(newAddressValue < currentAddressValue + getAddressCount(), "Value too large: %s. Resulting address would be beyond block capacity (%s)", toAdd, getAddressCount());
    
    return new IPv4Address(Binary.getBytes((int) newAddressValue), prefixLen);
  }
  
  /**
   * @param toSubtract the value to subtract from this instance.
   * @return a new {@link IPv4Address} resulting from this operation.
   */
  public IPv4Address minus(int toSubtract) {
    Check.isTrue(toSubtract >= 0, "Value to subtract from IPv4 address %s must be >= 0. Got %s", this, toSubtract);

    int  intValue = Binary.getInt(bytes); 
    long diff     = intValue - toSubtract;
    
    Check.isTrue(diff < 0, "Value too large: %s - operation would yield invalid IPv4 address", toSubtract);
    
    return new IPv4Address(Binary.getBytes((int) diff), prefixLen);
  }
  
  // --------------------------------------------------------------------------
  // NetworkAddress interface
  
  @Override
  public int length() {
    return LEN;
  }

  @Override
  public int size() {
    return LEN * Byte.SIZE;
  }
  
  @Override
  public ImmutableBitSet toBitSet() {
    if (bitset == null) {
      bitset = new MultiByteBitset(bytes);
    }
    return bitset;
  }
  
  @Override
  public ImmutableBitSet getPrefix() {
    if (prefix == null) {
      boolean[] bits = new boolean[prefixLen];
      for (int i = SIZE - prefixLen, j = bits.length - 1; i < SIZE; i++, j--) {
        bits[j] = getBitAt(i);
      }
      prefix = new BooleanBitSet(bits);
    }
    return prefix;
  }
 
  // --------------------------------------------------------------------------
  // Object overrides
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(LEN * 3 + 3);
    for (int i = 0; i < bytes.length; i++) {
      if (i > 0) {
        s.append('.');
      }
      s.append(Integer.toString(Binary.unsignedByteToInt(bytes[i])));
    }
    if (prefixLen > 0) {
      s.append("/").append(prefixLen);
    }
    return s.toString();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IPv4Address) {
      IPv4Address other = IPv4Address.class.cast(obj);
      return bytes[0] == other.bytes[0] 
          && bytes[1] == other.bytes[1]
          && bytes[2] == other.bytes[2]
          && bytes[3] == other.bytes[3];
    }
    return false;
  }
  
  @Override
  public int hashCode() {
     return Safe.hashBytes(bytes) + prefixLen * Safe.PRIME;
  }
 
  // --------------------------------------------------------------------------
  // Externalizable interface
  
  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    bytes     = (byte[]) in.readObject();
    prefixLen = in.readInt();
    guessClass();
  }
   
  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(bytes);
    out.writeInt(prefixLen);
    guessClass();
  }
  
  // --------------------------------------------------------------------------
  // Restricted
  
  protected byte[] doGetBytes() {
    return bytes;
  }
  
  private void guessClass() {
    if (prefixLen > 0) {
      clazz = IPAddressClass.CLASSLESS;
    } else {
      clazz = IPAddressClass.classOf(bytes[0]);
    }
  }
  
  /**
   * @param literal an IPv4 address literal.
   * @return a new instance of this class, corresponding to the given literal.
   */
  static Pair<int[], Integer> doParse(String literal) {
    int prefixIndex = literal.indexOf('/');
    
    int prefix = 0;
    if (prefixIndex > 0) {
      Check.isTrue(prefixIndex < literal.length() - 1, "Invalid IP address literal: %s. Prefix value is empty", literal);
      String prefixValue = literal.substring(prefixIndex + 1, literal.length());
      Check.isInteger(prefixValue, "Invalid prefix (%s) in address %s. Integer value expected", prefixValue, literal);
      prefix = Integer.parseInt(prefixValue);
      Check.isTrue(prefix >= 0, "Invalid prefix (%s) in address %. Positive value expected ", prefix, literal);
      Check.isTrue(prefix <= SIZE, "Invalid prefix (%s) in address %s. Value is too large, expected between 0 and %s", prefix, literal, SIZE);
    }
  
    String   ipAddressValue = prefixIndex > 0 ? literal.substring(0, prefixIndex) : literal;
    String[] parts          = ipAddressValue.split("\\.");
    
    Check.isTrue(parts.length == LEN, "Invalid value for IPv4 address %s: got %s parts, expected %s", ipAddressValue, parts.length, LEN);
    
    int[] bytes = new int[LEN];
    for (int i = 0; i < parts.length; i++) {
      String p = parts[i];
      Check.isInteger(p, "IPv4 address literal %s, must be composed of groups of 3 digits separated by dots: %s is not a digit", ipAddressValue, p);
      bytes[i] = Integer.parseInt(p);
    }
    
    return new Pair<>(bytes, prefix);
  }
}
