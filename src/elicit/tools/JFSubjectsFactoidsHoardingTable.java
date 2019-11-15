/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import metrics.interaction.HoardingMap.HoardingInformation;

/**
 *
 * @author mmanso
 */
public class JFSubjectsFactoidsHoardingTable extends JFSubjectsFactoidsTable {

    public JFSubjectsFactoidsHoardingTable(TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Factoids Hoarding");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringFactoidHoarded());
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each subject
        int sCount=0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
        {
            for ( int i=1; i<=m_trialData.m_factoidStats.totalFactoids ; i++ )
            {
                jtFactoids.setValueAt(' ', sCount,  i);
                HoardingInformation hHoarded = m_trialData.m_interactions.m_hoarding.getHoardingInformation(s.m_personName, i);
                HoardingInformation hShared = m_trialData.m_interactions.m_hoarding.getSharedFactoid(s.m_personName, i);
                if (hShared!=null && hShared.m_time<=time) {
                    jtFactoids.setValueAt(HoardingInformation.FACTOID_SHARED, sCount,  i);
                }
                else if (hHoarded!=null && hHoarded.m_time<=time) {
                    jtFactoids.setValueAt(HoardingInformation.FACTOID_HOARDED, sCount,  i);
                }
            }
            sCount++; //next subject
        }
    }

}
