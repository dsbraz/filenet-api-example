
import java.util.Iterator;

import com.filenet.wcm.api.*;

public class ShowUsersGroups extends MyCESession {

  public ShowUsersGroups() throws Exception {
  }


  public static void main(String[] args) throws Exception {
	try {

	  ShowUsersGroups myInstance = new ShowUsersGroups();

		ObjectStore objectStore = ObjectFactory.getObjectStore("Magellan", myCESession);
		EntireNetwork entireNetwork = ObjectFactory.getEntireNetwork(myCESession);
		Realm realm = entireNetwork.getUserRealm();
		String pattern = "m";
		Groups groups = realm.findGroups(pattern, Realm.PRINCIPAL_SEARCH_TYPE_PREFIX_MATCH, Realm.PRINCIPAL_SEARCH_ATTR_SHORT_NAME, Realm.PRINCIPAL_SEARCH_SORT_NONE, 0);
		com.filenet.wcm.api.Group group;
		Iterator groupsIt = groups.iterator();
		String[] props = {Property.DISPLAY_NAME, Property.DISTINGUISHED_NAME, Property.SHORT_NAME}; 
		System.out.println("Display Name \t\t\t Short Name\t\t Distinguished name" );		
		while (groupsIt.hasNext())
		{
			group = (Group)groupsIt.next();
			group.getProperties(props);
			System.out.println(group.getPropertyStringValue(Property.DISPLAY_NAME) + "\t\t\t" + 
            group.getPropertyStringValue(Property.SHORT_NAME) + "\t\t" + 
			group.getPropertyStringValue(Property.DISTINGUISHED_NAME));
		} 
		
		Users users = realm.findUsers(pattern, Realm.PRINCIPAL_SEARCH_TYPE_PREFIX_MATCH, Realm.PRINCIPAL_SEARCH_ATTR_SHORT_NAME, Realm.PRINCIPAL_SEARCH_SORT_NONE, 0);
		com.filenet.wcm.api.User user;
		Iterator usersIt = users.iterator();
	
		System.out.println("\n\nDisplay Name \t\t\t Short Name\t\t Distinguished name\t\tPrincipal Name" );		
		while (usersIt.hasNext())
		{
			user = (User)usersIt.next();
			user.getProperties(props);
			System.out.println(user.getPropertyStringValue(Property.DISPLAY_NAME) + "\t\t\t" + 
			user.getPropertyStringValue(Property.SHORT_NAME) + "\t\t" + 
			user.getPropertyStringValue(Property.DISTINGUISHED_NAME) + "\t\t" + 
			user.getName());
		} 		
	}
	catch (Exception e) {
	  e.printStackTrace();
	}
  }
}