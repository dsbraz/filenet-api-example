import com.filenet.wcm.api.*;

public class MyModifyProperties extends MyCESession {

  public MyModifyProperties() throws Exception  {
  }


  /**
  * Lab: Write a main method to modify custom object properties
  *
  * Obtain a content engine Session object; Use the variable "myCESession"
  * Retrieve the custom object specified by the custom object name
  * Obtain an empty Properties collection object from ObjectFactory
  * Obtain the Property object from the ObjectFactory for a known property name
  * Set the Property object value
  * Add the Property object to the Properties collection
  * Set the custom object properties
  *
  * Verification:
  * Call CustomObject.getPropertyStringValue method with the property name as the parameter
  * Display the property value.
  *
  * @param args the array containing custom object name
  */
  public static void main(String[] args)
  throws Exception
  {
    if(args.length < 2)
    {
      System.out.println("MyModifyProperties usage: run MyModifyProperties <CustomObjectPath> <new Value>");
      System.exit(0);
    }
    try
    {
      MyModifyProperties myInstance = new MyModifyProperties();
      String objectName = args[0];
	  String newValue = args[1];      
      ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
      CustomObject customObject = (CustomObject) objectStore.getObject(BaseObject.TYPE_CUSTOMOBJECT, objectName);
      Properties props = ObjectFactory.getProperties();
      Property prop = ObjectFactory.getProperty("agent_name");
      prop.setValue(newValue);
      props.add(prop);
      customObject.setProperties(props);

      String value = customObject.getPropertyStringValue("agent_name");
     System.out.println("Modified Property Value = "+value);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

}
