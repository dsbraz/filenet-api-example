import filenet.vw.api.*;

public class QueryRoster {
    public static void main(String[] args) {
        // declare variables (listed in order as used)
        Logon logon1;
        VWSession session;
        VWRoster roster;
        int queryFlags;
        String queryFilter;
        int queryType;
        VWRosterQuery query;
        int counter;
        VWRosterElement rosterItem;
        VWWorkObject workObject;

        // Perform logon
        logon1 = new Logon();

        // Retrieve VWSession
        session = logon1.session;

        try {
            // Retrieve all roster work items
            roster = session.getRoster("OrderRoster");
            queryFlags=VWRoster.QUERY_NO_OPTIONS;
            queryType=VWFetchType.FETCH_TYPE_ROSTER_ELEMENT;
            query = roster.createQuery(null,null,null,queryFlags,null, null,queryType);
            System.out.println("Roster Element Count: "+query.fetchCount());
            counter = 0;
            while(query.hasNext() && counter<20) {
                rosterItem= (VWRosterElement) query.next();
                System.out.println(++counter);
                System.out.println("WF  Number: "+rosterItem.getWorkflowNumber() );
                System.out.println("WOB Number: "+rosterItem.getWorkObjectNumber());
                System.out.println("F_StartTime: " + rosterItem.getFieldValue("F_StartTime") );
                System.out.println("F_Subject: " + rosterItem.getFieldValue("F_Subject") );
                System.out.println();
            }
            System.out.println();

            // Retrieve workflows
            roster = session.getRoster("OrderRoster");
            queryFlags=VWRoster.QUERY_NO_OPTIONS;
            queryFilter="F_WobNum=F_WorkFlowNumber";
            queryType=VWFetchType.FETCH_TYPE_ROSTER_ELEMENT;
            query = roster.createQuery(null,null,null,queryFlags,queryFilter, null,queryType);
            System.out.println("Workflow Count: "+query.fetchCount());
            counter = 0;
            while(query.hasNext() && counter<20) {
                rosterItem= (VWRosterElement) query.next();
                System.out.println(++counter);
                System.out.println("WF  Number: "+rosterItem.getWorkflowNumber() );
                System.out.println("WOB Number: "+rosterItem.getWorkObjectNumber());
                System.out.println("F_StartTime: " + rosterItem.getFieldValue("F_StartTime") );
                System.out.println("F_Subject: " + rosterItem.getFieldValue("F_Subject") );
                System.out.println();
            }
            System.out.println();

        }
        catch (VWException vwe) {
            System.out.println( "VWException Error: " + vwe.getMessage() );
        }

        logon1.logoff();
  System.exit(1);

    } // public static void main(String[] args)
} // public class QueryRoster
