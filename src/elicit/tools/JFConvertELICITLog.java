/*
 * JFConvertELICITLog.java
 *
 * Created on 16 August 2007, 23:53
 */

package elicit.tools;

import elicit.message.Message;
import elicit.message.MessageListener;
import elicit.tools.ReadELICITLog;
import java.awt.Dialog;
import java.awt.Dimension;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.ItemEvent;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 *
 * @author  Marco
 */
public class JFConvertELICITLog extends javax.swing.JFrame {

    static int COMBO_BOX_MAX_FILES = 10;
    
    Properties m_properties;
    static String m_propertiesFileName = "convertELICITlog.properties";
    static String m_propertiesStoreComment = "ELICIT tool properties file";

    //last input files
    static String m_inputFilesKey             = "inputFilesKey";
    Vector<String> m_inputFilesValues;
    //
    static String m_keyOutputSuffixKey        = "outputSufixKey";
    String m_keyOutputSuffixValue;
    static String m_keyOverallOutputSuffixKey = "overallOutputSufixKey";
    String m_keyOverallOutputSuffixValue;
    static String m_keyVNAOutputSuffixKey     = "VNAOutputSufixKey";
    String m_keyVNAOutputSuffixValue;
    static String m_keyIdSuffixKey            = "idSuffixKey";
    String m_keyIdSuffixValue;
    static String m_keyInfoQKey               = "infoQSuffixKey";
    String m_keyInfoQSuffixValue;

    //objects used to read and convert log
    static String m_idIextKey                 = "idTextKey";
    String m_identifyText = null;

    ReadELICITLog m_reader = null;
    ConvertELICITLog m_convertLog = null;
    FixIDDialog dialog = null;
    //JFFixIdentify m_fixIDWindow = null;
    
    //inner class for message listener
    class LocalMessageListener implements MessageListener {
        JFrame m_parent;
        LocalMessageListener(JFrame parent_p) {
            m_parent = parent_p;
        }
        public void OnStart() {
            m_convertLog.OnStart();
        }

        public void OnNewMessage(Message message_p) {
            m_convertLog.OnNewMessage(message_p);
        }

        public void OnFinish() {
            m_convertLog.OnFinish();
        }        
    }
    LocalMessageListener m_localMessageListener;
    
    /** Creates new form JFConvertELICITLog */
    public JFConvertELICITLog() throws IOException {

        m_localMessageListener = new LocalMessageListener(this);
        //initialize preferences
        readProperties();
        
        //handle UI
        tools.UItools.UITools.setNativeLookAndFeel();
        initComponents();
        
        //post processing
        updateFields();
    }
    
    private String getSelectedFilename() {        
        if (jcbInputFile.getSelectedIndex()!=-1) {
            return ((File)jcbInputFile.getSelectedItem()).getName();
        }
        return null;
    }
    
    private String addSuffixToFilename(String filename, String suffix) {        
        if (filename!=null) {
            return filename.substring(0, filename.lastIndexOf('.')) + suffix + filename.substring(filename.lastIndexOf('.'),filename.length());
        }
        else {
            return null;
        }
    }
    
    private void updateFields() {
        jlFileOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfFileOutputSuffix.getText()));
        jlFileOutputOverallResult.setText(addSuffixToFilename(getSelectedFilename(),jtfOverallFileOutputSuffix.getText()));
        jlVNAFileOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfVNAFileOutputSuffix.getText()));
        jlIDOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfIDSuffix.getText()));
        jlInfoQOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfInfoQSuffix.getText()));        
    }
    
    private void readProperties() throws IOException {
        m_properties = new Properties();
        try {
            //read properties file (any data?)
            m_properties.loadFromXML(new FileInputStream(m_propertiesFileName));

            XStream xstream = new XStream(new DomDriver());
            String xml;
            xml = m_properties.getProperty(m_inputFilesKey);
            m_inputFilesValues = (Vector<String>)xstream.fromXML(xml);
            //ID text
            m_identifyText = m_properties.getProperty(m_idIextKey);
            //
            //jtfFileOutputSuffix.setText(m_properties.getProperty(m_keyOutputSuffixKey));
            //
            //jtfOverallFileOutputSuffix.setText(m_properties.getProperty(m_keyOverallOutputSuffixKey));
            //
            //jtfVNAFileOutputSuffix.setText(m_properties.getProperty(m_keyVNAOutputSuffixKey));
        }
        catch (IOException ex) {
            //no properties? create
            m_properties.storeToXML(new FileOutputStream(m_propertiesFileName), m_propertiesStoreComment);
        }
        //initialize lists 
        if (m_inputFilesValues==null) {
            m_inputFilesValues = new Vector<String>();
        }
    }
    
    private void writeProperties() throws IOException {
        XStream xstream = new XStream(new DomDriver());
        String xml;
        //write properties
        //excel
        xml = xstream.toXML(m_inputFilesValues);
        m_properties.setProperty(m_inputFilesKey,xml);
        //excel
        m_properties.setProperty(m_keyOutputSuffixKey,jtfFileOutputSuffix.getText());
        //overall
        m_properties.setProperty(m_keyOverallOutputSuffixKey,jtfOverallFileOutputSuffix.getText());
        //VNA
        m_properties.setProperty(m_keyVNAOutputSuffixKey,jtfVNAFileOutputSuffix.getText());
        //ID 
        m_properties.setProperty(m_keyIdSuffixKey,jtfIDSuffix.getText());
        //info
        m_properties.setProperty(m_keyInfoQKey,jtfInfoQSuffix.getText());
        //ID text
        if (m_identifyText!=null) {
            m_properties.setProperty(m_idIextKey, m_identifyText);
        }
        //write file
        m_properties.storeToXML(new FileOutputStream(m_propertiesFileName), m_propertiesStoreComment);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpHeader = new javax.swing.JPanel();
        jlImage = new javax.swing.JLabel();
        jlTitle = new javax.swing.JLabel();
        jpOptions = new javax.swing.JPanel();
        jpFileInput = new javax.swing.JPanel();
        jlFileInput = new javax.swing.JLabel();
        jcbInputFile = new JComboBox(m_inputFilesValues);
        jbFileInputBrowse = new javax.swing.JButton();
        jpOutputfileOptions = new javax.swing.JPanel();
        jpFileOutputSuffix = new javax.swing.JPanel();
        jlFileOutputSuffix = new javax.swing.JLabel();
        jtfFileOutputSuffix = new javax.swing.JTextField();
        jtfFileOutputSuffix.setText(m_properties.getProperty(m_keyOutputSuffixKey));
        jlFileOutputResult = new javax.swing.JLabel();
        jpOverallFileOutputSuffix = new javax.swing.JPanel();
        jlOverallFileOutputSuffix = new javax.swing.JLabel();
        jtfOverallFileOutputSuffix = new javax.swing.JTextField();
        jlFileOutputOverallResult = new javax.swing.JLabel();
        jpVNAFileOutputSuffix = new javax.swing.JPanel();
        jlVNAFileOutputSuffix = new javax.swing.JLabel();
        jtfVNAFileOutputSuffix = new javax.swing.JTextField();
        jlVNAFileOutputResult = new javax.swing.JLabel();
        jpFixIds = new javax.swing.JPanel();
        jlFixIds = new javax.swing.JLabel();
        jcbIds = new javax.swing.JCheckBox();
        jpIDs = new javax.swing.JPanel();
        jlIDLabel = new javax.swing.JLabel();
        jtfIDSuffix = new javax.swing.JTextField();
        jlIDOutputResult = new javax.swing.JLabel();
        jpInfoQ = new javax.swing.JPanel();
        jlInfoQLabel = new javax.swing.JLabel();
        jtfInfoQSuffix = new javax.swing.JTextField();
        jlInfoQOutputResult = new javax.swing.JLabel();
        jpFooter = new javax.swing.JPanel();
        jbGenerate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ELICIT LOG Converter Tool");
        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlImage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/buildingblocks.gif"))); // NOI18N
        jlImage.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlImage.setRequestFocusEnabled(false);
        jpHeader.add(jlImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jlTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jlTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitle.setText("ELICIT LOG Converter Tool");
        jlTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpHeader.add(jlTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 420, 60));

        getContentPane().add(jpHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 501, -1));

        jpOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Log file data")));
        jpOptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlFileInput.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFileInput.setText("Input File");
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

        jpFileOutputSuffix.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlFileOutputSuffix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFileOutputSuffix.setText("Excel");
        jlFileOutputSuffix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlFileOutputSuffix.setPreferredSize(new java.awt.Dimension(150, 20));
        jpFileOutputSuffix.add(jlFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

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
        jpFileOutputSuffix.add(jtfFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 150, -1));

        jlFileOutputResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlFileOutputResult.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlFileOutputResult.setPreferredSize(new java.awt.Dimension(150, 20));
        jpFileOutputSuffix.add(jlFileOutputResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 190, -1));

        jpOutputfileOptions.add(jpFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 460, 30));

        jpOverallFileOutputSuffix.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlOverallFileOutputSuffix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlOverallFileOutputSuffix.setText("Overall Metrics");
        jlOverallFileOutputSuffix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlOverallFileOutputSuffix.setPreferredSize(new java.awt.Dimension(150, 20));
        jpOverallFileOutputSuffix.add(jlOverallFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        jtfOverallFileOutputSuffix.setPreferredSize(new java.awt.Dimension(100, 20));
        jtfOverallFileOutputSuffix.setText(m_properties.getProperty(m_keyOverallOutputSuffixKey));
        jtfOverallFileOutputSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfOverallFileOutputSuffixActionPerformed(evt);
            }
        });
        jtfOverallFileOutputSuffix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfOverallFileOutputSuffixFocusLost(evt);
            }
        });
        jpOverallFileOutputSuffix.add(jtfOverallFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 150, -1));

        jlFileOutputOverallResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlFileOutputOverallResult.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlFileOutputOverallResult.setPreferredSize(new java.awt.Dimension(150, 20));
        jpOverallFileOutputSuffix.add(jlFileOutputOverallResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 190, -1));

        jpOutputfileOptions.add(jpOverallFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 460, 30));

        jpVNAFileOutputSuffix.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlVNAFileOutputSuffix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlVNAFileOutputSuffix.setText("VNA Metrics");
        jlVNAFileOutputSuffix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlVNAFileOutputSuffix.setPreferredSize(new java.awt.Dimension(150, 20));
        jpVNAFileOutputSuffix.add(jlVNAFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, -1));

        jtfVNAFileOutputSuffix.setPreferredSize(new java.awt.Dimension(100, 20));
        jtfVNAFileOutputSuffix.setText(m_properties.getProperty(m_keyVNAOutputSuffixKey));
        jtfVNAFileOutputSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfVNAFileOutputSuffixActionPerformed(evt);
            }
        });
        jtfVNAFileOutputSuffix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfVNAFileOutputSuffixFocusLost(evt);
            }
        });
        jpVNAFileOutputSuffix.add(jtfVNAFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 150, -1));

        jlVNAFileOutputResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlVNAFileOutputResult.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlVNAFileOutputResult.setPreferredSize(new java.awt.Dimension(150, 20));
        jpVNAFileOutputSuffix.add(jlVNAFileOutputResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 190, -1));

        jpOutputfileOptions.add(jpVNAFileOutputSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 460, 30));

        jpFixIds.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlFixIds.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFixIds.setText("Fix Identifies");
        jlFixIds.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlFixIds.setMaximumSize(null);
        jlFixIds.setMinimumSize(null);
        jlFixIds.setPreferredSize(new java.awt.Dimension(150, 10));
        jpFixIds.add(jlFixIds, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, 20));

        jcbIds.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jcbIds.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jpFixIds.add(jcbIds, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        jpOutputfileOptions.add(jpFixIds, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 460, 30));

        jpIDs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlIDLabel.setText("Identification");
        jlIDLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlIDLabel.setPreferredSize(new java.awt.Dimension(150, 20));
        jpIDs.add(jlIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, -1));

        jtfIDSuffix.setPreferredSize(new java.awt.Dimension(100, 20));
        jtfIDSuffix.setText(m_properties.getProperty(m_keyIdSuffixKey));
        jtfIDSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfIDSuffixActionPerformed(evt);
            }
        });
        jtfIDSuffix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfIDSuffixFocusLost(evt);
            }
        });
        jpIDs.add(jtfIDSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 150, -1));

        jlIDOutputResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlIDOutputResult.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlIDOutputResult.setPreferredSize(new java.awt.Dimension(150, 20));
        jpIDs.add(jlIDOutputResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 190, -1));

        jpOutputfileOptions.add(jpIDs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 460, 30));

        jpInfoQ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlInfoQLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlInfoQLabel.setText("Information Q");
        jlInfoQLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlInfoQLabel.setPreferredSize(new java.awt.Dimension(150, 20));
        jpInfoQ.add(jlInfoQLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, -1));

        jtfInfoQSuffix.setPreferredSize(new java.awt.Dimension(100, 20));
        jtfInfoQSuffix.setText(m_properties.getProperty(m_keyInfoQKey));
        jtfInfoQSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfInfoQSuffixActionPerformed(evt);
            }
        });
        jtfInfoQSuffix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfInfoQSuffixFocusLost(evt);
            }
        });
        jpInfoQ.add(jtfInfoQSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 150, -1));

        jlInfoQOutputResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlInfoQOutputResult.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlInfoQOutputResult.setPreferredSize(new java.awt.Dimension(150, 20));
        jpInfoQ.add(jlInfoQOutputResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 190, -1));

        jpOutputfileOptions.add(jpInfoQ, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 460, 30));

        jpOptions.add(jpOutputfileOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 480, 210));

        getContentPane().add(jpOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 501, 290));

        jpFooter.setMaximumSize(null);

        jbGenerate.setText("GENERATE");
        jbGenerate.setPreferredSize(new java.awt.Dimension(100, 25));
        jbGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerateActionPerformed(evt);
            }
        });
        jpFooter.add(jbGenerate);

        getContentPane().add(jpFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 501, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfInfoQSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfInfoQSuffixFocusLost
        jlInfoQOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfInfoQSuffix.getText()));
    }//GEN-LAST:event_jtfInfoQSuffixFocusLost

    private void jtfInfoQSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfInfoQSuffixActionPerformed
        jlInfoQOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfInfoQSuffix.getText()));
    }//GEN-LAST:event_jtfInfoQSuffixActionPerformed

    private void jtfIDSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfIDSuffixFocusLost
        jlIDOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfIDSuffix.getText()));
    }//GEN-LAST:event_jtfIDSuffixFocusLost

    private void jtfIDSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfIDSuffixActionPerformed
        jlIDOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfIDSuffix.getText()));
    }//GEN-LAST:event_jtfIDSuffixActionPerformed

    private void jbGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerateActionPerformed
        //call working class: 
        File file = (File)jcbInputFile.getSelectedItem();
        if ( file != null && file.isFile() ) {
            //check sufixes
            //1. nulls are not allowed
            if ( jtfFileOutputSuffix.getText() != null
                 && !jtfFileOutputSuffix.getText().equals("")
                 && jtfOverallFileOutputSuffix.getText() != null
                 && !jtfOverallFileOutputSuffix.getText().equals("")
                 //
                 && jtfIDSuffix.getText() != null 
                 && !jtfIDSuffix.getText().equals("") 
                 && jtfInfoQSuffix.getText() != null 
                 && !jtfInfoQSuffix.getText().equals("") 
                 //
                 && jtfVNAFileOutputSuffix.getText() != null 
                 && !jtfVNAFileOutputSuffix.getText().equals("") ) {

                //2. must be different
                if ( !jlFileOutputResult.getText().equals(jlFileOutputOverallResult.getText()) 
                     && !jlFileOutputResult.getText().equals(jlVNAFileOutputResult.getText())
                     && !jlFileOutputResult.getText().equals(jlIDOutputResult.getText()) 
                     && !jlFileOutputResult.getText().equals(jlInfoQOutputResult.getText()) ) {
                    
                    try {
                        //create message event generator
                        m_reader = new ReadELICITLog(file.getAbsolutePath());
                        m_convertLog = new ConvertELICITLog();
                        m_convertLog.setLogOutputFilename(file.getParent()+file.separatorChar+jlFileOutputResult.getText());
                        m_convertLog.setOverallMetricsOutputFilename(file.getParent()+file.separatorChar+jlFileOutputOverallResult.getText());
                        m_convertLog.setVnaSubjectAndSiteOutputFilename(file.getParent()+file.separatorChar+jlVNAFileOutputResult.getText());
                        m_convertLog.setIDOutputFilename(file.getParent()+file.separatorChar+jlIDOutputResult.getText());
                        m_convertLog.setInfoQOutputFilename(file.getParent()+file.separatorChar+jlInfoQOutputResult.getText());
                        
                        if (jcbIds.isSelected()) {
                            m_reader.addMessageListener(m_localMessageListener);
                            m_reader.doIt();
                        }
                        else {
                            m_reader.addMessageListener(m_convertLog);
                            m_reader.doIt();
                        }
                        //
                        JOptionPane.showMessageDialog(this, "Conversion successful.");
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,"An error occured when processing ELICIT LOG.","Convertion error",JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
                else {
                    //pop-up error window
                    JOptionPane.showMessageDialog(this,"Please select different sufixes for the output files.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                //pop-up error window
                JOptionPane.showMessageDialog(this,"Please select valid sufixes for the output files.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            //pop-up error window
            JOptionPane.showMessageDialog(this,"Please select a valid LOG file.","Error",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jbGenerateActionPerformed

    private void jtfVNAFileOutputSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfVNAFileOutputSuffixFocusLost
        jlVNAFileOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfVNAFileOutputSuffix.getText()));
    }//GEN-LAST:event_jtfVNAFileOutputSuffixFocusLost

    private void jtfOverallFileOutputSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfOverallFileOutputSuffixFocusLost
        jlFileOutputOverallResult.setText(addSuffixToFilename(getSelectedFilename(),jtfOverallFileOutputSuffix.getText()));
    }//GEN-LAST:event_jtfOverallFileOutputSuffixFocusLost

    private void jtfFileOutputSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFileOutputSuffixFocusLost
        jlFileOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfFileOutputSuffix.getText()));
    }//GEN-LAST:event_jtfFileOutputSuffixFocusLost

    private void jtfVNAFileOutputSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfVNAFileOutputSuffixActionPerformed
        jlVNAFileOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfVNAFileOutputSuffix.getText()));
    }//GEN-LAST:event_jtfVNAFileOutputSuffixActionPerformed

    private void jtfOverallFileOutputSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfOverallFileOutputSuffixActionPerformed
        jlFileOutputOverallResult.setText(addSuffixToFilename(getSelectedFilename(),jtfOverallFileOutputSuffix.getText()));
    }//GEN-LAST:event_jtfOverallFileOutputSuffixActionPerformed

    private void jtfFileOutputSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfFileOutputSuffixActionPerformed
        jlFileOutputResult.setText(addSuffixToFilename(getSelectedFilename(),jtfFileOutputSuffix.getText()));
    }//GEN-LAST:event_jtfFileOutputSuffixActionPerformed

    private void jbFileInputBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFileInputBrowseActionPerformed
        JFileChooser chooser;
        if (jcbInputFile.getSelectedItem()!=null) {
            chooser = new JFileChooser((File)jcbInputFile.getSelectedItem());
        }
        else {
            chooser = new JFileChooser();
        }
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ELICIT LOG files", "txt", "log");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            //update in combo box
            if (chooser.getSelectedFile()!=null) {
                //only add if not already there
                //if (! jcbInputFile.get(chooser.getSelectedFile()) ) {
                    //do not allow more than MAX files
                    if (jcbInputFile.getItemCount()>=COMBO_BOX_MAX_FILES) {
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

    private void jcbInputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbInputFileActionPerformed
        //new selection
        if (jcbInputFile.getSelectedIndex() != -1 ) {
            //update other fields
            updateFields();
        }
    }//GEN-LAST:event_jcbInputFileActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            writeProperties();
        }
        catch (IOException ex) {
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
                    JFConvertELICITLog frame = new JFConvertELICITLog();
                    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbFileInputBrowse;
    private javax.swing.JButton jbGenerate;
    private javax.swing.JCheckBox jcbIds;
    private javax.swing.JComboBox jcbInputFile;
    private javax.swing.JLabel jlFileInput;
    private javax.swing.JLabel jlFileOutputOverallResult;
    private javax.swing.JLabel jlFileOutputResult;
    private javax.swing.JLabel jlFileOutputSuffix;
    private javax.swing.JLabel jlFixIds;
    private javax.swing.JLabel jlIDLabel;
    private javax.swing.JLabel jlIDOutputResult;
    private javax.swing.JLabel jlImage;
    private javax.swing.JLabel jlInfoQLabel;
    private javax.swing.JLabel jlInfoQOutputResult;
    private javax.swing.JLabel jlOverallFileOutputSuffix;
    private javax.swing.JLabel jlTitle;
    private javax.swing.JLabel jlVNAFileOutputResult;
    private javax.swing.JLabel jlVNAFileOutputSuffix;
    private javax.swing.JPanel jpFileInput;
    private javax.swing.JPanel jpFileOutputSuffix;
    private javax.swing.JPanel jpFixIds;
    private javax.swing.JPanel jpFooter;
    private javax.swing.JPanel jpHeader;
    private javax.swing.JPanel jpIDs;
    private javax.swing.JPanel jpInfoQ;
    private javax.swing.JPanel jpOptions;
    private javax.swing.JPanel jpOutputfileOptions;
    private javax.swing.JPanel jpOverallFileOutputSuffix;
    private javax.swing.JPanel jpVNAFileOutputSuffix;
    private javax.swing.JTextField jtfFileOutputSuffix;
    private javax.swing.JTextField jtfIDSuffix;
    private javax.swing.JTextField jtfInfoQSuffix;
    private javax.swing.JTextField jtfOverallFileOutputSuffix;
    private javax.swing.JTextField jtfVNAFileOutputSuffix;
    // End of variables declaration//GEN-END:variables
    
}
