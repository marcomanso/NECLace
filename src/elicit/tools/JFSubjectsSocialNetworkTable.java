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
public class JFSubjectsSocialNetworkTable extends JFSubjectsFactoidsTable {

    public JFSubjectsSocialNetworkTable(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Social Network: Subjects and Websites interactions (shares, posts and pulls)");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringSubjectsSocialInteractions());
    }

    @Override
    public void CreateFactoidTable() {
        int nbrRows = m_trialData.m_organizationInformation.m_memberList.size()+m_trialData.m_organizationInformation.m_teamList.size();
        int nbrCols = nbrRows+1;

        Object[][] obj = new Object[nbrRows][nbrCols];
        String[] header = new String[nbrCols];
        header[0] = "Subjs/WebSts";
        int count = 0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            header[count+1] = s.m_personName;
            obj[count][0] = s.m_personName;
            count++;
        }
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
        	
        	//System.out.println("processing: "+m);
        	
            if ( m.m_messageType.equals(Message.MESSAGE_TYPE_SHARE)
//                    && Integer.parseInt(m.m_commonData.sequence) <= time ) {
                    && Double.parseDouble(m.m_commonData.sequence) <= time ) {
                int row=getRowIndexInTable(m.m_data);
                int col=getColIndexInTable(m.m_destSubject);
                int value = (Integer)jtFactoids.getModel().getValueAt(row,col);
                jtFactoids.getModel().setValueAt(++value, row, col);
            }
            else if ( m.m_messageType.equals(Message.MESSAGE_TYPE_POST)
//                    && Integer.parseInt(m.m_commonData.sequence) <= time ) {
                    && Double.parseDouble(m.m_commonData.sequence) <= time ) {
                int row=getRowIndexInTable(m.m_data);
                int col=getColIndexInTable(m.m_postSite.toUpperCase());
                int value = (Integer)jtFactoids.getModel().getValueAt(row,col);
                jtFactoids.getModel().setValueAt(++value, row, col);
            }
            else if ( m.m_messageType.equals(Message.MESSAGE_TYPE_PULL)
                    && Double.parseDouble(m.m_commonData.sequence) <= time ) {
//                    && Integer.parseInt(m.m_commonData.sequence) <= time ) {
                int row=getRowIndexInTable(m.m_data2.toUpperCase());
                int col=getColIndexInTable(m.m_data);
                int value = (Integer)jtFactoids.getModel().getValueAt(row,col);
                jtFactoids.getModel().setValueAt(++value, row, col);
            }

        }
        for (int r=0; r<jtFactoids.getModel().getRowCount(); r++)
            for (int c=1; c<jtFactoids.getModel().getColumnCount(); c++)
                if ( (Integer)jtFactoids.getModel().getValueAt(r, c) == 0)
                    jtFactoids.getModel().setValueAt("", r, c);

    }

    public int getNbrSharesToProcess (String subjectName) {
        int nbrShares = 0;
        int col = getColIndexInTable(subjectName);
        //go over all subjects
        for (Subject s : m_trialData.m_organizationInformation.m_memberList) {
            if (!s.m_personName.equals(subjectName)) {
                int row = getRowIndexInTable(s.m_personName);
                try {
                    nbrShares+=(Integer)jtFactoids.getValueAt(row, col);
                } catch (Exception ex) {}
            }
        }
        return nbrShares;
    }

    public double getAVGNbrSharesToProcess () {
        int nbrShares = 0;
        for (Subject s : m_trialData.m_organizationInformation.m_memberList)
            nbrShares+=getNbrSharesToProcess(s.m_personName);
        return ((double)nbrShares)/((double)m_trialData.m_organizationInformation.m_memberList.size());
    }

    public int getNbrPostsToProcess (String subjectName) {
        int nbrPosts = 0;
        //go over all websites
        for ( String team : m_trialData.m_organizationInformation.m_teamList ) {
            if (m_trialData.hasWebsiteAccess(m_trialData.getOrganizationSubject(subjectName), team)) {
                int col = getColIndexInTable(team);
                for ( Subject s : m_trialData.m_organizationInformation.m_memberList ) {
                    if (!s.m_personName.equals(subjectName)) {
                        int row = getRowIndexInTable(s.m_personName);
                        try {
                            nbrPosts+=(Integer)jtFactoids.getValueAt(row, col);
                        } catch (Exception ex) {}
                    }
                }
            }
        }
        return nbrPosts;
    }

    public double getAVGNbrPostsToProcess () {
        int nbrPosts = 0;
        for (Subject s : m_trialData.m_organizationInformation.m_memberList)
            nbrPosts+=getNbrPostsToProcess(s.m_personName);
        return ((double)nbrPosts)/((double)m_trialData.m_organizationInformation.m_memberList.size());
    }

}
