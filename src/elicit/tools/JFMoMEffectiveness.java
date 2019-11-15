/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import java.util.Vector;

import elicit.message.Message;
import elicit.message.TrialData;
import elicit.message.TrialData.Subject;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.TreeMap;
import metrics.awareness.IDsQualityMap.AwUndData;
import metrics.informationquality.InformationQuality;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class JFMoMEffectiveness extends JFMoMCorrectSharedUnderstanding {

    NumberFormat m_formatter = null ;
    //parse through IDs - time interval of 5
    static int T_STEPS = 10;

    public JFMoMEffectiveness(TrialData trialData) {
        super(trialData);
        //jlTime.setVisible(false);
        //jlTimeSec.setVisible(false);
        //jsTime.setVisible(false);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: Effectiveness Scores");
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //

        //somehow: I tried to initialize m_formatter in constructor but didn't work
        //the next line is a workaround
        if (m_formatter==null) m_formatter = new DecimalFormat ( "0.00" ) ;

        //
        int t=0;
        //for (int t=0 ; t < m_trialData.m_trialInformation.m_durationSec ; ) {
        while ( t < m_trialData.m_trialInformation.m_durationSec ) {
            TreeMap<Subject, AwUndData> IDmap = new TreeMap<Subject, AwUndData>();
            for ( Subject s : m_trialData.m_organizationInformation.m_memberList ) {
                AwUndData data = m_trialData.m_subjectsIDsQualityMap.GetLastSubjectIDAtTime(s.m_personName, t);
                if (data != null) {
                    IDmap.put(s, data);
                    //System.out.println("subject: "+s.m_personName+"ID: "+ data.time+" who: "+data.who);
                }
            }
            //
            double effectiveness_score = CalculateEffectivessMetrics.getEffectivenessScore(m_trialData.m_organizationInformation.m_organizationName, m_trialData.m_solution, IDmap);

            FillData(m_overall, new Point2D.Double(t,effectiveness_score) );
            //FillData(m_overall, point2D);
            //
            //System.out.println("t: "+t +" effect="+effectiveness_score);
            t=t+T_STEPS;
            //t=t+5;
        }

    }

}
