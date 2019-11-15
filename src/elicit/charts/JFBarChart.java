/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JF2DataSetsTimeChart.java
 *
 * Created on 6/Mai/2009, 23:26:43
 */
package elicit.charts;

//
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.TreeMap;
import java.text.NumberFormat;
//
import metrics.informationquality.InformationQuality.*;
//
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

//import org.jfree.chart.labels.StandardXYItemLabelGenerator;
//import org.jfree.chart.labels.StandardXYToolTipGenerator;
//import org.jfree.chart.labels.XYItemLabelGenerator;
//import org.jfree.chart.plot.CombinedDomainXYPlot;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
//import org.jfree.chart.renderer.xy.XYStepRenderer;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
/**
 *
 * @author mmanso
 */
public abstract class JFBarChart extends javax.swing.JFrame {
    //
    boolean m_setAxis = false;
    double  m_minValue=0.0;
    double  m_maxValue=0.0;
    // attributes for chart
    public CategoryLabelPositions m_labelRotation = CategoryLabelPositions.STANDARD;
    public Color m_backgroundColor = Color.LIGHT_GRAY;
    public double m_lowerMargin = 0.02;
    public double m_upperMargin = 0.02;
    public double m_categoryMargin = 0.05;
    //
    boolean m_verticalChart = true;
    //
    JFreeChart m_chart;
    CategoryDataset m_dataset;
    //
    String m_title;
    String m_setYTitle = "";
    //
    double m_maxTime = 0.0;
    //
    NumberFormat m_formatter = new DecimalFormat("0.00");

    /** Creates new form JF2DataSetsTimeChart */
    public JFBarChart(String title) {
        initComponents();
        m_title = title;
        setTitle(m_title);
    }

    public JFBarChart(String title, boolean vertical) {
        this(title);
        m_verticalChart = vertical;
    }

    public void SetValueAxis(double minValue, double maxValue) {
        m_setAxis=true;
        m_minValue=minValue;
        m_maxValue=maxValue;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // DATA RELATED
    //
    public abstract void addDataSet(String index, TreeMap<String, Integer> data);

    public abstract CategoryDataset makeDataSet();

    protected abstract void ExportData();

    ////////////////////////////////////////////////////////////////////////
    //
    // MAIN CHART
    //
    public void showChart() {

        m_dataset = makeDataSet();
        //
        if (m_verticalChart)
            m_chart = ChartFactory.createBarChart(
                    m_title,
                    "Categories",
                    "Values",
                    m_dataset,
                    PlotOrientation.VERTICAL,
                    true, //legend
                    true, //tooltip
                    false); //URL
        else
            m_chart = ChartFactory.createBarChart(
                    m_title,
                    "Categories",
                    "Values",
                    m_dataset,
                    PlotOrientation.HORIZONTAL,
                    true, //legend
                    true, //tooltip
                    false); //URL

        m_chart.getCategoryPlot().setBackgroundPaint(m_backgroundColor);
        //set axis?
        ValueAxis axis = m_chart.getCategoryPlot().getRangeAxis();
        if (m_setAxis)
        {
            axis.setAutoRange(false);
            axis.setRange(m_minValue, m_maxValue);
        }
        TickUnits units = (TickUnits) NumberAxis.createIntegerTickUnits();
        axis.setStandardTickUnits(units);
        //margin between categories
        CategoryAxis catAxis = m_chart.getCategoryPlot().getDomainAxis();
        catAxis.setCategoryLabelPositions(m_labelRotation);
        catAxis.setLowerMargin(m_lowerMargin);
        catAxis.setUpperMargin(m_upperMargin);
        catAxis.setCategoryMargin(m_categoryMargin);

        //BarRenderer renderer = (BarRenderer) m_chart.getCategoryPlot().getRenderer();
        //renderer.setItemMargin(m_categoryMargin);
        //

        ChartPanel cp = new ChartPanel(m_chart, true, true, true, true, true);
        //cp.addChartMouseListener(this);
        cp.setPreferredSize(new java.awt.Dimension(500, 500));
        cp.setDisplayToolTips(true);
        cp.setInitialDelay(0);
        //cp.setDismissDelay(10);
        cp.setReshowDelay(0);
        jpChart.add(cp, java.awt.BorderLayout.CENTER);
        //jpChart.add(m_chart, java.awt.BorderLayout.CENTER);
        jpChart.updateUI();

        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanelTOP = new javax.swing.JPanel();
        jbExportData = new javax.swing.JButton();
        jPanelTimeSlider = new javax.swing.JPanel();
        jPanelTimeSlider.setVisible(false);
        jsTime = new javax.swing.JSlider();
        jlTimeSlider = new javax.swing.JLabel();
        jPanelLEFT = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jlCrossX = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlCrossY = new javax.swing.JLabel();
        jpChart = new javax.swing.JPanel();

        setTitle("Time Chart");

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanelTOP.setBackground(new java.awt.Color(204, 204, 204));
        jPanelTOP.setOpaque(false);
        jPanelTOP.setLayout(new javax.swing.BoxLayout(jPanelTOP, javax.swing.BoxLayout.LINE_AXIS));

        jbExportData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_download_48.png"))); // NOI18N
        jbExportData.setText("Save Data");
        jbExportData.setPreferredSize(new java.awt.Dimension(160, 60));
        jbExportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportDataActionPerformed(evt);
            }
        });
        jPanelTOP.add(jbExportData);

        jPanelTimeSlider.setBorder(javax.swing.BorderFactory.createTitledBorder("Time (Sec)"));
        jPanelTimeSlider.setMaximumSize(new java.awt.Dimension(150, 60));
        jPanelTimeSlider.setMinimumSize(new java.awt.Dimension(150, 60));
        jPanelTimeSlider.setPreferredSize(new java.awt.Dimension(150, 60));
        jPanelTimeSlider.setRequestFocusEnabled(false);
        jPanelTimeSlider.setLayout(new javax.swing.BoxLayout(jPanelTimeSlider, javax.swing.BoxLayout.LINE_AXIS));

        jsTime.setMajorTickSpacing(5);
        jsTime.setPaintTicks(true);
        jsTime.setValue(100);
        jsTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsTimeStateChanged(evt);
            }
        });
        jPanelTimeSlider.add(jsTime);

        jlTimeSlider.setFont(jlTimeSlider.getFont().deriveFont((float)8));
        jlTimeSlider.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlTimeSlider.setText("(x-axis)");
        jlTimeSlider.setMaximumSize(new java.awt.Dimension(40, 20));
        jlTimeSlider.setMinimumSize(new java.awt.Dimension(40, 20));
        jlTimeSlider.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelTimeSlider.add(jlTimeSlider);

        jPanelTOP.add(jPanelTimeSlider);

        jPanel3.add(jPanelTOP, java.awt.BorderLayout.NORTH);

        jPanelLEFT.setLayout(new javax.swing.BoxLayout(jPanelLEFT, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("TOP Crosshair"));
        jPanel6.setAlignmentX(0.0F);
        jPanel6.setAlignmentY(0.0F);
        jPanel6.setMaximumSize(new java.awt.Dimension(156, 60));
        jPanel6.setMinimumSize(new java.awt.Dimension(156, 60));
        jPanel6.setPreferredSize(new java.awt.Dimension(156, 60));
        jPanel6.setRequestFocusEnabled(false);
        jPanel6.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("X:");
        jLabel18.setMaximumSize(null);
        jLabel18.setMinimumSize(null);
        jLabel18.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jLabel18);

        jlCrossX.setMaximumSize(null);
        jlCrossX.setMinimumSize(null);
        jlCrossX.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jlCrossX);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Y:");
        jLabel19.setMaximumSize(null);
        jLabel19.setMinimumSize(null);
        jLabel19.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jLabel19);

        jlCrossY.setMaximumSize(null);
        jlCrossY.setMinimumSize(null);
        jlCrossY.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jlCrossY);

        jPanel1.add(jPanel6);

        jPanelLEFT.add(jPanel1);

        jPanel3.add(jPanelLEFT, java.awt.BorderLayout.WEST);

        jpChart.setPreferredSize(new java.awt.Dimension(400, 400));
        jpChart.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jpChart, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel3);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jsTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsTimeStateChanged
        jlTimeSlider.setText(Integer.toString((int) (m_maxTime * jsTime.getValue() / 100.0)) + " (s)");
    }//GEN-LAST:event_jsTimeStateChanged

    private void jbExportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportDataActionPerformed
        ExportData();
}//GEN-LAST:event_jbExportDataActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelLEFT;
    protected javax.swing.JPanel jPanelTOP;
    private javax.swing.JPanel jPanelTimeSlider;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbExportData;
    private javax.swing.JLabel jlCrossX;
    private javax.swing.JLabel jlCrossY;
    private javax.swing.JLabel jlTimeSlider;
    private javax.swing.JPanel jpChart;
    private javax.swing.JSlider jsTime;
    // End of variables declaration//GEN-END:variables
}
