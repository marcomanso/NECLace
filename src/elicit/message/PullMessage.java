/*
 * PullMessage.java
 *
 * Created on 27 June 2007, 22:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

/**
 * retrieve message from site
 *
 * @author Marco
 */
public class PullMessage extends Message {
 
    @Override
    public void Read(String logLine) {
    		m_assignmentId = new AssignmentId();
    	
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        st.nextToken();
        m_data = st.nextToken();
        
        //  name	 |  org_type |  team  | website
        m_assignmentId.m_name = m_data;
        m_assignmentId.m_organisationType = st.nextToken();
        m_assignmentId.m_team = st.nextToken().toUpperCase();
        
        m_data2 = st.nextToken().toUpperCase();
        m_pullSite = m_data2;
        
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \t"+m_assignmentId.m_name
               +" \tteam="+m_assignmentId.m_team
               +" \tpull="+m_pullSite;
    }

}
