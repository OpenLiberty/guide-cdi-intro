package io.openliberty.guides.inventory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.crypto.dsig.XMLObject;

import io.openliberty.guides.common.JsonMessages;
import io.openliberty.guides.inventory.util.InventoryUtil;

@ResultType
public class XMLInventoryManager extends InventoryManager {

  private ConcurrentMap<String, String> inv = new ConcurrentHashMap<>();
  
  public String getXML(String hostname) {
    if (InventoryUtil.responseOk(hostname)) {
        String properties = InventoryUtil.getPropertiesInXML(hostname);
        System.out.println("XMLIM:" + properties.toString());
        inv.putIfAbsent(hostname, properties);
        return properties;
    } else {
        return null; //JsonMessages.SERVICE_UNREACHABLE.getJson();
    }
  }
  // list the output in XML 
  /*public JsonObject list() {
    JsonObjectBuilder systems = Json.createObjectBuilder();
    inv.forEach((host, props) -> {
      JsonObject javaProps = Json.createObjectBuilder()
                                 .add("java.version",
                                      props.getString("java.version"))
                                 .add("java.runtime.name",
                                      props.getString("java.runtime.name"))
                                 .add("java.runtime.versionn",
                                      props.getString("java.runtime.version"))
                                 .add("java.vm.name",
                                      props.getString("java.vm.name"))
                                 .add("java.vm.version",
                                      props.getString("java.vm.version"))
                                 .add("java.vm.info",
                                      props.getString("java.vm.info"))
                                 .build();
      systems.add(host, javaProps);
    });
    systems.add("hosts", systems);
    systems.add("total", inv.size());
    return systems.build();
  }*/
  
  public String listXML() {
    /*inv.forEach((host, props) -> {
        JsonObject systemProps = Json.createObjectBuilder()
                                     .add("os.name", props.getString("os.name"))
                                     .add("user.name", props.getString("user.name"))
                                     .build();
        systems.add(host, systemProps);
    });
    systems.add("hosts", systems);
    systems.add("total", inv.size());
    return systems.build();*/
    return "TBA";
  }
  
}