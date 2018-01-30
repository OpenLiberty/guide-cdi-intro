package io.openliberty.guides.system;

import javax.xml.bind.annotation.XmlAttribute;

public class Property {

  private String key;
  private String value;
  
  public String getKey() {
    return key;
  }

  @XmlAttribute
  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  @XmlAttribute
  public void setValue(String value) {
    this.value = value;
  }
}
