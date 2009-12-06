import java.io.*;
import com.filenet.wcm.api.*;

public class MyGetDocumentContent extends MyCESession
{
  public MyGetDocumentContent() throws Exception
  {
  }

 /**
 * Lab: Write a method getMyDocumentContent to get the document content element and save the file.
 *
 * Steps:
 *
 *  Obtain a content engine session; use the variable "myCESession"
 *  Obtain the ObjectStore object from the ObjectFactory
 *  Retrieve the Document object from the ObjectStore
 *  Retrieve the content element from the document object. This is the TransportInputStream object
 *  Retrieve the mime type from the content element
 *  Retrieve the file name from the content element
 *  Retrieve the content size from the content element
 *  Retrieve the content stream from the content element. This is the InputStream object.
 *  Instantiate a FileOutputStream object using the file name. This is the OutputStream object
 *  Write the read contents from InputStream and write to OutputStream.
 *  Close the content, InputStream, OutputStream objects.
 *  Display the filename, mimetype, content size of the content element
 *
 *  @param objectStoreName the name of the ObjectStore
 *  @param documentName the full path and name of the document in the objectstore
 *  @param pageNumber the document content element number
  */
  public static void main(String[] args)
  throws Exception
  {
    if(args.length < 1)
    {
      System.out.println("Usage: run MyGetDocumentContent <documentPathName> [pageNumber]");
      System.exit(0);
    }
    MyGetDocumentContent myInstance = new MyGetDocumentContent();
    String documentName = args[0];
    String pageNumber = "1";
    if(args.length == 2)
      pageNumber = args[1];

        ObjectStore  objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
        Document document = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT, documentName);
        TransportInputStream content = document.getContentElement(Integer.parseInt(pageNumber));
        String mimeType = content.getMimeType();
        String fileName = content.getFilename();
        int contentSize = (int)content.getContentSize();

        InputStream inputStream = content.getContentStream();
        OutputStream outputStream = new FileOutputStream(fileName);

        byte[] nextBytes = new byte[64000];
        int nBytesRead;
        while ((nBytesRead = inputStream.read(nextBytes)) != -1)
        {
          outputStream.write(nextBytes, 0, nBytesRead);
          outputStream.flush();
        }

        System.out.println("document = "+documentName);
        System.out.println("page number = "+pageNumber);
        System.out.println("filename = "+fileName);
        System.out.println("mimetype = "+mimeType);
        System.out.println("content size = "+contentSize);
        inputStream.close();
        outputStream.close();
        content.close();
  }
}

