/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics.interaction;

import java.util.*;

/**
 *
 * @author mmanso
 */
public class OverallInteractions extends TreeMap<String, OverallInteractions.OverallStatistics> {

    public class OverallStatistics {

        public int totalPosts = 0;
        public int totalShares = 0;
        public int totalSharesRcv = 0;
        public int totalPulls = 0;
        public int totalIDs = 0;
        public int totalADDs = 0;
        //
        public double postsHour = 0.0;
        public double sharesHour = 0.0;
        public double sharesRcvHour = 0.0;
        public double pullsHour = 0.0;
        public double iDsHour = 0.0;
        public double aDDsHour = 0.0;
        //
        public int withinTeam = 0;
        public int outsideTeam = 0;
    }

    private double CalculateTotalPerHour(int totalActions, long durationSec) {
        return ((double) totalActions) / (double) (durationSec / (double) 3600);
    }

    public OverallStatistics get(String key) {
        if (!containsKey(key)) {
            put(key, new OverallStatistics());
        }
        return super.get(key);
    }

    public void CalculateTotalsPerHour(long durationSeconds) {
        for (String key : this.keySet()) {
            this.get(key).postsHour = CalculateTotalPerHour(this.get(key).totalPosts, durationSeconds);
            this.get(key).sharesHour = CalculateTotalPerHour(this.get(key).totalShares, durationSeconds);
            this.get(key).sharesRcvHour = CalculateTotalPerHour(this.get(key).totalSharesRcv, durationSeconds);
            this.get(key).pullsHour = CalculateTotalPerHour(this.get(key).totalPulls, durationSeconds);
            this.get(key).iDsHour = CalculateTotalPerHour(this.get(key).totalIDs, durationSeconds);
            this.get(key).aDDsHour = CalculateTotalPerHour(this.get(key).totalADDs, durationSeconds);
        }
    }
}
