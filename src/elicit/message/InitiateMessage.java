package elicit.message;
/*
 * InitiateMessage.java
 *
 * Created on 27 June 2007, 22:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author Marco
 */
public class InitiateMessage extends Message {
    //structure
    //<date> <time> <sequence> - initiate <factoid-set> <nbr-clients>
    //
    //NOTE:
    //  ELICITv1: <nbr-clients> = nbr players
    //  webELICIT: <nbr-clients> = nbr players + 1 (includes moderator)

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);
        //get factoid-set
        m_data = st.nextToken();
    }

    @Override
    public String Write() {
        return m_messageType
               +"\t"+m_commonData.date
               +"\t"+m_commonData.time
               +"\t"+m_commonData.sequence
               +"\t"+m_data;
    }
    
}
