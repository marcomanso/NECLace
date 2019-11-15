/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFOutputIdentifies.java
 *
 * Created on 28/Abr/2009, 20:56:17
 */
package elicit.tools;

import elicit.tools.*;
import tools.FileStructure.Utils;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import elicit.message.Message;
import elicit.message.MessageListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mmanso
 */
public class JFOutputIdentifyAnswers extends javax.swing.JFrame {

    static int COMBO_BOX_MAX_FILES = 10;
    Properties m_properties;
    static String m_propertiesFileName = "outputIdentifies.properties";
    static String m_propertiesStoreComment = "ELICIT identifies properties file";

    //last input files
    static String m_inputFilesKey = "inputFilesKey";
    Vector<String> m_inputFilesValues;
    //
    static String m_keyOutputSuffixKey = "outputSufixKey";
    String m_keyOutputSuffixValue;
    ReadELICITLog m_reader = null;
    FileWriter m_writer = null;

    //inner class for message listener
    class LocalMessageListener implements MessageListener {

        JFrame m_parent;

        LocalMessageListener(JFrame parent_p) {
            m_parent = parent_p;
        }

        @Override
        public void OnStart() {
            try {
                //HEADING
                m_writer.write("Filename: " + getSelectedFilename() + "\n");
                m_writer.write("Entry nbr\tAlias name\tID Answer\tWho\tWhat\tWhere\tDay\tMonth\tHour\tAM/PM\n");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void OnNewMessage(Message message_p) {
            if (message_p.m_messageType.equals(Message.MESSAGE_TYPE_IDENTIFY))
            {
                try {
                    //m_writer.write(message_p.m_commonData.sequence+"\t" + message_p.m_data + "\t"+ message_p.m_data2+"\n");
                    m_writer.write(message_p.m_logLine+"\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void OnFinish() {
            try {
                if (m_writer != null) {
                    m_writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    LocalMessageListener m_localMessageListener;

    /** Creates new form JFOutputIdentifies */
    public JFOutputIdentifyAnswers() throws IOException {
        m_localMessageListener = new LocalMessageListener(this);

        //initialize preferences
        readProperties();

        //handle UI
        tools.UItools.UITools.setNativeLookAndFeel();
        initComponents();

        //post processing
        updateFields();
    }

    private void readProperties() throws IOException {
        m_properties = new Properties();
        try {
            //read properties file (any data?)
            m_properties.loadFromXML(new FileInputStream(m_propertiesFileName));
            XStream xstream = new XStream(new DomDriver());
            String xml;
            //input files list
            xml = m_properties.getProperty(m_inputFilesKey);
            m_inputFilesValues = (Vector<String>) xstream.fromXML(xml);
            //output suffix
            m_keyOutputSuffixValue = m_properties.getProperty(m_keyOutputSuffixKey);
        } catch (IOException ex) {
            //no properties? create
            m_properties.storeToXML(new FileOutputStream(m_propertiesFileName), m_propertiesStoreComment);
        }
        //initialize lists
        if (m_inputFilesValues == null) {
            m_inputFilesValues = new Vector<String>();
        }
    }

    private void writeProperties() throws IOException {
        XStream xstream = new XStream(new DomDriver());
        String xml;
        //write properties
        //files
        xml = xstream.toXML(m_inputFilesValues);
        m_properties.setProperty(m_inputFilesKey, xml);
        //suffix
        m_properties.setProperty(m_keyOutputSuffixKey, jtfFileOutputSuffix.getText());

        //write file
        m_properties.storeToXML(new FileOutputStream(m_propertiesFileName), m_propertiesStoreComment);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpOptions = new javax.swing.JPanel();
        jpFileInput = new javax.swing.JPanel();
        jlFileInput = new javax.swing.JLabel();
        jcbInputFile = new JComboBox(m_inputFilesValues);
        jbFileInputBrowse = new javax.swing.JButton();
        jpOutputfileOptions = new javax.swing.JPanel();
        jlFileOutputSuffix = new javax.swing.JLabel();
        jtfFileOutputSuffix = new javax.swing.JTextField();
        jtfFileOutputSuffix.setText(m_properties.getProperty(m_keyOutputSuffixKey));
        jlFileOutputResult = new javax.swing.JLabel();
        jbGO = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Output Identifiy Answers");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jpOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Input file")));
        jpOptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlFileInput.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFileInput.setText("Filename");
        jlFileInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlFileInput.setPreferredSize(new java.awt.Dimension(70, 20));
        jpFileInput.add(jlFileInput);

        jcbInputFile.setPreferredSize(new java.awt.Dimension(310, 20));
        jcbInputFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbInputFileActionPerformed(evt);
            }
        });
        jpFileInput.add(jcbInputFile);

        jbFileInputBrowse.setText("Browse");
        jbFileInputBrowse.setPreferredSize(new java.awt.Dimension(70, 20));
        jbFileInputBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFileInputBrowseActionPerformed(evt);
            }
        });
        jpFileInput.add(jbFileInputBrowse);

        jpOptions.add(jpFileInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 31, 470, -1));

        jpOutputfileOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Output file sufixes"));
        jpOutputfileOptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlFileOutputSuffix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFileOutputSuffix.setText("Suffix");
        jlFileOutputSuffix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlFileOutputSuffix.setPreferredSize(new java.awt.Dimension(150, 20));
        jpOutputfileOptions.add(jlFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, -1));

        jtfFileOutputSuffix.setPreferredSize(new java.awt.Dimension(100, 20));
        jtfFileOutputSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfFileOutputSuffixActionPerformed(evt);
            }
        });
        jtfFileOutputSuffix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFileOutputSuffixFocusLost(evt);
            }
        });
        jpOutputfileOptions.add(jtfFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 80, -1));

        jlFileOutputResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlFileOutputResult.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlFileOutputResult.setPreferredSize(new java.awt.Dimension(150, 20));
        jpOutputfileOptions.add(jlFileOutputResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 290, -1));

        jbGO.setText("GO !");
        jbGO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGOActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpOutputfileOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbGO)
                        .addGap(220, 220, 220))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jpOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpOutputfileOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbGO)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateFields() {
        jlFileOutputResult.setText(Utils.addSuffixToFilename(getSelectedFilename(), jtfFileOutputSuffix.getText()));
    }

    private String getSelectedFilename() {
        if (jcbInputFile.getSelectedIndex() != -1) {
            return ((File) jcbInputFile.getSelectedItem()).getName();
        }
        return null;
    }

    private void jcbInputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbInputFileActionPerformed
        //new selection
        if (jcbInputFile.getSelectedIndex() != -1) {
            //update other fields
            updateFields();
        }
}//GEN-LAST:event_jcbInputFileActionPerformed

    private void jbFileInputBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFileInputBrowseActionPerformed
        JFileChooser chooser;
        if (jcbInputFile.getSelectedItem() != null) {
            chooser = new JFileChooser((File) jcbInputFile.getSelectedItem());
        } else {
            chooser = new JFileChooser();
        }
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ELICIT LOG files", "txt", "log");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //update in combo box
            if (chooser.getSelectedFile() != null) {
                //only add if not already there
                //if (! jcbInputFile.get(chooser.getSelectedFile()) ) {
                //do not allow more than MAX files
                if (jcbInputFile.getItemCount() >= COMBO_BOX_MAX_FILES) {
                    //delete oldest
                    jcbInputFile.removeItemAt(0);
                }
                //add to list
                jcbInputFile.addItem(chooser.getSelectedFile());
                //}
                //select it
                jcbInputFile.setSelectedItem(chooser.getSelectedFile());
            }
        }
        updateFields();
}//GEN-LAST:event_jbFileInputBrowseActionPerformed

    private void jtfFileOutputSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFileOutputSuffixFocusLost
        jlFileOutputResult.setText(Utils.addSuffixToFilename(getSelectedFilename(), jtfFileOutputSuffix.getText()));
}//GEN-LAST:event_jtfFileOutputSuffixFocusLost

    private void jtfFileOutputSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfFileOutputSuffixActionPerformed
        jlFileOutputResult.setText(Utils.addSuffixToFilename(getSelectedFilename(), jtfFileOutputSuffix.getText()));
}//GEN-LAST:event_jtfFileOutputSuffixActionPerformed

    private void jbGOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGOActionPerformed
        //call working class:
        File file = (File) jcbInputFile.getSelectedItem();
        if (file != null && file.isFile()) {
            //check sufixes
            //1. nulls are not allowed
            if (jtfFileOutputSuffix.getText() != null && !jtfFileOutputSuffix.getText().equals("")) {
                try {
                    m_writer = new FileWriter(file.getParent() + File.separatorChar + jlFileOutputResult.getText());
                    //create message event generator
                    m_reader = new ReadELICITLog(file.getAbsolutePath());
                    m_reader.addMessageListener(m_localMessageListener);
                    m_reader.doIt();
                    //
                    JOptionPane.showMessageDialog(this, "Conversion successful.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "An error occured when processing ELICIT LOG.", "Convertion error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                //pop-up error window
                JOptionPane.showMessageDialog(this, "Please select different sufixes for the output files.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //pop-up error window
            JOptionPane.showMessageDialog(this, "Please select a valid LOG file.", "Error", JOptionPane.ERROR_MESSAGE);
        }

}//GEN-LAST:event_jbGOActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            writeProperties();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new JFOutputIdentifyAnswers().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(JFOutputIdentifyAnswers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbFileInputBrowse;
    private javax.swing.JButton jbGO;
    private javax.swing.JComboBox jcbInputFile;
    private javax.swing.JLabel jlFileInput;
    private javax.swing.JLabel jlFileOutputResult;
    private javax.swing.JLabel jlFileOutputSuffix;
    private javax.swing.JPanel jpFileInput;
    private javax.swing.JPanel jpOptions;
    private javax.swing.JPanel jpOutputfileOptions;
    private javax.swing.JTextField jtfFileOutputSuffix;
    // End of variables declaration//GEN-END:variables
}
