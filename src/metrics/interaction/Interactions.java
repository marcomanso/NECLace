/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.interaction;

import java.util.TreeMap;

import elicit.message.*;
import elicit.message.TrialData.Subject;

/**
 *
 * @author mmanso
 */
public class Interactions {

    //
    public TreeMap<String, QualityOfInteractions> m_qualityOfInteractionShare = new TreeMap<String, QualityOfInteractions>();
    public TreeMap<String, QualityOfInteractions> m_qualityOfInteractionPost  = new TreeMap<String, QualityOfInteractions>();
    public TreeMap<String, QualityOfInteractions> m_qualityOfInteractionShareAndPost  = new TreeMap<String, QualityOfInteractions>();
    //
    public InteractionRates m_socialInterations  = new InteractionRates();
    //
    public InteractionRates m_reciprocityIndex  = new InteractionRates();
    //
    public HoardingMap m_hoarding  = new HoardingMap();

    public Interactions() {
        m_qualityOfInteractionPost.put(elicit.message.Message.m_OVERALL, new QualityOfInteractions());
        m_qualityOfInteractionShare.put(elicit.message.Message.m_OVERALL, new QualityOfInteractions());
        m_qualityOfInteractionShareAndPost.put(elicit.message.Message.m_OVERALL, new QualityOfInteractions());
    }

    //
    public void addSubject(Subject s) {
        if (m_qualityOfInteractionPost.get(s.m_personName)==null)
            m_qualityOfInteractionPost.put(s.m_personName, new QualityOfInteractions());
        if (m_qualityOfInteractionShare.get(s.m_personName)==null)
            m_qualityOfInteractionShare.put(s.m_personName, new QualityOfInteractions());
        if (m_qualityOfInteractionShareAndPost.get(s.m_personName)==null)
            m_qualityOfInteractionShareAndPost.put(s.m_personName, new QualityOfInteractions());
    }

    public void addTeam(String teamName) {
        if (m_qualityOfInteractionPost.get(teamName)==null)
            m_qualityOfInteractionPost.put(teamName, new QualityOfInteractions());
        if (m_qualityOfInteractionShare.get(teamName)==null)
            m_qualityOfInteractionShare.put(teamName, new QualityOfInteractions());
        if (m_qualityOfInteractionShareAndPost.get(teamName)==null)
            m_qualityOfInteractionShareAndPost.put(teamName, new QualityOfInteractions());
        //
    }

    public void addPull(Subject s, double time, String siteName) {
        m_socialInterations.add(siteName, s.m_personName, time, 1, 0);
    }

    public void addPost (Subject s, double time, String factoidMetadata, String siteName) {
        int KER=0;
        int N=0;
        if ( FactoidMessage.isKE(factoidMetadata) || FactoidMessage.isRelevant(factoidMetadata) )
            KER=1;
        else
            N=1;
        m_qualityOfInteractionPost.get(s.m_personName).add(time, KER, N);
        m_qualityOfInteractionPost.get(s.m_teamName).add(time, KER, N);
        m_qualityOfInteractionPost.get(elicit.message.Message.m_OVERALL).add(time, KER, N);
        //
        m_qualityOfInteractionShareAndPost.get(s.m_personName).add(time, KER, N);
        m_qualityOfInteractionShareAndPost.get(s.m_teamName).add(time, KER, N);
        m_qualityOfInteractionShareAndPost.get(elicit.message.Message.m_OVERALL).add(time, KER, N);
        //
        m_socialInterations.add(s.m_personName, siteName, time, 1, 0);
    }

    public void addShare (Subject s, Subject dest, double time, String factoidMetadata) {
        int KER=0;
        int N=0;
        if ( FactoidMessage.isKE(factoidMetadata) || FactoidMessage.isRelevant(factoidMetadata) )
            KER=1;
        else
            N=1;
        m_qualityOfInteractionShare.get(s.m_personName).add(time, KER, N);
        m_qualityOfInteractionShare.get(s.m_teamName).add(time, KER, N);
        m_qualityOfInteractionShare.get(elicit.message.Message.m_OVERALL).add(time, KER, N);
        //
        m_qualityOfInteractionShareAndPost.get(s.m_personName).add(time, KER, N);
        m_qualityOfInteractionShareAndPost.get(s.m_teamName).add(time, KER, N);
        m_qualityOfInteractionShareAndPost.get(elicit.message.Message.m_OVERALL).add(time, KER, N);
        //
        m_reciprocityIndex.add(s.m_personName, dest.m_personName, time, 1, 0);
        m_reciprocityIndex.add(dest.m_personName, s.m_personName, time, 0, 1);
        //
        m_socialInterations.add(s.m_personName, dest.m_personName, time, 1, 0);
    }
    public void endTime (double endTime) {
        for ( String s : m_qualityOfInteractionShare.keySet() ) {
            m_qualityOfInteractionShare.get(s).endTime(endTime);
        }
        for ( String s : m_qualityOfInteractionPost.keySet() ) {
            m_qualityOfInteractionPost.get(s).endTime(endTime);
        }
        for ( String s : m_qualityOfInteractionPost.keySet() ) {
            m_qualityOfInteractionShareAndPost.get(s).endTime(endTime);
        }
    }
    public static double getQValueAtTime (QualityOfInteractions qVector, double time) {
        double ratio = 0.0;
        for (QualityOfInteractions.QualityOfInteraction elem : qVector) {
            if (elem.time > time )
                break;
            ratio = elem.commulativeValue;
        }
        return ratio;
    }

}
