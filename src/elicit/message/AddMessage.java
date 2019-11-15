/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.message;

import java.util.StringTokenizer;

/**
 *
 * @author mmanso
 */
public class AddMessage extends Message {

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

