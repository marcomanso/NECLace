/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

import metrics.informationquality.CriticalInformationAccessible.CriticalInformationData;

/**
 *
 * @author mmanso
 */
public class CellColoringCriticalInformation extends javax.swing.table.DefaultTableCellRenderer {

    private final int GOOD = 0;
    private final int ALMOST = 1;

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
        //if (vColIndex==0 && value!=null && value.getClass() == Character.class) {
        if (vColIndex!=0 && value!=null && value.getClass() == CriticalInformationData.class) {
            CriticalInformationData v = (CriticalInformationData)value;
            int diff = v.total - v.nbrCritical;
            if (diff <= GOOD)
                comp.setBackground(java.awt.Color.GREEN);
            else if (diff <=ALMOST)
                comp.setBackground(java.awt.Color.YELLOW);
            else
                comp.setBackground(java.awt.Color.LIGHT_GRAY);
        }
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }

}
