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

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import io.openliberty.guides.common.JsonMessages;

public class SystemClient {

    // Constants for building URI to the system service.
    private static final int DEFAULT_PORT = Integer.valueOf(System.getProperty("default.http.port"));
    private static final String SYSTEM_PROPERTIES = "/system/properties";
    private static final String PROTOCOL = "http";
    private static final String SECURED_PROTOCOL = "https";

    private String url;
    private boolean status;
    private JsonObject content;

    public SystemClient(String hostname) {
      this.url = setUrl(PROTOCOL, hostname, DEFAULT_PORT, SYSTEM_PROPERTIES);
      Builder builder = createClientBuilder();
      setStatusAndContent(builder);
    }

    public SystemClient(String hostname, int port) {
      this.url = setUrl(PROTOCOL, hostname, port, SYSTEM_PROPERTIES);
      Builder builder = createClientBuilder();
      setStatusAndContent(builder);
    }

    public SystemClient(String hostname, String authHeader) {
      this.url = setUrl(SECURED_PROTOCOL, hostname, DEFAULT_PORT, SYSTEM_PROPERTIES);
      Builder builder = createClientBuilder();
      builder.header(HttpHeaders.AUTHORIZATION, authHeader);
      setStatusAndContent(builder);
    }

    public String getUrl(){
      return this.url;
    }

    public boolean isResponseOk(){
      return this.status;
    }

    public JsonObject getContent(){
      return this.content;
    }

    /**
     * <p>Builds the URI string to the system service for a particular host. This is just a helper method.</p>
     * @param protocol - http or https.
     * @param host - name of host.
     * @param port - port number.
     * @param path - Note that the path needs to start with a slash!!!
     * @return String representation of the URI to the system properties service.
     */
    public static String setUrl(String protocol, String host, int port, String path){
        String auth = null;
        String query = null;
        String fragment = null;
        try {
          URI uri =  new URI(protocol, auth, host, port, path, query, fragment);
          return uri.toString();
        } catch (Exception e){
          System.out.println("URISyntaxException");
        }
        return "";
    }

    public Builder createClientBuilder() {
      Client client = ClientBuilder.newClient();
      WebTarget target = client.target(this.url);
      Builder builder = target.request();
      builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
      return builder;
    }

    public void setStatusAndContent(Builder clientBuilder) {
      try {
        Response response = clientBuilder.build("GET").invoke();
        this.status = true;
        this.content = toJsonObj(response.readEntity(String.class));
      } catch (Exception e) {
        this.status = false;
        this.content = JsonMessages.SERVICE_UNREACHABLE.getJson();
      }
    }

    public static JsonObject toJsonObj(String json) {
        JsonReader jReader = Json.createReader(new StringReader(json));
          return jReader.readObject();
    }


}
