package io.openliberty.guides.inventory.model;

import java.util.Properties;

public class SystemModel {

    private final String hostname;
    private final Properties properties;

    public SystemModel(String hostname, Properties properties) {
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
      if (host instanceof SystemModel) {
        return hostname.equals(((SystemModel) host).getHostname());
      }
      return false;
    }
  }
