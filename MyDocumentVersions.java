import java.io.FileInputStream;
import com.filenet.wcm.api.*;

public class MyDocumentVersions extends MyCESession {

  public MyDocumentVersions() {
  }

  /**
  * LAB : Main method to execute versioning related API calls
  *
  * 1. Create an instance of MyDocumentVersions class
  *
  * 2. Write code to create a major version of a document. You may copy the code from MyAddDocument.java
  *
  *   Get empty Properties collection
  *   Get Property object for the Property.DOCUMENT_TITLE
  *   Set the Property object value using docTitle argument
  *   Add the Property object to the Properties collection
  *   Get Content Engine Session object. Use the variable 'myCESession'
  *   Get ObjectStore object
  *   Create a DOCUMENT object with the Properties collection created above.
  *   Get the Folder object specified by the folderName argument
  *   File the document into the Folder object
  *   Create an instance of TransportInputStream object with the file content specified by the fileName argument
  *   Set document content with checkin parameter set to true to create a major version
  *
  * 3. Write code to retrieve and risplay the document's current version numbers.
  *   Call ObjectStore object's getObject method to retreive the Document object specified by the document path.
  *   Get document's VersionSeries object
  *   Retrieve the current version document using the VersionSeries object.
  *   Retrieve document's MAJOR_VERSION_NUMBER property value
  *   Retrieve document's MINOR_VERSION_NUMBER property value
  *   Display the version numbers.
  *
  * 4. Write code to check-in the document's minor version
  *   Obtain the reservation document by calling checkOut method on the currentVersion document.
  *   Create an instance of TransportInputStream object with the file content specified by the fileName argument
  *   Set reservation document content with checkin parameter set to false
  *   Call reservation document's checkin method with the minorVersion parameter set to true
  *
  * 5. Repeat step 3 to retrieve and risplay the document's current version numbers.
  *
  * 6. Write code to promote the document version to VERSION_STATUS_RELEASED state.
  *   If the document's IS_CURRENT_VERSION propery value is true and
  *   If the document's VERSION_STATUS property value is equal to VersionableObject.VERSION_STATUS_IN_PROCESS
  *   Call current version document's promoteVersion method to promote the document to release state.
  *
  * 7. Repeat step 3 to retrieve and risplay the document's current version numbers.
  *
  * 8. Write code to demote the document version to VERSION_STATUS_IN_PROCESS state.
  *   If the document's IS_CURRENT_VERSION propery value is true and
  *   If the document's VERSION_STATUS property value is equal to VersionableObject.VERSION_STATUS_RELEASED and
  *   If the document's IS_RESERVED property value false
  *   Call document's demoteVersion method to demote the document to in-process state
  *
  * 9. Repeat step 3 to retrieve and risplay the document's current version numbers.
  *
  * 10. Write code to retrieve the document's release version
  *     Use document's VersionSeries object to Retreive release version document
  *     Retrieve release version document's MAJOR_VERSION_NUMBER property value
  *     Retrieve release version document's MINOR_VERSION_NUMBER property value
  *     Display the release version numbers.
  *
  * 11. Repeat step 6 to promote the document.
  * 12. Repeat steps 3 and 10 to diplay current version and release version numbers.
  *
  *
  *  @param docTitle the document title for the new document
  *  @param folderName the name of the folder to file the new document
  *  @param fileName the file name of the document content
  */

  public static void main(String[] args) throws Exception {
    if(args.length < 3)
    {
        System.out.println("Usage: run MyDocumentVersions <DocTitle> <FolderPath> <FileName>");
        System.exit(-1);
    }

    String docTitle = args[0];
    String folderName = args[1];
    String fileName = args[2];

    String documentPath = folderName+"/"+docTitle;

    MyDocumentVersions myDocVersions = new MyDocumentVersions();

      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
      Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderName);
      document.file(folder, false);
      TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
      // setting the checkin boolean value to true will check-in the document as a major version
      document.setContent(content, true, false);

    System.out.println("Created document's major version");

      Document myDocument = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,documentPath);
      VersionSeries vs = myDocument.getVersionSeries();
      Document currentVersion = vs.getCurrentVersion();
      String majorVersion = currentVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      String minorVersion = currentVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Current Document Version : "+majorVersion+"."+minorVersion);

      Document reservation = currentVersion.checkout();
      content = new TransportInputStream(new FileInputStream(fileName));
      reservation.setContent(content, false, false);
      reservation.checkin(false, true);
      System.out.println("checked-in document's minor version");

      myDocument = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,documentPath);
      vs = myDocument.getVersionSeries();
      currentVersion = vs.getCurrentVersion();
      majorVersion = currentVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      minorVersion = currentVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Current Document Version : "+majorVersion+"."+minorVersion);

      if (((currentVersion.getPropertyBooleanValue(Property.IS_CURRENT_VERSION)) == true)
      && ((currentVersion.getPropertyIntValue(Property.VERSION_STATUS)) == VersionableObject.VERSION_STATUS_IN_PROCESS))
      {
          currentVersion.promoteVersion();
          System.out.println("Document promoted to a released version");
      }
      else
      {
          System.out.println("Document cannot be promoted. Check version status.");
      }

      myDocument = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,documentPath);
      vs = myDocument.getVersionSeries();
      currentVersion = vs.getCurrentVersion();
      majorVersion = currentVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      minorVersion = currentVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Current Document Version : "+majorVersion+"."+minorVersion);

      if (((currentVersion.getPropertyBooleanValue(Property.IS_CURRENT_VERSION)) == true)
      && ((currentVersion.getPropertyIntValue(Property.VERSION_STATUS)) == VersionableObject.VERSION_STATUS_RELEASED)
      && ((currentVersion.getPropertyBooleanValue(Property.IS_RESERVED)) == false))
      {
          currentVersion.demoteVersion();
          System.out.println("Document demoted to a version in process");
      }
      else
      {
          System.out.println("Document cannot be demoted. Check version status.");
      }


      myDocument = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,documentPath);
      vs = myDocument.getVersionSeries();
      currentVersion = vs.getCurrentVersion();
      majorVersion = currentVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      minorVersion = currentVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Current Document Version : "+majorVersion+"."+minorVersion);

      Document releaseVersion = vs.getReleasedVersion();
      majorVersion = releaseVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      minorVersion = releaseVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Document's Release Version : "+majorVersion+"."+minorVersion);

      if (((currentVersion.getPropertyBooleanValue(Property.IS_CURRENT_VERSION)) == true)
      && ((currentVersion.getPropertyIntValue(Property.VERSION_STATUS)) == VersionableObject.VERSION_STATUS_IN_PROCESS))
      {
          currentVersion.promoteVersion();
          System.out.println("Document promoted to a released version");
      }
      else
      {
          System.out.println("Document cannot be promoted. Check version status.");
      }

      myDocument = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,documentPath);
      vs = myDocument.getVersionSeries();
      currentVersion = vs.getCurrentVersion();
      majorVersion = currentVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      minorVersion = currentVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Current Document Version : "+majorVersion+"."+minorVersion);

      releaseVersion = vs.getReleasedVersion();
      majorVersion = releaseVersion.getPropertyStringValue(Property.MAJOR_VERSION_NUMBER);
      minorVersion = releaseVersion.getPropertyStringValue(Property.MINOR_VERSION_NUMBER);
      System.out.println("Document's Release Version : "+majorVersion+"."+minorVersion);
  }
}