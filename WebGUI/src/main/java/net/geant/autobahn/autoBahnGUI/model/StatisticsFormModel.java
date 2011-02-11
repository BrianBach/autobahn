package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.List;

import net.geant.autobahn.administration.StatisticsType;

/**
 * Form model for showing setuptime statistics for each AutoBAHN IDM
 * 
 * @author Kostas Stamos <stamos@cti.gr>
 *
 */
public class StatisticsFormModel implements Serializable{
    
    /**
     * List of registered IDMs
     */
    private List<String> idms;
    
    /**
     * Chosen IDM
     */
    private String currentIdm;
    
    /**
     * Statistics information from IDM
     */
    private StatisticsType statistics;
    
    /**
     * Error information
     */
	private String error;
	
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public StatisticsType getStatistics() {
        return statistics;
    }
    
    public void setStatistics(StatisticsType statistics) {
        this.statistics = statistics;
    }
    
    public List<String> getIdms() {
        return idms;
    }
    
    public void setIdms(List<String> idms) {
        this.idms = idms;
    }
    
    public String getCurrentIdm() {
        return currentIdm;
    }
    
    public void setCurrentIdm(String currentIdm) {
        this.currentIdm = currentIdm;
    }
}
