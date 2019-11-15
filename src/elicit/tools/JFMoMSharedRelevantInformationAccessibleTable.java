/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class JFMoMSharedRelevantInformationAccessibleTable extends JFMoMSharedInformationAccessibleTable {

    public JFMoMSharedRelevantInformationAccessibleTable(TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: RELEVANT Information Accessible (percentage over time)");
    }

    @Override
    public void FillData (String name, InformationAccessible.AccessibilityIndexVector v) {
        int row = getRowIndexInTable(name);
        if (v!=null)
            for (InformationAccessibleData data : v) {
                double value = (double)(data.indexRelevant) / (double)(m_trialData.m_factoidStats.totalExpertise_Factoids+m_trialData.m_factoidStats.totalKey_Factoids+m_trialData.m_factoidStats.totalSupportive_Factoids);
                int col = getColIndexInTable(value);
                if (col!=-1 && jtFactoids.getValueAt(row, col)==null) {
                    jtFactoids.setValueAt((int)data.time, row, col);
                }
            }
    }

}
