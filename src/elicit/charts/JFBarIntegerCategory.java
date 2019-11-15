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
public class JFBarIntegerCategory extends JFBarChart {
    
    TreeMap<String, Integer> m_dataSetMap = new TreeMap<String, Integer>();
    String m_setName = "Set";

    public JFBarIntegerCategory(String title) {
        super(title);
    }

    public JFBarIntegerCategory(String title, boolean vertical) {
        super(title, vertical);
    }

    @Override
    public void addDataSet(String index, TreeMap<String, Integer> data) {
        m_setName = index;
        m_dataSetMap = data;
    }

    public void addDataSet(String name, String index, Integer data) {
        m_setName = name;
        m_dataSetMap.put(index, data);
    }

    @Override
    public CategoryDataset makeDataSet() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        for (String category : m_dataSetMap.keySet())
            d.addValue(m_dataSetMap.get(category), m_setName, category);
            //d.addValue(m_dataSetMap.get(category), category, category);
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
