
/*
 * ReadELICITLog.java
 *
 * Created on 27 June 2007, 23:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.tools;

import elicit.message.Message;
import elicit.message.MessageListener;
import elicit.message.MessageParser;
import java.io.*;
import java.util.*;
import java.util.logging.Level;

import org.jfree.util.Log;

/**
 *
 * @author Marco
 */
public class ReadELICITLog {
    
    String m_inputFile = null;
    MessageListener m_listener = null;

    /** Creates a new instance of ReadELICITLog */
    public ReadELICITLog(String inputFile) {
        m_inputFile = inputFile;
    }
    
    public void addMessageListener(MessageListener listener_p) {
        m_listener = listener_p;
    }
    
    public void doIt() {
        Scanner s = null;
        
        //Delimiter character is END_LINE
        //- each line will be written to output file
        try {
            //input file
            s = new Scanner(new BufferedReader(new FileReader(m_inputFile))).useDelimiter("\n");

            //trigger start
            if (m_listener!=null) {
                //new message
                m_listener.OnStart();
            }//end if message!=null

            //while has lines
            while (s.hasNext()) {

                //process line
                Message message = MessageParser.getMessage(s.next());
                if (message!=null && m_listener!=null) {
                    //new message
                    m_listener.OnNewMessage(message);
                }//end if message!=null
                
            }//end while
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
            
            //trigger end
            if (m_listener!=null) {
                //new message
                m_listener.OnFinish();
            }//end if message!=null
            
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: <ELICIT log filename>");
        }
        else {
            System.out.print("Input file: ");
            System.out.println(args[0]);
            ReadELICITLog readLog = new ReadELICITLog(args[0]);
            readLog.doIt();
        }
    }    
}
