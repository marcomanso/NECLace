/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import elicit.message.Message;

/**
 *
 * @author mmanso
 */
public class JFSystemUnderstandingEntropy extends JFSubjectsFactoidsTable {

    public static String m_header[] = {"Name",
                                        "Value"};
    
    public static String m_rows[] = {Message.m_teamWho,
                                     Message.m_teamWhat,
                                     Message.m_teamWhere,
                                     Message.m_teamWhen+"(t)",
                                     Message.m_teamWhat+"(d)",
                                     Message.m_teamWhen+"(m)",
                                     Message.m_OVERALL};

    public JFSystemUnderstandingEntropy(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Cognitive Self-Synchronization (Solution)");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringID(m_trialData.m_solution));
    }

    @Override
    public void CreateFactoidTable() {
        //
        Object[][] obj = new Object[m_rows.length][m_header.length];
        for (int i=0; i<m_rows.length; i++)
            obj[i][0]=m_rows[i];

        jtFactoids.setModel(
            new javax.swing.table.DefaultTableModel(obj,m_header) {

            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
                //return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
                //return canEdit[columnIndex];
            }
        });//end set model

        jspFactoidsTable.setViewportView(jtFactoids);
    }//end CreateFactoidTable

    @Override
    public void FillTableInformationAtTime (int time) {
        //
        int row=0;
        int col=1;
        for (int i=0; i<6; i++) {
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[i].size()!=0) {
                java.awt.geom.Point2D.Double value = (java.awt.geom.Point2D.Double)m_trialData.m_subjectsIDsQualityMap.m_entropy[i].lastElement();
                jtFactoids.getModel().setValueAt(value.y, row, col);
            }
            else
                jtFactoids.getModel().setValueAt("-", row, col);
            row++;
        }
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);

        /*
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyWho.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyWho.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        row++;
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhat.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyWhat.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        row++;
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhere.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyWhere.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        row++;
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhenTime.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyWhenTime.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        row++;
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhenDay.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyWhenDay.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        row++;
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhenMonth.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyWhenMonth.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        row++;
        if (m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.size()!=0)
            jtFactoids.getModel().setValueAt(m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.lastElement().y, row, col);
        else
            jtFactoids.getModel().setValueAt("-", row, col);
        */
    }

}
