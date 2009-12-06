import com.filenet.wcm.api.*;
import java.io.*;

public class MySearch extends MyCESession {

  public MySearch() throws Exception {
  }


  public static void main(String[] args)
  {
   if (args.length < 2)
    {
        System.out.println("usage: run MySearch <SearchTemplate Name> <key word>");
        System.exit(1);
    }
  try {
      MySearch myInstance = new MySearch(); 
	String searchTemplate = args[0];
      String keyword = args[1];
        //get the objectStore
        ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
         //get the stored search template
        StoredSearch ss = (StoredSearch)objectStore.getObject(BaseObject.TYPE_STORED_SEARCH, searchTemplate);
        //calling this method, builds the query with the keyword supplied
	String searchQuery = getMySearchQuery(keyword);
        //execute the query
	String searchResult = ss.executeXML(searchQuery);
        System.out.println(searchResult);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

 /**
  *  Method to build the XML query
  *
  *  @param keyword the keyword to be used in the Query.
  */

 public static String getMySearchQuery(String keyword)
  throws Exception {

String query = "<executedata  xmlns=\"http://filenet.com/namespaces/wcm/apps/1.0\">" +
  "<version dtd=\"3.0\"/>" +
    "<objecttypesdata>" +
      "<objecttypedata>" +
        "<from><class symname=\"document\" /></from>" +
      	"<templatedata>" +
        "<templatepropitems>" +
          "<templateitem itemid=\"5\">" +
            "<templateitemdata>%" + keyword + "%</templateitemdata>" +
          "</templateitem>" +
        "</templatepropitems>" +
      "</templatedata>" +
    "</objecttypedata>" + 
  "</objecttypesdata>" +
"</executedata>";
      return query;
  }
}


