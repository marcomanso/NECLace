/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author marcomanso
 */
public class ProcessAgilityFile {

    public static final int NBR_STEPS = 10;

    private static String fixSetupName(String s) {
        String sFixed = null;
        if ( Character.isDigit(s.charAt(0)) && s.charAt(1)=='-' ) {
            return s.substring(2, s.length()-1);
        }
        return s;
    }

    public static void processFile ( File file, ExperimentDataList dataList) throws FileNotFoundException, IOException, Exception {
        //
        FileReader fileReader = new FileReader(file);        
        BufferedReader br = new BufferedReader(fileReader);
        String s;
        //
        boolean firstLine=true;
        while((s = br.readLine()) != null) {
            if (firstLine) {
                //ignore
                firstLine=false;
            }
            else {
                StringTokenizer token = new StringTokenizer(s);
                //hard process tokens
                ExperimentData data = new ExperimentData(NBR_STEPS);
                data.m_approachNumber = token.nextToken();
                data.m_agilitySetup = fixSetupName(token.nextToken());
                //
                double shAwareness = Double.parseDouble(token.nextToken());
                double AVGcorrectness = Double.parseDouble(token.nextToken());
                double MAXtimeliness = Double.parseDouble(token.nextToken());
                data.m_avgCorrectness.addScoreByPercentage(AVGcorrectness, MAXtimeliness);
                data.m_sharedAwarenessTimeliness.addScoreByPercentage(shAwareness, MAXtimeliness);
                
                System.out.print("file="+file.getAbsoluteFile());
                System.out.print(" shAwareness="+shAwareness);
                System.out.print(" AVGcorrectness="+AVGcorrectness);
                System.out.print(" MAXtimeliness="+MAXtimeliness);
                System.out.println();
                
                //INFORMATION REACH
                for (int i=0; i<NBR_STEPS; i++) {
                    try {
                        double value = Double.parseDouble(token.nextToken());
                        data.m_informationReach.addScore(i, value);
                    }
                    catch (Exception ex) {
                        //no value - do nothing
                        //System.out.println("parsedouble exception: "+ex);
                    }
                }
                //CORRECT_AW
                for (int i=0; i<NBR_STEPS; i++) {
                    try {
                        double value = Double.parseDouble(token.nextToken());
                        data.m_correctAwareness.addScore(i, value);
                        
                        //System.out.println("m_correctAwareness: score="+i+" value="+value);
                        
                    }
                    catch (Exception ex) {
                        //no value - do nothing
                        //System.out.println("parsedouble exception: "+ex);
                    }
                }
                //EFFECTIVENESS
                for (int i=0; i<NBR_STEPS; i++) {
                    try {
                        double value = Double.parseDouble(token.nextToken());
                        data.m_effectiveness.addScore(i, value);
                    }
                    catch (Exception ex) {
                        //no value - do nothing
                        //System.out.println("parsedouble exception: "+ex);
                    }
                }
                //C2 approach
                data.m_approachName = token.nextToken();
                //log filename
                data.m_fileName = token.nextToken();
                //ADD TO LIST
                dataList.add(data);
                
                               
            }
        }
        fileReader.close();
    }

}
