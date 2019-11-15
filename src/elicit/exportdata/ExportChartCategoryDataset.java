/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author mmanso
 */
public class ExportChartCategoryDataset {

    FileWriter m_writer;
    CategoryDataset m_data;

    public ExportChartCategoryDataset (CategoryDataset data) {
        m_data = data;
    }

    public void writeData (File fileName) throws IOException {
        m_writer = new FileWriter(fileName);
        for (Object colText : m_data.getColumnKeys() ) 
            for (Object rowText : m_data.getRowKeys() ) {
                if (m_data.getValue((String)rowText, (String)colText)!=null) {
                    m_writer.write(colText+"\t"
                                   +rowText+"\t"
                                   +m_data.getValue((String)rowText, (String)colText)+"\t"+"\n");
                }
            }
        m_writer.close();
    }
}
