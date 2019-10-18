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

import java.net.URL;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import javax.ws.rs.ProcessingException;

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
import io.openliberty.guides.inventory.client.UnknownUrlException;
import io.openliberty.guides.inventory.client.UnknownUrlExceptionMapper;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.apache.commons.lang3.exception.ExceptionUtils;

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
  @RestClient
  SystemClient systemClient;

  @Inject
  @ConfigProperty(name = "default.http.port")
  String DEFAULT_PORT;

  @GET
  @Path("/{hostname}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPropertiesForHost(@PathParam("hostname") String hostname) {
    // Get properties for host
    String customURLString = "http://" + hostname + ":" + DEFAULT_PORT + "/system";
    URL customURL = null;
    Properties props = null;
    try {
      customURL = new URL(customURLString);
      SystemClient systemClient = RestClientBuilder.newBuilder()
                .baseUrl(customURL)
                .register(UnknownUrlExceptionMapper.class)
                .build(SystemClient.class);
      // tag::properties[]
      props = systemClient.getProperties();
      // end::properties[]
    } catch (ProcessingException ex) {
      handleProcessingException(ex);
    } catch (UnknownUrlException e) {
      System.err.println("The given URL is unreachable.");
    } catch (MalformedURLException e) {
      System.err.println("The given URL is not formatted correctly.");
    }
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

  private void handleProcessingException(ProcessingException ex) {
    Throwable rootEx = ExceptionUtils.getRootCause(ex);
    if (rootEx != null && (rootEx instanceof UnknownHostException || 
        rootEx instanceof ConnectException)) {
      System.err.println("The specified host is unknown.");
    } else {
      throw ex;
    }
  }

}
// tag::InventoryResource[]
