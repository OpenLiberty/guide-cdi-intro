// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2019 IBM Corporation and others.
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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.openliberty.guides.inventory.model.InventoryList;
import io.openliberty.guides.inventory.client.SystemClient;

// tag::RequestScoped[]
@RequestScoped
// end::RequestScoped[]
// tag::endpoint[]
@Path("/systems")
// end::endpoint[]
// tag::InventoryResource[]
public class InventoryResource {

  // tag::inject[]
  @Inject
  // end::inject[]
  InventoryManager manager;

  // tag::inject2[]
  @Inject
  // end::inject2[]
  SystemClient systemClient;

  @GET
  @Path("/{hostname}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPropertiesForHost(@PathParam("hostname") String hostname) {
    // Get properties for host
    // tag::properties[]
    Properties props = systemClient.getProperties(hostname);
    // end::properties[]
    if (props == null) {
      return Response.status(Response.Status.NOT_FOUND)
                     .entity("ERROR: Unknown hostname or the system service may not be " 
                             + "running on " + hostname)
                     .build();
    }

    // Add to inventory
    // tag::managerAdd[]
    manager.add(hostname, props);
    // end::managerAdd[]
    return Response.ok(props).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public InventoryList listContents() {
    // tag::managerList[]
    return manager.list();
    // end::managerList[]
  }
}
// tag::InventoryResource[]
