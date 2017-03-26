package org.sapia.ned.ip.v4;

import java.util.ArrayList;
import java.util.List;

import org.sapia.ned.util.Safe;

public class IPv4Subnet {
  
  private IPv4Address netAddress;
  private IPv4Address lastAddress;
  
  IPv4Subnet(IPv4Address netAddress, IPv4Address lastAddress) {
    this.netAddress  = netAddress;
    this.lastAddress = lastAddress;
  }

  public IPv4Address getNetworkAddress() {
    return netAddress;
  }
  
  public IPv4Address getLastAddress() {
    return lastAddress;
  }

  public List<IPv4Address> getAddresses() {
     List<IPv4Address> addresses = new ArrayList<>();
     addresses.add(netAddress);
     IPv4Address previous = netAddress;
     for (int i = 0; i < netAddress.getAddressCount() - 1; i++) {
       IPv4Address next = previous.plus(1);
       addresses.add(next);
       previous = next;
     }
     return addresses;
  }
  
  @Override
  public String toString() {
    return "[range: " + netAddress + " - " + lastAddress + ", mask: " + netAddress.getSubnetMask() +"]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IPv4Subnet) {
      IPv4Subnet other = IPv4Subnet.class.cast(obj);
      return netAddress.equals(other.netAddress) && lastAddress.equals(other.lastAddress);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return Safe.hashCode(netAddress, lastAddress);
  }

}
