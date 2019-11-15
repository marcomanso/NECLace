/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.interaction;

import elicit.message.FactoidMessage;
import metrics.interaction.HoardingMap.HoardingInformation;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Enumeration;

/**
 *
 * @author mmanso
 */
public class HoardingMap extends TreeMap<String, Vector<HoardingInformation>> {
    public class HoardingInformation {
        public final static char FACTOID_HOARDED = 'H';
        public final static char FACTOID_SHARED = 'S';

        public String m_factoidMetadata;
        public int   m_hoarded;
        public double m_time;
        public HoardingInformation(String factoidMetadata, int hoardedInfo, double time) {
            m_factoidMetadata = factoidMetadata;
            m_hoarded = hoardedInfo;
            m_time = time;
        }
    }

    public HoardingInformation getSharedFactoid (String subjectName, String factoidMetadata) {
    	
    	if (get(subjectName) == null)
    		return null;
    	
        for (Enumeration<HoardingInformation> e = get(subjectName).elements(); e.hasMoreElements(); ) {
            HoardingInformation h = (HoardingInformation)e.nextElement();
            if (FactoidMessage.isEqualTo(h.m_factoidMetadata, factoidMetadata) && h.m_hoarded == HoardingInformation.FACTOID_SHARED) {
                return h;
            }
        }
        return null;
    }

    public HoardingInformation getSharedFactoid (String subjectName, int factoidID) {
    	if (get(subjectName) == null)
    		return null;
    	
        for (Enumeration e = get(subjectName).elements(); e.hasMoreElements(); ) {
            HoardingInformation h = (HoardingInformation)e.nextElement();
            if (FactoidMessage.GetFactoidNumber(h.m_factoidMetadata)==factoidID && h.m_hoarded == HoardingInformation.FACTOID_SHARED) {
                return h;
            }
        }
        return null;
    }

    public HoardingInformation getHoardingInformation (String subjectName, String factoidMetadata) {
    	if (get(subjectName) == null)
    		return null;
    	
        for (Enumeration e = get(subjectName).elements(); e.hasMoreElements(); ) {
            HoardingInformation h = (HoardingInformation)e.nextElement();
            if (FactoidMessage.isEqualTo(h.m_factoidMetadata, factoidMetadata)) {
                return h;
            }
        }
        return null;
    }

    public HoardingInformation getHoardingInformation (String subjectName, int factoidID) {
    	if (get(subjectName) == null)
    		return null;
    	
        for (Enumeration e = get(subjectName).elements(); e.hasMoreElements(); ) {
            HoardingInformation h = (HoardingInformation)e.nextElement();
            if (FactoidMessage.GetFactoidNumber(h.m_factoidMetadata) == factoidID) {
                return h;
            }
        }
        return null;
    }

    public void addReceived (String subjectName, String factoidMetadata, double time) {
        if (get(subjectName)==null)
            put(subjectName, new Vector<HoardingInformation>());

        //already has factoid? if not insert it
        if (getHoardingInformation(subjectName, factoidMetadata)==null) {
            get(subjectName).add(new HoardingInformation(factoidMetadata,HoardingInformation.FACTOID_HOARDED, time));
        }
    }
    public void addShared (String subjectName, String factoidMetadata, double time) {
    	if (get(subjectName) == null)
    		return;

    	//already shared? ignore action
        HoardingInformation h = getSharedFactoid(subjectName, factoidMetadata);
        if (getSharedFactoid(subjectName, factoidMetadata)==null)
            get(subjectName).add(new HoardingInformation(factoidMetadata,HoardingInformation.FACTOID_SHARED, time));
    }
}
