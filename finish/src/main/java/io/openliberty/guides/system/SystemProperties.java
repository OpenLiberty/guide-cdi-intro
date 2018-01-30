package io.openliberty.guides.system;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SystemProperties {

  private List<Property> properties;
  String key;
  String value;
  
  public List<Property> getProperties() {
    return properties;
  }
  
  @XmlElement
  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

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
