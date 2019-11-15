/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

/**
 *
 * @author mmanso
 */
public class CellColoringQuantityOfFactoids extends javax.swing.table.DefaultTableCellRenderer  {

    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int rowIndex,
            int vColIndex)
    {
        java.awt.Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);
        if (vColIndex != 0 && value!=null && value.getClass() == Integer.class)
            comp.setBackground(CellColoring.translateNumberToColor_scale_10_5_3_1((Integer)value));
        else if (value.getClass() == Character.class)
            comp.setBackground(CellColoring.translateFactoidRelevanceToColor((Character)value));
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }
}
