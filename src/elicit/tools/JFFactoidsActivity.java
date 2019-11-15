/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.FactoidMessage;
import elicit.message.Message;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationQuality;

/**
 *
 * @author mmanso
 */
public class JFFactoidsActivity extends JFSubjectsFactoidsTable {

    private static String[] m_rowLabels = {"Shares", "Posts", "S + P", "(clas.)"};

    JFFactoidsActivity (elicit.message.TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Factoids Activity (Sharing and Posting)");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringQuantityOfFactoids());
    }

    @Override
    public void CreateFactoidTable() {

        int nbrRows = m_rowLabels.length;
        int nbrCols = m_trialData.m_factoidStats.totalFactoids+1;

        //new new model?
        //build factoids (columns)
        Object[][] obj = new Object[nbrRows][nbrCols];
        String[] factoidsCol = new String[nbrCols];
        factoidsCol[0] = "Action";
        for (int i=1; i<nbrCols; i++)
        {
            factoidsCol[i] = Integer.toString(i);
        }
        int count = 0;
        for (String label : m_rowLabels) {
            obj[count][0] = label;
            count++;
        }

        jtFactoids.setModel(
            new javax.swing.table.DefaultTableModel(obj,factoidsCol) {

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

    public int CountFactoidOccurencesInList (InformationAccessible.AccessibilityIndexVector v, int factnbr) {
        int nbr=0;
        for (InformationAccessible.InformationAccessibleData data : v) {
            try {
                if ( data.time <= GetSelectedTime ()
                     && !data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY)
                     && FactoidMessage.GetFactoidNumber(data.factoidMetadata)==factnbr )
                        nbr++;
            }
            catch (Exception ex) {
                System.out.println("..Error in factoid: "+data.factoidMetadata);
                System.out.println("..Exception: "+ex.getMessage());
            }
        }
        return nbr;
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each factoid
        for ( String factoidID : m_trialData.m_factoidsMessages.keySet() ) {
            int factnbr = FactoidMessage.GetFactoidNumber(factoidID);
            int nbrShares = 0;
            int nbrPosts = 0;
            for (elicit.message.TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
            {
                nbrShares += CountFactoidOccurencesInList(InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_formsInteraction), factnbr);
                nbrPosts  += CountFactoidOccurencesInList(InformationQuality.GetInformation2BySubject(s, m_trialData.m_informationQuality.m_formsInteraction), factnbr);
            }
            if (nbrShares!=0) jtFactoids.setValueAt(nbrShares, getRowIndexInTable(m_rowLabels[0]), factnbr);
            else              jtFactoids.setValueAt("", getRowIndexInTable(m_rowLabels[0]), factnbr);
            if (nbrPosts!=0)  jtFactoids.setValueAt(nbrPosts, getRowIndexInTable(m_rowLabels[1]), factnbr);
            else              jtFactoids.setValueAt("", getRowIndexInTable(m_rowLabels[1]), factnbr);
            if ((nbrShares+nbrPosts)!=0) jtFactoids.setValueAt(nbrShares+nbrPosts, getRowIndexInTable(m_rowLabels[2]), factnbr);
            else              jtFactoids.setValueAt("", getRowIndexInTable(m_rowLabels[2]), factnbr);

            jtFactoids.setValueAt(FactoidMessage.getFactoidRelevanceChar(factoidID), getRowIndexInTable(m_rowLabels[3]), factnbr);
        }


        /*
        for ( int factnbr=1; factnbr<=m_trialData.m_factoidStats.totalFactoids ; factnbr++ )
        {
            int nbrShares = 0;
            int nbrPosts = 0;
            for (elicit.message.TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
            {
                nbrShares += CountFactoidOccurencesInList(InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_formsInteraction), factnbr);
                nbrPosts  += CountFactoidOccurencesInList(InformationQuality.GetInformation2BySubject(s, m_trialData.m_informationQuality.m_formsInteraction), factnbr);
            }
            if (nbrShares!=0) jtFactoids.setValueAt(nbrShares, getRowIndexInTable(m_rowLabels[0]), factnbr);
            else              jtFactoids.setValueAt("", getRowIndexInTable(m_rowLabels[0]), factnbr);
            if (nbrPosts!=0)  jtFactoids.setValueAt(nbrPosts, getRowIndexInTable(m_rowLabels[1]), factnbr);
            else              jtFactoids.setValueAt("", getRowIndexInTable(m_rowLabels[1]), factnbr);
            if ((nbrShares+nbrPosts)!=0) jtFactoids.setValueAt(nbrShares+nbrPosts, getRowIndexInTable(m_rowLabels[2]), factnbr);
            else              jtFactoids.setValueAt("", getRowIndexInTable(m_rowLabels[2]), factnbr);


            jtFactoids.setValueAt(FactoidMessage.getFactoidRelevanceChar(), getRowIndexInTable(m_rowLabels[3]), factnbr);
        }//end for
         */
    }

}
