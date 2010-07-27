package net.geant.autobahn.testplatform.configuration;


public class IncrementalProperties {

	private String dbName;
	private int frameworkPort;
	private String nodesRange;
	private String portsRange;
	private String linksRange;
	private String home;
	private String domainName;
	
	private IncrementalProperties current;
	private int count = 0;
	
	public IncrementalProperties(MyProperties props) {
		this.dbName = props.getProperty("default.db-name-prefix") + 
			props.getProperty("environment.name") + "_";
		this.frameworkPort = Integer.valueOf(props.getProperty("default.mngmt-port"));
		this.nodesRange = props.getProperty("default.nodes-range");
		this.portsRange = props.getProperty("default.ports-range");
		this.linksRange = props.getProperty("default.links-range");
		this.home = props.getProperty("default.home") + "/" + props.getProperty("environment.name");
	}
	
	private IncrementalProperties() {
		
	}
	
	public IncrementalProperties incrementValues() {
		current = new IncrementalProperties();
		count++;
		
		current.dbName = dbName + count;
		current.frameworkPort = frameworkPort + count;
		current.nodesRange = buildRange(nodesRange, count);
		current.portsRange = buildRange(portsRange, count);
		current.linksRange = buildRange(linksRange, count);
		current.home = home + "/domain" + count;
		current.domainName = "domain" + count;
		
		return current;
	}
	
	private static String buildRange(String base, int count) {
		return base.replaceFirst("\\.\\d+\\.", "." + Integer.toString(9 + count) + ".");
	}

	public String getHome() {
		return home;
	}

    public String getDomainName() {
        return domainName;
    }	
	
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @return the frameworkPort
	 */
	public String getFrameworkPort() {
		return "" + frameworkPort;
	}

	/**
	 * @return the nodesRange
	 */
	public String getNodesRange() {
		return nodesRange;
	}

	/**
	 * @return the portsRange
	 */
	public String getPortsRange() {
		return portsRange;
	}

	/**
	 * @return the linksRange
	 */
	public String getLinksRange() {
		return linksRange;
	}

}
