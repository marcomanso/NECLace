/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.Message;
import elicit.message.TrialData;
import metrics.informationquality.*;

/**
 *
 * @author mmanso
 */
public class JFInformationTable extends JFSubjectsFactoidsTable {

    public static String m_header[] = {"Subjects/Teams","Reach (server only)","R Reach (server only)","Reach","R Reach","Reached","R Reached"};

    public JFInformationTable(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Information");
        jPanelBOTTOM.setVisible(false);
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringCriticalInformation());
    }

    @Override
    public void CreateFactoidTable() {

        //m_trialData.m_informationQuality.m_criticalInformationAccessible.GetStatsAtTime(0, m_trialData.m_informationQuality.m_accessibilityIndex);

        int nbrRows = m_trialData.m_organizationInformation.m_memberList.size()
                      + m_trialData.m_organizationInformation.m_teamList.size()
                      +1;  //for overall
        int nbrCols = m_header.length;

        Object[][] obj = new Object[nbrRows][nbrCols];
        int count = 0;
        obj[count][0] = Message.m_OVERALL;
        count++;
        for (String team : m_trialData.m_organizationInformation.m_teamList) {
            obj[count][0] = team.toUpperCase();
            count++;
        }
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            obj[count][0] = s.m_personName.toUpperCase();
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
        //InformationQuality infoQ = m_trialData.m_informationQuality;
        InformationAccessible infoAccessibleSrv = m_trialData.m_informationQuality.m_accessibilityNewInfomationServer;
        InformationAccessible infoAccessible = m_trialData.m_informationQuality.m_accessibilityIndex;
        InformationAccessible infoReached = m_trialData.m_informationQuality.m_reachedIndex;
        //subjects
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            int row=getRowIndexInTable(s.m_personName);
            double indexSrv = InformationQuality.GetInformationBySubject(s, infoAccessibleSrv).lastElement().indexAll;
            jtFactoids.getModel().setValueAt((int)indexSrv, row, 1);
            //
            double indexRSrv = InformationQuality.GetInformationBySubject(s, infoAccessibleSrv).lastElement().indexRelevant;
            jtFactoids.getModel().setValueAt((int)indexRSrv, row, 2);
            //
            double indexAll = InformationQuality.GetInformationBySubject(s, infoAccessible).lastElement().indexAll;
            jtFactoids.getModel().setValueAt((int)indexAll, row, 3);
            //
            double indexRAll = InformationQuality.GetInformationBySubject(s, infoAccessible).lastElement().indexRelevant;
            jtFactoids.getModel().setValueAt((int)indexRAll, row, 4);
            //
            double indexReachedAll = InformationQuality.GetInformationBySubject(s, infoReached).lastElement().indexAll;
            jtFactoids.getModel().setValueAt((int)indexReachedAll, row, 5);
            //
            double indexReachedRAll = InformationQuality.GetInformationBySubject(s, infoReached).lastElement().indexRelevant;
            jtFactoids.getModel().setValueAt((int)indexReachedRAll, row, 6);
            //
        }
        for (String t : m_trialData.m_organizationInformation.m_teamList) {
            int row=getRowIndexInTable(t);
            InformationAccessible.AccessibilityIndexVector infoV = InformationQuality.GetInformation2ByTeam(t, infoAccessibleSrv);
            if (infoV!=null) {
                jtFactoids.getModel().setValueAt((int)infoV.lastElement().indexAll, row, 1);
                jtFactoids.getModel().setValueAt((int)infoV.lastElement().indexRelevant, row, 2);
            }
            //
            infoV = InformationQuality.GetInformation2ByTeam(t, infoAccessible);
            if (infoV!=null) {
                jtFactoids.getModel().setValueAt((int)infoV.lastElement().indexAll, row, 3);
                jtFactoids.getModel().setValueAt((int)infoV.lastElement().indexRelevant, row, 4);
            }
            //
            infoV = InformationQuality.GetInformation2ByTeam(t, infoReached);
            if (infoV!=null) {
                jtFactoids.getModel().setValueAt((int)infoV.lastElement().indexAll, row, 5);
                jtFactoids.getModel().setValueAt((int)infoV.lastElement().indexRelevant, row, 6);
            }
        }
        {
            int row=getRowIndexInTable(Message.m_OVERALL);
            jtFactoids.getModel().setValueAt((int)m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_set2AccessIndexOverall.lastElement().indexAll, row, 1);
            jtFactoids.getModel().setValueAt((int)m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_set2AccessIndexOverall.lastElement().indexRelevant, row, 2);
            jtFactoids.getModel().setValueAt((int)m_trialData.m_informationQuality.m_accessibilityIndex.m_set2AccessIndexOverall.lastElement().indexAll, row, 3);
            jtFactoids.getModel().setValueAt((int)m_trialData.m_informationQuality.m_accessibilityIndex.m_set2AccessIndexOverall.lastElement().indexRelevant, row, 4);
            jtFactoids.getModel().setValueAt((int)m_trialData.m_informationQuality.m_reachedIndex.m_set2AccessIndexOverall.lastElement().indexAll, row, 5);
            jtFactoids.getModel().setValueAt((int)m_trialData.m_informationQuality.m_reachedIndex.m_set2AccessIndexOverall.lastElement().indexRelevant, row, 6);
        }
    }

}
