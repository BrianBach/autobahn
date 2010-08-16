package net.geant.autobahn.testplatform.configuration;

public class Domain {

    private String domainId;
    private String host;
	private int tcpPort;
    private int secureTcpPort;
    
    public Domain(String domainId, String host, int tcpPort, int secureTcpPort) {
        super();
        this.domainId = domainId;
        this.host = host;
        this.tcpPort = tcpPort;
        this.secureTcpPort = secureTcpPort;
    }

    /**
     * @return the domainId
     */
    public String getDomainId() {
        return domainId;
    }
    
    /**
     * @param domainId the domainId to set
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
    
    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }
    
    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }
    
    /**
     * @return the tcpPort
     */
    public int getTcpPort() {
        return tcpPort;
    }
    
    /**
     * @param tcpPort the tcpPort to set
     */
    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }
    
    /**
	 * @return the secureTcpPort
	 */
	public int getSecureTcpPort() {
		return secureTcpPort;
	}

	/**
	 * @param secureTcpPort the secureTcpPort to set
	 */
	public void setSecureTcpPort(int secureTcpPort) {
		this.secureTcpPort = secureTcpPort;
	}    
}
