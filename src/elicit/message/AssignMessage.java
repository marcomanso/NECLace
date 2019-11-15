/*
 * AssignMessage.java
 *
 * Created on 27 June 2007, 22:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.StringTokenizer;

/**
 * Assing player positions:  facetId, country
 *                           team, position (hierarchy)
 *
 * @author Marco
 */
public class AssignMessage extends Message {

    @Override
    public void Read(String logLine) {
    	
    	m_assignmentId = new AssignmentId();
    	
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);
        m_assignmentId.m_facetId = st.nextToken();
        m_data = st.nextToken();
		m_assignmentId.m_name = m_data;
        //get assignment data
        if (m_data.indexOf(m_ignoreOrganisation)==-1) {
        		m_assignmentId.m_organisationType = st.nextToken();
        		m_assignmentId.m_role = st.nextToken()+" "+st.nextToken();
        		m_assignmentId.m_team = st.nextToken().toUpperCase();
        }
        //st.nextToken();
        //m_data2 = st.nextToken("\n");
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \tname="+m_assignmentId.m_name
               +" \tfacet="+m_assignmentId.m_facetId
               +" \torg-type="+m_assignmentId.m_organisationType
               +" \trole="+m_assignmentId.m_role
               +" \tteam="+m_assignmentId.m_team;
    }

    public static String getOrganizationName (String logline_p) {
        StringTokenizer st = new StringTokenizer(logline_p);
        parseNWords(st, 7);
        return st.nextToken();
    }
    public static String getSubjectName (String logline_p) {
        StringTokenizer st = new StringTokenizer(logline_p);
        parseNWords(st, 6);
        return st.nextToken();
    }
    public static String getSubjectOrganization (String message_p) {
        StringTokenizer st = new StringTokenizer(message_p);
        parseNWords(st, 7);
        return st.nextToken();
    }
    public static String getSubjectRole (String logline_p) {
        StringTokenizer st = new StringTokenizer(logline_p);
        parseNWords(st, 8);
        return st.nextToken("\n").trim();
    }

    public static String getSubjectRole_v01 (String logline_p) {
        StringTokenizer st = new StringTokenizer(logline_p);
        parseNWords(st, 9);
        st.nextToken("\"");
        return st.nextToken("\"").trim();
    }

    public static boolean isOverallCoordinator(String role_p) {
        if ( role_p.indexOf(m_teamPositionCrossTeamCoordinator) != -1
             || role_p.indexOf(m_teamPositionCrossTeamCoordinator_v01) != -1 //ELICIT_v01
             || role_p.indexOf(m_teamPositionInformationBroker) != -1
             || role_p.indexOf(m_teamPositionIsolatedCoordinator) != -1 
             || role_p.indexOf(m_teamPositionFacilitator) != -1 )
        {
            return true;
        }
        return false;
    }
    
    public static boolean isTeamLeader(String role_p) {
        if ( role_p.indexOf(m_teamPositionTeamLeader) != -1 )
        {
            return true;
        }
        return false;
    }

    public static boolean isValidPosition(String role_p) {
        if ( role_p.indexOf(m_teamPositionInvalid) != -1 )
        {
            return false;
        }
        return true;
    }

}
