package io.openliberty.guides.inventory.models;

import java.util.List;

public class Host {
 
  private final String hostname; 
  private final List<Property> properties;

  public Host(String hostname, List<Property> properties) {
    this.hostname = hostname;
    this.properties = properties;
  }
  
  public String getHostname() {
    return hostname;
  }

  public List<Property> getProperties() {
    return properties;
  }
  
  @Override
  public boolean equals(Object host) {
    if (host instanceof Host) {
      return hostname.equals(((Host) host).getHostname());      
    }
    return false;
  }
}
