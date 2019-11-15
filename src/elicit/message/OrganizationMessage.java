/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.message;

import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 *
 * @author mmanso
 */
public class OrganizationMessage extends Message {

    @Override
    public void Read(String logLine) {
    	
		m_organizationId = new OrganizationId();

		m_logLine = logLine.trim();
        StringTokenizer st = new StringTokenizer(logLine);
        fillDateTimeSeqMsgType(st);
        m_data = st.nextToken();
		m_organizationId.m_index = m_data;
        //
    	//hack: next is VERY UGLY (as the log is very inconsistent). 
		// - only process if is digit
		//if (m_data.charAt(0) != m_ignoreOrganisation && Character.isDigit(m_data.charAt(0)) ) {
        if ( Character.isDigit(m_data.charAt(0)) ) {
            //get organisation details
    		m_organizationId.m_role = st.nextToken()+" "+st.nextToken();

    		m_organizationId.m_team = st.nextToken().toUpperCase();
    		m_organizationId.m_teamName = st.nextToken().toUpperCase();
    		m_organizationId.m_orgMembersAccess = st.nextToken();
    		
    		//if we enter here.... something went wrong... 
    		if (!isAccessPrivilege(m_organizationId.m_orgMembersAccess)) {
        		//ugly hack: need to find a digit that may indicate the team
        		//however, it so happens that sometimes the role is also put with a digit ... mess
        		//... and if team and team name are both digits... perhaps digit needs to be put in role..
    			
        		if ( Character.isDigit(m_organizationId.m_teamName.charAt(0)) 
        				&& Character.isDigit(m_organizationId.m_team.charAt(0)) ) {
        			m_organizationId.m_role += " "+m_organizationId.m_team;
        			m_organizationId.m_team = " "+m_organizationId.m_teamName;
        			m_organizationId.m_teamName = m_organizationId.m_orgMembersAccess;        			
        		}
        		else if ( Character.isDigit(m_organizationId.m_teamName.charAt(0)) ) {
        			m_organizationId.m_team += " "+m_organizationId.m_teamName;
        			m_organizationId.m_teamName = m_organizationId.m_orgMembersAccess;
        		}
        		else {
        			m_organizationId.m_teamName += " "+m_organizationId.m_orgMembersAccess;        			
        		}
        		m_organizationId.m_orgMembersAccess = st.nextToken();
        		while (!isAccessPrivilege(m_organizationId.m_orgMembersAccess)) {
        			m_organizationId.m_teamName += " " + m_organizationId.m_orgMembersAccess;        			
            		m_organizationId.m_orgMembersAccess = st.nextToken();
        		}
    		}
    		m_organizationId.m_sitesAccess = st.nextToken();
        }
        else {
        	//might be organisation name and names of teams
        	m_organizationName = m_data;        	
        }
    }

    @Override
    public String Write() {
        return m_messageType
               +" \t"+m_commonData.date
               +" \t"+m_commonData.time
               +" \t"+m_commonData.sequence
               +" \t"+m_organizationId.m_index
               +" \trole="+m_organizationId.m_role
               +" \tteam="+m_organizationId.m_team
               +" \tteam_name="+m_organizationId.m_teamName
        	   +" \taccess="+m_organizationId.m_orgMembersAccess
        	   +" \tsite_access="+m_organizationId.m_sitesAccess;
    }

    public static void GetWebsiteNames (String logLine, java.util.ArrayList<String> websites) {
        logLine = logLine.trim();
        StringTokenizer st = new StringTokenizer(logLine);
        parseNWords(st, 6);
        websites.add(st.nextToken().toUpperCase());
        websites.add(st.nextToken().toUpperCase());
        websites.add(st.nextToken().toUpperCase());
        websites.add(st.nextToken().toUpperCase());
    }

    static void FillWebSiteAccess(Message m, TreeMap<String, TreeMap<String, boolean[]>> websiteAccessPerRoleAndTeam) {
        //TreeMap<String, TreeMap<String, WebSiteAccess>> m_websiteAccessPerRoleAndTeam
        String role ="";

            //webaccess.index = Integer.valueOf(st.nextToken());
        //get role
        if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionIsolatedCoordinator)!=-1) {
            //
            role = Message.m_teamPositionIsolatedCoordinator;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionInformationBroker)!=-1) {
            role = Message.m_teamPositionInformationBroker;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionCrossTeamCoordinator)!=-1) {
            role = Message.m_teamPositionCrossTeamCoordinator;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionCrossTeamCoordinator_v01)!=-1) {
            role = Message.m_teamPositionCrossTeamCoordinator_v01;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionFacilitator)!=-1) {
            role = Message.m_teamPositionFacilitator;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionTeamLeader)!=-1) {
            role = Message.m_teamPositionTeamLeader;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionTeamMember)!=-1) {
            role = Message.m_teamPositionTeamMember;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionTM)!=-1) {
            role = Message.m_teamPositionTeamMember;
        }
        else if (m.m_organizationId.m_role.indexOf(Message.m_teamPositionLeader)!=-1) {
            role = Message.m_teamPositionTeamLeader;
        }
        else {
            role = Message.m_teamPositionInvalid;
        }
        if (!role.equals(Message.m_teamPositionInvalid)) {
            //
            if (websiteAccessPerRoleAndTeam.get(role) == null) {
                websiteAccessPerRoleAndTeam.put(role, new TreeMap<String, boolean[]>());
            }
            //
            String team = m.m_organizationId.m_team;
            //
            if (websiteAccessPerRoleAndTeam.get(role).get(team) == null) {
                websiteAccessPerRoleAndTeam.get(role).put(team, new boolean[4]);
            }
            boolean[] access = websiteAccessPerRoleAndTeam.get(role).get(team);
            //for (int i=0; i<websiteAccessPerRoleAndTeam.get(role).get(team).length; i++) {
            for (int i=0; i<access.length; i++) {
                if (m.m_organizationId.m_sitesAccess.charAt(i)=='1')
                    access[i]=true;
                else
                    access[i]=false;
            }
        }
    }

    public static boolean isEdge( String orgName ) {

    	return true;
    	
    	/* TODO : HACK -> set to EDGE (ie, no structure)
    	if (orgName.equalsIgnoreCase(Message.m_orgType_EDGE)
            || orgName.equalsIgnoreCase(Message.m_orgTypeEdge[0])
            || orgName.equalsIgnoreCase(Message.m_orgTypeEdge[1]) )
        {
            return true;
        }
        else {
            return false;
        }
        */
    }

    public static boolean isAccessPrivilege(String data) {
    	if ( data.length()<4 || !Character.isDigit(data.charAt(0)) || !Character.isDigit(data.charAt(1)) ) {
			return false;
    		
    	}
		else {
			return true;
		}
    }
    
    public static double getLevelNumber(String orgName) {
        if (orgName.equalsIgnoreCase(Message.m_orgType_EDGE)) return 5;
        else if(orgName.equalsIgnoreCase(Message.m_orgType_COLLABORATIVE2)) return 4.5;
        else if(orgName.equalsIgnoreCase(Message.m_orgType_COLLABORATIVE)) return 4;
        else if(orgName.equalsIgnoreCase(Message.m_orgType_COORDINATED)) return 3;
        else if(orgName.equalsIgnoreCase(Message.m_orgType_DECONFLICTED)) return 2;
        else return 1;//if(orgName.equalsIgnoreCase(Message.m_orgType_EDGE) return 5;
    }

}
