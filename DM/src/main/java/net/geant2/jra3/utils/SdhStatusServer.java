package net.geant2.jra3.utils;

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
import java.util.Properties;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 * This is a reference SDH monitoring SOAP server implementation.
 * It connects to SDH devices directly using SSH.
 * It is a stand-alone class 
 * (i.e. does not depend on the rest of the AutoBAHN modules).
 * See ssh_sdh.properties file for deployment instructions.
 * @author Kostas Stamos (stamos@cti.gr)
 *
 */
@WebService
public class SdhStatusServer {

    //Status of an SDH path
    private static int UNKNOWN=0;
    private static int UP=1;
    private static int DEGRADED=2;
    private static int DOWN=3;

    /**
     * Check the status of an SDH path using SSH communication 
     * with the server   
     * 
     * This method will be called when a SOAP client makes a connection
     * 
     * Here is an example XML message that will be received from the SOAP client:
     * 
     * <?xml version="1.0" encoding="UTF-8"?>
     *  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
     *  <soapenv:Body>
     *      <checkSdhStatusNS:checkSdhPathStatus xmlns:checkSdhStatusNS="http://www.geant2.net/jra3/SdhMonitoring">
     *          <checkSdhStatusNS:path>pathname</checkSdhStatusNS:path>
     *      </checkSdhStatusNS:checkSdhInterfaceStatus>
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
     * @param path: The SDH path name
     */
    @WebMethod
    @WebResult(name="result")
    public int checkEthInterfaceStatus(@WebParam(name="path") String path) {
        // Just for verbose information
        String output = "SDH path: "+path;
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

            // Send the command, appended with the SDH path name
            SessionChannelClient session = ssh.openSessionChannel();
            session.startShell();
            OutputStream out = session.getOutputStream();
            String cmd = SSHCommand + " '\\<" + path + "'\n";
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

        if (response!=null) {
            if (response.startsWith(path)) {
                status = DOWN;
                System.out.println("!!!!!!!!!!!!The SDH link is down!!!!!!!!!!!!");
            }
            else {
                status = UP;
                System.out.println("!!!!!!!!!!!!The SDH link is up!!!!!!!!!!!!");
            }
        }
        else {
            status = UP;
            System.out.println("!!!!!!!!!!!!The SDH link is up!!!!!!!!!!!!");
        }
        
        return status;
    }

    private Properties readProperties() throws Exception {
        Properties properties = new Properties();
        String filename = "ssh_sdh.properties";
        try {
            // Use classloader to find the ssh_sdh.properties file in the location
            // of our class
            URL url = EthernetInterfaceStatusServer.class.getClassLoader().getResource(filename);
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
