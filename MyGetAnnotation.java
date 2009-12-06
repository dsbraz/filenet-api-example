import java.util.Iterator;

import com.filenet.wcm.api.*;

public class MyGetAnnotation extends MyCESession {

  public MyGetAnnotation() throws Exception {
  }


  /** LAB: Write a main method to retrieve an annotation from a document
  *
  * Get Content Engine Session object. Use the "myCESession" variable.
  * Get ObjectStore object
  * Retrieve the Document Object
  * Get Document's ANNOTATIONS property values
  * Iterate the values.
  *
  * @param docPath the document path for annotated object
**/
  public static void main(String[] args) {
    try {
      if(args.length < 2)
      {
        System.out.println("Usage: run MyGetAnnotation <DocPath> <fileName>");
        System.exit(-1);
      }
      MyGetAnnotation myInstance = new MyGetAnnotation();
      String docPath = args[0];
      String fileName = args[1];
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document aDoc = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,docPath);
      Values docAnnos = aDoc.getPropertyValuesValue(Property.ANNOTATIONS);
       Iterator it = docAnnos.iterator();
       java.io.OutputStream outputStream = new java.io.FileOutputStream(fileName);
       while (it.hasNext()) 
       {     
	   Value v = (Value)it.next();
           Annotation anAnno = (Annotation)v.getValue();
          TransportInputStream in1 = anAnno.getContent();
          java.io.InputStream inputStream = in1.getContentStream();

          byte[] nextBytes = new byte[64000];
          int nBytesRead;
          while ((nBytesRead = inputStream.read(nextBytes)) != -1)
          {
            outputStream.write(nextBytes, 0, nBytesRead);
            outputStream.flush();
          }
       }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
