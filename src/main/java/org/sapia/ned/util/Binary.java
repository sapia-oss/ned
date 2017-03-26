package org.sapia.ned.util;

/**
 * Helper class providing binary-related utilities.
 * 
 * @author yduchesne
 *
 */
public final class Binary {

  private Binary() {
  }
  
  /**
   * @param index the index of the bit to return.
   * @param bits the bits to read from, <b>in big-endian notation</b>.
   * @return the bit at the specified index.
   */
  public static boolean getBitAt(int index, int bits) {
    return ((bits >> index) & 1) == 1; 
  }
  
  /**
   * @param index the index of the bit to set 'on'.
   * @param bits the integer for which to set a specific bit 'on'.
   * @return the integer resulting from the operation.
   */
  public static int setBitAt(int index, int bits) {
    return bits | (1 << index);
  }
  
  /**
   * @param index the index of the bit to set 'on' or 'off'.
   * @param bits the integer for which to set a specific bit 'on' or 'off'.
   * @param value an 'on' (<code>true</code>) or 'off' (<code>false</code>) value.
   * @return the integer resulting from the operation.
   */
  public static int setBitAt(int index, int bits, boolean value) {
    if (value) {
      return bits | (1 << index);
    } else {
      return bits & ~(1 << index);
    }
  }

  /**
   * @param bits the bits whose binary representation should be returned.
   * @return a string corresponding to the binary representation of the given bits.
   */
  public static String toBitString(int bits) {
    StringBuilder s = new StringBuilder();
    for (int i = Integer.SIZE - 1; i >= 0; i--) {
      s.append(getBitAt(i, bits) ? '1' : '0');
    }
    return s.toString();
  }
  
  /**
   * @param bits the bits whose binary representation should be returned.
   * @return a string corresponding to the binary representation of the given bits.
   */
  public static String toBitString(short bits) {
    StringBuilder s = new StringBuilder();
    for (int i = Short.SIZE - 1; i >= 0; i--) {
      s.append(getBitAt(i, bits) ? '1' : '0');
    }
    return s.toString();
  }
  
  /**
   * @param bits the bits whose binary representation should be returned.
   * @return a string corresponding to the binary representation of the given bits.
   */
  public static String toBitString(byte bits) {
    StringBuilder s = new StringBuilder();
    for (int i = Byte.SIZE - 1; i >= 0; i--) {
      s.append(getBitAt(i, bits) ? '1' : '0');
    }
    return s.toString();
  }
  
  /**
   * @param binaryLiteral a binary  literal.
   * @return the byte parsed from the given literal.
   */
  public static byte parseByte(String binaryLiteral) {
    Check.isTrue(binaryLiteral.length() <= Byte.SIZE, "Invalid length: must be <= %s. Got: %s", Byte.SIZE, binaryLiteral.length());
    byte bits = 0;
    for (int i = binaryLiteral.length() - 1; i >= 0; i--) {
      char c = binaryLiteral.charAt(i);
      Check.isTrue(c == '0' || c == '1', "Invalid character in binary literal. Expecting 0s and 1s, got: %s", c);
      if (c == '1') {
        bits = (byte) Binary.setBitAt(i, bits);
      }
    }
    return bits;
  }
  
  /**
   * @param b a unsigned byte to return as an int.
   * @return the given unsigned byte, as an int.
   */
  public static int unsignedByteToInt(byte b) {
    return b & 0xFF;
  }
  
  /**
   * @param i an unsigned int to return as a long.
   * @returna the given unsigned int, as a long.
   */
  public static long unnsignedIntToLong(int i) {
    return i & 0x00000000ffffffffL;
  }
  
  /**
   * @param bytes an array of bytes to interpret as unsigned.
   * @return an array of ints, corresponding to the given unsigned bytes.
   */
  public static int[] unsignedBytesToInts(byte[] bytes) {
    int[] ints = new int[bytes.length];
    for (int i = 0; i < ints.length; i++) {
      ints[i] = unsignedByteToInt(bytes[i]);
    }
    return ints;
  }
  
  /**
   * @param bytes an array of bytes, where the most significant byte is at index 0,
   *              and the least significant one at index bytes.length - 1.
   * @return the <code>int</code> value to which the given bytes have been encoded.
   */
  public static int getInt(byte[] bytes) {
    Check.isTrue(bytes.length == Integer.BYTES, "Expected the given array to have %s bytes. Got: %s", Integer.BYTES, bytes.length);
    return
      ((bytes[0] & 0xff) << 24) |
      ((bytes[1] & 0xff) << 16) |
      ((bytes[2] & 0xff) << 8) |
       (bytes[3] & 0xff);
  }
  
  /**
   * @param value a value to convert to an array of bytes.
   * @return a new array of bytes, where the most significant byte is at index 0,
   *         and the least significant one at index bytes.length - 1.
   */
  public static byte[] getBytes(int value) {
    byte[] bytes = new byte[Integer.BYTES];
    bytes[0] = (byte)(0xff & (value >> 24));
    bytes[1] = (byte)(0xff & (value >> 16));
    bytes[2] = (byte)(0xff & (value >> 8));
    bytes[3] = (byte)(0xff & value);
    return bytes;
  }
  
  public static void main(String[] args) {
    Stdout.args(
        unsignedByteToInt((byte) -126), ".",
        unsignedByteToInt((byte) -64),  ".",
        unsignedByteToInt((byte) 0), ".",
        unsignedByteToInt((byte) 12)
    );
    Stdout.args(getInt(new byte[] {-126, -64, 0, 12}));
  }

}
