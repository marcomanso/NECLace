/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.*;
import metrics.informationquality.InformationAccessible;
import metrics.informationquality.InformationQuality;

/**
 *
 * @author mmanso
 */
public class JFSubjectsFactoidsSharesAndPostsTable extends JFSubjectsFactoidsTable {

    public final static int OPTION_SHOW_SHARES = 0;
    public final static int OPTION_SHOW_POSTS = 1;
    public final static int OPTION_SHOW_SHARES_AND_POSTS = 2;
    int m_option = OPTION_SHOW_SHARES;

    public JFSubjectsFactoidsSharesAndPostsTable(TrialData trialData, int option) {
        super(trialData);
        //jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColloringFactoidHoarded());

        //HACK TABLE....
        m_option = option;
        SetTitle();
        SetTableModel();
        //
        CreateFactoidTable();
        FillTableInformationAtTime(GetSelectedTime());

    }

    @Override
    public void SetTitle ()
    {
       if (m_option==OPTION_SHOW_SHARES_AND_POSTS)
            this.setTitle("Shares and Posts");
        else if (m_option==OPTION_SHOW_SHARES)
            this.setTitle("Shares");
        else //(m_option==OPTION_SHOW_POSTS)
            this.setTitle("Posts");
    }

    @Override
    public void SetTableModel () {
        jtFactoids.setDefaultRenderer(String.class, new elicit.utils.tables.CellColoringQuantityOfFactoids());
    }

    @Override
    public void FillTableInformationAtTime (int time) {
        //parse each subject
        int sCount=0;
        for (TrialData.Subject s : m_trialData.m_organizationInformation.m_memberList)
        {
            InformationAccessible.AccessibilityIndexVector v = null;
            InformationAccessible.AccessibilityIndexVector v2 = null;
            if (m_option == OPTION_SHOW_SHARES) {
                v = InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_formsInteraction);
            }
            else if (m_option == OPTION_SHOW_POSTS) {
                v = InformationQuality.GetInformation2BySubject(s, m_trialData.m_informationQuality.m_formsInteraction);
            }
            else if (m_option == OPTION_SHOW_SHARES_AND_POSTS) {
                v = InformationQuality.GetInformationBySubject(s, m_trialData.m_informationQuality.m_formsInteraction);
                v2 = InformationQuality.GetInformation2BySubject(s, m_trialData.m_informationQuality.m_formsInteraction);
            }
            //clean previous factoids data
            for ( int factnbr=1; factnbr<=m_trialData.m_factoidStats.totalFactoids ; factnbr++ )
            {
                jtFactoids.setValueAt("", sCount,  factnbr);
                int count = 0;
                for (InformationAccessible.InformationAccessibleData data : v) {
                    try {
                        if ( data.time <= GetSelectedTime ()
                             && !data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY)
                             && FactoidMessage.GetFactoidNumber(data.factoidMetadata)==factnbr )
                                count++;
                    }
                    catch (Exception ex) {
                        System.out.println("..Error in factoid: "+data.factoidMetadata);
                        System.out.println("..Exception: "+ex.getMessage());
                    }
                }
                //
                if (m_option == OPTION_SHOW_SHARES_AND_POSTS) {
                    for (InformationAccessible.InformationAccessibleData data : v2)
                        try {
                            if ( data.time <= GetSelectedTime ()
                                    && !data.factoidMetadata.equals(Message.MESSAGE_TYPE_DUMMY)
                                    && FactoidMessage.GetFactoidNumber(data.factoidMetadata)==factnbr )
                                    count++;
                        }
                        catch (Exception ex) {
                            System.out.println("..Error in factoid: "+data.factoidMetadata);
                            System.out.println("..Exception: "+ex.getMessage());
                        }
                }
                if (count!=0)
                    jtFactoids.setValueAt(count, sCount,  factnbr);
                    //jtFactoids.setValueAt(Integer.toString(count), sCount,  factnbr);
            }//end for
            sCount++; //next subject
        }
    }


}
