/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import metrics.awareness.AwarenessQualityMap;
import metrics.awareness.AwarenessQualityMap.AwarenessQualityData;

/**
 *
 * @author mmanso
 */
public class JFSubjectsFactoidsAwarenessQTable extends JFSubjectsFactoidsTable {

    public JFSubjectsFactoidsAwarenessQTable(TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Quality of Awareness");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringQuantityOfFactoids());
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each subject
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
        {
            for ( int i=1; i<=m_trialData.m_factoidStats.totalFactoids ; i++ )
            {
                int row = getRowIndexInTable(s.m_personName);
                AwarenessQualityData data = m_trialData.m_awarenessQualityMap.getLastAwarenessData(s.m_personName, i, time);
                if (data==null)
                    jtFactoids.setValueAt(' ', row,  i);
                else
                    jtFactoids.setValueAt(data.q, row,  i);
            }
        }
    }

}
