/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

import metrics.interaction.HoardingMap.HoardingInformation;

/**
 *
 * @author mmanso
 */
public class CellColoringFactoidHoarded extends javax.swing.table.DefaultTableCellRenderer {

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
        if (vColIndex!=0 && value!=null && value.getClass() == Character.class) {
            Character v = (Character)value;
            comp.setBackground(translateFactoidHoardedInfoToColor(v.charValue()));
        }
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }

    public static java.awt.Color translateFactoidHoardedInfoToColor(char c) {
        if (c == HoardingInformation.FACTOID_SHARED ) {
            return java.awt.Color.GREEN;
        //} else if (c == HoardingInformation.FACTOID_HOARDED) {
        //    return java.awt.Color.RED;
        } else {
            return java.awt.Color.WHITE;
        }
    }

}
