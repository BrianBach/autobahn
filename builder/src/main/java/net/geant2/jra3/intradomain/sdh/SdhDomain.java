package net.geant2.jra3.intradomain.sdh;

import java.io.Serializable;
import java.util.Date;

public class SdhDomain implements Serializable {
	
	private static final long serialVersionUID = 8389119338270482311L;
	
	private long sdhDomainId;
	private String name;
	private String provMethod;
	private String equipmentProvider;
	private Date dateModified;
	
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public String getEquipmentProvider() {
		return equipmentProvider;
	}
	public void setEquipmentProvider(String equipmentProvider) {
		this.equipmentProvider = equipmentProvider;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvMethod() {
		return provMethod;
	}
	public void setProvMethod(String provMethod) {
		this.provMethod = provMethod;
	}
	public long getSdhDomainId() {
		return sdhDomainId;
	}
	public void setSdhDomainId(long sdhDomainId) {
		this.sdhDomainId = sdhDomainId;
	}
	

}
