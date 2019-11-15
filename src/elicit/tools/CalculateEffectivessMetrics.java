/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.main;
import elicit.message.IdentifyMessage;
import elicit.message.Message;
import elicit.message.TrialData;
import elicit.message.TrialData.Subject;
import java.util.TreeMap;
import metrics.awareness.IDsQualityMap.AwUndData;

/**
 *
 * @author marcomanso
 */
public class CalculateEffectivessMetrics {

    private static class IDandTime {
        String id;
        double time;
    }

    //small hack:  I use Point2D as a two-dimensional strucutre to store:
    //- x: as effectiveness score
    //- y: as time
    public static java.awt.geom.Point2D.Double effectivenessScoreTeamLeaders(TrialData trialData) {
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        for ( TrialData.Subject s : trialData.m_organizationInformation.m_memberList )
            if ( s.m_isTeamLeader ) {
                //metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(s.m_personName);
                metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(s.m_personName);
                if (data!=null) {
                    if ( s.m_teamName.equals(Message.m_teamWho)) {
                        score.x += data.whoQ;
                        if (score.y < data.time) score.y = data.time;
                    }
                    else if(s.m_teamName.equals(Message.m_teamWhat)) {
                        score.x += data.whatQ;
                        if (score.y < data.time) score.y = data.time;
                    }
                    else if(s.m_teamName.equals(Message.m_teamWhere)) {
                        score.x += data.whereQ;
                        if (score.y < data.time) score.y = data.time;
                    }
                    else if(s.m_teamName.equals(Message.m_teamWhen)) {
                        score.x += data.whenOverallQ;
                        if (score.y < data.time) score.y = data.time;
                    }
                }
            }
        return score;
    }

    public static double effectivenessTeamLeaders(TreeMap<Subject, AwUndData> IDmap) {
        double effectiveness = 0.0;
        for ( Subject s : IDmap.keySet() ) {
            if ( s.m_isTeamLeader ) {
                if ( s.m_teamName.equals(Message.m_teamWho)) {
                    effectiveness += IDmap.get(s).whoQ;
                }
                else if(s.m_teamName.equals(Message.m_teamWhat)) {
                    effectiveness += IDmap.get(s).whatQ;
                }
                else if(s.m_teamName.equals(Message.m_teamWhere)) {
                    effectiveness += IDmap.get(s).whereQ;
                }
                else if(s.m_teamName.equals(Message.m_teamWhen)) {
                    effectiveness += IDmap.get(s).whenOverallQ;
                }
            }
            
        }
        return effectiveness;
    }

    public static java.awt.geom.Point2D.Double effectivenessScoreCoordinator(TrialData trialData) {
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        for ( TrialData.Subject s : trialData.m_organizationInformation.m_memberList )
            if ( s.m_isOverallCoordinator ) {
                //metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(s.m_personName);
                metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(s.m_personName);
                if (data!=null) {
                    score.x += data.whoQ;
                    if (score.y < data.time) score.y = data.time;
                    score.x += data.whatQ;
                    if (score.y < data.time) score.y = data.time;
                    score.x += data.whereQ;
                    if (score.y < data.time) score.y = data.time;
                    score.x += data.whenOverallQ;
                    if (score.y < data.time) score.y = data.time;
                }
                break;
            }
        return score;
    }

    public static double effectivenessCoordinator(TreeMap<Subject, AwUndData> IDmap) {
        double effectiveness = 0.0;
        for ( Subject s : IDmap.keySet() ) {
            if (s.m_isOverallCoordinator) {
                effectiveness += IDmap.get(s).whoQ;
                effectiveness += IDmap.get(s).whatQ;
                effectiveness += IDmap.get(s).whereQ;
                effectiveness += IDmap.get(s).whenOverallQ;
                break;
            }
        }
        return effectiveness;
    }

    public static java.awt.geom.Point2D.Double effectivenessScoreCoordinatorOrTLs(TrialData trialData) {
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        double scoreWHO  =0.0;
        double scoreWHAT =0.0;
        double scoreWHERE=0.0;
        double scoreWHEN =0.0;
        for ( TrialData.Subject s : trialData.m_organizationInformation.m_memberList ) {
            //metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(s.m_personName);
            metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(s.m_personName);
            if (data!=null && s.m_isOverallCoordinator) {
                if (scoreWHO < data.whoQ) {
                    scoreWHO = data.whoQ;
                    if (score.y < data.time) score.y = data.time;
                }
                if (scoreWHAT < data.whatQ) {
                    scoreWHAT = data.whatQ;
                    if (score.y < data.time) score.y = data.time;
                }
                if (scoreWHERE < data.whereQ) {
                    scoreWHERE = data.whereQ;
                    if (score.y < data.time) score.y = data.time;
                }
                if (scoreWHEN < data.whenOverallQ) {
                    scoreWHEN = data.whenOverallQ;
                    if (score.y < data.time) score.y = data.time;
                }
            }
            else if(data!=null && s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWho)) {
                if (scoreWHO < data.whoQ) {
                    scoreWHO = data.whoQ;
                    if (score.y < data.time) score.y = data.time;
                }
            }
            else if(data!=null && s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWhat)) {
                if (scoreWHAT < data.whatQ) {
                    scoreWHAT = data.whatQ;
                    if (score.y < data.time) score.y = data.time;
                }
            }
            else if(data!=null && s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWhere)) {
                if (scoreWHERE < data.whereQ) {
                    scoreWHERE = data.whereQ;
                    if (score.y < data.time) score.y = data.time;
                }
            }
            else if(data!=null && s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWhen)) {
                if (scoreWHEN < data.whenOverallQ) {
                    scoreWHEN = data.whenOverallQ;
                    if (score.y < data.time) score.y = data.time;
                }
            }
        }
        score.x=scoreWHO+scoreWHAT+scoreWHERE+scoreWHEN;
        return score;
    }

    public static double effectivenessCoordinatorOrTLs(TreeMap<Subject, AwUndData> IDmap) {
        double scoreWHO  =0.0;
        double scoreWHAT =0.0;
        double scoreWHERE=0.0;
        double scoreWHEN =0.0;

        double effectiveness = 0.0;
        for ( Subject s : IDmap.keySet() ) {

            if (s.m_isOverallCoordinator) {
                if (scoreWHO < IDmap.get(s).whoQ) {
                    scoreWHO = IDmap.get(s).whoQ;
                }
                if (scoreWHAT < IDmap.get(s).whatQ) {
                    scoreWHAT = IDmap.get(s).whatQ;
                }
                if (scoreWHERE < IDmap.get(s).whereQ) {
                    scoreWHERE = IDmap.get(s).whereQ;
                }
                if (scoreWHEN < IDmap.get(s).whenOverallQ) {
                    scoreWHEN = IDmap.get(s).whenOverallQ;
                }
            }
            else if(s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWho)) {
                if (scoreWHO < IDmap.get(s).whoQ) {
                    scoreWHO = IDmap.get(s).whoQ;
                }
            }
            else if(s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWhat)) {
                if (scoreWHAT < IDmap.get(s).whatQ) {
                    scoreWHAT = IDmap.get(s).whatQ;
                }
            }
            else if(s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWhere)) {
                if (scoreWHERE < IDmap.get(s).whereQ) {
                    scoreWHERE = IDmap.get(s).whereQ;
                }
            }
            else if(s.m_isTeamLeader && s.m_teamName.equals(Message.m_teamWhen)) {
                if (scoreWHEN < IDmap.get(s).whenOverallQ) {
                    scoreWHEN = IDmap.get(s).whenOverallQ;
                }
            }
        }
        effectiveness=scoreWHO+scoreWHAT+scoreWHERE+scoreWHEN;
        return effectiveness;
    }

    public static IDandTime getMostVotedAnws( TreeMap<String, Integer> idMap, TreeMap<String, AwUndData> idMapData ) {
        IDandTime idAndTime = new IDandTime();
        int count = 0;
        for ( String idAnws : idMap.keySet() ) {
            if (count < idMap.get(idAnws).intValue()) {
                count = idMap.get(idAnws).intValue();
                idAndTime.id = idAnws;
                idAndTime.time = idMapData.get(idAnws).time;
            }
        }
        return idAndTime;
    }

    public static java.awt.geom.Point2D.Double effectivenessScoreEDGE(TrialData trialData) {
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        //get last subjects answer
        //WHO
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( String subject : trialData.m_subjectsIDsQualityMap.keySet() ) {
                AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(subject);
                //AwUndData data = trialData.m_subjectsIDsQualityMap.get(subject).lastElement();
                if (data!=null && data.who!=null) {
                    if (id.get(data.who)==null) {
                        id.put(data.who, 1);
                        idData.put(data.who, data);
                    }
                    else {
                        id.put(data.who, id.get(data.who).intValue()+1);
                        if ( data.time > idData.get(data.who).time ) idData.put(data.who, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime!=null && idAndTime.id!=null && idAndTime.id.equals(trialData.m_solution.get(Message.m_teamWho))) {
                score.x+=0.25;
                if (score.y < idAndTime.time) score.y = idAndTime.time;
            }
        }
        //WHAT
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( String subject : trialData.m_subjectsIDsQualityMap.keySet() ) {
                AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(subject);
                //AwUndData data = trialData.m_subjectsIDsQualityMap.get(subject).lastElement();
                if (data!=null && data.what!=null) {
                    if (id.get(data.what)==null) {
                        id.put(data.what, 1);
                        idData.put(data.what, data);
                    }
                    else {
                        id.put(data.what, id.get(data.what).intValue()+1);
                        if ( data.time > idData.get(data.what).time ) idData.put(data.what, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime!=null && idAndTime.id!=null && idAndTime.id.equals(trialData.m_solution.get(Message.m_teamWhat))) {
                score.x+=0.25;
                if (score.y < idAndTime.time) score.y = idAndTime.time;
            }
        }
        //WHERE
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( String subject : trialData.m_subjectsIDsQualityMap.keySet() ) {
                AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(subject);
                //AwUndData data = trialData.m_subjectsIDsQualityMap.get(subject).lastElement();
                if (data!=null && data.where!=null) {
                    if (id.get(data.where)==null) {
                        id.put(data.where, 1);
                        idData.put(data.where, data);
                    }
                    else {
                        id.put(data.where, id.get(data.where).intValue()+1);
                        if ( data.time > idData.get(data.where).time ) idData.put(data.where, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime!=null && idAndTime.id!=null && idAndTime.id.equals(trialData.m_solution.get(Message.m_teamWhere))) {
                score.x+=0.25;
                if (score.y < idAndTime.time) score.y = idAndTime.time;
            }
        }
        //WHEN-time
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( String subject : trialData.m_subjectsIDsQualityMap.keySet() ) {
                AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(subject);
                //AwUndData data = trialData.m_subjectsIDsQualityMap.get(subject).lastElement();
                if (data!=null && data.whenTime!=null) {
                    if (id.get(data.whenTime)==null) {
                        id.put(data.whenTime, 1);
                        idData.put(data.whenTime, data);
                    }
                    else {
                        id.put(data.whenTime, id.get(data.whenTime).intValue()+1);
                        if ( data.time > idData.get(data.whenTime).time ) idData.put(data.whenTime, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime!=null && idAndTime.id!=null && idAndTime.id.equals(elicit.message.SolutionMessage.getTime(trialData.m_solution.get(Message.m_teamWhen)))) {
                score.x+=0.09;
                if (score.y < idAndTime.time) score.y = idAndTime.time;
            }
        }
        //WHEN-day
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( String subject : trialData.m_subjectsIDsQualityMap.keySet() ) {
                AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(subject);
                //AwUndData data = trialData.m_subjectsIDsQualityMap.get(subject).lastElement();
                if (data!=null && data.whenDay!=null) {
                   if (id.get(data.whenDay)==null) {
                        id.put(data.whenDay, 1);
                        idData.put(data.whenDay, data);
                    }
                    else {
                        id.put(data.whenDay, id.get(data.whenDay).intValue()+1);
                        if ( data.time > idData.get(data.whenDay).time ) idData.put(data.whenDay, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime!=null && idAndTime.id!=null && idAndTime.id.equals(elicit.message.SolutionMessage.getDay(trialData.m_solution.get(Message.m_teamWhen)))) {
                score.x+=0.08;
                if (score.y < idAndTime.time) score.y = idAndTime.time;
            }
        }
        //WHEN-month
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( String subject : trialData.m_subjectsIDsQualityMap.keySet() ) {
                AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(subject);
                //AwUndData data = trialData.m_subjectsIDsQualityMap.get(subject).lastElement();
                if (data!=null && data.whenMonth!=null) {
                    if (id.get(data.whenMonth)==null) {
                        id.put(data.whenMonth, 1);
                        idData.put(data.whenMonth, data);
                    }
                    else {
                        id.put(data.whenMonth, id.get(data.whenMonth).intValue()+1);
                        if ( data.time > idData.get(data.whenMonth).time ) idData.put(data.whenMonth, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime!=null && idAndTime.id!=null && idAndTime.id.equals(elicit.message.SolutionMessage.getMonth(trialData.m_solution.get(Message.m_teamWhen)))) {
                score.x+=0.08;
                if (score.y < idAndTime.time) score.y = idAndTime.time;
            }
        }
        //
        return score;
    }


    public static double effectivenessEDGE(TreeMap<String, String> solution, TreeMap<Subject, AwUndData> IDmap) {
        double score = 0.0;
        //get last subjects answer
        //WHO
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( Subject subject : IDmap.keySet() ) {
                AwUndData data = IDmap.get(subject);
                if (data.who!=null) {
                    if (id.get(data.who)==null) {
                        id.put(data.who, 1);
                        idData.put(data.who, data);
                    }
                    else {
                        id.put(data.who, id.get(data.who).intValue()+1);
                        if ( data.time > idData.get(data.who).time ) idData.put(data.who, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime.id!=null && idAndTime.id.equals(solution.get(Message.m_teamWho))) {
                score+=0.25;
            }
        }
        //WHAT
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( Subject subject : IDmap.keySet() ) {
                AwUndData data = IDmap.get(subject);
                if (data.what!=null) {
                    if (id.get(data.what)==null) {
                        id.put(data.what, 1);
                        idData.put(data.what, data);
                    }
                    else {
                        id.put(data.what, id.get(data.what).intValue()+1);
                        if ( data.time > idData.get(data.what).time ) idData.put(data.what, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime.id!=null && idAndTime.id.equals(solution.get(Message.m_teamWhat))) {
                score+=0.25;
            }
        }
        //WHERE
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( Subject subject : IDmap.keySet() ) {
                AwUndData data = IDmap.get(subject);
                if (data.where!=null) {
                    if (id.get(data.where)==null) {
                        id.put(data.where, 1);
                        idData.put(data.where, data);
                    }
                    else {
                        id.put(data.where, id.get(data.where).intValue()+1);
                        if ( data.time > idData.get(data.where).time ) idData.put(data.where, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime.id!=null && idAndTime.id.equals(solution.get(Message.m_teamWhere))) {
                score+=0.25;
            }
        }
        //WHEN-time
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( Subject subject : IDmap.keySet() ) {
                AwUndData data = IDmap.get(subject);
                if (data.whenTime!=null) {
                    if (id.get(data.whenTime)==null) {
                        id.put(data.whenTime, 1);
                        idData.put(data.whenTime, data);
                    }
                    else {
                        id.put(data.whenTime, id.get(data.whenTime).intValue()+1);
                        if ( data.time > idData.get(data.whenTime).time ) idData.put(data.whenTime, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime.id!=null && idAndTime.id.equals(elicit.message.SolutionMessage.getTime(solution.get(Message.m_teamWhen)))) {
                score+=0.09;
            }
        }
        //WHEN-day
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( Subject subject : IDmap.keySet() ) {
                AwUndData data = IDmap.get(subject);
                if (data.whenDay!=null) {
                   if (id.get(data.whenDay)==null) {
                        id.put(data.whenDay, 1);
                        idData.put(data.whenDay, data);
                    }
                    else {
                        id.put(data.whenDay, id.get(data.whenDay).intValue()+1);
                        if ( data.time > idData.get(data.whenDay).time ) idData.put(data.whenDay, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime.id!=null && idAndTime.id.equals(elicit.message.SolutionMessage.getDay(solution.get(Message.m_teamWhen)))) {
                score+=0.08;
            }
        }
        //WHEN-month
        {
            TreeMap<String, Integer> id = new TreeMap<String, Integer>();
            TreeMap<String, AwUndData> idData = new TreeMap<String, AwUndData>();
            for ( Subject subject : IDmap.keySet() ) {
                AwUndData data = IDmap.get(subject);
                if (data.whenMonth!=null) {
                    if (id.get(data.whenMonth)==null) {
                        id.put(data.whenMonth, 1);
                        idData.put(data.whenMonth, data);
                    }
                    else {
                        id.put(data.whenMonth, id.get(data.whenMonth).intValue()+1);
                        if ( data.time > idData.get(data.whenMonth).time ) idData.put(data.whenMonth, data);
                    }
                }
            }
            IDandTime idAndTime = getMostVotedAnws(id, idData);
            if (idAndTime.id!=null && idAndTime.id.equals(elicit.message.SolutionMessage.getMonth(solution.get(Message.m_teamWhen)))) {
                score+=0.08;
            }
        }
        //
        return score;
    }

    public static java.awt.geom.Point2D.Double effectivenessScoreAVGAll(TrialData trialData) {
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        for ( TrialData.Subject s : trialData.m_organizationInformation.m_memberList ) {
            metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastSubjectID(s.m_personName);
            //metrics.awareness.IDsQualityMap.AwUndData data = trialData.m_subjectsIDsQualityMap.GetLastFixedSubjectWhereID(s.m_personName);
            if (data!=null) {
                score.x += data.whoQ+data.whatQ+data.whereQ+data.whenOverallQ;
                if (score.y < data.time) score.y = data.time;
            }
        }
        score.x = score.x / (double)trialData.m_organizationInformation.m_memberList.size();
        return score;
    }


    //small hack:  I use Point2D as a two-dimensional strucutre to store:
    //- x: as effectiveness score
    //- y: as time
    public static java.awt.geom.Point2D.Double effectivenessScore (TrialData trialData) {
        java.awt.geom.Point2D.Double score = null;
        if ( trialData.m_organizationInformation.m_organizationName.equals(Message.m_orgType_CONFLICTED)
             || trialData.m_organizationInformation.m_organizationName.equals(Message.m_orgType_DECONFLICTED) )
        {
            score = effectivenessScoreTeamLeaders(trialData);
        }
        else if ( trialData.m_organizationInformation.m_organizationName.equals(Message.m_orgType_COORDINATED) )
        {
            score = effectivenessScoreCoordinator(trialData);
        }
        else if ( trialData.m_organizationInformation.m_organizationName.equals(Message.m_orgType_COLLABORATIVE) ) {
            score = effectivenessScoreCoordinatorOrTLs(trialData);
        }
        else if ( trialData.m_organizationInformation.m_organizationName.equals(Message.m_orgType_COLLABORATIVE2) ) {
            score = effectivenessScoreCoordinatorOrTLs(trialData);
        }
        else if ( trialData.m_organizationInformation.m_organizationName.equals(Message.m_orgType_EDGE) ) {
            //score = effectivenessScoreAVGAll(trialData);
            score = effectivenessScoreEDGE(trialData);
        }
        return score;
    }

    public static double CalculateMAXTimeliness(TrialData trialData) {
        double time_first_correct = trialData.m_subjectsIDsQualityMap.m_firstAllCorrectID;
        if (time_first_correct==0.0) {
            return 0.0;
        }
        else {
            double duration = trialData.m_trialInformation.m_durationSec;
            return ( 1.0-( time_first_correct / duration ) );
        }
    }

    public static double CalculateMAXTimelinessEffort(TrialData trialData) {
        double nbrActionsToID = trialData.m_subjectsIDsQualityMap.m_nbrActionsToFirstAllCorrectID;
        if (nbrActionsToID==0.0) {
            return -1.0;
        }
        else {
            return ( 10.0 / nbrActionsToID );
        }
    }

    public static double CalculateAvgEfficiency(TrialData trialData) {
        java.awt.geom.Point2D.Double effectiveness_score = effectivenessScore(trialData);
        if (effectiveness_score==null)
        	return 0;
        double nbrActions = CalculateEffortSpent(trialData);
        return ( 1.0-(nbrActions/effectiveness_score.x) );
    }

    public static double CalculateEffortEfficiency(TrialData trialData) {
        java.awt.geom.Point2D.Double effectiveness_score = effectivenessScore(trialData);
        if (effectiveness_score==null)
        	return 0;
        double effort_spent = CalculateEffortSpent(trialData);
        double scale_1000_actions = 1000.0;
        return (effectiveness_score.x * effectiveness_score.x) * Math.log10(1.0+1.0/effort_spent * scale_1000_actions);
    }

    public static double CalculateTimeEfficiency(TrialData trialData) {
        java.awt.geom.Point2D.Double effectiveness_score = effectivenessScore(trialData);
        if (effectiveness_score==null)
        	return 0;
        double scale_1_hour = 3600.0 * trialData.m_trialInformation.m_compression;
        return (effectiveness_score.x * effectiveness_score.x) * Math.log10(1.0+1.0/effectiveness_score.y*scale_1_hour);
    }

    public static double CalculateEffortSpent(TrialData trialData) {
        return (double)trialData.m_overallStatistics.totalIDs
               + trialData.m_overallStatistics.totalShares
               + trialData.m_overallStatistics.totalPosts
               + trialData.m_overallStatistics.totalPulls;
    }

    //////////////
    //UTILS ...
    //

    public static double AVGRelevantInfoAccessed (TrialData trialData) {
        double avg = 0.0;
        for (TrialData.Subject s : trialData.m_organizationInformation.m_memberList) {
            avg += trialData.m_informationQuality.m_reachedIndex.m_informationAccessibleBySubjects.get(s.m_personName).getLastElement().indexRelevant;
        }
        return avg / ((double)trialData.m_organizationInformation.m_memberList.size());
    }

    public static java.awt.geom.Point2D.Double getBestAvgInfoAccessedScore(JFELICITMetricsVisualizer metrics) {
        //SH INFO REACHED
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        int row = metrics.jFMoMAverageInformationAccessedTable.getRowIndexInTable(elicit.tools.JFMoMSharedRelevantInformationAccessedTable.m_overall);
        for ( double scale : elicit.tools.JFMoMSharedRelevantInformationAccessedTable.m_percentagesSteps ) {
            int col = metrics.jFMoMAverageInformationAccessedTable.getColIndexInTable(Double.toString(scale));
            Integer value = (Integer)metrics.jFMoMAverageInformationAccessedTable.getTable().getValueAt(row, col);
            if (value!=null) {
                score.x = scale;
                score.y = (double)(value.intValue());
            }
        }
        return score;
    }

    public static java.awt.geom.Point2D.Double getBestShRInfoAccessedScore(JFELICITMetricsVisualizer metrics) {
        //SH INFO REACHED
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        int row = metrics.jFMoMSharedRelevantInformationAccessedTable.getRowIndexInTable(elicit.tools.JFMoMSharedRelevantInformationAccessedTable.m_overall);
        for ( double scale : elicit.tools.JFMoMSharedRelevantInformationAccessedTable.m_percentagesSteps ) {
            int col = metrics.jFMoMSharedInformationAccessedTable.getColIndexInTable(Double.toString(scale));
            Integer value = (Integer)metrics.jFMoMSharedInformationAccessedTable.getTable().getValueAt(row, col);
            if (value!=null) {
                score.x = scale;
                score.y = (double)(value.intValue());
            }
        }
        return score;
    }

    public static java.awt.geom.Point2D.Double getBestAwShScore(JFELICITMetricsVisualizer metrics) {
        //CORRECT AWAR
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        int row = metrics.jFMoMCorrectSharedUnderstandingTable.getRowIndexInTable(elicit.tools.JFMoMCorrectSharedUnderstanding.m_overall);
        for ( double scale : elicit.tools.JFMoMCorrectSharedUnderstanding.m_percentagesSteps ) {
            int col = metrics.jFMoMCorrectSharedUnderstandingTable.getColIndexInTable(Double.toString(scale));
            Integer value = (Integer)metrics.jFMoMCorrectSharedUnderstandingTable.getTable().getValueAt(row, col);
            if (value!=null) {
                score.x = scale;
                score.y = (double)(value.intValue());
            }
        }
        return score;
    }
    
    public static java.awt.geom.Point2D.Double getBestCCsyncScore(JFELICITMetricsVisualizer metrics) {
        //entropy
        java.awt.geom.Point2D.Double score = new java.awt.geom.Point2D.Double(0.0,0.0);
        int row = metrics.jFMoMCSSync.getRowIndexInTable(elicit.tools.JFMoMCSSync.m_overall);
        for ( double scale : elicit.tools.JFMoMCSSync.m_percentagesSteps ) {
            int col = metrics.jFMoMCSSync.getColIndexInTable(Double.toString(scale));
            Integer value = (Integer)metrics.jFMoMCSSync.getTable().getValueAt(row, col);
            if (value!=null) {
                score.x = scale;
                score.y = (double)(value.intValue());
            }
        }
        return score;
    }

    public static double getEffectivenessScore(String organizationName, TreeMap<String, String> solution, TreeMap<Subject, AwUndData> IDmap) {
        double score = 0.0;
        if ( organizationName.equals(Message.m_orgType_CONFLICTED)
             || organizationName.equals(Message.m_orgType_DECONFLICTED) )
        {
            score = effectivenessTeamLeaders(IDmap);
        }
        else if ( organizationName.equals(Message.m_orgType_COORDINATED) )
        {
            score = effectivenessCoordinator(IDmap);
        }
        else if ( organizationName.equals(Message.m_orgType_COLLABORATIVE) ) {
            score = effectivenessCoordinatorOrTLs(IDmap);
        }
        else if ( organizationName.equals(Message.m_orgType_COLLABORATIVE2) ) {
            score = effectivenessCoordinatorOrTLs(IDmap);
        }
        else if ( organizationName.equals(Message.m_orgType_EDGE) ) {
            //score = effectivenessScoreAVGAll(trialData);
            score = effectivenessEDGE(solution, IDmap);
        }
        if (score>0.0 && score!=1.0) score+=0.000001; //some funny rounding errors... this will add up
        return score;
    }


}
