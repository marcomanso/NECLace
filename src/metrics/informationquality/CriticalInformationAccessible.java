/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.informationquality;

import elicit.message.FactoidMessage;
import java.util.Vector;
import java.util.TreeMap;
import metrics.informationquality.InformationAccessible.InformationAccessibleData;

/**
 *
 * @author mmanso
 */
public class CriticalInformationAccessible {

    int m_totalNbrFactoids = 0;
    double m_endTime = 0.0;

    public TreeMap<String, Vector<String>> m_solutionFactoids;

    public class CriticalInformationData {
        public double time;
        public String factoidMetadata;
        public String source;
        //
        public int total;
        public int nbrCritical;
        public int nbrHoard;
        //
        public CriticalInformationData() {
        }
        @Override
        public String toString() {
            return nbrCritical+" | "+total +" ("+nbrHoard+")";
        }
    }
    
    //2-D map indexed by:
    // - subject
    // - solution space
    public TreeMap<String, TreeMap<String, CriticalInformationData>> m_subjectsCriticalInformationMap = new TreeMap<String, TreeMap<String, CriticalInformationData>>();
    //2-D map indexed by:
    // - teams
    // - solution space
    public TreeMap<String, TreeMap<String, CriticalInformationData>> m_teamsCriticalInformationMap = new TreeMap<String, TreeMap<String, CriticalInformationData>>();

    //
    public void setSolutionFactoids (TreeMap<String, Vector<String>> solutionFactoids) {
        m_solutionFactoids = solutionFactoids;
    }

    public int getTotalCriticalFacts(String solutionSpace) {
        return m_solutionFactoids.get(solutionSpace).size();
    }

    public void GetStatsAtTime(double time, InformationAccessible accessibilityIndex) {
        //parse solution space
        for ( String solutionSpace : m_solutionFactoids.keySet() ) {
            //parse subjects
            for ( String s : accessibilityIndex.m_informationAccessibleBySubjects.keySet() ) {
                if (m_subjectsCriticalInformationMap.get(s)==null)
                    m_subjectsCriticalInformationMap.put(s, new TreeMap<String, CriticalInformationData>());
                if (m_subjectsCriticalInformationMap.get(s).get(solutionSpace)==null)
                    m_subjectsCriticalInformationMap.get(s).put(solutionSpace, new CriticalInformationData());
                CriticalInformationData data = m_subjectsCriticalInformationMap.get(s).get(solutionSpace);
                data.time = time;
                data.total = getTotalCriticalFacts(solutionSpace);
                data.nbrCritical=0;
                data.nbrHoard=0;
                //parse all information accessible by subject
                for (InformationAccessibleData informationAccessible : accessibilityIndex.m_informationAccessibleBySubjects.get(s)) {
                    if (informationAccessible.time <= time
                        && FactoidMessage.ContainsFactoidInList(informationAccessible.factoidMetadata, m_solutionFactoids.get(solutionSpace)) )
                    {
                        data.nbrCritical++;
                        //check if it is hoarded
                        if (isHoardedBySubject(time, s, solutionSpace, informationAccessible.factoidMetadata, accessibilityIndex)) {
                            data.nbrHoard++;
                        }
                    }
                }
            }// end for subjects
            //parse teams
            for (String team : accessibilityIndex.m_informationAccessibleByTeams.keySet() ) {
                if (m_teamsCriticalInformationMap.get(team)==null)
                    m_teamsCriticalInformationMap.put(team, new TreeMap<String, CriticalInformationData>());
                if (m_teamsCriticalInformationMap.get(team).get(solutionSpace)==null)
                    m_teamsCriticalInformationMap.get(team).put(solutionSpace, new CriticalInformationData());
                CriticalInformationData data = m_teamsCriticalInformationMap.get(team).get(solutionSpace);
                data.time = time;
                data.total = getTotalCriticalFacts(solutionSpace);
                data.nbrCritical=0;
                data.nbrHoard=0;
                //parse all information accesseb by subject
                for (InformationAccessibleData informationAccessible : accessibilityIndex.m_informationAccessibleByTeams.get(team)) {
                    if (informationAccessible.time <= time
                        && FactoidMessage.ContainsFactoidInList(informationAccessible.factoidMetadata, m_solutionFactoids.get(solutionSpace)) )
                    {
                        data.nbrCritical++;
                        if (isHoardedByTeam(time, team, solutionSpace, informationAccessible.factoidMetadata, accessibilityIndex)) {
                            data.nbrHoard++;
                        }
                    }
                }
            }//end for teams
        }// end for solutionSpace
    }

    public boolean isHoardedBySubject (double time, String s, String solutionSpace, String factoidMetadata, InformationAccessible accessibilityIndex) {
        for (String s2: accessibilityIndex.m_informationAccessibleBySubjects.keySet())
            if (!s2.equals(s))
                for (InformationAccessibleData informationAccessible : accessibilityIndex.m_informationAccessibleBySubjects.get(s2))
                    if ( informationAccessible.time <= time 
                         && FactoidMessage.isEqualTo(informationAccessible.factoidMetadata, factoidMetadata) )
                        return false;
        return true;
    }

    public boolean isHoardedByTeam (double time, String t, String solutionSpace, String factoidMetadata, InformationAccessible accessibilityIndex) {
        //must exist more than 1 team (e.g., edge has 1 team - NA)
        if (accessibilityIndex.m_informationAccessibleByTeams.keySet().size()>1) {
            for (String t2: accessibilityIndex.m_informationAccessibleByTeams.keySet())
                if (!t2.equals(t))
                    for (InformationAccessibleData informationAccessible : accessibilityIndex.m_informationAccessibleByTeams.get(t2))
                        if ( informationAccessible.time <= time
                             && FactoidMessage.isEqualTo(informationAccessible.factoidMetadata, factoidMetadata) )
                            return false;
            return true;
        }
        else
            return false;
    }

}
