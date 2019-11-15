/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFELICITMetricsVisualizer.java
 *
 * Created on 29/Abr/2009, 12:18:54
 */
package elicit.tools;

import elicit.charts.*;
import elicit.message.Message;
import elicit.message.MessageListener;
import elicit.message.TrialData;
import elicit.message.FactoidMessage;
import metrics.interaction.QualityOfInteractions.QualityOfInteraction;
import metrics.interaction.QualityOfInteractions;
import metrics.awareness.IDsQualityMap.AwUndData;

import java.util.TreeMap;
import java.util.Vector;
import java.awt.geom.Point2D;
import javax.swing.JDialog;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import metrics.awareness.AwarenessQualityMap.AwarenessQualityData;

/**
 *
 * @author mmanso
 */
public class JFELICITMetricsVisualizer extends javax.swing.JFrame {

    public TrialData m_trialData = new TrialData();
    public String m_logFile;
    JDialog m_jd = new JDialog();

    JFSubjectsFactoidsTable jFFactoidsTableInformationAccessibleByServer = null;
    JFSubjectsFactoidsInformationReachTable jFFactoidsTableInformationAccessible = null;
    JFSubjectsFactoidsInformationReachedTable jFSubjectsFactoidsInformationReachedTable = null;
    JFSubjectsFactoidsSharesAndPostsTable jFFactoidsTableShares = null;
    JFSubjectsFactoidsSharesAndPostsTable jFFactoidsTablePosts = null;
    JFSubjectsFactoidsSharesAndPostsTable jFFactoidsTableSharesPosts = null;
    JFBarIntegerCategory jfFormsInterationsBar = null;
    //
    JFBarIntegerCategory jfInboundBar = null;
    JFBarIntegerCategory jfOutboundBar = null;
    //
    JFFactoidsActivity jFFactoidsActivity = null;
    JFBarIntegerCategory jFFactoidsActivityBar = null;
    //
    JFSubjectsCriticalInformationTable jFSubjectsCriticalInformationTable = null;
    JFSubjectsCriticalInformationTable2 jFSubjectsCriticalInformationTable2 = null;
    //
    JF2DataSetsTimeChart jfInfoQAccessibleServer = null;
    //
    public JFSubjectsSocialNetworkTable jFSubjectsSocialNetworkTable = null;
    public JFTeamSocialNetworkTable jFTeamSocialNetworkTable = null;
    public JFSubjectsReciprocityRateTable jFSubjectsReciprocityTable = null;
    public JFSubjectsFactoidsSharingDelayTable jFSubjectsSharingDelayTable = null;
    //
    JFNodeBalanceTable jFNodeBalanceTable = null;
    JFSubjectsNatureInteractionsTable jFSubjectsNatureInteractionsTable = null;
    JFSubjectsQualityInteractionsTable jFSubjectsQualityInteractionsTable = null;
    JFScatterChart jFNodeBalanceChart = null;
    //
    JGSNA sna = null;
    JGSNA teamSna = null;
    //
    JFSimpleTimeChart jFQualityOfInteractionsShares = null;
    TreeMap<String, Vector<Point2D.Double>> m_qualityOfSharesMap = null;
    JFSimpleTimeChart jFQualityOfInteractionsPosts = null;
    TreeMap<String, Vector<Point2D.Double>> m_qualityOfPostsMap = null;
    JFSimpleTimeChart jFQualityOfInteractionsSharesAndPosts = null;
    TreeMap<String, Vector<Point2D.Double>> m_qualityOfSharesAndPostsMap = null;
    //
    JFBarIDDistribution jfIDBar = null;
    JFSubjectsUnderstandingTable jfSubjIDTable = null;
    JFSimpleTimeChart jFQualityOfIDs = null;
    TreeMap<String, Vector<Point2D.Double>> m_qualityOfIDsMap;
    //
    JFSubjectsFactoidsAwarenessQTable jfAwarenessQTable;
    JFSimpleTimeChart jFQualityOfAwareness = null;
    TreeMap<String, Vector<Point2D.Double>> m_qualityOfAwarenessMap = null;
    //
    JFSimpleTimeChart jFCorrectIDs = null;
    JFSimpleTimeChart jFPercentageCorrectIDs = null;
    TreeMap<String, Vector<Point2D.Double>> m_correctIDsMap = null;

    //
    public JFInformationTable jFInformationTable = null;
    public JFMoMSharedInformationAccessibleTable jFMoMSharedInformationAccessibleTable = null;
    public JFMoMSharedRelevantInformationAccessibleTable jFMoMSharedRelevantInformationAccessibleTable = null;
    public JFMoMSharedInformationAccessedTable jFMoMSharedInformationAccessedTable = null;
    public JFMoMSharedRelevantInformationAccessedTable jFMoMSharedRelevantInformationAccessedTable = null;
    public JFMoMAverageInformationAccessedTable jFMoMAverageInformationAccessedTable = null;
    public JFMoMIDScoresTable jFMoMIDScoresTable = null;
    public JFMoMCorrectSharedUnderstanding jFMoMCorrectSharedUnderstandingTable = null;
    public JFMoMCSSync jFMoMCSSync = null;
    public JFMoMEffectiveness jFMoMEffectiveness = null;
    //
    JFSimpleTimeChart jFSincronizationEntropy = null;
    TreeMap<String, Vector<Point2D.Double>> m_syncEntropyMap = new TreeMap<String, Vector<Point2D.Double>>();
    JFSystemUnderstandingEntropy jFEntropyTable = null;
    //
    JFSubjectsFactoidsHoardingTable m_hoardingTable = null;
    //
    NumberFormat m_formatter = new DecimalFormat ( "0.00" ) ;

    //__JFInformationAccessibilityTimeChart m_JFinfoQ;
    

    //inner class for message listener
    class LocalMessageListener implements MessageListener {

        @Override
        public void OnStart() {
        }

        @Override
        public void OnNewMessage(Message message_p) {
            //System.out.println("new message..."+message_p.Write());
            m_trialData.addMessage(message_p);
        }

        @Override
        public void OnFinish() {
//            System.out.print("Overall metrics:\n");
//            System.out.print("-duration(sec)="+m_trialData.m_trialInformation.m_durationSec);
//            System.out.print("-total IDs="+m_trialData.m_overallStatistics.totalIDs);
//            System.out.print("-total pulls="+m_trialData.m_overallStatistics.totalPulls);
//            System.out.print("-total posts="+m_trialData.m_overallStatistics.totalPosts);
//            System.out.print("-total shares="+m_trialData.m_overallStatistics.totalShares);
//            System.out.print("\n");
        }
    }
    LocalMessageListener m_localMessageListener;

    /** Creates new form JFELICITMetricsVisualizer */
    public JFELICITMetricsVisualizer(String logFile) {
        m_logFile = logFile;
        m_localMessageListener = new LocalMessageListener();
        //
        initComponents();
        //
        jlFileName.setText(m_logFile);
        // READ LOG
        ReadLog();
    }

    //
    // UTILS
    private void ReadLog() {
        ReadELICITLog readLog = new ReadELICITLog(m_logFile);
        readLog.addMessageListener(m_localMessageListener);
        readLog.doIt();
        //
        updateUI();
    }
    private static void ConvertQualityOfInterationsMaptoPoint2DMap (TreeMap<String, QualityOfInteractions> inputMap, TreeMap<String, Vector<Point2D.Double>> outputMap) {
        for ( String s : inputMap.keySet() ) {
            QualityOfInteractions qV = inputMap.get(s);
            Vector<Point2D.Double> v = new Vector<Point2D.Double>();
            for ( QualityOfInteraction q : qV ) {
                v.add(new Point2D.Double(q.time, q.commulativeValue));
            }
            outputMap.put(s,v);
        }
        //add overall
        
    }

    private static void ConvertQualityOfIDMaptoPoint2DMap (TreeMap<String, Vector<AwUndData>> inputMap, TreeMap<String, Vector<Point2D.Double>> outputMap) {
        for ( String s : inputMap.keySet() ) {
            Vector<AwUndData> qV = inputMap.get(s);
            Vector<Point2D.Double> v = new Vector<Point2D.Double>();
            for ( AwUndData q : qV ) {
                v.add(new Point2D.Double(q.time, q.overallQ));
            }
            outputMap.put(s,v);
        }
    }

    private static void ConvertQualityOfAwarenessMaptoPoint2DMap (TreeMap<String, Vector<AwarenessQualityData>> inputMap, TreeMap<String, Vector<Point2D.Double>> outputMap) {
        for ( String s : inputMap.keySet() ) {
            Vector<AwarenessQualityData> qV = inputMap.get(s);
            Vector<Point2D.Double> v = new Vector<Point2D.Double>();
            for ( AwarenessQualityData q : qV ) {
                v.add(new Point2D.Double(q.time, q.qComm));
            }
            outputMap.put(s,v);
        }
    }

    private void updateUI() {
        //
        jlOrganizationType.setText(m_trialData.m_organizationInformation.m_organizationName);
        jlFactoidSetID.setText(m_trialData.m_trialInformation.factoidSet);
        jlDate.setText(m_trialData.m_trialInformation.m_date);
        jlStart.setText(m_trialData.m_trialInformation.m_timeStart);
        jlFinish.setText(m_trialData.m_trialInformation.m_timeFinish);
        jlDuration.setText(Integer.toString(m_trialData.m_trialInformation.m_durationMin));
        //
        jlShares.setText(Integer.toString(m_trialData.m_overallStatistics.totalShares));
        jlPosts.setText(Integer.toString(m_trialData.m_overallStatistics.totalPosts));
        jlPulls.setText(Integer.toString(m_trialData.m_overallStatistics.totalPulls));
        jlIDs.setText(Integer.toString(m_trialData.m_overallStatistics.totalIDs));
        jlADDs.setText(Integer.toString(m_trialData.m_overallStatistics.totalADDs));
        //
        jlSharesHour.setText(m_formatter.format(m_trialData.m_overallStatistics.sharesHour));
        jlPostsHour.setText(m_formatter.format(m_trialData.m_overallStatistics.postsHour));
        jlPullsHour.setText(m_formatter.format(m_trialData.m_overallStatistics.pullsHour));
        jlIDsHour.setText(m_formatter.format(m_trialData.m_overallStatistics.iDsHour));
        jlADDsHour.setText(m_formatter.format(m_trialData.m_overallStatistics.aDDsHour));
        //
        //update stats
        jlFactTotalMain.setText(Integer.toString(m_trialData.m_factoidStats.totalFactoids));
        jlFactKeyMain.setText(Integer.toString(m_trialData.m_factoidStats.totalKey_Factoids + m_trialData.m_factoidStats.totalExpertise_Factoids));
        jlFactSuppMain.setText(Integer.toString(m_trialData.m_factoidStats.totalSupportive_Factoids));
        jlFactNoiseMain.setText(Integer.toString(m_trialData.m_factoidStats.totalNoise_Factoids));
        jlFactMisinfoMain.setText(Integer.toString(m_trialData.m_factoidStats.totalMisinfo_Factoids));
        //
        jlFactKeyRatioMain.setText(m_formatter.format((double) (m_trialData.m_factoidStats.totalKey_Factoids + m_trialData.m_factoidStats.totalExpertise_Factoids) / m_trialData.m_factoidStats.totalFactoids));
        jlFactSuppRatioMain.setText(m_formatter.format((double) (m_trialData.m_factoidStats.totalKey_Factoids + m_trialData.m_factoidStats.totalExpertise_Factoids + m_trialData.m_factoidStats.totalSupportive_Factoids) / m_trialData.m_factoidStats.totalFactoids));
        jlFactNoiseRatioMain.setText(m_formatter.format((double) m_trialData.m_factoidStats.totalNoise_Factoids / m_trialData.m_factoidStats.totalFactoids));
        jlFactMisinfRatioMain.setText(m_formatter.format((double) m_trialData.m_factoidStats.totalMisinfo_Factoids / m_trialData.m_factoidStats.totalFactoids));
        //some subwindows may provide additional info - make them available

        jFMoMSharedRelevantInformationAccessedTable = new JFMoMSharedRelevantInformationAccessedTable(m_trialData);
        jFMoMSharedInformationAccessedTable = new JFMoMSharedInformationAccessedTable(m_trialData);
        jFMoMAverageInformationAccessedTable = new JFMoMAverageInformationAccessedTable(m_trialData);
        jFMoMCorrectSharedUnderstandingTable = new JFMoMCorrectSharedUnderstanding(m_trialData);
        jFMoMCSSync = new JFMoMCSSync(m_trialData);
        jFMoMEffectiveness = new JFMoMEffectiveness(m_trialData);
        //jfIDBar = new JFBarIDDistribution("ID Answers", m_trialData.m_solution);
        jFSubjectsSocialNetworkTable = new JFSubjectsSocialNetworkTable(m_trialData);
        //jFSubjectsSocialNetworkTable = new JFSubjectsSocialNetworkTable(m_trialData);

        //update sub-windows
        UpdateFactoidWindow();


    }

    private void UpdateFactoidWindow() {
        jTable1.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringFactoidRelevance());
        //new new model?
        javax.swing.table.TableModel tb = jTable1.getModel();
        if (tb.getRowCount() != m_trialData.m_factoidStats.totalFactoids) {
            Object[][] obj = new Object[m_trialData.m_factoidStats.totalFactoids][3];

            jTable1.setModel(new javax.swing.table.DefaultTableModel(obj,
                    new String[]{
                        "Key", "Metadata", "Content"
                    }) {

                Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
            jScrollPane1.setViewportView(jTable1);

            tb = jTable1.getModel();
        }
        //fill factoid data
        int row = 0;
        int factoid_width = 10;
        for (Message message : m_trialData.m_messageList) {
            if (message.m_messageType.equals(Message.MESSAGE_TYPE_FACTOID) && message.m_factoid.m_relevance != Message.m_AChar) {
                tb.setValueAt(message.m_factoid.m_relevance, row, 0);
                tb.setValueAt(message.m_factoid.m_factoidId, row, 1);
                tb.setValueAt(message.m_factoid.m_factoidtext, row, 2);
                if (message.m_factoid.m_factoidtext.length() > factoid_width) {
                    factoid_width = message.m_factoid.m_factoidtext.length();
                }
                //
                row++;
            }
        }
        // Set the column width to see all factoid text
        javax.swing.table.TableColumn col = jTable1.getColumnModel().getColumn(2);
        col.setPreferredWidth(factoid_width * 5);

        //jTable1.updateUI();

        //update stats
        jlFactTotal.setText(Integer.toString(m_trialData.m_factoidStats.totalFactoids));
        jlFactKey.setText(Integer.toString(m_trialData.m_factoidStats.totalKey_Factoids + m_trialData.m_factoidStats.totalExpertise_Factoids));
        jlFactSupp.setText(Integer.toString(m_trialData.m_factoidStats.totalSupportive_Factoids));
        jlFactNoise.setText(Integer.toString(m_trialData.m_factoidStats.totalNoise_Factoids));
        jlFactMisinfo.setText(Integer.toString(m_trialData.m_factoidStats.totalMisinfo_Factoids));
        //
        jlFactKeyRatio.setText(m_formatter.format((double) (m_trialData.m_factoidStats.totalKey_Factoids + m_trialData.m_factoidStats.totalExpertise_Factoids) / m_trialData.m_factoidStats.totalFactoids));
        jlFactSuppRatio.setText(m_formatter.format((double) (m_trialData.m_factoidStats.totalKey_Factoids + m_trialData.m_factoidStats.totalExpertise_Factoids + m_trialData.m_factoidStats.totalSupportive_Factoids) / m_trialData.m_factoidStats.totalFactoids));
        jlFactNoiseRatio.setText(m_formatter.format((double) m_trialData.m_factoidStats.totalNoise_Factoids / m_trialData.m_factoidStats.totalFactoids));
        jlFactMisinfRatio.setText(m_formatter.format((double) m_trialData.m_factoidStats.totalMisinfo_Factoids / m_trialData.m_factoidStats.totalFactoids));
        //
        if (m_trialData.m_answerMessage!=null)
            jlAnswer.setText(m_trialData.m_answerMessage.m_data2);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdResult = new javax.swing.JDialog();
        jfProcessing = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlLine = new javax.swing.JLabel();
        JFFactoids = new javax.swing.JFrame();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jlFactTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jlFactKey = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jlFactSupp = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jlFactNoise = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jlFactMisinfo = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jlFactTotal1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jlFactKeyRatio = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jlFactSuppRatio = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jlFactNoiseRatio = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jlFactMisinfRatio = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jlAnswer = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jpOptions = new javax.swing.JPanel();
        jlFileInput = new javax.swing.JLabel();
        jlFileName = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jlOrganizationType = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlFactoidSetID = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jlFinish = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlDuration = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlDate = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jlStart = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jlIDs = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jlShares = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jlPosts = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlPulls = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jlSharesHour = new javax.swing.JLabel();
        jlPostsHour = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jlIDsHour = new javax.swing.JLabel();
        jlPullsHour = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jlADDs = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jlADDsHour = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jlFactTotalMain = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jlFactKeyMain = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jlFactSuppMain = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jlFactNoiseMain = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jlFactMisinfoMain = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jlFactTotal3 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jlFactKeyRatioMain = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jlFactSuppRatioMain = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jlFactNoiseRatioMain = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jlFactMisinfRatioMain = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jmInformationDomain = new javax.swing.JMenu();
        jmiInformationTable = new javax.swing.JMenuItem();
        jmiInformationAccessibleServer = new javax.swing.JMenuItem();
        jmiInformationAccessibleServerTable = new javax.swing.JMenuItem();
        jmiInformationAccessible = new javax.swing.JMenuItem();
        jmiInformationAccessibleTable = new javax.swing.JMenuItem();
        jmiInformationReached = new javax.swing.JMenuItem();
        jmiInformationReachedTable = new javax.swing.JMenuItem();
        jmiFactoidsActivity = new javax.swing.JMenuItem();
        jmiFactoidsActivityBar = new javax.swing.JMenuItem();
        jmiCriticalInformationTable = new javax.swing.JMenuItem();
        jmiCriticalInformationTable2 = new javax.swing.JMenuItem();
        jmSNA = new javax.swing.JMenu();
        jmiSNA = new javax.swing.JMenuItem();
        jmiInteractionsMatrix = new javax.swing.JMenuItem();
        jmiTeamInteractionsMatrix = new javax.swing.JMenuItem();
        jmiReciprocityRate_shares = new javax.swing.JMenuItem();
        jmiNetworkBalanceTable = new javax.swing.JMenuItem();
        jmiNetworkBalanceChart = new javax.swing.JMenuItem();
        jmiInboundInteractions = new javax.swing.JMenuItem();
        jmiOutboundInteractions = new javax.swing.JMenuItem();
        jmiPostsAndShares = new javax.swing.JMenuItem();
        jmiFormsInteraction = new javax.swing.JMenuItem();
        jmiFormsInteractionSharesTable = new javax.swing.JMenuItem();
        jmiFormsInteractionPostsTable = new javax.swing.JMenuItem();
        jmiFormsInteractionSharesPostsTable = new javax.swing.JMenuItem();
        jmiSubjectsFormsInteractionsTable = new javax.swing.JMenuItem();
        jmiQualityInteractionsTable = new javax.swing.JMenuItem();
        jmiQualityOfInteractions_share = new javax.swing.JMenuItem();
        jmiQualityOfInteractions_post = new javax.swing.JMenuItem();
        jmiQualityOfInteractions_share_and_post = new javax.swing.JMenuItem();
        jmiFactoidsHoarding = new javax.swing.JMenuItem();
        jmiFactoidsSharingdelay_table = new javax.swing.JMenuItem();
        jmAwarenessUnderstanding = new javax.swing.JMenu();
        jmiAwarenessQ = new javax.swing.JMenuItem();
        jmiAwarenessQTable = new javax.swing.JMenuItem();
        jmiIdentificationsBar = new javax.swing.JMenuItem();
        jmiSubjectsIdentifications = new javax.swing.JMenuItem();
        jmiQualityOfIDs = new javax.swing.JMenuItem();
        jmiCorrectIDs = new javax.swing.JMenuItem();
        jmiRateCorrectIDs = new javax.swing.JMenuItem();
        jmiSyncronizationEntropy = new javax.swing.JMenuItem();
        jmiSyncronizationEntropyTable = new javax.swing.JMenuItem();
        jmMoM = new javax.swing.JMenu();
        jmiMoMSharedInformationAccessible = new javax.swing.JMenuItem();
        jmiMoMSharedInformationAccessed = new javax.swing.JMenuItem();
        jmiMoMSharedRelevantInformationAccessible = new javax.swing.JMenuItem();
        jmiMoMSharedRelevantInformationAccessed = new javax.swing.JMenuItem();
        jmiAVGInfoReached = new javax.swing.JMenuItem();
        jmiMoMShUnd = new javax.swing.JMenuItem();
        jmiMoMIDs = new javax.swing.JMenuItem();
        jmiMoMCSS = new javax.swing.JMenuItem();
        jmiMoMEffectiveness = new javax.swing.JMenuItem();

        jfProcessing.setTitle("Metrics Selection Window");
        jfProcessing.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Parsing Log...");
        jfProcessing.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel2.setText("Line:");
        jLabel2.setPreferredSize(new java.awt.Dimension(23, 20));
        jfProcessing.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jlLine.setPreferredSize(new java.awt.Dimension(40, 20));
        jfProcessing.getContentPane().add(jlLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 60, -1));

        JFFactoids.setTitle("Factoid Data");
        JFFactoids.setMinimumSize(new java.awt.Dimension(600, 400));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Overall Fatoid Data"));
        jPanel8.setMaximumSize(null);
        jPanel8.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("  TOTAL: ");
        jLabel3.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel3.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel9.add(jLabel3);

        jlFactTotal.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactTotal.setText("0");
        jlFactTotal.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactTotal.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactTotal.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel9.add(jlFactTotal);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Key/Exp:");
        jLabel9.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel9.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel9.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel9.add(jLabel9);

        jlFactKey.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactKey.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactKey.setText("0");
        jlFactKey.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactKey.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactKey.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel9.add(jlFactKey);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Support:");
        jLabel24.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel24.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel24.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel9.add(jLabel24);

        jlFactSupp.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactSupp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactSupp.setText("0");
        jlFactSupp.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactSupp.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactSupp.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel9.add(jlFactSupp);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Noise:");
        jLabel26.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel26.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel26.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel9.add(jLabel26);

        jlFactNoise.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactNoise.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactNoise.setText("0");
        jlFactNoise.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactNoise.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactNoise.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel9.add(jlFactNoise);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Mis-info:");
        jLabel28.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel28.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel28.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel9.add(jLabel28);

        jlFactMisinfo.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactMisinfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactMisinfo.setText("0");
        jlFactMisinfo.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfo.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfo.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel9.add(jlFactMisinfo);

        jPanel8.add(jPanel9);

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jlFactTotal1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactTotal1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactTotal1.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactTotal1.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactTotal1.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel10.add(jlFactTotal1);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("RATIOS: ");
        jLabel4.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel4.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel10.add(jLabel4);
        jLabel4.getAccessibleContext().setAccessibleName("");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("K/E Ratio");
        jLabel11.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel11.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel11.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel10.add(jLabel11);

        jlFactKeyRatio.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactKeyRatio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactKeyRatio.setText("0");
        jlFactKeyRatio.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactKeyRatio.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactKeyRatio.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel10.add(jlFactKeyRatio);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("K/E/S Rat:");
        jLabel25.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel25.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel25.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel10.add(jLabel25);

        jlFactSuppRatio.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactSuppRatio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactSuppRatio.setText("0");
        jlFactSuppRatio.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactSuppRatio.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactSuppRatio.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel10.add(jlFactSuppRatio);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Noise Rat:");
        jLabel27.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel27.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel27.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel10.add(jLabel27);

        jlFactNoiseRatio.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactNoiseRatio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactNoiseRatio.setText("0");
        jlFactNoiseRatio.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactNoiseRatio.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactNoiseRatio.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel10.add(jlFactNoiseRatio);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("M Ratio:");
        jLabel29.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel29.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel29.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel10.add(jLabel29);

        jlFactMisinfRatio.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactMisinfRatio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactMisinfRatio.setText("0");
        jlFactMisinfRatio.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfRatio.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfRatio.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel10.add(jlFactMisinfRatio);

        jPanel8.add(jPanel10);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("Answer: ");
        jLabel38.setPreferredSize(new java.awt.Dimension(60, 14));
        jPanel12.add(jLabel38);

        jlAnswer.setText("   ");
        jPanel12.add(jlAnswer);

        jPanel8.add(jPanel12);

        JFFactoids.getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(460, 500));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Key", "Metadata", "Content"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setEnabled(false);
        jTable1.setMinimumSize(new java.awt.Dimension(600, 400));
        jScrollPane1.setViewportView(jTable1);

        JFFactoids.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Legend"));
        jPanel11.setMaximumSize(null);
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel30.setBackground(new java.awt.Color(0, 204, 0));
        jLabel30.setText("    ");
        jLabel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel30.setOpaque(true);
        jPanel11.add(jLabel30);

        jLabel31.setText("Key / Expertise");
        jPanel11.add(jLabel31);

        jLabel32.setBackground(new java.awt.Color(255, 204, 0));
        jLabel32.setText("    ");
        jLabel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel32.setOpaque(true);
        jPanel11.add(jLabel32);

        jLabel33.setText("Supportive");
        jPanel11.add(jLabel33);

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("    ");
        jLabel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel34.setOpaque(true);
        jPanel11.add(jLabel34);

        jLabel35.setText("Noise");
        jPanel11.add(jLabel35);

        jLabel36.setBackground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("    ");
        jLabel36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel36.setOpaque(true);
        jPanel11.add(jLabel36);

        jLabel37.setText("Misinformation");
        jPanel11.add(jLabel37);

        JFFactoids.getContentPane().add(jPanel11, java.awt.BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Overall Metrics");
        setResizable(false);

        jpOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Input file")));
        jpOptions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jlFileInput.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlFileInput.setText("LOG File:");
        jlFileInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlFileInput.setPreferredSize(new java.awt.Dimension(70, 20));
        jpOptions.add(jlFileInput);

        jlFileName.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFileName.setPreferredSize(new java.awt.Dimension(400, 20));
        jpOptions.add(jlFileName);

        getContentPane().add(jpOptions, java.awt.BorderLayout.NORTH);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Overall Information"));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Organization Type:");
        jLabel6.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 100, -1));

        jlOrganizationType.setBackground(new java.awt.Color(204, 204, 204));
        jlOrganizationType.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlOrganizationType.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlOrganizationType.setToolTipText("Click to visualize organization type");
        jlOrganizationType.setOpaque(true);
        jlOrganizationType.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jlOrganizationType, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 300, -1));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Factoid-set ID:");
        jLabel5.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 90, -1));

        jlFactoidSetID.setBackground(new java.awt.Color(204, 204, 204));
        jlFactoidSetID.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactoidSetID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlFactoidSetID.setToolTipText("Click to visualize factoid-set");
        jlFactoidSetID.setOpaque(true);
        jlFactoidSetID.setPreferredSize(new java.awt.Dimension(66, 20));
        jlFactoidSetID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlFactoidSetIDMouseClicked(evt);
            }
        });
        jPanel5.add(jlFactoidSetID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 300, -1));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Finish:");
        jLabel7.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(276, 110, 50, -1));

        jlFinish.setBackground(new java.awt.Color(204, 204, 204));
        jlFinish.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFinish.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlFinish.setOpaque(true);
        jlFinish.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jlFinish, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 120, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText(" (min)");
        jLabel12.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 60, -1));

        jlDuration.setBackground(new java.awt.Color(204, 204, 204));
        jlDuration.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlDuration.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlDuration.setOpaque(true);
        jlDuration.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jlDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 120, -1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Duration:");
        jLabel13.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 90, -1));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Date:");
        jLabel14.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 100, -1));

        jlDate.setBackground(new java.awt.Color(204, 204, 204));
        jlDate.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlDate.setOpaque(true);
        jlDate.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jlDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 300, -1));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Start:");
        jLabel15.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 100, -1));

        jlStart.setBackground(new java.awt.Color(204, 204, 204));
        jlStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlStart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlStart.setOpaque(true);
        jlStart.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel5.add(jlStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 120, -1));

        jPanel6.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 182));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Overall Metrics"));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Total IDs:");
        jLabel16.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, -1));

        jlIDs.setBackground(new java.awt.Color(204, 204, 204));
        jlIDs.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlIDs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlIDs.setOpaque(true);
        jlIDs.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlIDs, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 120, -1));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Total Shares:");
        jLabel17.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, -1));

        jlShares.setBackground(new java.awt.Color(204, 204, 204));
        jlShares.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlShares.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlShares.setOpaque(true);
        jlShares.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlShares, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 120, -1));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Total Posts:");
        jLabel18.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, -1));

        jlPosts.setBackground(new java.awt.Color(204, 204, 204));
        jlPosts.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlPosts.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlPosts.setOpaque(true);
        jlPosts.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlPosts, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 120, -1));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Total Pulls:");
        jLabel19.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, -1));

        jlPulls.setBackground(new java.awt.Color(204, 204, 204));
        jlPulls.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlPulls.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlPulls.setOpaque(true);
        jlPulls.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlPulls, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 120, -1));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Shares/Hour:");
        jLabel20.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 90, -1));

        jlSharesHour.setBackground(new java.awt.Color(204, 204, 204));
        jlSharesHour.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlSharesHour.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlSharesHour.setOpaque(true);
        jlSharesHour.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlSharesHour, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 120, -1));

        jlPostsHour.setBackground(new java.awt.Color(204, 204, 204));
        jlPostsHour.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlPostsHour.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlPostsHour.setOpaque(true);
        jlPostsHour.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlPostsHour, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 120, -1));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Posts/Hour:");
        jLabel21.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 90, -1));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Pulls/Hour:");
        jLabel22.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 90, -1));

        jlIDsHour.setBackground(new java.awt.Color(204, 204, 204));
        jlIDsHour.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlIDsHour.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlIDsHour.setOpaque(true);
        jlIDsHour.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlIDsHour, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 120, -1));

        jlPullsHour.setBackground(new java.awt.Color(204, 204, 204));
        jlPullsHour.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlPullsHour.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlPullsHour.setOpaque(true);
        jlPullsHour.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlPullsHour, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 120, -1));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("IDs/Hour:");
        jLabel23.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 90, -1));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel49.setText("Total ADDs:");
        jLabel49.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 90, -1));

        jlADDs.setBackground(new java.awt.Color(204, 204, 204));
        jlADDs.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlADDs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlADDs.setOpaque(true);
        jlADDs.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlADDs, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 120, -1));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel50.setText("ADDs/Hour:");
        jLabel50.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 90, -1));

        jlADDsHour.setBackground(new java.awt.Color(204, 204, 204));
        jlADDsHour.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlADDsHour.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlADDsHour.setOpaque(true);
        jlADDsHour.setPreferredSize(new java.awt.Dimension(66, 20));
        jPanel7.add(jlADDsHour, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 120, -1));

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 510, 170));

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Factoid stats:"));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.Y_AXIS));

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("  TOTAL: ");
        jLabel39.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel39.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel39.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel14.add(jLabel39);

        jlFactTotalMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactTotalMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactTotalMain.setText("0");
        jlFactTotalMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactTotalMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactTotalMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel14.add(jlFactTotalMain);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("Key/Exp:");
        jLabel40.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel40.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel40.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel14.add(jLabel40);

        jlFactKeyMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactKeyMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactKeyMain.setText("0");
        jlFactKeyMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactKeyMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactKeyMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel14.add(jlFactKeyMain);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Support:");
        jLabel41.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel41.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel41.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel14.add(jLabel41);

        jlFactSuppMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactSuppMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactSuppMain.setText("0");
        jlFactSuppMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactSuppMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactSuppMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel14.add(jlFactSuppMain);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Noise:");
        jLabel42.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel42.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel42.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel14.add(jLabel42);

        jlFactNoiseMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactNoiseMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactNoiseMain.setText("0");
        jlFactNoiseMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactNoiseMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactNoiseMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel14.add(jlFactNoiseMain);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Mis-info:");
        jLabel43.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel43.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel43.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel14.add(jLabel43);

        jlFactMisinfoMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactMisinfoMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactMisinfoMain.setText("0");
        jlFactMisinfoMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfoMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfoMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel14.add(jlFactMisinfoMain);

        jPanel13.add(jPanel14);

        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.LINE_AXIS));

        jlFactTotal3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactTotal3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactTotal3.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactTotal3.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactTotal3.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel15.add(jlFactTotal3);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel44.setText("RATIOS: ");
        jLabel44.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel44.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel44.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel15.add(jLabel44);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel45.setText("K/E Ratio");
        jLabel45.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel45.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel45.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel15.add(jLabel45);

        jlFactKeyRatioMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactKeyRatioMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactKeyRatioMain.setText("0");
        jlFactKeyRatioMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactKeyRatioMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactKeyRatioMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel15.add(jlFactKeyRatioMain);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("K/E/S Rat:");
        jLabel46.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel46.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel46.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel15.add(jLabel46);

        jlFactSuppRatioMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactSuppRatioMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactSuppRatioMain.setText("0");
        jlFactSuppRatioMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactSuppRatioMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactSuppRatioMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel15.add(jlFactSuppRatioMain);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel47.setText("Noise Rat:");
        jLabel47.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel47.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel47.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel15.add(jLabel47);

        jlFactNoiseRatioMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactNoiseRatioMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactNoiseRatioMain.setText("0");
        jlFactNoiseRatioMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactNoiseRatioMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactNoiseRatioMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel15.add(jlFactNoiseRatioMain);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel48.setText("M Ratio:");
        jLabel48.setMaximumSize(new java.awt.Dimension(60, 30));
        jLabel48.setMinimumSize(new java.awt.Dimension(60, 30));
        jLabel48.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel15.add(jLabel48);

        jlFactMisinfRatioMain.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFactMisinfRatioMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFactMisinfRatioMain.setText("0");
        jlFactMisinfRatioMain.setMaximumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfRatioMain.setMinimumSize(new java.awt.Dimension(40, 30));
        jlFactMisinfRatioMain.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel15.add(jlFactMisinfRatioMain);

        jPanel13.add(jPanel15);

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 510, -1));

        getContentPane().add(jPanel6, java.awt.BorderLayout.CENTER);

        jmInformationDomain.setText("Information Domain");

        jmiInformationTable.setText("Information Table");
        jmiInformationTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationTableActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationTable);

        jmiInformationAccessibleServer.setText("Information Accessibile (Server Messages) (Chart)");
        jmiInformationAccessibleServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationAccessibleServerActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationAccessibleServer);

        jmiInformationAccessibleServerTable.setText("Information Accessible Server Messages (Table)");
        jmiInformationAccessibleServerTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationAccessibleServerTableActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationAccessibleServerTable);

        jmiInformationAccessible.setText("Information Accessible (Chart)");
        jmiInformationAccessible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationAccessibleActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationAccessible);

        jmiInformationAccessibleTable.setText("Information Accessible (Table)");
        jmiInformationAccessibleTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationAccessibleTableActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationAccessibleTable);

        jmiInformationReached.setText("Information Reached (Chart)");
        jmiInformationReached.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationReachedActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationReached);

        jmiInformationReachedTable.setText("Information Reached (Table)");
        jmiInformationReachedTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInformationReachedTableActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiInformationReachedTable);

        jmiFactoidsActivity.setText("Factoids Activity (Table)");
        jmiFactoidsActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFactoidsActivityActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiFactoidsActivity);

        jmiFactoidsActivityBar.setText("Factoids Activity (Bar)");
        jmiFactoidsActivityBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFactoidsActivityBarActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiFactoidsActivityBar);

        jmiCriticalInformationTable.setText("Critical Information Accessibility (Table)");
        jmiCriticalInformationTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCriticalInformationTableActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiCriticalInformationTable);

        jmiCriticalInformationTable2.setText("Critical Information Accessibility (Table - View 2)");
        jmiCriticalInformationTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCriticalInformationTable2ActionPerformed(evt);
            }
        });
        jmInformationDomain.add(jmiCriticalInformationTable2);

        jMenuBar.add(jmInformationDomain);

        jmSNA.setText("Interactions and SNA");

        jmiSNA.setText("SNA (Graph-Sociogram)");
        jmiSNA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSNAActionPerformed(evt);
            }
        });
        jmSNA.add(jmiSNA);

        jmiInteractionsMatrix.setText("Interactions Matrix (Table)");
        jmiInteractionsMatrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInteractionsMatrixActionPerformed(evt);
            }
        });
        jmSNA.add(jmiInteractionsMatrix);

        jmiTeamInteractionsMatrix.setText("Team Interactions Matrix (Table)");
        jmiTeamInteractionsMatrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTeamInteractionsMatrixActionPerformed(evt);
            }
        });
        jmSNA.add(jmiTeamInteractionsMatrix);

        jmiReciprocityRate_shares.setText("Sharing Reciprocity (Table)");
        jmiReciprocityRate_shares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiReciprocityRate_sharesActionPerformed(evt);
            }
        });
        jmSNA.add(jmiReciprocityRate_shares);

        jmiNetworkBalanceTable.setText("Nodes IN and OUT Deviation (Table)");
        jmiNetworkBalanceTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNetworkBalanceTableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiNetworkBalanceTable);

        jmiNetworkBalanceChart.setText("Nodes IN and OUT Deviation (Chart)");
        jmiNetworkBalanceChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNetworkBalanceChartActionPerformed(evt);
            }
        });
        jmSNA.add(jmiNetworkBalanceChart);

        jmiInboundInteractions.setText("In-Degrees - shares received (Bar)");
        jmiInboundInteractions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInboundInteractionsActionPerformed(evt);
            }
        });
        jmSNA.add(jmiInboundInteractions);

        jmiOutboundInteractions.setText("Out-Degrees - shares sent (Bar)");
        jmiOutboundInteractions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiOutboundInteractionsActionPerformed(evt);
            }
        });
        jmSNA.add(jmiOutboundInteractions);

        jmiPostsAndShares.setText("Forms of Interations (Bar)");
        jmiPostsAndShares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPostsAndSharesActionPerformed(evt);
            }
        });
        jmSNA.add(jmiPostsAndShares);

        jmiFormsInteraction.setText("Forms of Interaction (Chart)");
        jmiFormsInteraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFormsInteractionActionPerformed(evt);
            }
        });
        jmSNA.add(jmiFormsInteraction);

        jmiFormsInteractionSharesTable.setText("Forms of Interaction: Shares (Table)");
        jmiFormsInteractionSharesTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFormsInteractionSharesTableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiFormsInteractionSharesTable);

        jmiFormsInteractionPostsTable.setText("Forms of Interaction: Posts (Table)");
        jmiFormsInteractionPostsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFormsInteractionPostsTableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiFormsInteractionPostsTable);

        jmiFormsInteractionSharesPostsTable.setText("Forms of Interaction: Shares and Posts (Table)");
        jmiFormsInteractionSharesPostsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFormsInteractionSharesPostsTableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiFormsInteractionSharesPostsTable);

        jmiSubjectsFormsInteractionsTable.setText("Subjects Forms of Interactions (Table)");
        jmiSubjectsFormsInteractionsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSubjectsFormsInteractionsTableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiSubjectsFormsInteractionsTable);

        jmiQualityInteractionsTable.setText("Quality of Interactions (Table)");
        jmiQualityInteractionsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQualityInteractionsTableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiQualityInteractionsTable);

        jmiQualityOfInteractions_share.setText("Quality of Interactions (Share) (Chart)");
        jmiQualityOfInteractions_share.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQualityOfInteractions_shareActionPerformed(evt);
            }
        });
        jmSNA.add(jmiQualityOfInteractions_share);

        jmiQualityOfInteractions_post.setText("Quality of Interactions (Post) (Chart)");
        jmiQualityOfInteractions_post.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQualityOfInteractions_postActionPerformed(evt);
            }
        });
        jmSNA.add(jmiQualityOfInteractions_post);

        jmiQualityOfInteractions_share_and_post.setText("Quality of Interactions (Share and Posts) (Chart)");
        jmiQualityOfInteractions_share_and_post.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQualityOfInteractions_share_and_postActionPerformed(evt);
            }
        });
        jmSNA.add(jmiQualityOfInteractions_share_and_post);

        jmiFactoidsHoarding.setText("Factoids Hoarding (Table)");
        jmiFactoidsHoarding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFactoidsHoardingActionPerformed(evt);
            }
        });
        jmSNA.add(jmiFactoidsHoarding);

        jmiFactoidsSharingdelay_table.setText("Factoids Share or Post Delay (Table)");
        jmiFactoidsSharingdelay_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFactoidsSharingdelay_tableActionPerformed(evt);
            }
        });
        jmSNA.add(jmiFactoidsSharingdelay_table);

        jMenuBar.add(jmSNA);

        jmAwarenessUnderstanding.setText("Awareness and Understanding");

        jmiAwarenessQ.setText("Quality of Awareness (Chart)");
        jmiAwarenessQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAwarenessQActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiAwarenessQ);

        jmiAwarenessQTable.setText("Quality of Awareness (Table)");
        jmiAwarenessQTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAwarenessQTableActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiAwarenessQTable);

        jmiIdentificationsBar.setText("Identifications (Bar)");
        jmiIdentificationsBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiIdentificationsBarActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiIdentificationsBar);

        jmiSubjectsIdentifications.setText("Subjects Identifications (Table)");
        jmiSubjectsIdentifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSubjectsIdentificationsActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiSubjectsIdentifications);

        jmiQualityOfIDs.setText("Quality of IDs (Chart)");
        jmiQualityOfIDs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQualityOfIDsActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiQualityOfIDs);

        jmiCorrectIDs.setText("Number of Correct IDs (Chart)");
        jmiCorrectIDs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCorrectIDsActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiCorrectIDs);

        jmiRateCorrectIDs.setText("Percentage of Correct IDs (Chart)");
        jmiRateCorrectIDs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiRateCorrectIDsActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiRateCorrectIDs);

        jmiSyncronizationEntropy.setText("Cognitive Self-Syncronization (Chart)");
        jmiSyncronizationEntropy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSyncronizationEntropyActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiSyncronizationEntropy);

        jmiSyncronizationEntropyTable.setText("Cognitive Self-Syncronization (Table)");
        jmiSyncronizationEntropyTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSyncronizationEntropyTableActionPerformed(evt);
            }
        });
        jmAwarenessUnderstanding.add(jmiSyncronizationEntropyTable);

        jMenuBar.add(jmAwarenessUnderstanding);

        jmMoM.setText("MoM");

        jmiMoMSharedInformationAccessible.setText("Shared Information Accessible (Table)");
        jmiMoMSharedInformationAccessible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMSharedInformationAccessibleActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMSharedInformationAccessible);

        jmiMoMSharedInformationAccessed.setText("Shared Information Accessed (Table)");
        jmiMoMSharedInformationAccessed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMSharedInformationAccessedActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMSharedInformationAccessed);

        jmiMoMSharedRelevantInformationAccessible.setText("Shared Relevant Information Accessible (Table)");
        jmiMoMSharedRelevantInformationAccessible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMSharedRelevantInformationAccessibleActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMSharedRelevantInformationAccessible);

        jmiMoMSharedRelevantInformationAccessed.setText("Shared Relevant Information Accessed (Table)");
        jmiMoMSharedRelevantInformationAccessed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMSharedRelevantInformationAccessedActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMSharedRelevantInformationAccessed);

        jmiAVGInfoReached.setText("Average Relevant Information Reached (Table)");
        jmiAVGInfoReached.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAVGInfoReachedActionPerformed(evt);
            }
        });
        jmMoM.add(jmiAVGInfoReached);

        jmiMoMShUnd.setText("Correct Shared Understanding (Table)");
        jmiMoMShUnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMShUndActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMShUnd);

        jmiMoMIDs.setText("Identification Scores (Table)");
        jmiMoMIDs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMIDsActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMIDs);

        jmiMoMCSS.setText("Cog. Self-Sync (Table)");
        jmiMoMCSS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMCSSActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMCSS);

        jmiMoMEffectiveness.setText("Effectiveness (Table)");
        jmiMoMEffectiveness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMoMEffectivenessActionPerformed(evt);
            }
        });
        jmMoM.add(jmiMoMEffectiveness);

        jMenuBar.add(jmMoM);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlFactoidSetIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlFactoidSetIDMouseClicked
        JFFactoids.setVisible(true);
    }//GEN-LAST:event_jlFactoidSetIDMouseClicked

    private void jmiInformationAccessibleServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationAccessibleServerActionPerformed
        JF2DataSetsTimeChart jfInfoQ = new JF2DataSetsTimeChart("Information Accessible (Server Distribution)");
        jfInfoQ.setData("Information Accessible (Total Factoids)",
                        m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_accessIndexOverall,
                        m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_informationAccessibleByTeams,
                        m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_informationAccessibleBySubjects,
                        "Shared Information Accessible (Total Factoids)",
                        m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_set2AccessIndexOverall,
                        m_trialData.m_informationQuality.m_accessibilityNewInfomationServer.m_set2InformationAccessibleByTeams,
                        null);
        //jfInfoQ.SetValueAxis(0, m_trialData.m_factoidStats.totalFactoids+2);
        jfInfoQ.SetValueAxis();
        //
        jfInfoQ.showChart();
    }//GEN-LAST:event_jmiInformationAccessibleServerActionPerformed

    private void jmiInformationAccessibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationAccessibleActionPerformed
        JF2DataSetsTimeChart jfInfoQ = new JF2DataSetsTimeChart("Information Reach (Shares, Posts and Server Distribution)");
        jfInfoQ.setData("Information Accessible (Total Factoids)",
                        m_trialData.m_informationQuality.m_accessibilityIndex.m_accessIndexOverall,
                        m_trialData.m_informationQuality.m_accessibilityIndex.m_informationAccessibleByTeams,
                        m_trialData.m_informationQuality.m_accessibilityIndex.m_informationAccessibleBySubjects,
                        "Shared Information Reach (Total Factoids)",
                        m_trialData.m_informationQuality.m_accessibilityIndex.m_set2AccessIndexOverall,
                        m_trialData.m_informationQuality.m_accessibilityIndex.m_set2InformationAccessibleByTeams,
                        null);
        //jfInfoQ.SetValueAxis(0, m_trialData.m_factoidStats.totalFactoids+2);
        jfInfoQ.SetValueAxis();
        //
        jfInfoQ.showChart();
    }//GEN-LAST:event_jmiInformationAccessibleActionPerformed

    private void jmiFormsInteractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFormsInteractionActionPerformed
        JF2DataSetsTimeChart jfInfoQ = new JF2DataSetsTimeChart("Forms of Interaction (Shares and Posts)");
        jfInfoQ.setData("Total Shares",
                        m_trialData.m_informationQuality.m_formsInteraction.m_accessIndexOverall,
                        m_trialData.m_informationQuality.m_formsInteraction.m_informationAccessibleByTeams,
                        m_trialData.m_informationQuality.m_formsInteraction.m_informationAccessibleBySubjects,
                        "Total Posts",
                        m_trialData.m_informationQuality.m_formsInteraction.m_set2AccessIndexOverall,
                        m_trialData.m_informationQuality.m_formsInteraction.m_set2InformationAccessibleByTeams,
                        m_trialData.m_informationQuality.m_formsInteraction.m_set2InformationAccessibleBySubjects);
        //jfInfoQ.SetValueAxis(0, m_trialData.m_factoidStats.totalFactoids+2);
        jfInfoQ.SetValueAxis();
        //
        jfInfoQ.showChart();
    }//GEN-LAST:event_jmiFormsInteractionActionPerformed

    private void jmiInformationReachedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationReachedActionPerformed
        JF2DataSetsTimeChart jfInfoQ = new JF2DataSetsTimeChart("Information Reached (accessible after subjects shares or pulls)");
        jfInfoQ.setData("Information Accessible (Total Factoids)",
                        m_trialData.m_informationQuality.m_reachedIndex.m_accessIndexOverall,
                        m_trialData.m_informationQuality.m_reachedIndex.m_informationAccessibleByTeams,
                        m_trialData.m_informationQuality.m_reachedIndex.m_informationAccessibleBySubjects,
                        "Shared Information Reached (Shared Factoids)",
                        m_trialData.m_informationQuality.m_reachedIndex.m_set2AccessIndexOverall,
                        m_trialData.m_informationQuality.m_reachedIndex.m_set2InformationAccessibleByTeams,
                        m_trialData.m_informationQuality.m_reachedIndex.m_set2InformationAccessibleBySubjects);
        //jfInfoQ.SetValueAxis(0, m_trialData.m_factoidStats.totalFactoids+2);
        jfInfoQ.SetValueAxis();
        //
        jfInfoQ.showChart();
    }//GEN-LAST:event_jmiInformationReachedActionPerformed

    private void jmiInformationAccessibleServerTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationAccessibleServerTableActionPerformed
        if (jFFactoidsTableInformationAccessibleByServer == null) {
            jFFactoidsTableInformationAccessibleByServer = new JFSubjectsFactoidsTable(m_trialData);
        }
        if (!jFFactoidsTableInformationAccessibleByServer.isVisible())
            jFFactoidsTableInformationAccessibleByServer.setVisible(true);
    }//GEN-LAST:event_jmiInformationAccessibleServerTableActionPerformed

    private void jmiInformationAccessibleTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationAccessibleTableActionPerformed
        if (jFFactoidsTableInformationAccessible == null) {
            jFFactoidsTableInformationAccessible = new JFSubjectsFactoidsInformationReachTable(m_trialData);
        }
        if (!jFFactoidsTableInformationAccessible.isVisible())
            jFFactoidsTableInformationAccessible.setVisible(true);
    }//GEN-LAST:event_jmiInformationAccessibleTableActionPerformed

    private void jmiFormsInteractionSharesTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFormsInteractionSharesTableActionPerformed
        if (jFFactoidsTableShares == null) {
            jFFactoidsTableShares = new JFSubjectsFactoidsSharesAndPostsTable(m_trialData, JFSubjectsFactoidsSharesAndPostsTable.OPTION_SHOW_SHARES);
        }
        if (!jFFactoidsTableShares.isVisible())
            jFFactoidsTableShares.setVisible(true);
    }//GEN-LAST:event_jmiFormsInteractionSharesTableActionPerformed

    private void jmiFormsInteractionPostsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFormsInteractionPostsTableActionPerformed
        if (jFFactoidsTablePosts == null) {
            jFFactoidsTablePosts = new JFSubjectsFactoidsSharesAndPostsTable(m_trialData, JFSubjectsFactoidsSharesAndPostsTable.OPTION_SHOW_POSTS);
        }
        if (!jFFactoidsTablePosts.isVisible())
            jFFactoidsTablePosts.setVisible(true);
    }//GEN-LAST:event_jmiFormsInteractionPostsTableActionPerformed

    private void jmiFormsInteractionSharesPostsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFormsInteractionSharesPostsTableActionPerformed
        if (jFFactoidsTableSharesPosts == null) {
            jFFactoidsTableSharesPosts = new JFSubjectsFactoidsSharesAndPostsTable(m_trialData, JFSubjectsFactoidsSharesAndPostsTable.OPTION_SHOW_SHARES_AND_POSTS);
        }
        if (!jFFactoidsTableSharesPosts.isVisible())
            jFFactoidsTableSharesPosts.setVisible(true);
    }//GEN-LAST:event_jmiFormsInteractionSharesPostsTableActionPerformed

    private void jmiInteractionsMatrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInteractionsMatrixActionPerformed
        if (jFSubjectsSocialNetworkTable == null) {
            jFSubjectsSocialNetworkTable = new JFSubjectsSocialNetworkTable(m_trialData);
        }
        if (!jFSubjectsSocialNetworkTable.isVisible())
            jFSubjectsSocialNetworkTable.setVisible(true);
    }//GEN-LAST:event_jmiInteractionsMatrixActionPerformed

    private void jmiQualityOfInteractions_shareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQualityOfInteractions_shareActionPerformed
        if (jFQualityOfInteractionsShares == null) {
            jFQualityOfInteractionsShares = new JFSimpleTimeChart("Quality of Interations (Share)");
            m_qualityOfSharesMap = new TreeMap<String, Vector<Point2D.Double>>();
            ConvertQualityOfInterationsMaptoPoint2DMap (m_trialData.m_interactions.m_qualityOfInteractionShare,
                                                       m_qualityOfSharesMap);
            jFQualityOfInteractionsShares.setData("Quality of Interations (Share)",
                                                  m_qualityOfSharesMap);
            //
            jFQualityOfInteractionsShares.showChart();
        }
        if (!jFQualityOfInteractionsShares.isVisible()) jFQualityOfInteractionsShares.setVisible(true);
    }//GEN-LAST:event_jmiQualityOfInteractions_shareActionPerformed

    private void jmiQualityOfInteractions_postActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQualityOfInteractions_postActionPerformed
        if (jFQualityOfInteractionsPosts == null) {
            jFQualityOfInteractionsPosts = new JFSimpleTimeChart("Quality of Interations (Post)");
            m_qualityOfPostsMap = new TreeMap<String, Vector<Point2D.Double>>();
            ConvertQualityOfInterationsMaptoPoint2DMap (m_trialData.m_interactions.m_qualityOfInteractionPost,
                                                       m_qualityOfPostsMap);
            jFQualityOfInteractionsPosts.setData("Quality of Interations (Post)",
                                                 m_qualityOfPostsMap);
            //
            jFQualityOfInteractionsPosts.showChart();
        }
        if (!jFQualityOfInteractionsPosts.isVisible()) jFQualityOfInteractionsPosts.setVisible(true);
    }//GEN-LAST:event_jmiQualityOfInteractions_postActionPerformed

    private void jmiQualityOfInteractions_share_and_postActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQualityOfInteractions_share_and_postActionPerformed
        if (jFQualityOfInteractionsSharesAndPosts == null) {
            jFQualityOfInteractionsSharesAndPosts = new JFSimpleTimeChart("Quality of Interations (Share and Post)");
            m_qualityOfSharesAndPostsMap = new TreeMap<String, Vector<Point2D.Double>>();
            ConvertQualityOfInterationsMaptoPoint2DMap (m_trialData.m_interactions.m_qualityOfInteractionShareAndPost,
                                                       m_qualityOfSharesAndPostsMap);
            jFQualityOfInteractionsSharesAndPosts.setData("Quality of Interations (Share and Post)",
                                                          m_qualityOfSharesAndPostsMap);
            //
            jFQualityOfInteractionsSharesAndPosts.showChart();
        }
        if (!jFQualityOfInteractionsSharesAndPosts.isVisible()) jFQualityOfInteractionsSharesAndPosts.setVisible(true);
    }//GEN-LAST:event_jmiQualityOfInteractions_share_and_postActionPerformed

    private void jmiReciprocityRate_sharesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiReciprocityRate_sharesActionPerformed
        if (jFSubjectsReciprocityTable == null) {
            jFSubjectsReciprocityTable = new JFSubjectsReciprocityRateTable(m_trialData);
        }
        if (!jFSubjectsReciprocityTable.isVisible())
            jFSubjectsReciprocityTable.setVisible(true);
    }//GEN-LAST:event_jmiReciprocityRate_sharesActionPerformed

    private void jmiFactoidsHoardingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFactoidsHoardingActionPerformed
        if (m_hoardingTable == null) {
            m_hoardingTable = new JFSubjectsFactoidsHoardingTable(m_trialData);
        }
        if (!m_hoardingTable.isVisible())
            m_hoardingTable.setVisible(true);
    }//GEN-LAST:event_jmiFactoidsHoardingActionPerformed

    private void jmiFactoidsSharingdelay_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFactoidsSharingdelay_tableActionPerformed
        if (jFSubjectsSharingDelayTable == null) {
            jFSubjectsSharingDelayTable = new JFSubjectsFactoidsSharingDelayTable(m_trialData);
        }
        if (!jFSubjectsSharingDelayTable.isVisible())
            jFSubjectsSharingDelayTable.setVisible(true);
    }//GEN-LAST:event_jmiFactoidsSharingdelay_tableActionPerformed

    private void jmiSNAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSNAActionPerformed
        if  (sna == null) {
            sna = new JGSNA(m_trialData);
        }
        sna.setVisible(true);
    }//GEN-LAST:event_jmiSNAActionPerformed

    private void jmiInformationReachedTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationReachedTableActionPerformed
        if (jFSubjectsFactoidsInformationReachedTable == null) {
            jFSubjectsFactoidsInformationReachedTable = new JFSubjectsFactoidsInformationReachedTable(m_trialData);
        }
        if (!jFSubjectsFactoidsInformationReachedTable.isVisible())
            jFSubjectsFactoidsInformationReachedTable.setVisible(true);
    }//GEN-LAST:event_jmiInformationReachedTableActionPerformed

    private void jmiCriticalInformationTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCriticalInformationTableActionPerformed
        if (jFSubjectsCriticalInformationTable==null)
                jFSubjectsCriticalInformationTable = new JFSubjectsCriticalInformationTable(m_trialData);
        if (!jFSubjectsCriticalInformationTable.isVisible())
            jFSubjectsCriticalInformationTable.setVisible(true);
    }//GEN-LAST:event_jmiCriticalInformationTableActionPerformed

    private void jmiIdentificationsBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiIdentificationsBarActionPerformed
        if (jfIDBar==null) {
            jfIDBar = new JFBarIDDistribution("ID Answers", m_trialData.m_solution);
            jfIDBar.addDataSet("WHO", m_trialData.m_subjectsIDsQualityMap.GetWhoIDMap());
            jfIDBar.addDataSet("WHAT", m_trialData.m_subjectsIDsQualityMap.GetWhatIDMap());
            jfIDBar.addDataSet("WHERE", m_trialData.m_subjectsIDsQualityMap.GetWhereIDMap());
            jfIDBar.addDataSet("WHEN (time)", m_trialData.m_subjectsIDsQualityMap.GetWhenTimeIDMap());
            jfIDBar.addDataSet("WHEN (day)", m_trialData.m_subjectsIDsQualityMap.GetWhenDayIDMap());
            jfIDBar.addDataSet("WHEN (month)", m_trialData.m_subjectsIDsQualityMap.GetWhenMonthIDMap());
            jfIDBar.showChart();
        }
        if (!jfIDBar.isVisible())
            jfIDBar.setVisible(true);
    }//GEN-LAST:event_jmiIdentificationsBarActionPerformed

    private void jmiSubjectsIdentificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSubjectsIdentificationsActionPerformed
        if (jfSubjIDTable==null) {
            jfSubjIDTable = new JFSubjectsUnderstandingTable(m_trialData);
        }
        if (!jfSubjIDTable.isVisible())
            jfSubjIDTable.setVisible(true);
    }//GEN-LAST:event_jmiSubjectsIdentificationsActionPerformed

    private void jmiQualityOfIDsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQualityOfIDsActionPerformed
        if (jFQualityOfIDs == null) {
            jFQualityOfIDs = new JFSimpleTimeChart("Quality of IDs");
            m_qualityOfIDsMap = new TreeMap<String, Vector<Point2D.Double>>();
            ConvertQualityOfIDMaptoPoint2DMap(m_trialData.m_subjectsIDsQualityMap,
                                              m_qualityOfIDsMap);
            jFQualityOfIDs.setData("Quality of IDs (by subjects)",
                                   m_qualityOfIDsMap);
            //jFQualityOfIDs.setData("Quality of IDs",
            //                       m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall);
            jFQualityOfIDs.SetDomainAxis(0.0, m_trialData.m_trialInformation.m_durationSec);
            jFQualityOfIDs.SetValueAxis(0.0, 1.0);
            jFQualityOfIDs.showChart();
        }
        if (!jFQualityOfIDs.isVisible())
            jFQualityOfIDs.setVisible(true);
    }//GEN-LAST:event_jmiQualityOfIDsActionPerformed

    private void jmiAwarenessQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAwarenessQActionPerformed
        if ( jFQualityOfAwareness == null ) {
            jFQualityOfAwareness = new JFSimpleTimeChart("Quality of Awareness (inferred by factoids evaluation after Post and Share)");
            m_qualityOfAwarenessMap = new TreeMap<String, Vector<Point2D.Double>>();
            ConvertQualityOfAwarenessMaptoPoint2DMap(m_trialData.m_awarenessQualityMap, m_qualityOfAwarenessMap);
//System.out.println("m_trialData.m_awarenessQualityMap size="+m_trialData.m_awarenessQualityMap.size());            
            jFQualityOfAwareness.setData("Quality of Awareness", m_qualityOfAwarenessMap);
            jFQualityOfAwareness.showChart();
        }
        if (!jFQualityOfAwareness.isVisible()) jFQualityOfAwareness.setVisible(true);
    }//GEN-LAST:event_jmiAwarenessQActionPerformed

    private void jmiAwarenessQTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAwarenessQTableActionPerformed
        if (jfAwarenessQTable==null) {
            jfAwarenessQTable = new JFSubjectsFactoidsAwarenessQTable(m_trialData);
        }
        if (!jfAwarenessQTable.isVisible()) {
            jfAwarenessQTable.setVisible(true);
        }
    }//GEN-LAST:event_jmiAwarenessQTableActionPerformed

    private void jmiMoMSharedInformationAccessibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMSharedInformationAccessibleActionPerformed
        if (jFMoMSharedInformationAccessibleTable==null)
            jFMoMSharedInformationAccessibleTable = new JFMoMSharedInformationAccessibleTable(m_trialData);
        if (!jFMoMSharedInformationAccessibleTable.isVisible())
            jFMoMSharedInformationAccessibleTable.setVisible(true);        
    }//GEN-LAST:event_jmiMoMSharedInformationAccessibleActionPerformed

    private void jmiMoMSharedInformationAccessedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMSharedInformationAccessedActionPerformed
        if (jFMoMSharedInformationAccessedTable==null)
            jFMoMSharedInformationAccessedTable = new JFMoMSharedInformationAccessedTable(m_trialData);
        if (!jFMoMSharedInformationAccessedTable.isVisible())
            jFMoMSharedInformationAccessedTable.setVisible(true);
    }//GEN-LAST:event_jmiMoMSharedInformationAccessedActionPerformed

    private void jmiTeamInteractionsMatrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTeamInteractionsMatrixActionPerformed
        if (jFTeamSocialNetworkTable==null)
            jFTeamSocialNetworkTable = new JFTeamSocialNetworkTable(m_trialData);
        if (!jFTeamSocialNetworkTable.isVisible())
            jFTeamSocialNetworkTable.setVisible(true);
    }//GEN-LAST:event_jmiTeamInteractionsMatrixActionPerformed

    private void jmiMoMSharedRelevantInformationAccessibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMSharedRelevantInformationAccessibleActionPerformed
        if (jFMoMSharedRelevantInformationAccessibleTable==null)
            jFMoMSharedRelevantInformationAccessibleTable = new JFMoMSharedRelevantInformationAccessibleTable(m_trialData);
        if (!jFMoMSharedRelevantInformationAccessibleTable.isVisible())
            jFMoMSharedRelevantInformationAccessibleTable.setVisible(true);
    }//GEN-LAST:event_jmiMoMSharedRelevantInformationAccessibleActionPerformed

    private void jmiMoMSharedRelevantInformationAccessedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMSharedRelevantInformationAccessedActionPerformed
        if (jFMoMSharedRelevantInformationAccessedTable==null)
            jFMoMSharedRelevantInformationAccessedTable = new JFMoMSharedRelevantInformationAccessedTable(m_trialData);
        if (!jFMoMSharedRelevantInformationAccessedTable.isVisible())
            jFMoMSharedRelevantInformationAccessedTable.setVisible(true);
    }//GEN-LAST:event_jmiMoMSharedRelevantInformationAccessedActionPerformed

    private void jmiMoMIDsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMIDsActionPerformed
        if (jFMoMIDScoresTable==null)
            jFMoMIDScoresTable = new JFMoMIDScoresTable(m_trialData);
        if (!jFMoMIDScoresTable.isVisible())
            jFMoMIDScoresTable.setVisible(true);
    }//GEN-LAST:event_jmiMoMIDsActionPerformed

    private void jmiFactoidsActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFactoidsActivityActionPerformed
        if (jFFactoidsActivity==null)         jFFactoidsActivity = new JFFactoidsActivity(m_trialData);
        if (!jFFactoidsActivity.isVisible())  jFFactoidsActivity.setVisible(true);
    }//GEN-LAST:event_jmiFactoidsActivityActionPerformed

    private void jmiCorrectIDsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCorrectIDsActionPerformed
        if (jFCorrectIDs==null) {
            jFCorrectIDs = new JFSimpleTimeChart("Number of correct IDs (scaled max=17)");
            //
            m_correctIDsMap = new TreeMap<String, Vector<Point2D.Double>>();
            m_correctIDsMap.put("WHO",  m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWho);
            m_correctIDsMap.put("WHAT", m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhat);
            m_correctIDsMap.put("WHERE",m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhere);
            m_correctIDsMap.put("WHEN", m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhen);
            m_correctIDsMap.put("WHEN (time)",  m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhenTime);
            m_correctIDsMap.put("WHEN (day)",   m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhenDay);
            m_correctIDsMap.put("WHEN (month)", m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhenMonth);
            m_correctIDsMap.put("OVERALL", m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall);
            //
            jFCorrectIDs.setData("Correct IDs", m_correctIDsMap);
            //jFCorrectIDs.SetValueAxis(0.0, m_trialData.m_subjectsIDsQualityMap.keySet().size()+1);
            jFCorrectIDs.SetDomainAxis(0.0, m_trialData.m_trialInformation.m_durationSec);
            jFCorrectIDs.SetValueAxis(0.0, m_trialData.m_subjectsIDsQualityMap.keySet().size()+1);
            jFCorrectIDs.showChart();
        }
        if (!jFCorrectIDs.isVisible()) jFCorrectIDs.setVisible(true);
    }//GEN-LAST:event_jmiCorrectIDsActionPerformed

    private void jmiPostsAndSharesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPostsAndSharesActionPerformed
        if (jfFormsInterationsBar==null) {
            jfFormsInterationsBar = new JFBarIntegerCategory("Forms of Interaction (Shares and Posts)");
            jfFormsInterationsBar.m_categoryMargin = 0.20;
            for (elicit.message.TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
                if ( m_trialData.m_interactions.m_qualityOfInteractionShareAndPost.get(s.m_personName) !=null )
                    jfFormsInterationsBar.addDataSet("Number of Shares and Posts", s.m_personName, m_trialData.m_interactions.m_qualityOfInteractionShareAndPost.get(s.m_personName).lastElement().totalInteractions);
            }
            jfFormsInterationsBar.showChart();
        }
        if (!jfFormsInterationsBar.isVisible())
            jfFormsInterationsBar.setVisible(true);
    }//GEN-LAST:event_jmiPostsAndSharesActionPerformed

    private void jmiInboundInteractionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInboundInteractionsActionPerformed
        if (jfInboundBar==null) {
            jfInboundBar = new JFBarIntegerCategory("In-degrees (shares received)", false);
            jfInboundBar.m_categoryMargin = 0.10;
            for (elicit.message.TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
                jfInboundBar.addDataSet("Number of Shares Received", s.m_personName, m_trialData.m_overallInteractions.get(s.m_personName).totalSharesRcv);
            }
            //
            jfInboundBar.SetValueAxis(0.0,m_trialData.getMaxSharesRcvOrSentPerSubject()+2);
            //
            jfInboundBar.showChart();
        }
        if (!jfInboundBar.isVisible())
            jfInboundBar.setVisible(true);
    }//GEN-LAST:event_jmiInboundInteractionsActionPerformed

    private void jmiOutboundInteractionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiOutboundInteractionsActionPerformed
        if (jfOutboundBar==null) {
            jfOutboundBar = new JFBarIntegerCategory("Out-degrees (shares sent)", false);
            jfOutboundBar.m_categoryMargin = 0.10;
            for (elicit.message.TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
                jfOutboundBar.addDataSet("Number of Shares Sent", s.m_personName, m_trialData.m_overallInteractions.get(s.m_personName).totalShares);
            }
            //
            jfOutboundBar.SetValueAxis(0.0,m_trialData.getMaxSharesRcvOrSentPerSubject()+2);
            //
            jfOutboundBar.showChart();
        }
        if (!jfOutboundBar.isVisible())
            jfOutboundBar.setVisible(true);
    }//GEN-LAST:event_jmiOutboundInteractionsActionPerformed

    private void jmiFactoidsActivityBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFactoidsActivityBarActionPerformed
        if (jFFactoidsActivityBar==null) {
            jFFactoidsActivityBar = new JFBarIntegerCategory("Factoids Activity (shares + posts)", true);
            //set parameters
            jFFactoidsActivityBar.m_labelRotation = org.jfree.chart.axis.CategoryLabelPositions.UP_90;
            //jFFactoidsActivityBar.m_backgroundColor = java.awt.Color.WHITE;
            jFFactoidsActivityBar.m_lowerMargin = 0.01;
            jFFactoidsActivityBar.m_upperMargin = 0.01;
            jFFactoidsActivityBar.m_categoryMargin = 0.01;
            //
            for ( String factoidMetadata : m_trialData.m_factoidsMessages.keySet() ) {
                int factnbr = FactoidMessage.GetFactoidNumber(factoidMetadata);
                int nbrShares = m_trialData.getNbrShares(factoidMetadata);
                int nbrPosts = m_trialData.getNbrPosts(factoidMetadata);
                if (factnbr<=9)
                    jFFactoidsActivityBar.addDataSet("Factoids","0"+factnbr+" ("+FactoidMessage.getFactoidRelevanceChar(factoidMetadata)+") ", nbrShares+nbrPosts);
                else
                    jFFactoidsActivityBar.addDataSet("Factoids",factnbr+" ("+FactoidMessage.getFactoidRelevanceChar(factoidMetadata)+") ", nbrShares+nbrPosts);
            }
            //
            jFFactoidsActivityBar.showChart();
        }
        if (!jFFactoidsActivityBar.isVisible())
            jFFactoidsActivityBar.setVisible(true);

    }//GEN-LAST:event_jmiFactoidsActivityBarActionPerformed

    private void jmiRateCorrectIDsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiRateCorrectIDsActionPerformed
        if (jFPercentageCorrectIDs==null) {
            jFPercentageCorrectIDs = new JFSimpleTimeChart("Nbr Correct IDs");
            //
            m_correctIDsMap = new TreeMap<String, Vector<Point2D.Double>>();
            m_correctIDsMap.put("WHO",m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWho);
            m_correctIDsMap.put("WHAT",m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhat);
            m_correctIDsMap.put("WHERE",m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhere);
            m_correctIDsMap.put("WHEN",m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhen);
            m_correctIDsMap.put("OVERALL",m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall);
            //
            jFPercentageCorrectIDs.setData("Nbr of correct IDs", m_correctIDsMap);
            jFPercentageCorrectIDs.SetDomainAxis(0.0, m_trialData.m_trialInformation.m_durationSec);
            jFPercentageCorrectIDs.SetValueAxis(0.0, 17.0);
            jFPercentageCorrectIDs.showChart();
        }
        if (!jFPercentageCorrectIDs.isVisible()) jFPercentageCorrectIDs.setVisible(true);

    }//GEN-LAST:event_jmiRateCorrectIDsActionPerformed

    private void jmiMoMShUndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMShUndActionPerformed
        if (jFMoMCorrectSharedUnderstandingTable==null)
            jFMoMCorrectSharedUnderstandingTable = new JFMoMCorrectSharedUnderstanding(m_trialData);
        if (!jFMoMCorrectSharedUnderstandingTable.isVisible())
            jFMoMCorrectSharedUnderstandingTable.setVisible(true);
    }//GEN-LAST:event_jmiMoMShUndActionPerformed

    private void jmiSubjectsFormsInteractionsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSubjectsFormsInteractionsTableActionPerformed
        if (jFSubjectsNatureInteractionsTable==null)
            jFSubjectsNatureInteractionsTable = new JFSubjectsNatureInteractionsTable(m_trialData);
        if (!jFSubjectsNatureInteractionsTable.isVisible())
            jFSubjectsNatureInteractionsTable.setVisible(true);
    }//GEN-LAST:event_jmiSubjectsFormsInteractionsTableActionPerformed

    private void jmiQualityInteractionsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQualityInteractionsTableActionPerformed
        if (jFSubjectsQualityInteractionsTable==null)
            jFSubjectsQualityInteractionsTable = new JFSubjectsQualityInteractionsTable(m_trialData);
        if (!jFSubjectsQualityInteractionsTable.isVisible())
            jFSubjectsQualityInteractionsTable.setVisible(true);
    }//GEN-LAST:event_jmiQualityInteractionsTableActionPerformed

    private void jmiNetworkBalanceTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNetworkBalanceTableActionPerformed
        if (jFNodeBalanceTable==null)
            jFNodeBalanceTable = new JFNodeBalanceTable(m_trialData);
        if (!jFNodeBalanceTable.isVisible())
            jFNodeBalanceTable.setVisible(true);
    }//GEN-LAST:event_jmiNetworkBalanceTableActionPerformed

    private void jmiNetworkBalanceChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNetworkBalanceChartActionPerformed
        if (jFNodeBalanceChart==null) {
            jFNodeBalanceChart = new JFScatterChart("Node IN and OUT Flows (deviation from average)");
            //populate
            //GET average shares_SEND + posts per subject
            double avgOUT = (m_trialData.m_overallStatistics.totalShares+m_trialData.m_overallStatistics.totalPosts)/(double)m_trialData.m_organizationInformation.m_memberList.size();
            //GET average shares_RCV + pulls per subject
            double avgIN  = (m_trialData.m_overallStatistics.totalShares+m_trialData.m_overallStatistics.totalPulls)/(double)m_trialData.m_organizationInformation.m_memberList.size();
            //
            TreeMap<String, Vector<Point2D.Double>> netBalanceMap = new TreeMap<String, Vector<Point2D.Double>>();
            double max = 0.0;
            for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList) {
                double inV = m_trialData.m_overallInteractions.get(s.m_personName).totalSharesRcv + m_trialData.m_overallInteractions.get(s.m_personName).totalPulls - avgIN;
                double outV = (m_trialData.m_overallInteractions.get(s.m_personName).totalShares + m_trialData.m_overallInteractions.get(s.m_personName).totalPosts) - avgOUT;
                Vector<Point2D.Double> v = new Vector<Point2D.Double>();
                v.add(new Point2D.Double(inV, outV));
                netBalanceMap.put(s.m_personName+" ("+s.m_teamPosition+")", v);
                if (inV>max)
                    max = inV;
                if (outV>max)
                    max=outV;
            }
            jFNodeBalanceChart.setData("OUT (deviation form average in Shares_Sent + Posts)", netBalanceMap);
            jFNodeBalanceChart.SetValueAxis(-max-(int)(max*0.2), max+(int)(max*0.2));
            jFNodeBalanceChart.SetLabelX("IN (deviation form average in Shares_Rcvd + Pulls)");
            jFNodeBalanceChart.showChart();
        }
        if (!jFNodeBalanceChart.isVisible()) jFNodeBalanceChart.setVisible(true);

    }//GEN-LAST:event_jmiNetworkBalanceChartActionPerformed

    private void jmiSyncronizationEntropyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSyncronizationEntropyActionPerformed
        //jFSincronizationEntropy
        if (jFSincronizationEntropy==null) {
            jFSincronizationEntropy = new JFSimpleTimeChart("Self-Synchronization (cognitive)");
            //CCSync
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[5].size()!=0)
                m_syncEntropyMap.put("WHEN (m)",m_trialData.m_subjectsIDsQualityMap.m_entropy[5]);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[4].size()!=0)
                m_syncEntropyMap.put("WHEN (d)",m_trialData.m_subjectsIDsQualityMap.m_entropy[4]);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[3].size()!=0)
                m_syncEntropyMap.put("WHEN (t)",m_trialData.m_subjectsIDsQualityMap.m_entropy[3]);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[2].size()!=0)
                m_syncEntropyMap.put("WHERE",m_trialData.m_subjectsIDsQualityMap.m_entropy[2]);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[1].size()!=0)
                m_syncEntropyMap.put("WHAT",m_trialData.m_subjectsIDsQualityMap.m_entropy[1]);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropy[0].size()!=0)
                m_syncEntropyMap.put("WHO",m_trialData.m_subjectsIDsQualityMap.m_entropy[0]);
            //CCSync - uncertainty
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[5].size()!=0)
                m_syncEntropyMap.put("Unc WHEN (m)",m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[5]);
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[4].size()!=0)
                m_syncEntropyMap.put("Unc WHEN (d)",m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[4]);
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[3].size()!=0)
                m_syncEntropyMap.put("Unc WHEN (t)",m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[3]);
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[2].size()!=0)
                m_syncEntropyMap.put("Unc WHERE",m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[2]);
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[1].size()!=0)
                m_syncEntropyMap.put("Unc WHAT",m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[1]);
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[0].size()!=0)
                m_syncEntropyMap.put("Unc WHO",m_trialData.m_subjectsIDsQualityMap.m_uncEntropy[0]);
            //
            if (m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall.size()!=0)
                m_syncEntropyMap.put("Unc OVERALL",m_trialData.m_subjectsIDsQualityMap.m_uncEntropyOverall);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.size()!=0)
                m_syncEntropyMap.put("OVERALL",m_trialData.m_subjectsIDsQualityMap.m_entropyOverall);

            /*
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhenMonth.size()!=0)
                m_syncEntropyMap.put("WHEN (m)",m_trialData.m_subjectsIDsQualityMap.m_entropyWhenMonth);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhenDay.size()!=0)
                m_syncEntropyMap.put("WHEN (d)",m_trialData.m_subjectsIDsQualityMap.m_entropyWhenDay);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhenTime.size()!=0)
                m_syncEntropyMap.put("WHEN (t)",m_trialData.m_subjectsIDsQualityMap.m_entropyWhenTime);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhere.size()!=0)
                m_syncEntropyMap.put("WHERE",m_trialData.m_subjectsIDsQualityMap.m_entropyWhere);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyWhat.size()!=0)
                m_syncEntropyMap.put("WHAT",m_trialData.m_subjectsIDsQualityMap.m_entropyWhat);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyWho.size()!=0)
                m_syncEntropyMap.put("WHO",m_trialData.m_subjectsIDsQualityMap.m_entropyWho);
            if (m_trialData.m_subjectsIDsQualityMap.m_entropyOverall.size()!=0)
                m_syncEntropyMap.put("OVERALL",m_trialData.m_subjectsIDsQualityMap.m_entropyOverall);
            */

            jFSincronizationEntropy.setData("Self-Synchronization (cognitive)", m_syncEntropyMap);
            jFSincronizationEntropy.SetDomainAxis(0.0, m_trialData.m_trialInformation.m_durationSec);
            jFSincronizationEntropy.SetValueAxis(0.0, 1.0);
            jFSincronizationEntropy.showChart();
        }
        if (!jFSincronizationEntropy.isVisible()) jFSincronizationEntropy.setVisible(true);

    }//GEN-LAST:event_jmiSyncronizationEntropyActionPerformed

    private void jmiSyncronizationEntropyTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSyncronizationEntropyTableActionPerformed
        if (jFEntropyTable==null)
            jFEntropyTable = new JFSystemUnderstandingEntropy(m_trialData);
        if (!jFEntropyTable.isVisible())
            jFEntropyTable.setVisible(true);
    }//GEN-LAST:event_jmiSyncronizationEntropyTableActionPerformed

    private void jmiCriticalInformationTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCriticalInformationTable2ActionPerformed
        if (jFSubjectsCriticalInformationTable2==null)
                jFSubjectsCriticalInformationTable2 = new JFSubjectsCriticalInformationTable2(m_trialData);
        if (!jFSubjectsCriticalInformationTable2.isVisible())
            jFSubjectsCriticalInformationTable2.setVisible(true);
    }//GEN-LAST:event_jmiCriticalInformationTable2ActionPerformed

    private void jmiInformationTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInformationTableActionPerformed
        if (jFInformationTable==null)
                jFInformationTable = new JFInformationTable(m_trialData);
        if (!jFInformationTable.isVisible())
            jFInformationTable.setVisible(true);
    }//GEN-LAST:event_jmiInformationTableActionPerformed

    private void jmiMoMCSSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMCSSActionPerformed
        if (jFMoMCSSync==null)
                jFMoMCSSync = new JFMoMCSSync(m_trialData);
        if (!jFMoMCSSync.isVisible())
            jFMoMCSSync.setVisible(true);
    }//GEN-LAST:event_jmiMoMCSSActionPerformed

    private void jmiMoMEffectivenessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMoMEffectivenessActionPerformed
        // TODO add your handling code here:
        if (jFMoMEffectiveness==null)
                jFMoMEffectiveness = new JFMoMEffectiveness(m_trialData);
        if (!jFMoMEffectiveness.isVisible())
            jFMoMEffectiveness.setVisible(true);
    }//GEN-LAST:event_jmiMoMEffectivenessActionPerformed

    private void jmiAVGInfoReachedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAVGInfoReachedActionPerformed
        // TODO add your handling code here:
        if (jFMoMAverageInformationAccessedTable==null)
            jFMoMAverageInformationAccessedTable = new JFMoMAverageInformationAccessedTable(m_trialData);
        if (!jFMoMAverageInformationAccessedTable.isVisible())
            jFMoMAverageInformationAccessedTable.setVisible(true);

    }//GEN-LAST:event_jmiAVGInfoReachedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new JFELICITMetricsVisualizer(args[0]).setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame JFFactoids;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JDialog jdResult;
    public javax.swing.JFrame jfProcessing;
    private javax.swing.JLabel jlADDs;
    private javax.swing.JLabel jlADDsHour;
    private javax.swing.JLabel jlAnswer;
    private javax.swing.JLabel jlDate;
    private javax.swing.JLabel jlDuration;
    private javax.swing.JLabel jlFactKey;
    private javax.swing.JLabel jlFactKeyMain;
    private javax.swing.JLabel jlFactKeyRatio;
    private javax.swing.JLabel jlFactKeyRatioMain;
    private javax.swing.JLabel jlFactMisinfRatio;
    private javax.swing.JLabel jlFactMisinfRatioMain;
    private javax.swing.JLabel jlFactMisinfo;
    private javax.swing.JLabel jlFactMisinfoMain;
    private javax.swing.JLabel jlFactNoise;
    private javax.swing.JLabel jlFactNoiseMain;
    private javax.swing.JLabel jlFactNoiseRatio;
    private javax.swing.JLabel jlFactNoiseRatioMain;
    private javax.swing.JLabel jlFactSupp;
    private javax.swing.JLabel jlFactSuppMain;
    private javax.swing.JLabel jlFactSuppRatio;
    private javax.swing.JLabel jlFactSuppRatioMain;
    private javax.swing.JLabel jlFactTotal;
    private javax.swing.JLabel jlFactTotal1;
    private javax.swing.JLabel jlFactTotal3;
    private javax.swing.JLabel jlFactTotalMain;
    private javax.swing.JLabel jlFactoidSetID;
    private javax.swing.JLabel jlFileInput;
    private javax.swing.JLabel jlFileName;
    private javax.swing.JLabel jlFinish;
    private javax.swing.JLabel jlIDs;
    private javax.swing.JLabel jlIDsHour;
    private javax.swing.JLabel jlLine;
    private javax.swing.JLabel jlOrganizationType;
    private javax.swing.JLabel jlPosts;
    private javax.swing.JLabel jlPostsHour;
    private javax.swing.JLabel jlPulls;
    private javax.swing.JLabel jlPullsHour;
    private javax.swing.JLabel jlShares;
    private javax.swing.JLabel jlSharesHour;
    private javax.swing.JLabel jlStart;
    private javax.swing.JMenu jmAwarenessUnderstanding;
    private javax.swing.JMenu jmInformationDomain;
    private javax.swing.JMenu jmMoM;
    private javax.swing.JMenu jmSNA;
    private javax.swing.JMenuItem jmiAVGInfoReached;
    private javax.swing.JMenuItem jmiAwarenessQ;
    private javax.swing.JMenuItem jmiAwarenessQTable;
    private javax.swing.JMenuItem jmiCorrectIDs;
    private javax.swing.JMenuItem jmiCriticalInformationTable;
    private javax.swing.JMenuItem jmiCriticalInformationTable2;
    private javax.swing.JMenuItem jmiFactoidsActivity;
    private javax.swing.JMenuItem jmiFactoidsActivityBar;
    private javax.swing.JMenuItem jmiFactoidsHoarding;
    private javax.swing.JMenuItem jmiFactoidsSharingdelay_table;
    private javax.swing.JMenuItem jmiFormsInteraction;
    private javax.swing.JMenuItem jmiFormsInteractionPostsTable;
    private javax.swing.JMenuItem jmiFormsInteractionSharesPostsTable;
    private javax.swing.JMenuItem jmiFormsInteractionSharesTable;
    private javax.swing.JMenuItem jmiIdentificationsBar;
    private javax.swing.JMenuItem jmiInboundInteractions;
    private javax.swing.JMenuItem jmiInformationAccessible;
    private javax.swing.JMenuItem jmiInformationAccessibleServer;
    private javax.swing.JMenuItem jmiInformationAccessibleServerTable;
    private javax.swing.JMenuItem jmiInformationAccessibleTable;
    private javax.swing.JMenuItem jmiInformationReached;
    private javax.swing.JMenuItem jmiInformationReachedTable;
    private javax.swing.JMenuItem jmiInformationTable;
    private javax.swing.JMenuItem jmiInteractionsMatrix;
    private javax.swing.JMenuItem jmiMoMCSS;
    private javax.swing.JMenuItem jmiMoMEffectiveness;
    private javax.swing.JMenuItem jmiMoMIDs;
    private javax.swing.JMenuItem jmiMoMShUnd;
    private javax.swing.JMenuItem jmiMoMSharedInformationAccessed;
    private javax.swing.JMenuItem jmiMoMSharedInformationAccessible;
    private javax.swing.JMenuItem jmiMoMSharedRelevantInformationAccessed;
    private javax.swing.JMenuItem jmiMoMSharedRelevantInformationAccessible;
    private javax.swing.JMenuItem jmiNetworkBalanceChart;
    private javax.swing.JMenuItem jmiNetworkBalanceTable;
    private javax.swing.JMenuItem jmiOutboundInteractions;
    private javax.swing.JMenuItem jmiPostsAndShares;
    private javax.swing.JMenuItem jmiQualityInteractionsTable;
    private javax.swing.JMenuItem jmiQualityOfIDs;
    private javax.swing.JMenuItem jmiQualityOfInteractions_post;
    private javax.swing.JMenuItem jmiQualityOfInteractions_share;
    private javax.swing.JMenuItem jmiQualityOfInteractions_share_and_post;
    private javax.swing.JMenuItem jmiRateCorrectIDs;
    private javax.swing.JMenuItem jmiReciprocityRate_shares;
    private javax.swing.JMenuItem jmiSNA;
    private javax.swing.JMenuItem jmiSubjectsFormsInteractionsTable;
    private javax.swing.JMenuItem jmiSubjectsIdentifications;
    private javax.swing.JMenuItem jmiSyncronizationEntropy;
    private javax.swing.JMenuItem jmiSyncronizationEntropyTable;
    private javax.swing.JMenuItem jmiTeamInteractionsMatrix;
    private javax.swing.JPanel jpOptions;
    // End of variables declaration//GEN-END:variables
}
