import java.io.File;
import java.io.FileInputStream;

import com.filenet.wcm.api.*;

public class MyAnnotation extends MyCESession {

  public MyAnnotation() throws Exception {
  }


  /** LAB: Write a main method to create and add a annotation to a document
  *
  * Get Content Engine Session object. Use the "myCESession" variable.
  * Get ObjectStore object
  * Retrieve a Document Object
  * Get empty Properties collection
  * Get Property object for the Property.ANNOTATED_OBJECT
  * Set the Property object value to Document Object
  * Add the Property object to the Properties collection
  * Create a Annotation object with the Properties collection
  * Create an instance of TransportInputStream object with the file content specified by the fileName argument
  * Set annotation content
  * Retrieve and display the annotation ID
  *
  * @param docPath the document path for annotated object
  * @param fileName the file name of the annotation content
*/
  public static void main(String[] args) {
    try {
      if(args.length < 2)
      {
        System.out.println("Usage: run MyAnnotation <DocumentPath> <FileName>");
        System.exit(-1);
      }
      MyAnnotation myInstance = new MyAnnotation();
      String docPath = args[0];
      String fileName = args[1];
      if(!new File(fileName).exists())
      {
        System.out.println("File "+fileName+" does not exist.");
        System.exit(-1);
      }
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document doc = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,docPath);
      
      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.ANNOTATED_OBJECT);
      property.setValue(doc);
      properties.add(property);
      
      property = ObjectFactory.getProperty(Property.ANNOTATED_CONTENT_ELEMENT);
      property.setValue(1);
      properties.add(property);
                          
      Permissions  perms = doc.getUserAccess();
                                                  
      Annotation annotation = (Annotation) objectStore.createObject(ClassDescription.ANNOTATION,
          properties, perms);
	  String annoID = annotation.getId();
	  System.out.println("annoID = " + annoID);

      TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
      content.setFilename(doc.getFilename());
      annotation.setContent(content);
      System.out.println("Annotation successfully created.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
