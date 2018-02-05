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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// CDI
import javax.enterprise.context.ApplicationScoped;

import io.openliberty.guides.inventory.models.Host;
import io.openliberty.guides.inventory.models.Inventory;
import io.openliberty.guides.inventory.models.Property;
import io.openliberty.guides.inventory.util.InventoryUtil;

// tag::ApplicationScoped[]
@ApplicationScoped
// end::ApplicationScoped[]
public class InventoryManager {

  private ConcurrentMap<String, Properties> inv = new ConcurrentHashMap<>();
  private Inventory invModel = new Inventory();
  
  public Properties get(String hostname) {
    if (InventoryUtil.responseOk(hostname)) {
      Properties properties = InventoryUtil.getProperties(hostname);
      buildInvModel(hostname, properties);
      inv.putIfAbsent(hostname, properties);
      return properties;
    } else {
      return null;
    }
  }

  public Inventory list() {
    return invModel;
  }
  
  private void buildInvModel(String hostname, Properties properties) {
    List<Property> propsList = new ArrayList<Property>();
    
    Property prop1 = new Property("os.name", properties.getProperty("os.name"));
    propsList.add(prop1);

    Property prop2 = new Property("user.name", properties.getProperty("user.name"));
    propsList.add(prop2);

    Host host = new Host(hostname, propsList);
    
    if (!invModel.getHosts().contains(host)) {
      invModel.getHosts().add(host);
    }
  }
}
