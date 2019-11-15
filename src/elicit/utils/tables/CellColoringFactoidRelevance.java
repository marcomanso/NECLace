/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

/**
 *
 * @author mmanso
 */
public class CellColoringFactoidRelevance extends javax.swing.table.DefaultTableCellRenderer {

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
        if (vColIndex==0 && value!=null && value.getClass() == Character.class) {
            Character v = (Character)value;
            comp.setBackground(CellColoring.translateFactoidRelevanceToColor(v.charValue()));
        }
        else if (vColIndex!=0 && value!=null && value.getClass() == Character.class) {
            Character v = (Character)value;
            comp.setBackground(CellColoring.translateFactoidRelevanceToColor(v.charValue()));
        }
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }

}
