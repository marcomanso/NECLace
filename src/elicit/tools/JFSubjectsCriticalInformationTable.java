/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;

/**
 *
 * @author mmanso
 */
public class JFSubjectsCriticalInformationTable extends JFSubjectsFactoidsTable {

    public JFSubjectsCriticalInformationTable(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Critical Information Accessible");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringCriticalInformation());
    }

    @Override
    public void CreateFactoidTable() {

        m_trialData.m_informationQuality.m_criticalInformationAccessible.GetStatsAtTime(0, m_trialData.m_informationQuality.m_accessibilityIndex);

        int nbrRows = m_trialData.m_informationQuality.m_criticalInformationAccessible.m_subjectsCriticalInformationMap.size()
                      + m_trialData.m_informationQuality.m_criticalInformationAccessible.m_teamsCriticalInformationMap.size();
        int nbrCols = m_trialData.m_informationQuality.m_criticalInformationAccessible.m_solutionFactoids.size()+1;

        Object[][] obj = new Object[nbrRows][nbrCols];
        int count = 0;
        for (String s : m_trialData.m_informationQuality.m_criticalInformationAccessible.m_subjectsCriticalInformationMap.keySet()) {
            obj[count][0] = s;
            count++;
        }
        for (String team : m_trialData.m_informationQuality.m_criticalInformationAccessible.m_teamsCriticalInformationMap.keySet()) {
            obj[count][0] = team.toUpperCase();
            count++;
        }

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
        m_trialData.m_informationQuality.m_criticalInformationAccessible.GetStatsAtTime(time, m_trialData.m_informationQuality.m_accessibilityIndex);
        for ( String s : m_trialData.m_informationQuality.m_criticalInformationAccessible.m_subjectsCriticalInformationMap.keySet() ) {
            for (String solSpace : m_trialData.m_informationQuality.m_criticalInformationAccessible.m_subjectsCriticalInformationMap.get(s).keySet() ) {
                int row=getRowIndexInTable(s);
                int col=getColIndexInTable(solSpace);
                jtFactoids.getModel().setValueAt(m_trialData.m_informationQuality.m_criticalInformationAccessible.m_subjectsCriticalInformationMap.get(s).get(solSpace), row, col);
            }
        }
        for ( String team : m_trialData.m_informationQuality.m_criticalInformationAccessible.m_teamsCriticalInformationMap.keySet() ) {
            for (String solSpace : m_trialData.m_informationQuality.m_criticalInformationAccessible.m_teamsCriticalInformationMap.get(team).keySet() ) {
                int row=getRowIndexInTable(team);
                int col=getColIndexInTable(solSpace);
                jtFactoids.getModel().setValueAt(m_trialData.m_informationQuality.m_criticalInformationAccessible.m_teamsCriticalInformationMap.get(team).get(solSpace), row, col);
            }
        }
    }

}
