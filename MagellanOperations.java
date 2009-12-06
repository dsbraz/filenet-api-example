/*
 * MagellanOperations.java
 */

import java.io.*;
import com.filenet.wcm.api.*;
import com.filenet.wcm.api.Session;
import com.filenet.wcm.toolkit.server.operations.util.opsUtil;
//import com.filenet.wcm.toolkit.util.*;
import filenet.vw.api.*;

public class MagellanOperations {

    java.io.BufferedWriter letWriter;
    static Session myCESession = null;
    String letter = null;
    PrintStream myLog = null;
    String logName = "c:/Magellan/Logs/MagellanOperations.log";

    public MagellanOperations() {
/*
    	try {
            FileOutputStream fout =  new FileOutputStream(logName, true);
            myLog = new PrintStream(fout);
            myLog.println("MagellanOperations initialized");
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
 */
    }

 public void createLetterDoc (String letter_type, String customer_id, String reference_id, String full_name, String letter_text) throws Exception{
 letter = "Dear "+full_name+"\n\n"+
            "Thank you for doing business with us at Magellan World Cruises.\n"+
            letter_text+"\n\n"+
            "Sincerely,\n"+
            "The Magellan Staff";
             addDocCE(customer_id,full_name, letter_type, reference_id);
    }

 public VWAttachment createCEFolderAttachment(String libraryName, String folderPath) throws Exception  {
 	opsUtil ops = new com.filenet.wcm.toolkit.server.operations.util.opsUtil();
	Session myCESession = ops.getSession();
 	ObjectStore objectStore = ObjectFactory.getObjectStore(libraryName, myCESession);
	Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderPath);
	VWAttachment folderAttachment = new VWAttachment();
 	folderAttachment.setLibraryType(VWLibraryType.LIBRARY_TYPE_CONTENT_ENGINE);
 	folderAttachment.setLibraryName(libraryName);
 	folderAttachment.setAttachmentDescription("Customer Travel Documents");
	folderAttachment.setAttachmentName(folder.getName());
 	folderAttachment.setType(VWAttachmentType.ATTACHMENT_TYPE_FOLDER);
 	folderAttachment.setId(folder.getId());
 	return folderAttachment;
 }

 public void fileDocument(VWAttachment attachment, String folderName) throws Exception  {
	opsUtil ops = new com.filenet.wcm.toolkit.server.operations.util.opsUtil();
	Session myCESession = ops.getSession();
	String libraryName = attachment.getLibraryName();
	ObjectStore objectStore = ObjectFactory.getObjectStore(libraryName, myCESession);
	Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderName);
	String versionSeriesId = attachment.getId();
	VersionSeries versionSeries = (VersionSeries)objectStore.getObject(BaseObject.TYPE_VERSIONSERIES, versionSeriesId);
	Document document = versionSeries.getCurrentVersion();
	document.file(folder, false);
 }

 /**
    * this method creates/adds a doc (Letter type) to the folder  "Customers/" + customerId + "/CustomerDocuments"
    * The content will be the document created from the printLetter method
    *
    **/
 private  void addDocCE(String customerId, String customerName, String letter_type, String reference_id) throws Exception {
    String docTitle = letter_type + " letter for " + reference_id;
    String folderName = "/Customers/" + customerId + "/CustomerDocuments";

    try {
      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property docProp =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      docProp.setValue(docTitle);
      properties.add(docProp);
      Property cusProp = ObjectFactory.getProperty("customer_id");
      cusProp.setValue(customerId);
      properties.add(cusProp);
      Property cusNameProp = ObjectFactory.getProperty("customer_name");
      cusProp.setValue(customerName);
      properties.add(cusNameProp);
      opsUtil ops = new com.filenet.wcm.toolkit.server.operations.util.opsUtil();
      Session myCESession = ops.getSession();
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document document = (Document) objectStore.createObject("Letter", properties, null);
      Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderName);
      document.file(folder, false);
      TransportInputStream content = new TransportInputStream
      (new ByteArrayInputStream(letter.getBytes()));
      document.setContent(content, false, false);
      document.checkin(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) throws Exception
  {
        MagellanOperations me = new MagellanOperations();
        me.createLetterDoc("Reservation", "mmouse", "mmouse0123101", "Mickey Mouse", "hello");
  }

}
