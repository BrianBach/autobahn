/**
 * InterfaceType.java
 * 2007-03-28
 */

package net.geant.autobahn.intradomain.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class represents an interface type
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="InterfaceType", namespace="common.intradomain.autobahn.geant.net",
	propOrder={"switchingType", "dataEncodingType"
})
public class InterfaceType implements Serializable {
	
	private static final long serialVersionUID = -3760191037619830478L;
	@XmlTransient
	private long interfaceTypeId;
	private String switchingType; 
	private String dataEncodingType;
	
	/**
	 * This method returns the data encoding type
	 * 
	 * @return Returns the data encoding type
	 */
	public String getDataEncodingType() {
		return dataEncodingType;
	}
	
	/**
	 * This method sets the data encoding type
	 * 
	 * @param dataEncodingType the data encoding type
	 */
	public void setDataEncodingType(String dataEncodingType) {
		this.dataEncodingType = dataEncodingType;
	}
	
	/**
	 * This method returns the id of the interface type
	 * 
	 * @return Returns the id of the interface type
	 */
	public long getInterfaceTypeId() {
		return interfaceTypeId;
	}
	
	/**
	 * This method sets the id of the interface type
	 * 
	 * @param interfaceTypeId the id of the interface type
	 */
	public void setInterfaceTypeId(long interfaceTypeId) {
		this.interfaceTypeId = interfaceTypeId;
	}
	
	/**
	 * This method returns the switching type
	 * 
	 * @return Returns the switching type
	 */
	public String getSwitchingType() {
		return switchingType;
	}
	
	/**
	 * This method sets the switching type
	 * 
	 * @param switchingType the switching type
	 */
	public void setSwitchingType(String switchingType) {
		this.switchingType = switchingType;
	}


}
