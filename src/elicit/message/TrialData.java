/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.message;

import java.util.ArrayList;
import java.util.Vector;
import java.util.TreeMap;
import metrics.informationquality.InformationQuality;
import metrics.interaction.Interactions;
import metrics.awareness.*;
import metrics.interaction.OverallInteractions;

/**
 *
 * @author mmanso
 */
public class TrialData {

    //TRIAL RELATED DATA
    public class TrialInformation {
        public String m_date;
        public String m_timeStart;
        public String m_timeFinish;
        public long m_durationSec = 0;
        public int m_durationMin = 0;
        public double m_compression;
        //
        public String factoidSet;
        public double timeOfLastFactoid = 0.0;
        //
        public String m_runname;
    };
    public TrialInformation m_trialInformation = new TrialInformation();
    
    //Organization related
    public class OrganizationInformation {
        public String m_organizationName;
        public ArrayList<String> m_teamList = new ArrayList<String>();
        public ArrayList<Subject> m_memberList = new ArrayList<Subject>();

        public Subject getSubject(String subjectName) {
            for (Subject subject : m_memberList) {
                if (subject.m_personName.equals(subjectName)) {
                    return subject;
                }
            }
            return null;
        }
    }
    public OrganizationInformation m_organizationInformation = new OrganizationInformation();

    //subject and team info
    public class Subject implements Comparable {
        public String m_personFacetId;
        public String m_personName;
        public String m_teamPosition; //for hierarchy and L1-L4
        public boolean m_isTeamLeader; //for hierarchy and L1-L4
        public boolean m_isOverallCoordinator; //for hierarchy and L1-L4
        public String m_teamName;         //for hierarchy and L1-L4
        public String m_country; //for hierarchy ONLY
          //
        @Override
          public int compareTo(Object anotherPerson) throws ClassCastException {
            if (!(anotherPerson instanceof Subject))
              throw new ClassCastException("A Subject object expected.");
            return this.m_personName.compareTo(( (Subject) anotherPerson).m_personName);
          }
    }

    public Vector<String> m_agentFile = new Vector<String>();
    public String m_agentsSetup = new String("");
    //website access:
    //2-dimension map indexed by: team_role and team_name
    //ELICIT TODO: current log doesn't is ambiguous to match subject's ID and web-site access
    public TreeMap<String, TreeMap<String, boolean[]>> m_websiteAccessPerRoleAndTeam = new TreeMap<String, TreeMap<String, boolean[]>>();

    //factoids
    public class Factoid {
        public int totalFactoids = 0;
        public int totalKey_Factoids = 0;
        public int totalExpertise_Factoids = 0;
        public int totalSupportive_Factoids = 0;
        public int totalNoise_Factoids = 0;
        public int totalMisinfo_Factoids = 0;
        public boolean hasAnswer = false;
    };
    public Factoid m_factoidStats = new Factoid();
    public TreeMap<String, FactoidMessage> m_factoidsMessages = new TreeMap<String, FactoidMessage>();
    public TreeMap<String, Vector<String>> m_solutionFactoids = new TreeMap<String, Vector<String>>();
    public TreeMap<String, String> m_solution = new TreeMap<String, String>();

    public class OverallStatistics {
        public int totalPosts = 0;
        public int totalShares = 0;
        public int totalSharesRcv = 0;
        public int totalPulls = 0;
        public int totalIDs = 0;
        //
        public int totalADDs = 0;
        //
        public double postsHour = 0.0;
        public double sharesHour = 0.0;
        public double sharesRcvHour = 0.0;
        public double pullsHour = 0.0;
        public double iDsHour = 0.0;
        public double aDDsHour = 0.0;
        //
        public double withinTeam = 0.0;
        public double outsideTeam = 0.0;

    }
    public OverallStatistics m_overallStatistics = new OverallStatistics();
    //
    public ArrayList<Message> m_messageList = new ArrayList<Message>();
    public Message m_answerMessage;

    /////////////////////////////////////////////////////////////////////////
    // todo: CONSTRUCTOR - has a hack to set start of run to ZERO
    public TrialData() {
    	Message.m_timeStartOfRun = 0;
    }
    
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // METRICS
    //currently: only ADDs is used in next attribute
    public metrics.interaction.OverallInteractions m_overallInteractions = new metrics.interaction.OverallInteractions();

    //
    public InformationQuality m_informationQuality = new InformationQuality();
    public Interactions m_interactions = new Interactions();
    public IDsQualityMap m_subjectsIDsQualityMap = new IDsQualityMap();
    public AwarenessQualityMap m_awarenessQualityMap = new AwarenessQualityMap();
    //public TreeMap<String, Vector<String>> m_networkReach;

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // CLASS CODE
    public Subject getOrganizationSubject(String subjectName_p) {
        Subject subj = null;
        for (Subject s : m_organizationInformation.m_memberList) {
            if (s.m_personName.equals(subjectName_p)) {
                subj = s;
                break;
            }
        }
        return subj;
    }
    public int getNbrTeamLeaders () {
        int nbrLeaders = 0;
        for (Subject s : m_organizationInformation.m_memberList) {
            if (s.m_isTeamLeader) {
                nbrLeaders++;
            }
        }
        return nbrLeaders;
    }
    public int getNbrTeamMembers (String teamName) {
        int nbrLeaders = 0;
        for (Subject s : m_organizationInformation.m_memberList) {
            if (s.m_teamName.equalsIgnoreCase(teamName)) {
                nbrLeaders++;
            }
        }
        return nbrLeaders;
    }
    public int getNbrMembers () {
        int nbr = 0;
        for (Subject s : m_organizationInformation.m_memberList) {
            if (!s.m_isOverallCoordinator && !s.m_isTeamLeader) {
                nbr++;
            }
        }
        return nbr;
    }
    public boolean hasOverallCoordinator () {
        for (Subject s : m_organizationInformation.m_memberList) {
            if (s.m_isOverallCoordinator) {
                return true;
            }
        }
        return false;
    }
    public boolean hasWebsiteAccess (Subject s, String site) {
    		/*
        if (site.equals(Message.m_teamWho))
            return m_websiteAccessPerRoleAndTeam.get(s.m_teamPosition).get(s.m_teamName)[Message.m_websiteWhoIndex];
        if (site.equals(Message.m_teamWhat))
            return m_websiteAccessPerRoleAndTeam.get(s.m_teamPosition).get(s.m_teamName)[Message.m_websiteWhatIndex];
        if (site.equals(Message.m_teamWhere))
            return m_websiteAccessPerRoleAndTeam.get(s.m_teamPosition).get(s.m_teamName)[Message.m_websiteWhereIndex];
        if (site.equals(Message.m_teamWhen))
            return m_websiteAccessPerRoleAndTeam.get(s.m_teamPosition).get(s.m_teamName)[Message.m_websiteWhenIndex];
        else
            return false;
            */
    		return true;
    }
    public int getNbrShares (String factoidMetadata) {
        int count = 0;

        for (Message msg : m_messageList) {
            if ( msg.m_messageType.equals(Message.MESSAGE_TYPE_SHARE)
                 && FactoidMessage.isEqualTo(factoidMetadata, msg.m_data2) )
            {
                count++;
            }
        }
        return count;
    }
    public int getNbrPosts (String factoidMetadata) {
        int count = 0;
        for (Message msg : m_messageList) {
            if ( msg.m_messageType.equals(Message.MESSAGE_TYPE_POST)
                 && FactoidMessage.isEqualTo(factoidMetadata, msg.m_data2) )
            {
                count++;
            }
        }
        return count;
    }
    public int getMaxSharesRcvOrSentPerSubject () {
        int max=0;
        for (Subject s : m_organizationInformation.m_memberList) {
            int countOut = m_overallInteractions.get(s.m_personName).totalShares;
            int countIn  = m_overallInteractions.get(s.m_personName).totalSharesRcv;
            if (countOut > max)
                max = countOut;
            if (countIn > max)
                max = countIn;
        }
        return max;
    }
    public int getNbrActionsBySubjectUntilTime (String subjectName, double time) {
        int nbrActions = 0;
        for (Message m : m_messageList) {
            if ( m.m_commonData.sequence!=null && Double.parseDouble(m.m_commonData.sequence) <= time )
            {
                if ( m.m_messageType.equals(Message.MESSAGE_TYPE_SHARE)
                     || m.m_messageType.equals(Message.MESSAGE_TYPE_POST)
                     || m.m_messageType.equals(Message.MESSAGE_TYPE_PULL)
                     || m.m_messageType.equals(Message.MESSAGE_TYPE_IDENTIFY))
                {
                    if ( m.m_data.equals(subjectName) )
                    {
                        nbrActions++;
                    }
                }
            }
         }//end for
         return nbrActions;
    }
    public String getAgentsSetup() {
        String setup = "";
        for (String agentFileName : m_agentFile) {
            String value = getTypeOfAgent(agentFileName);
            if (value==null) value = Message.m_agentSetupMap.get("UNKNOWN");
            setup += value;
        }
        return setup;
    }
    public static String getTypeOfAgent(String agentFileName) {
        String setup = "";
        for (String key : Message.m_agentSetupMap.keySet()) {
            if ( agentFileName.toUpperCase().indexOf(key)!=-1 )
                setup = Message.m_agentSetupMap.get(key);
        }
        return setup;
    }

    //////////////////////////////////////////////////////////////////////
    //
    //
    public void addMessage (Message message_p) {

        //System.out.println("message line: "+message_p.Write());

        if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_INITIATE)) {
            m_trialInformation.m_date = message_p.m_commonData.date;
            //trialInformation.m_timeStart = message_p.m_commonData.time;
            //
            m_trialInformation.factoidSet = message_p.m_data;
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_FACTOID)) {
            m_factoidStats.totalFactoids++;
            if (message_p.m_factoid.m_relevance==Message.m_AChar) {
                m_answerMessage = message_p;
                m_factoidStats.hasAnswer=true;
                m_factoidStats.totalFactoids--;
            }
            else {
                m_factoidsMessages.put(message_p.m_factoid.m_factoidId, (FactoidMessage)message_p);
                if (message_p.m_factoid.m_relevance==Message.m_EChar) m_factoidStats.totalExpertise_Factoids++;
                else if (message_p.m_factoid.m_relevance==Message.m_KChar) m_factoidStats.totalKey_Factoids++;
                else if (message_p.m_factoid.m_relevance==Message.m_SChar) m_factoidStats.totalSupportive_Factoids++;
                else if (message_p.m_factoid.m_relevance==Message.m_MChar) m_factoidStats.totalMisinfo_Factoids++;
                else m_factoidStats.totalNoise_Factoids++;
            }
        }
        //only for webELICIT
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_ORGANIZATION))
        {
            if (m_organizationInformation.m_organizationName == null) {
                m_organizationInformation.m_organizationName = message_p.m_data;
                //fill org websites names
                OrganizationMessage.GetWebsiteNames(message_p.m_logLine, m_organizationInformation.m_teamList);
            }
            else {
                OrganizationMessage.FillWebSiteAccess(message_p, m_websiteAccessPerRoleAndTeam);
            }
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_START)) {
            m_trialInformation.m_timeStart = message_p.m_commonData.time;
            
            //todo: hack - start of run
            Message.m_timeStartOfRun = Double.valueOf(message_p.m_commonData.sequence);
            System.out.println("HACK:  set start of run to "+Message.m_timeStartOfRun);
            
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_BATCH)) {
            m_trialInformation.m_compression = Double.valueOf(message_p.m_data);
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_ASSIGN)) {
            Subject subject = new Subject();
            
            subject.m_personName = message_p.m_assignmentId.m_name;
            subject.m_teamPosition = message_p.m_assignmentId.m_role;
            subject.m_teamName = message_p.m_assignmentId.m_team;
            subject.m_isOverallCoordinator = AssignMessage.isOverallCoordinator(subject.m_teamPosition);
            subject.m_isTeamLeader = AssignMessage.isTeamLeader(subject.m_teamPosition);
            
/*            subject.m_personName = AssignMessage.getSubjectName(message_p.m_logLine);
            subject.m_teamPosition = AssignMessage.getSubjectRole(message_p.m_logLine);
            subject.m_isOverallCoordinator = AssignMessage.isOverallCoordinator(message_p.m_logLine);
            subject.m_isTeamLeader = AssignMessage.isTeamLeader(message_p.m_logLine);
*/            
//            if (AssignMessage.isValidPosition(subject.m_teamPosition)) {
            m_organizationInformation.m_memberList.add(subject);
            m_informationQuality.addSubject(subject.m_personName);
            //
            m_interactions.addSubject(subject);
            //
            m_subjectsIDsQualityMap.addSubject(subject.m_personName);
//            }

            if (!m_organizationInformation.m_teamList.contains(subject.m_teamName)) {
                m_organizationInformation.m_teamList.add(subject.m_teamName);
            }
            m_informationQuality.addTeam(subject.m_teamName);
            m_interactions.addTeam(subject.m_teamName);
            
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_DIST)) {
            //Subject s = m_organizationInformation.getSubject(message_p.m_data);

            Subject s = m_organizationInformation.getSubject(message_p.m_assignmentId.m_name);
            
            String factoidMetadata = message_p.m_factoid.m_factoidId;
            //factoidMetadata = DistMessage.getFactoidMetadata(message_p.m_logLine, s.m_teamPosition);
            
            // three lists are affected:
            // - new info
            // - info accessibility index
            // - info accessed index
            m_informationQuality.m_accessibilityNewInfomationServer.add(s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), factoidMetadata, Message.m_server);
            m_informationQuality.m_accessibilityIndex.add(s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), factoidMetadata, Message.m_server);
            m_informationQuality.m_accessibilityIndex.addIfSharedByTeamAndOverall(m_organizationInformation.m_memberList, s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), factoidMetadata, Message.m_server);
            //m_informationQuality.m_accessibilityIndex.add(s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), factoidMetadata, Message.m_server);
            m_informationQuality.m_reachedIndex.add(s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), factoidMetadata, Message.m_server);
            m_informationQuality.m_reachedIndex.addIfSharedByTeamAndOverall(m_organizationInformation.m_memberList, s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), factoidMetadata, Message.m_server);

            //
            m_interactions.m_hoarding.addReceived(s.m_personName, factoidMetadata, Double.parseDouble(message_p.m_commonData.sequence));
            
        	double timeFactoid = Double.valueOf(message_p.m_commonData.sequence);
        	if (timeFactoid > m_trialInformation.timeOfLastFactoid) {
        		m_trialInformation.timeOfLastFactoid = timeFactoid;

        		//System.out.println("time of last factoid: "+m_trialInformation.timeOfLastFactoid);        		
        		
        	}
        	            
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_SHARE)) {
            //
            m_overallStatistics.totalShares++;

            ShareMessage m =(ShareMessage)message_p;
            
            //m.FillData(m.m_logLine);
            
            //
            // two lists are affected:
            // - info accessibility index
            // - info accessed index
            Subject origS = getOrganizationSubject(message_p.m_assignmentId.m_name);
            Subject destS = getOrganizationSubject(message_p.m_destSubject);
            //
            m_informationQuality.m_accessibilityIndex.add(destS.m_personName, destS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
            m_informationQuality.m_accessibilityIndex.addIfSharedByTeamAndOverall(m_organizationInformation.m_memberList, destS.m_personName, destS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
            //
            m_informationQuality.m_reachedIndex.add(destS.m_personName, destS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
            m_informationQuality.m_reachedIndex.addIfSharedByTeamAndOverall(m_organizationInformation.m_memberList, destS.m_personName, destS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
            //
            m_informationQuality.m_formsInteraction.addComulativeSet1(origS.m_personName, origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
            // Q interactions
            m_interactions.addShare(origS, destS, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId);
            m_awarenessQualityMap.addAwarenessAction(origS.m_personName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, m.m_relevanceEval);
            m_awarenessQualityMap.addAwarenessAction(origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, m.m_relevanceEval);
            //            
            m_interactions.m_hoarding.addShared(origS.m_personName, m.m_factoid.m_factoidId, Double.parseDouble(message_p.m_commonData.sequence));
            m_interactions.m_hoarding.addReceived(destS.m_personName, m.m_factoid.m_factoidId, Double.parseDouble(message_p.m_commonData.sequence));
            
            //
            m_overallInteractions.get(origS.m_personName).totalShares++;
            m_overallInteractions.get(destS.m_personName).totalSharesRcv++;
            //
            if ( origS.m_teamName.equals(destS.m_teamName) ) {
                m_overallInteractions.get(origS.m_personName).withinTeam++;
                m_overallStatistics.withinTeam++;
            }
            else {
                m_overallInteractions.get(origS.m_personName).outsideTeam++;
                m_overallStatistics.outsideTeam++;
            }
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_POST)) {
            m_overallStatistics.totalPosts++;
            PostMessage m =(PostMessage)message_p;
            
            //m.FillData(m.m_logLine);
            //
            //Subject origS = getOrganizationSubject(message_p.m_data);
            Subject origS = getOrganizationSubject(message_p.m_assignmentId.m_name);
            
            m_informationQuality.m_formsInteraction.addComulativeSet2(origS.m_personName, origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
//System.out.println("seq: "+message_p.m_commonData.sequence+"post to site - data2:"+m.m_data2+" data:"+m.m_data);
            // list affected:
            // - info accessibility index
            //who has access to site? increase accessibility index
            for ( Subject s : m_organizationInformation.m_memberList ) {
                if (hasWebsiteAccess(s,message_p.m_postSite)) {
                    m_informationQuality.m_accessibilityIndex.add(s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
                    m_informationQuality.m_accessibilityIndex.addIfSharedByTeamAndOverall(m_organizationInformation.m_memberList, s.m_personName, s.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, origS.m_personName);
                }
            }
            // Q interactions
            m_interactions.addPost(origS, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, message_p.m_postSite);
            m_awarenessQualityMap.addAwarenessAction(origS.m_personName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, m.m_relevanceEval);
            m_awarenessQualityMap.addAwarenessAction(origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_factoid.m_factoidId, m.m_relevanceEval);
            //
            m_interactions.m_hoarding.addShared(origS.m_personName, m.m_factoid.m_factoidId, Double.parseDouble(message_p.m_commonData.sequence));
            //
            m_overallInteractions.get(origS.m_personName).totalPosts++;
            //
            if ( origS.m_teamName.equals(Message.m_teamNA) || origS.m_teamName.equals(message_p.m_postSite) ) {
                m_overallInteractions.get(origS.m_personName).withinTeam++;
                m_overallStatistics.withinTeam++;
            }
            else {
                m_overallInteractions.get(origS.m_personName).outsideTeam++;
                m_overallStatistics.outsideTeam++;
            }
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_PULL)) {
            m_overallStatistics.totalPulls++;
            
            //Subject origS = getOrganizationSubject(message_p.m_data);
            
            Subject origS = getOrganizationSubject(message_p.m_assignmentId.m_name);
            
            // list affected:
            // - info accessed index
            //get all factoids posted in site
            for (Message m : m_messageList) {
                if (m.m_messageType.equals(Message.MESSAGE_TYPE_POST)
                    && m.m_postSite.equals(message_p.m_pullSite))
                {
                    m_informationQuality.m_reachedIndex.add(origS.m_personName, origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), message_p.m_pullSite, origS.m_personName);
                    m_informationQuality.m_reachedIndex.addIfSharedByTeamAndOverall(m_organizationInformation.m_memberList, origS.m_personName, origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), message_p.m_pullSite, origS.m_personName);
                    //m_informationQuality.m_reachedIndex.addShared(m_organizationInformation.m_memberList, origS.m_personName, origS.m_teamName, Double.parseDouble(message_p.m_commonData.sequence), m.m_data2, origS.m_personName);
                }
            }
            // Q interactions
            m_interactions.addPull(origS, Double.parseDouble(message_p.m_commonData.sequence), message_p.m_pullSite);
            //
            m_overallInteractions.get(origS.m_personName).totalPulls++;
            //
            if ( origS.m_teamName.equals(Message.m_teamNA) || origS.m_teamName.equals(message_p.m_pullSite) ) {
                m_overallInteractions.get(origS.m_personName).withinTeam++;
                m_overallStatistics.withinTeam++;
            }
            else {
                m_overallInteractions.get(origS.m_personName).outsideTeam++;
                m_overallStatistics.outsideTeam++;
            }
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_IDENTIFY)) {
            m_overallStatistics.totalIDs++;
            //
            m_subjectsIDsQualityMap.AddID(Double.parseDouble(message_p.m_commonData.sequence), message_p.m_data, message_p.m_data2);
            //
            m_overallInteractions.get(message_p.m_data).totalIDs++;
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_ADMIN)) {
        	
            String teamName = AdminMessage.getTeamName(message_p.m_logLine);
            if (teamName!=null) {
                Subject s = getOrganizationSubject(message_p.m_data);
                if (s!=null) {
                    s.m_teamName = teamName.toUpperCase();
                    if (!m_organizationInformation.m_teamList.contains(s.m_teamName)) {
                        m_organizationInformation.m_teamList.add(s.m_teamName);
                    }
                }
                m_informationQuality.addTeam(teamName.toUpperCase());
                //
                m_interactions.addTeam(teamName.toUpperCase());
            }
            // may also be solution
            else if (AdminMessage.isTrialAnswer(message_p.m_logLine)) {
                m_solution.put(message_p.m_data, message_p.m_data2);
            }
        	
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_SOLUTIONFACTOIDS)) {
            m_solutionFactoids.put(message_p.m_data, SolutionFactoidsMessage.getSolutionFactoids(message_p.m_data2));
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_SOLUTION)) {
            m_solution.put(Message.m_teamWho, SolutionMessage.getWho(message_p.m_logLine));
            m_solution.put(Message.m_teamWhat, SolutionMessage.getWhat(message_p.m_logLine));
            m_solution.put(Message.m_teamWhere, SolutionMessage.getWhere(message_p.m_logLine));
            m_solution.put(Message.m_teamWhen, SolutionMessage.getWhen(message_p.m_logLine));
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_RUNNAME)) {
            m_trialInformation.m_runname = message_p.m_data;
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_AGENT)) {
            m_agentFile.add(message_p.m_data2);
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_ADD)) {
            m_overallInteractions.get(message_p.m_data).totalADDs++;
            m_overallStatistics.totalADDs++;
        }
        else if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_END)) {
            //System.out.println("duration"+message_p.m_commonData.sequence);
            //m_trialInformation.m_duration=10;
            
            //m_trialInformation.m_durationSec=Long.valueOf(message_p.m_commonData.sequence);
            m_trialInformation.m_durationSec=(long)Double.valueOf(message_p.m_commonData.sequence).doubleValue();

            m_trialInformation.m_durationMin= (int) ( ((double)m_trialInformation.m_durationSec) / 60.0);
            m_trialInformation.m_timeFinish=message_p.m_commonData.time;
            //
            m_overallStatistics.sharesHour = (double)m_overallStatistics.totalShares / m_trialInformation.m_durationMin * 60.0;
            m_overallStatistics.sharesRcvHour = (double)m_overallStatistics.totalSharesRcv / m_trialInformation.m_durationMin * 60.0;
            m_overallStatistics.postsHour  = (double)m_overallStatistics.totalPosts  / m_trialInformation.m_durationMin * 60.0;
            m_overallStatistics.pullsHour  = (double)m_overallStatistics.totalPulls  / m_trialInformation.m_durationMin * 60.0;
            m_overallStatistics.iDsHour    = (double)m_overallStatistics.totalIDs    / m_trialInformation.m_durationMin * 60.0;
            m_overallStatistics.aDDsHour   = (double)m_overallStatistics.totalADDs    / m_trialInformation.m_durationMin * 60.0;
            //
            m_informationQuality.endTime(Double.parseDouble(message_p.m_commonData.sequence));
            //
            m_interactions.endTime(Double.parseDouble(message_p.m_commonData.sequence));
            //
            m_informationQuality.m_criticalInformationAccessible.setSolutionFactoids(m_solutionFactoids);
            //
            m_subjectsIDsQualityMap.setSolution(m_solution);
            m_subjectsIDsQualityMap.setOrganizationInfo(m_organizationInformation);
            m_subjectsIDsQualityMap.CalculateAll();
            //calculate nbrAction to first correct ID (must be done after m_subjectsIDsQualityMap.CalculateAll())
            if (m_subjectsIDsQualityMap.m_subjectNameWithFirstAllCorrectID != null) {
                m_subjectsIDsQualityMap.m_nbrActionsToFirstAllCorrectID = getNbrActionsBySubjectUntilTime(
                        m_subjectsIDsQualityMap.m_subjectNameWithFirstAllCorrectID,
                        m_subjectsIDsQualityMap.m_firstAllCorrectID);
            }
            //
            m_overallInteractions.CalculateTotalsPerHour(m_trialInformation.m_durationSec);
        }
        m_messageList.add(message_p);
    }


}
