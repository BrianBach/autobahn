package net.geant.autobahn.utils;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PublicKeyAuthenticationClient;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKey;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKeyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;


/**
 * This is a reference SDH monitoring SOAP server implementation.
 * Alarms from SDH nodes are collected and stored at a NM system.
 * This class connects to the NM machine using SSH and checks for
 * active alarms.
 * It is a stand-alone class 
 * (i.e. does not depend on the rest of the AutoBAHN modules).
 * See ssh_sdh.properties file for deployment instructions.
 * @author Kostas Stamos (stamos@cti.gr)
 *
 */
@WebService
public class SdhInterfaceStatusServer {

    //Status of an Ethernet link
    private static int UNKNOWN=0;
    private static int UP=1;
    private static int DEGRADED=2;
    private static int DOWN=3;

    /**
     * Check the status of an SDH path using SSH communication with the NM
     * 
     * This method will be called when a SOAP client makes a connection
     * 
     * Here is an example XML message that will be received from the SOAP client:
     * 
     * <?xml version="1.0" encoding="UTF-8"?>
     *  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
     *  <soapenv:Body>
     *      <checkSdhStatusNS:checkSdhPathStatus xmlns:checkSdhStatusNS="http://www.geant2.net/ws1">
     *          <checkSdhPathStatusNS:pathName>testPath</checkSdhPathStatusNS:pathName>
     *      </checkSdhStatusNS:checkSdhPathStatus>
     *  </soapenv:Body></soapenv:Envelope>
     * 
     * And here is an example response that we will send back:
     * 
     * <?xml version='1.0' encoding='UTF-8'?>
     *  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
     *      <soapenv:Body>
     *          <result>0</result>
     *      </soapenv:Body>
     *  </soapenv:Envelope>
     * 
     * @param pathName: The SDH path name
     */
    @WebMethod
    @WebResult(name="result")
    public int CheckSdhPathStatus(@WebParam(name="pathName") String pathName) {
        // Just for verbose information
    	String output = "pathName: "+pathName;
        System.err.println(output);

        int status = UNKNOWN;
        String response = null;
        
        try {
            Properties properties = readProperties();
            String SSHServer = properties.getProperty("SSHServer");
            String SSHUser = properties.getProperty("SSHUser");
            String SSHPvtKey = properties.getProperty("SSHPvtKey");
            String SSHPvtPhrase = properties.getProperty("SSHPvtPhrase");
            String SSHCommand = properties.getProperty("SSHCommand");
            
            //connect to the server using ssh
            SshClient ssh = new SshClient();
            // Connect to the server even if the host key is not cached
            // at the registry (otherwise interactive input would be required)
            ssh.connect(SSHServer, new IgnoreHostKeyVerification());
            PublicKeyAuthenticationClient pk = new PublicKeyAuthenticationClient();
            pk.setUsername(SSHUser);
            SshPrivateKeyFile file = SshPrivateKeyFile.parse(new File(SSHPvtKey));
            SshPrivateKey key = file.toPrivateKey(SSHPvtPhrase);
            pk.setKey(key);
            int result = ssh.authenticate(pk);
            if(result==AuthenticationProtocolState.FAILED) System.out.println("The authentication failed");
            if(result==AuthenticationProtocolState.PARTIAL) System.out.println("The authentication succeeded but another" + "authentication is required");
            if(result==AuthenticationProtocolState.COMPLETE) System.out.println("The authentication is complete");

            // Send the command
            SessionChannelClient session = ssh.openSessionChannel();
            session.startShell();
            OutputStream out = session.getOutputStream();
            
            
            String cmd = SSHCommand + " '\\<" + pathName + "'\n";
            out.write(cmd.getBytes());
            
            session.setLocalEOF();
            InputStream in = session.getInputStream();
            byte buffer[] = new byte[255];

            //read the response
            int read;
            if ((read = in.read(buffer)) > 0) {
                String out2 = new String(buffer, 0, read);
                response = out2;
            }

            session.close();
            ssh.disconnect();
        }
        catch(Exception e) {
            e.printStackTrace();
        }


		if (response!=null)	{
			if (response.equals((pathName+"\n"))) {
			    status=DOWN;

			}
			else {
				status=UP;

			}
		}
		else {
			status=UP;

		}
		return status;
     }

    private Properties readProperties() throws Exception {
        Properties properties = new Properties();
        String filename = "ssh_sdh.properties";
        try {
            // Use classloader to find the ssh_sdh.properties file in the location
            // of our class
            URL url = SdhInterfaceStatusServer.class.getClassLoader().getResource(filename);
            FileInputStream fis = new FileInputStream(url.getPath());
            properties.load(fis);
            fis.close();
            System.out.println(properties.size() + " properties loaded");
            return properties;
        } catch (IOException e) {
            System.err.println("Could not load "+filename+": " + e.getMessage());
            throw new Exception("Could not load "+filename+": " + e.getMessage());
        }
    }
}
