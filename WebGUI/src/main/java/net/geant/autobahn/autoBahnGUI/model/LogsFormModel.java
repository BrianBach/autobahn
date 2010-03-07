package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.List;

/**
 * Form model for showing logs for each AutoBAHN IDM
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class LogsFormModel implements Serializable{
	
	/**
	 * List of registered JRA2 IDM
	 */
	private List<String> idms;
	/**
	 * Chosen IDM
	 */
	private String currentIdm;
	/**
	 * Logs information get from IDM
	 */
	private String logs;
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
	public String getLogs() {
		return logs;
	}
	public void setLogs(String logs) {
		this.logs = logs;
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
