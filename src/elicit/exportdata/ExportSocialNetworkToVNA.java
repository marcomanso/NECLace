/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import elicit.message.Message;
import java.io.FileWriter;

import elicit.message.TrialData;
import elicit.message.OrganizationMessage;
import metrics.interaction.InteractionRates.ReciprocityRate;

/**
 *
 * @author mmanso
 */
public class ExportSocialNetworkToVNA {

    //
    final private static int WIDTH = 600;
    final private static int HEIGHT = 600;
    final private static String MEMBER_COLOR = "255";
    final private static String TEAMLEADER_COLOR = "16744576";
    final private static String CTC_COLOR = "16776960";
    final private static String WEBSITES_COLOR = "65535";
    //
    private TrialData m_trialData;

    public ExportSocialNetworkToVNA(TrialData trialData) {
        m_trialData = trialData;
    }

    private double getCenterX () {
        return WIDTH / 2.0;
        //return jgraph.getPreferredSize().width/2.0;
    }

    private double getCenterY () {
        return HEIGHT / 2.0;
        //return jgraph.getPreferredSize().height/2.0;
    }

    private double getInnerRadius () {
        if (WIDTH < HEIGHT)
            return WIDTH * 1.2/6.0;
        else
            return HEIGHT * 1.2/6.0;
    }

    private double getOuterRadius () {
        if (WIDTH < HEIGHT)
            return WIDTH * 2.1/6.0;
        else
            return HEIGHT * 2.1/6.0;
    }

    private double getSitesOuterRadius () {
        if (WIDTH < HEIGHT)
            return WIDTH * 0.9/2.0;
        else
            return HEIGHT * 0.9/2.0;
    }

    private void WriteOrgStrucutreAtTime(FileWriter writer, double time, boolean includeSites) throws java.io.IOException {
        //determine organization structure: teams, team leaders and CTC - or - edge
        //it is assumed nbr subjects is 17: if not edge - 1 CTC, 4 TL and 12 TM
        if (OrganizationMessage.isEdge(m_trialData.m_organizationInformation.m_organizationName)) {
            WriteEdgeAtTime(writer, time, includeSites);
        }
        else {
            WriteStructuredOrganizationAtTime(writer, time, includeSites);
        }
    }

    private void WriteEdgeAtTime (FileWriter writer, double time, boolean includeSites) throws java.io.IOException {
        //add vertexes
        double incrementAngle = 2.0 * Math.PI / (double)m_trialData.m_organizationInformation.m_memberList.size() ;
        double center_x = getCenterX();
        double center_y = getCenterY();
        double currentAngle = 0.0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            String text = new String("");
            //addVertex(s.m_personName);
            int x = (int)(getOuterRadius()*Math.cos(currentAngle) + center_x);
            int y = (int)(getOuterRadius()*Math.sin(currentAngle) + center_y);
            text += "\"" + s.m_personName +"\" "
                          + x + " " //pos
                          + y + " " //pos
                          + MEMBER_COLOR + " " //color
                          + "1 8 " // shape and size
                          + "\"" + s.m_personName + "\" " //labeltext
                          + "11 0 3 5 TRUE"; //labelsize labelcolor gapx gapy active
            currentAngle+=incrementAngle;
            writer.write(text+"\n");
        }
        if (includeSites) {
            //incrementAngle = 2.0 * Math.PI / (double)m_trialData.m_organizationInformation.m_teamList.size() ;
            incrementAngle = 2.0 * Math.PI / (double)Message.m_webSitesList.length;
            currentAngle = Math.PI / 4.0 ;
            //for (String team : m_trialData.m_organizationInformation.m_teamList) {
            for (String team : Message.m_webSitesList) {
                String text = new String("");
                int x = (int)(getInnerRadius()*Math.cos(currentAngle) + center_x);
                int y = (int)(getInnerRadius()*Math.sin(currentAngle) + center_y);
                text += "\"" + team +"\" "
                              +x + " " //pos
                              + y + " " //pos
                              + WEBSITES_COLOR + " " //color
                              + "1 8 " // shape and size
                              + "\"" + team + "\" " //labeltext
                              + "11 0 3 5 TRUE"; //labelsize labelcolor gapx gapy active
                writer.write(text+"\n");
                currentAngle+=incrementAngle;
            }
        }
    }

    private void WriteStructuredOrganizationAtTime (FileWriter writer, double time, boolean includeSites) throws java.io.IOException {
        //assume:
        // -ctc role
        // -tl role
        // -tm role
        double center_x = getCenterX();
        double center_y = getCenterY();
        //process CTC
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            if (s.m_isOverallCoordinator) {
                String text = new String("");
                text += "\"" + s.m_personName +"\" "
                              + center_x + " " //pos
                              + center_y + " " //pos
                              + CTC_COLOR + " " //color
                              + "1 8 " // shape and size
                              + "\"" + s.m_personName + "\" " //labeltext
                              + "11 0 3 5 TRUE"; //labelsize labelcolor gapx gapy active
                writer.write(text+"\n");
            }
        }
        //process TLs and TMs
        double incrementAngle = 2.0 * Math.PI / (double)m_trialData.getNbrTeamLeaders();
        double currentAngle = Math.PI / 4.0;
        //
        double const_angleOffsetStartTM = - Math.PI / 6.0;
        double incrementAngleTM = 0.0;
        double currentAngleTM = 0.0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            //find leader
            if (s.m_isTeamLeader) {
                String text = new String("");
                int x = (int)(getInnerRadius()*Math.cos(currentAngle) + center_x);
                int y = (int)(getInnerRadius()*Math.sin(currentAngle) + center_y);
                text += "\"" + s.m_personName +"\" "
                              + x + " " //pos
                              + y + " " //pos
                              + TEAMLEADER_COLOR + " " //color
                              + "1 8 " // shape and size
                              + "\"" + s.m_personName + "\" " //labeltext
                              + "11 0 3 5 TRUE"; //labelsize labelcolor gapx gapy active
                writer.write(text+"\n");
                //now handle members
                int nbrMembers = m_trialData.getNbrTeamMembers(s.m_teamName);
                if (nbrMembers > 2) {
                    incrementAngleTM = const_angleOffsetStartTM * 2.0 / (double)(nbrMembers-2);
                    currentAngleTM = currentAngle + const_angleOffsetStartTM;
                }
                else {
                    incrementAngleTM = 0.0;
                    currentAngleTM = currentAngle;
                }
                //second loop to get team members
                for (TrialData.Subject member : m_trialData.m_organizationInformation.m_memberList) {
                    if (member.m_teamName.equalsIgnoreCase(s.m_teamName) && !member.m_isTeamLeader) {
                        String textM = new String("");
                        int member_x = (int)(getOuterRadius()*Math.cos(currentAngleTM) + center_x);
                        int member_y = (int)(getOuterRadius()*Math.sin(currentAngleTM) + center_y);
                        textM += "\"" + member.m_personName +"\" "
                                      + member_x + " " //pos
                                      + member_y + " " //pos
                                      + MEMBER_COLOR + " " //color
                                      + "1 8 " // shape and size
                                      + "\"" + member.m_personName + "\" " //labeltext
                                      + "11 0 3 5 TRUE"; //labelsize labelcolor gapx gapy active
                        writer.write(textM+"\n");
                        currentAngleTM -= incrementAngleTM;
                    }
                }
                //set currentAngleOffsetTM
                //move to next TL angle
                currentAngle+=incrementAngle;
            }
        }
        if (includeSites) {
            //process Sites / Teams
            //incrementAngle = 2.0 * Math.PI / (double)m_trialData.m_organizationInformation.m_teamList.size() ;
            incrementAngle = 2.0 * Math.PI / (double)Message.m_webSitesList.length;
            currentAngle = Math.PI / (4.0-1.0) ; //put some tilt so that CTC and TL lines do not overlap
            //for (String team : m_trialData.m_organizationInformation.m_teamList) {
            for (String team : Message.m_webSitesList) {
                String text = new String("");
                int x = (int)(getSitesOuterRadius()*Math.cos(currentAngle) + center_x);
                int y = (int)(getSitesOuterRadius()*Math.sin(currentAngle) + center_y);
                text += "\"" + team +"\" "
                              +x + " " //pos
                              + y + " " //pos
                              + WEBSITES_COLOR + " " //color
                              + "1 8 " // shape and size
                              + "\"" + team + "\" " //labeltext
                              + "11 0 3 5 TRUE"; //labelsize labelcolor gapx gapy active
                writer.write(text+"\n");
                //
                currentAngle+=incrementAngle;
            }
        }
    }

    private void WriteConnections (FileWriter writer, double time, boolean includeSites) throws java.io.IOException {
        //draw
        for ( String srcName : m_trialData.m_interactions.m_socialInterations.keySet() ) {
            int nbrInteractions = 0;
            for ( String dstName : m_trialData.m_interactions.m_socialInterations.get(srcName).keySet() ) {
                elicit.message.TrialData.Subject srcSbj = m_trialData.getOrganizationSubject(srcName);
                elicit.message.TrialData.Subject dstSbj = m_trialData.getOrganizationSubject(dstName);

                if (includeSites
                    || (!includeSites && srcSbj!=null && dstSbj!=null )  )
                {
                    for (ReciprocityRate rr : m_trialData.m_interactions.m_socialInterations.get(srcName).get(dstName))
                        if (rr.time <= time)
                            nbrInteractions=rr.totalSent;
                        else
                            break;
                    //ok - now write
                    if (nbrInteractions!=0)
                        writer.write(srcName+" "+dstName+" "+nbrInteractions+" 1\n");
                }
            }
        }
    }    

    public void writeVNAFile(java.io.File vnaFile, double time, boolean includeSites) {
        FileWriter writer = null;

        try {
            //output file
            writer = new FileWriter(vnaFile);

            //write template data
            writer.write("*file:"+vnaFile.getAbsolutePath()+"\n");
            writer.write("*org-name:"+m_trialData.m_organizationInformation.m_organizationName+" set:"+m_trialData.m_trialInformation.factoidSet+"\n");
            writer.write("*time:"+time+"(sec)\n");
            writer.write("*Node properties\n");
            writer.write("ID x y color shape size labeltext labelsize labelcolor gapx gapy active\n");
            WriteOrgStrucutreAtTime(writer, time, includeSites);
            //
            writer.write("*Tie data\n");
            writer.write("from to friends strength\n");
            //
            WriteConnections(writer, time, includeSites);

        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    } //writeVNAFile
}
 
