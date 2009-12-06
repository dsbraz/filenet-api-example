import com.filenet.wcm.api.*;

public class MyBatch extends MyCESession {

  public MyBatch() throws Exception {
  }


  /*
  * Lab: Write main method to perform the batch operation.
  * The goal of this batch program is to promote all the documents participating in a life cycle contained in the specified folder.
  *
  *  Obtain the content engine Session object. Use "myCESession" variable.
  *  Obtain the ObjectStore object
  *  Retrieve the Folder object specified by the folderId argument
  *  Retrieve all the Documents in the folder
  *  Wait for a few seconds if the Session is in Batch. Continue to wait till the session is not in Batch.
  *  Start Batch. transaction, discardResults and stopOnException flags to false
  *  For each Document in the Document collection
  *       1. Get the document name
  *       2. set batch item label to a unique name, ex: "DocumentName"+index, where index is a counter value.
  *       3. Get the document property value for the property CURRENT_STATE
  *       4. set batch item label to a unique name, ex: "BeforePromotion"+index, where index is a counter value.
  *       5. promote the document lifecycle state
  *       6. Get the document property value for the property CURRENT_STATE
  *       7. set batch item label to a unique name, ex: "AfterPromotion"+index, where index is a counter value.
  *  Obtain the BatchResultItems collection by executing the batch
  *  Extract the BatchResultItem using the previously used unique label. Ex: "DocumentName"+index
  *  Display the result values for document name, document lifecycle states before and after promotion.
  *
  *  @param folderId the folder path for the batch operation
  */
  public static void main(String[] args) throws Exception {
    try {
      if(args.length < 1)
      {
        System.out.println("Usage: run MyBatch <FolderPath>");
        System.exit(-1);
      }
      MyBatch myInstance = new MyBatch();

      String folderId = args[0];
    ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
    Folder folder = (Folder) objectStore.getObject(BaseObject.TYPE_FOLDER, folderId);

    Documents docs = (Documents) folder.getContainees(new int[]{BaseObject.TYPE_DOCUMENT});
    // wait if another session is already performing ExecuteBatch operations
    /*
    while (myCESession.isInBatch()) {
      wait(5000);
    }
    */
    myCESession.startBatch(false, false, false);
    java.util.Iterator iter = docs.iterator();
    int index = 0;
    while(iter.hasNext()) {
      index++;
      Document doc = (Document) iter.next();
      String docName = doc.getName();
      myCESession.setBatchItemLabel("DocumentName"+index);
      doc.getPropertyStringValue(Property.CURRENT_STATE);
      myCESession.setBatchItemLabel("Before_Promotion"+index);
      doc.changeLifeCycleState(Document.LIFECYCLE_PROMOTE);
      doc.getPropertyStringValue(Property.CURRENT_STATE);
      myCESession.setBatchItemLabel("After_Promotion"+index);
    }

    BatchResultItems resultItems = myCESession.executeBatch();

    BatchResultItem result = null;
    for(int item=1; item <= index; item++)
    {
      result = resultItems.getItemByLabel("DocumentName"+item);
      System.out.println("Document Name "+item+" : "+(String)result.getResult());
      result = resultItems.getItemByLabel("Before_Promotion"+item);
      System.out.println("Before Promotion "+" : "+(String)result.getResult());
      result = resultItems.getItemByLabel("After_Promotion"+item);
      System.out.println("After Promotion "+" : "+(String)result.getResult());
    }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}