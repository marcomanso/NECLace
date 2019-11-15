/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elicit.utils.dialogs;

import java.io.*;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mmanso
 */
public class DialogFileChooser {

    public static File SelectFile (Component parent,
						            FileNameExtensionFilter filter,
						            File f,
						            boolean isNewFile) throws Exception
	{
        JFileChooser chooser = new JFileChooser();
        //chooser.setFileFilter(filter);
        //int returnVal = chooser.showOpenDialog(parent);
        
        if (f!=null)
        	chooser.setSelectedFile(f);
        
        int returnVal = chooser.showDialog(parent, "SAVE");
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            //update in combo box
            File selectedFile = chooser.getSelectedFile();
            if (selectedFile ==null)
                throw new Exception("No valid file selected.");
            else if (isNewFile && selectedFile.exists())
                throw new Exception("File already exists.");
            else if (selectedFile.exists() && !selectedFile.canWrite())
                throw new Exception("No write permissions for selected file.");
            //must create new since 'chooser' selected File object is set to null
            return selectedFile;
        }
        else {
            throw new Exception("Operation cancelled.");
        }
	}    
    
    public static File SelectFile (Component parent,
            FileNameExtensionFilter filter,
            boolean isNewFile) throws Exception
	{
	return SelectFile(parent, filter, null, isNewFile);
	}

    public static File SelectNewFile (Component parent,
                                      FileNameExtensionFilter filter) throws Exception 
    {
        return SelectFile(parent, filter, true);
    }//newfile
    
}
