package io.openliberty.guides.inventory.models;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Host> hosts = new ArrayList<Host>();

    public List<Host> getHosts() {
      return hosts;
    }

    public int getTotal() {
      return hosts.size();
    }
    
    public boolean containsHost(String hostname) {
      for (Host host : hosts) {
        return host.getHostname().equals(hostname);
      }
      return false;
    }
}

