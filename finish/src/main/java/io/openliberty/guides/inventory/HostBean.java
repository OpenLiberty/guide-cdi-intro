package io.openliberty.guides.inventory;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class HostBean {
  
  private String hostname;

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }
}