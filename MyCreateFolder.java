import java.util.Iterator;

import com.filenet.wcm.api.*;

public class MyCreateFolder extends MyCESession {

  public MyCreateFolder() throws Exception {
  }


 /**
  * Write main method to create sub-folders within a folder
  *
  * Obtain a content engine Session object; use myCESession variable.
  * Obtain the ObjectStore object
  * Obtain the Folder object for the root folder.
  * Add first sub-folder into the root folder. This is the parent folder
  * Add a sub-folder within the parent folder.
  * Retrieve the contained objects within the parent folder
  * Iterate through the contained BaseObjects collection, checking the instance type of the BaseObject. Display the type of the object.
  *
  *
  *  @param parentFolderName the first folder to be added to root folder
  *  @param newFolderName the name of the new sub-folder to be created.
  *
  *
  */
  public static void main(String[] args) {
    try
    {
      if(args.length < 2)
      {
        System.out.println("MyCreateFolder usage: run MyCreateFolder <parentFolderName> <NewFolderName>");
        System.exit(0);
      }
      MyCreateFolder myInstance = new MyCreateFolder();
      ObjectStore objectStore = ObjectFactory.getObjectStore("FN_Desenv", myCESession);

      //Folder rootFolder = objectStore.getRootFolder();
	  Folder aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emissão RIC");
	  
      String parentFolderName = args[0];
      String newFolderName = args[1];
      Folder parentFolder = aFolder.addSubFolder(parentFolderName, null, null);


      Folder  childFolder = parentFolder.addSubFolder(newFolderName, null, null);

      BaseObjects baseObjects = parentFolder.getContainees();
      Iterator it = baseObjects.iterator();
      while (it.hasNext())
      {
        BaseObject baseObject = (BaseObject)it.next();    	  
        String name = baseObject.getName();
        System.out.println("name = "+name);
      }  
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
