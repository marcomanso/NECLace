/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agility;

/**
 *
 * @author marcomanso
 */
public class Score {
    //
    private int m_nbrSteps;
    private Double[] m_score_values;

    public double score=0.0;
    public double value=0.0;

    public Score(int nbrSteps) {
        m_nbrSteps = nbrSteps;
        m_score_values = new Double[m_nbrSteps];
    }

    public void addScore(int index, double value) {
        //fill in score at given step
        m_score_values[index] = value;
        //fill in all scores below if empty
        for (int i=0; i<m_score_values.length; i++)
            if (m_score_values[index]==null)
                m_score_values[index]=value;
    }

    public void addScoreByPercentage(double percentage, double value) {
        int index = (int)(percentage*10)    ;
        if (index>=m_nbrSteps) index = m_nbrSteps-1;
        addScore(index, value);
        //System.out.println("percent="+percentage+", index="+index+", value="+value);
    }

    public Double getScore(int index) {
        return m_score_values[index];
        //return null;
    }
    public Double[] getScore() {
        return m_score_values;
    }
    public int getNbrSteps() {
        return m_nbrSteps;
    }

}
