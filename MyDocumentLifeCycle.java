import com.filenet.wcm.api.*;

public class MyDocumentLifeCycle extends MyCESession {

  public MyDocumentLifeCycle() throws Exception {}

  /** LAB: Write a main method to perform document lifecycle operations
  *
  * Get content engine Session. Use 'myCESession' variable
  * Get the ObjectStore object
  * Get the Document object specified by the documentPath parameter
  * Verify that the document has an associated lifecycle policy by retrieving the CURRENT_STATE property value
  * Call Document's getLifeCycleStates method to retrieve and display all states defined in the lifecycle policy
  * Call Document's changeLifeCycleState with LIFECYCLE_PROMOTE or LIFECYCLE_DEMOTE parameters based on the specified action parameter
  * Retrieve and display the CURRENT_STATE property value after each operation
  *
  * @param documentPath the document path
  * @param action the action string : promote | demote
  */

  public static void main(String[] args) throws Exception
  {
      if(args.length < 2)
      {
        System.out.println("Usage: run MyDocumentLifeCycle <Document Path> <promote|demote>");
        System.exit(0);
      }

    try
    {
        MyDocumentLifeCycle myInstance = new MyDocumentLifeCycle();
      String documentPath = args[0];
      String action = args[1];

      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      Document document = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,documentPath);
      // Verify if the document has an associated lifecycle policy
      String currentState = document.getPropertyStringValue(Property.CURRENT_STATE);
      if(currentState == null)
      {
        System.out.println("The document does not have a lifecycle policy. Try again.");
        System.exit(0);
      }
      // Get document's lifecycle states
      String[] states = document.getLifeCycleStates();
      System.out.println("Document lifecycle states are:");
      for(int i = 0; i < states.length; i++)
        System.out.println(states[i]);
      System.out.println("Current Document LifeCycle State = "+currentState);

      if(action.equalsIgnoreCase("promote"))
      {
        // promote document's lifecycle state
        document.changeLifeCycleState(Document.LIFECYCLE_PROMOTE);
        currentState = document.getPropertyStringValue(Property.CURRENT_STATE);
        System.out.println("Document promoted to "+currentState);
      }
      //verify if the document can be demoted and demote document's lifecycle state
      else if( document.canDemoteLifeCycleState() == true)
      {
        document.changeLifeCycleState(Document.LIFECYCLE_DEMOTE);
        currentState = document.getPropertyStringValue(Property.CURRENT_STATE);
        System.out.println("Document demoted to "+currentState);
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}