/*
 * FactoidMessage.java
 *
 * Created on 27 June 2007, 22:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import java.util.*;

/**
 *
 * @author Marco
 */
public class FactoidMessage extends Message {
    
    @Override
    public void Read(String logLine) {
        m_factoid = new Factoid();
        
        m_logLine = logLine;
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);

        //get factoid-ser and nbr clients
        m_data = st.nextToken();
        st.nextToken("\"");
        m_data2 = st.nextToken("\"");

        m_factoid.m_factoidId = m_data;
        m_factoid.m_factoidtext = m_data2;

        //get relevance and dest.team - find LETTER        
        int characterIndex = getRelevanceCharIndex(m_data);

        //webELICIT has character in column 2 or 3
        m_isELICITv01 = isELICIT_v01(m_data);
        int characterTeamIndex;
        if (m_isELICITv01)
            characterTeamIndex =characterIndex-1;
        else
            characterTeamIndex =characterIndex+1;

        switch ( m_data.charAt(characterIndex) ) {
            case 'E':
                m_factoid.m_relevance = m_EChar;
                break;
            case 'K':
                m_factoid.m_relevance = m_KChar;
                break;
            case 'S':
                m_factoid.m_relevance = m_SChar;
                break;
            case 'M'://may be misinformation
                m_factoid.m_relevance = m_MChar;
                break;
            case 'A'://may be answer
                m_factoid.m_relevance = m_AChar;
                break;
            default:
                m_factoid.m_relevance = m_NChar;
                break;
        }
        switch ( m_data.charAt(characterTeamIndex) ) {
            case '1':
                m_factoid.m_team = m_teamWho;
                break;
            case '2':
                m_factoid.m_team = m_teamWhat;
                break;
            case '3':
                m_factoid.m_team = m_teamWhere;
                break;
            default:
                m_factoid.m_team = m_teamWhen;
                break;
        }
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \t"+m_factoid.m_factoidId
               +" \t"+m_factoid.m_relevance
               +" \t"+m_factoid.m_team
               +" \t"+m_factoid.m_factoidtext;
    }

    public static boolean isFactoid (String factoidMetadata) {
    	try {
    		//hack - if no exception, should be factoid
        	char metadata = getFactoidRelevanceChar(factoidMetadata);
        	return true;
    	}
    	catch (Exception ex) {
    	}
		return false;
    }
    
    public static char getFactoidRelevanceChar (String factoidMetadata) {
        switch ( factoidMetadata.charAt( getRelevanceCharIndex(factoidMetadata)) ) {
            case 'E':
                return m_EChar;
            case 'K':
                return m_KChar;
            case 'S':
                return m_SChar;
            case 'M'://may be misinformation
                return m_MChar;
            case 'A'://may be answer
                return m_AChar;
            default:
                return m_NChar;
        }
    }

    public static int getRelevanceCharIndex(String factoidMetadata) {

    	//get relevance and dest.team - find LETTER
        int characterIndex = 0;
        while ( Character.isDigit(factoidMetadata.charAt(characterIndex)) ) {
            characterIndex++;
        }
        return characterIndex;
    }
    public static boolean isELICIT_v01 (String factoidMetadata) {
        int i = getRelevanceCharIndex(factoidMetadata);
        //webELICIT has character in column 2 or 3
        if (i<4) {
            //is webELICIT
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isKE (String factoidMetadata) {
        char c = getFactoidRelevanceChar(factoidMetadata);
        if (c==Message.m_EChar || c==Message.m_KChar) {
            return true;
        }
        return false;
    }

    public static boolean isRelevant (String factoidMetadata) {
        char c = getFactoidRelevanceChar(factoidMetadata);
        if (c==Message.m_EChar || c==Message.m_KChar || c==Message.m_SChar) {
            return true;
        }
        return false;
    }

    public static boolean isMisinfo (String factoidMetadata) {
        char c = getFactoidRelevanceChar(factoidMetadata);
        if (c==Message.m_MChar) {
            return true;
        }
        return false;
    }

    public static boolean isAnswer (String factoidMetadata) {
        char c = getFactoidRelevanceChar(factoidMetadata);
        if (c==Message.m_AChar) {
            return true;
        }
        return false;
    }

    public static boolean isEqualTo (String factoidMetadata1, String factoidMetadata2) {
        boolean value = false;
        //is ELICITv01?
        if (isELICIT_v01(factoidMetadata2)) {
            //use 3 and 4 letters
            if ( factoidMetadata1.charAt(2)==factoidMetadata2.charAt(2)
                 && factoidMetadata1.charAt(3)==factoidMetadata2.charAt(3)
                 && factoidMetadata1.charAt(6)==factoidMetadata2.charAt(6) )
            {
                 value = true;
            }
        }
        else { //use 2 first letters
            if ( factoidMetadata1.charAt(0)==factoidMetadata2.charAt(0)
                 && factoidMetadata1.charAt(1)==factoidMetadata2.charAt(1) )
            {
                 value = true;
            }
        }
        return value;
    }

    public static int GetFactoidNumber(String factoidMetadata) {
        String numberStr = factoidMetadata.substring(0, getRelevanceCharIndex(factoidMetadata));
        return Integer.parseInt(numberStr);
    }

    public static boolean ContainsFactoidInList (String factoidMetadata, List<String> factoidsMetadataList) {
        for (String fact : factoidsMetadataList)
            if ( isEqualTo(factoidMetadata, fact) )
                return true;
        return false;
    }

}
