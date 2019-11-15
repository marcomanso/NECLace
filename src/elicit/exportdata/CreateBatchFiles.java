/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import elicit.message.TrialData;
import elicit.message.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author marcomanso
 */
public class CreateBatchFiles {

    public static String label_agentsFile[] = {"BASELINE", "HIGH", "LOW"};
    public static String agentsFile[] = { "SenseMaking_Agent2.4_N2C2M2_BASELINE.txt",
                                          "SenseMaking_Agent2.4_N2C2M2_high.txt",
                                          "SenseMaking_Agent2.4_N2C2M2_low.txt"};

    public static String label_problem[] = {"NORMAL", "EASY", "DIFFICULT"};
    public static String problem[] = { "factoidset1a5-17.txt",
                                       "factoidset1a5-easy-17.txt",
                                       "factoidset1a5-difficult-17.txt"};

    public static final int INDEX_COORDINATED = 0;
    public static final int INDEX_COLLABORATIVE = 1;
    public static final int INDEX_COLLABORATIVE2 = 2;
    public static final int INDEX_EDGE = 3;
    public static String label_organization[] = {"3", "4", "4.5", "5"};
    public static String name_organization[] = {"COORDINATED", "COLLABORATIVE", "E-COLLABORATIVE", "EDGE"};
    public static String organization[] = { "organization-N2C2M2-level1.txt",
                                            "organization-N2C2M2-level2.txt",
                                            "organization-N2C2M2-level3.txt",
                                            "organization-N2C2M2-level4-less-connected.txt",
                                            "organization-N2C2M2-level4-less-connected2.txt",
                                            "organization-N2C2M2-level5.txt"};

    public static String label_nodelink_down = "1_NODELINK_1_WEB_DOWN";
    public static String nodelink_down_organization[] = {
        "organization-N2C2M2-level3-agility-01-Chris-Jesse-17.txt",
        "organization-N2C2M2-level3-agility-02-Dale-Morgan-17.txt",
        "organization-N2C2M2-level3-agility-03-Francis-Robin-17.txt",
        "organization-N2C2M2-level3-agility-04-Harlan-Taylor-17.txt",
        "organization-N2C2M2-level3-agility-05-Alex-Chris-17.txt",
        "organization-N2C2M2-level3-agility-06-Alex-Dale-17.txt",
        "organization-N2C2M2-level3-agility-07-Alex-Francis-17.txt",
        "organization-N2C2M2-level3-agility-08-Alex-Harlan-17.txt",
        "organization-N2C2M2-level4-agility-01-Chris-Jesse-17.txt",
        "organization-N2C2M2-level4-agility-02-Dale-Morgan-17.txt",
        "organization-N2C2M2-level4-agility-03-Francis-Robin-17.txt",
        "organization-N2C2M2-level4-agility-04-Harlan-Taylor-17.txt",
        "organization-N2C2M2-level4-agility-05-Alex-Chris-17.txt",
        "organization-N2C2M2-level4-agility-06-Alex-Dale-17.txt",
        "organization-N2C2M2-level4-agility-07-Alex-Francis-17.txt",
        "organization-N2C2M2-level4-agility-08-Alex-Harlan-17.txt",
        "organization-N2C2M2-level5-agility-01-Chris-Jesse-17.txt",
        "organization-N2C2M2-level5-agility-02-Dale-Morgan-17.txt",
        "organization-N2C2M2-level5-agility-03-Francis-Robin-17.txt",
        "organization-N2C2M2-level5-agility-04-Harlan-Taylor-17.txt",
        "organization-N2C2M2-level5-agility-05-Alex-Chris-17.txt",
        "organization-N2C2M2-level5-agility-06-Alex-Dale-17.txt",
        "organization-N2C2M2-level5-agility-07-Alex-Francis-17.txt",
        "organization-N2C2M2-level5-agility-08-Alex-Harlan-17.txt"
    };
    public static String label_2nodelink_down = "2_NODELINK_2_WEB_DOWN";
    public static String nodelink_2_down_organization[] = {
        "organization-N2C2M2-level3-agility-01-Alex-Chris-Jesse-17.txt",
        "organization-N2C2M2-level3-agility-02-Alex-Dale-Morgan-17.txt",
        "organization-N2C2M2-level3-agility-03-Alex-Francis-Robin-17.txt",
        "organization-N2C2M2-level3-agility-04-Alex-Harlan-Taylor-17.txt",
        "organization-N2C2M2-level4-agility-01-Alex-Chris-Jesse-17.txt",
        "organization-N2C2M2-level4-agility-02-Alex-Dale-Morgan-17.txt",
        "organization-N2C2M2-level4-agility-03-Alex-Francis-Robin-17.txt",
        "organization-N2C2M2-level4-agility-04-Alex-Harlan-Taylor-17.txt",
        "organization-N2C2M2-level5-agility-01-Alex-Chris-Jesse-17.txt",
        "organization-N2C2M2-level5-agility-02-Alex-Dale-Morgan-17.txt",
        "organization-N2C2M2-level5-agility-03-Alex-Francis-Robin-17.txt",
        "organization-N2C2M2-level5-agility-04-Alex-Harlan-Taylor-17.txt"
    };
    public static String label_node_down = "1_NODE_DOWN";
    public static String node_down_organization[] = {
        "organization-N2C2M2-level3-agility-01-Jesse-17.txt",
        "organization-N2C2M2-level3-agility-02-Morgan-17.txt",
        "organization-N2C2M2-level3-agility-03-Robin-17.txt",
        "organization-N2C2M2-level3-agility-04-Taylor-17.txt",
        "organization-N2C2M2-level3-agility-05-Chris-17.txt",
        "organization-N2C2M2-level3-agility-06-Dale-17.txt",
        "organization-N2C2M2-level3-agility-07-Francis-17.txt",
        "organization-N2C2M2-level3-agility-08-Harlan-17.txt",
        "organization-N2C2M2-level4-agility-01-Jesse-17.txt",
        "organization-N2C2M2-level4-agility-02-Morgan-17.txt",
        "organization-N2C2M2-level4-agility-03-Robin-17.txt",
        "organization-N2C2M2-level4-agility-04-Taylor-17.txt",
        "organization-N2C2M2-level4-agility-05-Chris-17.txt",
        "organization-N2C2M2-level4-agility-06-Dale-17.txt",
        "organization-N2C2M2-level4-agility-07-Francis-17.txt",
        "organization-N2C2M2-level4-agility-08-Harlan-17.txt",
        "organization-N2C2M2-level5-agility-01-Jesse-17.txt",
        "organization-N2C2M2-level5-agility-02-Morgan-17.txt",
        "organization-N2C2M2-level5-agility-03-Robin-17.txt",
        "organization-N2C2M2-level5-agility-04-Taylor-17.txt",
        "organization-N2C2M2-level5-agility-05-Chris-17.txt",
        "organization-N2C2M2-level5-agility-06-Dale-17.txt",
        "organization-N2C2M2-level5-agility-07-Francis-17.txt",
        "organization-N2C2M2-level5-agility-08-Harlan-17.txt"
    };

    public static String suffix = "-17.txt";
    //
    public static String website[] = {"WHO","WHAT","WHERE","WHEN"};
    public static String nodelink_web_down_organization[] = {
        "organization-N2C2M2-level3-agility-01-Chris-Jesse-",
        "organization-N2C2M2-level3-agility-02-Chris-Jesse-Kim-",
        "organization-N2C2M2-level3-agility-03-Chris-Jesse-Kim-Leslie-",
        "organization-N2C2M2-level3-agility-04-WHO-Morgan-",
        "organization-N2C2M2-level3-agility-05-WHO-Morgan-Pat-",
        "organization-N2C2M2-level3-agility-06-WHO-Morgan-Pat-Quinn-",
        "organization-N2C2M2-level3-agility-07-WHO-WHAT-Robin-",
        "organization-N2C2M2-level3-agility-08-WHO-WHAT-Robin-Sam-",
        "organization-N2C2M2-level3-agility-09-WHO-WHAT-Robin-Sam-Sidney-",
        "organization-N2C2M2-level3-agility-10-WHO-WHAT-WHERE-Taylor-",
        "organization-N2C2M2-level3-agility-11-WHO-WHAT-WHERE-Taylor-Val-",
        "organization-N2C2M2-level3-agility-12-WHO-WHAT-WHERE-Taylor-Val-Whit-",
        "organization-N2C2M2-level5-agility-01-Chris-Jesse-",
        "organization-N2C2M2-level5-agility-02-Chris-Jesse-Kim-",
        "organization-N2C2M2-level5-agility-03-Chris-Jesse-Kim-Leslie-",
        "organization-N2C2M2-level5-agility-04-WHO-Morgan-",
        "organization-N2C2M2-level5-agility-05-WHO-Morgan-Pat-",
        "organization-N2C2M2-level5-agility-06-WHO-Morgan-Pat-Quinn-",
        "organization-N2C2M2-level5-agility-07-WHO-WHAT-Robin-",
        "organization-N2C2M2-level5-agility-08-WHO-WHAT-Robin-Sam-",
        "organization-N2C2M2-level5-agility-09-WHO-WHAT-Robin-Sam-Sidney-",
        "organization-N2C2M2-level5-agility-10-WHO-WHAT-WHERE-Taylor-",
        "organization-N2C2M2-level5-agility-11-WHO-WHAT-WHERE-Taylor-Val-",
        "organization-N2C2M2-level5-agility-12-WHO-WHAT-WHERE-Taylor-Val-Whit-",
    };
    public static String nodedown_1toN_organization_prefix_L3 = "organization-N2C2M2-level3-agility-";
    public static String nodedown_1toN_organization_prefix_L5 = "organization-N2C2M2-level5-agility-";
    public static String nodedown_1toN_organization_remove_text = "-remove";

    //
    public static int NBR_RUNS_2LINK_DOWN=5;
    public static int NBR_RUNS_2NODE_DOWN=5;
    //
    public static int NBR_SUBJECTS = 17;
    public static int CTC_INDEX = 0;
    public static int[] TL_INDEX = {1,2,3,4};
    public static int[] TM_INDEX = {5,6,7,8,9,10,11,12,13,14,15,16};
    public static int[] WEBSITES_INDEX = {17,18,19,20};

    public static String ORG_HEADER = "How to read table:\nn|Role|Team|Country|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|website1|website2|website3|website4|\n\n<begin actual table>\n";
    public static String ORG_FOOTER = "who\nwhat\nwhere\nwhen\n<end actual table>\n";

    //

    static String getDate () {
        String DATE_FORMAT_NOW = "yyyy_MM_dd";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    static String getOrganizationType(String org) {
        String orgType = "5";
        if ( org.indexOf("level1")!=-1 ) orgType = "1";
        else if(org.indexOf("level2") != -1) orgType = "2";
        else if ( org.indexOf("level3")!=-1 ) orgType = "3";
        else if ( org.indexOf("level4")!=-1 ) orgType = "4";
        //else if ( org.indexOf("level5")!=-1 ) orgType = Message.m_orgType_EDGE;
        return orgType;
    }
    static String getAgentsTypeDistribution(String coord, String tl, String tm) {
        String type = coord;
        for ( int i=0; i<4; i++ ) type += tl;
        for ( int i=0; i<12; i++ ) type += tm;
        return type;
    }

    static void writeHeader(FileWriter writer) throws IOException {
        writer.write("<start trialset>\n");
        writer.write("interval|5\n");
        writer.write("waves|3\n");
        writer.write("trial|60|factoidset1a5-17.txt|countries1.txt\n");
        writer.write("names|names17.txt\n");
    }

    static void writeHeader(FileWriter writer, String factoidSet) throws IOException {
        writer.write("<start trialset>\n");
        writer.write("interval|20\n");
        writer.write("waves|3\n");
        writer.write("trial|60|"+factoidSet+"|countries1.txt\n");
        writer.write("names|names17.txt\n");
    }

    static void writeFooter(FileWriter writer, String runName) throws IOException {
        writer.write("agentauditing|false\n");
        writer.write("teamtableauditing|false\n");
        writer.write("numberofidentifies|-1\n");
        writer.write("agentcompression|0.1\n");
        writer.write("startAgentDelay|0\n");
        writer.write("runName|"+runName+"\n");
        writer.write("country|Portugal\n");
        writer.write("institution|WEAREIT\n");
        writer.write("researcher|MarcoManso\n");
        writer.write("researcherContactInfo|marco.manso@weareit.pt\n");
        writer.write("natureOfParticipants|agent\n");
        writer.write("experienceOfParticipants|agent\n");
        writer.write("baselineExperiment|no\n\n");
    }
    public static void createN2C2M2Batch(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-N2C2M2-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);
            //
            for (String org : organization)
                for (String agentTMs : agentsFile)
                    for (String agentTLs : agentsFile)
                        for (String agentCoord : agentsFile) {

                            String coord=TrialData.getTypeOfAgent(agentCoord);
                            String tl=TrialData.getTypeOfAgent(agentTLs);
                            String tm=TrialData.getTypeOfAgent(agentTMs);

                            String orgAndAgents = getOrganizationType(org)+"-"+getAgentsTypeDistribution(coord, tl, tm);

                            System.out.println(".. organization"+org
                                               +", coord:"+agentCoord
                                               +", tl: "+agentTLs
                                               + ", tm: "+agentTMs+"\n");
                            //
                            writeHeader(writer);
                            writer.write("group|"+org+"\n");
                            writer.write("role|"+agentCoord+"\n");
                            //
                            writer.write("role|"+agentTLs+"\n");
                            writer.write("role|"+agentTLs+"\n");
                            writer.write("role|"+agentTLs+"\n");
                            writer.write("role|"+agentTLs+"\n");
                            //
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            //
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            //
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            //
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            writer.write("role|"+agentTMs+"\n");
                            writeFooter(writer, orgAndAgents );
                        }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void createAgilityPerOrganization(FileWriter writer, String orgLabel, String org) throws IOException {
        //parse problem difficulty
        for ( int prob=0; prob<problem.length; prob++ ) {
            //parse agents performance
            for (int agent=0; agent<agentsFile.length; agent++) {
                //label_problem
                //        problem
                    //label_agentsFile
                    //agentsFile
                writeHeader(writer, problem[prob]);
                writer.write("group|"+org+"\n");
                for (int i=0; i<17; i++)
                    writer.write("role|"+agentsFile[agent]+"\n");
                writeFooter(writer, label_agentsFile[agent]+"_"+label_problem[prob]+"_"+orgLabel);
                System.out.println("...RUN: "+label_agentsFile[agent]+"_"+label_problem[prob]+"_"+orgLabel);
            }
        }
    }

    public static void createRun(FileWriter writer, String orgLabel, String org) throws IOException {
        writeHeader(writer, problem[0]);
        writer.write("group|"+org+"\n");
        for (int i=0; i<17; i++)
            writer.write("role|"+agentsFile[0]+"\n");
        writeFooter(writer, orgLabel);
    }

    public static void createAgilityBatch(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);
            //
            for ( String orgName : nodelink_down_organization ) {
                createAgilityPerOrganization(writer, label_nodelink_down, orgName);
            }
            for ( String orgName : nodelink_2_down_organization) {
                createAgilityPerOrganization(writer, label_2nodelink_down, orgName);
            }
            for ( String orgName : node_down_organization) {
                createAgilityPerOrganization(writer, label_node_down, orgName);
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void createNodeWebDownAgilityBatch(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-node-web-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);
            //
            int countWebDown =1;
            String websiteLbl="";
            for (String lbl : website) {
                websiteLbl += lbl;
                for (int i=0; i<nodelink_web_down_organization.length; i++) {
                    int nbrLinksDown = (i+1)%12;
                    if (nbrLinksDown == 0) nbrLinksDown = 12;
                    String label = ""+(nbrLinksDown)+"_NODELINK_"+countWebDown+"_WEB_DOWN";
                    String orgName = nodelink_web_down_organization[i]+websiteLbl+suffix;
                    createRun(writer, label, orgName);
                    System.out.println("label: "+label+" org_name: "+orgName);
                    //
                }
                countWebDown++;
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void createNodeDownAgilityBatch(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-node-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);
            for (int i=1; i<=12; i++) {
                String number = "";
                if (i<10) number += "0"+i; else number += i;
                String label = number+"_NODE_DOWN";
                String orgName = nodedown_1toN_organization_prefix_L3
                        +number
                        +nodedown_1toN_organization_remove_text
                        +number
                        +suffix;
                createRun(writer, label, orgName);
                System.out.println("label: "+label+" org_name: "+orgName);
            }
            for (int i=1; i<=12; i++) {
                String number = "";
                if (i<10) number += "0"+i; else number += i;
                String label = number+"_NODE_DOWN";
                String orgName = nodedown_1toN_organization_prefix_L5
                        +number
                        +nodedown_1toN_organization_remove_text
                        +number
                        +suffix;
                createRun(writer, label, orgName);
                System.out.println("label: "+label+" org_name: "+orgName);
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void allCommunicate(int start, int end, int[][] matrix) {
        for (int i=start; i<=end; i++)
            for (int j=start; j<=end; j++)
                if (i!=j)
                    matrix[i][j]=1;
    }

    public static void allWebsite(int subjectIndex, int[][] matrix) {
        for (int i : WEBSITES_INDEX)
            matrix[subjectIndex][i]=1;
    }

    public static void fillWebsite(int start, int end, int websiteIndex, int[][] matrix) {
        for (int i=start; i<=end; i++)
            matrix[i][websiteIndex]=1;
    }

    public static void fillMatrixCoordinated(int[][] matrix) {
        //allCommunicate(0, 4, matrix);
        //CTC and team leaders
        for (int i=1; i<5; i++) {
            matrix[i][0]=1;
            matrix[0][i]=1;
        }
        //TL and TMs
        matrix[1][5]=1; matrix[1][6]=1; matrix[1][7]=1;
        matrix[5][1]=1; matrix[6][1]=1; matrix[7][1]=1;
        matrix[2][8]=1; matrix[2][9]=1; matrix[2][10]=1;
        matrix[8][2]=1; matrix[9][2]=1; matrix[10][2]=1;
        matrix[3][11]=1;matrix[3][12]=1;matrix[3][13]=1;
        matrix[11][3]=1;matrix[12][3]=1; matrix[13][3]=1;
        matrix[4][14]=1;matrix[4][15]=1;matrix[4][16]=1;
        matrix[14][4]=1;matrix[15][4]=1; matrix[16][4]=1;
        //TMs
        allCommunicate(5, 7, matrix);
        allCommunicate(8, 10, matrix);
        allCommunicate(11, 13, matrix);
        allCommunicate(14, 16, matrix);
        //websites
        allWebsite(0, matrix);
        //specific website access
        matrix[1][17]=1;
        matrix[2][18]=1;
        matrix[3][19]=1;
        matrix[4][20]=1;
        //TMs WHO
        for (int i=5; i<=7; i++)
            matrix[i][WEBSITES_INDEX[0]]=1;
        //TMs WHAT
        for (int i=8; i<=10; i++)
            matrix[i][WEBSITES_INDEX[1]]=1;
        //TMs WHERE
        for (int i=11; i<=13; i++)
            matrix[i][WEBSITES_INDEX[2]]=1;
        //TMs WHEN
        for (int i=14; i<=16; i++)
            matrix[i][WEBSITES_INDEX[3]]=1;
        //
    }
    
    public static void fillMatrixCollaborative(int[][] matrix) {
        //CTC and team leaders
        allCommunicate(0, 4, matrix);
        //TL and TMs
        matrix[1][5]=1; matrix[1][6]=1; matrix[1][7]=1;
        matrix[5][1]=1; matrix[6][1]=1; matrix[7][1]=1;
        matrix[2][8]=1; matrix[2][9]=1; matrix[2][10]=1;
        matrix[8][2]=1; matrix[9][2]=1; matrix[10][2]=1;
        matrix[3][11]=1;matrix[3][12]=1;matrix[3][13]=1;
        matrix[11][3]=1;matrix[12][3]=1; matrix[13][3]=1;
        matrix[4][14]=1;matrix[4][15]=1;matrix[4][16]=1;
        matrix[14][4]=1;matrix[15][4]=1; matrix[16][4]=1;
        //TMs
        allCommunicate(5, 7, matrix);
        allCommunicate(8, 10, matrix);
        allCommunicate(11, 13, matrix);
        allCommunicate(14, 16, matrix);
        //websites
        for (int i=0; i<5; i++)
            allWebsite(i, matrix);
        //specific website access
        //TMs WHO
        for (int i=5; i<=7; i++)
            matrix[i][WEBSITES_INDEX[0]]=1;
        //TMs WHAT
        for (int i=8; i<=10; i++)
            matrix[i][WEBSITES_INDEX[1]]=1;
        //TMs WHERE
        for (int i=11; i<=13; i++)
            matrix[i][WEBSITES_INDEX[2]]=1;
        //TMs WHEN
        for (int i=14; i<=16; i++)
            matrix[i][WEBSITES_INDEX[3]]=1;
        //
    }

    public static void fillMatrixCollaborative2(int[][] matrix) {
        fillMatrixCollaborative(matrix);
        //add specifics
        //X-WHO
        matrix[6][16]=1;
        matrix[6][20]=1;
        matrix[7][9]=1;
        matrix[7][18]=1;
        //X-WHAT
        matrix[9][7]=1;
        matrix[9][17]=1;
        matrix[10][12]=1;
        matrix[10][19]=1;
        //X-WHERE
        matrix[12][10]=1;
        matrix[12][18]=1;
        matrix[13][15]=1;
        matrix[13][20]=1;
        //X-WHEN
        matrix[15][13]=1;
        matrix[15][19]=1;
        matrix[16][6]=1;
        matrix[16][17]=1;
    }

    public static void fillMatrixEdge(int[][] matrix) {
        allCommunicate(0, 16, matrix);
        for (int i=0; i<17; i++)
            allWebsite(i, matrix);
    }

    public static void killNode(int index, int[][] matrix) {
        for (int j=0; j<matrix[index].length; j++) {
            matrix[index][j]=0;
        }
        for (int j=0; j<matrix.length; j++) {
            matrix[j][index]=0;
        }
    }

    public static int getLeader(int index) {
        if (index<=4)
            return 0;
        else
            return (index-2)/3;
    }
    public static int getWEB(int index) {
        return getLeader(index)+16;
    }
    public static void fillRandomTMs(int tm, int[] list) {
        //TBD
            //check NOT TL
            //check NOT same subject
        System.arraycopy(TM_INDEX, 0, list, 0, list.length);
    }

    public static void killLinkWithLeader(int index, int[][] matrix) {
        //kill connection between TM (index) and respective TL + website
        int TLindex = getLeader(index);
        int WEBindex = getWEB(index);
        matrix[index][TLindex]=0;
        matrix[TLindex][index]=0;
        matrix[index][WEBindex]=0;
    }

    public static String getOrganizationRoleAndTeam(int index) {
        switch (index) {
            case 0:
                return Message.m_teamPositionCrossTeamCoordinator+"||Chiland\t|";
            case 1: return Message.m_teamPositionTeamLeader+"|"+Message.m_teamWho+"|Chiland\t\t|";
            case 2: return Message.m_teamPositionTeamLeader+"|"+Message.m_teamWhat+"|Psiland\t\t|";
            case 3: return Message.m_teamPositionTeamLeader+"|"+Message.m_teamWhere+"|Omegaland\t\t|";
            case 4: return Message.m_teamPositionTeamLeader+"|"+Message.m_teamWhen+"|Deltaland\t\t|";
            case 5:
            case 6:
            case 7:
                return Message.m_teamPositionTeamMember+"|"+Message.m_teamWho+"|Chiland\t\t|";
            case 8:
            case 9:
            case 10:
                return Message.m_teamPositionTeamMember+"|"+Message.m_teamWhat+"|Psiland\t\t|";
            case 11:
            case 12:
            case 13:
                return Message.m_teamPositionTeamMember+"|"+Message.m_teamWhere+"|Omegaland\t\t|";
            case 14:
            case 15:
            case 16:
                return Message.m_teamPositionTeamMember+"|"+Message.m_teamWhen+"|Deltaland\t\t|";
            default: return Message.m_teamPositionTeamMember+"||Chiland\t|";
        }
    }

    public static void writeOrganization(String filename, String orgName, int[][] matrix, boolean withTeams) {
        try {
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);
            //
            writer.write(ORG_HEADER);
            for (int i=0; i<NBR_SUBJECTS; i++) {
                writer.write((i+1)+"|");
                if (withTeams)
                    writer.write(getOrganizationRoleAndTeam(i));
                else
                    writer.write(getOrganizationRoleAndTeam(-1));
                for (int j=0; j<matrix[i].length; j++)
                    writer.write(matrix[i][j]+"|");
                writer.write("\n");
            }
            writer.write(orgName+"\n");
            writer.write(ORG_FOOTER);
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }

    }

    public static void fillMatrix (int[][] matrix, int index) {
        switch ( index ) {
            case INDEX_COORDINATED: 
                fillMatrixCoordinated(matrix);
                break;
            case INDEX_COLLABORATIVE:
                fillMatrixCollaborative(matrix);
                break;
            case INDEX_COLLABORATIVE2:
                fillMatrixCollaborative2(matrix);
                break;
            default:
                fillMatrixEdge(matrix);
                break;
        }
    }

    public static void writeLinkDown(String dir, int orgIndex) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        fillMatrix(matrix, orgIndex);
        //kill links ....
        for (int i=NBR_SUBJECTS-1; i>0; i--) {
            killLinkWithLeader(i, matrix);
            String filename = dir+File.separator+"organization-N2C2M2-level"+label_organization[orgIndex]+"-linkdown-"+(NBR_SUBJECTS-i)+".txt";
            if (orgIndex==INDEX_EDGE)
                writeOrganization(filename, name_organization[orgIndex], matrix, true);
            else
                writeOrganization(filename, name_organization[orgIndex], matrix, false);
        }
    }
    public static void write_1LinkDown(String dir, int orgIndex) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        for (int i=NBR_SUBJECTS-1; i>0; i--) {
            fillMatrix(matrix, orgIndex);
            //kill links ....
            killLinkWithLeader(i, matrix);
            String filename = dir+File.separator+"organization-N2C2M2-level"+label_organization[orgIndex]+"-1linkdown-"+(NBR_SUBJECTS-i)+".txt";
            if (orgIndex==INDEX_EDGE)
                writeOrganization(filename, name_organization[orgIndex], matrix, false);
            else
                writeOrganization(filename, name_organization[orgIndex], matrix, true);
        }
    }
    public static void write_2LinkDown(String dir, int orgIndex, int nbrRuns) {
        int index = 1;
        //generate random sequences for TMs in second link down
        int[] secondlinkIndex = new int[nbrRuns];

        for (int i=5; i<NBR_SUBJECTS; i++) {

            fillRandomTMs(i, secondlinkIndex);

            int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
            fillMatrix(matrix, orgIndex);
            //kill links ....
            killLinkWithLeader(i, matrix);
            //kill second link
            for (int j=0; j<secondlinkIndex.length; j++) {
                killLinkWithLeader(secondlinkIndex[j], matrix);
                String filename = dir+File.separator+"organization-N2C2M2-level"+label_organization[orgIndex]+"-2linkdown-"+index+"-"+(j+1)+".txt";
                if (orgIndex==INDEX_EDGE)
                    writeOrganization(filename, name_organization[orgIndex], matrix, false);
                else
                    writeOrganization(filename, name_organization[orgIndex], matrix, true);
            }
            index++;
        }
    }
    public static void writeNodeDown(String dir, int orgIndex) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        fillMatrix(matrix, orgIndex);
        //kill nodes ....
        for (int i=NBR_SUBJECTS-1; i>=0; i--) {
            killNode(i, matrix);
            String filename = dir+File.separator+"organization-N2C2M2-level"+label_organization[orgIndex]+"-nodedown-"+(NBR_SUBJECTS-i)+".txt";
            if (orgIndex==INDEX_EDGE)
                writeOrganization(filename, name_organization[orgIndex], matrix, false);
            else
                writeOrganization(filename, name_organization[orgIndex], matrix, true);
        }
    }

    public static void write_1NodeDown(String dir, int orgIndex) {
        for (int i=NBR_SUBJECTS-1; i>=0; i--) {
            int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
            fillMatrix(matrix, orgIndex);
            //kill node ....
            killNode(i, matrix);
            String filename = dir+File.separator+"organization-N2C2M2-level"+label_organization[orgIndex]+"-1nodedown-"+(NBR_SUBJECTS-i)+".txt";
            if (orgIndex==INDEX_EDGE)
                writeOrganization(filename, name_organization[orgIndex], matrix, false);
            else
                writeOrganization(filename, name_organization[orgIndex], matrix, true);
        }
    }

    public static void writeCoordinated(String dir) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        fillMatrixCoordinated(matrix);
        String filename = dir+File.separator+"organization-N2C2M2-level3.txt";
        writeOrganization(filename, Message.m_orgType_COORDINATED, matrix, true);
    }

    public static void writeCollaborative(String dir) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        fillMatrixCollaborative(matrix);
        String filename = dir+File.separator+"organization-N2C2M2-level4.txt";
        writeOrganization(filename, Message.m_orgType_COLLABORATIVE, matrix, true);
    }

    public static void writeCollaborative2(String dir) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        fillMatrixCollaborative2(matrix);
        String filename = dir+File.separator+"organization-N2C2M2-level4.5.txt";
        writeOrganization(filename, Message.m_orgType_COLLABORATIVE2, matrix, true);
    }

    public static void writeEdge (String dir) {
        int[][] matrix = new int[NBR_SUBJECTS][NBR_SUBJECTS+WEBSITES_INDEX.length];
        fillMatrixEdge(matrix);
        String filename = dir+File.separator+"organization-N2C2M2-level5.txt";
        writeOrganization(filename, Message.m_orgType_EDGE, matrix, false);
    }

    public static void writeBatchLinkDown(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-link-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);
            for (String org_label : label_organization ) {
                for (int i=1; i<NBR_SUBJECTS; i++) {
                    String number="";
                    if (i<10) number += "0"+i; else number += i;
                    String orgName = "organization-N2C2M2-level"+org_label+"-linkdown-"+i+".txt";
                    String label = number+"_LINK_DOWN";
                    createRun(writer, label, orgName);
                }
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void writeBatch_1LinkDown(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-1link-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);

            for (String org_label : label_organization ) {
                for (int i=1; i<NBR_SUBJECTS; i++) {
                    String orgName = "organization-N2C2M2-level"+org_label+"-1linkdown-"+i+".txt";
                    createRun(writer, "1_LINK_DOWN", orgName);
                }
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void writeBatch_2LinkDown(String dir, int nbrRuns) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-2link-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);

            for (String org_label : label_organization ) {
                for (int i=1; i<=NBR_SUBJECTS; i++) {
                    for (int j=1; j<=nbrRuns; j++)
                    {
                        String orgName = "organization-N2C2M2-level"+org_label+"-2linkdown-"+i+"-"+j+".txt";
                        createRun(writer, "2_LINK_DOWN", orgName);
                        //System.out.println("label: "+label+" org_name: "+orgName);
                    }
                }
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void writeBatchNodeDown(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-node-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);

            for (String org_label : label_organization ) {
                for (int i=1; i<=NBR_SUBJECTS; i++) {
                    String number="";
                    if (i<10) number += "0"+i; else number += i;
                    String orgName = "organization-N2C2M2-level"+org_label+"-nodedown-"+i+".txt";
                    String label = number+"_NODE_DOWN";
                    createRun(writer, label, orgName);
                    //System.out.println("label: "+label+" org_name: "+orgName);
                }
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void writeBatch_1NodeDown(String dir) {
        try {
            String filename = dir+File.separator+"agent-batch-agility-1node-down-"+getDate()+".txt";
            FileWriter writer = new FileWriter(filename);
            System.out.println("Create: "+filename);

            for (String org_label : label_organization ) {
                for (int i=1; i<=NBR_SUBJECTS; i++) {
                    String orgName = "organization-N2C2M2-level"+org_label+"-1nodedown-"+i+".txt";
                    createRun(writer, "1_NODE_DOWN", orgName);
                }
            }
            writer.close();
            System.out.println("done !");
        }
        catch (IOException ex) {
            ex.toString();
        }
    }

    public static void writeOrganizations(String dir) {
        writeCoordinated(dir);
        writeCollaborative(dir);
        writeCollaborative2(dir);
        writeEdge(dir);
        // 1 LINK DOWN
        write_1LinkDown(dir, INDEX_COORDINATED);
        write_1LinkDown(dir, INDEX_COLLABORATIVE);
        write_1LinkDown(dir, INDEX_COLLABORATIVE2);
        write_1LinkDown(dir, INDEX_EDGE);
        writeBatch_1LinkDown(dir);
        // 2 LINK DOWN
        //write_2LinkDown(dir, INDEX_COORDINATED, NBR_RUNS_2LINK_DOWN);
        //write_2LinkDown(dir, INDEX_COLLABORATIVE, NBR_RUNS_2LINK_DOWN);
        //write_2LinkDown(dir, INDEX_COLLABORATIVE2, NBR_RUNS_2LINK_DOWN);
        //write_2LinkDown(dir, INDEX_EDGE, NBR_RUNS_2LINK_DOWN);
        //writeBatch_2LinkDown(dir, NBR_RUNS_2LINK_DOWN);
        // LINK DOWN
        writeLinkDown(dir, INDEX_COORDINATED);
        writeLinkDown(dir, INDEX_COLLABORATIVE);
        writeLinkDown(dir, INDEX_COLLABORATIVE2);
        writeLinkDown(dir, INDEX_EDGE);
        writeBatchLinkDown(dir);
        // NODE DOWN
        writeNodeDown(dir, INDEX_COORDINATED);
        writeNodeDown(dir, INDEX_COLLABORATIVE);
        writeNodeDown(dir, INDEX_COLLABORATIVE2);
        writeNodeDown(dir, INDEX_EDGE);
        writeBatchNodeDown(dir);
        // 1 NODE DOWN
        write_1NodeDown(dir, INDEX_COORDINATED);
        write_1NodeDown(dir, INDEX_COLLABORATIVE);
        write_1NodeDown(dir, INDEX_COLLABORATIVE2);
        write_1NodeDown(dir, INDEX_EDGE);
        writeBatch_1NodeDown(dir);
    }

}
