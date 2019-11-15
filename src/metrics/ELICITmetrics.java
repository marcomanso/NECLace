/*
 * ELICIRmetrics.java
 *
 * Created on 16 August 2007, 23:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package metrics;

import java.io.*;
import java.util.*;

/**
 *
 * @author Marco
 */
public class ELICITmetrics {

    //SOME ELICIT METRICS and DATA
    //create relation map (map of maps)
    public HashMap<String, HashMap> m_socialRelationMatrix; //map of maps
    //create relation map with SITES -post and pull (map of maps)
    public HashMap<String, HashMap> m_sitesRelationMatrix; //map of maps
    
    //networking data (share, post and pull)
    public HashMap<String, Integer> m_personShareActivity; //map of ints
    public HashMap<String, Integer> m_personShareRcvActivity; //map of ints
    public HashMap<String, Integer> m_personPostActivity;  //map of ints
    public HashMap<String, Integer> m_personPullActivity;  //map of ints
    //
    public double m_trialTimeHour = 0;
    
    //IDs
    public HashMap<String, Integer> m_personIdentifies;  //map of ints
    public HashMap<String, Double> m_personIDQuality; //map of doubles
    
    //Info Quality
    public int m_infoTxRelevant = 0;
    public HashMap<String, Double> m_personInfoQTx; //map of doubles
    public HashMap<String, Double> m_personInfoQRx; //map of doubles

    /** Creates a new instance of ELICIRmetrics */
    public ELICITmetrics() {
        m_socialRelationMatrix     = new HashMap<String, HashMap>(); //map of maps
        m_sitesRelationMatrix      = new HashMap<String, HashMap>(); //map of maps
        m_personShareActivity      = new HashMap<String, Integer>();
        m_personShareRcvActivity   = new HashMap<String, Integer>();
        m_personPostActivity       = new HashMap<String, Integer>();
        m_personPullActivity       = new HashMap<String, Integer>();
        m_personIdentifies         = new HashMap<String, Integer>();
        m_personIDQuality          = new HashMap<String, Double>();
        m_personInfoQTx            = new HashMap<String, Double>();
    }
    
    public int getNbrIdentifies() {
        int nbr = 0;
        for (Iterator it = m_personIdentifies.values().iterator(); it.hasNext(); ) {
            nbr += (Integer)it.next();
        }
        return nbr;
    }
    
    public int getNbrPosts() {
        int nbr = 0;
        for (Iterator it = m_personPostActivity.values().iterator(); it.hasNext(); ) {
            nbr += (Integer)it.next();
        }
        return nbr;
    }
    
    public int getNbrShares() {
        int nbr = 0;
        for (Iterator it = m_personShareActivity.values().iterator(); it.hasNext(); ) {
            nbr += (Integer)it.next();
        }
        return nbr;
    }
    
    public double getIDOverallQuality() {
        //shall be the average value of all IDs Q
        double idQ = 0.0;
        //how many players?
        int nbr = m_personIDQuality.size();
        for (Iterator it = m_personIDQuality.values().iterator(); it.hasNext(); ) {
            idQ += (Double)it.next();
        }
        return (double)idQ;
    }    
    
    public double getInfoTxOverallQuality() {
        //shall be the average value of all IDs Q
        double idQ = 0.0;
        //how many players?
        int nbr = m_personInfoQTx.size();
        for (Iterator it = m_personInfoQTx.values().iterator(); it.hasNext(); ) {
            idQ += (Double)it.next();
        }
        return idQ;
        //return idQ/nbr;
    }    
    
}
