// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.inventory;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// CDI
import javax.enterprise.context.ApplicationScoped;

import io.openliberty.guides.inventory.models.Inventory;
import io.openliberty.guides.inventory.util.InventoryUtil;

// tag::ApplicationScoped[]
@ApplicationScoped
// end::ApplicationScoped[]
public class InventoryManager {

  private static final ConcurrentMap<String, Properties> invMap = new ConcurrentHashMap<>();
  
  public Properties get(String hostname) {
    if (InventoryUtil.responseOk(hostname)) {
      Properties properties = InventoryUtil.getProperties(hostname);
      invMap.putIfAbsent(hostname, properties);
      return properties;
    } else {
      return null;
    }
  }

  public Inventory list() {
    Inventory inv = new Inventory();
    
    for (String host : invMap.keySet()) {
      Properties systemProps = invMap.get(host);
      Properties props = new Properties();
      props.setProperty("os.name", systemProps.getProperty("os.name"));
      props.setProperty("user.name", systemProps.getProperty("user.name"));
          
      inv.addHost(host, props);
    }
    return inv;
  }
}
