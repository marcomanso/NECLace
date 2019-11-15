/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFInformationAccessibilityTimeChart.java
 *
 * Created on 6/Mai/2009, 23:26:43
 */
package elicit.tools;

import elicit.exportdata.ExportAccessibilityIndex;
import elicit.message.FactoidMessage;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.JCheckBox;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationAccessible.AccessibilityIndexVector;
import metrics.informationquality.InformationQuality.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author mmanso
 */
public class JFFormsInteractionsTimeChart extends javax.swing.JFrame
        implements ChartProgressListener {

    public static int INFOQ_ACCESSIBILITY_INDEX_RELEVANT = 0;
    public static int INFOQ_ACCESSIBILITY_INDEX_MISINFO = 1;
    public static int INFOQ_ACCESSIBILITY_INDEX_ALL = 2;
    //
    JFreeChart m_chart;
    String m_title;
    String m_set1YTitle = "";
    String m_set2YTitle = "";
    //
    //ArrayList<String> m_teamNames;
    //ArrayList<Subject> m_subjectNames;
    Vector<ArrayList<XYPointerAnnotation>> m_annotationsSet1 = new Vector<ArrayList<XYPointerAnnotation>>();
    Vector<ArrayList<XYPointerAnnotation>> m_annotationsSet2 = new Vector<ArrayList<XYPointerAnnotation>>();
    //
    TreeMap<String, AccessibilityIndexVector> m_subjectsDataSet1;
    TreeMap<String, AccessibilityIndexVector> m_teamsDataSet1;
    AccessibilityIndexVector m_overallDataSet1;
    //
    TreeMap<String, AccessibilityIndexVector> m_subjectsDataSet2;
    TreeMap<String, AccessibilityIndexVector> m_teamsDataSet2;
    AccessibilityIndexVector m_overallDataSet2;

    double m_maxTime = 0.0;

    /** Creates new form JFInformationAccessibilityTimeChart */
    public JFFormsInteractionsTimeChart(String title) {
        initComponents();
        m_title = title;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // DATA RELATED
    //
    public void setData(String set1Ytitle,
                        AccessibilityIndexVector overallDataSet1,
                        TreeMap<String, AccessibilityIndexVector> teamsDataSet1,
                        TreeMap<String, AccessibilityIndexVector> subjectsDataSet1,
                        String set2Ytitle,
                        AccessibilityIndexVector overallDataSet2,
                        TreeMap<String, AccessibilityIndexVector> teamsDataSet2,
                        TreeMap<String, AccessibilityIndexVector> subjectsDataSet2) {
        m_set1YTitle = set1Ytitle;
        setOverallData (overallDataSet1);
        setTeamsData(teamsDataSet1);
        setSubjectsData(subjectsDataSet1);
        //
        m_set2YTitle = set2Ytitle;
        setOverallSet2Data (overallDataSet2);
        setTeamsSet2Data(teamsDataSet2);
        setSubjectsSet2Data(subjectsDataSet2);
    }

    public void setOverallData (AccessibilityIndexVector overallDataSet) {
        m_overallDataSet1 = overallDataSet;
        m_annotationsSet1.add(new ArrayList<XYPointerAnnotation>());
    }

    public void setOverallSet2Data (AccessibilityIndexVector overallDataSet) {
        m_overallDataSet2 = overallDataSet;
        m_annotationsSet2.add(new ArrayList<XYPointerAnnotation>());
    }

    public void setTeamsData(TreeMap<String, AccessibilityIndexVector> teamsDataSet) {
        m_teamsDataSet1 = teamsDataSet;
        for (String name : m_teamsDataSet1.keySet() ) {
            JCheckBox jcb = new javax.swing.JCheckBox();
            jcb.setText(name);
            jcb.setPreferredSize(new java.awt.Dimension(120, 20));
            jcb.setMinimumSize(new java.awt.Dimension(120, 20));
            jcb.setMaximumSize(new java.awt.Dimension(120, 20));
            jcb.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jcbAccessIndexTeamActionPerformed(evt);
                }
            });
            jpTeams.add(jcb);
            m_annotationsSet1.add(new ArrayList<XYPointerAnnotation>());
        }
    }

    public void setTeamsSet2Data(TreeMap<String, AccessibilityIndexVector> teamsDataSet) {
        if (teamsDataSet==null) {
            m_teamsDataSet2 = new TreeMap<String, AccessibilityIndexVector>();
        }
        else {
            m_teamsDataSet2 = teamsDataSet;
            for (String name : m_teamsDataSet2.keySet() ) {
                m_annotationsSet2.add(new ArrayList<XYPointerAnnotation>());
            }
        }
    }

    public void setSubjectsData(TreeMap<String, AccessibilityIndexVector> subjectsDataSet) {
        m_subjectsDataSet1 = subjectsDataSet;
        for (String name : m_subjectsDataSet1.keySet() ) {
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
            m_annotationsSet1.add(new ArrayList<XYPointerAnnotation>());
        }
    }

    public void setSubjectsSet2Data(TreeMap<String, AccessibilityIndexVector> subjectsDataSet) {
        if (subjectsDataSet==null) {
            m_subjectsDataSet2 = new TreeMap<String, AccessibilityIndexVector>();
        }
        else {
            m_subjectsDataSet2 = subjectsDataSet;
        }
/* TODO
        for (String name : m_subjectsDataSet1.keySet() ) {
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
            m_annotationsSet1.add(new ArrayList<XYPointerAnnotation>());
        }
*/
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
        jl.setMaximumSize(new java.awt.Dimension(35, 8));
        jl.setMinimumSize(new java.awt.Dimension(35, 8));
        jl.setPreferredSize(new java.awt.Dimension(35, 8));
        //
        javax.swing.JLabel jlR = new javax.swing.JLabel();
        jlR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlR.setText("-");
        jlR.setPreferredSize(new java.awt.Dimension(15, 8));
        //
        javax.swing.JLabel jlA = new javax.swing.JLabel();
        jlA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlA.setText("-");
        jlA.setPreferredSize(new java.awt.Dimension(15, 8));
        //
        javax.swing.JLabel jlM = new javax.swing.JLabel();
        jlM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlM.setText("-");
        jlM.setPreferredSize(new java.awt.Dimension(15, 8));
        //
        javax.swing.JLabel jlH = new javax.swing.JLabel();
        jlH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlH.setText("-");
        jlH.setPreferredSize(new java.awt.Dimension(15, 8));
        //
        p.add(jl);
        p.add(jlR);
        p.add(jlM);
        p.add(jlA);
        p.add(jlH);
        //
        return p;
    }

    public void updatePanel (javax.swing.JPanel p, int nbrKE, int nbrR, int nbrA, int nbrH) {
        javax.swing.JLabel lKE = (javax.swing.JLabel)p.getComponent(1);
        lKE.setText(Integer.toString(nbrKE));
        javax.swing.JLabel lR = (javax.swing.JLabel)p.getComponent(2);
        lR.setText(Integer.toString(nbrR));
        javax.swing.JLabel lA = (javax.swing.JLabel)p.getComponent(3);
        lA.setText(Integer.toString(nbrA));
        javax.swing.JLabel lH = (javax.swing.JLabel)p.getComponent(4);
        lH.setText(Integer.toString(nbrH));
    }

    //right panel - with metrics data
    public void buildSubjectsData () {
        for (String name : m_subjectsDataSet1.keySet() ) {
            jpSubjMtrcs.add(buildDataPanel(name));
        }
        //set parent dimensions
        java.awt.Dimension d = jpSubjectsMetrics.getPreferredSize();
        d.height=22*m_subjectsDataSet1.keySet().size();
        jpSubjectsMetrics.setPreferredSize(d);
    }

    //right panel - with metrics data
    public void updateSubjectsData () {
        int i=5;
        Component[] c = jpSubjMtrcs.getComponents();
        for (String name : m_subjectsDataSet1.keySet() ) {
            //get metrics
            int nbrKE = InformationAccessible.getNbrKEFactoids(m_subjectsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrR = InformationAccessible.getNbrRelevantFactoids(m_subjectsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrA = InformationAccessible.getNbrAllFactoids(m_subjectsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrH = 0;
            //
            updatePanel((javax.swing.JPanel)c[i], nbrKE, nbrR, nbrA, nbrH);
            i++;
        }
    }

    //right panel - with metrics data
    public void buildTeamsData () {
        for (String name : m_teamsDataSet1.keySet() ) {
            jpTeamMtrcs.add(buildDataPanel(name));
        }
        //set parent dimensions
        java.awt.Dimension d = jpTeamsMetrics.getPreferredSize();
        d.height=22*m_teamsDataSet1.keySet().size();
        jpTeamsMetrics.setPreferredSize(d);
    }

    //right panel - with metrics data
    public void updateTeamsData () {
        int i=5;
        Component[] c = jpTeamMtrcs.getComponents();
        for (String name : m_teamsDataSet1.keySet() ) {
            //get metrics
            int nbrKE = InformationAccessible.getNbrKEFactoids(m_teamsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrR = InformationAccessible.getNbrRelevantFactoids(m_teamsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrA = InformationAccessible.getNbrAllFactoids(m_teamsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrH = InformationAccessible.getNbrFactoidsHoarded(m_teamsDataSet1.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            //
            updatePanel((javax.swing.JPanel)c[i], nbrKE, nbrR, nbrA, nbrH);
            i++;
        }
    }

    //right panel - with metrics data
    public void buildSet2Data () {
        for (String name : m_teamsDataSet2.keySet() ) {
            //
            jpSharedMtrcs.add(buildDataPanel(name));
        }
        {
            jpSharedMtrcs.add(buildDataPanel("Overall"));
        }
        //set parent dimensions
        java.awt.Dimension d = jpSharedMetrics.getPreferredSize();
        d.height=22*(m_teamsDataSet1.keySet().size()+1);
        jpSharedMetrics.setPreferredSize(d);
    }

    //right panel - with metrics data
    public void updateSet2Data () {
        int i=5;
        Component[] c = jpSharedMtrcs.getComponents();
        //update TEAMS
        for (String name : m_teamsDataSet1.keySet() ) {
            //get metrics
            int nbrKE = InformationAccessible.getNbrKEFactoids(m_teamsDataSet2.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrR = InformationAccessible.getNbrRelevantFactoids(m_teamsDataSet2.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrA = InformationAccessible.getNbrAllFactoids(m_teamsDataSet2.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrH = InformationAccessible.getNbrFactoidsHoarded(m_teamsDataSet2.get(name),(int)(m_maxTime*jsTime.getValue()/100.0));
            //
            updatePanel((javax.swing.JPanel)c[i], nbrKE, nbrR, nbrA, nbrH);
            i++;
        }
        //update OVERALL
        {
            int nbrKE = InformationAccessible.getNbrKEFactoids(m_overallDataSet2,(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrR = InformationAccessible.getNbrRelevantFactoids(m_overallDataSet2,(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrA = InformationAccessible.getNbrAllFactoids(m_overallDataSet2,(int)(m_maxTime*jsTime.getValue()/100.0));
            int nbrH = InformationAccessible.getNbrFactoidsHoarded(m_overallDataSet2,(int)(m_maxTime*jsTime.getValue()/100.0));
            updatePanel((javax.swing.JPanel)c[i], nbrKE, nbrR, nbrA, nbrH);
        }

    }

    ////////////////////////////////////////////////////////////////////////
    //
    // MAIN CHART
    //
    public void showChart() {

        //get MAX time - set slider boundaries
        for (String name : m_subjectsDataSet1.keySet() ) {
            AccessibilityIndexVector vector = m_subjectsDataSet1.get(name);
            m_maxTime = vector.elementAt(vector.size()-1).time;
            break;
        }
        jlTimeSlider.setText(Integer.toString((int)m_maxTime)+" (s)");
        //build right panel data
        buildSubjectsData();
        updateSubjectsData();
        buildTeamsData();
        updateTeamsData();
        buildSet2Data();
        updateSet2Data();
        //
        XYPlot plotTOP = buildPlot();
        populateTopPlot(plotTOP);
        XYPlot plotBOTTOM = buildPlot();
        populateBottomPlot(plotBOTTOM);
        //
        ValueAxis domainAxis = new NumberAxis("Time (sec)");
        CombinedDomainXYPlot combPlot = new CombinedDomainXYPlot(domainAxis);
        combPlot.add(plotTOP);
        combPlot.add(plotBOTTOM);
        //
        m_chart = new JFreeChart(m_title, JFreeChart.DEFAULT_TITLE_FONT, combPlot, true);
        m_chart.addProgressListener(this);
        //
        setChartLabels (plotTOP);
        setChartLabels (plotBOTTOM);
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
        ValueAxis domainAxis = new NumberAxis("Time (sec)");
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
    // BUILD TOP PLOT: accessible information
    //
    public void populateTopPlot(XYPlot plot) {
        ValueAxis rangeAxis = new NumberAxis(m_set1YTitle);
        TickUnitSource units = NumberAxis.createIntegerTickUnits();
        rangeAxis.setStandardTickUnits(units);
        plot.setRangeAxis(rangeAxis);
        plotAccessibilityBySubjectChart(plot, m_subjectsDataSet1, m_annotationsSet1);
        plotAccessibilityByTeamChart(plot, m_subjectsDataSet1.keySet().size(), m_teamsDataSet1, m_annotationsSet1);
        plotAccessibilityIndexChart(plot, m_subjectsDataSet1.keySet().size()+m_teamsDataSet1.keySet().size(), m_overallDataSet1);
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // BUILD BOTTOM PLOT: shared information
    //
    public void populateBottomPlot(XYPlot plot) {
        ValueAxis rangeAxis = new NumberAxis(m_set2YTitle);
        TickUnitSource units = NumberAxis.createIntegerTickUnits();
        rangeAxis.setStandardTickUnits(units);
        plot.setRangeAxis(rangeAxis);
        plotAccessibilityByTeamChart(plot, 0, m_teamsDataSet2, m_annotationsSet2);
        plotAccessibilityIndexChart(plot, m_teamsDataSet1.keySet().size(), m_overallDataSet2);
    }


    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT SUBJECTS DATA
    //
    public void plotAccessibilityBySubjectChart(XYPlot plot, 
                                                TreeMap<String, AccessibilityIndexVector> informationAccessibleBySubjects,
                                                Vector<ArrayList<XYPointerAnnotation>> annotations) {

        int i=0;
        //for (String subjectName : informationAccessibleBySubjects.keySet()) {
        //must preserve order of subjects
        for (String name : informationAccessibleBySubjects.keySet() ) {
            XYStepRenderer renderer = new XYStepRenderer();
            XYSeries sA = new XYSeries(name + "(all)");
            XYSeries sR = new XYSeries(name + "(relevant)");
            XYSeries sM = new XYSeries(name + "(misinfo)");
            for (InformationAccessible.InformationAccessibleData info : informationAccessibleBySubjects.get(name)) {
                sR.add(info.time, info.indexRelevant);
                sM.add(info.time, info.indexMisinfo);
                sA.add(info.time, info.indexAll);
                if (FactoidMessage.isKE(info.factoidMetadata)) {
                    //make annotation
                    XYPointerAnnotation annot = new XYPointerAnnotation("K/E", info.time, info.indexRelevant, 200.0);
                    annot.setTipRadius(10.0);
                    annot.setBaseRadius(25.0);
                    annot.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
                    annot.setPaint(java.awt.Color.BLACK);
                    annot.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
                    annotations.elementAt(i).add(annot);
                }
            }
            XYSeriesCollection collection = new XYSeriesCollection();
            collection.addSeries(sR);
            collection.addSeries(sM);
            collection.addSeries(sA);
            plot.setRenderer(i, renderer);
            plot.setDataset(i, collection);
            i++;
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT TEAMS DATA
    //
    public void plotAccessibilityByTeamChart(XYPlot plot,
                                             int renderIndex,
                                             TreeMap<String, AccessibilityIndexVector> informationAccessibleByTeams,
                                             Vector<ArrayList<XYPointerAnnotation>> annotations) {
        int i=renderIndex;
        for (String name : informationAccessibleByTeams.keySet() ) {
            XYStepRenderer renderer = new XYStepRenderer();
            XYSeries sA = new XYSeries(name + "(all)");
            XYSeries sR = new XYSeries(name + "(relevant)");
            XYSeries sM = new XYSeries(name + "(misinfo)");
            for (InformationAccessible.InformationAccessibleData info : informationAccessibleByTeams.get(name)) {
                sR.add(info.time, info.indexRelevant);
                sM.add(info.time, info.indexMisinfo);
                sA.add(info.time, info.indexAll);
                if (FactoidMessage.isKE(info.factoidMetadata)) {
                    //make annotation
                    XYPointerAnnotation annot = new XYPointerAnnotation("K/E", info.time, info.indexRelevant, 200.0);
                    annot.setTipRadius(10.0);
                    annot.setBaseRadius(25.0);
                    annot.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
                    annot.setPaint(java.awt.Color.BLACK);
                    annot.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
                    annotations.elementAt(i).add(annot);
                }
            }
            XYSeriesCollection collection = new XYSeriesCollection();
            collection.addSeries(sR);
            collection.addSeries(sM);
            collection.addSeries(sA);
            plot.setRenderer(i, renderer);
            plot.setDataset(i, collection);
            i++;
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT OVERALL DATA
    //
    public void plotAccessibilityIndexChart(XYPlot plot, 
                                            int renderIndex,
                                            AccessibilityIndexVector dataVector) {

        XYSeries xyAccessAll = new XYSeries("Accessibility Index (All)");
        XYSeries xyAccessRelevant = new XYSeries("Accessibility Index (Relevant)");
        XYSeries xyAccessMisinfo = new XYSeries("Accessibility Index (Misinformation)");
        for (InformationAccessible.InformationAccessibleData data : dataVector) {
            xyAccessAll.add(data.time, data.indexAll);
            xyAccessRelevant.add(data.time, data.indexRelevant);
            xyAccessMisinfo.add(data.time, data.indexMisinfo);
        }
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(xyAccessRelevant);
        collection.addSeries(xyAccessMisinfo);
        collection.addSeries(xyAccessAll);

        XYStepAreaRenderer renderer = new XYStepAreaRenderer();
        //
        XYItemLabelGenerator gen = new StandardXYItemLabelGenerator("{2}", new DecimalFormat("0.0"), new DecimalFormat("0.0"));
        renderer.setBaseItemLabelGenerator(gen);
        renderer.setBaseItemLabelsVisible(true);

        //
        plot.setDataset(renderIndex, collection);
        plot.setRenderer(renderIndex, renderer);

    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT CHART LABELS DATA
    //
    public void setChartLabels (XYPlot plotP) {
        XYItemLabelGenerator gen = new StandardXYItemLabelGenerator("{2}", new DecimalFormat("0"), new DecimalFormat("0"));
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
    private void removeAllAnnotations (XYPlot plot) {
        for (Object a : plot.getAnnotations() ) {
            plot.removeAnnotation((XYPointerAnnotation)a);
        }
    }

    private void showAnnotation (XYPlot plot, Vector<ArrayList<XYPointerAnnotation>> annotations, int index, boolean show) {
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
    private void showItemDataPerIndex (XYPlot plot, Vector<ArrayList<XYPointerAnnotation>> annotations, int i, boolean show) {

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

        if (jcbAnnotateKE.isSelected() && jcbRelevant.isSelected())
            showAnnotation(plot, annotations, i, show);

        setChartLabels(plot);

    }

    private void showTOPItemData () {
        CombinedDomainXYPlot p = (CombinedDomainXYPlot)m_chart.getPlot();
        //must handle top and bottom plots separately
        XYPlot plot = (XYPlot)p.getSubplots().get(0);
        removeAllAnnotations(plot);
        Component[] comp = jpIndividuals.getComponents();
        for (int i=0; i<comp.length; i++) {
            JCheckBox box = (JCheckBox)comp[i];
            showItemDataPerIndex(plot, m_annotationsSet1, i, box.isSelected());
        }
        Component[] compT = jpTeams.getComponents();
        for (int i=0; i<compT.length; i++) {
            JCheckBox box = (JCheckBox)compT[i];
            showItemDataPerIndex(plot, m_annotationsSet1, m_subjectsDataSet1.keySet().size()+i, box.isSelected());
        }
        showItemDataPerIndex(plot, m_annotationsSet1, m_subjectsDataSet1.keySet().size()+m_teamsDataSet1.keySet().size(), jcbOverall.isSelected());
    }

    private void showBOTTOMItemData () {
        CombinedDomainXYPlot p = (CombinedDomainXYPlot)m_chart.getPlot();
        //must handle top and bottom plots separately
        XYPlot plot = (XYPlot)p.getSubplots().get(1);
        removeAllAnnotations(plot);
        Component[] compT = jpTeams.getComponents();
        for (int i=0; i<compT.length; i++) {
            JCheckBox box = (JCheckBox)compT[i];
            showItemDataPerIndex(plot, m_annotationsSet2, i, box.isSelected());
        }
        showItemDataPerIndex(plot, m_annotationsSet2, m_teamsDataSet2.keySet().size(), jcbOverall.isSelected());
    }

    private void showItemData () {
        showTOPItemData();
        showBOTTOMItemData();
    }

    private void jcbAccessIndexSubjectActionPerformed (java.awt.event.ActionEvent evt) {
        showItemData();
    }

    private void jcbAccessIndexTeamActionPerformed (java.awt.event.ActionEvent evt) {
        showItemData();
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // CROSS-HAIR
    //
    @Override
    public void chartProgress(ChartProgressEvent arg0) {
        if (arg0.getType() == ChartProgressEvent.DRAWING_FINISHED) {
            CombinedDomainXYPlot p = (CombinedDomainXYPlot)m_chart.getPlot();
            XYPlot plotTOP = (XYPlot)p.getSubplots().get(0);
            double domainTOP = plotTOP.getDomainCrosshairValue();
            double valueTOP = plotTOP.getRangeCrosshairValue();
            jlTOPCrossX.setText(Double.toString(domainTOP));
            jlTOPCrossY.setText(Double.toString(valueTOP));
            //
            XYPlot plotBOTTOM = (XYPlot)p.getSubplots().get(1);
            double domainBOTTOM = plotBOTTOM.getDomainCrosshairValue();
            double valueBOTTOM = plotBOTTOM.getRangeCrosshairValue();
            jlBOTTOMCrossX.setText(Double.toString(domainBOTTOM));
            jlBOTTOMCrossY.setText(Double.toString(valueBOTTOM));
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
        jcbAll = new javax.swing.JCheckBox();
        jcbRelevant = new javax.swing.JCheckBox();
        jcbMisinformation = new javax.swing.JCheckBox();
        jcbAnnotateKE = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jsTime = new javax.swing.JSlider();
        jlTimeSlider = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jcbOverall = new javax.swing.JCheckBox();
        jpTeams = new javax.swing.JPanel();
        jpIndividuals = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jlTOPCrossX = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlTOPCrossY = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jlBOTTOMCrossX = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jlBOTTOMCrossY = new javax.swing.JLabel();
        jpChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jpSubjectsMetrics = new javax.swing.JPanel();
        jpSubjMtrcs = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jpTeamsMetrics = new javax.swing.JPanel();
        jpTeamMtrcs = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jpSharedMetrics = new javax.swing.JPanel();
        jpSharedMtrcs = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Time Chart");

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setOpaque(false);
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jbExportData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_download_48.png"))); // NOI18N
        jbExportData.setText("Export Data");
        jbExportData.setPreferredSize(new java.awt.Dimension(160, 60));
        jbExportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportDataActionPerformed(evt);
            }
        });
        jPanel16.add(jbExportData);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Relevance Filter"));
        jPanel5.setMaximumSize(null);
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jcbAll.setSelected(true);
        jcbAll.setText("All");
        jcbAll.setMinimumSize(new java.awt.Dimension(120, 20));
        jcbAll.setPreferredSize(new java.awt.Dimension(100, 20));
        jcbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAllActionPerformed(evt);
            }
        });
        jPanel5.add(jcbAll);

        jcbRelevant.setSelected(true);
        jcbRelevant.setText("Relevant");
        jcbRelevant.setMinimumSize(new java.awt.Dimension(170, 20));
        jcbRelevant.setPreferredSize(new java.awt.Dimension(100, 20));
        jcbRelevant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbRelevantActionPerformed(evt);
            }
        });
        jPanel5.add(jcbRelevant);

        jcbMisinformation.setText("Misinformation");
        jcbMisinformation.setMinimumSize(new java.awt.Dimension(170, 20));
        jcbMisinformation.setPreferredSize(new java.awt.Dimension(100, 20));
        jcbMisinformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMisinformationActionPerformed(evt);
            }
        });
        jPanel5.add(jcbMisinformation);

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
        jPanel7.setMaximumSize(new java.awt.Dimension(140, 60));
        jPanel7.setMinimumSize(new java.awt.Dimension(140, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(140, 60));
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
        jlTimeSlider.setText("(s)");
        jlTimeSlider.setMaximumSize(new java.awt.Dimension(35, 20));
        jlTimeSlider.setMinimumSize(new java.awt.Dimension(35, 20));
        jlTimeSlider.setPreferredSize(new java.awt.Dimension(35, 20));
        jPanel7.add(jlTimeSlider);

        jPanel16.add(jPanel7);

        jPanel3.add(jPanel16, java.awt.BorderLayout.NORTH);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.Y_AXIS));

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));
        jPanel20.setAlignmentY(0.0F);
        jPanel20.setPreferredSize(new java.awt.Dimension(160, 60));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.Y_AXIS));

        jcbOverall.setSelected(true);
        jcbOverall.setText("Overall Data");
        jcbOverall.setMaximumSize(new java.awt.Dimension(140, 20));
        jcbOverall.setMinimumSize(new java.awt.Dimension(140, 20));
        jcbOverall.setPreferredSize(new java.awt.Dimension(140, 20));
        jcbOverall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbOverallActionPerformed(evt);
            }
        });
        jPanel20.add(jcbOverall);

        jpTeams.setBorder(javax.swing.BorderFactory.createTitledBorder("Teams"));
        jpTeams.setPreferredSize(new java.awt.Dimension(120, 30));
        jpTeams.setLayout(new javax.swing.BoxLayout(jpTeams, javax.swing.BoxLayout.Y_AXIS));
        jPanel20.add(jpTeams);

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

        jlTOPCrossX.setMaximumSize(null);
        jlTOPCrossX.setMinimumSize(null);
        jlTOPCrossX.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jlTOPCrossX);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Y:");
        jLabel19.setMaximumSize(null);
        jLabel19.setMinimumSize(null);
        jLabel19.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jLabel19);

        jlTOPCrossY.setMaximumSize(null);
        jlTOPCrossY.setMinimumSize(null);
        jlTOPCrossY.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel6.add(jlTOPCrossY);

        jPanel1.add(jPanel6);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("BOTTOM Crosshair"));
        jPanel4.setAlignmentX(0.0F);
        jPanel4.setAlignmentY(0.0F);
        jPanel4.setMaximumSize(new java.awt.Dimension(156, 60));
        jPanel4.setMinimumSize(new java.awt.Dimension(156, 60));
        jPanel4.setPreferredSize(new java.awt.Dimension(156, 60));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("X:");
        jLabel6.setMaximumSize(null);
        jLabel6.setMinimumSize(null);
        jLabel6.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel4.add(jLabel6);

        jlBOTTOMCrossX.setMaximumSize(null);
        jlBOTTOMCrossX.setMinimumSize(null);
        jlBOTTOMCrossX.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel4.add(jlBOTTOMCrossX);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Y:");
        jLabel8.setMaximumSize(null);
        jLabel8.setMinimumSize(null);
        jLabel8.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel4.add(jLabel8);

        jlBOTTOMCrossY.setMaximumSize(null);
        jlBOTTOMCrossY.setMinimumSize(null);
        jlBOTTOMCrossY.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel4.add(jlBOTTOMCrossY);

        jPanel1.add(jPanel4);

        jPanel17.add(jPanel1);

        jPanel3.add(jPanel17, java.awt.BorderLayout.WEST);

        jpChart.setPreferredSize(new java.awt.Dimension(400, 400));
        jpChart.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jpChart, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("KE");
        jLabel2.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel2.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel2.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSubjMtrcs.add(jLabel2);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("R");
        jLabel3.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel3.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel3.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSubjMtrcs.add(jLabel3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("All");
        jLabel4.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel4.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel4.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSubjMtrcs.add(jLabel4);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("(H)");
        jLabel5.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel5.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel5.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSubjMtrcs.add(jLabel5);

        jpSubjectsMetrics.add(jpSubjMtrcs);

        jPanel2.add(jpSubjectsMetrics);

        jpTeamsMetrics.setBorder(javax.swing.BorderFactory.createTitledBorder("Team Metrics"));
        jpTeamsMetrics.setPreferredSize(new java.awt.Dimension(140, 100));
        jpTeamsMetrics.setLayout(new javax.swing.BoxLayout(jpTeamsMetrics, javax.swing.BoxLayout.Y_AXIS));

        jpTeamMtrcs.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 2));

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()-1f));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Team");
        jLabel7.setMaximumSize(new java.awt.Dimension(35, 10));
        jLabel7.setMinimumSize(new java.awt.Dimension(35, 10));
        jLabel7.setPreferredSize(new java.awt.Dimension(35, 10));
        jpTeamMtrcs.add(jLabel7);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("KE");
        jLabel9.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel9.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel9.setPreferredSize(new java.awt.Dimension(15, 10));
        jpTeamMtrcs.add(jLabel9);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("R");
        jLabel10.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel10.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel10.setPreferredSize(new java.awt.Dimension(15, 10));
        jpTeamMtrcs.add(jLabel10);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("All");
        jLabel11.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel11.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel11.setPreferredSize(new java.awt.Dimension(15, 10));
        jpTeamMtrcs.add(jLabel11);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("(H)");
        jLabel12.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel12.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel12.setPreferredSize(new java.awt.Dimension(15, 10));
        jpTeamMtrcs.add(jLabel12);

        jpTeamsMetrics.add(jpTeamMtrcs);

        jPanel2.add(jpTeamsMetrics);

        jpSharedMetrics.setBorder(javax.swing.BorderFactory.createTitledBorder("Shared Information"));
        jpSharedMetrics.setPreferredSize(new java.awt.Dimension(140, 100));
        jpSharedMetrics.setLayout(new javax.swing.BoxLayout(jpSharedMetrics, javax.swing.BoxLayout.Y_AXIS));

        jpSharedMtrcs.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 2));

        jLabel13.setFont(jLabel13.getFont().deriveFont(jLabel13.getFont().getSize()-1f));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Team");
        jLabel13.setMaximumSize(new java.awt.Dimension(35, 10));
        jLabel13.setMinimumSize(new java.awt.Dimension(35, 10));
        jLabel13.setPreferredSize(new java.awt.Dimension(35, 10));
        jpSharedMtrcs.add(jLabel13);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("KE");
        jLabel14.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel14.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel14.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSharedMtrcs.add(jLabel14);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("R");
        jLabel15.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel15.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel15.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSharedMtrcs.add(jLabel15);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("All");
        jLabel16.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel16.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel16.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSharedMtrcs.add(jLabel16);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("(H)");
        jLabel17.setMaximumSize(new java.awt.Dimension(15, 10));
        jLabel17.setMinimumSize(new java.awt.Dimension(15, 10));
        jLabel17.setPreferredSize(new java.awt.Dimension(15, 10));
        jpSharedMtrcs.add(jLabel17);

        jpSharedMetrics.add(jpSharedMtrcs);

        jPanel2.add(jpSharedMetrics);

        jPanel3.add(jPanel2, java.awt.BorderLayout.EAST);

        jScrollPane1.setViewportView(jPanel3);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbOverallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbOverallActionPerformed
        showItemData();
}//GEN-LAST:event_jcbOverallActionPerformed

    private void jcbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAllActionPerformed
        showItemData();
}//GEN-LAST:event_jcbAllActionPerformed

    private void jcbRelevantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbRelevantActionPerformed
        showItemData();
}//GEN-LAST:event_jcbRelevantActionPerformed

    private void jcbMisinformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMisinformationActionPerformed
        showItemData();
}//GEN-LAST:event_jcbMisinformationActionPerformed

    private void jcbAnnotateKEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAnnotateKEActionPerformed
        showItemData();
}//GEN-LAST:event_jcbAnnotateKEActionPerformed

    private void jsTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsTimeStateChanged
        jlTimeSlider.setText( Integer.toString( (int) (m_maxTime*jsTime.getValue()/100.0))+" (s)" );
        updateSubjectsData ();
        updateTeamsData();
        updateSet2Data();
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
                ExportAccessibilityIndex exportData = new ExportAccessibilityIndex();
                for (String name : m_subjectsDataSet1.keySet()) {
                    exportData.addData(name+"::"+m_set1YTitle, m_subjectsDataSet1.get(name));
                }
                for (String name : m_teamsDataSet1.keySet()) {
                    exportData.addData(name+"::"+m_set1YTitle, m_teamsDataSet1.get(name));
                }
                exportData.addData("Overall::"+m_set1YTitle, m_overallDataSet1);
                //
                for (String name : m_subjectsDataSet2.keySet()) {
                    exportData.addData(name+"::"+m_set2YTitle, m_subjectsDataSet2.get(name));
                }
                for (String name : m_teamsDataSet2.keySet()) {
                    exportData.addData(name+"::"+m_set2YTitle, m_teamsDataSet2.get(name));
                }
                exportData.addData("Overall::"+m_set2YTitle, m_overallDataSet2);
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
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbExportData;
    private javax.swing.JCheckBox jcbAll;
    private javax.swing.JCheckBox jcbAnnotateKE;
    private javax.swing.JCheckBox jcbMisinformation;
    private javax.swing.JCheckBox jcbOverall;
    private javax.swing.JCheckBox jcbRelevant;
    private javax.swing.JLabel jlBOTTOMCrossX;
    private javax.swing.JLabel jlBOTTOMCrossY;
    private javax.swing.JLabel jlTOPCrossX;
    private javax.swing.JLabel jlTOPCrossY;
    private javax.swing.JLabel jlTimeSlider;
    private javax.swing.JPanel jpChart;
    private javax.swing.JPanel jpIndividuals;
    private javax.swing.JPanel jpSharedMetrics;
    private javax.swing.JPanel jpSharedMtrcs;
    private javax.swing.JPanel jpSubjMtrcs;
    private javax.swing.JPanel jpSubjectsMetrics;
    private javax.swing.JPanel jpTeamMtrcs;
    private javax.swing.JPanel jpTeams;
    private javax.swing.JPanel jpTeamsMetrics;
    private javax.swing.JSlider jsTime;
    // End of variables declaration//GEN-END:variables

}
