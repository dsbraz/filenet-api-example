import java.util.Iterator;

import com.filenet.wcm.api.*;
import com.filenet.wcm.api.util.Name;

public class MyGetPermissions extends MyCESession {

  public MyGetPermissions() throws Exception {
  }

  String permissionSourceAsName(int intPermSource)
  {
         String permissionSource= "";
          switch(intPermSource) 
         {
          case Permission.PERMISSION_SOURCE_DEFAULT:
            permissionSource = "default";
            break;
          case Permission.PERMISSION_SOURCE_DIRECT:
            permissionSource = "direct";
            break;
          case Permission.PERMISSION_SOURCE_PARENT:
            permissionSource = "parent";
            break;
          case Permission.PERMISSION_SOURCE_TEMPLATE:
            permissionSource = "template";
            break;
         }
         return permissionSource;
  }

  String granteeTypeAsName(int intGranteeType)
  {
      String granteeType = "";
      switch(intGranteeType)
      {
         case BaseObject.TYPE_USER:
         	granteeType = "User";
       	break;
	   case BaseObject.TYPE_GROUP:
		granteeType = "Group";
            break;
      }
      return granteeType;
  }

  public static void main(String[] args) throws Exception {
    try {
      if(args.length < 1)
      {
        System.out.println("run MyGetPermissions <documentId>");
        System.exit(-1);
      }
      MyGetPermissions myInstance = new MyGetPermissions();
      String documentId = args[0];

        ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
        Document document = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT, documentId);
        Permissions permissions = document.getPermissions(); // getUserAccess();
        Permission permission;
        Iterator permIt = permissions.iterator();
        while (permIt.hasNext())
        {
             permission = (Permission)permIt.next();   
		     System.out.println("============================================");
             System.out.println("Name = " + permission.getGranteeName());
		     System.out.println("Grantee Type = " + myInstance.granteeTypeAsName(permission.getGranteeType()));
             System.out.println("Access Level = " + Name.accessLevelAsName(permission.getAccess()));
             System.out.println("Access type = " + Name.accessTypeAsName(permission.getAccessType()));
             System.out.println("Permission Source = " + myInstance.permissionSourceAsName(permission.getPermissionSource()));
             java.util.ArrayList names = Name.accessRightsAsNames(permission.getAccess());
   		     Iterator nameIt = names.iterator();
             while (nameIt.hasNext())
     		 {
                 System.out.println("right = " + nameIt.next().toString());
		 }
        }
    }
    catch (com.filenet.wcm.api.BadReferenceException bre)
    {
        System.out.println("Object Not Found : User may not have access or bad document");
    }
    catch (com.filenet.wcm.api.InvalidCredentialsException ice)
    {
        System.out.println("Invalid username and password");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}