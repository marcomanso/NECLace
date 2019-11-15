/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFAgility.java
 *
 * Created on Jan 25, 2012, 10:21:42 AM
 */

package agility;

import java.io.File;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author marcomanso
 */
public class JFAgility extends javax.swing.JFrame {

    private File m_selectedFile = null;
    private final String EMPTY_FILE = " <select file...>";
    //
    JFAgilityMapChart m_agilityMap = new JFAgilityMapChart();
    //
    private ExperimentDataList m_experimentDataList = new ExperimentDataList();

    /** Creates new form JFAgility */
    public JFAgility() {
        initComponents();
        //
        disableInput();
        setFileSelected();
    }
    private void enableInput () {
        jbAgilityMaps.setEnabled(true);
        //jbSpiderWeb.setEnabled(true);
        jbExport.setEnabled(true);
    }
    private void disableInput () {
        jbAgilityMaps.setEnabled(false);
        jbSpiderWeb.setEnabled(false);
        jbExport.setEnabled(false);
    }

    private void reconfigureAgilityFrames() {
        //
        m_agilityMap.setVisible(false);
        m_agilityMap.setParameters(m_experimentDataList);
        m_agilityMap.redrawPlot();
        //
    }

    private void setFileSelected () {
        if (m_selectedFile==null) {
            jlFileSelected.setText(EMPTY_FILE);
        }
        else {
            jlFileSelected.setText(m_selectedFile.getAbsolutePath());
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();
        jlFrameName = new javax.swing.JLabel();
        jlLogo = new javax.swing.JLabel();
        jlFile = new javax.swing.JLabel();
        jlFileSelected = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jbAgilityMaps = new javax.swing.JButton();
        jbSpiderWeb = new javax.swing.JButton();
        jbExport = new javax.swing.JButton();

        jFileChooser.setDialogTitle("Choose File");

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlFrameName.setBackground(new java.awt.Color(204, 204, 204));
        jlFrameName.setFont(new java.awt.Font("Arial", 1, 14));
        jlFrameName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFrameName.setText("Agility Maps");
        jlFrameName.setOpaque(true);
        getContentPane().add(jlFrameName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 310, 40));

        jlLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agility_advantage.jpg"))); // NOI18N
        jlLogo.setBounds(new java.awt.Rectangle(0, 0, 0, 10));
        getContentPane().add(jlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, 40));

        jlFile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFile.setText("CSV File:");
        getContentPane().add(jlFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 60, 20));

        jlFileSelected.setBackground(new java.awt.Color(204, 204, 204));
        jlFileSelected.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        jlFileSelected.setText("  <select file>");
        jlFileSelected.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlFileSelected.setOpaque(true);
        jlFileSelected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlFileSelectedMouseClicked(evt);
            }
        });
        getContentPane().add(jlFileSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 310, 20));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Charts and Measurement Resuls"));

        jbAgilityMaps.setText("Agility Maps");
        jbAgilityMaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgilityMapsActionPerformed(evt);
            }
        });

        jbSpiderWeb.setText("Spider Web Results");
        jbSpiderWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSpiderWebActionPerformed(evt);
            }
        });

        jbExport.setText("Export to CSV");
        jbExport.setActionCommand("Export to CSV");
        jbExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jbExport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jbSpiderWeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jbAgilityMaps, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jbAgilityMaps, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbSpiderWeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbExport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 350, 190));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSpiderWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSpiderWebActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbSpiderWebActionPerformed

    private void jbAgilityMapsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgilityMapsActionPerformed
        if (!m_agilityMap.isVisible())
            m_agilityMap.setVisible(true);
        m_agilityMap.toFront();
    }//GEN-LAST:event_jbAgilityMapsActionPerformed

    private void jlFileSelectedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlFileSelectedMouseClicked
        int returnVal = jFileChooser.showOpenDialog(this);
        if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION )
        {
            m_selectedFile = jFileChooser.getSelectedFile();
            //parse file - of OK then accept
            try {
                m_experimentDataList.clear();
                m_agilityMap.setVisible(false);
                m_agilityMap.dispose();
                m_agilityMap = new JFAgilityMapChart();
                //
                ProcessAgilityFile.processFile(m_selectedFile, m_experimentDataList);
                reconfigureAgilityFrames();
                enableInput();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error processing file:\n"+m_selectedFile.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                m_selectedFile = null;
                disableInput();
            }
            //
            setFileSelected();            
        }
    }//GEN-LAST:event_jlFileSelectedMouseClicked

    private void jbExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportActionPerformed
        String path = m_selectedFile.getParent();
        try {
            ExportAgilityToCSV.ExportToCSVGlobal(path, m_experimentDataList);
            JOptionPane.showMessageDialog(this, "File exported to:\n"+path, "File Exported", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting to CSV file", "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                m_selectedFile = null;
                disableInput();
        }
    }//GEN-LAST:event_jbExportActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFAgility().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbAgilityMaps;
    private javax.swing.JButton jbExport;
    private javax.swing.JButton jbSpiderWeb;
    private javax.swing.JLabel jlFile;
    private javax.swing.JLabel jlFileSelected;
    private javax.swing.JLabel jlFrameName;
    private javax.swing.JLabel jlLogo;
    // End of variables declaration//GEN-END:variables

}
