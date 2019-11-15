/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.interaction;

import java.util.Vector;
import metrics.interaction.QualityOfInteractions.QualityOfInteraction;

/**
 *
 * @author mmanso
 */
public class QualityOfInteractions extends Vector<QualityOfInteraction> {

    public class QualityOfInteraction {
        public double time;
        public int totalInteractions;
        public int totalKER;
        public int totalN;
        //public double commulativeRatio; // R = (totalKR-totalN) / totalN
        public double commulativeValue; // R = totalKR - totalN

        public QualityOfInteraction() {
            time=0.0;totalInteractions=0;totalKER=0;totalN=0;commulativeValue=0.0;//commulativeRatio=0.0;
        }
    }

    public void add (double t, int ker, int n) {
        QualityOfInteraction intQ = new QualityOfInteraction();
        QualityOfInteraction prevQ;
        if (size()!=0) {
            prevQ = this.elementAt(size()-1);
            intQ.totalInteractions = prevQ.totalInteractions;
            intQ.totalKER = prevQ.totalKER;
            intQ.totalN = prevQ.totalN;
        }
        intQ.time = t;
        intQ.totalInteractions++;
        intQ.totalKER+=ker;
        intQ.totalN+=n;
        //intQ.commulativeRatio = (intQ.totalKER - intQ.totalN)/(double)intQ.totalInteractions;
        intQ.commulativeValue = (intQ.totalKER - intQ.totalN);

        //System.out.println("Q: "+ intQ.commulativeRatio );
        this.add(intQ);
    }
    public void endTime (double t) {
        QualityOfInteraction intQ = new QualityOfInteraction();
        QualityOfInteraction prevQ;
        if (size()!=0) {
            prevQ = this.elementAt(size()-1);
            intQ.totalInteractions = prevQ.totalInteractions;
            intQ.totalKER = prevQ.totalKER;
            intQ.totalN = prevQ.totalN;
            //intQ.commulativeRatio = prevQ.commulativeRatio;
            intQ.commulativeValue = prevQ.commulativeValue;
        }
        intQ.time = t;
        this.add(intQ);
    }
    public QualityOfInteraction getValueAt(int time) {
        QualityOfInteraction q = null;
        for ( QualityOfInteraction it : this ) {
            if (it.time <= time)
                q = it;
            else
                break;
        }
        return q;
    }

}
