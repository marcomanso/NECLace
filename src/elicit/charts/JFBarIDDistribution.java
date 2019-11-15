/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.charts;

import elicit.exportdata.ExportChartCategoryDataset;

import java.util.TreeMap;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author mmanso
 */
public class JFBarIDDistribution extends JFBarChart {

    //
    public TreeMap<String, TreeMap<String, Integer>> m_dataSetMap = new TreeMap<String, TreeMap<String, Integer>>();

    public JFBarIDDistribution(String title, TreeMap<String,String> solutionMap) {
        super(title);
        //add label to display solution
        javax.swing.JLabel jlSolution = new javax.swing.JLabel();
        for (String keySol : solutionMap.keySet()) {
            jlSolution.setText(jlSolution.getText()+" "+keySol+" ( "+solutionMap.get(keySol)+" )    ");
        }
        jPanelTOP.add(jlSolution);
    }

    @Override
    public void addDataSet(String index, TreeMap<String, Integer> data) {
        m_dataSetMap.put(index, data);
    }
    @Override
    public CategoryDataset makeDataSet() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        for (String category : m_dataSetMap.keySet())
            for (String id : m_dataSetMap.get(category).keySet())
                d.addValue(m_dataSetMap.get(category).get(id),id, category);
        return d;
    }
    
    @Override
    protected void ExportData() {
        javax.swing.JFileChooser jfcOpenFile = new javax.swing.JFileChooser();
        int returnVal = jfcOpenFile.showOpenDialog(this);
        java.io.File file = jfcOpenFile.getSelectedFile();
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            if (file == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please select a valid file.");
            } else if (file.exists()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cannot overwrite existing file.");
            } else {
                ExportChartCategoryDataset exportData = new ExportChartCategoryDataset(m_dataset);
                try {
                    exportData.writeData(file);
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Error writing file: " + ex.getMessage());
                }
            }
        }//end selected file
    }

}
