/*
 * WhatSeeMessage.java
 *
 * Created on 27 June 2007, 22:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

/**
 * player what_see request
 *
 * @author Marco
 */
public class WhatSeeMessage extends Message {

//    public String m_facetId;
//    public String m_playerName;
//    public String m_playerTeam; //if hierarchy
    
    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        st.nextToken();
        m_data = st.nextToken();
        m_data2 = st.nextToken("\n");
    }

    @Override
    public String Write() {
        return m_messageType
               +"\t"+m_commonData.date
               +"\t"+m_commonData.time
               +"\t"+m_commonData.sequence
               +"\t"+m_data
               +"\t"+m_data2;
    }
    
}
