import com.filenet.wcm.api.*;

public class MyPublish extends MyCESession {

  public MyPublish() throws Exception {

  }

  public static void main(String[] args) throws Exception {
  // did the user supply enough arguments?
     if (args.length < 2)
    {
        System.out.println("usage: run MyPublish <document name> <PublishTemplate Name>");
        System.exit(0);
    }

    try {
        MyPublish myInstance = new MyPublish();
        String docName = args[0];
        String templateName = args[1];

        // declare and initialize a MimeTypes type variable by calling its constructor
        MimeTypes mimeTypes = new MimeTypes();
        //get the objectstore
        ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
        //get the document to be published
        Document doc = (Document)objectStore.getObject(BaseObject.TYPE_DOCUMENT, docName);
        //get all the publish templates assigned to this document
        PublishTemplates publishTemplates = doc.getPublishTemplates(mimeTypes,(String[])null);
        //filter the templates by template name
        publishTemplates = (PublishTemplates)publishTemplates.filterByProperty(
                                                            Property.DOCUMENT_TITLE,
                                                            PublishTemplates.IS_EQUAL,
                                                            templateName);
        //get the first template from the collection
        PublishTemplate btTemplate = (PublishTemplate) publishTemplates.get(0);
        //construct an XML string for the name of the published document
        String option = "<publishoptions><publicationname>"+  doc.getName()+
	                     "_Publication</publicationname></publishoptions>";
        //publish the document
        doc.publish(btTemplate, option);
        //get and return the name of the published document
        String publishedDoc = doc.getName()+ "_Publication";
        System.out.println(publishedDoc + " is Published in StudentPublish Folder");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}