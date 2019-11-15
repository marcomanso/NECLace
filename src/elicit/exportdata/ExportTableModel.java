/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import java.io.File;
import javax.swing.table.TableModel;

/**
 *
 * @author mmanso
 */
public class ExportTableModel {

    public static void WriteTableModelToFile (TableModel model, File file) throws Exception {
        java.io.FileWriter writer = new java.io.FileWriter(file);
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++)
                writer.write( model.getValueAt(row, col)+"\t");
            writer.write("\n");
        }
        writer.close();
    }

}
