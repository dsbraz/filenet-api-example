import com.filenet.wcm.api.*;

public class MyRepublish extends MyCESession{

  public MyRepublish() throws Exception {
  }

  public static void main(String[] args) {
   if (args.length < 1)
    {
        System.out.println("usage: run MyRepublish <document name>");
        System.exit(1);
    }
    try {
        MyRepublish myInstance = new MyRepublish();
        String docName = args[0];

        // declare and initialize a MimeTypes type variable by calling its constructor
        MimeTypes mimeTypes = new MimeTypes();
        //get the objectstore
        ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
        //get the document of interest
        Document sourceDoc = (Document)objectStore.getObject(BaseObject.TYPE_DOCUMENT, docName);
       //get the publications linked to this document
        Documents pubDocs =sourceDoc.getPublications (PublicationFilter.uniqueTarget, mimeTypes, (String[])null);
         //check to see there are publications
         if ( pubDocs.size() > 0 ) {
            //get the first publication document to republish
            Document pubDoc = (Document) pubDocs.get(0);
            //construct an XML string to define republished document name
            String publishOpts = new String ("<publishoptions><publicationname>" +
            sourceDoc.getName()+ "Republished </publicationname></publishoptions>");
            //sourceDoc  is the document to republish
            sourceDoc.republish(pubDoc, publishOpts);
            //get and return the name of the rePublished document
            String rePublishedDoc = sourceDoc.getName()+ "_RePublished";
            System.out.println(rePublishedDoc + " is re-published in StudentPublishFolder Folder");

          }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}