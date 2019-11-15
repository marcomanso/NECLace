/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import java.text.NumberFormat;
import java.text.DecimalFormat;

import elicit.message.TrialData;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author mmanso
 */
public class JFSubjectsQualityInteractionsTable extends JFSubjectsFactoidsTable {

    public static String m_header[] = {"Subjects/Teams", "Q Interactions"};

    NumberFormat m_formatter = null ;

    public JFSubjectsQualityInteractionsTable(TrialData trialData) {
        super(trialData);
        //
    }

    @Override
    public void SetTitle()
    {
        super.SetTitle();
        this.setTitle("Quality of Interactions (Table)");
        //
        jPanelBOTTOM.setVisible(true);
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
    }
    
    @Override
    public void CreateFactoidTable() {
        //
        //int nbrRows = m_trialData.m_organizationInformation.m_memberList.size()+m_trialData.m_organizationInformation.m_teamList.size();
        int nbrRows = m_trialData.m_interactions.m_qualityOfInteractionShareAndPost.keySet().size();

        Object[][] obj = new Object[nbrRows][m_header.length];

        int count = 0;
        for (String key : m_trialData.m_interactions.m_qualityOfInteractionShareAndPost.keySet() ) {
            obj[count][0] = key;
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
        if (m_formatter==null) m_formatter = new DecimalFormat ( "0" ) ;

        //subject
        for (String key : m_trialData.m_interactions.m_qualityOfInteractionShareAndPost.keySet() ) {
            metrics.interaction.QualityOfInteractions.QualityOfInteraction q = m_trialData.m_interactions.m_qualityOfInteractionShareAndPost.get(key).getValueAt(time);
            int row = getRowIndexInTable(key);
            if (q==null) {
                jtFactoids.getModel().setValueAt("0", row, getColIndexInTable(m_header[1]));
            }
            else {
                jtFactoids.getModel().setValueAt(m_formatter.format(q.commulativeValue)+"  (R:"+q.totalKER+",N:"+q.totalN+")", row, getColIndexInTable(m_header[1]));
            }
        }
        
    }

}
