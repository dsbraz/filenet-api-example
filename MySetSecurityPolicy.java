import java.util.Iterator;

import com.filenet.wcm.api.*;

public class MySetSecurityPolicy extends MyCESession {

  public MySetSecurityPolicy() throws Exception {
  }

  public static void main(String[] args) {
    try {
      if(args.length < 2)
      {
        System.out.println("run MySetSecurityPolicy <securityPolicy name> <folderId>");
        System.exit(-1);
      }
      MySetSecurityPolicy myInstance = new MySetSecurityPolicy();
      String securityPolicyName = args[0];
      String folderId = args[1];

       ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
       Folder folder =(Folder) objectStore.getObject(BaseObject.TYPE_FOLDER, folderId);

       SecurityPolicies securityPolicies = objectStore.getSecurityPolicies();
       SecurityPolicy securityPolicy = (SecurityPolicy)securityPolicies.findByProperty(Property.DISPLAY_NAME, ReadableMetadataObjects.IS_EQUAL, securityPolicyName); 

       Values securityTemplates = securityPolicy.getPropertyValuesValue(Property.SECURITY_TEMPLATES);
       Iterator secTemplateIt = securityTemplates.iterator();
       String securityTemplateID = "";
       while (secTemplateIt.hasNext())
       {
            Value value = (Value)secTemplateIt.next();
            SecurityTemplate securityTemplate = (SecurityTemplate)value.getValue();
            securityTemplateID = securityTemplate.getApplyStateId();
            System.out.println("ID = " + securityTemplateID);
       }
    
        Property property = ObjectFactory.getProperty(Property.SECURITY_POLICY);
        property.setValue(securityPolicy);
        Properties properties = folder.getProperties();
        properties.add(property);
        folder.setProperties(properties);

        folder.applySecurityTemplate(securityTemplateID);

    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }
}