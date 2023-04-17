// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.inventory.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Properties;
import java.net.URI;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SystemClient {

  // Constants for building URI to the system service.
  private final String SYSTEM_PROPERTIES = "/system/properties";
  private final String PROTOCOL = "http";

  @Inject
  @ConfigProperty(name = "system.http.port")
  String SYS_HTTP_PORT;

  // Wrapper function that gets properties
  public Properties getProperties(String hostname) {
    Properties properties = null;
    Client client = ClientBuilder.newClient();
    try {
      // Build the URL
      URI uri = new URI(
        PROTOCOL, null, hostname, Integer.valueOf(SYS_HTTP_PORT),
        SYSTEM_PROPERTIES, null, null);
      String url = uri.toString();

      // Create the client builder
      Builder builder = client.target(url).request()
                              .header(HttpHeaders.CONTENT_TYPE,
                                      MediaType.APPLICATION_JSON);

      // Process the request
      Response response = builder.get();
      if (response.getStatus() == Status.OK.getStatusCode()) {
        properties = response.readEntity(Properties.class);
      } else {
        System.err.println("Response Status is not OK.");
      }
    } catch (Exception e) {
      System.err.println(
        "Exception thrown while getting properties: " + e.getMessage());
    } finally {
      client.close();
    }
    return properties;
  }
}
