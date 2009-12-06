import com.filenet.wcm.api.*;
import java.util.Iterator;

public class MyChoiceList extends MyCESession {

  public MyChoiceList() {
  }


  /**
   *  LAB: Write main method to retrieve choice list properties for a document class
	*
	*  Create an instance of MyChoiceList class.
	*  Get the content engine Session object. Use "myCESession" variable
	*  Get the ObjectStore object
	*  Get the ClassDescriptions collection from the objectstore for Document and CustomObject Types
	*  Get the ClassDescription object for the specified document class
	*  Get the PropertyDescriptions collection from the ClassDescription object
	*  Filter-out SYSTEM_GENERATED PropertyDescriptions from the PropertyDescriptions collection
	*  Get the PropertyDescription object from the PropertyDescriptions collection for each Choice property in the list of property names.
	*  Get the ChoiceList object from the PropertyDescription object
	*
	*
	*
	* Iterate through the ChoiceList collection to display the name and allowed value of the Choice:
	*  Call ChoiceList's getName method and display the ChoiceList name
	*  Iterate through the ChoiceList collection. For each Choice object in the collection:
	*     Call getLabel and getValue methods and display the Choice name and allowed value.
	*     If the Choice object hasContainedChoices
	*       Call getContainedChoices method to get the ChoiceList.
	*
	*  Note: Make sure to check if there are nested ChoiceList objects in the ChoiceList collection
	*
	*  @param className the name of the document class containing the ChoiceList properties. For example: "CruiseOrderDoc", {"payment_type"}
	*  @param propertyNames the array containing the ChoiceList property names
  */
  private void displayChoiceList(ChoiceList choices)
  {
	  Iterator it = choices.iterator();
	  while(it.hasNext())
	  {
		Choice choice = (Choice)it.next();
		//get the display name
		String choiceLabel = choice.getLabel();
		System.out.println("Choice Label = " + choiceLabel);
		//get symbolic name
		String choiceSymName = choice.getName();
		System.out.println("Choice Symbolic Name = " + choiceSymName);

		if(choice.hasContainedChoices())
		{
		  ChoiceList choiceList = choice.getContainedChoices();
		  displayChoiceList(choiceList);
		  // Repeat the same steps to display this choice list information
		}
		else
		{
		  //  get the allowed value for this choice
		  String choiceValue = (String)choice.getValue();
		  System.out.println("Choice value ="+choiceValue);
		}
	  }
  }

  public static void main(String[] args) throws Exception {

	  MyChoiceList myInstance = new MyChoiceList();

	  int[] types = {BaseObject.TYPE_DOCUMENT, BaseObject.TYPE_CUSTOMOBJECT};

	  ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);

	  // get all the class descriptions for this objectstore
	  ClassDescriptions cds = objectStore.getClassDescriptions(types);

	  //get class description  for the specified class
	  ClassDescription cd = (ClassDescription) cds.findByProperty(Property.NAME,
												 ClassDescriptions.IS_EQUAL,
												   "CruiseOrderDoc");

	  //get property descriptions
	  PropertyDescriptions pds = (PropertyDescriptions) cd.getPropertyDescriptions();

	  // filter the system property descriptions
	  pds = (PropertyDescriptions)pds.filterByProperty(Property.IS_SYSTEM_GENERATED, PropertyDescriptions.IS_EQUAL, false);

	  //Find the property description by name.
	  PropertyDescription pd = (PropertyDescription) pds.findByProperty(Property.NAME,
												 PropertyDescriptions.IS_EQUAL,
												  "payment_type");
	  //  get the choice list for this property
		 ChoiceList choices = pd.getChoices(-1);

	  //  get the choice list id
	  System.out.println("ChoiceList id = "+ choices.getId());

	  //  get the choice list name
	  System.out.println("ChoiceList name = "+ choices.getName());
	  myInstance.displayChoiceList(choices );
  }
}
