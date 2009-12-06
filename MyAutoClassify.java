import java.io.*;

import com.filenet.wcm.api.*;


public class MyAutoClassify extends MyCESession {


  public MyAutoClassify() throws Exception {
  }

  /**
  * Write a main method to perform and verify document auto classification
  * Obtain a content engine Session object; Use "myCESession" variable
  * Obtain the ObjectStore object
  * Create a Document object of specified classId
  * Obtain the Folder object specified by the folder name
  * File the Document with the folder
  * Create a TransportInputStream object with the file content to be autoclassified into the document
  * Set document content using the TransportInputStream content object and set the checkin and the autoclassify flags to true
  *
  *
  * Verify Auto Classification :
  *      Obtain the Properties collection from the document for the specified autoclassified property names
  *      Iterate throught the Properties collection, for each property display the property name and the property value
  *
  *  @param classId the class id
  *  @param docTitle the document title
  *  @param folderName the folder name
  *  @param fileName the name of the file containing the content to be autoclassified in a document
 */
  public static void main(String[] args)  throws Exception {
    try {
      if(args.length < 3)
      {
        System.out.println("Usage: run MyAutoClassify <DocTitle> <FolderPath> <FileName>");
        System.exit(-1);
      }

      MyAutoClassify myInstance = new MyAutoClassify();
      String docTitle = args[0];
      String folderName = args[1];
      String fileName = args[2];

      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);
  
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
      Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderName);
      document.file(folder, false);
      TransportInputStream content = new TransportInputStream(new FileInputStream(new File(fileName)));
      document.setContent(content, true, true);
      
	  while (document.getPropertyIntValue(Property.CLASSIFICATION_STATUS) == 1)
		 document.refresh();
		 
      String[] objectProps = {"cruise_id","customer_name"};
      Properties props = document.getProperties(objectProps);
      property = (Property) props.get(0);
      System.out.println(property.getLabel()+ " = "+property.getStringValue());
      property = (Property) props.get(1);
      System.out.println(property.getLabel()+ " = "+property.getStringValue());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
