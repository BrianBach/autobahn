/**
 * VersionInfo.java
 * 2007-03-28
 */

package net.geant.autobahn.intradomain.common;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;


/**
 * This class represents version information
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VersionInfo", namespace="common.intradomain.autobahn.geant.net", 
		propOrder={ "versionId", "startDate", "endDate", "createdBy", "modifiedBy",
		"dateCreated", "dateModified"
})
public class VersionInfo implements Serializable {
	
	private static final long serialVersionUID = 837509601849111470L;
	//@XmlTransient
	private long versionId;
	private Date startDate;
	private Date endDate;
	private String createdBy;
	private String modifiedBy;
	private Date dateCreated;
	private Date dateModified;
	
	
	/**
	 * This method returns the creator of the version information
	 * 
	 * @return Returns the creator of the version information
	 */
	public String getCreatedBy() {
		return createdBy;
	}


	/**
	 * This method sets the creator of the version information
	 * 
	 * @param createdBy the creator of the version information
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * This method returns the date of creation of the version information
	 * 
	 * @return Returns the date of the version information
	 */
	public Date getDateCreated() {
		return dateCreated;
	}


	/**
	 * This method sets the date of the version information
	 * 
	 * @param dateCreated the date of the version information
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	/**
	 * This method returns the modification date of the version information
	 * 
	 * @return Returns the modification date of the version information
	 */
	public Date getDateModified() {
		return dateModified;
	}


	/**
	 * This method sets the modification date of the version information
	 * 
	 * @param dateModified the modification date of the version information
	 */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}


	/**
	 * This method returns the end date of the version information
	 * 
	 * @return Returns the end date of the version information
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * This method sets the end date of the version information
	 * 
	 * @param endDate the end date of the version information
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * This method returns the name of the person that modified the version information
	 * 
	 * @return Returns the name of the person that modified the version information
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}


	/**
	 * This method sets the name of the person that modified the version information
	 * 
	 * @param modifiedBy the name of the person that modified the version information
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	/**
	 * This method returns the start date of the version information 
	 * 
	 * @return Returns the start date of the version information
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * This method sets the start date of the version information
	 * 
	 * @param startDate the start date of the version information
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * This method returns the id of the version information
	 * 
	 * @return Returns the id of the version information
	 */
	public long getVersionId() {
		return versionId;
	}


	/**
	 * This method sets the id of the version information
	 * 
	 * @param versionId the id of the version information
	 */
	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}
	
}
