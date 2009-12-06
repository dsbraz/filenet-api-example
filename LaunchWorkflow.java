import java.io.*;
import java.text.*;
import java.util.Date;

import filenet.vw.api.*;

public class LaunchWorkflow {
    public static void main(String[] args) {
        // declare variables (listed in order used)
        Logon logon1;
        VWSession session;
        String[] workClassNames;
        BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
        NumberFormat nf = NumberFormat.getInstance();
        int workclassNum;
        String workclass;
        VWStepElement stepElement;
        VWParameter[] parameters;
        int i;
        String tempStr;
        String[] tempStrA = new String[5];
        Boolean[] tempBlnA = new Boolean[5];
        Integer[] tempIntA = new Integer[5];
        Double[] tempFltA = new Double[5];
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        Date tempDate;
        Date[] tempDateA = new Date[5];
        VWParticipant tempPart;
        VWParticipant[] tempPartA = new VWParticipant[5];
        VWAttachment tempAtt;
        VWAttachment[] tempAttA = new VWAttachment[5];
        String newSubject;
        String[] responses;
        int selResponse;


        // Perform logon & retrieve VWSession
        logon1 = new Logon();
        session = logon1.session;

        try {
            //select workflow
            System.out.println( "Work Classes" );
            workClassNames = session.fetchWorkClassNames(true);
            for (i=0;i<workClassNames.length;i++) {
                System.out.println( i+": " + workClassNames[i] );
            }
            System.out.print("Select Work Class (by number): ");
            workclassNum = nf.parse(in.readLine()).intValue();
            workclass = workClassNames[workclassNum];

            // To skip the user selection, use the line below
            //workclass = "Simple Order Workflow";

            // Create Workflow
            stepElement = session.createWorkflow(workclass);


            // Assign User-defined Parameters
            parameters = stepElement.getParameters(VWFieldType.ALL_FIELD_TYPES, VWStepElement.FIELD_USER_DEFINED);
            if (parameters != null) {
                for (i=0; i<parameters.length; i++) {
                    System.out.println(parameters[i].getName()+" - " + VWFieldType.getLocalizedString(parameters[i].getFieldType())+" - " + parameters[i].getStringValue()+" - " + VWModeType.getLocalizedString(parameters[i].getMode()) );
                    if (parameters[i].getMode()==VWModeType.MODE_TYPE_IN) {
                        System.out.println("Read Only.");
                    }
                    else {
                        // switch through each attachment data type
                        switch(parameters[i].getFieldType()) {
                            // String
                            case VWFieldType.FIELD_TYPE_STRING:
                                if (parameters[i].isArray()) {
                                    System.out.print(parameters[i].getName()+" (String[]) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        tempStrA[0] = tempStr;
                                        stepElement.setParameterValue(parameters[i].getName(),tempStrA,true);
                                    }
                                }
                                else {
                                    System.out.print(parameters[i].getName()+" (String) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length() > 0) {
                                        stepElement.setParameterValue(parameters[i].getName(),tempStr,true);
                                    }
                                }
                                break;
                                // Boolean
                            case VWFieldType.FIELD_TYPE_BOOLEAN:
                                if (parameters[i].isArray()) {
                                    System.out.print(parameters[i].getName()+" (Boolean[]) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        tempBlnA[0] = new Boolean(tempStr);
                                        stepElement.setParameterValue(parameters[i].getName(),tempBlnA,true);
                                    }
                                }
                                else {
                                    System.out.print(parameters[i].getName()+" (Boolean) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        stepElement.setParameterValue(parameters[i].getName(),  new Boolean(tempStr),true);
                                    }
                                }
                                break;
                                // Integer
                            case VWFieldType.FIELD_TYPE_INT:
                                if (parameters[i].isArray()) {
                                    System.out.print(parameters[i].getName()+" (Integer[]) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        tempIntA[0] = new Integer(tempStr);
                                        stepElement.setParameterValue(parameters[i].getName(), tempIntA, true);
                                    }
                                }
                                else {
                                    System.out.print(parameters[i].getName()+" (Integer) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        stepElement.setParameterValue( parameters[i].getName(),  new Integer(tempStr),true);
                                    }
                                }
                                break;
                                // Double
                            case VWFieldType.FIELD_TYPE_FLOAT:
                                if (parameters[i].isArray()) {
                                    System.out.print(parameters[i].getName()+" (Double[]) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        tempFltA[0] = new Double(tempStr);
                                        stepElement.setParameterValue(parameters[i].getName(), tempFltA, true);
                                    }
                                }
                                else {
                                    System.out.print(parameters[i].getName()+" (Double) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        stepElement.setParameterValue( parameters[i].getName(),  new Double(tempStr), true);
                                    }
                                }
                                break;
                                // Date
                            case VWFieldType.FIELD_TYPE_TIME:
                                if (parameters[i].isArray()) {
                                    System.out.print(parameters[i].getName()+" (Date[]) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length() > 0) {
                                        tempDateA[0] = df.parse(tempStr);
                                        stepElement.setParameterValue(parameters[i].getName(), tempDateA, true);
                                    }
                                }
                                else {
                                    System.out.print(parameters[i].getName()+" (Date) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length() > 0) {
                                        tempDate = df.parse(tempStr);
                                        stepElement.setParameterValue(parameters[i].getName(), tempDate, true);
                                    }
                                }
                                break;
                                // VWParticipant
                            case VWFieldType.FIELD_TYPE_PARTICIPANT:
                                if (parameters[i].isArray()) {
                                    tempPartA = (VWParticipant[]) parameters[i].getValue();
                                    System.out.print(parameters[i].getName() + " (VWParticipant[]) [" + parameters[i].getStringValue()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length()>0) {
                                        tempPartA[0].setParticipantName(tempStr);
                                        stepElement.setParameterValue(parameters[i].getName(), tempPartA, true);
                                    }
                                }
                                else {
                                    System.out.println("Unexpected Data Type: VWParticipant");
                                }
                                break;
                                // VWAttachment
                            case VWFieldType.FIELD_TYPE_ATTACHMENT:
                                if (parameters[i].isArray()) {
                                    tempAttA = (VWAttachment[]) parameters[i].getValue();
                                    tempAtt = tempAttA[0];
                                    System.out.println(parameters[i].getName() +  " (Attachment[]) [" + parameters[i].getStringValue()+"] ");
                                    System.out.print("Attachment Name [" + tempAtt.getAttachmentName()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length() > 0) {
                                        tempAtt.setAttachmentName(tempStr);
                                        System.out.print("Attachment Description [" + tempAtt.getAttachmentDescription()+"]: ");
                                        tempAtt.setAttachmentDescription(in.readLine());
                                        System.out.print("Attachment Type ["+tempAtt.getType()+"]: ");
                                        tempAtt.setType(nf.parse(in.readLine()).intValue());
                                        System.out.print("Attachment Location Type [" + tempAtt.getLibraryType()+"]: ");
                                        tempAtt.setLibraryType(nf.parse(in.readLine()).intValue());
                                        System.out.print("Attachment Location Name [" + tempAtt.getLibraryName()+"]: ");
                                        tempAtt.setLibraryName(in.readLine());
                                        System.out.print("Attachment ID ["+tempAtt.getId()+"]: ");
                                        tempAtt.setId(in.readLine());
                                        System.out.print("Attachment Version (Optional) [" + tempAtt.getVersion()+"]: ");
                                        tempAtt.setVersion(in.readLine());
                                        tempAttA[0] = tempAtt;
                                        stepElement.setParameterValue(parameters[i].getName(), tempAttA, true);
                                    }
                                }
                                else {
                                    tempAtt = (VWAttachment) parameters[i].getValue();
                                    System.out.println(parameters[i].getName()+" (Attachment) [" + parameters[i].getStringValue()+"] ");
                                    System.out.print("Attachment Name [" + tempAtt.getAttachmentName()+"]: ");
                                    tempStr = in.readLine();
                                    if (tempStr.length() > 0) {
                                        tempAtt.setAttachmentName(tempStr);
                                        System.out.print("Attachment Description [" + tempAtt.getAttachmentDescription()+"]: ");
                                        tempAtt.setAttachmentDescription(in.readLine());
                                        System.out.print("Attachment Type ["+tempAtt.getType() + "]: ");
                                        tempAtt.setType(nf.parse(in.readLine()).intValue());
                                        System.out.print("Attachment Location Type [" + tempAtt.getLibraryType()+"]: ");
                                        tempAtt.setLibraryType( nf.parse(in.readLine()).intValue());
                                        System.out.print("Attachment Location Name [" + tempAtt.getLibraryName()+"]: ");
                                        tempAtt.setLibraryName(in.readLine());
                                        System.out.print("Attachment ID ["+tempAtt.getId()+"]: ");
                                        tempAtt.setId(in.readLine());
                                        System.out.print("Attachment Version (Optional) [" + tempAtt.getVersion()+"]: ");
                                        tempAtt.setVersion(in.readLine());
                                        stepElement.setParameterValue(parameters[i].getName(), tempAtt, true);
                                    }
                                }
                                break;
                            default:
                                System.out.println("Unknown Type.");
                        } // switch(parameters[i].getFieldType())
                    }  // if (parameters[i].getMode()==2)
                }  // for (i=0; i<parameters.length; i++)
            }

            // System Parameter - Subject
            System.out.print("WorkFlow Subject [" + stepElement.getParameterValue("F_Subject") + "]: ");
            newSubject = in.readLine();
            if (newSubject.length() > 0) {
                stepElement.setParameterValue("F_Subject", newSubject, true);
            }

            // System Parameter - Responses
            responses = stepElement.getStepResponses();
            if (responses==null) {
                System.out.println("No response choices.");
            }
            else {
                for(i=0;i<responses.length;i++) {
                    System.out.println(i+": "+responses[i]);
                }
                System.out.print("Select Response (by number): ");
                selResponse = nf.parse(in.readLine()).intValue();
                stepElement.setSelectedResponse(responses[selResponse]);
            }

            // Complete Launch
            stepElement.doDispatch();
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
}  // public class LaunchWorkflow
