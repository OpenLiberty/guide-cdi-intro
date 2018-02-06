package io.openliberty.guides.inventory.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Inventory {
  
  private List<Host> hosts = new ArrayList<Host>();

  public List<Host> getHosts() {
    return hosts;
  }

  public int getTotal() {
    return hosts.size();
  }
  
  public void addHost(String hostname, Properties properties) {
    hosts.add(new Host(hostname, properties));
  }
  
  class Host {
    
    private final String hostname; 
    private final Properties properties;

    public Host(String hostname, Properties properties) {
      this.hostname = hostname;
      this.properties = properties;
    }
    
    public String getHostname() {
      return hostname;
    }

    public Properties getProperties() {
      return properties;
    }
  }
}

