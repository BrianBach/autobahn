package net.geant.autobahn.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.network.StatisticsEntry;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StatisticsType", namespace="administration.autobahn.geant.net", propOrder={
		"averageIntra", "averageInter", "intra", "inter"
})
public class StatisticsType implements Serializable {
	
	private static final long serialVersionUID = 7426855467383158387L;
	
	private long averageIntra;
    private long averageInter;
    private List<StatisticsEntry> intra = new ArrayList<StatisticsEntry>();
    private List<StatisticsEntry> inter = new ArrayList<StatisticsEntry>();
    
    public StatisticsType() {
    	
    }

    /**
     * @return the average time for Intradomain segments
     */
    public long getAverageIntra() {
        return averageIntra;
    }

    /**
     * @param averageIntra the average time for Intradomain segments to set
     */
    public void setAverageIntra(long averageIntra) {
        this.averageIntra = averageIntra;
    }

    /**
     * @return the average Interdomain reservation setup time
     */
    public long getAverageInter() {
        return averageInter;
    }

    /**
     * @param averageInter the average Interdomain reservation setup time to set
     */
    public void setAverageInter(long averageInter) {
        this.averageInter = averageInter;
    }

    /**
     * @return the list of intradomain segment reservation statistics
     */
    public List<StatisticsEntry> getIntra() {
        return intra;
    }

    /**
     * @param intra the list of intradomain segment reservation statistics to set
     */
    public void setIntra(List<StatisticsEntry> intra) {
        this.intra = intra;
        setAverageIntra(calculateAverageSetup(intra));
    }

    /**
     * @return the list of interdomain reservation statistics
     */
    public List<StatisticsEntry> getInter() {
        return inter;
    }

    /**
     * @param inter the list of interdomain reservation statistics to set
     */
    public void setInter(List<StatisticsEntry> inter) {
        this.inter = inter;
        setAverageInter(calculateAverageSetup(inter));
    }
    
    /**
     * Calculates the average value of setuptime for the elements of
     * a StatisticsEntry list 
     * @param selist - the StatisticsEntry list
     * @return the average value
     */
    private long calculateAverageSetup(List<StatisticsEntry> selist) {

        if (selist == null || selist.size() < 1) {
            return 0;
        }
        
        long sum = 0;
        for (StatisticsEntry se : selist) {
            sum += se.getSetup_time();
        }
        return sum/selist.size();
    }

}
