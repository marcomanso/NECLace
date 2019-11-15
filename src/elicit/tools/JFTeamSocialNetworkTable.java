/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.Message;
import elicit.message.TrialData;
import elicit.message.TrialData.Subject;

/**
 *
 * @author mmanso
 */
public class JFTeamSocialNetworkTable extends JFSubjectsFactoidsTable {

    public JFTeamSocialNetworkTable(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Team Social Network: Interactions (shares only)");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringSubjectsSocialInteractions());
    }

    @Override
    public void CreateFactoidTable() {
        int nbrRows = m_trialData.m_organizationInformation.m_teamList.size();
        int nbrCols = nbrRows+1;

        Object[][] obj = new Object[nbrRows][nbrCols];
        String[] header = new String[nbrCols];
        header[0] = "Teams/WebSts";
        int count = 0;
        for (String team : m_trialData.m_organizationInformation.m_teamList) {
            header[count+1] = team.toUpperCase();
            obj[count][0] = team.toUpperCase();
            count++;
        }

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

    @Override
    public void FillTableInformationAtTime (int time) {
        //reset table
        for (int r=0; r<jtFactoids.getModel().getRowCount(); r++)
            for (int c=1; c<jtFactoids.getModel().getColumnCount(); c++)
                jtFactoids.getModel().setValueAt(0, r, c);
        //
        for (Message m : m_trialData.m_messageList) {
            if ( m.m_messageType.equals(Message.MESSAGE_TYPE_SHARE)
                    && Double.parseDouble(m.m_commonData.sequence) <= time ) {
//                    && Integer.parseInt(m.m_commonData.sequence) <= time ) {
                Subject origS = m_trialData.getOrganizationSubject(m.m_data);
                Subject destS = m_trialData.getOrganizationSubject(m.m_destSubject);
                int row=getRowIndexInTable(origS.m_teamName);
                int col=getColIndexInTable(destS.m_teamName);
                int value = (Integer)jtFactoids.getModel().getValueAt(row,col);
                jtFactoids.getModel().setValueAt(++value, row, col);
            }
            /*
            else if ( m.m_messageType.equals(Message.MESSAGE_TYPE_POST)
                    && Integer.parseInt(m.m_commonData.sequence) <= time ) {
                Subject origS = m_trialData.getOrganizationSubject(m.m_data);
                int row=getRowIndexInTable(origS.m_teamName);
                int col=getColIndexInTable(m.m_postSite.toUpperCase());
                int value = (Integer)jtFactoids.getModel().getValueAt(row,col);
                jtFactoids.getModel().setValueAt(++value, row, col);
            }
            else if ( m.m_messageType.equals(Message.MESSAGE_TYPE_PULL)
                    && Integer.parseInt(m.m_commonData.sequence) <= time ) {
                int row=getRowIndexInTable(m.m_data2);
                Subject origS = m_trialData.getOrganizationSubject(m.m_data);
                int col=getColIndexInTable(origS.m_teamName);
                int value = (Integer)jtFactoids.getModel().getValueAt(row,col);
                jtFactoids.getModel().setValueAt(++value, row, col);
            }
            */

        }
        for (int r=0; r<jtFactoids.getModel().getRowCount(); r++)
            for (int c=1; c<jtFactoids.getModel().getColumnCount(); c++)
                if ( (Integer)jtFactoids.getModel().getValueAt(r, c) == 0)
                    jtFactoids.getModel().setValueAt("", r, c);

    }

}
