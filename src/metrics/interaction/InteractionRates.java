/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.interaction;

import java.util.Vector;
import java.util.TreeMap;
import metrics.interaction.InteractionRates.ReciprocityRate;

/**
 *
 * @author mmanso
 */
public class InteractionRates extends TreeMap<String, TreeMap<String, Vector<ReciprocityRate>> > {

    public class ReciprocityRate {
        public double time;
        public int totalInteractions;
        public int totalSent;
        public int totalRcv;
        public double ratio; // R = sent / rcv (if rcv = 0)
        public String sent_rcv_txt; // "S / R"

        ReciprocityRate() {
            time=0.0;totalInteractions=0;totalSent=0;totalRcv=0;ratio=0.0;sent_rcv_txt="0/0";
        }
        @Override
        public String toString() {
            return totalSent + " | " + totalRcv;
        }
    }

    public void add (String source, String dest, double t, int shared, int received) {
        if (get(source)==null)
            put(source, new TreeMap<String, Vector<ReciprocityRate>>());
        if (get(source).get(dest)==null)
            get(source).put(dest, new Vector<ReciprocityRate>());

        ReciprocityRate rr = new ReciprocityRate();
        ReciprocityRate prevRR;
        if (get(source).get(dest).size()!=0) {
            prevRR = get(source).get(dest).elementAt(get(source).get(dest).size()-1);
            rr.totalInteractions = prevRR.totalInteractions;
            rr.totalSent = prevRR.totalSent;
            rr.totalRcv = prevRR.totalRcv;
            rr.totalInteractions = prevRR.totalInteractions;
        }
        rr.time = t;
        rr.totalInteractions+= shared + received;
        rr.totalSent += shared;
        rr.totalRcv  += received;
        if (rr.totalRcv!=0)
            rr.ratio =  rr.totalSent/rr.totalRcv;
        rr.sent_rcv_txt = rr.totalSent + " | " + rr.totalRcv;
        //System.out.println("Q: "+ rr.commulativeRatio );

        get(source).get(dest).add(rr);
    }

    public ReciprocityRate getReciprocityAtTime (String source, String dest, double time) {
        ReciprocityRate rr = new ReciprocityRate();
        if (get(source)!=null && get(source).get(dest)!=null)
            for ( ReciprocityRate rrParse : get(source).get(dest) ) {
                if (rrParse.time <= time) {
                    rr = rrParse;
                }
            }
        return rr;
    }

    public ReciprocityRate getLastReciprocityElement (String source, String dest) {
        ReciprocityRate rr = new ReciprocityRate();
        if (get(source)!=null && get(source).get(dest)!=null && get(source).get(dest).lastElement()!=null)
            rr = get(source).get(dest).lastElement();
        return rr;
    }

    public void endTime (double t) {
        for (String source : this.keySet()) {
            for (String dest : get(source).keySet()) {
                add(source, dest, t, 0, 0);
            }
        }

    }    

}
