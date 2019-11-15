/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agility;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author marcomanso
 */
public class ExperimentDataList extends LinkedList<ExperimentData> {

    public List<String> getExperimentApproaches() {
        LinkedList<String> set = new LinkedList<String>();
        //force order
        for (double nbr=1; nbr<=5; nbr+=0.5) {
            for (ExperimentData data : this ) {
                if (nbr==Double.parseDouble(data.m_approachNumber)) {
                    set.add(data.m_approachName);
                    break;
                }
            }
        }
        return set;
    }
    public String[] getExperimentMeasures() {
        return ExperimentData.m_measurements;
    }
    public List<String> getExperimentManipulations() {
        LinkedList<String> set = new LinkedList<String>();
        for (ExperimentData data : this )
            if (!set.contains(data.m_agilitySetup))
                set.addLast(data.m_agilitySetup);
        return set;
    }
    
}
