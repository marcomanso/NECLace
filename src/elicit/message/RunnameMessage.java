/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.message;

import java.util.StringTokenizer;

/**
 *
 * @author marcomanso
 */
public class RunnameMessage extends Message {

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        //ignore next two words
        st.nextToken();
        st.nextToken();

        m_data = st.nextToken();

        /*
        m_data = st.nextToken();
        //filter out text - get only compression number
        m_data = getRunname(m_data);
        m_data2 = null;
         *
         */
    }

    public static boolean isRunnameMessageInMetadata(String logLine) {
        if (logLine.indexOf(Message.m_batch_runnameMetadata)!=-1)
            return true;
        return false;
    }

    public static boolean isRunnameMessage(String logLine) {
        if (logLine.indexOf(Message.m_batch_runname)!=-1)
            return true;
        return false;
    }

    public static String getRunname(String token) {
        return token = token.substring(
                            token.indexOf(Message.m_batch_runname)
                            +Message.m_batch_runname.length(),
                            token.length());
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
