//tag::copyright[]
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
package io.openliberty.guides.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

//CDI
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
//JAX-RS
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("xmlproperties")
public class XMLPropertiesResource {

 @GET
 @Produces(MediaType.APPLICATION_XML)
 public SystemProperties getPropertiesInXML() {
   /*
   try {
   DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
   DocumentBuilder builder = builderFactory.newDocumentBuilder();

   Document document = builder.newDocument();
   Element root = document.createElement("systemProperties");
   document.appendChild(root);

   Properties systemProps = System.getProperties();
   Set<Object> keySet = systemProps.keySet();
   for (Object keyObject : keySet) {
     String keyString = (String) keyObject;

     Element property = document.createElement("property");
     root.appendChild(property);

     Attr key = document.createAttribute("key");
     key.setValue(keyString);
     property.setAttributeNode(key);

     Attr value = document.createAttribute("value");
     value.setValue(systemProps.getProperty(keyString));
     property.setAttributeNode(value);
   }
   
   DOMSource domSource = new DOMSource(document);
   StringWriter writer = new StringWriter();
   StreamResult result = new StreamResult(writer);
   TransformerFactory tf = TransformerFactory.newInstance();
   Transformer transformer = tf.newTransformer();
   transformer.transform(domSource, result);
   System.out.println(writer.toString());
   
   return writer.toString();
   
  } catch (ParserConfigurationException | TransformerException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
*/
   // Using JAXB 
   Properties systemProps = System.getProperties();
   Set<Object> keySet = systemProps.keySet();
   List<Property> properties = new ArrayList<Property>();
   for (Object keyObject : keySet) {
     String keyString = (String) keyObject;

     Property property = new Property();

     property.setKey(keyString);
     property.setValue(systemProps.getProperty(keyString));
     properties.add(property);
   }
   SystemProperties result = new SystemProperties();
   result.setProperties(properties);
   
   return result;
   
 }
 
}

