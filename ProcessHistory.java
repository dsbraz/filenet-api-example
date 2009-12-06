import filenet.vw.api.*;

public class ProcessHistory {
    public ProcessHistory() {
    } // public ProcessHistory()

    public void getHistory() {
        // Declare variables
        Logon logon1;
        VWSession session;
        VWRoster roster;
        int queryFlag;
        String filters;
        VWRosterQuery rosterQuery;
        VWWorkObject rosterElement;
        VWProcess process;
        VWWorkflowDefinition workflowDefinition;
        VWMapDefinition[] maps;
        int mapID;
        VWWorkflowHistory workflowHistory;
        VWStepHistory stepHistory;
        VWStepOccurrenceHistory stepOccurenceHistory;
        VWStepWorkObjectHistory stepWorkObjectHistory;
        VWParticipantHistory participantHistory;
        VWWorkflowMilestones milestones;
        VWMilestoneElement milestoneElement;
        int level;

        // Perform logon & retrieve VWSession
        logon1 = new Logon();
        session = logon1.session;

        try {
            roster = session.getRoster("OrderRoster");
            if(roster == null) {
                return;
            }
            queryFlag = VWRoster.QUERY_NO_OPTIONS;

            filters = "F_Subject like '%Order%'";
            rosterQuery = roster.createQuery(null, null, null, queryFlag, filters, null, VWFetchType.FETCH_TYPE_WORKOBJECT);
            rosterElement = null;
            if (rosterQuery.fetchCount() < 1) {
                System.out.println("No roster elements found");
                return;
            }
            while (rosterQuery.hasNext()==true) {
                rosterElement = (VWWorkObject)rosterQuery.next();
                process = rosterElement.fetchProcess();
                workflowDefinition = process.fetchWorkflowDefinition(false);
                maps = workflowDefinition.getMaps();
                for (int i = 0; i < maps.length; i++) {
                    System.out.println("Map Name: " + maps[i].getName());
                    mapID = maps[i].getMapId();
                    System.out.println("Map ID: " + mapID);

                    workflowHistory = process.fetchWorkflowHistory(mapID);
                    System.out.println("Originator: " + workflowHistory.getOriginator());
                    System.out.println("<stepHistory Info>");
                    while (workflowHistory.hasNext()) {
                        stepHistory = workflowHistory.next();
                        System.out.println(stepHistory.getStepName());
                        stepHistory.resetFetch();
                        System.out.println("\t<stepOccurenceHistory Info>");
                        while (stepHistory.hasNext()) {
                            stepOccurenceHistory = stepHistory.next();
                            System.out.println("\tDate received: " + stepOccurenceHistory.getDateReceived());
                            System.out.println("\tDate completed: " + stepOccurenceHistory.getCompletionDate());
                            System.out.println("\t\t<stepWorkObjectHistory Info>");
                            while (stepOccurenceHistory.hasNext()) {
                                stepWorkObjectHistory = stepOccurenceHistory.next();
                                stepWorkObjectHistory.resetFetch();
                                System.out.println("\t\t\t<ParticipantHistory Info>");
                                while (stepWorkObjectHistory.hasNext()) {
                                    participantHistory = stepWorkObjectHistory.next();
                                    System.out.println("\t\t\tDate received = " + participantHistory.getDateReceived());
                                    System.out.println("\t\t\tComments = " + participantHistory.getComments());
                                    System.out.println("\t\t\tUser = " + participantHistory.getUserName());
                                    System.out.println("\t\t\tParticipant = " + participantHistory.getParticipantName());
                                }  // while stepWorkObjectHistory
                                System.out.println("\t\t\t<ParticipantHistory Info>");
                            }   // while stepOccurenceHistory
                            System.out.println("\t\t</stepWorkObjectHistory Info>");
                        }  // while stepHistory
                        System.out.println("\t</stepOccurenceHistory Info>");
                    } // while workflowHistory
                } // for Workflow maps
                System.out.println("</stepHistoryInfo>");

                level = 1;
                milestones = process.fetchReachedWorkflowMilestones(level);
                while (milestones.hasNext()) {
                    System.out.println("Milestone element information:");
                    milestoneElement = milestones.next();
                    System.out.println("Name: " + milestoneElement.getName());
                    System.out.println("Message: " + milestoneElement.getMessage());
                    System.out.println("Time logged: " + milestoneElement.getTimestamp());
                }
            } // while rosterQuery hasnext()
        } // end try
        catch(Exception e) {
            System.out.println("Exception Caught " + e.getMessage());
            e.printStackTrace();
        }
        catch(Error err) {
            System.out.println("error caught " + err.getMessage());
        }
        logon1.logoff();
    } // public void getHistory()

    public static void main(String args[]) {
        ProcessHistory history = new ProcessHistory();
        history.getHistory();
	 System.exit(1);
    } // public static void main(String args[])
} //public class ProcessHistory
