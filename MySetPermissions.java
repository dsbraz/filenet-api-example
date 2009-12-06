import com.filenet.wcm.api.*;

public class MySetPermissions extends MyCESession {

  public MySetPermissions() throws Exception {
  }

  public static void main(String[] args) {
    try {
      if(args.length < 4)
      {
        System.out.println("run MySetPermissions <username> <folderId> <documentId> <access type>");
        System.exit(-1);
      }
      MySetPermissions myInstance = new MySetPermissions();
      String user = args[0];
      String folderId = args[1];
      String type = args[3];
      String documentId = args[2];
       ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
       Folder folder =(Folder) objectStore.getObject(BaseObject.TYPE_FOLDER, folderId);
       Permissions perms =folder.getPermissions(Property.PERMISSIONS); 
// ObjectFactory.getPermissions(); 
       int accessType = Permission.TYPE_ALLOW;
       if(type.compareToIgnoreCase("allow") != 0)
          accessType = Permission.TYPE_DENY;
       Permission perm = ObjectFactory.getPermission(Permission.LEVEL_VIEW_CONTENT,
                                                   accessType,
                                                   user,
                                                   BaseObject.TYPE_USER);
       perm.setInheritableDepth (Permission.INHERITABLE_DEPTH_UNLIMITED); 
       perms.add(perm);
       folder.setPermissions(perms);

        Document document = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT, documentId);
        document.setSecurityParent(folder);
/*
        Property property = ObjectFactory.getProperty(Property.SECURITY_PARENT);
        property.setValue(folder);
        Properties properties = ObjectFactory.getProperties(); //document.getProperties();
        properties.add(property);
        document.setProperties(properties);
*/

    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }
}