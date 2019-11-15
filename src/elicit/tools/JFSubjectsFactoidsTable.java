/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFSubjectsFactoidsTable.java
 *
 * Created on 22/Set/2009, 14:50:52
 */

package elicit.tools;

import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationQuality;
import elicit.message.*;
import javax.swing.*;

/**
 *
 * @author mmanso
 */
public class JFSubjectsFactoidsTable extends javax.swing.JFrame {

    TrialData m_trialData = null;
    public static String m_header[] = {"Subjects / ID space", Message.m_teamWho, Message.m_teamWhat, Message.m_teamWhere, Message.m_teamWhen};

    /** Creates new form JFSubjectsFactoidsTable */
    public JFSubjectsFactoidsTable(TrialData trialData) {
        initComponents();
        //
        m_trialData = trialData;
        jlTimeSec.setText(Integer.toString(GetSelectedTime()));
        //
        SetTitle();
        SetTableModel();
        //
        CreateFactoidTable();
        FillTableInformationAtTime(GetSelectedTime());
    }

    public void SetTitle ()
    {
        this.setTitle("Accessible Factoids (distributed by server)");
    }

    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringFactoidRelevance());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTOP = new javax.swing.JPanel();
        jspFactoidsTable = new javax.swing.JScrollPane();
        jtFactoids = new javax.swing.JTable();
        jPanelBOTTOM = new javax.swing.JPanel();
        jlTime = new javax.swing.JLabel();
        jsTime = new javax.swing.JSlider();
        jlTimeSec = new javax.swing.JLabel();
        jbExport = new javax.swing.JButton();

        getContentPane().add(jPanelTOP, java.awt.BorderLayout.NORTH);

        jspFactoidsTable.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 5));

        jtFactoids.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 6));
        jtFactoids.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23", "Title 24", "Title 25", "Title 26", "Title 27", "Title 28", "Title 29", "Title 30", "Title 31", "Title 32", "Title 33", "Title 34", "Title 35", "Title 36", "Title 37", "Title 38", "Title 39", "Title 40", "Title 41", "Title 42", "Title 43", "Title 44", "Title 45", "Title 46", "Title 47", "Title 48", "Title 49", "Title 50", "Title 51", "Title 52", "Title 53", "Title 54", "Title 55", "Title 56", "Title 57", "Title 58", "Title 59", "Title 60", "Title 61", "Title 62", "Title 63", "Title 64", "Title 65", "Title 66", "Title 67", "Title 68"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jspFactoidsTable.setViewportView(jtFactoids);

        getContentPane().add(jspFactoidsTable, java.awt.BorderLayout.CENTER);

        jlTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlTime.setText("Time: ");
        jPanelBOTTOM.add(jlTime);

        jsTime.setValue(100);
        jsTime.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jsTimeMouseReleased(evt);
            }
        });
        jsTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsTimeStateChanged(evt);
            }
        });
        jPanelBOTTOM.add(jsTime);

        jlTimeSec.setText("...");
        jlTimeSec.setPreferredSize(new java.awt.Dimension(70, 14));
        jPanelBOTTOM.add(jlTimeSec);

        jbExport.setText("Export to CSV");
        jbExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportActionPerformed(evt);
            }
        });
        jPanelBOTTOM.add(jbExport);

        getContentPane().add(jPanelBOTTOM, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jsTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsTimeStateChanged
        jlTimeSec.setText(Integer.toString(GetSelectedTime()));
    }//GEN-LAST:event_jsTimeStateChanged

    private void jsTimeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsTimeMouseReleased
        FillTableInformationAtTime (GetSelectedTime());
    }//GEN-LAST:event_jsTimeMouseReleased

    private void jbExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportActionPerformed
        java.io.File file = null;
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("CSV files", "txt", "csv");
        try {
            file = elicit.utils.dialogs.DialogFileChooser.SelectFile(this, filter, false);
            elicit.exportdata.ExportTableModel.WriteTableModelToFile(jtFactoids.getModel(), file);
        }
        catch (Exception ex) {
             JOptionPane.showMessageDialog(this,
                                         ex.getMessage(),
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbExportActionPerformed

    public void CreateFactoidTable() {
        int nbrRows = m_trialData.m_organizationInformation.m_memberList.size();
        //int nbrCols = m_trialData.m_factoidStats.totalFactoids+1;
        //int nbrCols = 70; //hack
        
        int nbrCols = ( m_trialData.m_factoidStats.totalFactoids<70 ) ? 70 : m_trialData.m_factoidStats.totalFactoids+1 ;

        //new new model?
        //build factoids (columns)
        Object[][] obj = new Object[nbrRows][nbrCols];
        String[] factoidsCol = new String[nbrCols];
        factoidsCol[0] = "Subjects";
        for (int i=1; i<nbrCols; i++)
        {
            factoidsCol[i] = Integer.toString(i);
        }
        int count = 0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
            obj[count][0] = s.m_personName;
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

    public void FillSubjectsFactoidsRelevance (String subjectName, InformationAccessible.AccessibilityIndexVector v, int sCount) {
        for ( int i=1; i<=m_trialData.m_factoidStats.totalFactoids ; i++ )
        {
            jtFactoids.setValueAt(' ', sCount,  i);
        }
        for (InformationAccessible.InformationAccessibleData data : v) {
            if ( data.time <= GetSelectedTime ()
                 && !data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY) )
            {
                //draw in table
                try {
                    jtFactoids.setValueAt(FactoidMessage.getFactoidRelevanceChar(data.factoidMetadata), sCount,  FactoidMessage.GetFactoidNumber(data.factoidMetadata));
                }
                catch (Exception ex) {
                    System.out.println("..Error in factoid: "+data.factoidMetadata);
                    System.out.println("..Exception: "+ex.getMessage());
                }
            }
        }
    }

    public void FillSubjectsFactoidsNumber (String subjectName, InformationAccessible.AccessibilityIndexVector v, int sCount) {
        for ( int factnbr=1; factnbr<=m_trialData.m_factoidStats.totalFactoids ; factnbr++ )
        {
            jtFactoids.setValueAt("", sCount,  factnbr);
            int count = 0;
            for (InformationAccessible.InformationAccessibleData data : v) {
                try {
                    if ( data.time <= GetSelectedTime ()
                         && !data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY)
                         && FactoidMessage.GetFactoidNumber(data.factoidMetadata)==factnbr )
                            count++;
                }
                catch (Exception ex) {
                    System.out.println("..Error in factoid: "+data.factoidMetadata);
                    System.out.println("..Exception: "+ex.getMessage());
                }
            }
            //
            if (count!=0)
                jtFactoids.setValueAt(count, sCount,  factnbr);
                //jtFactoids.setValueAt(Integer.toString(count), sCount,  factnbr);
        }//end for
    }

    public void FillTableInformationAtTime (int time) {
        //parse each subject
        int sCount=0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
        {
            FillSubjectsFactoidsRelevance(s.m_personName, InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_accessibilityNewInfomationServer), sCount);
            sCount++; //next subject
        }
    }

    public int GetSelectedTime () {
        return (int) ( jsTime.getValue() * m_trialData.m_trialInformation.m_durationSec / 100.0);
    }

    public int getRowIndexInTable (String name) {
        javax.swing.table.TableModel model = jtFactoids.getModel();
        for (int i=0; i<model.getRowCount() ; i++) {
            if ( name.equalsIgnoreCase( (String)model.getValueAt(i,0) ) )
                return i;
        }
        return -1;
    }

    public int getColIndexInTable (String name) {

        javax.swing.table.TableColumnModel header = jtFactoids.getTableHeader().getColumnModel();
        for (int i=0; i<header.getColumnCount() ; i++) {
            if ( name.equalsIgnoreCase( (String)header.getColumn(i).getHeaderValue() ) )
                return i;
        }
        return -1;
    }

    public JTable getTable() {
        return jtFactoids;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel jPanelBOTTOM;
    protected javax.swing.JPanel jPanelTOP;
    private javax.swing.JButton jbExport;
    protected javax.swing.JLabel jlTime;
    protected javax.swing.JLabel jlTimeSec;
    protected javax.swing.JSlider jsTime;
    protected javax.swing.JScrollPane jspFactoidsTable;
    protected javax.swing.JTable jtFactoids;
    // End of variables declaration//GEN-END:variables

}