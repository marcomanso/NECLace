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
public class CompressionMessage extends Message {

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        m_data = st.nextToken();
        //filter out text - get only compression number
        m_data = getCompression(m_data);
        m_data2 = null;
    }

    public static boolean isCompressionMessage(String logLine) {
        if (logLine.indexOf(Message.m_batch_compression)!=-1)
            return true;
        return false;
    }

    public static String getCompression(String token) {
        return token = token.substring(
                            token.indexOf(Message.m_batch_compression)
                            +Message.m_batch_compression.length(),
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
