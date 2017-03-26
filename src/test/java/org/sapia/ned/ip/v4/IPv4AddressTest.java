package org.sapia.ned.ip.v4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.sapia.ned.bitset.ImmutableBitSet;
import org.sapia.ned.bitset.MultiByteBitset;
import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Safe;

public class IPv4AddressTest {
  
  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testIPv4Address_with_bytes() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);
    
    assertThat(addr.getIntAt(0)).isEqualTo(102);
    assertThat(addr.getIntAt(1)).isEqualTo(0);
    assertThat(addr.getIntAt(2)).isEqualTo(168);
    assertThat(addr.getIntAt(3)).isEqualTo(192);
  }

  @Test
  public void testIPv4Address_with_literal() {
    IPv4Address addr = new IPv4Address("192.168.0.102");
    
    assertThat(addr.getIntAt(0)).isEqualTo(102);
    assertThat(addr.getIntAt(1)).isEqualTo(0);
    assertThat(addr.getIntAt(2)).isEqualTo(168);
    assertThat(addr.getIntAt(3)).isEqualTo(192);
  }
  
  
  @Test
  public void testIPv4Address_with_literal_prefix() {
    IPv4Address addr = new IPv4Address("192.168.0.102/16");
    
    assertThat(addr.getIntAt(0)).isEqualTo(102);
    assertThat(addr.getIntAt(1)).isEqualTo(0);
    assertThat(addr.getIntAt(2)).isEqualTo(168);
    assertThat(addr.getIntAt(3)).isEqualTo(192);
    
    assertThat(addr.getPrefixLength()).isEqualTo(16); 
  }
  
  @Test
  public void testGetAddressClass_A() {
    IPv4Address addr = new IPv4Address("127.0.0.1");
    
    assertThat(addr.getAddressClass()).isEqualTo(IPAddressClass.CLASS_A);
  }
  
  @Test
  public void testGetAddressClass_B() {
    IPv4Address addr = new IPv4Address("172.0.0.1");
    
    assertThat(addr.getAddressClass()).isEqualTo(IPAddressClass.CLASS_B);
  }
  
  
  @Test
  public void testGetAddressClass_C() {
    IPv4Address addr = new IPv4Address("192.168.0.102");
    
    assertThat(addr.getAddressClass()).isEqualTo(IPAddressClass.CLASS_C);
  }
  
  @Test
  public void testGetAddressClass_D() {
    IPv4Address addr = new IPv4Address("224.0.0.1");
    
    assertThat(addr.getAddressClass()).isEqualTo(IPAddressClass.CLASS_D);
  }
  
  @Test
  public void testGetAddressClass_E() {
    IPv4Address addr = new IPv4Address("240.0.0.1");
    
    assertThat(addr.getAddressClass()).isEqualTo(IPAddressClass.CLASS_E);
  }

  @Test
  public void testIPv4Address_with_ImmutableBitSet() {
    ImmutableBitSet bitset = new MultiByteBitset(new byte[] {
       (byte) 192,
       (byte) 168,
       (byte) 0,
       (byte) 102
    });
    
    IPv4Address addr = new IPv4Address(bitset, 0);
    
    assertThat(addr.getIntAt(0)).isEqualTo(102);
    assertThat(addr.getIntAt(1)).isEqualTo(0);
    assertThat(addr.getIntAt(2)).isEqualTo(168);
    assertThat(addr.getIntAt(3)).isEqualTo(192);
  }

  @Test
  public void testGetBitSetAt() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);
    ImmutableBitSet bitset = addr.getBitSetAt(0);
    
    // expecting 102 (01100110)
    
    assertThat(bitset.getBitAt(0)).isEqualTo(false);
    assertThat(bitset.getBitAt(1)).isEqualTo(true);
    assertThat(bitset.getBitAt(2)).isEqualTo(true);
    assertThat(bitset.getBitAt(3)).isEqualTo(false);
    
    assertThat(bitset.getBitAt(4)).isEqualTo(false);
    assertThat(bitset.getBitAt(5)).isEqualTo(true);
    assertThat(bitset.getBitAt(6)).isEqualTo(true);
    assertThat(bitset.getBitAt(7)).isEqualTo(false);
  }

  @Test
  public void testGetBytes() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);
    int[] unsigned = Binary.unsignedBytesToInts(addr.getBytes());
    
    assertThat(unsigned[0]).isEqualTo(192);
    assertThat(unsigned[1]).isEqualTo(168);
    assertThat(unsigned[2]).isEqualTo(0);
    assertThat(unsigned[3]).isEqualTo(102);
  }

  @Test
  public void testLength() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);

    assertThat(addr.length()).isEqualTo(4);
  }

  @Test
  public void testSize() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);

    assertThat(addr.size()).isEqualTo(32);
  }

  @Test
  public void testToBitSet() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);
    IPv4Address copy = new IPv4Address(addr.toBitSet(), 0);
    
    assertThat(copy.getIntAt(0)).isEqualTo(102);
    assertThat(copy.getIntAt(1)).isEqualTo(0);
    assertThat(copy.getIntAt(2)).isEqualTo(168);
    assertThat(copy.getIntAt(3)).isEqualTo(192);
  }
  
  @Test
  public void testGetPrefix() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 0).withPrefix(16);

    ImmutableBitSet bitset = addr.getPrefix();
    
    assertThat(bitset.size()).isEqualTo(16);
    
    // expecting 11000000 10101000
    
    assertThat(bitset.getBitAt(0)).isFalse();
    assertThat(bitset.getBitAt(1)).isFalse();
    assertThat(bitset.getBitAt(2)).isFalse();
    assertThat(bitset.getBitAt(3)).isTrue();
    assertThat(bitset.getBitAt(4)).isFalse();
    assertThat(bitset.getBitAt(5)).isTrue();
    assertThat(bitset.getBitAt(6)).isFalse();
    assertThat(bitset.getBitAt(7)).isTrue();
    
    assertThat(bitset.getBitAt(8)).isFalse();
    assertThat(bitset.getBitAt(9)).isFalse();
    assertThat(bitset.getBitAt(10)).isFalse();
    assertThat(bitset.getBitAt(11)).isFalse();
    assertThat(bitset.getBitAt(12)).isFalse();
    assertThat(bitset.getBitAt(13)).isFalse();
    assertThat(bitset.getBitAt(14)).isTrue();
    assertThat(bitset.getBitAt(15)).isTrue();
  }
  
  @Test
  public void testGetSubnetMask_classless() {
    IPv4Address addr = new IPv4Address("167.199.170.82/27");
    
    // expecting 255.255.255.224
    
    assertThat(addr.getSubnetMask().getIntAt(0)).isEqualTo(224);
    assertThat(addr.getSubnetMask().getIntAt(1)).isEqualTo(255);
    assertThat(addr.getSubnetMask().getIntAt(2)).isEqualTo(255);
    assertThat(addr.getSubnetMask().getIntAt(3)).isEqualTo(255);
  }
  
  @Test
  public void testGetSubnetMask_classfull() {
    IPv4Address addr = new IPv4Address("127.0.0.1");
    
    assertThat(addr.getSubnetMask().getIntAt(0)).isEqualTo(0);
    assertThat(addr.getSubnetMask().getIntAt(1)).isEqualTo(0);
    assertThat(addr.getSubnetMask().getIntAt(2)).isEqualTo(0);
    assertThat(addr.getSubnetMask().getIntAt(3)).isEqualTo(255);
  }
  
  @Test
  public void testToString() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);
    
    assertThat(addr.toString()).isEqualTo("192.168.0.102");
  }

  @Test
  public void testEquals() {
    IPv4Address addr1 = new IPv4Address(192, 168, 0, 101);
    IPv4Address addr2 = new IPv4Address(192, 168, 0, 101);

    assertThat(addr1).isEqualTo(addr2);
  }

  @Test
  public void testEquals_false() {
    IPv4Address addr1 = new IPv4Address(192, 168, 0, 101);
    IPv4Address addr2 = new IPv4Address(192, 168, 0, 102);

    assertThat(addr1).isNotEqualTo(addr2);
  }
  
  @Test
  public void testHashCode() {
    IPv4Address addr = new IPv4Address(192, 168, 0, 102);
    assertThat(addr.hashCode()).isEqualTo(Safe.hashBytes(addr.getBytes()));
  }


}
