/*
 * IdentifyMessage.java
 *
 * Created on 27 June 2007, 22:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.StringTokenizer;

/**
 *
 * @author Marco
 */
public class SolutionFactoidsMessage extends Message {

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);
        m_data = st.nextToken().toUpperCase();  //solution space
        m_data2 = st.nextToken("\n").trim(); //answer
    }

    public static java.util.Vector<String> getSolutionFactoids (String solutionFactoidsLog) {
        java.util.Vector<String> v = new java.util.Vector<String>();
        StringTokenizer st = new StringTokenizer(solutionFactoidsLog);
        while (st.hasMoreElements()) {
            v.add(st.nextToken());
        }
        return v;
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
