/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import java.text.NumberFormat;
import java.text.DecimalFormat;

import elicit.message.TrialData;
import elicit.message.TrialData.Subject;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author mmanso
 */
public class JFSubjectsNatureInteractionsTable extends JFSubjectsFactoidsTable {

    public static String m_header[] = {"Subjects",
                                        "Shares (snt)",
                                        "Shares (rcv)",
                                        "Posts",
                                        "Pulls",
                                        "IDs",
                                        "ADDs",
                                        "Shares(snt)/Hour",
                                        "Shares(rcv)/Hour",
                                        "Posts/Hour",
                                        "Pulls/Hour",
                                        "IDs/Hour",
                                        "ADDs/Hour" };

    NumberFormat m_formatter = null ;

    public JFSubjectsNatureInteractionsTable(TrialData trialData) {
        super(trialData);
        //
    }

    @Override
    public void SetTitle()
    {
        super.SetTitle();
        this.setTitle("Nature of Interactions (Table)");
        //
        jPanelBOTTOM.setVisible(false);
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
    }
    
    @Override
    public void CreateFactoidTable() {
        //
        int nbrRows = m_trialData.m_organizationInformation.m_memberList.size();

        Object[][] obj = new Object[nbrRows][m_header.length];

        int count=0;
        for (Subject s : m_trialData.m_organizationInformation.m_memberList) {
            obj[count][0] = s.m_personName;
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

        //somehow: I tried to initialize m_formatter in constructor but didn't work
        //the next line is a workaround
        if (m_formatter==null) m_formatter = new DecimalFormat ( "0.0" ) ;

        //subject
        for ( Subject s : m_trialData.m_organizationInformation.m_memberList ) {
            metrics.interaction.OverallInteractions.OverallStatistics overallSubj = m_trialData.m_overallInteractions.get(s.m_personName);
            int row = getRowIndexInTable(s.m_personName);
            jtFactoids.getModel().setValueAt(overallSubj.totalShares, row, getColIndexInTable(m_header[1]));
            jtFactoids.getModel().setValueAt(overallSubj.totalSharesRcv, row, getColIndexInTable(m_header[2]));
            jtFactoids.getModel().setValueAt(overallSubj.totalPosts, row, getColIndexInTable(m_header[3]));
            jtFactoids.getModel().setValueAt(overallSubj.totalPulls, row, getColIndexInTable(m_header[4]));
            jtFactoids.getModel().setValueAt(overallSubj.totalIDs, row, getColIndexInTable(m_header[5]));
            jtFactoids.getModel().setValueAt(overallSubj.totalADDs, row, getColIndexInTable(m_header[6]));
            //
            jtFactoids.getModel().setValueAt(m_formatter.format(overallSubj.sharesHour), row, getColIndexInTable(m_header[7]));
            jtFactoids.getModel().setValueAt(m_formatter.format(overallSubj.sharesRcvHour), row, getColIndexInTable(m_header[8]));
            jtFactoids.getModel().setValueAt(m_formatter.format(overallSubj.postsHour), row, getColIndexInTable(m_header[9]));
            jtFactoids.getModel().setValueAt(m_formatter.format(overallSubj.pullsHour), row, getColIndexInTable(m_header[10]));
            jtFactoids.getModel().setValueAt(m_formatter.format(overallSubj.iDsHour), row, getColIndexInTable(m_header[11]));
            jtFactoids.getModel().setValueAt(m_formatter.format(overallSubj.aDDsHour), row, getColIndexInTable(m_header[12]));
        }
        //
        
    }

}
