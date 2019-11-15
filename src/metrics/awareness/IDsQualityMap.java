/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.awareness;

import metrics.awareness.IDsQualityMap.AwUndData;
import elicit.message.Message;
import elicit.message.SolutionMessage;
import elicit.message.IdentifyMessage;
import elicit.message.TrialData.OrganizationInformation;
import java.awt.geom.Point2D;
import java.util.TreeMap;
import java.util.Vector;


/**
 *
 * @author mmanso
 */
public class IDsQualityMap extends TreeMap<String, Vector<AwUndData>> {

    // ALL WEIGTH sum equals 1.0
    //
    public static final double WHO_SOL_WEIGTH        = 0.25;
    public static final double WHAT_SOL_WEIGTH       = 0.25;
    public static final double WHERE_SOL_WEIGTH      = 0.25;
    //when
    public static final double WHEN_SOL_WEIGTH      = 0.25;
    public static final double WHEN_TIME_SOL_WEIGTH  = 0.09;
    public static final double WHEN_DAY_SOL_WEIGTH   = 0.08;
    public static final double WHEN_MONTH_SOL_WEIGTH = 0.08;

    public static final double ADD_TO_ROUND_UP = 0.0000001;

    //key: problem space (who, ...)
    private TreeMap<String, String> m_solution;
    OrganizationInformation m_organizationInformation;

    //maps storing correct ID rates
    //- store as Point2D.Double for convenience (use in charts)
    public Vector<Point2D.Double> m_CorrectIDsWho   = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsWhat  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsWhere = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsWhen  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsWhenTime  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsWhenDay  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsWhenMonth  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_CorrectIDsOverall  = new Vector<Point2D.Double>();

    //maps storing understanding Entropy
    public static final int MIN_SIZE_ID = 4;
    public double[] m_WEIGHT = { 0.25,//WHO
                                 0.25,//WHAT
                                 0.25,//WHERE
                                 0.09,//WHEN-time
                                 0.08,//WHEN-day
                                 0.08,//WHEN-month
                                };
    public Vector[] m_entropy = { new Vector<Point2D.Double>(),//WHO
                                  new Vector<Point2D.Double>(),//WHAT
                                  new Vector<Point2D.Double>(),//WHERE
                                  new Vector<Point2D.Double>(),//WHEN-time
                                  new Vector<Point2D.Double>(),//WHEN-day
                                  new Vector<Point2D.Double>(),//WHEN-month
                                };
    public Vector[] m_uncEntropy = { new Vector<Point2D.Double>(),//WHO
                                  new Vector<Point2D.Double>(),//WHAT
                                  new Vector<Point2D.Double>(),//WHERE
                                  new Vector<Point2D.Double>(),//WHEN-time
                                  new Vector<Point2D.Double>(),//WHEN-day
                                  new Vector<Point2D.Double>(),//WHEN-month
                                };
    public Vector<Point2D.Double> m_entropyOverall  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_uncEntropyOverall  = new Vector<Point2D.Double>();

    /*
    public Vector<Point2D.Double> m_entropyWho   = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_entropyWhat  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_entropyWhere = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_entropyWhenTime = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_entropyWhenDay  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_entropyWhenMonth= new Vector<Point2D.Double>();
    //
    public Vector<Point2D.Double> m_uncEntropyWho   = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_uncEntropyWhat  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_uncEntropyWhere = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_uncEntropyWhenTime = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_uncEntropyWhenDay  = new Vector<Point2D.Double>();
    public Vector<Point2D.Double> m_uncEntropyWhenMonth= new Vector<Point2D.Double>();
     */

    public class AwUndData {
        public double time;
        public String answer;
        //
        public String who;
        public String what;
        public String where;
        public String whenTime;
        public String whenDay;
        public String whenMonth;
        //
        public double whoQ;
        public double whatQ;
        public double whereQ;
        //
        public double whenTimeQ;
        public double whenDayQ;
        public double whenMonthQ;
        //
        public double whenOverallQ;
        public double overallQ;
        
        public String toString() {
        	return "time="+time
        			+ " who="+who+" score="+whoQ
        			+ " what="+what+" score="+whatQ
        			+ " where="+where+" score="+whereQ
        			+ " when-m="+whenMonth+" when-d="+whenDay+" when-time="+whenTime+" score="+whereQ
        			+ " whenOverallQ="+whenOverallQ;
        }
    }

    public double m_AVGCorrectAnswersALLWs = 0.0;
    public double m_firstAllCorrectID = 0.0;
    public int m_nbrActionsToFirstAllCorrectID = 0;
    public String m_subjectNameWithFirstAllCorrectID = null;

    public void setSolution (TreeMap<String, String> solution) {
        m_solution = solution;
    }
    public void setOrganizationInfo (OrganizationInformation organizationInformation) {
        m_organizationInformation = organizationInformation;
    }

    public void addSubject (String subject) {
        if ( get(subject)==null )
            put(subject, new Vector<AwUndData>());
    }

    public int getNbrSubjectsID () {
        int nbr = 0;
        for ( String s : this.keySet() ) {
            if (this.get(s).size()>0)
                nbr++;
        }
        return nbr;
    }

    private void AddSolutionToMap (String sol, TreeMap<String, Integer> map) {
        if (sol!=null)
            if (map.get(sol)==null)
                map.put(sol, 1);
            else
                map.put(sol, map.get(sol)+1);
    }

    public void AddIdData(double time, String subject, AwUndData data) {
        if ( get(subject)==null )
            put(subject, new Vector<AwUndData>());
        get(subject).add(data);
    }

    public void AddID (double time, String subject, String idAnswer) {
        if (get(subject)==null)
            put(subject,new Vector<AwUndData>());
        //
        AwUndData data = new AwUndData();
        data.time = time;
        data.answer   = idAnswer;
        data.who      = IdentifyMessage.getWhoAnswer(idAnswer);
        data.what     = IdentifyMessage.getWhatAnswer(idAnswer);
        data.where    = IdentifyMessage.getWhereAnswer(idAnswer);
        data.whenTime = IdentifyMessage.getWhenTimeAnswer(idAnswer);
        data.whenDay  = IdentifyMessage.getWhenDayAnswer(idAnswer);
        data.whenMonth= IdentifyMessage.getWhenMonthAnswer(idAnswer);
        //
        AddIdData(time, subject, data);
        //
        TreeMap<String, Integer> solutionMap = new TreeMap<String, Integer>();
        Point2D.Double value;
        double valueOverall = 0.0;
        double valueOverallUnc = 0.0;
        //WHO
        for ( String name : this.keySet() ) {
            AwUndData idData = GetLastFixedSubjectWhoID(name);
            if (idData!=null)
                AddToIntMap(idData.who, solutionMap);
        }
        int index = 0;
        value = GetCSSync(solutionMap);
        if (value != null) {
            m_entropy[index].add(new Point2D.Double(data.time, value.x));
            m_uncEntropy[index].add(new Point2D.Double(data.time, value.y));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            valueOverall += m_WEIGHT[index]*value.x;
            valueOverallUnc += m_WEIGHT[index]*value.y;
        }
        //WHAT
        solutionMap.clear();
        for ( String name : this.keySet() ) {
            AwUndData idData = GetLastFixedSubjectWhatID(name);
            if (idData!=null)
                AddToIntMap(idData.what, solutionMap);
        }
        index++;
        value = GetCSSync(solutionMap);
        if (value != null) {
            m_entropy[index].add(new Point2D.Double(data.time, value.x));
            m_uncEntropy[index].add(new Point2D.Double(data.time, value.y));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            valueOverall += m_WEIGHT[index]*value.x;
            valueOverallUnc += m_WEIGHT[index]*value.y;
        }
        //WHERE
        solutionMap.clear();
        for ( String name : this.keySet() ) {
            AwUndData idData = GetLastFixedSubjectWhereID(name);
            if (idData!=null)
                AddToIntMap(idData.where, solutionMap);
        }
        index++;
        value = GetCSSync(solutionMap);
        if (value != null) {
            m_entropy[index].add(new Point2D.Double(data.time, value.x));
            m_uncEntropy[index].add(new Point2D.Double(data.time, value.y));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            valueOverall += m_WEIGHT[index]*value.x;
            valueOverallUnc += m_WEIGHT[index]*value.y;
        }
        //WHEN (time)
        solutionMap.clear();
        for ( String name : this.keySet() ) {
            AwUndData idData = GetLastFixedSubjectWhenID(name);
            if (idData!=null && idData.whenTime!=null)
                AddToIntMap(idData.whenTime, solutionMap);

        }
        index++;
        value = GetCSSync(solutionMap);
        if (value != null) {
            m_entropy[index].add(new Point2D.Double(data.time, value.x));
            m_uncEntropy[index].add(new Point2D.Double(data.time, value.y));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            valueOverall += m_WEIGHT[index]*value.x;
            valueOverallUnc += m_WEIGHT[index]*value.y;
        }
        //WHEN (day)
        solutionMap.clear();
        for ( String name : this.keySet() ) {
            AwUndData idData = GetLastFixedSubjectWhenID(name);
            if (idData!=null && idData.whenDay!=null)
                AddToIntMap(idData.whenDay, solutionMap);

        }
        index++;
        value = GetCSSync(solutionMap);
        if (value != null) {
            m_entropy[index].add(new Point2D.Double(data.time, value.x));
            m_uncEntropy[index].add(new Point2D.Double(data.time, value.y));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            valueOverall += m_WEIGHT[index]*value.x;
            valueOverallUnc += m_WEIGHT[index]*value.y;
        }
        //WHEN (month)
        solutionMap.clear();
        for ( String name : this.keySet() ) {
            AwUndData idData = GetLastFixedSubjectWhenID(name);
            if (idData!=null && idData.whenMonth!=null)
                AddToIntMap(idData.whenMonth, solutionMap);
        }
        //
        index++;
        value = GetCSSync(solutionMap);
        if (value != null) {
            m_entropy[index].add(new Point2D.Double(data.time, value.x));
            m_uncEntropy[index].add(new Point2D.Double(data.time, value.y));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            //m_entropyWho.add(new Point2D.Double(data.time, value));
            valueOverall += m_WEIGHT[index]*value.x;
            valueOverallUnc += m_WEIGHT[index]*value.y;
        }
        valueOverall = ((double)((int)((valueOverall+0.005)*100)))/100.0; //round up
        //
        m_entropyOverall.add(new Point2D.Double(data.time, valueOverall));
        m_uncEntropyOverall.add(new Point2D.Double(data.time, valueOverallUnc));
    }

    private double CalculateQID (String subjID, String answID, double weight) {
        if (subjID!=null && subjID.equalsIgnoreCase(answID))
            return weight;
        return 0.0;
    }

    private double CalculateQIDWhen (AwUndData data) {
        double weight = 0.0;
        if ( data.whenTime!=null && data.whenTime.equalsIgnoreCase(SolutionMessage.getTime(m_solution.get(Message.m_teamWhen))) )
            weight+=WHEN_TIME_SOL_WEIGTH;
        if ( data.whenDay!=null && data.whenDay.equalsIgnoreCase(SolutionMessage.getDay(m_solution.get(Message.m_teamWhen))) )
            weight+=WHEN_DAY_SOL_WEIGTH;
        if ( data.whenMonth!=null && data.whenMonth.equalsIgnoreCase(SolutionMessage.getMonth(m_solution.get(Message.m_teamWhen))) )
            weight+=WHEN_MONTH_SOL_WEIGTH;
        return weight;
    }

    public void CalculateOverallQ(String sbjName, AwUndData data) {
        //Subject s = m_organizationInformation.getSubject(sbjName);
        data.overallQ = (data.whoQ + data.whatQ + data.whereQ + data.whenOverallQ);
    }

    public double CalculateAVGCorrectAnswerAllW () {
        double value = 0.0;
        for (String s : keySet()) {
            AwUndData data = GetLastSubjectID(s);
            if (data!=null && data.overallQ == 1.0) {
                value+=1.0;
            }
        }
        value = value / size();
        return value;
    }

    public void CalculateAll () {
        for (String s : keySet()) {
            for ( AwUndData e : get(s) ) {
                e.whoQ   = CalculateQID(e.who,   m_solution.get(Message.m_teamWho), WHO_SOL_WEIGTH);
                e.whatQ  = CalculateQID(e.what,  m_solution.get(Message.m_teamWhat), WHAT_SOL_WEIGTH);
                e.whereQ = CalculateQID(e.where, m_solution.get(Message.m_teamWhere), WHERE_SOL_WEIGTH);
                e.whenTimeQ  = CalculateQID(e.whenTime, SolutionMessage.getTime(m_solution.get(Message.m_teamWhen)), WHEN_TIME_SOL_WEIGTH);
                e.whenDayQ   = CalculateQID(e.whenDay,    SolutionMessage.getDay(m_solution.get(Message.m_teamWhen)), WHEN_DAY_SOL_WEIGTH);
                e.whenMonthQ = CalculateQID(e.whenMonth,    SolutionMessage.getMonth(m_solution.get(Message.m_teamWhen)), WHEN_MONTH_SOL_WEIGTH);
                e.whenOverallQ = CalculateQIDWhen(e);
                CalculateOverallQ(s, e);
                //
                if (e.overallQ == 1
                    && (m_firstAllCorrectID == 0.0 || e.time < m_firstAllCorrectID))
                {
                        m_firstAllCorrectID = e.time;
                        m_subjectNameWithFirstAllCorrectID = s;
                }

            }
        }
        //
        CalculateCorrectIDsMap();
        m_AVGCorrectAnswersALLWs = CalculateAVGCorrectAnswerAllW();
    }

    public AwUndData GetLastFixedSubjectWhoID(String name) {
        AwUndData data = null;
        if ( get(name)!=null ) {
            for ( AwUndData d : get(name) )
                if (d.who==null)
                    data = null; //reset to no-answ
                else if (data==null)
                    data = d;    //always accept non-empty when null
                else if ( !data.who.equalsIgnoreCase(d.who) )
                    data = d; // if different - store last
        }
        return data;
    }

    public AwUndData GetLastFixedSubjectWhatID(String name) {
        AwUndData data = null;
        if ( get(name)!=null ) {
            for ( AwUndData d : get(name) )
                if (d.what==null)
                    data = null; //reset to no-answ
                else if (data==null)
                    data = d;    //always accept non-empty when null
                else if ( !data.what.equalsIgnoreCase(d.what) )
                    data = d; // if different - store last
        }
        return data;
    }

    public AwUndData GetLastFixedSubjectWhereID(String name) {
        AwUndData data = null;
        if ( get(name)!=null ) {
            for ( AwUndData d : get(name) )
                if (d.where==null)
                    data = null; //reset to no-answ
                else if (data==null)
                    data = d;    //always accept non-empty when null
                else if ( !data.where.equalsIgnoreCase(d.where) )
                    data = d; // if different - store last
        }
        return data;
    }

    public boolean areTheTwoTheSame(String str1, String str2) {
    	boolean areTheSame = true;
    	if (str1 == null && str2 != null) {
    		areTheSame = false;
    	}
    	else if (str1 != null && str2 == null) {
    		areTheSame = false;
    	}
    	else if (!str1.equalsIgnoreCase(str2)) {
    		areTheSame = false;
    	}
    	return areTheSame;
    }
    public AwUndData GetLastFixedSubjectWhenID(String name) {
        AwUndData data = null;
        if ( get(name)!=null ) {
            for ( AwUndData d : get(name) )
                if (data==null) {
                    if ( d.whenTime!=null || d.whenDay!=null || d.whenMonth!=null )
                        data=d;  //always accept non-empty when null
                }
                else if (d.whenTime==null && d.whenDay==null && d.whenMonth==null)
                    data=null;  //reset to no-answ

            	//at this point, data is not null and at least 1 when is also not null
                else
                {                	
                    //must check for changes
                	//System.out.println("GetLastFixedSubjectWhenID - d	: "+d);
                	//System.out.println("--< GetLastFixedSubjectWhenID - data: "+data);

                    //WHEN - TIME
                	if (d.whenTime!=null && areTheTwoTheSame(data.whenTime, d.whenTime)==false ) 
                    	data=d;                		
                	else if (d.whenDay!=null && areTheTwoTheSame(data.whenDay, d.whenDay)==false)
                       	data=d;                		
                	else if (d.whenMonth!=null && areTheTwoTheSame(data.whenMonth, d.whenMonth)==false)
                    	data=d;                		

                	//System.out.println("--> GetLastFixedSubjectWhenID - data: "+data);
                    
                }
        }
        return data;
    }

    public AwUndData GetLastSubjectID(String name) {
        AwUndData data = null;
        if ( get(name)!=null && get(name).size()!=0 ) {
            data = get(name).elementAt( get(name).size()-1 );
        }
        return data;
    }

    public AwUndData GetLastSubjectIDAtTime(String name, double time) {
        AwUndData data = null;
        if ( get(name)!=null ) {
            for ( AwUndData d : get(name) )
                if (d.time<=time)
                    data = d;
        }
        return data;
    }

    //
    public TreeMap<String, Integer> GetWhoIDMap() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        for (String subj : keySet()) {
            AwUndData data = GetLastSubjectID(subj);
            if (data!=null)
                AddSolutionToMap(GetLastSubjectID(subj).who, map);
        }
        return map;
    }
    public TreeMap<String, Integer> GetWhatIDMap() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        for (String subj : keySet()) {
            AwUndData data = GetLastSubjectID(subj);
            if (data!=null)
                AddSolutionToMap(GetLastSubjectID(subj).what, map);
        }
        return map;
    }
    public TreeMap<String, Integer> GetWhereIDMap() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        for (String subj : keySet()) {
            AwUndData data = GetLastSubjectID(subj);
            if (data!=null)
                AddSolutionToMap(GetLastSubjectID(subj).where, map);
        }
        return map;
    }
    public TreeMap<String, Integer> GetWhenTimeIDMap() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        for (String subj : keySet()) {
            AwUndData data = GetLastSubjectID(subj);
            if (data!=null)
                AddSolutionToMap(GetLastSubjectID(subj).whenTime, map);
        }
        return map;
    }
    public TreeMap<String, Integer> GetWhenDayIDMap() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        for (String subj : keySet()) {
            AwUndData data = GetLastSubjectID(subj);
            if (data!=null)
                AddSolutionToMap(GetLastSubjectID(subj).whenDay, map);
        }
        return map;
    }
    public TreeMap<String, Integer> GetWhenMonthIDMap() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        for (String subj : keySet()) {
            AwUndData data = GetLastSubjectID(subj);
            if (data!=null)
                AddSolutionToMap(GetLastSubjectID(subj).whenMonth, map);
        }
        return map;
    }

    public static void AddToIntMap (String s, TreeMap<String, Integer> map) {
        if (map.get(s)==null) {
            map.put(s,1);
        }
        else {
            map.put(s,map.get(s)+1);
        }
    }

    ///////////////////////////////////////////////////////
    //
    // UTIL function to create map of correct IDs per solution space

    private void CalculateCorrectIDsMap() {

        double TIME_INCREMENT = 1.0;

        //when was the last ID performed?
        double endTime = 0.0;
        for (String s : this.keySet()) {
            if ( GetLastSubjectID(s) != null ) {
                double sbjTime = GetLastSubjectID(s).time;
                if (sbjTime > endTime)
                    endTime = sbjTime;
            }
        }

        double old_nbrCorrectWHO  =-1.0;
        double old_nbrCorrectWHAT =-1.0;
        double old_nbrCorrectWHERE=-1.0;
        double old_nbrCorrectWHEN =-1.0;
        double old_nbrCorrectWHENtime =-1.0;
        double old_nbrCorrectWHENday =-1.0;
        double old_nbrCorrectWHENmonth =-1.0;
        double old_overall=0.0;
        for ( double time = 0.0; time < endTime + TIME_INCREMENT ; time+=TIME_INCREMENT ) {
            double nbrCorrectWHO=0.0;
            double nbrCorrectWHAT=0.0;
            double nbrCorrectWHERE=0.0;
            double nbrCorrectWHEN=0.0;
            //
            double nbrCorrectWHENtime=0.0;
            double nbrCorrectWHENday=0.0;
            double nbrCorrectWHENmonth=0.0;
            //
            double overall=0.0;
            //parse all subjects
            for (String s : this.keySet()) {
                //
                AwUndData data = this.GetLastSubjectIDAtTime(s, time);
                if (data!=null) {
                   if (data.whoQ>0) nbrCorrectWHO++;
                   if (data.whatQ>0) nbrCorrectWHAT++;
                   if (data.whereQ>0) nbrCorrectWHERE++;
                   //
                   if (data.whenTimeQ>0) nbrCorrectWHENtime++;
                   if (data.whenDayQ>0) nbrCorrectWHENday++;
                   if (data.whenMonthQ>0) nbrCorrectWHENmonth++;
                   //strick assessing time:  only consider if all WHEN are correct
                   if (data.whenTimeQ>0 && data.whenDayQ>0 && data.whenMonthQ>0)
                     nbrCorrectWHEN++;

                }
            }//for sbjs
            //nbrCorrectWHEN = (nbrCorrectWHENtime+nbrCorrectWHENday+nbrCorrectWHENmonth)/3.0;
            overall =  WHO_SOL_WEIGTH*nbrCorrectWHO
                       + WHAT_SOL_WEIGTH*nbrCorrectWHAT
                       + WHERE_SOL_WEIGTH*nbrCorrectWHERE
                       + WHEN_SOL_WEIGTH*nbrCorrectWHEN;
            //
            if ( old_nbrCorrectWHO == -1.0 || old_nbrCorrectWHO!=nbrCorrectWHO || time >= endTime)
                m_CorrectIDsWho.add(new Point2D.Double(time, nbrCorrectWHO));
            if ( old_nbrCorrectWHAT == -1.0 || old_nbrCorrectWHAT!=nbrCorrectWHAT || time >= endTime)
                m_CorrectIDsWhat.add(new Point2D.Double(time, nbrCorrectWHAT));
            if ( old_nbrCorrectWHERE == -1.0 || old_nbrCorrectWHERE!=nbrCorrectWHERE || time >= endTime)
                m_CorrectIDsWhere.add(new Point2D.Double(time, nbrCorrectWHERE));
            if ( old_nbrCorrectWHEN == -1.0 || old_nbrCorrectWHEN!=nbrCorrectWHEN || time >= endTime)
                m_CorrectIDsWhen.add(new Point2D.Double(time, nbrCorrectWHEN));
            //
            if ( old_nbrCorrectWHENtime == -1.0 || old_nbrCorrectWHENtime!=nbrCorrectWHENtime || time >= endTime)
                m_CorrectIDsWhenTime.add(new Point2D.Double(time, nbrCorrectWHENtime));
            if ( old_nbrCorrectWHENday == -1.0 || old_nbrCorrectWHENday!=nbrCorrectWHENday || time >= endTime)
                m_CorrectIDsWhenDay.add(new Point2D.Double(time, nbrCorrectWHENday));
            if ( old_nbrCorrectWHENmonth == -1.0 || old_nbrCorrectWHENmonth!=nbrCorrectWHENmonth || time >= endTime)
                m_CorrectIDsWhenMonth.add(new Point2D.Double(time, nbrCorrectWHENmonth));
            //
            if (old_overall == -1.0 || old_overall != overall || time >= endTime)
                m_CorrectIDsOverall.add(new Point2D.Double(time, overall));
            //
            old_nbrCorrectWHO  =nbrCorrectWHO;
            old_nbrCorrectWHAT =nbrCorrectWHAT;
            old_nbrCorrectWHERE=nbrCorrectWHERE;
            old_nbrCorrectWHEN =nbrCorrectWHEN;
            old_overall = overall;

        }//for time

    }

    // stores in:
    //    X - CSSync
    //    Y - uncertainty parcel (normalized 0-1)
    public Point2D.Double GetCSSync(TreeMap<String, Integer> solutionMap) {
        Point2D.Double value = new Point2D.Double();
        value.x=0.0;
        value.y=0.0;
        int size = 0;
        double nbrSbjs = this.keySet().size();
        //calculate MAX disorder
        double max_disorder = - java.lang.Math.log( nbrSbjs );

        //calculate entropy
        for ( String s : solutionMap.keySet() )
            size += solutionMap.get(s) ;
        //total disorder:
        //  ln 1/17
        // total order:  size=17, p=1 , v = 0
        //
        if (size<nbrSbjs) {
            //those that don't ID: assume total disorder
            double p = 1.0/nbrSbjs;
            value.y += (nbrSbjs-size) * p * java.lang.Math.log( p );
        }
        value.x = value.y;
        if (size>0)
            for ( String s : solutionMap.keySet() ) {
                double p = ((double)solutionMap.get(s))/(double)nbrSbjs;
                value.x += p * java.lang.Math.log( p );
            }
        //normalize and scale
        value.x = 1.0 - value.x / max_disorder;
        value.y = value.y / max_disorder;
        return value;
    }

}
