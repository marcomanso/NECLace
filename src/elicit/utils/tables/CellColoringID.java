/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.tables;

import java.util.TreeMap;
import elicit.tools.JFSubjectsUnderstandingTable;

/**
 *
 * @author mmanso
 */
public class CellColoringID extends javax.swing.table.DefaultTableCellRenderer {

    private final int GOOD = 0;
    private final int ALMOST = 1;

    public TreeMap<String, String> m_solution;

    public CellColoringID(TreeMap<String, String> solution) {
        m_solution = solution;
    }

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
        if (vColIndex==4 && value!=null && value.getClass() == String.class) {
            comp.setBackground(java.awt.Color.LIGHT_GRAY);
        }
        else if (vColIndex!=0 && value!=null && value.getClass() == String.class) {
            for (int i=1; i< JFSubjectsUnderstandingTable.m_header.length; i++)
                if (vColIndex==i) {
                    if (m_solution.get(JFSubjectsUnderstandingTable.m_header[i])==null)
                        comp.setBackground(java.awt.Color.WHITE);
                    else if ( value.equals(m_solution.get(JFSubjectsUnderstandingTable.m_header[i])) )
                        comp.setBackground(java.awt.Color.GREEN);
                    //else if ( ((String)value).indexOf(m_solution.get(JFSubjectsUnderstandingTable.m_header[i]))!=-1 )
                    else if ( m_solution.get(JFSubjectsUnderstandingTable.m_header[i]).indexOf((String)value)!=-1 )
                        comp.setBackground(java.awt.Color.ORANGE);
                    else
                        comp.setBackground(java.awt.Color.RED);
                }
        }
        else
            comp.setBackground(java.awt.Color.WHITE);
        return comp;
    }

}
