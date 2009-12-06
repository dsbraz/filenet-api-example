import com.filenet.wcm.api.*;

public class MyGetCustomObject extends MyCESession {

  public MyGetCustomObject() throws Exception {
  }

  /**
  * Lab : Write a main method to retrieve a CustomObject
  *
  * Get the Content Engine Session object. use the variable 'myCESession".
  * Get the ObjectStore object
  * Get the CustomObject specified by the path : folderId/customObjectName
  * Retrieve the CustomObject properties
  * Retrieve the Property object from the properties collection
  * Display name and string value of each property.
  *
  * @param customObjectName CustomObject name
  * @param folderId Folder path name
  */
  public static void main(String[] args) {
    try
    {
      if(args.length < 2)
      {
        System.out.println("MyGetCustomObject usage: run MyGetCustomObject <CustomObject Title> <FolderPath>");
        System.exit(-1);
      }
      MyGetCustomObject myInstance = new MyGetCustomObject();

      String customObjectName = args[0];
      String folderId = args[1];
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      CustomObject customObject = (CustomObject) objectStore.getObject(BaseObject.TYPE_CUSTOMOBJECT, folderId+"/"+customObjectName);
      Properties ceProps = customObject.getProperties(new String[]{"agent_name","agent_id","agent_contract_date"});
      Property property = (Property) ceProps.get(0);
      String propName = property.getName();
      String propValue = property.getStringValue();
      System.out.println("Property Name = "+propName+" Value = "+propValue);
      property = (Property) ceProps.get(1);
      propName = property.getName();
      propValue = property.getStringValue();
      System.out.println("Property Name = "+propName+" Value = "+propValue);
      property = (Property) ceProps.get(2);
      propName = property.getName();
      propValue = property.getStringValue();
      System.out.println("Property Name = "+propName+" Value = "+propValue);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
