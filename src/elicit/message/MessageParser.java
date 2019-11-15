/*
 * MessageParser.java
 *
 * Created on 28 June 2007, 00:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

import org.jfree.util.Log;

/**
 *
 * @author Marco
 */
public class MessageParser {

    public static Message getMessage(String logLine) {

        Message message = null;

        //an exception may be raised:  in that case, return null (and leave output message)
        try {
            String type = Message.getMessageType(logLine);

            //hack - change type from admin to solution?
            if (type.equals(Message.MESSAGE_TYPE_ADMIN) && AdminMessage.isTrialAnswer(logLine)) {
                type = Message.MESSAGE_TYPE_SOLUTION;
            }
            
            //
            if (type.equals(Message.MESSAGE_TYPE_ADMIN)) {
                //admin
                message = new AdminMessage();
                message.m_messageType = Message.MESSAGE_TYPE_ADMIN;
            }
            else if (type.equals(Message.MESSAGE_TYPE_ASSIGN)) {
                //"assign",
                message = new AssignMessage();
                message.m_messageType = Message.MESSAGE_TYPE_ASSIGN;
            }
            else if (type.equals(Message.MESSAGE_TYPE_METADATA)) {
                if ( RunnameMessage.isRunnameMessageInMetadata(logLine) ) {
                    message = new RunnameMessage();
                    message.m_messageType = Message.MESSAGE_TYPE_RUNNAME;
                }
            }
            else if(type.equals(Message.MESSAGE_TYPE_BATCH)) {
                if (CompressionMessage.isCompressionMessage(logLine)) {
                    message = new CompressionMessage();
                    message.m_messageType = Message.MESSAGE_TYPE_BATCH;
                }
                //else if (RunnameMessage.isRunnameMessage(logLine)) {
                //    message = new RunnameMessage();
                //    message.m_messageType = Message.MESSAGE_TYPE_RUNNAME;
                //}
            }
            else if (type.equals(Message.MESSAGE_TYPE_CONTEXT)) {
                //"context",
                message = new ContextMessage();
                message.m_messageType = Message.MESSAGE_TYPE_CONTEXT;
            }
            else if (type.equals(Message.MESSAGE_TYPE_DIST)) {
                //"dist",
                message = new DistMessage();
                message.m_messageType = Message.MESSAGE_TYPE_DIST;
            }
            else if (type.equals(Message.MESSAGE_TYPE_END)) {
                //"end",
                message = new EndMessage();
                message.m_messageType = Message.MESSAGE_TYPE_END;
            }
/*            else if (type.equals(Message.MESSAGE_TYPE_FACET)) {
                //"facet",
                message = new FacetMessage();
                message.m_messageType = Message.MESSAGE_TYPE_FACET;
            }
*/            else if (type.equals(Message.MESSAGE_TYPE_FACTOID)) {
                //"factoid",
                message = new FactoidMessage();
                message.m_messageType = Message.MESSAGE_TYPE_FACTOID;
            }
            else if (type.equals(Message.MESSAGE_TYPE_HOW_SEEN)) {
                //"how_seen",
                message = new HowSeenMessage();
                message.m_messageType = Message.MESSAGE_TYPE_HOW_SEEN;
            }
            else if (type.equals(Message.MESSAGE_TYPE_IDENTIFY)) {
                //"identify",
                message = new IdentifyMessage();
                message.m_messageType = Message.MESSAGE_TYPE_IDENTIFY;
            }
            else if (type.equals(Message.MESSAGE_TYPE_INITIATE)) {
                //"initiate",
                message = new InitiateMessage();
                message.m_messageType = Message.MESSAGE_TYPE_INITIATE;
            }
            else if (type.equals(Message.MESSAGE_TYPE_POST)) {
                //"post",
                message = new PostMessage();
                message.m_messageType = Message.MESSAGE_TYPE_POST;
            }
            else if (type.equals(Message.MESSAGE_TYPE_PULL)) {
                //"pull",
                message = new PullMessage();
                message.m_messageType = Message.MESSAGE_TYPE_PULL;
            }
            else if (type.equals(Message.MESSAGE_TYPE_SHARE)) {
                //"share",
                message = new ShareMessage();
                message.m_messageType = Message.MESSAGE_TYPE_SHARE;
            }
            else if (type.equals(Message.MESSAGE_TYPE_WHAT_SEE)) {
                //"what_see"
                message = new WhatSeeMessage();
                message.m_messageType = Message.MESSAGE_TYPE_WHAT_SEE;
            }
            else if (type.equals(Message.MESSAGE_TYPE_ORGANIZATION)) {
                message = new OrganizationMessage();
                message.m_messageType = Message.MESSAGE_TYPE_ORGANIZATION;
            }
            else if (type.equals(Message.MESSAGE_TYPE_START)) {
                message = new StartMessage();
                message.m_messageType = Message.MESSAGE_TYPE_START;
            }
            else if (type.equals(Message.MESSAGE_TYPE_ADD)) {
                message = new AddMessage();
                message.m_messageType = Message.MESSAGE_TYPE_ADD;
            }
            else if (type.equals(Message.MESSAGE_TYPE_SOLUTION)) {
                message = new SolutionMessage();
                message.m_messageType = Message.MESSAGE_TYPE_SOLUTION;
            }
            else if (type.equals(Message.MESSAGE_TYPE_SOLUTIONFACTOIDS)) {
                message = new SolutionMessage();
                message.m_messageType = Message.MESSAGE_TYPE_SOLUTIONFACTOIDS;
            }
/*            else if (type.equals(Message.MESSAGE_TYPE_AGENT)) {
                //only process if is agent name
                if (AgentMessage.isAgentName(logLine)) {
                    message = new AgentMessage();
                    message.m_messageType = Message.MESSAGE_TYPE_AGENT;
                }
            }
*/          else {
            		//System.out.println("Unknown line: "+logLine);  
	
            }
            //
            if (message!=null) {

                message.Read(logLine);
                
                //System.out.println("LINE: "+message.m_logLine);                	
                //System.out.println("\t"+message);

                //System.out.println(message);
            	
            }
        }
        catch (Exception ex) {
            System.out.println("An exception was raised: "+ex.getMessage()+ " - following line was discarded: "+logLine);
            
            //ex.printStackTrace();
            
            return null;
        }
        return message;
    }
        
}
