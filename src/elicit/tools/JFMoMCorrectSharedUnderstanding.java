/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import java.util.Vector;

import elicit.message.Message;
import elicit.message.TrialData;
import java.awt.geom.Point2D;
import metrics.awareness.IDsQualityMap.AwUndData;
import metrics.informationquality.InformationQuality;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class JFMoMCorrectSharedUnderstanding extends JFSubjectsFactoidsTable {

    public static final String m_overall = "OVERALL";
    public static final String m_solutionSpace[] = {Message.m_teamWho,Message.m_teamWhat,Message.m_teamWhere,Message.m_teamWhen};
    public static final double m_percentagesSteps[] = {0.10, 0.20, 0.30, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 1.00};
    //

    public JFMoMCorrectSharedUnderstanding(TrialData trialData) {
        super(trialData);
        jlTime.setVisible(false);
        jlTimeSec.setVisible(false);
        jsTime.setVisible(false);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: Correct Shared Understanding (percentage over time)");
    }

    @Override
    public void SetTableModel () {
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoring());
        jtFactoids.setDefaultRenderer(String.class, new javax.swing.table.DefaultTableCellRenderer());
    }

    @Override
    public void CreateFactoidTable() {
        int nbrRows = m_solutionSpace.length+1; //must add overall
        int nbrCols = m_percentagesSteps.length+1;

        Object[][] obj = new Object[nbrRows][nbrCols];
        String[] header = new String[m_percentagesSteps.length+1];
        header[0] = "Solution Space";
        for ( int i=1; i<header.length; i++ ) {
            header[i]=Double.toString(m_percentagesSteps[i-1]);
        }
        //
        int count = 0;
        for (String s : m_solutionSpace ) {
            obj[count][0] = s;
            count++;
        }
        obj[count][0] = m_overall;

        jtFactoids.setModel(
            new javax.swing.table.DefaultTableModel(obj,header) {

            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
                //return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
                //return canEdit[columnIndex];
            }
        });//end set model

        jspFactoidsTable.setViewportView(jtFactoids);
    }//end CreateFactoidTable

    public int getColIndexInTable (double value) {
        int col=-1;
        for ( int i=0; i<m_percentagesSteps.length; i++ )
            if (value>=m_percentagesSteps[i])
                col=i+1;
//System.out.println("col:"+col+" value="+value);
        return col;
    }

    //Shared understanding may decrease during run
    //we should only evaluate score based on last best value
    //therefore: when ShUnd degrades, higher scores should be removed
    public void ClearHigherData (int row, int startingCol) {
        if (startingCol==-1)
            startingCol=1;
            //jtFactoids.setValueAt(null, row, 1);
        //else
            //for ( int col=startingCol+1; col<m_percentagesSteps.length; col++ )
            for ( int col=startingCol+1; col<=m_percentagesSteps.length; col++ )
                if ( jtFactoids.getValueAt(row, col)!=null )
                    jtFactoids.setValueAt(null, row, col);
    }

    public void FillData (String name, Point2D.Double point2D) {
        int row = getRowIndexInTable(name);
        int col = getColIndexInTable(point2D.y);
        //chech is ShUnd has decreased  if so clean values
        ClearHigherData (row, col);
        if (col!=-1) {
//System.out.println("name: "+name+" X-value: "+point2D.x+" Y-value: "+point2D.y);
            //ClearHigherData (row, col);
            if (jtFactoids.getValueAt(row, col)==null)
                jtFactoids.setValueAt((int)point2D.x, row, col);
        }
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //1. per sol space
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWho)
            FillData(m_solutionSpace[0], new Point2D.Double(point2D.x , point2D.y/(double)m_trialData.m_organizationInformation.m_memberList.size()) );
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhat)
            FillData(m_solutionSpace[1], new Point2D.Double(point2D.x , point2D.y/(double)m_trialData.m_organizationInformation.m_memberList.size()) );
            //FillData(m_solutionSpace[1], point2D);
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhere)
            FillData(m_solutionSpace[2], new Point2D.Double(point2D.x , point2D.y/(double)m_trialData.m_organizationInformation.m_memberList.size()) );
            //FillData(m_solutionSpace[2], point2D);
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhen)
            FillData(m_solutionSpace[3], new Point2D.Double(point2D.x , point2D.y/(double)m_trialData.m_organizationInformation.m_memberList.size()) );
            //FillData(m_solutionSpace[3], point2D);
        //2. overall
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall)
            FillData(m_overall, new Point2D.Double(point2D.x , point2D.y/(double)m_trialData.m_organizationInformation.m_memberList.size()) );
            //FillData(m_overall, point2D);
        /*
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWho)
            FillData(m_solutionSpace[0], point2D);
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhat)
            FillData(m_solutionSpace[1], point2D);
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhere)
            FillData(m_solutionSpace[2], point2D);
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsWhen)
            FillData(m_solutionSpace[3], point2D);
        //2. overall
        for (Point2D.Double point2D : m_trialData.m_subjectsIDsQualityMap.m_CorrectIDsOverall)
            FillData(m_overall, point2D);
         *
         */
    }

}
