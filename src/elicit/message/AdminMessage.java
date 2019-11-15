/*
 * AdminMessage.java
 *
 * Created on 27 June 2007, 22:30
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
public class AdminMessage extends Message {

    //web-elicit
    //public static String TRIAL_STARTING = "TRIAL STARTING";
    public static String TRIAL_STARTING = "STARTING";
    //v01-elicit
    public static String CONFIG = "CONFIG";
    public static String CONFIG_ACK = "CONFIG ACK";

    //common
    public static String TRIAL_ANSWER = "The trial answer is: ";
    public static String END_OF_TRIAL = "END OF TRIAL";

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);
        st.nextToken();
        m_data = st.nextToken();
        st.nextToken();
        m_data2 = st.nextToken();
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

    public static boolean isTrialStarting (String logLine) {
        if (logLine.indexOf(TRIAL_STARTING)!=-1)
            return true;
        return false;
    }

    public static boolean isConfigAck (String logLine) {
        if (logLine.indexOf(CONFIG_ACK)!=-1)
            return true;
        return false;
    }

    public static boolean isTrialAnswer (String logLine) {
        if (logLine.indexOf(TRIAL_ANSWER)!=-1)
            return true;
        return false;
    }

    public static boolean isEndTrial (String logLine) {
        if (logLine.indexOf(END_OF_TRIAL)!=-1)
            return true;
        return false;
    }

    public static String getTeamName (String logLine) {
        String s = null;

        //web-elicit
        if (isTrialStarting(logLine)) {
            s = getTeam(logLine);
        }
        //elicit_v01
        else if (isConfigAck(logLine)) {
            s = getTeam(logLine);
            //however, if EDGE, ELICIT_v01 may not have teams assigned
            if (s.indexOf(CONFIG)!=-1)
                s=Message.m_teamNA;
        }
        return s;
    }

    private static String getTeam (String logLine) {
        StringTokenizer st = new StringTokenizer(logLine);
        parseNWords(st, 8);
        return st.nextToken();
    }

}
