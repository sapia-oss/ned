package org.sapia.ned.ip.v4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.sapia.ned.ip.v4.IPv4Address;
import org.sapia.ned.ip.v4.IPv4SubnetMask;
import org.sapia.ned.util.Serialization;

public class IPv4SubnetMaskTest {
  
  private IPv4SubnetMask mask;

  @Before
  public void setUp() throws Exception {
    mask = new IPv4SubnetMask(new int[] {255, 255, 0, 0}, 2 * Byte.SIZE);
  }

  @Test
  public void testIPv4SubnetMask_with_bytes() {
    IPv4SubnetMask other = new IPv4SubnetMask(mask.getBytes(), 2 * Byte.SIZE);
    
    assertThat(other).isEqualTo(mask);
  }

  @Test
  public void testIPv4SubnetMask_with_address() {
    IPv4SubnetMask other = new IPv4SubnetMask(new IPv4Address(new int[] {255, 255, 0, 0}, 2 * Byte.SIZE));
    
    assertThat(other).isEqualTo(mask);
  }
  
  @Test
  public void testGetFirstBlockAddress_classfull() {
    IPv4Address addr = new IPv4Address("73.22.17.25");
    
    IPv4Address first = addr.getSubnetMask().getFirstBlockAddress(addr);
    
    assertThat(first).isEqualTo(new IPv4Address("73.0.0.0"));
  }
  
  @Test
  public void testGetLastBlockAddress_classfull() {
    IPv4Address addr = new IPv4Address("73.22.17.25");
    
    IPv4Address last = addr.getSubnetMask().getLastBlockAddress(addr);
    
    assertThat(last).isEqualTo(new IPv4Address("73.255.255.255"));
  }

  @Test
  public void testGetFirstBlockAddress_classless() {
    IPv4Address addr = new IPv4Address("167.199.170.82/27");
    
    IPv4Address first = addr.getSubnetMask().getFirstBlockAddress(addr);
    
    assertThat(first).isEqualTo(new IPv4Address("167.199.170.64/27"));
  }

  @Test
  public void testGetLastBlockAddress_classless() {
    IPv4Address addr = new IPv4Address("167.199.170.82/27");
    
    IPv4Address last = addr.getSubnetMask().getLastBlockAddress(addr);
    
    assertThat(last).isEqualTo(new IPv4Address("167.199.170.95/27"));
  }

  @Test
  public void testToString() {
    assertThat(mask.toString()).isEqualTo("255.255.0.0");
  }
  
  @Test
  public void testEquals() {
     IPv4SubnetMask other = new IPv4SubnetMask(new int[] {255, 255, 0, 0}, 2 * Byte.SIZE);
     
     assertThat(other).isEqualTo(mask);
  }

  @Test
  public void testSerialization() {
    IPv4SubnetMask copy = Serialization.deserialize(Serialization.serialize(mask));
    
    assertThat(copy).isEqualTo(mask);
  }

}
