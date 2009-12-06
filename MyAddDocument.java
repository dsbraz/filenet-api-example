import java.io.File;
import java.io.FileInputStream;

import com.filenet.wcm.api.*;

public class MyAddDocument extends MyCESession {

  public MyAddDocument() throws Exception {
  }


  /* LAB: Write a main method to create and add a document to objectstore
  *
*
*  Get empty Properties collection
*  Get Property object for the Property.DOCUMENT_TITLE
*  Set the Property object value using docTitle argument
*  Add the Property object to the Properties collection
*  Get Content Engine Session object. Use the "myCESession" variable.
*  Get ObjectStore object
*  Create a DOCUMENT object with the Properties collection created above.
*  Get the Folder object specified by the folderName argument
*  file the document into the Folder object
*  Create an instance of TransportInputStream object with the file content specified by the fileName argument
*  Set document content with checkin parameter set to false
*  Checkin the document
*  Retrieve and display the document ID
*
*  @param docTitle the document title for the new document
*  @param folderName the name of the folder to file the new document
*  @param fileName the file name of the document content
*/
  public static void main(String[] args) {
    try {
      if(args.length < 3)
      {
        System.out.println("Usage: run MyAddDocument <DocTitle> <FolderPath> <FileName>");
        System.exit(-1);
      }
      MyAddDocument myInstance = new MyAddDocument();
      String docTitle = args[0];
      String folderName = args[1];
      String fileName = args[2];
      if(!new File(fileName).exists())
      {
        System.out.println("File "+fileName+" does not exist.");
        System.exit(-1);
      }
      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
      Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderName);
      document.file(folder, false);
      TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
      document.setContent(content, false, false);
      document.checkin(false);
      System.out.println("Document successfully added. DocID = "+document.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
