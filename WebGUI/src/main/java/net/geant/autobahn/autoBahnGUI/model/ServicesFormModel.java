package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.geant.autobahn.reservation.Service;


/**
 * Data model for SubmittedServicesForm controller
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ServicesFormModel implements Serializable{
	/**
	 * List of registered JRA2 IDM
	 */
	List<String> idms;
	/**
	 * Identifies currently selected interdomain manager name
	 */
	String currentIdm;
	/**
	 * Identifies services submitted at selected interdomain manager
	 */
	List<Service> services;
	/**
	 * Indetifies comparator for ordering serices list
	 */
	Comparator<Service> comparator;
	
	public List<String> getIdms() {
		return idms;
	}
	public void setIdms(List idms) {
		this.idms = idms;
	}
	public String getCurrentIdm() {
		return currentIdm;
	}
	public void setCurrentIdm(String currentIdm) {
		this.currentIdm = currentIdm;
	}
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services=services;
		if (services!= null && comparator!=null)
			Collections.sort(services, comparator);
	}
	public Comparator<Service> getComparator() {
		return comparator;
	}
	public void setComparator(Comparator<Service> comparator) {
		this.comparator = comparator;
		if (services!= null && comparator!=null)
			Collections.sort(services, comparator);
	}
	
}
