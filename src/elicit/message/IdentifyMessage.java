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
public class IdentifyMessage extends Message {

    public static char m_answDelimiter = '\'';
    public static char m_answWhenDelimiter = '-';

    @Override
    public void Read(String logLine) {
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        st.nextToken();
        m_data = st.nextToken();  //subject-name

        st.nextToken("\"");
        m_data2 = st.nextToken("\"").toUpperCase(); //answer
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \t"+m_data
               +" \t"+m_data2;
    }

    //removes stating and end characters: '
    public static String CleanAnswer (String answ) {
        String text = answ;
        while (text!=null && text.charAt(0)==m_answDelimiter) {
            text = text.substring(1);
        }
        while (text!=null && text.charAt(text.length()-1)==m_answDelimiter) {
            text = text.substring(0,text.length()-1);
        }
        if (text!=null)
            text = text.trim();
        		//System.out.println(answ);
        return text;
    }

    //if answer is '?' discard
    public static String ReturnValidAnswer(String answ) {
        if (answ.equals("?"))
            return null;
        else {
            answ = answ.replace('_', ' ');
            return answ;
        }
    }

    public static String getWhoAnswer (String idMessage) {
        StringTokenizer st = new StringTokenizer(idMessage, "'");
        st.nextToken();
        return ReturnValidAnswer(st.nextToken());
        /*
        String answ = idMessage.substring(Message.m_Prewho.length(),
                                          idMessage.indexOf(Message.m_Prewhat)).trim();
        if ( answ.charAt(0) == m_noAnswer )
            answ = null;
        return CleanAnswer(answ);
         *
         */
    }
    public static String getWhatAnswer (String idMessage) {
        StringTokenizer st = new StringTokenizer(idMessage, "'");
        //discard who
        for (int i=0; i<3; i++)
            st.nextToken();
        //
        return ReturnValidAnswer(st.nextToken());

//        String answ = idMessage.substring(idMessage.indexOf(Message.m_Prewhat)+Message.m_Prewhat.length(),
//                                          idMessage.indexOf(Message.m_Prewhere)).trim();
//        if ( answ.charAt(0) == m_noAnswer )
//            answ = null;
//        return CleanAnswer(answ);
    }
    public static String getWhereAnswer (String idMessage) {
        StringTokenizer st = new StringTokenizer(idMessage, "'");
        //discard who and what
        for (int i=0; i<5; i++)
            st.nextToken();
        //
        return ReturnValidAnswer(st.nextToken());
//        String answ = idMessage.substring(idMessage.indexOf(Message.m_Prewhere)+Message.m_Prewhere.length(),
//                                          idMessage.indexOf(Message.m_PreWhenMonth)).trim();
//        if ( answ.charAt(0) == m_noAnswer )
//            answ = null;
//        return CleanAnswer(answ);
    }
    public static String getWhenMonthAnswer (String idMessage) {
        StringTokenizer st = new StringTokenizer(idMessage, "'");
        //discard who and what
        for (int i=0; i<7; i++)
            st.nextToken();
        //
        return ReturnValidAnswer(st.nextToken());

//        String time = getWhenAnswer(idMessage);
//        if (time==null)
//            return null;
//        StringTokenizer st = new StringTokenizer(time);
//        st.nextToken();
//        st.nextToken();
//        time = st.nextToken();
//        if (time.charAt(0)==m_answWhenDelimiter) {
//            time = null;
//        }
//        return time;
    }
    public static String getWhenDayAnswer (String idMessage) {
        StringTokenizer st = new StringTokenizer(idMessage, "'");
        //discard who and what
        for (int i=0; i<9; i++)
            st.nextToken();
        //
        return ReturnValidAnswer(st.nextToken());

//        String time = getWhenAnswer(idMessage);
//        if (time==null)
//            return null;
//        StringTokenizer st = new StringTokenizer(time);
//        st.nextToken();
//        time = st.nextToken();
//        if (time.charAt(0)==m_answWhenDelimiter) {
//            time = null;
//        }
//        return time;
    }
    public static String getWhenTimeAnswer (String idMessage) {
        StringTokenizer st = new StringTokenizer(idMessage, "'");
        String time ="";
        //discard who, what, where
        for (int i=0; i<11; i++)
            st.nextToken();
        //
        String r = ReturnValidAnswer(st.nextToken());
        if (r!=null) {
            if (r.indexOf(':')==-1)
                r+=":00";
            time = r;
        }
        st.nextToken();
        r = ReturnValidAnswer(st.nextToken());
        if (r!=null) {
        	//HACK....check if 'DAY' to convert to 'AM'
        	if (r.equals(Message.m_answer_DAY)) {
        		r=Message.m_answer_AM;
        	}
        	time += r;
        }
        return time;

//        //String answ = CleanAnswer(idMessage.substring(idMessage.indexOf(Message.m_Prewhen)+Message.m_Prewhen.length()).trim());
//        String time = getWhenAnswer(idMessage);
//        if (time==null)
//            return null;
//        StringTokenizer st = new StringTokenizer(time);
//        time = st.nextToken();
//        if (time.charAt(0)==m_answWhenDelimiter) {
//            time = null;
//        }
//        return time;
    }

    public static String getWhenAnswer (String idMessage) {
        //when encloses ? with ' - must clean before comparing
        String answ = CleanAnswer(idMessage.substring(idMessage.indexOf(Message.m_PreWhenMonth)+Message.m_PreWhenMonth.length()).trim());
        //String answ = CleanAnswer(idMessage.substring(idMessage.indexOf(Message.m_Prewhen)+Message.m_Prewhen.length()).trim());
        if ( answ.charAt(0) == m_noAnswer )
            answ = null;
        return answ;
    }

}
