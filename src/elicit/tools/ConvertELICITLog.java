/*
 * ConvertELICITLog.java
 *
 * Created on 27 June 2007, 23:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.Message;
import elicit.message.MessageListener;
import elicit.message.MessageParser;
import java.io.*;
import java.util.*;
import metrics.ELICITmetrics;

/**
 *
 * @author Marco
 */
public class ConvertELICITLog implements MessageListener {

    String m_fileName_output;
    String m_fileName_overallMetricsOutput;
    String m_fileName_vnaSubjectAndSiteOutput;
    String m_IDfileName_output;
    String m_InfoQfileName_output;
    //static String m_fileName_vnaOutput             = "c:\\edisoft\\"+logPrefix+"_subjects.vna";
    //static String m_fileName_vnaSiteOutput         = "c:\\edisoft\\"+logPrefix+"_sites.vna";
    //end...
    
    String m_inputFile = null;
    //WRITERS
    FileWriter m_writer = null;
    FileWriter m_IDwriter = null;
    FileWriter m_InfoQwriter = null;
    
    ELICITmetrics logMetrics;
    
    /** Creates a new instance of ConvertELICITLog */
    public ConvertELICITLog() {
        //m_outputFile = inputFile+".out";
        logMetrics = new ELICITmetrics();
    }

    /** Creates a new instance of ConvertELICITLog */
    public ConvertELICITLog(String inputFile) {
        m_inputFile = inputFile;
        //m_outputFile = inputFile+".out";
        logMetrics = new ELICITmetrics();
    }

    public void setLogOutputFilename (String filename) {
        m_fileName_output = filename;
    }
    
    public void setIDOutputFilename (String filename) {
        m_IDfileName_output = filename;
    }
    
    public void setOverallMetricsOutputFilename (String filename) {
        m_fileName_overallMetricsOutput = filename;
    }
    
    public void setVnaSubjectAndSiteOutputFilename (String filename) {
        m_fileName_vnaSubjectAndSiteOutput = filename;
    }
    
    public void setInfoQOutputFilename (String filename) {
        m_InfoQfileName_output = filename;
    }
    
    /*
    public void setVnaOutputSufix (String sufix) {
        m_fileName_vnaOutputSufix = sufix;
    }
    
    public void setVnaSiteOutputSufix (String sufix) {
        m_fileName_vnaSiteOutputSufix = sufix;
    }
    */
    
    private void writeVNAHashMap(FileWriter writer, HashMap<String, HashMap> relationMap) throws IOException {
        Set<String> keySet = relationMap.keySet();
        for (Iterator it = keySet.iterator(); it.hasNext(); ) {
            String personSourceName = (String)it.next();
            //get hash
            HashMap relationPersonMap = relationMap.get(personSourceName);
            //get hash relation
            Set<String> relationKeySet = relationPersonMap.keySet();
            for (Iterator itRelation = relationKeySet.iterator(); itRelation.hasNext(); ) {
                String relationPersonName = (String)itRelation.next();
                Integer numRelation = (Integer)relationPersonMap.get(relationPersonName);
                writer.write(personSourceName+" "+relationPersonName+" "+numRelation+" 1\r\n");
            }
        }
    }
    
    private void writeVNAFile(String vnaFilename, HashMap<String, HashMap> relationMap) {
        FileWriter writer = null;

        try {
            //output file
            writer = new FileWriter(vnaFilename);
            
            //write template data
            writer.write("*Node properties\n");
            writer.write("ID x y color shape size labeltext labelsize labelcolor gapx gapy active\n");
            writer.write("\"Alex\" 484 341 16776960 1 8 \"Alex\" 11 0 3 5 TRUE\n");
            writer.write("\"Chris\" 276 198 16744576 1 8 \"Chris\" 11 0 3 5 TRUE\n");
            writer.write("\"Dale\" 336 29 255 1 8 \"Dale\" 11 0 3 5 TRUE\n");
            writer.write("\"Francis\" 52 188 255 1 8 \"Francis\" 11 0 3 5 TRUE\n");
            writer.write("\"Harlan\" 189 54 255 1 8 \"Harlan\" 11 0 3 5 TRUE\n");
            writer.write("\"Jesse\" 169 500 255 1 8 \"Jesse\" 11 0 3 5 TRUE\n");
            writer.write("\"Kim\" 57 437 255 1 8 \"Kim\" 11 0 3 5 TRUE\n");
            writer.write("\"Leslie\" 282 664 255 1 8 \"Leslie\" 11 0 3 5 TRUE\n");
            writer.write("\"Morgan\" 317 475 16744576 1 8 \"Morgan\" 11 0 3 5 TRUE\n");
            writer.write("\"Pat\" 584 174 16744576 1 8 \"Pat\" 11 0 3 5 TRUE\n");
            writer.write("\"Quinn\" 571 36 255 1 8 \"Quinn\" 11 0 3 5 TRUE\n");
            writer.write("\"Robin\" 858 178 255 1 8 \"Robin\" 11 0 3 5 TRUE\n");
            writer.write("\"Sam\" 658 105 255 1 8 \"Sam\" 11 0 3 5 TRUE\n");
            writer.write("\"Sidney\" 606 446 16744576 1 8 \"Sidney\" 11 0 3 5 TRUE\n");
            writer.write("\"Taylor\" 762 473 255 1 8 \"Taylor\" 11 0 3 5 TRUE\n");
            writer.write("\"Val\" 655 654 255 1 8 \"Val\" 11 0 3 5 TRUE\n");
            writer.write("\"What\" 92 660 33023 1 12 \"What\" 11 0 3 5 TRUE\n");
            writer.write("\"When\" 840 646 33023 1 12 \"When\" 11 0 3 5 TRUE\n");
            writer.write("\"Where\" 829 45 33023 1 12 \"Where\" 11 0 3 5 TRUE\n");
            writer.write("\"Whitley\" 864 352 255 1 8 \"Whitley\" 11 0 3 5 TRUE\n");
            writer.write("\"Who\" 71 43 33023 1 12 \"Who\" 11 0 3 5 TRUE\n");
            writer.write("\"\" 475 627 33023 1 8 \"22\" 11 0 3 5 FALSE\n");
            
            writer.write("*Tie data\r\n");
            writer.write("from to friends strength\r\n");

            writeVNAHashMap(writer, relationMap);            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    } //writeVNAFile
/*    
    private void writeVNAFile() {
        writeVNAFile(m_fileName_vnaOutput, logMetrics.m_socialRelationMatrix);
    } //writeVNAFile
    
    public void writeSiteVNAFile()
    {
        writeVNAFile(m_fileName_vnaSiteOutput, logMetrics.m_sitesRelationMatrix);
    }//end writeSiteVNAFile
*/
    
    public void writeSubjectAndSiteVNAFile()
    {
        FileWriter writer = null;

        try {
            //output file
            writer = new FileWriter(m_fileName_vnaSubjectAndSiteOutput);

            //write template data
            writer.write("*Node properties\n");
            writer.write("ID x y color shape size labeltext labelsize labelcolor gapx gapy active\n");
            writer.write("\"Alex\" 484 341 16776960 1 8 \"Alex\" 11 0 3 5 TRUE\n");
            writer.write("\"Chris\" 276 198 16744576 1 8 \"Chris\" 11 0 3 5 TRUE\n");
            writer.write("\"Dale\" 336 29 255 1 8 \"Dale\" 11 0 3 5 TRUE\n");
            writer.write("\"Francis\" 52 188 255 1 8 \"Francis\" 11 0 3 5 TRUE\n");
            writer.write("\"Harlan\" 189 54 255 1 8 \"Harlan\" 11 0 3 5 TRUE\n");
            writer.write("\"Jesse\" 169 500 255 1 8 \"Jesse\" 11 0 3 5 TRUE\n");
            writer.write("\"Kim\" 57 437 255 1 8 \"Kim\" 11 0 3 5 TRUE\n");
            writer.write("\"Leslie\" 282 664 255 1 8 \"Leslie\" 11 0 3 5 TRUE\n");
            writer.write("\"Morgan\" 317 475 16744576 1 8 \"Morgan\" 11 0 3 5 TRUE\n");
            writer.write("\"Pat\" 584 174 16744576 1 8 \"Pat\" 11 0 3 5 TRUE\n");
            writer.write("\"Quinn\" 571 36 255 1 8 \"Quinn\" 11 0 3 5 TRUE\n");
            writer.write("\"Robin\" 858 178 255 1 8 \"Robin\" 11 0 3 5 TRUE\n");
            writer.write("\"Sam\" 658 105 255 1 8 \"Sam\" 11 0 3 5 TRUE\n");
            writer.write("\"Sidney\" 606 446 16744576 1 8 \"Sidney\" 11 0 3 5 TRUE\n");
            writer.write("\"Taylor\" 762 473 255 1 8 \"Taylor\" 11 0 3 5 TRUE\n");
            writer.write("\"Val\" 655 654 255 1 8 \"Val\" 11 0 3 5 TRUE\n");
            writer.write("\"Whitley\" 864 352 255 1 8 \"Whitley\" 11 0 3 5 TRUE\n");
            writer.write("\"What\" 92 660 33023 1 12 \"What\" 11 0 3 5 TRUE\n");
            writer.write("\"When\" 840 646 33023 1 12 \"When\" 11 0 3 5 TRUE\n");
            writer.write("\"Where\" 829 45 33023 1 12 \"Where\" 11 0 3 5 TRUE\n");
            writer.write("\"Who\" 71 43 33023 1 12 \"Who\" 11 0 3 5 TRUE\n");
            writer.write("\"\" 475 627 33023 1 8 \"22\" 11 0 3 5 FALSE\n");
            
            writer.write("*Tie data\r\n");
            writer.write("from to friends strength\r\n");
            writeVNAHashMap(writer, logMetrics.m_socialRelationMatrix);
            writeVNAHashMap(writer, logMetrics.m_sitesRelationMatrix);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//end writeSiteVNAFile
    
    private void writeOverallMetricsFile() {
        FileWriter writer = null;
        try {
            //output file
            writer = new FileWriter(m_fileName_overallMetricsOutput);
            writer.write("Player\tTotal Shares\tTotal Shares Rcv\tTotal Post\tTotal Pull\tTotal Identifies\tFinal ID score\tTx Q\tDuration (hours)\tShares/Hour\tShares Rcv/Hour\tPosts/Hour\tPulls/Hour\tIds/Hour\r\n");
            Set<String> keySet = logMetrics.m_personPullActivity.keySet();
            for (Iterator it = keySet.iterator(); it.hasNext(); ) {
                String personSourceName = (String)it.next();
                int totalShares = 0;
                int totalShareRcv = 0; 
                int totalPush = 0; 
                int totalPull = 0; 
                int totalIds  = 0; 
                double finalIDScore = 0; 
                double infoTxScore  = 0; 
                
                if (logMetrics.m_personShareActivity.containsKey(personSourceName)) {
                    totalShares = logMetrics.m_personShareActivity.get(personSourceName);
                }
                if (logMetrics.m_personShareRcvActivity.containsKey(personSourceName)) {
                    totalShareRcv = logMetrics.m_personShareRcvActivity.get(personSourceName);
                }
                if (logMetrics.m_personPostActivity.containsKey(personSourceName)) {
                    totalPush = logMetrics.m_personPostActivity.get(personSourceName);
                }
                if (logMetrics.m_personPullActivity.containsKey(personSourceName)) {
                    totalPull = logMetrics.m_personPullActivity.get(personSourceName);
                }
                if (logMetrics.m_personIdentifies.containsKey(personSourceName)) {
                    totalIds = logMetrics.m_personIdentifies.get(personSourceName);
                }
                if (logMetrics.m_personIDQuality.containsKey(personSourceName)) {
                    finalIDScore = logMetrics.m_personIDQuality.get(personSourceName);
                }
                if (logMetrics.m_personInfoQTx.containsKey(personSourceName)) {
                    infoTxScore = logMetrics.m_personInfoQTx.get(personSourceName);
                }
                writer.write(personSourceName+"\t"+totalShares+"\t"+totalShareRcv+"\t"+totalPush+"\t"+totalPull+"\t"+totalIds+"\t"+finalIDScore+"\t"+infoTxScore+"\t");
                writer.write( logMetrics.m_trialTimeHour+"\t"+totalShares/logMetrics.m_trialTimeHour+"\t"+totalShareRcv/logMetrics.m_trialTimeHour+"\t"+totalPush/logMetrics.m_trialTimeHour+"\t"+totalPull/logMetrics.m_trialTimeHour+"\t"+totalIds/logMetrics.m_trialTimeHour+"\r\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void doIt() {
        Scanner s = null;
        FileWriter writer = null;
        
        //Delimiter character is END_LINE
        //- each line will be written to output file
        try {
            //input file
            s = new Scanner(new BufferedReader(new FileReader(m_inputFile))).useDelimiter("\n");

            //output file
            writer = new FileWriter(m_fileName_output);
            
            //create parser
            MessageParser mParser = new MessageParser();
            
            //HEADING
            writer.write("Sequence\tAction\tAction By\tAction By Team\tInformation\tSend Posted To\tSend To Team\tWho Score\tWhat Score\tWhere Score\tWhen Score\tNbr IDs (overall)\tQ IDs (overall)\r\n");

            //while has lines
            while (s.hasNext()) {
                //process line
                Message m = mParser.getMessage(s.next());
                if (m!=null) {
                    //////////////////////////////////////////////////////////////////////////
                    //METRICS:
                    //extract social metrics
                    //1-st: we want: messages related to share 
                    if (m.m_commonData.type.equals("share")) {
                        if (!logMetrics.m_socialRelationMatrix.containsKey(m.m_subject.m_personName)) {
                            logMetrics.m_socialRelationMatrix.put(m.m_subject.m_personName, new HashMap<String, Integer>());
                        }
                        //get map contents (subject relates to whom?)
                        HashMap<String, Integer> relMap = logMetrics.m_socialRelationMatrix.get(m.m_subject.m_personName);
                        if (!relMap.containsKey(m.m_destSubject)) {
                            relMap.put(m.m_destSubject,new Integer(1));
                        }
                        else {
                            Integer value = relMap.get(m.m_destSubject);
                            value = value +1;
                            relMap.put(m.m_destSubject,value);
                        }
                        //now add metrics for individual sharing activity
                        if (!logMetrics.m_personShareActivity.containsKey(m.m_subject.m_personName)) {
                            logMetrics.m_personShareActivity.put(m.m_subject.m_personName, new Integer(1));
                        }
                        else {
                            Integer value = logMetrics.m_personShareActivity.get(m.m_subject.m_personName);
                            value = value +1;
                            logMetrics.m_personShareActivity.put(m.m_subject.m_personName,value);
                        }
                        if (!logMetrics.m_personShareRcvActivity.containsKey(m.m_destSubject)) {
                            logMetrics.m_personShareRcvActivity.put(m.m_destSubject, new Integer(1));
                        }
                        else {
                            Integer value = logMetrics.m_personShareRcvActivity.get(m.m_destSubject);
                            value = value +1;
                            logMetrics.m_personShareRcvActivity.put(m.m_destSubject,value);
                        }
                    }//end 1st. (share)
                    //2-nd: we want: messages related to post
                    else if (m.m_commonData.type.equals("post")) {
                        if (!logMetrics.m_sitesRelationMatrix.containsKey(m.m_subject.m_personName)) {
                            logMetrics.m_sitesRelationMatrix.put(m.m_subject.m_personName, new HashMap<String, Integer>());
                        }
                        //get map contents (subject relates to whom?)
                        HashMap<String, Integer> relMap = logMetrics.m_sitesRelationMatrix.get(m.m_subject.m_personName);
                        if (!relMap.containsKey(m.m_postSite)) {
                            relMap.put(m.m_postSite,new Integer(1));
                        }
                        else {
                            Integer value = relMap.get(m.m_postSite);
                            value = value +1;
                            relMap.put(m.m_postSite,value);
                        }
                        //now add metrics for individual post activity
                        if (!logMetrics.m_personPostActivity.containsKey(m.m_subject.m_personName)) {
                            logMetrics.m_personPostActivity.put(m.m_subject.m_personName, new Integer(1));
                        }
                        else {
                            Integer value = logMetrics.m_personPostActivity.get(m.m_subject.m_personName);
                            value = value +1;
                            logMetrics.m_personPostActivity.put(m.m_subject.m_personName,value);
                        }
                    }//end (post)
                    //3-rd: we want: messages related to pull
                    else if (m.m_commonData.type.equals("pull")) {
                        if (!logMetrics.m_sitesRelationMatrix.containsKey(m.m_pullSite)) {
                            logMetrics.m_sitesRelationMatrix.put(m.m_pullSite, new HashMap<String, Integer>());
                        }
                        //get map contents (subject relates to whom?)
                        HashMap<String, Integer> relMap = logMetrics.m_sitesRelationMatrix.get(m.m_pullSite);
                        if (!relMap.containsKey(m.m_subject.m_personName)) {
                            relMap.put(m.m_subject.m_personName,new Integer(1));
                        }
                        else {
                            Integer value = relMap.get(m.m_subject.m_personName);
                            value = value +1;
                            relMap.put(m.m_subject.m_personName,value);
                        }
                        //now add metrics for individual pull activity
                        if (!logMetrics.m_personPullActivity.containsKey(m.m_subject.m_personName)) {
                            logMetrics.m_personPullActivity.put(m.m_subject.m_personName, new Integer(1));
                        }
                        else {
                            Integer value = logMetrics.m_personPullActivity.get(m.m_subject.m_personName);
                            value = value +1;
                            logMetrics.m_personPullActivity.put(m.m_subject.m_personName,value);
                        }
                    }//end (pull)
                    //messages related to pull
                    else if (m.m_commonData.type.equals("identify")) {
                        if (!logMetrics.m_personIdentifies.containsKey(m.m_subject.m_personName)) {
                            logMetrics.m_personIdentifies.put(m.m_subject.m_personName, new Integer(1));
                        }
                        else {
                            Integer value = logMetrics.m_personIdentifies.get(m.m_subject.m_personName);
                            value = value +1;
                            logMetrics.m_personIdentifies.put(m.m_subject.m_personName,value);
                        }
                    }//end (identify)
                    else if (m.m_commonData.type.equals("initiate")) {
                        //logMetrics.m_trialTimeHour = m.m_startExperiment;
                    }                    
                    else if (m.m_commonData.type.equals("end")) {
                        //logMetrics.m_trialTimeHour = ((double)(m.m_endExperiment - logMetrics.m_trialTimeHour))/1000.0/3600.0;
                    }                    
                    //////////////////////////////////////////////////////////////////////////
                    if ( m.Write().length()!=0 ) {
                        writer.write(m.Write());
                        writer.write("\r\n");
                    }
                }//end message not null
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        //write VNA file
        //writeVNAFile();
        //writeSiteVNAFile();
        writeSubjectAndSiteVNAFile();
        writeOverallMetricsFile();
    }

    public void OnStart() {
        m_writer = null;
        //Delimiter character is END_LINE
        //- each line will be written to output file
        try {
            //output file
            m_writer = new FileWriter(m_fileName_output);
            //HEADING
            m_writer.write("Sequence\tAction\tAction By\tAction By Team\tInformation\tSend Posted To\tSend To Team\tWho Score\tWhat Score\tWhere Score\tWhen Score\r\n");
            //ID file
            m_IDwriter = new FileWriter(m_IDfileName_output);
            m_IDwriter.write("Sequence\tAction\tAction By\tAction By Team\tInformation\tSend Posted To\tSend To Team\tWho Score\tWhat Score\tWhere Score\tWhen Score\tNbr IDs\tQ IDs\tNbr IDs (overall)\tQ IDs (overall)\tMAX\tMIN\r\n");
            //InfoQ
            m_InfoQwriter = new FileWriter(m_InfoQfileName_output);
            m_InfoQwriter.write("Sequence\tAction\tAction By\tAction By Team\tContent\tSend Posted To\tSend To Team"
                                +"\tInfo ID\tNbr Tx Actions\tTx Relevant Actions (%)\tQ Tx\tQ Tx Player\tQ Tx (overall)\r\n");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void OnFinish() {
        try {
            if (m_writer != null) {
                m_writer.close();
            }
            if (m_IDwriter != null) {
                m_IDwriter.close();
            }
            if (m_InfoQwriter != null) {
                m_InfoQwriter.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //write VNA file
        //writeVNAFile();
        //writeSiteVNAFile();
        writeSubjectAndSiteVNAFile();
        writeOverallMetricsFile();
    }

    public void OnNewMessage(Message message_p) {
        if (message_p == null) {
            return;
        }
        try {
            //////////////////////////////////////////////////////////////////////////
            //METRICS:
            //extract social metrics
            //1-st: we want: messages related to share 
            if (message_p.m_commonData.type.equals("share")) {
                if (!logMetrics.m_socialRelationMatrix.containsKey(message_p.m_subject.m_personName)) {
                    logMetrics.m_socialRelationMatrix.put(message_p.m_subject.m_personName, new HashMap<String, Integer>());
                }
                //get map contents (subject relates to whom?)
                HashMap<String, Integer> relMap = logMetrics.m_socialRelationMatrix.get(message_p.m_subject.m_personName);
                if (!relMap.containsKey(message_p.m_destSubject)) {
                    relMap.put(message_p.m_destSubject,new Integer(1));
                }
                else {
                    Integer value = relMap.get(message_p.m_destSubject);
                    value = value +1;
                    relMap.put(message_p.m_destSubject,value);
                }
                //now add metrics for individual sharing activity
                if (!logMetrics.m_personShareActivity.containsKey(message_p.m_subject.m_personName)) {
                    logMetrics.m_personShareActivity.put(message_p.m_subject.m_personName, new Integer(1));
                }
                else {
                    Integer value = logMetrics.m_personShareActivity.get(message_p.m_subject.m_personName);
                    value = value +1;
                    logMetrics.m_personShareActivity.put(message_p.m_subject.m_personName,value);
                }
                if (!logMetrics.m_personShareRcvActivity.containsKey(message_p.m_destSubject)) {
                    logMetrics.m_personShareRcvActivity.put(message_p.m_destSubject, new Integer(1));
                }
                else {
                    Integer value = logMetrics.m_personShareRcvActivity.get(message_p.m_destSubject);
                    value = value +1;
                    logMetrics.m_personShareRcvActivity.put(message_p.m_destSubject,value);
                }
                //get info quality
                //calculateInfoQ(message_p);
            }//end 1st. (share)
            //2-nd: we want: messages related to post
            else if (message_p.m_commonData.type.equals("post")) {
                if (!logMetrics.m_sitesRelationMatrix.containsKey(message_p.m_subject.m_personName)) {
                    logMetrics.m_sitesRelationMatrix.put(message_p.m_subject.m_personName, new HashMap<String, Integer>());
                }
                //get map contents (subject relates to whom?)
                HashMap<String, Integer> relMap = logMetrics.m_sitesRelationMatrix.get(message_p.m_subject.m_personName);
                if (!relMap.containsKey(message_p.m_postSite)) {
                    relMap.put(message_p.m_postSite,new Integer(1));
                }
                else {
                    Integer value = relMap.get(message_p.m_postSite);
                    value = value +1;
                    relMap.put(message_p.m_postSite,value);
                }
                //now add metrics for individual post activity
                if (!logMetrics.m_personPostActivity.containsKey(message_p.m_subject.m_personName)) {
                    logMetrics.m_personPostActivity.put(message_p.m_subject.m_personName, new Integer(1));
                }
                else {
                    Integer value = logMetrics.m_personPostActivity.get(message_p.m_subject.m_personName);
                    value = value +1;
                    logMetrics.m_personPostActivity.put(message_p.m_subject.m_personName,value);
                }
                //get info quality
                //calculateInfoQ(message_p);
            }//end (post)
            //3-rd: we want: messages related to pull
            else if (message_p.m_commonData.type.equals("pull")) {
                if (!logMetrics.m_sitesRelationMatrix.containsKey(message_p.m_pullSite)) {
                    logMetrics.m_sitesRelationMatrix.put(message_p.m_pullSite, new HashMap<String, Integer>());
                }
                //get map contents (subject relates to whom?)
                HashMap<String, Integer> relMap = logMetrics.m_sitesRelationMatrix.get(message_p.m_pullSite);
                if (!relMap.containsKey(message_p.m_subject.m_personName)) {
                    relMap.put(message_p.m_subject.m_personName,new Integer(1));
                }
                else {
                    Integer value = relMap.get(message_p.m_subject.m_personName);
                    value = value +1;
                    relMap.put(message_p.m_subject.m_personName,value);
                }
                //now add metrics for individual pull activity
                if (!logMetrics.m_personPullActivity.containsKey(message_p.m_subject.m_personName)) {
                    logMetrics.m_personPullActivity.put(message_p.m_subject.m_personName, new Integer(1));
                }
                else {
                    Integer value = logMetrics.m_personPullActivity.get(message_p.m_subject.m_personName);
                    value = value +1;
                    logMetrics.m_personPullActivity.put(message_p.m_subject.m_personName,value);
                }
            }//end (pull)            
            //////////////////////////////////////////////////////////////////////////
            //METRICS:
            //IDs (metrics and quality)
            else if (message_p.m_commonData.type.equals("identify")) {
                if (!logMetrics.m_personIdentifies.containsKey(message_p.m_subject.m_personName)) {
                    logMetrics.m_personIdentifies.put(message_p.m_subject.m_personName, new Integer(1));
                    //logMetrics.m_personIDQuality.put(message_p.m_subject.m_personName, message_p.getIDQAverage());
                }
                else {
                    Integer value = logMetrics.m_personIdentifies.get(message_p.m_subject.m_personName);
                    value = value +1;
                    logMetrics.m_personIdentifies.put(message_p.m_subject.m_personName,value);
                    //logMetrics.m_personIDQuality.put(message_p.m_subject.m_personName, message_p.getIDQAverage());
                }
                //
                if ( message_p.Write().length()!=0 ) {
                    m_IDwriter.write(message_p.Write());
                    m_IDwriter.write("\t"+logMetrics.m_personIdentifies.get(message_p.m_subject.m_personName));
                    //m_IDwriter.write("\t"+message_p.getIDQAverage());
                    m_IDwriter.write("\t"+logMetrics.getNbrIdentifies());
                    m_IDwriter.write("\t"+logMetrics.getIDOverallQuality());

                    //m_IDwriter.write("\t"+logMetrics.getIDMaxQuality());
                    //m_IDwriter.write("\t"+logMetrics.getIDMinQuality());
                    
                //...also WRITE max and min (through time)
                
                    m_IDwriter.write("\r\n");
                }
            }//end (identify)
            //////////////////////////////////////////////////////////////////////////
            else if (message_p.m_commonData.type.equals("initiate")) {
                //logMetrics.m_trialTimeHour = message_p.m_startExperiment;
            }                    
            else if (message_p.m_commonData.type.equals("end")) {
                //logMetrics.m_trialTimeHour = ((double)(message_p.m_endExperiment - logMetrics.m_trialTimeHour))/1000.0/3600.0;
            }                    
            //////////////////////////////////////////////////////////////////////////7
            if ( message_p.Write().length()!=0 ) {
                m_writer.write(message_p.Write());
                m_writer.write("\r\n");
            }
            //
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length != 1) {
            System.out.println("usage: <ELICIT log filename>");
        }
        else {
            System.out.print("Input file: ");
            System.out.print(args[0]);
            
            ReadELICITLog reader = new ReadELICITLog(args[0]);
            ConvertELICITLog convert = new ConvertELICITLog(args[0]);
            reader.addMessageListener(convert);
            reader.doIt();
            //convert.doIt();
        }
    }    

    /*
    private void calculateInfoQ(Message message_p) throws IOException {
        double q = message_p.getMessageInfoQValue();
        if (message_p.isRelevant()) {
            logMetrics.m_infoTxRelevant++;
        }
        if (!logMetrics.m_personInfoQTx.containsKey(message_p.m_subject.m_personName)) 
        {
            logMetrics.m_personInfoQTx.put(message_p.m_subject.m_personName, q);
        }
        else 
        {
            Double value = logMetrics.m_personInfoQTx.get(message_p.m_subject.m_personName);
            value = value +q;
            logMetrics.m_personInfoQTx.put(message_p.m_subject.m_personName,value);
        }
        if ( message_p.Write().length()!=0 ) {
            m_InfoQwriter.write(message_p.Write());
            if (message_p.m_commonData.type.equals("post")) {
                m_InfoQwriter.write("\t");
            }
            //m_InfoQwriter.write("\t'"+message_p.m_factoid.m_factoidId+"'");
            m_InfoQwriter.write("\t"+(logMetrics.getNbrPosts()+logMetrics.getNbrShares()) );
            m_InfoQwriter.write("\t"+((double)logMetrics.m_infoTxRelevant/(logMetrics.getNbrPosts()+logMetrics.getNbrShares())) );
            m_InfoQwriter.write("\t"+q);
            m_InfoQwriter.write("\t"+logMetrics.m_personInfoQTx.get(message_p.m_subject.m_personName));
            m_InfoQwriter.write("\t"+logMetrics.getInfoTxOverallQuality());
            m_InfoQwriter.write("\r\n");
        }        
    }
     * */
    
}
