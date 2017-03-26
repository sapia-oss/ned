package org.sapia.ned.examples;

import java.util.List;

import org.sapia.ned.ip.v4.IPv4Address;
import org.sapia.ned.ip.v4.IPv4Subnet;
import org.sapia.ned.ip.v4.IPv4SubnetGenerator;
import org.sapia.ned.util.Stdout;

public class Subnets {

  public static void main(String[] args) {
    Stdout.args("---> Classless addressing: subnet, number of addresses");
    
    IPv4Address classlessAddr = new IPv4Address("206.0.64.0/18");
    Stdout.args("Class: ", classlessAddr.getAddressClass());
    IPv4Subnet classlessSubnet = new IPv4SubnetGenerator()
        .withBaseAddress(classlessAddr)
        .withNumberOfAddresses(1024)
        .generate().get(0);
    Stdout.args("Subnet: ", classlessSubnet);
    

    Stdout.args("---> Subnetting (with specified number of subnets)");
    IPv4Address classfullAddr = new IPv4Address("205.101.55.0/24");
    List<IPv4Subnet>  classfullSubnets = new IPv4SubnetGenerator()
        .withBaseAddress(classfullAddr)
        .withNumberOfSubnets(2)
        .generate();
    Stdout.args("Number of subnets....: ", classfullSubnets.size());
    
    IPv4Subnet classFullSubnet = classfullSubnets.get(0);
    Stdout.args("1st subnet: " + classFullSubnet);
    Stdout.args("Subnet: ", classFullSubnet.getNetworkAddress().getSubnetMask(), " => ", classFullSubnet.getNetworkAddress().getSubnetMask().toBitSet());
    
  }
  
}
