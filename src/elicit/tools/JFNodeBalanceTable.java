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
public class JFNodeBalanceTable extends JFSubjectsFactoidsTable {

    public static String m_header[] = {"Subjects",
                                        "IN dev from AVG (shares rcv and pulls)",
                                        "OUT dev from AVG (shares snt and posts)",
                                        "ABS dev from AVG" };

    NumberFormat m_formatter = null ;

    public JFNodeBalanceTable(TrialData trialData) {
        super(trialData);
        //
    }

    @Override
    public void SetTitle()
    {
        super.SetTitle();
        this.setTitle("Node IN and OUT Balance (Table)");
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

        //GET average shares_SEND + posts per subject
        double avgOUT = (m_trialData.m_overallStatistics.totalShares+m_trialData.m_overallStatistics.totalPosts)/(double)m_trialData.m_organizationInformation.m_memberList.size();
        //GET average shares_RCV + pulls per subject
        double avgIN  = (m_trialData.m_overallStatistics.totalShares+m_trialData.m_overallStatistics.totalPulls)/(double)m_trialData.m_organizationInformation.m_memberList.size();
        //System.out.println("avgOUT: "+avgOUT);
        //System.out.println("avgIN: "+avgIN);

        for (Subject s : m_trialData.m_organizationInformation.m_memberList) {
            int row = getRowIndexInTable(s.m_personName);
            //IN
            double inV = m_trialData.m_overallInteractions.get(s.m_personName).totalSharesRcv + m_trialData.m_overallInteractions.get(s.m_personName).totalPulls - avgIN;
            jtFactoids.getModel().setValueAt(m_formatter.format(inV), row, getColIndexInTable(m_header[1]));
            //OUT
            double outV = (m_trialData.m_overallInteractions.get(s.m_personName).totalShares + m_trialData.m_overallInteractions.get(s.m_personName).totalPosts) - avgOUT;
            jtFactoids.getModel().setValueAt(m_formatter.format(outV), row, getColIndexInTable(m_header[2]));
            //IN + OUT
            jtFactoids.getModel().setValueAt(m_formatter.format( java.lang.Math.sqrt( inV*inV+outV*outV ) ), row, getColIndexInTable(m_header[3]));
        }
    }

}
