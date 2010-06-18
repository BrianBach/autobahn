/**
 * 
 */
package net.geant.autobahn.tool.pionier;

/**
 * @author Michal
 *
 */
public class DemoReservation {
	
	//common parameters
	private final static String VLL_NAME = "XMR1-sc07demo-XMR2";
	private final static int VLL_VC_ID = 227;
	
	//ssh
	private final static String SSH_USERNAME = "scdemo";
	private final static String SSH_PASSWORD = "321scdemo07";
	
	//XMR 1 toward PSNC testbed
	private final static String VLL_XMR1_PORT = "8/3";
	private final static String VLL_XMR1_PEER_IP = "192.168.29.2";
	private final static String XMR1_MGMT_IP = "172.16.50.1";
	
	//XMR 2 toward GN2
	private final static String VLL_XMR2_PORT = "8/7";
	private final static int VLAN_TAG = 3010;
	private final static String VLL_XMR2_PEER_IP = "192.168.29.1";
	private final static String XMR2_MGMT_IP = "172.16.50.2";

	public static ReservationParameters GetReservationParametersSC07 () {
		
		SshConnectionParameters sshStart = new SshConnectionParameters(
				XMR1_MGMT_IP, SSH_USERNAME, SSH_PASSWORD);
		
		SshConnectionParameters sshEnd = new SshConnectionParameters(
				XMR2_MGMT_IP, SSH_USERNAME, SSH_PASSWORD);
		
		ReservationParameters parameters = new ReservationParameters(
				VLL_NAME,
				VLL_VC_ID,
				VLL_XMR1_PORT,
				VLL_XMR2_PORT,
				VLL_XMR1_PEER_IP,
				VLL_XMR2_PEER_IP,
				VLAN_TAG,
				sshStart,
				sshEnd);
		
		return parameters;
	}
}
