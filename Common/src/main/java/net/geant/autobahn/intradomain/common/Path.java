/**
 * GenericConnection.java
 * 2007-03-28
 */

package net.geant.autobahn.intradomain.common;

import java.io.Serializable;

/**
 * This class represents a network path
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
public class Path implements Serializable {
	
	private static final long serialVersionUID = -8284087311808918479L;
	
	private long pathId;
	private VersionInfo version;
	private String name;
	private String status;
	
	/**
	 * This method returns the name of the path
	 * 
	 * @return Returns the name of the path
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the path
	 * 
	 * @param name the name of the path
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the id of the path
	 * 
	 * @return Returns the id of the path
	 */
	public long getPathId() {
		return pathId;
	}
	
	/**
	 * This method sets the id of the path
	 * 
	 * @param pathId the id of the path
	 */
	public void setPathId(long pathId) {
		this.pathId = pathId;
	}
	
	/**
	 * This method returns the status of the path
	 * 
	 * @return Returns the status of the path
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * This method sets the status of the path
	 * 
	 * @param status the status of the path
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * This method returns version information for the path
	 * 
	 * @return Returns version information for the path
	 */
	public VersionInfo getVersion() {
		return version;
	}
	
	/**
	 * This method sets version information for the path
	 * 
	 * @param version the version information for the path
	 */
	public void setVersion(VersionInfo version) {
		this.version = version;
	}
	

}
