import filenet.vw.api.*;

public class QueryInbox {
    public static void main(String[] args) {
        // declare variables (listed in order as used)
        Logon logon1;
        VWSession session;
        VWQueue queue;
        VWSecurityList users;
        String userName;
        int userID;
        String queryFilter;
        int queryFlag;
        int queryType;
        VWQueueQuery query;
        int counter;
        VWQueueElement queueElement;
        String wob = new String();
        String queryIndex;
        Object[] queryMin = new Object[5];
        Object[] queryMax = new Object[5];

        // Perform logon
        logon1 = new Logon();

        // Retrieve VWSession
        session = logon1.session;

        try {
            // Retrieve Inbox and display total count
            queue = session.getQueue("Inbox(0)");
            System.out.println("Total Items in Inbox: "+queue.fetchCount());

            // Fetch All Queue Elements of Queue "Inbox(0)"
            queryFlag = VWQueue.QUERY_NO_OPTIONS;
            queryType = VWFetchType.FETCH_TYPE_QUEUE_ELEMENT;
            query = queue.createQuery(null,null,null,queryFlag,null, null,queryType);
            counter = 0;
            while (query.hasNext()) {
                counter++;
                queueElement = (VWQueueElement) query.next();
                System.out.println(counter+": (" + queueElement.getWorkObjectNumber()+") " + queueElement.getFieldValue("F_BoundUser")+", " + queueElement.getWorkflowName()+", " + queueElement.getFieldValue("F_Subject") );
            }
            System.out.println();

            // Fetch All Queue Elements of Queue "Inbox"
            // (Retrieves for current user)
            queue = session.getQueue("Inbox");
            queryFlag = VWQueue.QUERY_NO_OPTIONS;
            queryType = VWFetchType.FETCH_TYPE_QUEUE_ELEMENT;
            query = queue.createQuery(null,null,null,queryFlag, null,null,queryType);
            counter = 0;
            System.out.println( "Inbox: " + queue.fetchCount() + "    Query: " + query.fetchCount());
            while (query.hasNext()) {
                counter++;
                queueElement = (VWQueueElement) query.next();
                System.out.println(counter+": (" + queueElement.getWorkObjectNumber()+") " + queueElement.getFieldValue("F_BoundUser")+", " + queueElement.getWorkflowName()+", " + queueElement.getFieldValue("F_Subject")+", " + queueElement.getFieldValue("F_LockTime") );
            }
            System.out.println();


            // Determine counts of each inbox
            queue = session.getQueue("Inbox(0)");
            users = session.fetchUsers(100,false);
            while(users.hasNext()) {
                userName = (String)users.next();
                userID = session.convertUserNameToId(userName);
                queryFilter = "F_BoundUser="+userID;
                queryFlag = VWQueue.QUERY_NO_OPTIONS;
                queryType = VWFetchType.FETCH_TYPE_QUEUE_ELEMENT;
                query = queue.createQuery(null,null,null,queryFlag,queryFilter, null,queryType);
                System.out.println(userName+": "+query.fetchCount());
            }
            System.out.println();

            // Query by Filtering a text field
            queue = session.getQueue("Inbox(0)");
            //queryFilter = "F_Subject like '%user%'";
            queryFilter = "F_Subject like '%Customer Order%'";
            queryFlag = VWQueue.QUERY_NO_OPTIONS;
            queryType = VWFetchType.FETCH_TYPE_QUEUE_ELEMENT;
            query = queue.createQuery(null,null,null,queryFlag,queryFilter, null,queryType);
            System.out.println( query.fetchCount()+" items with filter: " + queryFilter);
            counter = 0;
            while (query.hasNext()) {
                counter++;
                queueElement = (VWQueueElement) query.next();
                System.out.println(counter+": (" + queueElement.getWorkObjectNumber() +") " + queueElement.getWorkflowName()+", " + queueElement.getFieldValue("F_Subject") );
                wob = queueElement.getWorkObjectNumber();  // for next section
            }
            System.out.println();


            // Query using Index (Retrieve a Specific entry by Wob Number)
            queue = session.getQueue("Inbox(0)");
            queryIndex = "F_WobNum";
            queryMin[0] = wob;
            queryMax[0] = wob;
            queryFlag = VWQueue.QUERY_MIN_VALUES_INCLUSIVE + VWQueue.QUERY_MAX_VALUES_INCLUSIVE;
            queryType = VWFetchType.FETCH_TYPE_QUEUE_ELEMENT;
            query = queue.createQuery(queryIndex,queryMin,queryMax,queryFlag, null,null,queryType);
            System.out.println("Query for wob number: " + wob);
            counter = 0;
            while (query.hasNext()) {
                counter++;
                queueElement = (VWQueueElement) query.next();
                System.out.println(counter+ ": (" +queueElement.getWorkObjectNumber()+ ") " + queueElement.getWorkflowName()+", " + queueElement.getFieldValue("F_Subject"));
            }
            System.out.println();

        }
        catch (VWException vwe) {
            System.out.println( "VWException Error: " + vwe.getMessage() );
        }

        // logoff
        logon1.logoff();
 	  System.exit(1);

    }  // public static void main(String[] args)
}  // public class QueryInbox
