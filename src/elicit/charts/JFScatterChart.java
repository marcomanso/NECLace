/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elicit.charts;

//
import java.awt.BasicStroke;
import java.util.*;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.Component;
import java.awt.Stroke;
import javax.swing.JCheckBox;
import java.text.NumberFormat;
import java.text.DecimalFormat;
//
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.plot.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.category.*;

//
/**
 *
 * @author mmanso
 */
public class JFScatterChart extends JFSimpleTimeChart {

    public JFScatterChart(String title) {
        super(title);
        //m_renderer = new XYDotRenderer();
        jPanel16.setVisible(false);
        jcbAnnotateKE.setSelected(true);
    }

    public java.awt.Color getColor (String name) {
        if (name.toUpperCase().indexOf("COORDINATOR")!=-1
            || name.toUpperCase().indexOf("BROKER")!=-1)
        {
            return java.awt.Color.MAGENTA;
        }
        else if (name.toUpperCase().indexOf("LEADER")!=-1)
        {
            return java.awt.Color.BLUE;
        }
        else
            return java.awt.Color.GRAY;
    }

    @Override
    public void showChart() {
        super.showChart();
        //now: hack a bit ...
        //SHOW ALL VALUES
        XYPlot plot = (XYPlot) m_chart.getPlot();
        Component[] comp = jpIndividuals.getComponents();
        for (int i = 0; i < comp.length; i++) {
            JCheckBox box = (JCheckBox) comp[i];
            box.setSelected(true);
            showItemDataPerIndex(plot, m_annotationsSet, i, box.isSelected());
        }

    }

    ////////////////////////////////////////////////////////////////////////
    //
    // PLOT DATA
    //
    @Override
    public void plotAccessibilityByChart(XYPlot plot,
            TreeMap<String, Vector<Point2D.Double>> dataSet,
            Vector<ArrayList<XYPointerAnnotation>> annotations) {

        int i = 0;
        //for (String subjectName : informationAccessibleBySubjects.keySet()) {
        //must preserve order of subjects
        for (String name : dataSet.keySet()) {
            XYStepRenderer renderer = new XYStepRenderer();
            XYSeries serie = new XYSeries(name);
            for (Point2D.Double data : dataSet.get(name)) {
                serie.add(data.x, data.y);
                //make annotation
                XYPointerAnnotation annot = new XYPointerAnnotation(name, data.x, data.y, 100.0);
                annot.setTipRadius(10.0);
                annot.setBaseRadius(35.0);
                annot.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 8));
                annot.setPaint(getColor(name));
                annot.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
                annotations.elementAt(i).add(annot);
            }
            XYSeriesCollection collection = new XYSeriesCollection();
            collection.addSeries(serie);
            plot.setRenderer(i, renderer);
            plot.setDataset(i, collection);
            i++;
        }
    }

    @Override
    public void setChartLabels (XYPlot plotP) {
        //XYItemLabelGenerator gen = new StandardXYItemLabelGenerator("{2}", new DecimalFormat("0.00"), new DecimalFormat("0.00"));
        for (int i=0; i<plotP.getRendererCount(); i++) {
            for (int j=0; j<=INFOQ_ACCESSIBILITY_INDEX_ALL;j++) {
                plotP.getRenderer(i).setSeriesStroke(j,
                                new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,1.0f, new float[] {10.0f, 6.0f}, 0.0f)
                                );
                plotP.getRenderer(i).setSeriesItemLabelsVisible(j, true);
            }
        }

    }

    @Override
    public void populatePlot(XYPlot plot) {
        ValueAxis rangeAxis = new NumberAxis(m_setYTitle);
        //TickUnitSource units = NumberAxis.createIntegerTickUnits();
        //rangeAxis.setStandardTickUnits(units);
        if (m_setAxis) {
            rangeAxis.setAutoRange(false);
            rangeAxis.setRange(m_minValue, m_maxValue);
            plot.getDomainAxis().setRange(m_minValue, m_maxValue);
        }
        plot.setRangeAxis(rangeAxis);
        plotAccessibilityByChart(plot, m_dataSetMap, m_annotationsSet);
    }
}
