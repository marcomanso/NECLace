/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFFactoidsReclassification.java
 *
 * Created on 12/Jan/2010, 10:25:12
 */
package elicit.utils.dialogs;

import java.awt.Component;
import java.io.*;

import elicit.message.*;
import elicit.tools.*;

/**
 *
 * @author mmanso
 */
public class JFFactoidsReclassification extends javax.swing.JFrame {

    java.io.File m_selectedFile = null;
    String m_file_reclassification_suffix = "_reclassified";
    String m_file_undoReclassification_suffix = "_undo";
    boolean m_reclassify = true;

    //

    //set reclassification table
//    String m_set[][][] =  {
//                            //set 1  //original                 //reclassification
//                            { { " 4E","22K","31K","38N","41N" },{ " 4S","22S","31S","38K","41S" } },
//                            //set 2  //original                 //reclassification
//                            { { " 4E","35K","42N","55N" },      { " 4S","35S","42S","55K" } },
//                            //set 3  //original                 //reclassification
//                            { { "15N","47K","59N" },            { "15S","47S","59S" } },
//                            //set 4  //original                 //reclassification
//                            { { "dummyzzzzz" },                 { "dummyzzzzz" } }
//                          };

    //set reclassification table
    String m_set[][][] =  {
                            //set 1  //original                 //reclassification
                            { { " 4E","31K","41N" },            { " 4S","31S","41K" } },
                            //set 2  //original                 //reclassification
                            { { " 4E","55N" },                  { " 4S","55K" } },
                            //set 3  //original                 //reclassification
                            { { " 6K","15N","47K","59N" },      { " 6S", "15S","47S","59S" } },
                            //set 4  //original                 //reclassification
                            { { "4E","19S","20K","34K","63N"},  { "4S","19K","20S","34S","63S" } }
                          };

    String factoidSetIDText[] = {"factoidset1", "factoidset2", "factoidset3", "factoidset4"};

    //inner class for message listener
    class LocalMessageListener implements MessageListener {

        Component m_parent = null;
        String m_destFile = null;
        FileWriter m_writer = null;
        int m_setNbr=-1;

        LocalMessageListener (Component parent) {
            m_parent = parent;
        }

        @Override
        public void OnStart() {
            //dest file
            if (m_reclassify)
                m_destFile = tools.FileStructure.Utils.addSuffixToFilename(m_selectedFile.getAbsolutePath(), m_file_reclassification_suffix);
            else
                m_destFile = tools.FileStructure.Utils.addSuffixToFilename(m_selectedFile.getAbsolutePath(), m_file_undoReclassification_suffix);

            try {
                //output file
                m_writer = new FileWriter(m_destFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void OnNewMessage(Message message_p) {
            try {
                //output file
                if (m_writer!=null && message_p.m_logLine != null && message_p.m_logLine.length()!=0) {
                    if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_INITIATE)) {
                        m_setNbr = GetFactoidSetID(message_p);
                    }
                    //already initialized? (needs INITIATE msg)
                    if (m_setNbr!=-1) {
                        //String modMessageLine =
                        ChangeFactoidTableInLine(message_p);
                        m_writer.write(message_p.m_logLine+"\n");
                        //System.out.println("mod message: "+modMessageLine);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void OnFinish() {
            if (m_writer != null) {
                try {
                    m_writer.close();
                    //
                    javax.swing.JOptionPane.showMessageDialog(m_parent, "Conversion finished - new classification: "+GetNewClassificationListAsString(), "Conversion finished.", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(m_parent, "Conversion failed.", "Conversion failed", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                javax.swing.JOptionPane.showMessageDialog(m_parent, "Conversion failed.", "Conversion failed", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }

        public String GetNewClassificationListAsString() {
            String str ="";
            int indexSource=0;
            int indexDest=1;
            if (!m_reclassify) {indexSource=1;indexDest=0;}
            for ( int factNbr=0; factNbr<m_set[m_setNbr-1][indexSource].length; factNbr++ ) {
                str += m_set[m_setNbr-1][indexDest][factNbr]+" ";
            }
            return str;
        }

        public int GetFactoidSetID(Message message_p) {
            int i = 1;
            for (int j=0; j<factoidSetIDText.length; j++)
                if ( message_p.m_logLine.indexOf(factoidSetIDText[j])!=-1 )
                    i=j+1;
            return i;
        }

        public void ChangeFactoidTableInLine(Message message_p) {
            //find a factoid - replace
            int indexSource=0;
            int indexDest=1;
            if (!m_reclassify) {indexSource=1;indexDest=0;}
            for ( int factNbr=0; factNbr<m_set[m_setNbr-1][indexSource].length; factNbr++ ) {
                message_p.m_logLine = message_p.m_logLine.replaceAll(m_set[m_setNbr-1][indexSource][factNbr], m_set[m_setNbr-1][indexDest][factNbr]);
                //System.out.println("? "+m_set[m_setNbr-1][indexSource][factNbr]+" --> "+m_set[m_setNbr-1][indexDest][factNbr]);
                //System.out.println("..mod: "+str);
            }
            //return str;
        }
    }
    LocalMessageListener m_localMessageListener;

    /** Creates new form JFFactoidsReclassification */
    public JFFactoidsReclassification() {
        tools.UItools.UITools.setNativeLookAndFeel();
        //
        initComponents();
        //
        m_localMessageListener = new LocalMessageListener(this);
    }

    //
    // UTILS
    private void ReadLog() {
        ReadELICITLog readLog = new ReadELICITLog(m_selectedFile.getAbsolutePath());
        readLog.addMessageListener(m_localMessageListener);
        readLog.doIt();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jfcOpenFile = new javax.swing.JFileChooser();
        jLabel2 = new javax.swing.JLabel();
        jtfFilename = new javax.swing.JTextField();
        jbReclassify = new javax.swing.JButton();
        jbUndo = new javax.swing.JButton();
        jbClose = new javax.swing.JButton();

        setTitle("Factoids Reclassification");

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("  Log File");
        jLabel2.setOpaque(true);

        jtfFilename.setEditable(false);
        jtfFilename.setToolTipText("Click me to open a file ...");
        jtfFilename.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfFilenameMouseClicked(evt);
            }
        });

        jbReclassify.setText("Reclassify");
        jbReclassify.setPreferredSize(new java.awt.Dimension(85, 25));
        jbReclassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbReclassifyActionPerformed(evt);
            }
        });

        jbUndo.setText("Undo");
        jbUndo.setPreferredSize(new java.awt.Dimension(85, 25));
        jbUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUndoActionPerformed(evt);
            }
        });

        jbClose.setText("Close");
        jbClose.setPreferredSize(new java.awt.Dimension(85, 25));
        jbClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jbReclassify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbUndo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfFilename, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfFilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbUndo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbReclassify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfFilenameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfFilenameMouseClicked
        int returnVal = jfcOpenFile.showOpenDialog(this);
        if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION
            && jfcOpenFile.getSelectedFile() != null
            && jfcOpenFile.getSelectedFile().isFile() )
        {
            m_selectedFile = jfcOpenFile.getSelectedFile();
            jtfFilename.setText(jfcOpenFile.getSelectedFile().getAbsolutePath());
        }
        else {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a valid file.", "Warning ...", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jtfFilenameMouseClicked

    private void jbCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jbCloseActionPerformed

    private void jbReclassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReclassifyActionPerformed
        m_reclassify = true;
        if (m_selectedFile != null && m_selectedFile.isFile())
            ReadLog();
    }//GEN-LAST:event_jbReclassifyActionPerformed

    private void jbUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUndoActionPerformed
        m_reclassify = false;
        if (m_selectedFile != null && m_selectedFile.isFile())
            ReadLog();
    }//GEN-LAST:event_jbUndoActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFFactoidsReclassification().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbClose;
    private javax.swing.JButton jbReclassify;
    private javax.swing.JButton jbUndo;
    private javax.swing.JFileChooser jfcOpenFile;
    private javax.swing.JTextField jtfFilename;
    // End of variables declaration//GEN-END:variables

}
