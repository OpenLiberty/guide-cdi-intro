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
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import io.openliberty.guides.inventory.util.InventoryUtil;

// tag::ApplicationScoped[]
@ApplicationScoped
// end::ApplicationScoped[]
public class InventoryManager {

    private ConcurrentMap<String, Properties> inv = new ConcurrentHashMap<>();

    public Properties get(String hostname) {
        if (InventoryUtil.responseOk(hostname)) {
          Properties properties = InventoryUtil.getProperties(hostname);
            inv.putIfAbsent(hostname, properties);
            return properties;
        } else {
            return null;
        }
    }

    public JsonObject list() {      
      JsonObjectBuilder systems = Json.createObjectBuilder();
      inv.forEach((host, props) -> {
          JsonObject systemProps = Json.createObjectBuilder()
                                       .add("os.name", props.getProperty("os.name"))
                                       .add("user.name", props.getProperty("user.name"))
                                       .build();
          systems.add(host, systemProps);
      });
      systems.add("hosts", systems);
      systems.add("total", inv.size());
      return systems.build();
    }
}
