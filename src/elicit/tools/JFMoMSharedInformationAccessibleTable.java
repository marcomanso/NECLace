/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import metrics.informationquality.InformationQuality;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class JFMoMSharedInformationAccessibleTable extends JFSubjectsFactoidsTable {

    public static final String m_overall = "OVERALL";
    //public static final double m_percentagesSteps[]      = {0.10, 0.20, 0.30, 0.50, 0.65, 0.80, 1.00};
    public static final double m_percentagesSteps[] = {0.10, 0.20, 0.30, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 1.00};
    //
    
    public JFMoMSharedInformationAccessibleTable(TrialData trialData) {
        super(trialData);
        jlTime.setVisible(false);
        jlTimeSec.setVisible(false);
        jsTime.setVisible(false);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: Information Accessible (percentage over time)");
    }

    @Override
    public void SetTableModel () {
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoring());
        jtFactoids.setDefaultRenderer(String.class, new javax.swing.table.DefaultTableCellRenderer());
    }

    @Override
    public void CreateFactoidTable() {
        int nbrRows = m_trialData.m_organizationInformation.m_memberList.size()+m_trialData.m_organizationInformation.m_teamList.size()+1;
        int nbrCols = m_percentagesSteps.length+1;

        Object[][] obj = new Object[nbrRows][nbrCols];
        String[] header = new String[m_percentagesSteps.length+1];
        header[0] = "Subjs/Teams";
        for ( int i=1; i<header.length; i++ ) {
            header[i]=Double.toString(m_percentagesSteps[i-1]);
        }
        //
        int count = 0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            obj[count][0] = s.m_personName;
            count++;
        }
        for (String team : m_trialData.m_organizationInformation.m_teamList) {
            obj[count][0] = team.toUpperCase();
            count++;
        }
        obj[count][0] = m_overall;

        jtFactoids.setModel(
            new javax.swing.table.DefaultTableModel(obj,header) {

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

    public int getColIndexInTable (double value) {
        int col=-1;
        for ( int i=0; i<m_percentagesSteps.length; i++ )
            if (value>=m_percentagesSteps[i])
                col=i+1;
        return col;
    }

    public void FillData (String name, InformationAccessible.AccessibilityIndexVector v) {
        int row = getRowIndexInTable(name);
        if (v!=null)
            for (InformationAccessibleData data : v) {
                double value = (double)data.indexAll / (double)m_trialData.m_factoidStats.totalFactoids;
                int col = getColIndexInTable(value);
                if (col!=-1 && jtFactoids.getValueAt(row, col)==null) {
                    jtFactoids.setValueAt((int)data.time, row, col);
                }
            }
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each subject
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
            FillData(s.m_personName, InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_accessibilityIndex));
        //teams
        for (String team : m_trialData.m_organizationInformation.m_teamList)
            FillData(team, InformationQuality.GetInformation2ByTeam(team, m_trialData.m_informationQuality.m_accessibilityIndex));
        //overall
        FillData(m_overall, m_trialData.m_informationQuality.m_accessibilityIndex.m_set2AccessIndexOverall);
    }

}
