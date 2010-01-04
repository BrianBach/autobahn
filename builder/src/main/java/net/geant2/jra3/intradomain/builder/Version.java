package net.geant2.jra3.intradomain.builder;
import java.util.Date;

/**
 * Class provide information about version
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class Version {
	/**
	 * Date and time of creation of element
	 */
	private Date created;
	/**
	 * Date of last modification of element  
	 */
	private Date lastModified;
	/**
	 * Element version
	 */
	private int version;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
