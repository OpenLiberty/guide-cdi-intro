package io.openliberty.guides.inventory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

@JavaVersion
public class JavaVersionInventoryManager extends InventoryManager {

  // list the java version info of the hosts
  public JsonObject list() {
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
  }
}