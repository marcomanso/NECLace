/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import java.awt.geom.Point2D;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class JFMoMAverageInformationAccessedTable extends JFMoMCorrectSharedUnderstanding {

    public JFMoMAverageInformationAccessedTable(TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: Average Relevant Information Reached (percentage over time)");
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //pool over time - get info.accessed
        int TIME_STEPS = 1;
        int t=0;
        while ( t < m_trialData.m_trialInformation.m_durationSec ) {
            double value = 0.0;
            for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
                InformationAccessibleData data = m_trialData.m_informationQuality.m_reachedIndex.m_informationAccessibleBySubjects.get(s.m_personName).getLastElementBeforeTime(t);
                if (data!=null)
                    value += ((double)data.indexRelevant)/((double)(m_trialData.m_factoidStats.totalKey_Factoids+m_trialData.m_factoidStats.totalExpertise_Factoids+m_trialData.m_factoidStats.totalSupportive_Factoids));
            }
            value=value/((double)m_trialData.m_organizationInformation.m_memberList.size());
            FillData(m_overall, new Point2D.Double(t,value) );
            //
            t+=TIME_STEPS;
        }
    }

}
