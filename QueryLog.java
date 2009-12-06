import java.io.*;
import java.text.*;

import filenet.vw.api.*;

public class QueryLog {
    public static void main(String[] args) {
        // declare variables (listed in order as used)
        Logon logon1;
        VWSession session;
        String[] eventLogNames;
        int i;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        NumberFormat nf = NumberFormat.getInstance();
        String eventLogName;
        int eventLogNum;
        VWLog log;
        int queryFlags;
        VWLogQuery query;
        int counter;
        String[] fieldNames;
        VWLogElement logElement;

        // Perform logon
        logon1 = new Logon();

        // Retrieve VWSessionThe output is same as the previous exercise, but notice that workitems retrieved belong to one useId only, matching the filter criteria used.
        session = logon1.session;

        try {
            // List Event Logs and select
            System.out.println( "Event Logs" );
            eventLogNames = session.fetchEventLogNames();
            for (i=0;i<eventLogNames.length;i++) {
                System.out.println( i+": " + eventLogNames[i] );
            }
            System.out.print("Select Event Log (by number): ");
            eventLogNum = nf.parse(in.readLine()).intValue();
            eventLogName = eventLogNames[eventLogNum];


            // To skip the selection, use the line below
            // eventLogName = "DefaultEventLog";


            // Retrieve the desired Event Log
            log = session.fetchEventLog(eventLogName);

            queryFlags=VWLog.QUERY_NO_OPTIONS;
            query = log.startQuery(null,null,null,queryFlags,null,null);
            System.out.println("Log Element Count: "+query.fetchCount());
            counter = 0;
            while(query.hasNext() && counter < 10) {
                logElement = (VWLogElement) query.next();
                counter++;
                System.out.println(counter + ") Log Type: " + logElement.getEventType() + VWLoggingOptionType.getLocalizedString(logElement.getEventType()) );
                fieldNames = logElement.getFieldNames();
                for(i=0;i<fieldNames.length;i++) {
                    System.out.println(fieldNames[i]+": " + logElement.getFieldValue(fieldNames[i]));
                }
                System.out.println();
            }
            System.out.println();

        }
        catch (VWException vwe) {
            System.out.println( "VWException Error: " + vwe.getMessage() );
        }
        catch (IOException ioe) {
            System.out.println( "IOException Error: " + ioe.getMessage() );
        }
        catch (ParseException pe) {
            System.out.println( "ParseException Error: " + pe.getMessage() );
        }

        logon1.logoff();
 System.exit(1);

    }  // public static void main(String[] args)
}  // public class QueryLog
