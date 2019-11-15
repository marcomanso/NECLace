/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agility;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 *
 * @author marcomanso
 */
public class ExportAgilityToCSV {

    public static final int MAX_NAME = 80;

    public static final String FILE_PREFIX        = "AGILITY_";

    public static final String SUFFIXES[] = {
        "_information.csv",
        "_awareness.csv",
        "_effectiveness.csv",
        "_avg_correct.csv",
        "_shAw_timeliness.csv"};


    public static void WriteHeader(File file) throws IOException {
        System.out.println("... create file "+file.getName());
        FileWriter writer = new FileWriter(file);
        //
        writer.write("Approach\t");
        writer.write("ManipulationType\t");
        writer.write("Measurement\t");
        //
        writer.write("Agility_Absolute\t");
        writer.write("Agility_Relative\t");
        writer.write("Coverage_sometimes_success\t");
        writer.write("Coverage_always_success\t");
        writer.write("Downgrade\t");
        writer.write("Relative_Downgrade");
        //
        writer.write("\n");
        writer.close();

    }
    public static void WriteData(File file,
            String approach,
            String type,
            String measurement,
            LinkedList<double[][]> list,
            double[][] agilityMap) throws IOException {

        if (!file.exists())
            WriteHeader(file);
        FileWriter writer = new FileWriter(file, true);
        //
        writer.write(approach+"\t");
        writer.write(type+"\t");
        writer.write(measurement+"\t");
        //
        NumberFormat formatter = new DecimalFormat ( "0.00" ) ;
        writer.write(formatter.format(JFAgilityMapChart.calculateAgility(agilityMap))+"\t");
        writer.write(formatter.format(JFAgilityMapChart.calculateRelativeAgility(list))+"\t");
        writer.write(formatter.format(JFAgilityMapChart.calculateCoverage(agilityMap))+"\t");
        writer.write(formatter.format(JFAgilityMapChart.calculateAlwaysCovered(agilityMap))+"\t");
        writer.write(formatter.format(JFAgilityMapChart.calculateDowngrade(agilityMap))+"\t");
        writer.write(formatter.format(JFAgilityMapChart.calculateRelativeDowngrade(agilityMap))+"\t");
        //
        writer.write("\n");
        writer.close();
    }


    public static void FillAgilityMap(
            int index,
            String approach,
            ExperimentDataList dataList,
            LinkedList<double[][]> list,
            double[][] agilityMap) {

        for ( ExperimentData data : dataList ) {
            if (data.m_approachName.equals(approach)) {
                //measurement: INFO
                double[][] m = new double[JFAgilityMapChart.MAP_ROWS_COLUMNS][JFAgilityMapChart.MAP_ROWS_COLUMNS];
                JFAgilityMapChart.buildMatrix(data, ExperimentData.m_measurements[index], m);
                list.add(m);
            }//for if
        }
        JFAgilityMapChart.buildAgilityMap(list, agilityMap);
    }

    public static void ExportToCSVPerType(String directory, String fileName, ExperimentDataList dataList) throws IOException {
        for (int index = 0; index<ExperimentData.m_measurements.length; index++) {
            File file = new File(directory+File.separator+FILE_PREFIX+fileName+SUFFIXES[index]);
            for ( String approach : dataList.getExperimentApproaches() ) {
                LinkedList<double[][]> list = new LinkedList<double[][]>();
                double[][] agilityMap = new double[JFAgilityMapChart.MAP_ROWS_COLUMNS][JFAgilityMapChart.MAP_ROWS_COLUMNS];
                JFAgilityMapChart.zeroAll(agilityMap);
                FillAgilityMap (
                        index,
                        approach,
                        dataList,
                        list,
                        agilityMap);
                WriteData(file, approach, fileName, ExperimentData.m_measurements[index], list, agilityMap);
            }//end for approach
        }//end forindex
    }

    public static void ExportToCSVPerManipulation(String directory, LinkedList<String> manipulations, ExperimentDataList fullDataList) throws IOException {
        String fileName="";
        for (String manipulation : manipulations)
            fileName+=manipulation+"_";
        if (fileName.length() > MAX_NAME)
            fileName=fileName.substring(0, MAX_NAME);
        //select applicable runs
        ExperimentDataList dataList = new ExperimentDataList();
        for (String manipulation : manipulations)
            for (ExperimentData data : fullDataList)
                if ( data.m_agilitySetup.equals(manipulation))
                    dataList.add(data);
        if (dataList.size()!=0) {
            ExportToCSVPerType(directory, fileName, dataList);
        }
    }

    public static void ExportToCSVGlobal(String directory, ExperimentDataList dataList) throws IOException {
        ExportToCSVPerType(directory, "ALL", dataList);
    }

}
