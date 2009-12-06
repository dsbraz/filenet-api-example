import filenet.vw.api.*;

public class Logon {
    VWSession session;

    public static void main(String[] args) {
        Logon logon1 = new Logon();
        logon1.logoff();
	
    } // public static void main(String[] args)

    public Logon() {
        String user = "luca";  // Enter your username
        String password = "Jtull101";
        String server = "s01web05";  // Enter the server name
        String port = "32771"; // Enter the port number
        String router = "vwrouter";  // Usually vwrouter, but can be changed
        String routerURL = "rmi://" + server + ":" + port + "/" + router;
        try {
            session = new VWSession(user, password, routerURL);
            System.out.println( "Logon Done." );
		
        }
        catch (VWException vwe) {
            System.out.println( "Logon Error:" + vwe.getMessage() );
        }
    } // public Logon()

    public boolean logoff() {
        try {
            session.logoff();
            System.out.println( "logoff Done." );
            return true;
        }
        catch (VWException vwe) {
            System.out.println( "logoff Error:" + vwe.getMessage() );
            return false;
        }
    } // public boolean logoff()
} // public class Logon
