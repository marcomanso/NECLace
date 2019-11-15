/*
 * HowSeenMessage.java
 *
 * Created on 27 June 2007, 22:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

/**
 *   SUBJECT INFORMATION REQUEST
 *   player how_seen request
 *
 * @author Marco
 */
public class HowSeenMessage extends Message {

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
