/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import java.util.Vector;

import elicit.message.Message;
import elicit.message.TrialData;
import java.awt.geom.Point2D;
import metrics.awareness.IDsQualityMap.AwUndData;
import metrics.informationquality.InformationQuality;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class JFMoMCSSync extends JFMoMCorrectSharedUnderstanding {

    public JFMoMCSSync(TrialData trialData) {
        super(trialData);
        //jlTime.setVisible(false);
        //jlTimeSec.setVisible(false);
        //jsTime.setVisible(false);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: Cognitive Self-Synchronization Scores");
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //1. per sol space
        for (Point2D.Double point2D : (Vector<Point2D.Double>)m_trialData.m_subjectsIDsQualityMap.m_entropy[0])
            FillData(m_solutionSpace[0], new Point2D.Double(point2D.x , point2D.y) );
        for (Point2D.Double point2D : (Vector<Point2D.Double>)m_trialData.m_subjectsIDsQualityMap.m_entropy[1])
            FillData(m_solutionSpace[1], new Point2D.Double(point2D.x , point2D.y) );
        for (Point2D.Double point2D : (Vector<Point2D.Double>)m_trialData.m_subjectsIDsQualityMap.m_entropy[2])
            FillData(m_solutionSpace[2], new Point2D.Double(point2D.x , point2D.y) );
//        for (Point2D.Double point2D : (Vector<Point2D.Double>)m_trialData.m_subjectsIDsQualityMap.m_entropy[3])
//            FillData("WHEN (time)", new Point2D.Double(point2D.x , point2D.y) );
//        for (Point2D.Double point2D : (Vector<Point2D.Double>)m_trialData.m_subjectsIDsQualityMap.m_entropy[4])
//            FillData("WHEN (day)", new Point2D.Double(point2D.x , point2D.y) );
//        for (Point2D.Double point2D : (Vector<Point2D.Double>)m_trialData.m_subjectsIDsQualityMap.m_entropy[5])
//            FillData("WHEN (month)", new Point2D.Double(point2D.x , point2D.y) );
        //2. overall
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_entropyOverall)
            FillData(m_overall, new Point2D.Double(point2D.x , point2D.y) );
            //FillData(m_overall, point2D);
    }

}
