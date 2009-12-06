
import com.filenet.wcm.api.*;
import java.util.*;

public class MyCopyAnnotations extends MyCESession {

  public MyCopyAnnotations() throws Exception {
  }


   public static void main(String[] args) 
  {
	try 
	{
	  if(args.length < 2)
	  {
		System.out.println("Usage: run MyCopyAnnotations <DocPath> <version>");
		System.exit(-1);
	  }
	  MyCopyAnnotations myInstance = new MyCopyAnnotations();
	  String docPath = args[0];
      String version = args[1];
      int intVersion = new Integer(version).intValue();
	  ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
	  Document doc = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT,docPath);
      
	  Documents versions = doc.getVersions();
	  Document  doc1 = null;
	  for (int i = 0; i < versions.size(); i++)
	  {  
	  	 doc1 = (Document)versions.get(i);
	     int versionNumber = doc1.getPropertyIntValue(Property.MAJOR_VERSION_NUMBER);
	     if (versionNumber == intVersion)
	     	break;
	  }
	  
	  Iterator it;
	  Values annotProp = doc1.getPropertyValuesValue(Property.ANNOTATIONS);

	  if (annotProp != null)
	  {
		it = annotProp.iterator();
		while (it.hasNext())
		{
			  Value val = (Value)it.next();
			  Annotation note = (Annotation)val.getValue();

			  com.filenet.wcm.api.Properties props = ObjectFactory.getProperties();
			  Property prop = ObjectFactory.getProperty(Property.ANNOTATED_OBJECT);
			  prop.setValue(doc);
			  props.add(prop);

			  prop = ObjectFactory.getProperty(Property.ANNOTATED_CONTENT_ELEMENT);
			  prop.setValue(1);
			  props.add(prop);
                        
			  Annotation newNote = (Annotation)objectStore.createObject(ClassDescription.ANNOTATION, 
										props, note.getPermissions());
			  if (newNote != null)
				  newNote.setContent(note.getContent());
			  System.out.println("Annotation copied");
		}  // while
	}  // if
	  }  // try
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	  } // main
}

