/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.awareness;

import metrics.awareness.AwarenessQualityMap.AwarenessQualityData;

import java.util.Vector;
import java.util.TreeMap;
import elicit.message.FactoidMessage;
import elicit.message.Message;

/**
 *
 * @author mmanso
 */
public class AwarenessQualityMap extends TreeMap<String, Vector<AwarenessQualityData>> {

    // matrix:
    //  - row=0 is K/E fact metadata
    //  - row=1 is S fact metadata
    //  - row=2 is N fact metadata
    public static double evalMatrix[][] = { {-5, -1,  3,  5,  5 },
                                            {-5, -1,  5,  5,  3 },
                                            { 5,  5, -1, -5, -5 } };
    public static int K_index = 0;
    public static int E_index = 0;
    public static int S_index = 1;
    public static int N_index = 2;

    public class AwarenessQualityData {
        public double time;
        public String factoidMetadata;
        //
        public int   evaluation;
        public double q     = 0.0;
        public double qComm = 0.0;

        public AwarenessQualityData(double t, String fMetadata, int eval) {
            time=t;this.factoidMetadata=fMetadata;evaluation=eval;
            if (eval==-1)
                q=0.0;
            else
                q=evalMatrix[getRelevanceIndex(FactoidMessage.getFactoidRelevanceChar(factoidMetadata))][eval-1];
        }
    }

    public static int getRelevanceIndex(char c) {
        switch(c) {
            case Message.m_KChar:
                return K_index;
            case Message.m_EChar:
                return E_index;
            case Message.m_SChar:
                return S_index;
            //case Message.m_NChar:
            default:
                return N_index;
        }
    }

    public void CalculateCommulativeAwarenessQ(String name) {
        double averageQ=0.0;
        //first element? 
        if ( get(name).size()==1 )
            get(name).elementAt(0).qComm = get(name).elementAt(0).q;
        //else calculate average of all
        else if (get(name).size()>1) {
        for ( AwarenessQualityData data : get(name) )
            averageQ += data.q;
        }
        averageQ = averageQ / (double)get(name).size();
        get(name).elementAt(get(name).size()-1).qComm = averageQ;
    }

    public void addAwarenessAction (String name, double t, String fMetadata, int eval) {
        //todo:  DISCARD if 'no evaluation'
        //if (eval!=-1) {
            AwarenessQualityData data = new AwarenessQualityData(t, fMetadata, eval);
            data.evaluation = eval;
            if (get(name)==null)
                put(name, new Vector<AwarenessQualityData>());
            get(name).add(data);
            CalculateCommulativeAwarenessQ(name);
        //}
    }

    public AwarenessQualityData getLastAwarenessData (String subj, String factoidMetadata, double time) {
        AwarenessQualityData data = null;
        Vector<AwarenessQualityData> v = get(subj);
        if (v!=null)
            for ( AwarenessQualityData d : v )
                if ( d.time>time )
                    break;
                else if (FactoidMessage.isEqualTo(factoidMetadata, d.factoidMetadata))
                        data = d;
        return data;
    }
    public AwarenessQualityData getLastAwarenessData (String subj, int factoidNbr, double time) {
        AwarenessQualityData data = null;
        Vector<AwarenessQualityData> v = get(subj);
        if (v!=null)
            for ( AwarenessQualityData d : v )
                if ( d.time>time )
                    break;
                else if (FactoidMessage.GetFactoidNumber(d.factoidMetadata)== factoidNbr)
                        data = d;
        return data;
    }

}
