/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFAgilityMapChart.java
 *
 * Created on Jan 25, 2012, 12:25:18 PM
 */

package agility;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.*;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.data.xy.*;
//

/**
 *
 * @author marcomanso
 */
public class JFAgilityMapChart10x10 extends javax.swing.JFrame {

    LinkedList<JCheckBox> boxList = new LinkedList<JCheckBox>();
    int m_WIDTH = 120;
    int m_HEIGTH = 25;
    //
    // CHART
    JFreeChart m_chart;
    int CHART_WIDTH  = 400;
    int CHART_HEIGHT = 400;
    String m_title = " ";
    String m_setYTitle = "Measurement (0..1)";
    String m_setXTitle = "Timeliness (0..1)";
    //
    public final static int MAP_ROWS_COLUMNS = 10;
    XYZDataset m_dataset = null;
    NumberFormat m_formatter = new DecimalFormat ( "0.00" ) ;
    //
    ExperimentDataList m_dataList = null;
    public final static double MAX_VALUE = 1.0;
    double m_agility = 0.0;
    double m_relativeAgility = 0.0;
    //

    /** Creates new form JFAgilityMapChart */
    public JFAgilityMapChart10x10() {
        initComponents();
    }

    public void setParameters (ExperimentDataList dataList) {
        m_dataList = dataList;
        jlAgility.setText("-");
        jlRelativeAgility.setText("-");
        jlAlwaysSuccessful.setText("-");
        jlSuccessful.setText("-");
        jlDowngrade.setText("-");
        jlRelativeDowngrade.setText("-");
        //
        jcbMeasurement.removeAllItems();
        for (String s : dataList.getExperimentMeasures()) {
            jcbMeasurement.addItem(s);
        }
        //
        jcbApproach.removeAllItems();
        for ( String s : dataList.getExperimentApproaches() ) {
            jcbApproach.addItem(s);
        }
        //
        boxList.clear();
        for ( String s : dataList.getExperimentManipulations() ) {
            JCheckBox box = new JCheckBox();
            box.setText(s);
            box.setSize(m_WIDTH,m_HEIGTH);
            box.setSelected(true);
            boxList.add(box);
        }
        for (JCheckBox box : boxList)
            jpManipulations.add(box);
    }
    private boolean isSelectedExperimentType(String experimentType) {
        for ( JCheckBox box : boxList )
            if (box.getText().equalsIgnoreCase(experimentType) && box.isSelected())
                return true;
        return false;
    }

    //CHART STUFF
    private static int getIndexX(double value) {
        int x = (int)(value*10);
        if (x>10) x=10;
        return x;
    }
    private static int getIndexY(int value) {
        int y = value;
        if (y>10) y=10;
        return y;
    }
    private static void fillMatrixSeries(double[][] m, int x, int y, double v) {
        //fill x and y
        for (int i=0; i<=x; i++)
            for (int j=0; j<=y; j++)
                m[i][j]=v;
        
    }
    private static void buildMatrixSeries(double[][] m, MatrixSeries map) {
        for (int i=0; i<m.length; i++)
            for (int j=0; j<m[i].length; j++)
                map.update(j+1, i+1, m[i][j]);
    }
    //
    public static double roundTwoDecimals(double d) {
        if (Double.isNaN(d))
            return 0.0;
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public static void buildAgilityMap(LinkedList<double[][]> list, double[][] agilityMap) {
        int nbrTrials = list.size();
        //add and normalize
        for (double[][] m : list)
            for (int i=0; i<m.length; i++)
                for (int j=0; j<m[i].length; j++)
                    agilityMap[i][j]+=m[i][j];
                    //agilityMap[i][j]+=m[i][j]/(double)nbrTrials;
        //
        //due to rounding errors, it is better to
        //make the for-loop again to make the division (nbrTials)
        for (int i=0; i<agilityMap.length; i++)
            for (int j=0; j<agilityMap[i].length; j++)
                agilityMap[i][j]=agilityMap[i][j]/(double)nbrTrials;
    }
    public static void zeroAll(double[][] m) {
        for (int i=0; i<m.length; i++)
            for (int j=0; j<m[i].length; j++)
                m[i][j]=0.0;
    }
    public static void buildMatrix(ExperimentData data, String measurement, double[][] m) {
        zeroAll(m);
        //MatrixSeries m = new MatrixSeries((String)(jcbMeasurement.getSelectedItem()),11,11);
        //m.zeroAll();
        Score score = data.getMeasurementDataSet(measurement);
        for (int y=0; y<m.length; y++) {
            if (score.getScore(y)!=null) {
                int x = getIndexX(score.getScore(y));
                int yV = getIndexY(y);
                fillMatrixSeries(m, x, yV, MAX_VALUE);
            }
        }
    }
    public static double calculateAgility (double[][] m) {
        int totalNbrCells = m.length*m[0].length;
        double value = 0.0;
        for (int i=0; i<m.length; i++)
            for (int j=0; j<m[i].length; j++)
                value+=m[i][j];
        value=value/MAX_VALUE/(double)totalNbrCells;
        return value;
    }
    public static double calculateRelativeAgility (LinkedList<double[][]> list) {
        int totalNbrCells = 0;
        int nbrRuns = list.size();
        double nbrCellsBaseline = 0.0;
        double value = 0.0;
        boolean isFirst = true;
        for ( double[][] m : list ) {
            //first is baseline
            if (isFirst) {
                totalNbrCells = m.length*m[0].length;
                isFirst = false;
                for (int i=0; i<m.length; i++)
                    for (int j=0; j<m[i].length; j++)
                        nbrCellsBaseline+=m[i][j];
                //System.out.println("nbrCellsBasel="+nbrCellsBaseline);
                if (nbrCellsBaseline==0) return 0.0;
            }
            else {
                double nbrCells=0.0;
                for (int i=0; i<m.length; i++)
                    for (int j=0; j<m[i].length; j++)
                        nbrCells+=m[i][j];
                //System.out.println("nbrCells="+nbrCells);
                value+=nbrCells/nbrCellsBaseline/(double)(nbrRuns-1);
            }
        }
        return value;
    }
    public static double calculateAlwaysCovered (double[][] m) {
        int totalNbrCells = m.length*m[0].length;
        double value = 0.0;
        for (int i=0; i<m.length; i++)
            for (int j=0; j<m[i].length; j++)
                //if (m[i][j]==MAX_VALUE)
                if (MAX_VALUE == roundTwoDecimals(m[i][j]))
                    value+=1.0;
        value=value/(double)totalNbrCells;
        return value;
    }
    public static double calculateCoverage (double[][] m) {
        int totalNbrCells = m.length*m[0].length;
        double value = 0.0;
        for (int i=0; i<m.length; i++)
            for (int j=0; j<m[i].length; j++)
                if (m[i][j]!=0.0)
                    value+=1.0;
        value=value/(double)totalNbrCells;
        return value;
    }
    public static double calculateDowngrade (double[][] m) {
        int totalNbrCells = m.length*m[0].length;
        double value = 0.0;
        for (int i=0; i<m.length; i++)
            for (int j=0; j<m[i].length; j++)
                //if (m[i][j]>0.0 && m[i][j]<MAX_VALUE)
                //if (0.0<roundTwoDecimals(m[i][j]) && MAX_VALUE>roundTwoDecimals(m[i][j]))
                if (0.0!=roundTwoDecimals(m[i][j]) && MAX_VALUE!=roundTwoDecimals(m[i][j]))
                    value+=1.0;
        value=value/(double)totalNbrCells;
        return value;
    }
    public static double calculateRelativeDowngrade (double[][] m) {
        double downgrade = calculateDowngrade(m);
        double coverage = calculateCoverage(m);
        if (coverage!=0.0) {
            return downgrade/coverage;
        }
        return 0.0;
    }

    private void fillDataSet() {
        //what approach?  what measurements?
        //set data to dataset
        LinkedList<double[][]> list = new LinkedList<double[][]>();
        //
        String approach = (String)(jcbApproach.getSelectedItem());
        String measurement = (String)(jcbMeasurement.getSelectedItem());
        for (ExperimentData data : m_dataList) {
            //parse each type of experiment
            if (isSelectedExperimentType(data.m_agilitySetup))
            {
                if (data.m_approachName.equals(approach)) {
                    double[][] m = new double[MAP_ROWS_COLUMNS][MAP_ROWS_COLUMNS];
                    buildMatrix(data, measurement, m);
                    list.add(m);
                }
            }
        }
        //
        //System.out.println("nbr logs count: "+list.size());

        // build AGILITY MAP
        double[][] agilityMap = new double[MAP_ROWS_COLUMNS][MAP_ROWS_COLUMNS];
        zeroAll(agilityMap);
        buildAgilityMap(list, agilityMap);
        MatrixSeries map = new MatrixSeries((String)(jcbMeasurement.getSelectedItem()),MAP_ROWS_COLUMNS+1,MAP_ROWS_COLUMNS+1);
        map.zeroAll();
        buildMatrixSeries(agilityMap, map);
        //
        //calculate AGILITY measures
        NumberFormat formatter = new DecimalFormat ( "0.00" ) ;
        jlAgility.setText(formatter.format(calculateAgility(agilityMap)));
        jlRelativeAgility.setText(formatter.format(calculateRelativeAgility(list)));
        jlAlwaysSuccessful.setText(formatter.format(calculateAlwaysCovered(agilityMap)));
        jlSuccessful.setText(formatter.format(calculateCoverage(agilityMap)));
        jlDowngrade.setText(formatter.format(calculateDowngrade(agilityMap)));
        jlRelativeDowngrade.setText(formatter.format(calculateRelativeDowngrade(agilityMap)));
        //
        MatrixSeriesCollection col = new MatrixSeriesCollection(map);
        m_dataset = col;
    }
    public JFreeChart buildPlot (XYZDataset dataset, String xTitle, String yTitle) {
        //private static JFreeChart createChart(XYZDataset dataset) {
        NumberAxis xAxis = new NumberAxis(xTitle);
        //xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        NumberAxis yAxis = new NumberAxis(yTitle);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
        //
        XYBlockRenderer renderer = new XYBlockRenderer();
        PaintScale scale = new GrayPaintScale(0.0, 1.0);
        renderer.setPaintScale(scale);
        //
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        //
        JFreeChart chart = new JFreeChart(m_title, plot);
        //chart.removeLegend();
        chart.setBackgroundPaint(Color.white);
        return chart;
    }

    public void redrawPlot() {
        //
        jpChart.removeAll();
        //
        fillDataSet();
        //
        ChartPanel cp = new ChartPanel(buildPlot(m_dataset, m_setXTitle, m_setYTitle), true, true, true, true, true);
        //cp.addChartMouseListener(this);
        cp.setPreferredSize(new java.awt.Dimension(CHART_WIDTH, CHART_HEIGHT));
        cp.setDisplayToolTips(true);
        cp.setInitialDelay(0);
        //cp.setDismissDelay(10);
        cp.setReshowDelay(0);

        jpChart.add(cp,java.awt.BorderLayout.CENTER);
        jpChart.updateUI();
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jcbApproach = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jcbMeasurement = new javax.swing.JComboBox();
        jpChart = new javax.swing.JPanel();
        jpManipulations = new javax.swing.JPanel();
        jbRedraw = new javax.swing.JButton();
        jbExport = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jlAgility = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jlRelativeAgility = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlSuccessful = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlAlwaysSuccessful = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jlDowngrade = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jlRelativeDowngrade = new javax.swing.JLabel();

        jFileChooser.setDialogTitle("SELECT DIRECTORY");
        jFileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Approach:");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel2.add(jLabel2);

        jcbApproach.setMaximumSize(null);
        jcbApproach.setMinimumSize(new java.awt.Dimension(150, 25));
        jcbApproach.setPreferredSize(new java.awt.Dimension(150, 25));
        jcbApproach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbApproachActionPerformed(evt);
            }
        });
        jPanel2.add(jcbApproach);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Measurement:");
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel2.add(jLabel1);

        jcbMeasurement.setMaximumSize(null);
        jcbMeasurement.setMinimumSize(new java.awt.Dimension(150, 25));
        jcbMeasurement.setPreferredSize(new java.awt.Dimension(150, 25));
        jcbMeasurement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMeasurementActionPerformed(evt);
            }
        });
        jPanel2.add(jcbMeasurement);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jpChart.setPreferredSize(new java.awt.Dimension(400, 400));
        jpChart.setSize(new java.awt.Dimension(400, 400));
        jpChart.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jpChart, java.awt.BorderLayout.CENTER);

        jpManipulations.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));
        jpManipulations.setLayout(new javax.swing.BoxLayout(jpManipulations, javax.swing.BoxLayout.Y_AXIS));

        jbRedraw.setText("REDRAW");
        jbRedraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRedrawActionPerformed(evt);
            }
        });
        jpManipulations.add(jbRedraw);

        jbExport.setText("Export to CSV");
        jbExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportActionPerformed(evt);
            }
        });
        jpManipulations.add(jbExport);

        jPanel1.add(jpManipulations, java.awt.BorderLayout.WEST);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Agility Map Results"));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel3.setText("Abs. Agility");
        jLabel3.setMinimumSize(new java.awt.Dimension(120, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel3.add(jLabel3);

        jlAgility.setText("-");
        jlAgility.setMinimumSize(new java.awt.Dimension(120, 30));
        jlAgility.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel3.add(jlAgility);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel4.setText("Relative Agility");
        jPanel3.add(jLabel4);

        jlRelativeAgility.setText("-");
        jPanel3.add(jlRelativeAgility);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel5.setText("% Coverage (0..1)");
        jLabel5.setMinimumSize(new java.awt.Dimension(120, 30));
        jLabel5.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel3.add(jLabel5);

        jlSuccessful.setText("-");
        jPanel3.add(jlSuccessful);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel6.setText("% Always Cov. (0..1)");
        jLabel6.setMinimumSize(new java.awt.Dimension(120, 30));
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel3.add(jLabel6);

        jlAlwaysSuccessful.setText("-");
        jPanel3.add(jlAlwaysSuccessful);

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel7.setText("% Downgrade (0..1)");
        jLabel7.setMinimumSize(new java.awt.Dimension(120, 30));
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel3.add(jLabel7);

        jlDowngrade.setText("-");
        jPanel3.add(jlDowngrade);

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel8.setText("% Rel. Downg. (0..1)");
        jLabel8.setMinimumSize(new java.awt.Dimension(120, 30));
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel3.add(jLabel8);

        jlRelativeDowngrade.setText("-");
        jPanel3.add(jlRelativeDowngrade);

        jPanel1.add(jPanel3, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbRedrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRedrawActionPerformed
        redrawPlot();
    }//GEN-LAST:event_jbRedrawActionPerformed

    private void jbExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportActionPerformed
        int returnVal = jFileChooser.showOpenDialog(this);
        if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION )
        {
            LinkedList<String> list = new LinkedList<String>();
            String dir = jFileChooser.getSelectedFile().getAbsolutePath();

            try {
                for ( JCheckBox box : boxList )
                    if (box.isSelected()) {
                            list.addFirst(box.getText());
                    }//if box
                if (list.size()!=0)
                    ExportAgilityToCSV.ExportToCSVPerManipulation(dir, list, m_dataList);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating file in:\n"+dir, "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }//if returnVal
    }//GEN-LAST:event_jbExportActionPerformed

    private void jcbApproachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbApproachActionPerformed
        redrawPlot();
    }//GEN-LAST:event_jcbApproachActionPerformed

    private void jcbMeasurementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMeasurementActionPerformed
        redrawPlot();
    }//GEN-LAST:event_jcbMeasurementActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFAgilityMapChart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jbExport;
    private javax.swing.JButton jbRedraw;
    private javax.swing.JComboBox jcbApproach;
    private javax.swing.JComboBox jcbMeasurement;
    private javax.swing.JLabel jlAgility;
    private javax.swing.JLabel jlAlwaysSuccessful;
    private javax.swing.JLabel jlDowngrade;
    private javax.swing.JLabel jlRelativeAgility;
    private javax.swing.JLabel jlRelativeDowngrade;
    private javax.swing.JLabel jlSuccessful;
    private javax.swing.JPanel jpChart;
    private javax.swing.JPanel jpManipulations;
    // End of variables declaration//GEN-END:variables

}
