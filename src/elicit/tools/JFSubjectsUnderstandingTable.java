/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import elicit.message.TrialData.Subject;
import elicit.message.Message;
import metrics.awareness.IDsQualityMap.AwUndData;

/**
 *
 * @author mmanso
 */
public class JFSubjectsUnderstandingTable extends JFSubjectsFactoidsTable {

    public JFSubjectsUnderstandingTable(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Subjects Understanding (IDs)");
        //
        javax.swing.JLabel jlSolution = new javax.swing.JLabel();
        for (String keySol : m_trialData.m_solution.keySet()) {
            jlSolution.setText(jlSolution.getText()+" "+keySol+" ( "+m_trialData.m_solution.get(keySol)+" )    ");
        }
        jPanelTOP.add(jlSolution);
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringID(m_trialData.m_solution));
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
        //
        for ( Subject s : m_trialData.m_organizationInformation.m_memberList ) {
            int row=getRowIndexInTable(s.m_personName);
            int colWho=getColIndexInTable(Message.m_teamWho);
            int colWhat=getColIndexInTable(Message.m_teamWhat);
            int colWhere=getColIndexInTable(Message.m_teamWhere);
            int colWhen=getColIndexInTable(Message.m_teamWhen);
            AwUndData data = m_trialData.m_subjectsIDsQualityMap.GetLastSubjectIDAtTime(s.m_personName, time);
            if (data!=null) {
                jtFactoids.getModel().setValueAt(data.who, row, colWho);
                jtFactoids.getModel().setValueAt(data.what, row, colWhat);
                jtFactoids.getModel().setValueAt(data.where, row, colWhere);
                String when="";
                if (data.whenMonth==null)  when+="-"; else when+=data.whenMonth+" ";
                if (data.whenDay==null)  when+="- "; else when+=data.whenDay+" ";
                when += "AT ";
                if (data.whenTime==null)  when+="- "; else when+=data.whenTime;
                jtFactoids.getModel().setValueAt(when, row, colWhen);
            }
            else {
                jtFactoids.getModel().setValueAt(null, row, colWho);
                jtFactoids.getModel().setValueAt(null, row, colWhat);
                jtFactoids.getModel().setValueAt(null, row, colWhere);
                jtFactoids.getModel().setValueAt(null, row, colWhen);
            }
        }
    }

}
