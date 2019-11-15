/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.*;
import metrics.interaction.InteractionRates.ReciprocityRate;

/**
 *
 * @author mmanso
 */
public class JFSubjectsReciprocityRateTable extends JFSubjectsSocialNetworkTable {

        /** Creates new form JFSubjectsFactoidsTable */
    public JFSubjectsReciprocityRateTable(TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Social Network: Shares Reciprocity (outbounds | inbounds)");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringSubjectsReciprocity());
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //reset table
        for (int r=0; r<jtFactoids.getModel().getRowCount(); r++)
            for (int c=1; c<jtFactoids.getModel().getColumnCount(); c++)
                jtFactoids.getModel().setValueAt("", r, c);
        for (String source : m_trialData.m_interactions.m_reciprocityIndex.keySet()) {
            for (String dest : m_trialData.m_interactions.m_reciprocityIndex.get(source).keySet()) {
                ReciprocityRate rr = null;
                for (ReciprocityRate rrTemp : m_trialData.m_interactions.m_reciprocityIndex.get(source).get(dest))
                    if (rrTemp.time <= time)
                        rr = rrTemp;
                    else
                        break;
                if (rr!=null)
                    jtFactoids.getModel().setValueAt(rr.toString(),getRowIndexInTable(source),getColIndexInTable(dest));
            }
        }//end for all sources
    }

}
