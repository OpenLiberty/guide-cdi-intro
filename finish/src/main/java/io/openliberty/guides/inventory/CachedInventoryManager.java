package io.openliberty.guides.inventory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.json.JsonObject;

import io.openliberty.guides.common.JsonMessages;
import io.openliberty.guides.inventory.util.InventoryUtil;

@Cached
public class CachedInventoryManager extends InventoryManager {
  
  private ConcurrentMap<String, JsonObject> inv = new ConcurrentHashMap<>();
  
  public JsonObject get(String hostname) {
    JsonObject properties = inv.get(hostname);
    if (properties == null) {
      if (InventoryUtil.responseOk(hostname)) {
        properties = InventoryUtil.getProperties(hostname);
        inv.putIfAbsent(hostname, properties);
      } else {
        return JsonMessages.SERVICE_UNREACHABLE.getJson();
      }
    } 
    return properties;
  }
}
