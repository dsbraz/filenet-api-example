import com.filenet.wcm.api.*;

public class MyAdhocSearch extends MyCESession  {

  public MyAdhocSearch() throws Exception {
  }

  public static void main(String[] args)
  throws Exception
  {
        MyAdhocSearch myInstance = new MyAdhocSearch();
        String adhocSearchXML = "<request>"+
"<objectstores mergeoption=\"union\"> <objectstore id=\"Magellan\"/></objectstores>"+ "<querystatement>"+
"SELECT DocumentTitle, DateCreated FROM Document WHERE IsCurrentVersion=true"+
"</querystatement>"+ "<options maxrecords=\"100\"/>"+  "</request> ";
        Search searchObject = ObjectFactory.getSearch(myCESession);
        String searchResult = searchObject.executeXML(adhocSearchXML);
        System.out.println(searchResult);
  }

}
