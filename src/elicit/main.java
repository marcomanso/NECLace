/*
 * main.java
 *
 * Created on 27 June 2007, 15:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit;

/**
 *
 * @author Marco
 */
public class main {
    
    
    /** Creates a new instance of main */
    public main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //ELICITMainWindow jfMain = new ELICITMainWindow();
        try {
            //JFConvertELICITLog jfMain = new JFConvertELICITLog();
            JFMainWindow jfMain = new JFMainWindow();
            jfMain.setVisible(true);            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
