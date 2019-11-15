/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metrics.informationquality;

import metrics.*;
import elicit.message.TrialData.Subject;
import java.util.ArrayList;

/**
 *
 * @author mmanso
 */
public class InformationQuality {

    // INFORMATION ACCESSIBILITY INDEX

    // NEW INFORMATION
    /**
     * Stores information accessibility for new information that is
     * distributed by server.
     * Shares and posts are not included.
     */
    public InformationAccessible m_accessibilityNewInfomationServer = new InformationAccessible();
    public InformationAccessible m_accessibilityIndex = new InformationAccessible();
    public InformationAccessible m_reachedIndex = new InformationAccessible();
    public InformationAccessible m_formsInteraction = new InformationAccessible();

    //
    public CriticalInformationAccessible m_criticalInformationAccessible = new CriticalInformationAccessible();

    /////////////////////////////////////////////////////////////////////////
    //
    // WRAPPER FUNCTIONS - set same values to both classes
    //
    public void addTeam(String teamName) {
        m_accessibilityNewInfomationServer.addTeam(teamName);
        m_accessibilityIndex.addTeam(teamName);
        m_reachedIndex.addTeam(teamName);
        m_formsInteraction.addTeam(teamName);
    }
    public void addSubject(String subjectName) {
        m_accessibilityNewInfomationServer.addSubject(subjectName);
        m_accessibilityIndex.addSubject(subjectName);
        m_reachedIndex.addSubject(subjectName);
        m_formsInteraction.addSubject(subjectName);
    }
    public void endTime (double endTime) {
        m_accessibilityNewInfomationServer.endTime(endTime);
        m_accessibilityIndex.endTime(endTime);
        m_reachedIndex.endTime(endTime);
        m_formsInteraction.endTime(endTime);
    }
    // add factoid to subject's list of factoids accessible
    /*
    public void add(String subject, String teamName, double time, String factoidMetadata, String source) {
        m_accessibilityNewInfomationServer.add(subject, teamName, time, factoidMetadata, source);
        m_accessibilityIndex.add(subject, teamName, time, factoidMetadata, source);
        m_reachedIndex.add(subject, teamName, time, factoidMetadata, source);
    }
    // add if shared by team and overall
    public void addIfSharedByTeamAndOverall(ArrayList<Subject> subjectList, String subject, String teamName, double time, String factoidMetadata, String source) {
        m_accessibilityNewInfomationServer.addIfSharedByTeamAndOverall(subjectList, subject, teamName, time, factoidMetadata, source);
        m_accessibilityIndex.addIfSharedByTeamAndOverall(subjectList, subject, teamName, time, factoidMetadata, source);
        m_reachedIndex.addIfSharedByTeamAndOverall(subjectList, subject, teamName, time, factoidMetadata, source);
    }
     * */

    /////////////////////////////////////////////////////////////////////////
    //
    // UTILS

    public static InformationAccessible.AccessibilityIndexVector GetInformationBySubject (Subject s, InformationAccessible infoAccessible) {
        return infoAccessible.m_informationAccessibleBySubjects.get(s.m_personName);
    }
    public static InformationAccessible.AccessibilityIndexVector GetInformationByTeam (String team, InformationAccessible infoAccessible) {
        return infoAccessible.m_informationAccessibleByTeams.get(team);
    }

    public static InformationAccessible.AccessibilityIndexVector GetInformation2BySubject (Subject s, InformationAccessible infoAccessible) {
        return infoAccessible.m_set2InformationAccessibleBySubjects.get(s.m_personName);
    }
    public static InformationAccessible.AccessibilityIndexVector GetInformation2ByTeam (String team, InformationAccessible infoAccessible) {
        return infoAccessible.m_set2InformationAccessibleByTeams.get(team);
    }

}
