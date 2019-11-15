/*
 * DistMessage.java
 *
 * Created on 27 June 2007, 22:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

/**
 * deliver a factoid to a player
 *
 * @author Marco
 */
public class DistMessage extends Message {

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;

        m_assignmentId = new AssignmentId();
        m_factoid = new Factoid();

        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        m_assignmentId.m_facetId = st.nextToken();

        // subject |  org-type  |   role (2 strings) | factoid-id | factoid-content
        //06/10/2019 06:44:19.262 0002.891 - dist         1570365843823- zNIC1 MDC2-C3-2-NICIM1High NIC  IM 1 1E11711 "The Lion is involved"

        m_data = st.nextToken();

        m_assignmentId.m_name = m_data;
        m_assignmentId.m_organisationType = st.nextToken();
        m_assignmentId.m_role = st.nextToken() + " "+st.nextToken();
        //hack... search for factoid ID
        do {
            m_factoid.m_factoidId = st.nextToken();        	
        } while ( !FactoidMessage.isFactoid(m_factoid.m_factoidId) );
        m_factoid.m_relevance = FactoidMessage.getFactoidRelevanceChar(m_factoid.m_factoidId);   
        st.nextToken("\"");
        m_factoid.m_factoidtext = st.nextToken("\"");
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \t"+m_assignmentId.m_name
               +" \t"+m_assignmentId.m_facetId
               +" \t"+m_assignmentId.m_organisationType
               +" \t"+m_assignmentId.m_role
               +" \t"+m_factoid.m_factoidId
               +" \t"+m_factoid.m_relevance
               +" \t"+m_factoid.m_factoidtext;
    }

    public static String getFactoidMetadata(String logLine, String teamRole) {
        //factoid metadata is always after team-role
        String s = logLine.substring(logLine.indexOf(teamRole)+teamRole.length(), logLine.length());
        StringTokenizer st = new StringTokenizer(s);
        return st.nextToken();
    }

    public static String getFactoidMetadata_v01(String logLine, boolean isEdge) {
        StringTokenizer st = new StringTokenizer(logLine);
        parseNWords(st, 8);
        if (!isEdge)
            st.nextToken();
        return st.nextToken();
    }
}
