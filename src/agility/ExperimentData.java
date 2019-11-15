/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agility;

/**
 *
 * @author marcomanso
 */
public class ExperimentData {

    public String m_approachNumber;
    public String m_approachName;
    public String m_agilitySetup;
    public String m_fileName;

    public Score m_informationReach;
    public Score m_correctAwareness;
    public Score m_effectiveness;
    public Score m_avgCorrectness;
    public Score m_sharedAwarenessTimeliness;
    public static String[] m_measurements = {"Average Relevant Information (Reach)",
                                            "Shared Awareness",
                                            "Effectiveness",
                                            "AVG Correctness/Max Timeliness",
                                            "Shared Awareness/Max Timeliness"};

    public ExperimentData(int nbrSteps) {
        m_informationReach = new Score(nbrSteps);
        m_correctAwareness = new Score(nbrSteps);
        m_effectiveness    = new Score(nbrSteps);
        m_avgCorrectness   = new Score(nbrSteps);
        m_sharedAwarenessTimeliness   = new Score(nbrSteps);
    }
    public Score getMeasurementDataSet(String measurement) {
        if ( measurement.equals(m_measurements[0]) )
            return m_informationReach;
        else if ( measurement.equals(m_measurements[1]) )
            return m_correctAwareness;
        else if ( measurement.equals(m_measurements[2]) )
            return m_effectiveness;
        else if ( measurement.equals(m_measurements[3]) )
            return m_avgCorrectness;
        else
            return m_sharedAwarenessTimeliness;
    }

}
