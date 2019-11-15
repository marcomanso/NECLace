/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.TrialData;
import metrics.informationquality.InformationQuality;

/**
 *
 * @author mmanso
 */
public class JFMoMSharedInformationAccessedTable extends JFMoMSharedInformationAccessibleTable {

    public JFMoMSharedInformationAccessedTable(TrialData trialData) {
        super(trialData);
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("MoM: Information Reached (percentage over time)");
    }


    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each subject
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
            FillData(s.m_personName, InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_reachedIndex));
        //teams
        for (String team : m_trialData.m_organizationInformation.m_teamList)
            FillData(team, InformationQuality.GetInformation2ByTeam(team, m_trialData.m_informationQuality.m_reachedIndex));
        //overall
        FillData(m_overall, m_trialData.m_informationQuality.m_reachedIndex.m_set2AccessIndexOverall);
    }

}
