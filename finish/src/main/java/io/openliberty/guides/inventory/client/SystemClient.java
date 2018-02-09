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
package io.openliberty.guides.inventory.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Properties;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class SystemClient {

  // Constants for building URI to the system service.
  private static final int DEFAULT_PORT = Integer.valueOf(System.getProperty("default.http.port"));
  private static final String SYSTEM_PROPERTIES = "/system/properties";
  private static final String PROTOCOL = "http";

  private String url;
  private Builder clientBuilder;

  // Used by the following guide(s): CDI, MP-METRICS, FAULT-TOLERANCE
  public void init(String hostname) {
    this.initHelper(hostname, DEFAULT_PORT);
  }

  // Used by the following guide(s): MP-CONFIG, MP-HEALTH
  public void init(String hostname, int port) {
    this.initHelper(hostname, port);
  }

  // Helper method to set the attributes.
  private void initHelper(String hostname, int port) {
    this.setUrl(PROTOCOL, hostname, port, SYSTEM_PROPERTIES);
    this.setClientBuilder();
  }

  // tag::doc[]
  /**
   * <p>
   * Builds the URI string to the system service for a particular host. This is
   * just a helper method.
   * </p>
   *
   * @param protocol
   *          - http or https.
   * @param host
   *          - name of host.
   * @param port
   *          - port number.
   * @param path
   *          - Note that the path needs to start with a slash!!!
   * @return String representation of the URI to the system properties service.
   */
  // end::doc[]
  public void setUrl(String protocol, String host, int port, String path) {
    try {
      URI uri = new URI(protocol, null, host, port, path, null, null);
      this.url = uri.toString();
    } catch (Exception e) {
      System.out.println("URISyntaxException");
      this.url = null;
    }
  }

  public void setClientBuilder() {
    try {
      Client client = ClientBuilder.newClient();
      Builder builder = client.target(this.url).request();
      this.clientBuilder = builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    } catch (Exception e) {
      System.out.println("ClientBuilderException");
      this.clientBuilder = null;
    }
  }

  public Properties getProperties() {
    try {
      Response response = this.clientBuilder.get();

      if (response.getStatus() == Status.OK.getStatusCode()) {
        return response.readEntity(Properties.class);
      } else {
        System.out.println("Response Status is not OK.");
        return null;
      }

    } catch (Exception e) {
      System.out.println("Exception thrown while invoking the request.");
      return null;
    }

  }
}
