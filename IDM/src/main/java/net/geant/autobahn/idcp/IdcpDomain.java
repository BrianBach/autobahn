/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.Properties;

/**
 * Represents single idcp domain, either peered or not
 * @author PCSS
 */
public class IdcpDomain {
	
	private String idcpUrl, idcpNotifyUrl; // url addresses to both interfaces
	private String domainName; // idcp domain name as found in its topology
	private String psTopologyUrl; // url to idcp domain topology service
	private String staticRoute; // domainName that is between autobahn domain and this idcp domain
	private Properties properties; // kept for link mapping searching
	
	IdcpDomain(String idcpUrl, String idcpNotifyUrl, String domainName, String psTopologyUrl, String staticRoute, Properties properties) {
		
		this.idcpUrl = idcpUrl;
		this.idcpNotifyUrl = idcpNotifyUrl;
		this.domainName = domainName;
		this.psTopologyUrl = psTopologyUrl;
		this.staticRoute = staticRoute;
		this.properties = properties;
	}
	
	public boolean isPeered() { 
		
		return staticRoute == null;
	}
	
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @return the idcpUrl
	 */
	public String getIdcpUrl() {
		return idcpUrl;
	}

	/**
	 * @return the idcpNotifyUrl
	 */
	public String getIdcpNotifyUrl() {
		return idcpNotifyUrl;
	}

	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * @return the psTopologyUrl
	 */
	public String getPsTopologyUrl() {
		return psTopologyUrl;
	}

	/**
	 * @return the staticRoute
	 */
	public String getStaticRoute() {
		return staticRoute;
	}

	@Override
	public String toString() { 
		
		if (staticRoute != null)		
			return domainName + " via " + staticRoute;
		else
			return domainName + " - " + idcpUrl;
	}
}
