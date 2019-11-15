/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import elicit.exportdata.ExportAccessibilityIndex.ExportAccessibilityIndexData;
import metrics.informationquality.InformationAccessible.AccessibilityIndexVector;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class ExportAccessibilityIndex extends Vector<ExportAccessibilityIndexData> {

    FileWriter m_writer;

    public class ExportAccessibilityIndexData {
        public String label;
        public AccessibilityIndexVector dataVector;
        public ExportAccessibilityIndexData (String label_p, AccessibilityIndexVector data_p) {
            label = label_p;
            dataVector = data_p;
        }
    }

    public ExportAccessibilityIndex () {
    }
    public void addData (String label, AccessibilityIndexVector data) {
        add(new ExportAccessibilityIndexData(label, data));
    }
    public void writeData (File fileName) throws IOException {
        m_writer = new FileWriter(fileName);
        //
        int[] indexV = new int[this.size()];
        double currentTime=getStartTime();
        writeHeader();
        do {
            m_writer.write(currentTime+"\t");
            for (int i=0; i<indexV.length; i++) {
                InformationAccessibleData data = getElement(currentTime,i,indexV[i]);
                if (data!=null)
                    m_writer.write(data.indexKE+"\t"
                                   +data.indexRelevant+"\t"
                                   +data.indexMisinfo+"\t"
                                   +data.indexAll+"\t"
                                   +data.indexHoarded+"\t");
                else
                    m_writer.write("0.0\t"
                                   +"0.0\t"
                                   +"0.0\t"
                                   +"0.0\t"
                                   +"0.0\t");

            }
            m_writer.write("\n");
            currentTime=getNextTime(indexV);
        }
        while (!finishedLists(indexV));
        m_writer.close();
    }
    private void writeHeader () throws IOException {
        m_writer.write("\t");
        for ( ExportAccessibilityIndexData d : this ) {
            m_writer.write(d.label+"\t\t\t\t\t");
        }
        m_writer.write("\n");
        //
        m_writer.write("Time\t");
        for ( ExportAccessibilityIndexData d : this ) {
            m_writer.write("KE\tR\tM\tA\tH\t");
        }
        m_writer.write("\n");
    }
    private double getStartTime() {
        double startTime = 0.0;
        for (ExportAccessibilityIndexData data : this) {
            if (data.dataVector.elementAt(0).time < startTime)
                startTime = data.dataVector.elementAt(0).time;
        }
        return startTime;
    }
    private InformationAccessibleData getElement (double time, int i, int index) {
        if (elementAt(i).dataVector.elementAt( index ).time > time) {
            return null;
        }
        else {
            return elementAt(i).dataVector.elementAt( index );
        }
    }
    private double getNextTime(int[] indexV) {
        double nextTime = Double.MAX_VALUE;
        //first find next minimum value
        for (int i=0; i<this.size(); i++) {
            if (indexV[i]+1 < elementAt(i).dataVector.size()) {
                if (this.elementAt(i).dataVector.elementAt( indexV[i]+1 ).time < nextTime) {
                    nextTime = this.elementAt(i).dataVector.elementAt( indexV[i]+1 ).time;
                }
            }
        }
        //now increment all indexes to point to the same time
        for (int i=0; i<this.size(); i++) {
            if (indexV[i]+1 < elementAt(i).dataVector.size()) {
                if (this.elementAt(i).dataVector.elementAt( indexV[i]+1 ).time == nextTime) {
                    indexV[i]++;
                }
            }
        }
        return nextTime;
    }

    private boolean finishedLists (int[] indexV) {
        boolean end = true;
        for (int i=0; i<this.size(); i++) {
            if ( indexV[i] < elementAt(i).dataVector.size()-1 ) {
                end = false;
                break;
            }
        }
        return end;
    }

}
