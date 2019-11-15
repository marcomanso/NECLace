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
public class SolutionMessage extends Message {

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);
        m_data = st.nextToken().toUpperCase();  //solution space
        m_data2 = st.nextToken("\n").trim().toUpperCase(); //solution
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

    public static String getWho(String logLine) {
//        StringTokenizer st = new StringTokenizer(logLine, Message.m_trialAnswerText);
//        st.nextToken();
//        return st.nextToken(Message.m_trialAnswerTextWhat);
//System.out.println(logLine);
        int start = logLine.toUpperCase().indexOf(Message.m_trialAnswerText) + Message.m_trialAnswerText.length();
        int end   = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhat, start);
        if (end==-1)
            end = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhat2, start);
        return logLine.toUpperCase().substring(start, end);
    }
    public static String getWhat(String logLine) {
        int start = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhat) + Message.m_trialAnswerTextWhat.length();
        if (logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhat)==-1)
            start = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhat2) + Message.m_trialAnswerTextWhat2.length();
        int end   = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhere, start);
        return logLine.toUpperCase().substring(start, end);
    }
    public static String getWhere(String logLine) {
        int start = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhere) + Message.m_trialAnswerTextWhere.length();
        int end   = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhen, start);
        return logLine.toUpperCase().substring(start, end);
    }
    public static String getWhen(String logLine) {
        int indexWhen = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhere);
        int start = logLine.toUpperCase().indexOf(Message.m_trialAnswerTextWhen, indexWhen) + Message.m_trialAnswerTextWhen.length();
        return logLine.toUpperCase().substring(start, logLine.length());
    }

    // extract data from solution:  DD MM 'AT' HH:MM[PM/AM]
    public static String getDay(String anws) {
        StringTokenizer st = new StringTokenizer(anws);
        st.nextToken();
        return st.nextToken();
    }
    public static String getMonth(String anws) {
        StringTokenizer st = new StringTokenizer(anws);
        return st.nextToken();
    }
    public static String getTime(String anws) {
        StringTokenizer st = new StringTokenizer(anws);
        st.nextToken();
        st.nextToken();
        st.nextToken();
        return st.nextToken()+st.nextToken();
    }

}
