import java.io.FileInputStream;
import java.util.HashMap;
import com.filenet.wcm.api.*;

 /*
    This class helps to obtain a Content Engine Session
 */

public class MyCESession {
  static Session myCESession;

  public MyCESession()
  {
    try {
     myCESession = ObjectFactory.getSession("MyApp", Session.DEFAULT, "LUCA", "xxxx");
     myCESession.setConfiguration(new FileInputStream ("WcmApiConfig.properties"));
     myCESession.verify();
	 System.out.println("Logon no Content......");
     
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void setMyCESessionEnv (Session session, String contentEngineUrl)
  throws Exception
  {
    session.setRemoteServerUrl(contentEngineUrl + "xcmisasoap.dll");
    session.setRemoteServerUploadUrl(contentEngineUrl + "doccontent.dll");
    session.setRemoteServerDownloadUrl(contentEngineUrl + "doccontent.dll");
  }

  public static void getMySessionInfo()
  {
    String token = myCESession.getToken();
    HashMap cred = myCESession.fromToken(token);

    String appId = (String) cred.get(Session.APPID);
    String userId = (String) cred.get(Session.USERID);
    String password = (String) cred.get(Session.PASSWORD);
    String time = (String) cred.get(Session.DATETIME);

    System.out.println("AppId = "+appId);
    System.out.println("User = "+userId);
    System.out.println("Password = "+password);
    System.out.println("Time = "+ time);

  }

 /**
  * LAB: Write a main method to obtain a content engine session and display the session Info.
  *
  *  Obtain a Session object from the ObjectFactory using appId, credTag, userId and password parameters
  *  Set Session configuration with WcmAPIConfig.properties file content
  *  verify the session
  *
  *  Get the session token string
  *  Get the credentials from the token with the session token as the parameter
  *  Retrieve and display the APPID, USERID, PASSWORD, DATETIME field values from the credentials map object
  *
  *  @param appId the application identifier string
  *  @param credTag the protection level for the credentials
  *  @param userId the user id string
  *  @param password the password string
 */
  public static void main(String[] args) throws Exception
  {
      if(args.length < 2)
      {
        System.out.println("Usage: run MyCESession <userId> <password>");
        System.exit(0);
      }
      try {
        String userId = args[0];
        String password = args[1]; 
        Session session = ObjectFactory.getSession("MyAppId", Session.DEFAULT, userId, password);
        session.setConfiguration(new FileInputStream ("WcmApiConfig.properties"));
        session.verify();
      /** Get Session Information **/
        String token = session.getToken();
        HashMap cred = session.fromToken(token);

        String appId = (String) cred.get(Session.APPID);
        userId = (String) cred.get(Session.USERID);
        password = (String) cred.get(Session.PASSWORD);
        String time = (String) cred.get(Session.DATETIME);

        System.out.println("AppId = "+appId);
        System.out.println("User = "+userId);
        System.out.println("Password = "+password);
        System.out.println("Time = "+ time);
      } catch (Exception e)
      {
        e.printStackTrace();
      }
  }
}
