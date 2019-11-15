/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

import java.util.StringTokenizer;

/**
 *
 * @author mmanso
 */
public class CellColoringSubjectsReciprocity extends javax.swing.table.DefaultTableCellRenderer  {

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

        if (vColIndex!=0 && vColIndex == rowIndex+1)
            comp.setBackground(java.awt.Color.BLACK);
        else if (vColIndex!=0 && value!=null && value.getClass() == String.class && ((String)value).length()!=0) {
            StringTokenizer st = new StringTokenizer((String)value);
            //paint according to first or second value
            int v1 = Integer.parseInt(st.nextToken());
            st.nextToken();
            int v2 = Integer.parseInt(st.nextToken());
            if (v1>v2)
                comp.setBackground(CellColoring.translateNumberToColor_scale_10_5_3_1(v1));
            else
                comp.setBackground(CellColoring.translateNumberToColor_scale_10_5_3_1(v2));
        }
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }

}
