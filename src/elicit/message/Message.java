
/*
 * Message.java
 *
 * Created on 27 June 2007, 21:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

import elicit.message.Message.AssignmentId;
import elicit.message.Message.OrganizationId;

/**
 *
 * @author Marco
 */
public abstract class Message {

	
    //////////////////////////////////////////////////////
    // todo HACK:  time of start of run should be subtracted from time of events
	// UNFORTUNATELY:  has to be made static to be used in any context
	public static double m_timeStartOfRun = 0.0;
	
	
    //////////////////////////////////////////////////////
    // CONSTANTS
    public static final String m_orgTypeHierarchical[] = {"C2","B"};
    public static final String m_orgTypeEdge[] = {"A","E"};

    public static final String m_orgType_CONFLICTED = "CONFLICTED";
    public static final String m_orgType_DECONFLICTED = "DECONFLICTED";
    public static final String m_orgType_COORDINATED = "COORDINATED";
    public static final String m_orgType_COLLABORATIVE = "COLLABORATIVE";
    public static final String m_orgType_COLLABORATIVE2 = "COLLABORATIVE2";
    public static final String m_orgType_EDGE = "EDGE";
    public static final String m_orgTypeHierarchy = "HIERARCHY";

    public static final String m_teamWho = "WHO";
    public static final String m_teamWhat = "WHAT";
    public static final String m_teamWhere = "WHERE";
    public static final String m_teamWhen = "WHEN";
    public static final String m_teamNA = "NA";
    public static final String m_server = "server";
    public static final String m_OVERALL = "OVERALL";

    public static final Map<String,String> m_agentSetupMap = Collections.unmodifiableMap(new HashMap(){
        {
            put("BASELINE", "B");
            put("LOW", "L");
            put("HIGH", "H");
            put("UNKNOWN", "U");
        }
    });

    public static final String m_teamPositionIsolatedCoordinator = "Isolated Coordinator";
    public static final String m_teamPositionInformationBroker = "Information Broker";
    public static final String m_teamPositionCrossTeamCoordinator = "Cross Team Coordinator";
    public static final String m_teamPositionCrossTeamCoordinator_v01 = "Cross-team Coordinator";
    public static final String m_teamPositionFacilitator = "Coordinator-Facilitator";
    public static final String m_teamPositionTeamLeader = "Team leader";
    public static final String m_teamPositionTeamMember = "Team member";

    public static final String m_teamPositionLeader = "Leader";
    public static final String m_teamPositionTM = "TM";
    
    public static final String m_teamPositionInvalid = "NA";
    //
    public static final int m_websiteWhoIndex   = 0;
    public static final int m_websiteWhatIndex  = 1;
    public static final int m_websiteWhereIndex = 2;
    public static final int m_websiteWhenIndex  = 3;
    public static final String[] m_webSitesList = {m_teamWho, m_teamWhat, m_teamWhere, m_teamWhen};

    public static final String MESSAGE_TYPE_ADMIN = "admin";
    public static final String MESSAGE_TYPE_ASSIGN = "assign";
    public static final String MESSAGE_TYPE_BATCH = "batch";
    public static final String MESSAGE_TYPE_CONTEXT = "context";
    public static final String MESSAGE_TYPE_DIST = "dist";
    public static final String MESSAGE_TYPE_END = "end";
    public static final String MESSAGE_TYPE_FACET = "facet";
    public static final String MESSAGE_TYPE_FACTOID = "factoid";
    public static final String MESSAGE_TYPE_HOW_SEEN = "how_seen";
    public static final String MESSAGE_TYPE_IDENTIFY = "identify";
    public static final String MESSAGE_TYPE_INITIATE = "initiate";
    public static final String MESSAGE_TYPE_POST = "post";
    public static final String MESSAGE_TYPE_PULL = "pull";
    public static final String MESSAGE_TYPE_METADATA = "metadata";
    public static final String MESSAGE_TYPE_SHARE = "share";
    public static final String MESSAGE_TYPE_WHAT_SEE = "what_see";
    public static final String MESSAGE_TYPE_ORGANIZATION = "organization";
    public static final String MESSAGE_TYPE_START = "start";
    public static final String MESSAGE_TYPE_ADD = "add";
    public static final String MESSAGE_TYPE_SOLUTIONFACTOIDS = "solutionFactoids";
    public static final String MESSAGE_TYPE_SOLUTION = "solution";
    //abELICIT
    public static final String MESSAGE_TYPE_RUNNAME = "runname";
    public static final String MESSAGE_TYPE_AGENT = "agent";

    public static final String MESSAGE_TYPE_DUMMY = "dummy";
    public static final String MESSAGE_TYPE_NOT_APPLICABLE = "N/A";
    public static final int NOVALUE = -1;

    //Identify format
    public static final char m_noAnswer = '?';
    public static final String m_Prewho = "IDENTIFY: ";
    public static final String m_Prewhat = " WILL ATTACK THE ";
    public static final String m_Prewhere = " IN ";
    //public static final String m_Prewhen = " ON THE ";
    public static final String m_PreWhenMonth = " ON ";
    public static final String m_PreWhenAtTime = " AT ";

    public static final String m_answer_DAY = "DAY";
    public static final String m_answer_AM = "AM";
    public static final String m_answer_PM = "PM";
    
    //trial answer
    public static final String m_trialAnswerText = "THE TRIAL ANSWER IS: THE ";
    public static final String m_trialAnswerTextWho = "THE ";
    public static final String m_trialAnswerTextWhat = " GROUP PLANS TO ATTACK A ";
    public static final String m_trialAnswerTextWhat2 = " GROUP PLANS TO ATTACK THE ";
    public static final String m_trialAnswerTextWhere = " IN ";
    public static final String m_trialAnswerTextWhen = " ON "; //date format: [MM] [d] at [hh] [AM/PM]
    //batch: compression
    public static final String m_batch_compression = "agentcompression|";
    public static final String m_agentname = "Agent name";
    //
    public static final String m_batch_runname = "runName|";
    public static final String m_batch_runnameMetadata = "Run Name:";

    //Info Q
    static public final char m_KChar = 'K';
    static public final char m_EChar = 'E';
    static public final char m_SChar = 'S';
    static public final char m_NChar = 'N';
    static public final char m_MChar = 'M';
    static public final char m_AChar = 'A';

    @Deprecated
    static public final int m_indexOfValue = 6; //works for V.1
    @Deprecated
    static public final double m_KValue =  0.25;
    @Deprecated
    static public final double m_EValue =  0.25;
    @Deprecated
    static public final double m_SValue =  0.1;
    @Deprecated
    static public final double m_NValue = -0.1;

    //////////////////////////////////////////////////////
    // VARIABLES

    //common
    public String m_messageType;
    public class CommonData {
        public String date;
        public String time;
        public String sequence;
        public String type;
    };
    public CommonData m_commonData = new CommonData();
    public String m_data; //specific data will be stored herein
    public String m_data2; //2nd specific data will be stored herein

    //factoid
    public class Factoid {
        public String m_factoidId;
        public char m_relevance;
        public String m_team;
        public String m_factoidtext;
    };
    public Factoid m_factoid;

    //organization
    public class OrganizationId {
    		public String m_index;
    		public String m_role;
    		public String m_team;
    		public String m_teamName;
    		public String m_orgMembersAccess;
    		public String m_sitesAccess;
    }
    public OrganizationId m_organizationId;
    public static String m_organizationName;
    public static char m_ignoreOrganisation = 'B';
    
    //assignments
    public class AssignmentId {
		public String m_name;
		public String m_facetId;
		public String m_organisationType;
		public String m_role;
		public String m_team;
    }
    public AssignmentId m_assignmentId;
    public static String m_ignoreAssignment = "admin";
    
    //
    public TrialData.Subject m_subject;
    //
    public String m_destSubject; //for share messages

    //for post messages: post SITE
    public String m_postSite;
    //for pull messages: pull SITE
    public String m_pullSite;
    
    //factoid evaluation (when applicable)
    public static int m_relevanceEval=NOVALUE;
    public static int m_trustEval=NOVALUE;
    

    //public static boolean m_isEdge = true;
    //public HashMap<String, Subject> m_subjectMap = new HashMap<String, Subject>();

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //
    public String m_logLine;
    //retro-compatibility: ELICIT V1 and webELICIT
    static boolean m_isELICITv01 = false;

    //////////////////////////////////////////////////////
    //abstract methods to be implemented by all specific messages
    public abstract void Read(String logLine);
    
    public abstract String Write();
    
    //////////////////////////////////////////////////////
    //some utilities
    public static void parseNWords(StringTokenizer s, int n) {
        for (int i=0; i<n; i++) {
            s.nextToken();
        }
    }
    public static int getInt(String s) {
        int value = NOVALUE;
        if (s.indexOf(MESSAGE_TYPE_NOT_APPLICABLE)==-1) {
            value = Integer.valueOf(s);
        }
        return value;
    }

    public static String getDate(String logLine) {
        //token is space
        //get first
        StringTokenizer s = new StringTokenizer(logLine);
        return s.nextToken();
    }

    public static String getTime(String logLine) {
        //token is space
        //get second
        StringTokenizer s = new StringTokenizer(logLine);
        s.nextToken();
        return s.nextToken();
    }

    public static String getSequence(String logLine) {
        //token is space
        //get third
        StringTokenizer s = new StringTokenizer(logLine);
        s.nextToken();
        s.nextToken();
        return s.nextToken();
    }

    public static String getMessageType(String logLine) {
        //token is space
        //get 5th
        StringTokenizer s = new StringTokenizer(logLine);
        parseNWords(s, 4);
        return s.nextToken();
    }
    
    public void fillDateTimeSeqMsgType(StringTokenizer s) {
        m_commonData.date = s.nextToken();

        m_commonData.time = s.nextToken(); 
        
        //m_commonData.sequence = s.nextToken(); //todo: hacked to subtract start of run
        m_commonData.sequence = String.valueOf(Double.valueOf(s.nextToken()) - Message.m_timeStartOfRun); // hacked for start of run
        
        //System.out.println("start of run: "+Message.m_timeStartOfRun+" time_message: "+m_commonData.sequence);        
        
        s.nextToken();
        m_commonData.type = s.nextToken();
    }
      
    public void fillSubject(StringTokenizer s) {
        //SUBJECT INFORMATION
        //get facetid
        m_subject.m_personFacetId = s.nextToken();
        //get person name
        m_subject.m_personName = s.nextToken();
    }

    @Override
    public String toString() {
    	return Write();
    }
    
    
//    public boolean isRelevant() {
//        return true;
//        //return !(m_factoid.m_factoidId.charAt(m_indexOfValue)==m_NChar);
//    }
//
//    @Deprecated
//    public double getIDQAverage() {
//        return (m_valueWho + m_valueWhat + m_valueWhere + m_valueWhen);
//    }
//
//    @Deprecated
//    public double getMessageInfoQValue () {
//        double q = 0;
//        /*
//        if (m_factoid.m_factoidId.charAt(m_indexOfValue)==m_KChar )
//        {
//            q = m_KValue;
//        }
//        else if (m_factoid.m_factoidId.charAt(m_indexOfValue)==m_EChar)
//        {
//            q = m_EValue;
//        }
//        else if (m_factoid.m_factoidId.charAt(m_indexOfValue)==m_SChar)
//        {
//            q = m_SValue;
//        }
//        else if (m_factoid.m_factoidId.charAt(m_indexOfValue)==m_NChar)
//        {
//            q = m_NValue;
//        }
//         */
//        return q;
//    }
      
}
