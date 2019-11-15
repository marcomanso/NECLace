/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import java.text.NumberFormat;
import java.text.DecimalFormat;

import elicit.message.TrialData;
import elicit.message.TrialData.Subject;
import elicit.message.Message;
import javax.swing.table.DefaultTableCellRenderer;
import metrics.awareness.IDsQualityMap.AwUndData;

/**
 *
 * @author mmanso
 */
public class JFMoMIDScoresTable extends JFSubjectsFactoidsTable {

    public static String m_headerScores[] = {"Subjects",
                                        Message.m_teamWho,
                                        Message.m_teamWho+" (sec)",
                                        Message.m_teamWhat,
                                        Message.m_teamWhat+" (sec)",
                                        Message.m_teamWhere,
                                        Message.m_teamWhere+" (sec)",
                                        Message.m_teamWhen,
                                        Message.m_teamWhen+" (sec)",
                                        "OVERALL",
                                        "Time Answ (sec)" };

    NumberFormat m_formatter = null ;

    public JFMoMIDScoresTable(TrialData trialData) {
        super(trialData);
        //
    }

    @Override
    public void SetTitle()
    {
        super.SetTitle();
        this.setTitle("MoM: ID scores");
        //
        jPanelBOTTOM.setVisible(false);
    }

    @Override
    public void SetTableModel () {
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoring());
        jtFactoids.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
    }
    
    @Override
    public void CreateFactoidTable() {
        //
        int nbrRows = m_trialData.m_organizationInformation.m_memberList.size();

        Object[][] obj = new Object[nbrRows][m_headerScores.length];

        int count=0;
        for (Subject s : m_trialData.m_organizationInformation.m_memberList) {
            obj[count][0] = s.m_personName;
            count++;
        }

        jtFactoids.setModel(
            new javax.swing.table.DefaultTableModel(obj,m_headerScores) {

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
        if (m_formatter==null) m_formatter = new DecimalFormat ( "0.00" ) ;

        //subject
        for ( Subject s : m_trialData.m_organizationInformation.m_memberList ) {
            int row           =getRowIndexInTable(s.m_personName);
            int colWho        =getColIndexInTable(m_headerScores[1]);
            int colWhoSec     =getColIndexInTable(m_headerScores[2]);
            int colWhat       =getColIndexInTable(m_headerScores[3]);
            int colWhatSec    =getColIndexInTable(m_headerScores[4]);
            int colWhere      =getColIndexInTable(m_headerScores[5]);
            int colWhereSec   =getColIndexInTable(m_headerScores[6]);
            int colWhen       =getColIndexInTable(m_headerScores[7]);
            int colWhenSec    =getColIndexInTable(m_headerScores[8]);
            int colOverall    =getColIndexInTable(m_headerScores[9]);
            int colSec        =getColIndexInTable(m_headerScores[10]);

            AwUndData data=null;

            // WHO
            data = m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhoID(s.m_personName);
            if (data!=null) {
                jtFactoids.getModel().setValueAt(m_formatter.format(data.whoQ*4), row, colWho);
                jtFactoids.getModel().setValueAt((int)data.time, row, colWhoSec);
            }
            else {
                jtFactoids.getModel().setValueAt(null, row, colWho);
                jtFactoids.getModel().setValueAt(null, row, colWhoSec);
            }
            // WHAT
            data = m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhatID(s.m_personName);
            if (data!=null) {
                jtFactoids.getModel().setValueAt(m_formatter.format(data.whatQ*4), row, colWhat);
                jtFactoids.getModel().setValueAt((int)data.time, row, colWhatSec);
            }
            else {
                jtFactoids.getModel().setValueAt(null, row, colWhat);
                jtFactoids.getModel().setValueAt(null, row, colWhatSec);
            }
            // WHERE
            data = m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(s.m_personName);
            if (data!=null) {
                jtFactoids.getModel().setValueAt(m_formatter.format(data.whereQ*4), row, colWhere);
                jtFactoids.getModel().setValueAt((int)data.time, row, colWhereSec);
            }
            else {
                jtFactoids.getModel().setValueAt(null, row, colWhere);
                jtFactoids.getModel().setValueAt(null, row, colWhereSec);
            }
            // WHEN
            data = m_trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhenID(s.m_personName);
            if (data!=null) {
                jtFactoids.getModel().setValueAt(m_formatter.format(data.whenOverallQ*4), row, colWhen);
                jtFactoids.getModel().setValueAt((int)data.time, row, colWhenSec);
            }
            else {
                jtFactoids.getModel().setValueAt(null, row, colWhen);
                jtFactoids.getModel().setValueAt(null, row, colWhenSec);
            }
            //overall
            data = m_trialData.m_subjectsIDsQualityMap.GetLastSubjectID(s.m_personName);
            if (data!=null) {
                jtFactoids.getModel().setValueAt(m_formatter.format(data.overallQ*4), row, colOverall);
                jtFactoids.getModel().setValueAt((int)data.time, row, colSec);
            }
            else {
                jtFactoids.getModel().setValueAt(null, row, colOverall);
                jtFactoids.getModel().setValueAt(null, row, colSec);
            }
        }
        //
        
    }

}
