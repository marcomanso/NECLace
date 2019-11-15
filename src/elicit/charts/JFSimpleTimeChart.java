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

import elicit.exportdata.ExportData2D;
//
import java.awt.Component;
import java.awt.geom.Point2D;
import java.awt.Font;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.JCheckBox;
import java.text.NumberFormat;
//
import metrics.informationquality.InformationQuality.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;

import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
//import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author mmanso
 */
public class JFSimpleTimeChart extends javax.swing.JFrame
        implements ChartProgressListener {

    public static int INFOQ_ACCESSIBILITY_INDEX_RELEVANT = 0;
    public static int INFOQ_ACCESSIBILITY_INDEX_MISINFO = 1;
    public static int INFOQ_ACCESSIBILITY_INDEX_ALL = 2;
    //
    JFreeChart m_chart;
    String m_title;
    String m_setYTitle = "y";
    String m_setXTitle = "x";
    //XYStepRenderer m_renderer = null;
    //
    boolean m_setAxis = false;
    double  m_minValue=0.0;
    double  m_maxValue=0.0;
    boolean m_setDomainAxis = false;
    double  m_minDomainValue=0.0;
    double  m_maxDomainValue=0.0;

    protected TreeMap<String, Vector<Point2D.Double>> m_dataSetMap;
    protected Vector<ArrayList<XYPointerAnnotation>> m_annotationsSet = new Vector<ArrayList<XYPointerAnnotation>>();
    //
    double m_maxTime = 0.0;
    //
    NumberFormat m_formatter = new DecimalFormat ( "0.00" ) ;

    /** Creates new form JF2DataSetsTimeChart */
    public JFSimpleTimeChart(String title) {
        //m_renderer = new XYStepRenderer();
        initComponents();
        m_title = title;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // UTILS
    //
    public static double getYValueAtXAxis (double x, Vector<Point2D.Double> dataSet) {
        double value = 0.0;
        for (Point2D.Double data : dataSet) {
            if (data.x > x )
                break;
            value = data.y;
        }
        return value;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // DATA RELATED
    //
    public void setData(String set1Ytitle,
                        TreeMap<String, Vector<Point2D.Double>> dataSet1) {
        m_setYTitle = set1Ytitle;
        setDataSet(dataSet1);
    }

    public void setDataSet(TreeMap<String, Vector<Point2D.Double>> dataSet) {
        m_dataSetMap = dataSet;
        for (String name : m_dataSetMap.keySet() ) {
            JCheckBox jcb = new javax.swing.JCheckBox();
            jcb.setText(name);
            jcb.setPreferredSize(new java.awt.Dimension(110, 20));
            jcb.setMinimumSize(new java.awt.Dimension(110, 20));
            jcb.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jcbAccessIndexSubjectActionPerformed(evt);
                }
            });
            jpIndividuals.add(jcb);
            m_annotationsSet.add(new ArrayList<XYPointerAnnotation>());
        }
    }
    
    public void SetValueAxis(double minValue, double maxValue) {
        m_setAxis=true;
        m_minValue=minValue;
        m_maxValue=maxValue;
    }

    public void SetDomainAxis(double minValue, double maxValue) {
        m_setDomainAxis=true;
        m_minDomainValue=minValue;
        m_maxDomainValue=maxValue;
    }

    public void SetLabelX (String xLabel) {
        m_setXTitle = xLabel;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PANELS
    //
    public javax.swing.JPanel buildDataPanel (String label) {
        javax.swing.JPanel p = new javax.swing.JPanel();
        //
        javax.swing.JLabel jl = new javax.swing.JLabel();
        jl.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()-1f));
        jl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jl.setText(label);
        jl.setMaximumSize(new java.awt.Dimension(45, 8));
        jl.setMinimumSize(new java.awt.Dimension(45, 8));
        jl.setPreferredSize(new java.awt.Dimension(45, 8));
        //
        javax.swing.JLabel jlR = new javax.swing.JLabel();
        jlR.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()-1f));
        jlR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlR.setText("-");
        jlR.setPreferredSize(new java.awt.Dimension(60, 8));
        //
        //
        p.add(jl);
        p.add(jlR);
        //
        return p;
    }

    public void updatePanel (javax.swing.JPanel p, double value) {
        javax.swing.JLabel l = (javax.swing.JLabel)p.getComponent(1);
        l.setText(m_formatter.format(value));
    }

    //right panel - with metrics data
    public void buildDataSet () {
        for (String name : m_dataSetMap.keySet() ) {
            jpSubjMtrcs.add(buildDataPanel(name));
        }
        //set parent dimensions
        java.awt.Dimension d = jpSubjectsMetrics.getPreferredSize();
        d.height=22*m_dataSetMap.keySet().size();
        jpSubjectsMetrics.setPreferredSize(d);
    }

    //right panel - with metrics data
    public void updateDataSet () {
        int i=2;
        Component[] c = jpSubjMtrcs.getComponents();
        for (String name : m_dataSetMap.keySet() ) {
            //get metrics
            double intQ = getYValueAtXAxis((int)( (m_maxTime+1)*jsTime.getValue()/100.0), m_dataSetMap.get(name));
            //
            updatePanel((javax.swing.JPanel)c[i], intQ);
            i++;
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // MAIN CHART
    //
    public void showChart() {

        //get MAX x-axis - set slider boundaries
        for (String name : m_dataSetMap.keySet() ) {
            Vector<Point2D.Double> vector = m_dataSetMap.get(name);
            if ( !vector.isEmpty() ) {
                if (m_maxTime < vector.elementAt(vector.size()-1).x+5 ) m_maxTime = vector.elementAt(vector.size()-1).x+5;
                //break;
            }
        }
        jlTimeSlider.setText(Integer.toString((int)m_maxTime));
        //build right panel data
        //SET1
        buildDataSet();
        updateDataSet();
        //
        XYPlot plot = buildPlot();
        //ValueAxis domainAxis = new NumberAxis(m_setXTitle);
        //plot.setDomainAxes(domainAxis);
        populatePlot(plot);
        //
        //CombinedDomainXYPlot combPlot = new CombinedDomainXYPlot(domainAxis);
        //combPlot.add(plot);
        //
        m_chart = new JFreeChart(m_title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        //m_chart = new JFreeChart(m_title, JFreeChart.DEFAULT_TITLE_FONT, combPlot, true);
        m_chart.addProgressListener(this);
        //
        setChartLabels (plot);
        showItemData();
        //
        ChartPanel cp = new ChartPanel(m_chart, true, true, true, true, true);
        //cp.addChartMouseListener(this);
        cp.setPreferredSize(new java.awt.Dimension(200, 200));
        cp.setDisplayToolTips(true);
        cp.setInitialDelay(0);
        //cp.setDismissDelay(10);
        cp.setReshowDelay(0);

        jpChart.add(cp,java.awt.BorderLayout.CENTER);
        jpChart.updateUI();

        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // BUILD GENERIC PLOT
    //
    public XYPlot buildPlot () {
        XYPlot plot = new XYPlot();
        ValueAxis domainAxis = new NumberAxis(m_setXTitle);
        plot.setDomainAxis(domainAxis);

        //SET CROSSHAIR VISIBLE
        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairLockedOnData(false);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(false);
        //
        return plot;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // BUILD PLOT: accessible information
    //
    public void populatePlot(XYPlot plot) {
        ValueAxis rangeAxis = new NumberAxis(m_setYTitle);
        ValueAxis rangeDomainAxis = new NumberAxis(m_setXTitle);
        //TickUnitSource units = NumberAxis.createIntegerTickUnits();
        //rangeAxis.setStandardTickUnits(units);
        if (m_setAxis)
        {
            rangeAxis.setAutoRange(false);
            rangeAxis.setRange(m_minValue, m_maxValue);
        }
        if (m_setDomainAxis) {
            rangeDomainAxis.setAutoRange(false);
            rangeDomainAxis.setRange(m_minDomainValue, m_maxDomainValue);
        }
        //TickUnits units = (TickUnits) NumberAxis.createIntegerTickUnits();
        //rangeAxis.setStandardTickUnits(units);

        plot.setRangeAxis(rangeAxis);
        plot.setDomainAxis(rangeDomainAxis);
        plotAccessibilityByChart(plot, m_dataSetMap, m_annotationsSet);
        //plotAccessibilityIndexChart(plot, m_subjectsDataSet1.keySet().size()+m_teamsDataSet1.keySet().size(), m_overallDataSet1);
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT DATA
    //
    public void plotAccessibilityByChart(XYPlot plot,
                                        TreeMap<String, Vector<Point2D.Double>> dataSet,
                                        Vector<ArrayList<XYPointerAnnotation>> annotations) {

        int i=0;
        //for (String subjectName : informationAccessibleBySubjects.keySet()) {
        //must preserve order of subjects
        for (String name : dataSet.keySet() ) {
            XYStepRenderer renderer = new XYStepRenderer();
            XYSeries serie = new XYSeries(name);
            for (Point2D.Double data : dataSet.get(name)) {
                serie.add(data.x, data.y);
                //make annotation
                XYPointerAnnotation annot = new XYPointerAnnotation("Q: ", data.x, data.y, 200.0);
                annot.setTipRadius(10.0);
                annot.setBaseRadius(25.0);
                annot.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
                annot.setPaint(java.awt.Color.BLACK);
                annot.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
                annotations.elementAt(i).add(annot);
            }
            XYSeriesCollection collection = new XYSeriesCollection();
            collection.addSeries(serie);
            plot.setRenderer(i, renderer);
            plot.setDataset(i, collection);
            i++;
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT CHART LABELS DATA
    //
    public void setChartLabels (XYPlot plotP) {
        XYItemLabelGenerator gen = new StandardXYItemLabelGenerator("{2}", new DecimalFormat("0.00"), new DecimalFormat("0.00"));
        for (int i=0; i<plotP.getRendererCount(); i++) {
            for (int j=0; j<=INFOQ_ACCESSIBILITY_INDEX_ALL;j++) {
                plotP.getRenderer(i).setSeriesItemLabelsVisible(j, true);
                plotP.getRenderer(i).setSeriesItemLabelFont(j, new Font(Font.SANS_SERIF, Font.PLAIN, 8));
                plotP.getRenderer(i).setSeriesItemLabelGenerator(j, gen);
                plotP.getRenderer(i).setSeriesToolTipGenerator(j,  new StandardXYToolTipGenerator());
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT ANNOTATIONS
    //
    protected void removeAllAnnotations (XYPlot plot) {
        for (Object a : plot.getAnnotations() ) {
            plot.removeAnnotation((XYPointerAnnotation)a);
        }
    }

    protected void showAnnotation (XYPlot plot, Vector<ArrayList<XYPointerAnnotation>> annotations, int index, boolean show) {
        if (index < annotations.size()) {
            for ( XYPointerAnnotation a : annotations.elementAt(index) ) {
                if (show)
                    plot.addAnnotation(a);
            }//end for
        }//end if
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT DATA - APPLY FILTERS
    //
    protected void showItemDataPerIndex (XYPlot plot, Vector<ArrayList<XYPointerAnnotation>> annotations, int i, boolean show) {
        /*
        if (jcbRelevant.isSelected())
            plot.getRenderer(i).setSeriesVisible(INFOQ_ACCESSIBILITY_INDEX_RELEVANT, show);
        else
            plot.getRenderer(i).setSeriesVisible(INFOQ_ACCESSIBILITY_INDEX_RELEVANT, false);

        if (jcbMisinformation.isSelected())
            plot.getRenderer(i).setSeriesVisible(INFOQ_ACCESSIBILITY_INDEX_MISINFO, show);
        else
            plot.getRenderer(i).setSeriesVisible(INFOQ_ACCESSIBILITY_INDEX_MISINFO, false);

        if (jcbAll.isSelected())
            plot.getRenderer(i).setSeriesVisible(INFOQ_ACCESSIBILITY_INDEX_ALL, show);
        else
            plot.getRenderer(i).setSeriesVisible(INFOQ_ACCESSIBILITY_INDEX_ALL, false);
            */
        plot.getRenderer(i).setBaseSeriesVisible(show);
        if (jcbAnnotateKE.isSelected())
            showAnnotation(plot, annotations, i, show);
        setChartLabels(plot);
    }

    private void showItemData () {
        //CombinedDomainXYPlot p = (CombinedDomainXYPlot)m_chart.getPlot();
        //must handle top and bottom plots separately
        //XYPlot plot = (XYPlot)p.getSubplots().get(0);
        XYPlot plot = (XYPlot)m_chart.getPlot();
        removeAllAnnotations(plot);
        Component[] comp = jpIndividuals.getComponents();
        for (int i=0; i<comp.length; i++) {
            JCheckBox box = (JCheckBox)comp[i];
            showItemDataPerIndex(plot, m_annotationsSet, i, box.isSelected());
        }
    }

    private void jcbAccessIndexSubjectActionPerformed (java.awt.event.ActionEvent evt) {
        showItemData();
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // CROSS-HAIR
    //
    @Override
    public void chartProgress(ChartProgressEvent arg0) {
        if (arg0.getType() == ChartProgressEvent.DRAWING_FINISHED) {
            XYPlot plot = (XYPlot)m_chart.getPlot();
            double domainV = plot.getDomainCrosshairValue();
            double valueV = plot.getRangeCrosshairValue();
            jlCrossX.setText(m_formatter.format(domainV));
            jlCrossY.setText(m_formatter.format(valueV));
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jbExportData = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jcbAnnotateKE = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jsTime = new javax.swing.JSlider();
        jlTimeSlider = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jpIndividuals = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jlCrossX = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlCrossY = new javax.swing.JLabel();
        jpChart = new javax.swing.JPanel();
        jpSubjectsMetrics = new javax.swing.JPanel();
        jpSubjMtrcs = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Time Chart");

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setOpaque(false);
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jbExportData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_download_48.png"))); // NOI18N
        jbExportData.setText("Save Data");
        jbExportData.setPreferredSize(new java.awt.Dimension(160, 60));
        jbExportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportDataActionPerformed(evt);
            }
        });
        jPanel16.add(jbExportData);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));
        jPanel5.setMaximumSize(null);
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jcbAnnotateKE.setText("Annotate K/E");
        jcbAnnotateKE.setMinimumSize(new java.awt.Dimension(170, 20));
        jcbAnnotateKE.setPreferredSize(new java.awt.Dimension(100, 20));
        jcbAnnotateKE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAnnotateKEActionPerformed(evt);
            }
        });
        jPanel5.add(jcbAnnotateKE);

        jPanel16.add(jPanel5);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Time (Sec)"));
        jPanel7.setMaximumSize(new java.awt.Dimension(150, 60));
        jPanel7.setMinimumSize(new java.awt.Dimension(150, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 60));
        jPanel7.setRequestFocusEnabled(false);
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jsTime.setMajorTickSpacing(5);
        jsTime.setPaintTicks(true);
        jsTime.setValue(100);
        jsTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsTimeStateChanged(evt);
            }
        });
        jPanel7.add(jsTime);

        jlTimeSlider.setFont(jlTimeSlider.getFont().deriveFont((float)8));
        jlTimeSlider.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlTimeSlider.setText("(x-axis)");
        jlTimeSlider.setMaximumSize(new java.awt.Dimension(40, 20));
        jlTimeSlider.setMinimumSize(new java.awt.Dimension(40, 20));
        jlTimeSlider.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel7.add(jlTimeSlider);

        jPanel16.add(jPanel7);

        jPanel3.add(jPanel16, java.awt.BorderLayout.NORTH);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.Y_AXIS));

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));
        jPanel20.setAlignmentY(0.0F);
        jPanel20.setPreferredSize(new java.awt.Dimension(160, 60));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.Y_AXIS));

        jpIndividuals.setBorder(javax.swing.BorderFactory.createTitledBorder("Individuals"));
        jpIndividuals.setPreferredSize(new java.awt.Dimension(120, 30));
        jpIndividuals.setLayout(new javax.swing.BoxLayout(jpIndividuals, javax.swing.BoxLayout.Y_AXIS));
        jPanel20.add(jpIndividuals);

        jPanel17.add(jPanel20);
        jPanel20.getAccessibleContext().setAccessibleName(null);

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

        jPanel17.add(jPanel1);

        jPanel3.add(jPanel17, java.awt.BorderLayout.WEST);

        jpChart.setPreferredSize(new java.awt.Dimension(400, 400));
        jpChart.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jpChart, java.awt.BorderLayout.CENTER);

        jpSubjectsMetrics.setBorder(javax.swing.BorderFactory.createTitledBorder("Individual Metrics"));
        jpSubjectsMetrics.setPreferredSize(new java.awt.Dimension(140, 100));
        jpSubjectsMetrics.setLayout(new javax.swing.BoxLayout(jpSubjectsMetrics, javax.swing.BoxLayout.Y_AXIS));

        jpSubjMtrcs.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 2));

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()-1f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Subject");
        jLabel1.setMaximumSize(new java.awt.Dimension(35, 10));
        jLabel1.setMinimumSize(new java.awt.Dimension(35, 10));
        jLabel1.setPreferredSize(new java.awt.Dimension(35, 10));
        jpSubjMtrcs.add(jLabel1);
        jLabel1.getAccessibleContext().setAccessibleName("Name:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("value");
        jLabel2.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel2.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel2.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSubjMtrcs.add(jLabel2);

        jpSubjectsMetrics.add(jpSubjMtrcs);

        jPanel3.add(jpSubjectsMetrics, java.awt.BorderLayout.EAST);

        jScrollPane1.setViewportView(jPanel3);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbAnnotateKEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAnnotateKEActionPerformed
        showItemData();
}//GEN-LAST:event_jcbAnnotateKEActionPerformed

    private void jsTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsTimeStateChanged
        jlTimeSlider.setText( Integer.toString( (int) (m_maxTime*jsTime.getValue()/100.0))+" (s)" );
        //SET1
        updateDataSet();
    }//GEN-LAST:event_jsTimeStateChanged

    private void jbExportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportDataActionPerformed
        javax.swing.JFileChooser jfcOpenFile = new javax.swing.JFileChooser();
        int returnVal = jfcOpenFile.showOpenDialog(this);
        File file = jfcOpenFile.getSelectedFile();
        if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION ) {
            if (file == null ) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please select a valid file.");
            }
            else if (file.exists()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cannot overwrite existing file.");
            }
            else {
                ExportData2D exportData = new ExportData2D();
                for (String name : m_dataSetMap.keySet()) {
                    exportData.addData(name+"::"+m_setYTitle, m_dataSetMap.get(name));
                }
                //exportData.addData("Overall::"+m_set2YTitle, m_overallDataSet2);
                try {
                    exportData.writeData(file);
                } catch (Exception ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    javax.swing.JOptionPane.showMessageDialog(this, "Error writing file: "+ex.getMessage());
                }
            }
        }//end selected file

}//GEN-LAST:event_jbExportDataActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbExportData;
    protected javax.swing.JCheckBox jcbAnnotateKE;
    private javax.swing.JLabel jlCrossX;
    private javax.swing.JLabel jlCrossY;
    private javax.swing.JLabel jlTimeSlider;
    private javax.swing.JPanel jpChart;
    protected javax.swing.JPanel jpIndividuals;
    private javax.swing.JPanel jpSubjMtrcs;
    private javax.swing.JPanel jpSubjectsMetrics;
    protected javax.swing.JSlider jsTime;
    // End of variables declaration//GEN-END:variables

}
