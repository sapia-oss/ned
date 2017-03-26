package org.sapia.ned.ip.v4;

import java.util.ArrayList;
import java.util.List;

import org.sapia.ned.util.Binary;
import org.sapia.ned.util.Check;
import org.sapia.ned.util.Stdout;

public class IPv4SubnetGenerator {
  
  private IPv4Address address;
  private int numberOfSubnets   = -1;
  private int numberOfAddresses = -1;
  
  public IPv4SubnetGenerator withBaseAddress(IPv4Address address) {
    this.address = address;
    return this;
  }
  
  public IPv4SubnetGenerator withNumberOfAddresses(int numberOfAddresses){
    this.numberOfAddresses = numberOfAddresses;
    return this;
  }
  
  public IPv4SubnetGenerator withNumberOfSubnets(int numberOfSubnets) {
    this.numberOfSubnets = numberOfSubnets;
    return this;
  }
  
  public List<IPv4Subnet> generate() {
    Check.isNotNull(address, "Base IP address not set");
    Check.isTrue(address.getPrefixLength() > 0, "Expected prefix length to be > 0 for base address %s", address);
    Check.isTrue(numberOfAddresses > 0 || numberOfSubnets > 0, "Either number of expected subnets or number of expected addresses has to be specified");
    Check.isTrue(
        Binary.unnsignedIntToLong(Binary.getInt(address.getBytes())) % (long) address.getAddressCount() == 0, 
        "The base address %s must divisible by the number of addresses (%s) in the block", 
        address, address.getAddressCount()
    );
    if (numberOfAddresses > 0) {
      Check.isTrue(
          numberOfAddresses <= address.getAddressCount(), 
          "Number of expected addresses (%s) must be smaller than/equal to number of possible addresses (%s) in block corresponding to %s", 
          numberOfAddresses, address.getAddressCount(), address
      );
    }
    
    // Formula: n_sub = n + log2 (N/N_sub)
    // Where:
    //  n     = network prefix of the base IP address
    //  N     = the total number of addresses in the network corresponding to the base IP address
    //  n_sub = the network prefix computed for each subnet
    //  N_sub = the number of addresses in each subnet
    
    int n     = address.getPrefixLength();
    int N     = address.getAddressCount();
    int N_sub = numberOfSubnets > 0 ? address.getAddressCount() / numberOfSubnets : numberOfAddresses;
    Check.isTrue((N_sub & (N_sub - 1)) == 0, "Number of addresses in subnets must be a power of two. Got %s", N_sub);
    int n_sub = n + (int) (Math.log(N / N_sub) / Math.log(2));
    
    if (n_sub < 0) {
      Stdout.args("....");
    }
    
    List<IPv4Subnet> subnets = new ArrayList<>(numberOfSubnets > 0 ? numberOfSubnets : 1);
    
    IPv4Address previous = null;
    int max = numberOfSubnets > 0 ? numberOfSubnets : 1;
    for (int i = 0; i < max; i++) {
      IPv4Address firstAddress;
      if (previous == null) {
        firstAddress = new IPv4Address(address.toBitSet(), n_sub);
      } else {
        firstAddress = previous.plus(1);
      }
      
      IPv4Address lastAddress = firstAddress.plus(N_sub - 1);
      subnets.add(new IPv4Subnet(firstAddress, lastAddress));
      previous = lastAddress;
    }

    return subnets;
  }
  
  public static void main(String[] args) {
    IPv4Subnet ip1 = new IPv4SubnetGenerator()
        .withBaseAddress(new IPv4Address("130.192.0.0/16"))
        .withNumberOfAddresses(64)
        .generate().get(0);
    Stdout.args("IP1, IP2: ", ip1);

    IPv4Subnet ip3 = new IPv4SubnetGenerator()
        .withBaseAddress(ip1.getLastAddress().plus(1))
        .withNumberOfAddresses(32)
        .generate().get(0);
    Stdout.args("IP3, IP4: ", ip3);

    IPv4Subnet ip5 = new IPv4SubnetGenerator()
        .withBaseAddress(ip3.getLastAddress().plus(33))
        .withNumberOfAddresses(32)
        .generate().get(0);
    Stdout.args("IP5: ", ip5);
  }

}
