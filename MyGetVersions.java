import com.filenet.wcm.api.*;


public class MyGetVersions extends MyCESession {

  public MyGetVersions() throws Exception {
  }


   public static void main(String[] args) {
    try {
      if(args.length < 1)
      {
        System.out.println("Usage: run MyGetVersions <DocPath>");
        System.exit(-1);
      }
      MyGetVersions myInstance = new MyGetVersions();
      String docPath = args[0];

      ObjectStore objectStore = ObjectFactory.getObjectStore("Sample", myCESession);
      Document doc = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,docPath);
      
      Documents versions = doc.getVersions();
      //Document doc1 = (Document)versions.get(0);
       java.util.Iterator docsIt = versions.iterator();
       while (docsIt.hasNext())
       {
       	   doc = (Document)docsIt.next();
       	   System.out.println("Document ID = "  + doc.getId());
       }

     VersionSeries versionSeries = doc.getVersionSeries();
     System.out.println("version series ID = " + versionSeries.getId());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
