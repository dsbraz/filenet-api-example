import com.filenet.wcm.api.*;

public class MyAddCustomObject extends MyCESession {

  public MyAddCustomObject() throws Exception {
  }

  /**
  * Lab : Write a main method to create a CustomObject
  *
  * Create a Properties collection with CustomObject property values.
  * Obtain Content Engine Session object. use "myCESession" variable
  * Get ObjectStore object
  * Create the CustomObject using CustomObject class name and the properties collection parameters
  * Get the Folder object specified by the folderName parameter
  * Set the CustomObject Name and File the CustomObject into the Folder
  *
  * @param  customObjectName  new CustomObject name
  * @param  folderId  folder name to file the CustomObject
  */
  public static void main(String[] args) {
    try
    {
      if(args.length < 2)
      {
        System.out.println("MyAddCustomObject usage: run MyAddCustomObject <CustomObject Title> <FolderPath>");
        System.exit(-1);
      }
      MyAddCustomObject myInstance = new MyAddCustomObject();

      String customObjectName = args[0];
      String folderId = args[1];

      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty("agent_name");
      property.setValue("M&M");
      properties.add(property);

      property =  ObjectFactory.getProperty("agent_id");
      property.setValue(new Integer("365"));
      properties.add(property);

      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yy");
      java.util.Date date = sdf.parse("01/31/05");

      property =  ObjectFactory.getProperty("agent_contract_date");
      property.setValue(date);
      properties.add(property);

      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      CustomObject customObject = (CustomObject) objectStore.createObject("TravelAgent", properties, null);
      Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderId);
      customObject.file(folder,true,customObjectName);
//	  customObject = (CustomObject) objectStore.createAndFileObject("TravelAgent", properties, null, folder);      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
