package org.sapia.ned.ip.v4;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.sapia.ned.ip.v4.IPAddressClass;
import org.sapia.ned.ip.v4.IPv4Address;

public class IPAddressClassTest {


  @Test
  public void testIsClassOf() {
  }
  
  // --------------------------------------------------------------------------
  // classOf

  @Test
  public void testClassOf_with_class_A() {
    IPv4Address addr = new IPv4Address("127.0.0.1");
    
    assertThat(IPAddressClass.classOf(addr)).isEqualTo(IPAddressClass.CLASS_A);
  }
  
  @Test
  public void testClassOf_with_class_B() {
    IPv4Address addr = new IPv4Address("150.0.0.1");
    
    assertThat(IPAddressClass.classOf(addr)).isEqualTo(IPAddressClass.CLASS_B);
  }
  
  @Test
  public void testClassOf_with_class_C() {
    IPv4Address addr = new IPv4Address("192.0.0.1");
    
    assertThat(IPAddressClass.classOf(addr)).isEqualTo(IPAddressClass.CLASS_C);
  }

  @Test
  public void testClassOf_with_class_D() {
    IPv4Address addr = new IPv4Address("225.0.0.1");
    
    assertThat(IPAddressClass.classOf(addr)).isEqualTo(IPAddressClass.CLASS_D);
  }
  
  @Test
  public void testClassOf_with_class_E() {
    IPv4Address addr = new IPv4Address("241.0.0.1");
    
    assertThat(IPAddressClass.classOf(addr)).isEqualTo(IPAddressClass.CLASS_E);
  }
  
  // --------------------------------------------------------------------------
  // addressCount

  @Test
  public void testAddressCount_with_class_A() {
    assertThat(IPAddressClass.CLASS_A.addressCount()).isEqualTo(16777216);
  }

  @Test
  public void testAddressCount_with_class_B() {
    assertThat(IPAddressClass.CLASS_B.addressCount()).isEqualTo(65536);
  }
  
  @Test
  public void testAddressCount_with_class_C() {
    assertThat(IPAddressClass.CLASS_C.addressCount()).isEqualTo(256);
  }
  
  @Test
  public void testAddressCount_with_class_D() {
    assertThat(IPAddressClass.CLASS_D.addressCount()).isEqualTo(268435456);
  }
  
  @Test
  public void testAddressCount_with_class_E() {
    assertThat(IPAddressClass.CLASS_D.addressCount()).isEqualTo(268435456);
  }
  
  // --------------------------------------------------------------------------
  // addressBlockCount
  
  public void testAddressBlockCount_with_class_A() {
    assertThat(IPAddressClass.CLASS_A.addressBlockCount()).isEqualTo(16777216);
  }
  
  public void testAddressBlockCount_with_class_B() {
    assertThat(IPAddressClass.CLASS_B.addressBlockCount()).isEqualTo(16384);
  }
  
  public void testAddressBlockCount_with_class_C() {
    assertThat(IPAddressClass.CLASS_C.addressBlockCount()).isEqualTo(2097152);
  }
  
  public void testAddressBlockCount_with_class_D() {
    assertThat(IPAddressClass.CLASS_D.addressBlockCount()).isEqualTo(1);
  }
  
  public void testAddressBlockCount_with_class_E() {
    assertThat(IPAddressClass.CLASS_E.addressBlockCount()).isEqualTo(1);
  }
}
