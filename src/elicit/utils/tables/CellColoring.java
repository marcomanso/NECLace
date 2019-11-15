/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

import elicit.message.Message;
import metrics.interaction.HoardingMap.HoardingInformation;

/**
 *
 * @author mmanso
 */
public class CellColoring extends javax.swing.table.DefaultTableCellRenderer {

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
        if (vColIndex != 0 && value!=null) {
            if (value.getClass() == Integer.class) {
                comp.setBackground(CellColoring.translateNumberToColor_scale_10_5_3_1((Integer)value));
            } else if (value.getClass() == Character.class) {
                comp.setBackground(CellColoring.translateFactoidRelevanceToColor((Character)value));
            } else if (value.getClass() == String.class) {
                //do nothing
            } else
                comp.setBackground(java.awt.Color.WHITE);
        }
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }

    public static java.awt.Color translateFactoidRelevanceToColor(char messageTypeChar) {
        if (messageTypeChar == Message.m_KChar || messageTypeChar == Message.m_EChar) {
            return java.awt.Color.GREEN;
        } else if (messageTypeChar == Message.m_SChar) {
            return java.awt.Color.ORANGE;
        } else if (messageTypeChar == Message.m_MChar) {
            return java.awt.Color.RED;
        } else if (messageTypeChar == Message.m_NChar) {
            return java.awt.Color.LIGHT_GRAY;
        //minor hack to color Hoardings
        } else if (messageTypeChar == HoardingInformation.FACTOID_HOARDED) {
            return java.awt.Color.RED;
        } else {
            return java.awt.Color.WHITE;
        }
    }

    public static java.awt.Color translateNumberToColor_scale_10_5_3_1(int val) {
        if (val>10)
            return java.awt.Color.GREEN;
        else if (val>5)
            return java.awt.Color.YELLOW;
        else if (val>3)
            return java.awt.Color.CYAN;
        else if (val>=1)
            return java.awt.Color.LIGHT_GRAY;
        else
            return java.awt.Color.WHITE;
    }

    public static java.awt.Color translateTimeToColor_scale_60_300_600 (int val) {
        if (val<60)
            return java.awt.Color.GREEN;
        else if (val<300)
            return java.awt.Color.LIGHT_GRAY;
        else if (val<600)
            return java.awt.Color.YELLOW;
        else 
            return java.awt.Color.RED;
        //else
        //    return java.awt.Color.WHITE;
    }

}
