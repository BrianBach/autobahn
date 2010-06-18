/**
 * 
 */
package net.geant.autobahn.tool.pionier;

/**
 * @author Michal
 *
 */
public class ReservationParameters {
	
	private String vllName;
	private int vllVcId;
	private String vllStartPort;
	private String vllEndPort;
	private String vllStartIP;
	private String vllEndIP;
	private int vlanTag;
	private SshConnectionParameters sshConnectionStartParameters; 
	private SshConnectionParameters sshConnectionEndParameters;
	
	//Constructor
	ReservationParameters(String vllName,
						 int vllVcId,
						 String vllStartPort,
						 String vllEndPort,
						 String vllStartIP,
						 String vllEndIP,
						 int vlanTag,
						 SshConnectionParameters sshConnectionStartParameters,
						 SshConnectionParameters sshConnectionEndParameters) {
		
		this.vllName = vllName; 
		this.vllVcId = vllVcId;
		this.vllStartPort = vllStartPort;
		this.vllEndPort = vllEndPort;
		this.vllStartIP = vllStartIP;
		this.vllEndIP = vllEndIP;
		this.vlanTag = vlanTag;
		this.sshConnectionStartParameters = sshConnectionStartParameters;
		this.sshConnectionEndParameters = sshConnectionEndParameters;
		
	}

	public String getVllEndIP() {
		return vllEndIP;
	}

	public String getVllEndPort() {
		return vllEndPort;
	}
	
	public String getVllName() {
		return vllName;
	}
	public String getVllStartIP() {
		return vllStartIP;
	}

	public String getVllStartPort() {
		return vllStartPort;
	}

	public int getVllVcId() {
		return vllVcId;
	}
	
	public int getVlanTag() {
		return vlanTag;
	}
	
	public SshConnectionParameters getSshConnectionEndParameters() {
		return sshConnectionEndParameters;
	}

	public SshConnectionParameters getSshConnectionStartParameters() {
		return sshConnectionStartParameters;
	}
}
