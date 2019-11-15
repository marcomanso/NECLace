/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.exportdata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import elicit.exportdata.ExportData2D.ExportedData2D;
import java.awt.geom.Point2D;

/**
 *
 * @author mmanso
 */
public class ExportData2D extends Vector<ExportedData2D> {

    FileWriter m_writer;

    public class ExportedData2D {
        public String m_label;
        public Vector<Point2D.Double> m_dataVector;
        public ExportedData2D (String label_p, Vector<Point2D.Double> data_p) {
            m_label = label_p;
            m_dataVector = data_p;
        }
    }

    public ExportData2D () {
    }

    public void addData (String label, Vector<Point2D.Double> data) {
        add(new ExportedData2D(label, data));
    }

    public void writeData (File fileName) throws IOException {
        m_writer = new FileWriter(fileName);
        //
        int[] indexV = new int[this.size()];
        double currentXValue=getStartXValue();
        writeHeader();
        do {
            m_writer.write(currentXValue+"\t");
            for (int i=0; i<indexV.length; i++) {
                Point2D.Double data = getElement(currentXValue,i,indexV[i]);
                if (data!=null)
                    m_writer.write(data.y+"\t");
                else
                    m_writer.write("0.0\t");

            }
            m_writer.write("\n");
            currentXValue=getNextXValue(indexV);
        }
        while (!finishedLists(indexV));
        m_writer.close();
    }
    private void writeHeader () throws IOException {
        m_writer.write("\t");
        for ( ExportedData2D d : this ) {
            m_writer.write(d.m_label+"\t");
        }
        m_writer.write("\n");
        //
        m_writer.write("x\t");
        for ( ExportedData2D d : this ) {
            m_writer.write("y\t");
        }
        m_writer.write("\n");
    }
    private double getStartXValue() {
        double startX = 0.0;
        for (ExportedData2D data : this) {
            if (data.m_dataVector.elementAt(0).x < startX)
                startX = data.m_dataVector.elementAt(0).x;
        }
        return startX;
    }
    private Point2D.Double getElement (double Xvalue, int i, int index) {
        if (elementAt(i).m_dataVector.elementAt( index ).x > Xvalue) {
            return null;
        }
        else {
            return elementAt(i).m_dataVector.elementAt( index );
        }
    }
    private double getNextXValue(int[] indexV) {
        double nextX = Double.MAX_VALUE;
        //first find next minimum value
        for (int i=0; i<this.size(); i++) {
            if (indexV[i]+1 < elementAt(i).m_dataVector.size()) {
                if (this.elementAt(i).m_dataVector.elementAt( indexV[i]+1 ).x < nextX) {
                    nextX = this.elementAt(i).m_dataVector.elementAt( indexV[i]+1 ).x;
                }
            }
        }
        //now increment all indexes to point to the same X value
        for (int i=0; i<this.size(); i++) {
            if (indexV[i]+1 < elementAt(i).m_dataVector.size()) {
                if (this.elementAt(i).m_dataVector.elementAt( indexV[i]+1 ).x == nextX) {
                    indexV[i]++;
                }
            }
        }
        return nextX;
    }

    private boolean finishedLists (int[] indexV) {
        boolean end = true;
        for (int i=0; i<this.size(); i++) {
            if ( indexV[i] < elementAt(i).m_dataVector.size()-1 ) {
                end = false;
                break;
            }
        }
        return end;
    }

}
