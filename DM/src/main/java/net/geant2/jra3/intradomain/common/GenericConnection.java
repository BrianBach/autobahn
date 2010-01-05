/**
 * GenericConnection.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.common;

import java.io.Serializable;


/**
 * This class represents a network connection
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
public class GenericConnection implements Serializable {
	
	private static final long serialVersionUID = 1978789765485621401L;
	
	private long genericConnectionId;
	private VersionInfo version;
	private Path path;
	private GenericLink link;
	private String direction;
	private String connectionType;
	private double bandwidth;
	
	/**
	 * This method returns the bandwidth of the connection 
	 * 
	 * @return Returns the bandwidth of the connection
	 */
	public double getBandwidth() {
		return bandwidth;
	}
	
	/**
	 * This method sets the bandwidth of the connection
	 * 
	 * @param bandwidth the bandwidth of the connection
	 */
	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	/**
	 * This method returns the type of the connection
	 * 
	 * @return Returns the type of the connection
	 */
	public String getConnectionType() {
		return connectionType;
	}
	
	/**
	 * This method sets the type of the connection
	 * 
	 * @param connectionType the type of the connection
	 */
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	
	/**
	 * This method returns the direction of the connection
	 * 
	 * @return Returns the direction of the connection
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * This method sets the direction of the connection
	 * 
	 * @param direction the direction of the connection
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	/**
	 * This method returns the id of the connection
	 * 
	 * @return Returns the id of the connection
	 */
	public long getGenericConnectionId() {
		return genericConnectionId;
	}
	
	/**
	 * This method sets the id of the connection
	 * 
	 * @param genericConnectionId the id of the connection
	 */
	public void setGenericConnectionId(long genericConnectionId) {
		this.genericConnectionId = genericConnectionId;
	}
	
	/**
	 * This method returns the generic link creating the connection
	 * 
	 * @return Returns the generic link creating the connection
	 */
	public GenericLink getLink() {
		return link;
	}
	
	/**
	 * This method sets the generic link creating the connection
	 * 
	 * @param link the generic link creating the connection
	 */
	public void setLink(GenericLink link) {
		this.link = link;
	}
	
	/**
	 * This method returns the path to which the connection belongs
	 * 
	 * @return Returns the path to which the connection belongs
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * This method sets the path to which the connection belongs
	 * 
	 * @param path the path to which the connection belongs
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/**
	 * This method returns version information for the connection
	 * 
	 * @return Returns version information for the connection
	 */
	public VersionInfo getVersion() {
		return version;
	}
	
	/**
	 * This method sets version information for the connection
	 * 
	 * @param version the version information for the connection
	 */
	public void setVersion(VersionInfo version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (genericConnectionId ^ (genericConnectionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GenericConnection other = (GenericConnection) obj;
		if (genericConnectionId != other.genericConnectionId)
			return false;
		return true;
	}
}
