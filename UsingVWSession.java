import filenet.vw.api.*;

public class UsingVWSession {
    public static void main(String[] args) {
        // declare variables (listed in order as used)
        Logon logon1;
        VWSession session;
        VWUserInfo userInfo;
        VWSecurityList users;
        String userName;
        int userID;
        VWSecurityList groups;
        String groupName;
        int groupID;

        // Perform logon
        logon1 = new Logon();

        try {
            // Retrieve VWSession and display some info
            session = logon1.session;

            //Retrieve and Display current user info
            userInfo = session.fetchCurrentUserInfo();
            System.out.println( "Current User: " + userInfo.getName() );
            System.out.println( "email: " + userInfo.getEMailAddress() );
            System.out.println( "Proxy: " + userInfo.getProxyUserName() );
            System.out.println();

            //Retrieve and Display Users with eProcess internal ID number
            System.out.println( "User Name (ID)" );
            users = session.fetchUsers(200,false);
            while (users.hasNext()) {
                userName = (String) users.next();
                userID = session.convertUserNameToId(userName);
                System.out.println( userName + " (" + userID + ")");
            }
            System.out.println();


            //Retrieve and Display Groups with eProcess internal ID number
            System.out.println( "Group Name (ID)" );
            groups = session.fetchUserGroups(100);
            while (groups.hasNext()) {
                groupName = (String) groups.next();
                groupID = session.convertUserNameToId(groupName);
                System.out.println( groupName + " (" + groupID + ")" );
            }
            System.out.println();

        }
        catch (VWException vwe) {
            System.out.println( "Using VWSession Error: " + vwe.getMessage() );
        }
        logon1.logoff();

    }  // public static void main(String[] args)
}  // public class UsingVWSession
