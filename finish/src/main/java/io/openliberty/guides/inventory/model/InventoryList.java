// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.inventory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InventoryList {

  private List<Host> hosts = new ArrayList<Host>();

  public List<Host> getHosts() {
    return hosts;
  }

  public int getTotal() {
    return hosts.size();
  }

  public void addToInventoryList(String hostname, Properties systemProps) {
    Properties props = new Properties();
    props.setProperty("os.name", systemProps.getProperty("os.name"));
    props.setProperty("user.name", systemProps.getProperty("user.name"));

    this.addHost(hostname, props);

  }

  public void addHost(String hostname, Properties properties) {
    Host host = new Host(hostname, properties);
    if (!hosts.contains(host))
      hosts.add(host);
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

    @Override
    public boolean equals(Object host) {
      if (host instanceof Host) {
        return hostname.equals(((Host) host).getHostname());
      }
      return false;
    }
  }
}
