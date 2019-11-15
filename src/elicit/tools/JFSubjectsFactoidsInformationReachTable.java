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
public class JFSubjectsFactoidsInformationReachTable extends JFSubjectsFactoidsTable {

    public JFSubjectsFactoidsInformationReachTable(TrialData trialData) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());
    }

    @Override
    public void SetTitle ()
    {
        this.setTitle("Information Reach");
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each subject
        int sCount=0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
        {
            FillSubjectsFactoidsRelevance(s.m_personName, InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_accessibilityIndex), sCount);
            sCount++; //next subject
        }
    }

}
