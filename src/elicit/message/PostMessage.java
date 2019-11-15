/*
 * PostMessage.java
 *
 * Created on 27 June 2007, 22:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

/**
 *
 * @author Marco
 */
public class PostMessage extends Message {
    
    @Override
    public void Read(String logLine) {
    		
		m_assignmentId = new AssignmentId();
		m_factoid = new Factoid();
    	
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        st.nextToken();
        //subject - sender
        m_data = st.nextToken();
        
        //
        m_assignmentId.m_name = m_data;
        m_assignmentId.m_organisationType = st.nextToken();
        m_assignmentId.m_team = st.nextToken().toUpperCase();
        		
        //
        m_factoid.m_factoidId = st.nextToken();
        m_factoid.m_relevance=FactoidMessage.getFactoidRelevanceChar(m_factoid.m_factoidId);
        st.nextToken("\"");
        m_factoid.m_factoidtext = st.nextToken();
        st.nextToken(" \n");
        m_postSite = st.nextToken().toUpperCase();        

        //
        //FillData(logLine);
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \t"+m_assignmentId.m_name
               +" \tteam="+m_assignmentId.m_team
               +" \tpost="+m_postSite
               +" \tfactoid="+m_factoid.m_factoidId
               +" \t"+m_relevanceEval
               +" \t"+m_trustEval;
    }

    /*
    public void FillData(String logLine) {
        StringTokenizer st = new StringTokenizer(logLine);
        //parseNWords(st, 10);
        //ignore factoid text
        st.nextToken("\"");
        st.nextToken("\"");
        st.nextToken(" \t\n");
        m_postSite = st.nextToken().toUpperCase();
        if (st.hasMoreTokens())
            m_relevanceEval = getInt(st.nextToken());
        if (st.hasMoreTokens())
            m_trustEval = getInt(st.nextToken());
    }
    */

}
