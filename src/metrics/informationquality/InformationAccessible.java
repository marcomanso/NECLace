/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.informationquality;

import elicit.message.*;
import elicit.message.TrialData.Subject;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

/**
 *
 * @author mmanso
 */
public class InformationAccessible {

    int m_totalNbrFactoids = 0;
    double m_endTime = 0.0;

    public class InformationAccessibleData {
        public double time;
        public String factoidMetadata;
        public String source;
        //
        public double indexAll;
        public double indexRelevant;
        public double indexKE;
        public double indexMisinfo;
        public double indexHoarded = 0.0;

        public InformationAccessibleData(double t, String s, String src) {
            time=t;factoidMetadata=s;source=src;
        }
    };
    public class AccessibilityIndexVector extends Vector<InformationAccessibleData> {
        @Override
        public boolean add (InformationAccessibleData data) {
            double iA=0.0;
            double iKE=0.0;
            double iR=0.0;
            double iM=0.0;
            //only adds if factoid is new
            for (InformationAccessibleData access : this) {
                iA=access.indexAll;
                iR=access.indexRelevant;
                iKE=access.indexKE;
                iM=access.indexMisinfo;
                if ( FactoidMessage.isEqualTo(access.factoidMetadata, data.factoidMetadata)) {
                    return false;
                }
            }
            if (!data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY)) {
                if (FactoidMessage.isKE(data.factoidMetadata))
                    iKE+=1;
                if (FactoidMessage.isRelevant(data.factoidMetadata))
                    iR+=1;
                else if (FactoidMessage.isMisinfo(data.factoidMetadata))
                    iM+=1;
                iA++;
            }
            data.indexRelevant=iR;
            data.indexKE=iKE;
            data.indexAll=iA;
            data.indexMisinfo=iM;
            return super.add(data);
        }

        public boolean addComulative(InformationAccessibleData data) {
            double iA=0.0;
            double iKE=0.0;
            double iR=0.0;
            double iM=0.0;
            if (this.size()!=0) {
                iKE=this.elementAt(this.size()-1).indexKE;
                iR=this.elementAt(this.size()-1).indexRelevant;
                iM=this.elementAt(this.size()-1).indexMisinfo;
                iA=this.elementAt(this.size()-1).indexAll;
            }
            if (!data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY)) {
                if (FactoidMessage.isKE(data.factoidMetadata))
                    iKE+=1;
                if (FactoidMessage.isRelevant(data.factoidMetadata))
                    iR+=1;
                else if (FactoidMessage.isMisinfo(data.factoidMetadata))
                    iM+=1;
                iA++;
            }
            data.indexRelevant=iR;
            data.indexKE=iKE;
            data.indexAll=iA;
            data.indexMisinfo=iM;
            return super.add(data);            
        }

        public void setEndTime(double endTime_p) {
            add(new InformationAccessibleData(endTime_p, Message.MESSAGE_TYPE_DUMMY, null));
        }
        public boolean hasFactoid (String factoidMetadata_p) {
            boolean value = false;
            for ( InformationAccessibleData data : this ) {
                if ( FactoidMessage.isEqualTo(data.factoidMetadata, factoidMetadata_p) ) {
                    value = true;
                    break;
                }
            }
            return value;
        }
        public InformationAccessibleData getLastElement() {
            return this.elementAt(this.size()-1);
        }
        public InformationAccessibleData getLastElementBeforeTime(double time) {
            InformationAccessibleData data = null;
            for ( InformationAccessibleData d : this ) {
                if ( d.time > time )
                    break;
                else
                    data = d;
            }
            return data;
        }
    };
    public AccessibilityIndexVector m_accessIndexOverall = new AccessibilityIndexVector();
    public AccessibilityIndexVector m_set2AccessIndexOverall = new AccessibilityIndexVector();
    //
    public TreeMap<String, AccessibilityIndexVector> m_informationAccessibleByTeams = new TreeMap<String, AccessibilityIndexVector>();
    public TreeMap<String, AccessibilityIndexVector> m_set2InformationAccessibleByTeams = new TreeMap<String, AccessibilityIndexVector>();
    //
    public TreeMap<String, AccessibilityIndexVector> m_informationAccessibleBySubjects = new TreeMap<String, AccessibilityIndexVector>();
    public TreeMap<String, AccessibilityIndexVector> m_set2InformationAccessibleBySubjects = new TreeMap<String, AccessibilityIndexVector>();
    //

    /////////////////////////////////////////////////////////////////////////
    //
    // SETUP functions
    //
    public void addTeam(String teamName) {
        if (m_informationAccessibleByTeams.get(teamName)==null)
            m_informationAccessibleByTeams.put(teamName, new AccessibilityIndexVector());
        if (m_set2InformationAccessibleByTeams.get(teamName)==null)
            m_set2InformationAccessibleByTeams.put(teamName, new AccessibilityIndexVector());
    };
    public void addSubject(String subjectName) {
        if (m_informationAccessibleBySubjects.get(subjectName)==null)
            m_informationAccessibleBySubjects.put(subjectName, new AccessibilityIndexVector());
        if (m_set2InformationAccessibleBySubjects.get(subjectName)==null)
            m_set2InformationAccessibleBySubjects.put(subjectName, new AccessibilityIndexVector());
    };
    public void endTime (double endTime) {
        m_endTime = endTime;
        //mark end in accessibility
        m_accessIndexOverall.setEndTime(endTime);
        m_set2AccessIndexOverall.setEndTime(endTime);
        for (String name : m_informationAccessibleBySubjects.keySet()) {
            m_informationAccessibleBySubjects.get(name).setEndTime(endTime);
            m_set2InformationAccessibleBySubjects.get(name).setEndTime(endTime);
        }
        for (String name : m_informationAccessibleByTeams.keySet()) {
            m_informationAccessibleByTeams.get(name).setEndTime(endTime);
            m_set2InformationAccessibleByTeams.get(name).setEndTime(endTime);
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //
    // DATA ADD (only new data)
    //
    public void add(String subject, String teamName, double time, String factoidMetadata, String source) {
        m_accessIndexOverall.add(new InformationAccessibleData(time, factoidMetadata, source));
        if (subject != null )
            m_informationAccessibleBySubjects.get(subject).add(new InformationAccessibleData(time, factoidMetadata, source));
        if (teamName != null)
            m_informationAccessibleByTeams.get(teamName).add(new InformationAccessibleData(time, factoidMetadata, source));
    };

    public void addIfSharedByTeamAndOverall(ArrayList<Subject> subjectList, String subject, String teamName, double time, String factoidMetadata, String source) {
        //parse all subjects - find intersection
        boolean isSharedInTeam = true;
        boolean isSharedOverall = true;
        for (Subject s : subjectList) {
            //first find within team
            if ( s.m_teamName.equals(teamName) ) {
                AccessibilityIndexVector v = m_informationAccessibleBySubjects.get(s.m_personName);
                if (v.hasFactoid(factoidMetadata)==false) {
                    isSharedInTeam = false;
                    break;
                }
            }
        }
        //now, find in overall
        if (isSharedInTeam) {
            for (AccessibilityIndexVector v : m_informationAccessibleBySubjects.values() ) {
                if (v.hasFactoid(factoidMetadata)==false) {
                    isSharedOverall=false;
                    break;
                }
            }
        }
        else {
            isSharedOverall=false;
        }
        if (isSharedInTeam) {
            m_set2InformationAccessibleByTeams.get(teamName).add(new InformationAccessibleData(time, factoidMetadata, source));
            if (isSharedOverall) {
                m_set2AccessIndexOverall.add(new InformationAccessibleData(time, factoidMetadata, source));
            }
        }
    };

    /////////////////////////////////////////////////////////////////////////
    //
    // DATA ADD (commulative - repeated data is allowed)
    //
    public void addComulativeSet1(String subject, String teamName, double time, String factoidMetadata, String source) {
        m_accessIndexOverall.addComulative(new InformationAccessibleData(time, factoidMetadata, source));
        if (subject != null )
            m_informationAccessibleBySubjects.get(subject).addComulative(new InformationAccessibleData(time, factoidMetadata, source));
        if (teamName != null)
            m_informationAccessibleByTeams.get(teamName).addComulative(new InformationAccessibleData(time, factoidMetadata, source));
    };
    public void addComulativeSet2(String subject, String teamName, double time, String factoidMetadata, String source) {
        m_set2AccessIndexOverall.addComulative(new InformationAccessibleData(time, factoidMetadata, source));
        if (subject != null )
            m_set2InformationAccessibleBySubjects.get(subject).addComulative(new InformationAccessibleData(time, factoidMetadata, source));
        if (teamName != null)
            m_set2InformationAccessibleByTeams.get(teamName).addComulative(new InformationAccessibleData(time, factoidMetadata, source));
    };

    public static int getNbrKEFactoids (AccessibilityIndexVector v, double time) {
        int nKE = 0;
        for (InformationAccessibleData elem : v) {
            if (elem.time > time )
                break;
            nKE = (int)elem.indexKE;
        }
        return nKE;
    }

    public static int getNbrRelevantFactoids (AccessibilityIndexVector v, double time) {
        int nR = 0;
        for (InformationAccessibleData elem : v) {
            if (elem.time > time )
                break;
            nR = (int)elem.indexRelevant;
        }
        return nR;
    }

    public static int getNbrMisinfoFactoids (AccessibilityIndexVector v, double time) {
        int nM = 0;
        for (InformationAccessibleData elem : v) {
            if (elem.time > time )
                break;
            nM = (int)elem.indexMisinfo;
        }
        return nM;
    }

    public static int getNbrAllFactoids (AccessibilityIndexVector v, double time) {
        int nA = 0;
        for (InformationAccessibleData elem : v) {
            if (elem.time > time )
                break;
            nA = (int)elem.indexAll;
        }
        return nA;
    }

    public static int getNbrFactoidsHoarded (AccessibilityIndexVector v, double time) {
        int nA = 0;
        for (InformationAccessibleData elem : v) {
            if (elem.time > time )
                break;
            nA = (int)elem.indexHoarded;
        }
        return nA;
    }

}
