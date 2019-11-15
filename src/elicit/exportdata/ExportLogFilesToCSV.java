/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import elicit.message.OrganizationMessage;
import elicit.message.TrialData;
import elicit.tools.CalculateEffectivessMetrics;
import elicit.tools.JFELICITMetricsVisualizer;
import elicit.tools.JFSubjectsReciprocityRateTable;
import metrics.awareness.IDsQualityMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author marcomanso
 */
//public class ExportLogFilesToCSV extends TimerTask {
public class ExportLogFilesToCSV implements Runnable {
//public class ExportLogFilesToCSV extends Thread {

    java.io.File m_logFile = null;
    Thread parentThread = null;
    //
    public static final String GENERAL_FILE      = "ELICIT_RUNS_general.csv";
    public static final String INFORMATION_FILE  = "ELICIT_RUNS_information.csv";
    public static final String INTERACTIONS_FILE = "ELICIT_RUNS_interactions.csv";
    public static final String AWARENESS_FILE    = "ELICIT_RUNS_awareness.csv";
    public static final String AWARENESS_FILE_SINCE_DIST    = "ELICIT_RUNS_awareness_since_last_dist.csv";
    public static final String SCORES_FILE       = "ELICIT_RUNS_scores.csv";
    public static final String MoM_FILE          = "ELICIT_RUNS_MoM.csv";
    public static final String AGILITY_FILE      = "ELICIT_RUNS_agility.csv";
    public static final String AGILITY_FILE_FIXED_DURATION  = "ELICIT_RUNS_agility_fixed_duration.csv";

    ExportLogFilesToCSV(java.io.File logFile, Thread thread) {
        m_logFile = logFile;
        parentThread = thread;
    }


    public void DeleteFile(File file) {
        if (file.exists()) file.delete();
    }
    public void WriteGeneralHeaders(File file) throws IOException {
        System.out.println("... create general file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
        //
        writer.write("Duration(sec)\t");
        writer.write("Duration(min)\t");
        writer.write("Start of Run(sec)\t");
        writer.write("Compression_Factor\t");
        writer.write("Signal_Noise_Ratio\t");
        writer.write("Total_Shares\t");
        writer.write("Total_Posts\t");
        writer.write("Total_Pulls\t");
        writer.write("Total_IDs\t");
        //
        writer.write("Workload_Rate\t");
        //
        writer.write("C2_Approach\t");
        writer.write("Date\t");
        writer.write("Factoid_Set\t");
        writer.write("Log_Filename\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteGeneralMetrics(File file, JFELICITMetricsVisualizer metrics) throws IOException  {
        if (!file.exists())
            WriteGeneralHeaders(file);
        //System.out.println("... write general metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        writer.write(Double.toString(metrics.m_trialData.m_trialInformation.m_durationSec)+"\t");
        writer.write(Integer.toString(metrics.m_trialData.m_trialInformation.m_durationMin)+"\t");
        writer.write(Double.toString(elicit.message.Message.m_timeStartOfRun)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_compression+"\t");
        //
        writer.write((double)(metrics.m_trialData.m_factoidStats.totalKey_Factoids
                     + metrics.m_trialData.m_factoidStats.totalExpertise_Factoids
                     + metrics.m_trialData.m_factoidStats.totalSupportive_Factoids)
                     /(double)metrics.m_trialData.m_factoidStats.totalFactoids+"\t");
        writer.write(metrics.m_trialData.m_overallStatistics.totalShares+"\t");
        writer.write(metrics.m_trialData.m_overallStatistics.totalPosts+"\t");
        writer.write(metrics.m_trialData.m_overallStatistics.totalPulls+"\t");
        writer.write(Integer.toString(metrics.m_trialData.m_overallStatistics.totalIDs)+"\t");
        //
        
        double avgWorkload = ( metrics.m_trialData.m_overallStatistics.totalShares
        		+ metrics.m_trialData.m_overallStatistics.totalPosts
        		+ metrics.m_trialData.m_overallStatistics.totalPulls ) 
        		/ (double)metrics.m_trialData.m_trialInformation.m_durationSec;
        writer.write(avgWorkload + "\t");
        //writer.write((metrics.jFSubjectsSocialNetworkTable.getAVGNbrSharesToProcess()
        //        +metrics.jFSubjectsSocialNetworkTable.getAVGNbrPostsToProcess())
        //        +"\t");
        
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_date+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.factoidSet+"\t");
        writer.write(metrics.m_logFile+"\t");
        for ( String agentName : metrics.m_trialData.m_agentFile )
            writer.write(agentName+"\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteInformationHeaders(File file, TrialData.OrganizationInformation organization) throws IOException {
        System.out.println("... create information file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
        //
        writer.write("KE_facts_shared\t");
        writer.write("Info_Accessible\t");
        writer.write("AVG_R_Info_Accessed\t");
        //
        writer.write("Sh_Info_Reach\t");
        writer.write("Sh_Info_Reached\t");
        //
        writer.write("C2_Approach\t");
        writer.write("Log_Filename\t");
        //
        for (TrialData.Subject subj : organization.m_memberList )
            writer.write("Info_Reached_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
        //SPECIFICS:
        //also for teams (if any)
        for (String team : organization.m_teamList )
            writer.write("TEAM_Sh_Info_Reach_for_"+team+"\t");
        //also for teams (if any)
        for (String team : organization.m_teamList )
            writer.write("TEAM_Sh_Info_Reached_for_"+team+"\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteInformationMetrics(File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteInformationHeaders(file, metrics.m_trialData.m_organizationInformation);
        //System.out.println("... write information metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //info
        writer.write( Double.toString(metrics.m_trialData.m_informationQuality.m_accessibilityIndex.m_accessIndexOverall.getLastElement().indexKE)+"\t" );
        writer.write(metrics.m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_accessIndexOverall.getLastElement().indexAll+"\t");
        writer.write(CalculateEffectivessMetrics.AVGRelevantInfoAccessed(metrics.m_trialData)+"\t");
        //SH info accessible (server) + reach + reached
        if (metrics.m_trialData.m_informationQuality.m_accessibilityIndex.m_set2AccessIndexOverall.getLastElement()!=null)
            writer.write(metrics.m_trialData.m_informationQuality.m_accessibilityIndex.m_set2AccessIndexOverall.getLastElement().indexAll+"\t");
        else
            writer.write("-\t");
        if (metrics.m_trialData.m_informationQuality.m_reachedIndex.m_set2AccessIndexOverall.getLastElement()!=null)
            writer.write(metrics.m_trialData.m_informationQuality.m_reachedIndex.m_set2AccessIndexOverall.getLastElement().indexAll+"\t");
        else
            writer.write("-\t");
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        //
        //Subjects
        for (TrialData.Subject subj : metrics.m_trialData.m_organizationInformation.m_memberList )
            writer.write(metrics.m_trialData.m_informationQuality.m_reachedIndex.m_informationAccessibleBySubjects.get(subj.m_personName).getLastElement().indexAll+"\t");
        //also for teams (NOT FOR EDGE)
        //if ( metrics.m_trialData.m_organizationInformation.m_teamList.size()!=0 ) {
        if ( !metrics.m_trialData.m_organizationInformation.m_organizationName.equals(elicit.message.Message.m_orgType_EDGE) ) {
            for (String team : metrics.m_trialData.m_organizationInformation.m_teamList )
                if (metrics.m_trialData.m_informationQuality.m_accessibilityIndex.m_set2InformationAccessibleByTeams.get(team)!=null
                    && metrics.m_trialData.m_informationQuality.m_accessibilityIndex.m_set2InformationAccessibleByTeams.get(team).getLastElement()!=null)
                    writer.write(metrics.m_trialData.m_informationQuality.m_accessibilityIndex.m_set2InformationAccessibleByTeams.get(team).getLastElement().indexAll+"\t");
                else
                    writer.write("-\t");

            //also for teams (if any)
            for (String team : metrics.m_trialData.m_organizationInformation.m_teamList )
                if (metrics.m_trialData.m_informationQuality.m_reachedIndex.m_set2InformationAccessibleByTeams.get(team)!=null)
                    writer.write(metrics.m_trialData.m_informationQuality.m_reachedIndex.m_set2InformationAccessibleByTeams.get(team).getLastElement().indexAll+"\t");
                else
                    writer.write("-\t");

        }
        writer.write("\n");
        writer.close();
    }

    public void WriteInteractionsHeaders(File file, TrialData.OrganizationInformation organization) throws IOException {
        System.out.println("... create interactions file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
       //
        writer.write("AVG S_P_P_per_node\t");
        writer.write("STD S_P_P_per_node\t");
        //
        writer.write("SHARE-POST ratio\t");
        //
        writer.write("in-out team\t");
        //
        writer.write("AVG_net_reach\t");
        writer.write("STD_net_reach\t");
        //
        writer.write("C2_Approach\t");
        writer.write("Log_Filename\t");
        //
        for (TrialData.Subject subj : organization.m_memberList )
            writer.write("S_P_P_"+subj.m_personName+"("+subj.m_teamName+")\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteInteractionsMetrics(File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteInteractionsHeaders(file, metrics.m_trialData.m_organizationInformation);
        //System.out.println("... write interactions metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");

        //calculate S_P_P per node
        double N = (double)metrics.m_trialData.m_organizationInformation.m_memberList.size();
        double AVG_S_P_P = (double)( metrics.m_trialData.m_overallStatistics.totalShares
                               + metrics.m_trialData.m_overallStatistics.totalPosts
                               + metrics.m_trialData.m_overallStatistics.totalPulls ) / N;
        double SHARE_POST_ratio = (double)metrics.m_trialData.m_overallStatistics.totalShares /
                                    (double)( metrics.m_trialData.m_overallStatistics.totalShares
                                              + metrics.m_trialData.m_overallStatistics.totalPosts);
        double IN_OUT_TEAM_ratio = metrics.m_trialData.m_overallStatistics.withinTeam /
                                        (metrics.m_trialData.m_overallStatistics.withinTeam
                                         + metrics.m_trialData.m_overallStatistics.outsideTeam);
        //calculate STD
        double STD_S_P_P = 0.0;
        for (TrialData.Subject s : metrics.m_trialData.m_organizationInformation.m_memberList) {
            double x = metrics.m_trialData.m_overallInteractions.get(s.m_personName).totalShares
                            + metrics.m_trialData.m_overallInteractions.get(s.m_personName).totalPosts
                            + metrics.m_trialData.m_overallInteractions.get(s.m_personName).totalPulls;
            STD_S_P_P += ( x - AVG_S_P_P ) * ( x - AVG_S_P_P ) / N ;
        }
        STD_S_P_P = Math.sqrt( STD_S_P_P );
        //net reach

        //
        writer.write(AVG_S_P_P+"\t");
        writer.write(STD_S_P_P+"\t");
        writer.write(SHARE_POST_ratio+"\t");
        writer.write(IN_OUT_TEAM_ratio+"\t");

        //use social table - calculate % reach
        //JFSubjectsReciprocityRateTable

        JFSubjectsReciprocityRateTable recipTable = new JFSubjectsReciprocityRateTable(metrics.m_trialData);
        javax.swing.JTable table = recipTable.getTable();
        double AVG_reach = 0.0;
        //parse all cols, parse all rows
        for (int row = 0; row < N; row++) {
            double recip = 0.0;
            for (int col = 1; col <= N; col++)
                if ( (row+1) != col && table.getValueAt(row, col)!=null) {
                    String content = (String)table.getValueAt(row, col);
                    if (content.indexOf('|')!=-1)
                        recip++;
                }
            recip = recip / (N-1);
            AVG_reach += recip;
        }
        AVG_reach = AVG_reach / N;
        //
        double STD_reach = 0.0;
        for (int row = 0; row < N; row++) {
            double recip = 0.0;
            for (int col = 1; col <= N; col++)
                if ( (row+1) != col && table.getValueAt(row, col)!=null) {
                    String content = (String)table.getValueAt(row, col);
                    if (content.indexOf('|')!=-1)
                        recip++;
                }
            recip = recip / (N-1);
            STD_reach += ( recip - AVG_reach ) * ( recip - AVG_reach ) / N ;
        }
        STD_reach = Math.sqrt( STD_reach );
        //
        writer.write(AVG_reach+"\t");
        writer.write(STD_reach+"\t");
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        //
        //Subjects
        for (TrialData.Subject subj : metrics.m_trialData.m_organizationInformation.m_memberList ) {
            double s_p_p = metrics.m_trialData.m_overallInteractions.get(subj.m_personName).totalShares
                            + metrics.m_trialData.m_overallInteractions.get(subj.m_personName).totalPosts
                            + metrics.m_trialData.m_overallInteractions.get(subj.m_personName).totalPulls;
            writer.write(s_p_p+"\t");
        }
        writer.write("\n");
        writer.close();
    }

    public void WriteAwarenessHeaders(File file, TrialData.OrganizationInformation organization) throws IOException {
        System.out.println("... create awareness file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
        //
        writer.write("Time_First_Correct_ID_OVERALL\t");
        //
        writer.write("Nbr_Correct_IDs_OVERALL\t");
        writer.write("Nbr_Correct_IDs_WHO\t");
        writer.write("Nbr_Correct_IDs_WHAT\t");
        writer.write("Nbr_Correct_IDs_WHERE\t");
        writer.write("Nbr_Correct_IDs_WHEN\t");
        //
        writer.write("CCSYNC\t");
        writer.write("Uncertainty_CCSYNC\t");
        //
        for (TrialData.Subject subj : organization.m_memberList ) {
            writer.write("Q_ID_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("T_ID_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("Q_ID_WHO_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("T_ID_WHO_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("Q_ID_WHAT_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("T_ID_WHAT_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("Q_ID_WHERE_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("T_ID_WHERE_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("Q_ID_WHEN_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
            writer.write("T_ID_WHEN_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
        }
        //
        writer.write("C2_Approach\t");
        writer.write("Log_Filename\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteAwarenessMetrics(File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteAwarenessHeaders(file, metrics.m_trialData.m_organizationInformation);
        //System.out.println("... write awareness metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_firstAllCorrectID+"\t");
        //
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWho.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhat.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhere.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhen.lastElement().y+"\t");
        //
        if (metrics.m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.size()!=0
            && metrics.m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.size()!=0
            && metrics.m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.lastElement()!=null
            && metrics.m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.lastElement()!=null )
        {
            writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.lastElement().y+"\t");
            writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.lastElement().y+"\t");
        }
        else { writer.write("-\t-\t"); }
        //
        for (TrialData.Subject subj : metrics.m_trialData.m_organizationInformation.m_memberList) {
            if ( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName)!=null
                 && !metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).isEmpty() ) {

            	IDsQualityMap.AwUndData dOverall = metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement();
            	if (dOverall==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dOverall.overallQ+"\t");
            		writer.write(dOverall.time+"\t");
            	}
            	IDsQualityMap.AwUndData dWho = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhoID(subj.m_personName);
            	if (dWho==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWho.whoQ+"\t");
            		writer.write(dWho.time+"\t");
            	}
            	IDsQualityMap.AwUndData dWhat = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhatID(subj.m_personName);
            	if (dWhat==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWhat.whatQ+"\t");
            		writer.write(dWhat.time+"\t");
            	}
            	IDsQualityMap.AwUndData dWhere = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(subj.m_personName);
            	if (dWhere==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWhere.whereQ+"\t");
            		writer.write(dWhere.time+"\t");
            	}
            	IDsQualityMap.AwUndData dWhen = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhenID(subj.m_personName);
            	if (dWhen==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWhen.whenOverallQ+"\t");
            		writer.write(dWhen.time+"\t");
            	}
            	
            	//System.out.println(subj.m_personName+" "+dWho);
            	
            	/*
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().overallQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whoQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whatQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whereQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whenOverallQ+"\t" );
                //writer.write("Q_ID_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
                
                 */
            }
            else {
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
            }
        }
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteAwarenessMetricsSinceLastDistribution(File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteAwarenessHeaders(file, metrics.m_trialData.m_organizationInformation);
        //System.out.println("... write awareness metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_firstAllCorrectID+"\t");
        //
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWho.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhat.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhere.lastElement().y+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhen.lastElement().y+"\t");
        //
        if (metrics.m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.size()!=0
            && metrics.m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.size()!=0
            && metrics.m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.lastElement()!=null
            && metrics.m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.lastElement()!=null )
        {
            writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.lastElement().y+"\t");
            writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.lastElement().y+"\t");
        }
        else { writer.write("-\t-\t"); }
        //
        for (TrialData.Subject subj : metrics.m_trialData.m_organizationInformation.m_memberList) {
            if ( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName)!=null
                 && !metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).isEmpty() ) {

            	IDsQualityMap.AwUndData dOverall = metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement();
            	if (dOverall==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dOverall.overallQ+"\t");
            		writer.write((dOverall.time-metrics.m_trialData.m_trialInformation.timeOfLastFactoid)+"\t");
            	}
            	IDsQualityMap.AwUndData dWho = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhoID(subj.m_personName);
            	if (dWho==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWho.whoQ+"\t");
            		writer.write((dWho.time-metrics.m_trialData.m_trialInformation.timeOfLastFactoid)+"\t");
            	}
            	IDsQualityMap.AwUndData dWhat = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhatID(subj.m_personName);
            	if (dWhat==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWhat.whatQ+"\t");
            		writer.write((dWhat.time-metrics.m_trialData.m_trialInformation.timeOfLastFactoid)+"\t");
            	}
            	IDsQualityMap.AwUndData dWhere = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(subj.m_personName);
            	if (dWhere==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWhere.whereQ+"\t");
            		writer.write((dWhere.time-metrics.m_trialData.m_trialInformation.timeOfLastFactoid)+"\t");
            	}
            	IDsQualityMap.AwUndData dWhen = metrics.m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhenID(subj.m_personName);
            	if (dWhen==null) {
            		writer.write("-\t-\t");
            	}
            	else {
            		writer.write(dWhen.whenOverallQ+"\t");
            		writer.write((dWhen.time-metrics.m_trialData.m_trialInformation.timeOfLastFactoid)+"\t");
            	}
            	
            	//System.out.println(subj.m_personName+" "+dWho);
            	
            	/*
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().overallQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whoQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whatQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whereQ+"\t" );
                writer.write( metrics.m_trialData.m_subjectsIDsQualityMap.get(subj.m_personName).lastElement().whenOverallQ+"\t" );
                //writer.write("Q_ID_for_"+subj.m_personName+"("+subj.m_teamName+")\t");
                
                 */
        		
            }
            else {
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
                writer.write( "-\t" );
            }
        }
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
		writer.write("time of last factoid dist: "+metrics.m_trialData.m_trialInformation.timeOfLastFactoid);
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteScoresHeaders(File file) throws IOException {
        System.out.println("... create scores file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
        //
        for ( double scale : elicit.tools.JFMoMSharedInformationAccessedTable.m_percentagesSteps )
            writer.write("R_InfoReached_Score_"+scale+"\t");
        for ( double scale : elicit.tools.JFMoMSharedInformationAccessedTable.m_percentagesSteps )
            writer.write("AVG_R_InfoReached_Score_"+scale+"\t");
        for ( double scale : elicit.tools.JFMoMCorrectSharedUnderstanding.m_percentagesSteps )
            writer.write("CorrectAw_Score_"+scale+"\t");
        //
        for ( double scale : elicit.tools.JFMoMCSSync.m_percentagesSteps )
            writer.write("CSSync_Score_"+scale+"\t");
        //
        for ( double scale : elicit.tools.JFMoMEffectiveness.m_percentagesSteps )
            writer.write("Effectiveness_Score_"+scale+"\t");
        //
        writer.write("C2_Approach\t");
        writer.write("Log_Filename\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteScoresMetrics(File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteScoresHeaders(file);
        //System.out.println("... write scores metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //
        //get table - must parse it...
        //SH INFO REACHED
        {
            int row = metrics.jFMoMSharedRelevantInformationAccessedTable.getRowIndexInTable(elicit.tools.JFMoMSharedRelevantInformationAccessedTable.m_overall);
            for ( double scale : elicit.tools.JFMoMSharedRelevantInformationAccessedTable.m_percentagesSteps ) {
                int col = metrics.jFMoMSharedInformationAccessedTable.getColIndexInTable(Double.toString(scale));
                Integer value = (Integer)metrics.jFMoMSharedInformationAccessedTable.getTable().getValueAt(row, col);
                if (value!=null)
                    writer.write(value+"\t");
                else
                    writer.write("-\t");
            }
        }
        //AVG INFO REACHED
        {
            int row = metrics.jFMoMAverageInformationAccessedTable.getRowIndexInTable(elicit.tools.JFMoMAverageInformationAccessedTable.m_overall);
            for ( double scale : elicit.tools.JFMoMAverageInformationAccessedTable.m_percentagesSteps ) {
                int col = metrics.jFMoMAverageInformationAccessedTable.getColIndexInTable(Double.toString(scale));
                Integer value = (Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col);
                if (value!=null)
                    writer.write(value+"\t");
                else
                    writer.write("-\t");
            }
        }
        //CORRECT AWAR
        {
            int row = metrics.jFMoMCorrectSharedUnderstandingTable.getRowIndexInTable(elicit.tools.JFMoMCorrectSharedUnderstanding.m_overall);
            for ( double scale : elicit.tools.JFMoMCorrectSharedUnderstanding.m_percentagesSteps ) {
                int col = metrics.jFMoMCorrectSharedUnderstandingTable.getColIndexInTable(Double.toString(scale));
                Integer value = (Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col);
                if (value!=null)
                    writer.write(value+"\t");
                else
                    writer.write("-\t");
            }
        }
        //entropy
        {
            int row = metrics.jFMoMCSSync.getRowIndexInTable(elicit.tools.JFMoMCSSync.m_overall);
            for ( double scale : elicit.tools.JFMoMCSSync.m_percentagesSteps ) {
                int col = metrics.jFMoMCSSync.getColIndexInTable(Double.toString(scale));
                Integer value = (Integer)metrics.jFMoMCSSync.getTable().getValueAt(row, col);
                if (value!=null)
                    writer.write(value+"\t");
                else
                    writer.write("-\t");
            }
        }
        //effectiveness
        {
            int row = metrics.jFMoMEffectiveness.getRowIndexInTable(elicit.tools.JFMoMEffectiveness.m_overall);
            for ( double scale : elicit.tools.JFMoMEffectiveness.m_percentagesSteps ) {
                int col = metrics.jFMoMEffectiveness.getColIndexInTable(Double.toString(scale));
                Integer value = (Integer)metrics.jFMoMEffectiveness.getTable().getValueAt(row, col);
                if (value!=null)
                    writer.write(value+"\t");
                else
                    writer.write("-\t");
            }
        }
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        //
        writer.write("\n");
        writer.close();
    }


    public void WriteMoMHeaders(File file) throws IOException {
        System.out.println("... create MoM file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
        //
        writer.write("Effectiveness\t");
        writer.write("Effect_time\t");

        writer.write("Efficiency_time_MM\t");
        writer.write("Efficiency_effort_MM\t");

        writer.write("AVG_correctness\t");
        writer.write("MAX_timeliness\t");
        writer.write("MAX_timeliness_effort\t");

        writer.write("best_score_AVG_R_Info_Accessed\t");
        writer.write("best_score_AVG_R_Info_Accessed_time\t");
        writer.write("best_score_ShInfo_R_accessed\t");
        writer.write("best_score_ShInfo_R_accessed_time\t");
        writer.write("best_score_ShAw\t");
        writer.write("best_score_ShAw_time\t");
        writer.write("best_score_CSSync\t");
        writer.write("best_score_CSSync_time\t");
        //
        writer.write("C2_Approach\t");
        writer.write("Log_Filename\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteMoMMetrics(File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteMoMHeaders(file);
        //System.out.println("... write MoM metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //
        java.awt.geom.Point2D.Double score = CalculateEffectivessMetrics.effectivenessScore(metrics.m_trialData);
        if (score!=null) {
            writer.write(score.x+"\t");
            writer.write(score.y+"\t");        	
        }
        //
        writer.write(CalculateEffectivessMetrics.CalculateTimeEfficiency(metrics.m_trialData)+"\t");
        writer.write(CalculateEffectivessMetrics.CalculateEffortEfficiency(metrics.m_trialData)+"\t");
        //
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_AVGCorrectAnswersALLWs+"\t");
        writer.write(CalculateEffectivessMetrics.CalculateMAXTimeliness(metrics.m_trialData)+"\t");
        writer.write(CalculateEffectivessMetrics.CalculateMAXTimelinessEffort(metrics.m_trialData)+"\t");
        //writer.write(CalculateEffectivessMetrics.CalculateAvgEfficiency(metrics.m_trialData)+"\t");
        java.awt.geom.Point2D.Double scoreAvgInfo = CalculateEffectivessMetrics.getBestAvgInfoAccessedScore(metrics);
        writer.write(scoreAvgInfo.x+"\t");
        writer.write(scoreAvgInfo.y+"\t");
        //
        java.awt.geom.Point2D.Double scoreShInfo = CalculateEffectivessMetrics.getBestShRInfoAccessedScore(metrics);
        writer.write(scoreShInfo.x+"\t");
        writer.write(scoreShInfo.y+"\t");
        //
        java.awt.geom.Point2D.Double scoreShAw = CalculateEffectivessMetrics.getBestAwShScore(metrics);
        writer.write(scoreShAw.x+"\t");
        writer.write(scoreShAw.y+"\t");
        //
        java.awt.geom.Point2D.Double scoreCSSync = CalculateEffectivessMetrics.getBestCCsyncScore(metrics);
        writer.write(scoreCSSync.x+"\t");
        writer.write(scoreCSSync.y+"\t");
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        //
        writer.write("\n");
        writer.close();
    }

    public void WriteAgilityHeaders(File file) throws IOException {
        System.out.println("... create Agility file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Number\t");
        writer.write("Run_Name\t");
        //
        writer.write("Shared_Awareness\t");
        writer.write("AVG_correctness\t");
        writer.write("MAX_timeliness\t");
        //
        for ( double scale : elicit.tools.JFMoMAverageInformationAccessedTable.m_percentagesSteps )
            writer.write("AVG_InfoReached_Score_"+scale+"\t");
        for ( double scale : elicit.tools.JFMoMCorrectSharedUnderstanding.m_percentagesSteps )
            writer.write("CorrectAw_Score_"+scale+"\t");
        for ( double scale : elicit.tools.JFMoMEffectiveness.m_percentagesSteps )
            writer.write("Effectiveness_Score_"+scale+"\t");
        //
        writer.write("C2_Approach\t");
        writer.write("Log_Filename\t");
        //
        writer.write("\n");
        writer.close();
    }

    private void WriteAgilityMetrics (File file, JFELICITMetricsVisualizer metrics) throws IOException {
        if (!file.exists())
            WriteAgilityHeaders(file);

        //System.out.println("... write MoM metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //
        //java.awt.geom.Point2D.Double scoreShAw = CalculateEffectivessMetrics.getBestAwShScore(metrics);
        //writer.write(scoreShAw.x+"\t");
        writer.write(CalculateEffectivessMetrics.effectivenessScoreAVGAll(metrics.m_trialData).x+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_AVGCorrectAnswersALLWs+"\t");
        writer.write(CalculateEffectivessMetrics.CalculateMAXTimeliness(metrics.m_trialData)+"\t");
        //
        // AGILITY MEASURES USE TIMELINESS INSTEAD OF TIME
        //
        //AVG INFO REACHED
        {
            int row = metrics.jFMoMAverageInformationAccessedTable.getRowIndexInTable(elicit.tools.JFMoMAverageInformationAccessedTable.m_overall);
            for ( double scale : elicit.tools.JFMoMAverageInformationAccessedTable.m_percentagesSteps ) {
                int col = metrics.jFMoMAverageInformationAccessedTable.getColIndexInTable(Double.toString(scale));

                //Integer value = (Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col);
                double timelinessV = 0.0;
                if (metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col)!=null)
                    timelinessV = 1.0 - ((Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col)).doubleValue() / (double)(metrics.m_trialData.m_trialInformation.m_durationSec) ;

                if (timelinessV!=0.0)
                    writer.write(timelinessV+"\t");
                else
                    writer.write("-\t");
            }
        }
        //CORRECT AWAR
        {
            int row = metrics.jFMoMCorrectSharedUnderstandingTable.getRowIndexInTable(elicit.tools.JFMoMCorrectSharedUnderstanding.m_overall);
            for ( double scale : elicit.tools.JFMoMCorrectSharedUnderstanding.m_percentagesSteps ) {
                int col = metrics.jFMoMCorrectSharedUnderstandingTable.getColIndexInTable(Double.toString(scale));

                //Integer value = (Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col);
                double timelinessV = 0.0;
                if (metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col)!=null)
                    timelinessV = 1.0 - ((Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col)).doubleValue() / (double)(metrics.m_trialData.m_trialInformation.m_durationSec) ;

                if (timelinessV!=0.0)
                    writer.write(timelinessV+"\t");
                else
                    writer.write("-\t");
            }
        }
        //effectiveness
        {
            int row = metrics.jFMoMEffectiveness.getRowIndexInTable(elicit.tools.JFMoMEffectiveness.m_overall);
            for ( double scale : elicit.tools.JFMoMEffectiveness.m_percentagesSteps ) {
                int col = metrics.jFMoMEffectiveness.getColIndexInTable(Double.toString(scale));

                //Integer value = (Integer)metrics.jFMoMEffectiveness.getTable().getValueAt(row, col);
                double timelinessV = 0.0;
                if (metrics.jFMoMEffectiveness.getTable().getValueAt(row, col)!=null)
                    timelinessV = 1.0 - ((Integer)metrics.jFMoMEffectiveness.getTable().getValueAt(row, col)).doubleValue() / (double)(metrics.m_trialData.m_trialInformation.m_durationSec) ;

                if (timelinessV!=0.0)
                    writer.write(timelinessV+"\t");
                else
                    writer.write("-\t");
            }
        }
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        //
        writer.write("\n");
        writer.close();
    }

    private void WriteAgilityMetricsFixedDuration (File file, JFELICITMetricsVisualizer metrics) throws IOException {

    	//todo: HACK here....
    	double maxRunDurationSec = 300.0;
    	System.out.println("################################################");
    	System.out.println("##");
    	System.out.println("##");
    	System.out.println("##  AGILITY MAPS FIXED DURATION - SET DURATION TO "+maxRunDurationSec);
    	System.out.println("##");

    	
    	if (!file.exists())
            WriteAgilityHeaders(file);

        //System.out.println("... write MoM metrics "+file.getName());
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(OrganizationMessage.getLevelNumber(metrics.m_trialData.m_organizationInformation.m_organizationName)+"\t");
        writer.write(metrics.m_trialData.m_trialInformation.m_runname+"\t");
        //
        //java.awt.geom.Point2D.Double scoreShAw = CalculateEffectivessMetrics.getBestAwShScore(metrics);
        //writer.write(scoreShAw.x+"\t");
        writer.write(CalculateEffectivessMetrics.effectivenessScoreAVGAll(metrics.m_trialData).x+"\t");
        writer.write(metrics.m_trialData.m_subjectsIDsQualityMap.m_AVGCorrectAnswersALLWs+"\t");
        writer.write(CalculateEffectivessMetrics.CalculateMAXTimeliness(metrics.m_trialData)+"\t");
        //
        // AGILITY MEASURES USE TIMELINESS INSTEAD OF TIME
        //
        //AVG INFO REACHED
        {
            int row = metrics.jFMoMAverageInformationAccessedTable.getRowIndexInTable(elicit.tools.JFMoMAverageInformationAccessedTable.m_overall);
            for ( double scale : elicit.tools.JFMoMAverageInformationAccessedTable.m_percentagesSteps ) {
                int col = metrics.jFMoMAverageInformationAccessedTable.getColIndexInTable(Double.toString(scale));

                //Integer value = (Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col);
                double timelinessV = 0.0;
                if (metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col)!=null)
                    timelinessV = 1.0 - ((Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col)).doubleValue() / maxRunDurationSec ;
                	//timelinessV = 1.0 - ((Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col)).doubleValue() / (double)(metrics.m_trialData.m_trialInformation.m_durationSec) ;

                if (timelinessV!=0.0)
                    writer.write(timelinessV+"\t");
                else
                    writer.write("-\t");
            }
        }
        //CORRECT AWAR
        {
            int row = metrics.jFMoMCorrectSharedUnderstandingTable.getRowIndexInTable(elicit.tools.JFMoMCorrectSharedUnderstanding.m_overall);
            for ( double scale : elicit.tools.JFMoMCorrectSharedUnderstanding.m_percentagesSteps ) {
                int col = metrics.jFMoMCorrectSharedUnderstandingTable.getColIndexInTable(Double.toString(scale));

                //Integer value = (Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col);
                double timelinessV = 0.0;
                if (metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col)!=null)
                    timelinessV = 1.0 - ((Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col)).doubleValue() / maxRunDurationSec ;
                    //timelinessV = 1.0 - ((Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col)).doubleValue() / (double)(metrics.m_trialData.m_trialInformation.m_durationSec) ;

                if (timelinessV!=0.0)
                    writer.write(timelinessV+"\t");
                else
                    writer.write("-\t");
            }
        }
        //effectiveness
        {
            int row = metrics.jFMoMEffectiveness.getRowIndexInTable(elicit.tools.JFMoMEffectiveness.m_overall);
            for ( double scale : elicit.tools.JFMoMEffectiveness.m_percentagesSteps ) {
                int col = metrics.jFMoMEffectiveness.getColIndexInTable(Double.toString(scale));

                //Integer value = (Integer)metrics.jFMoMEffectiveness.getTable().getValueAt(row, col);
                double timelinessV = 0.0;
                if (metrics.jFMoMEffectiveness.getTable().getValueAt(row, col)!=null)
                    timelinessV = 1.0 - ((Integer)metrics.jFMoMEffectiveness.getTable().getValueAt(row, col)).doubleValue() / maxRunDurationSec ;
                    //timelinessV = 1.0 - ((Integer)metrics.jFMoMEffectiveness.getTable().getValueAt(row, col)).doubleValue() / (double)(metrics.m_trialData.m_trialInformation.m_durationSec) ;

                if (timelinessV!=0.0)
                    writer.write(timelinessV+"\t");
                else
                    writer.write("-\t");
            }
        }
        //
        writer.write(metrics.m_trialData.m_organizationInformation.m_organizationName+"\t");
        writer.write(metrics.m_logFile+"\t");
        writer.write("fixed duration to: "+maxRunDurationSec);
        //
        writer.write("\n");
        writer.close();
    }

    private void writeMetrics(File file,
                            File general_file,
                            File information_file,
                            File interactions_file,
                            File awareness_file,
                            File awareness_file_dist,
                            File scores_file,
                            File mom_file,
                            File agility_file,
                            File agility_file_fixed_duration) throws IOException
    {
        JFELICITMetricsVisualizer metrics = new JFELICITMetricsVisualizer(file.getPath());
        //extract metrics
        WriteGeneralMetrics(general_file, metrics);
        WriteInformationMetrics(information_file, metrics);
        WriteInteractionsMetrics(interactions_file, metrics);
        WriteAwarenessMetrics(awareness_file, metrics);
        WriteAwarenessMetricsSinceLastDistribution(awareness_file_dist, metrics);
        WriteScoresMetrics(scores_file, metrics);
        WriteMoMMetrics(mom_file, metrics);

        WriteAgilityMetrics(agility_file, metrics);
        WriteAgilityMetricsFixedDuration(agility_file_fixed_duration, metrics);
        
        //System.out.println(". log file: "+file.getName());

        //release resources ...
        metrics.dispose();
        metrics = null;
    }

    public void ProcessLog () {
        File general_file     = new File(m_logFile.getParent()+File.separator+GENERAL_FILE);
        //DeleteFile(general_file);
        File information_file = new File(m_logFile.getParent()+File.separator+INFORMATION_FILE);
        //DeleteFile(information_file);
        File interactions_file= new File(m_logFile.getParent()+File.separator+INTERACTIONS_FILE);
        //DeleteFile(interactions_file);
        File awareness_file   = new File(m_logFile.getParent()+File.separator+AWARENESS_FILE);
        //DeleteFile(awareness_file);
        File awareness_file_dist= new File(m_logFile.getParent()+File.separator+AWARENESS_FILE_SINCE_DIST);
        File scores_file      = new File(m_logFile.getParent()+File.separator+SCORES_FILE);
        //DeleteFile(scores_file);
        File mom_file         = new File(m_logFile.getParent()+File.separator+MoM_FILE);
        //DeleteFile(mom_file);
        File agility_file     = new File(m_logFile.getParent()+File.separator+AGILITY_FILE);
        //DeleteFile(agility_file);
        File agility_file_fixed_duration = new File(m_logFile.getParent()+File.separator+AGILITY_FILE_FIXED_DURATION);
        //DeleteFile(agility_file);

        try {
            if (parentThread!=null) {
                parentThread.join();
            }

           //
           System.out.println(". processing log file: "+m_logFile);

           writeMetrics(m_logFile,
                    general_file,
                    information_file,
                    interactions_file,
                    awareness_file,
                    awareness_file_dist,
                    scores_file,
                    mom_file,
                    agility_file,
                    agility_file_fixed_duration);

            Runtime.getRuntime().freeMemory();
            System.gc();
            System.out.println("Total memory="+Runtime.getRuntime().totalMemory()+" Free memory="+Runtime.getRuntime().freeMemory());
        }
        catch (Exception ex) {
            System.err.println("An exception was raised: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        ProcessLog();
    }

    public static void main(String[] args) {
        //ELICITMainWindow jfMain = new ELICITMainWindow();
        try {
            //JFConvertELICITLog jfMain = new JFConvertELICITLog();
            ExportLogFilesToCSV process = new ExportLogFilesToCSV(new File(args[1]), null);
            process.ProcessLog();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
