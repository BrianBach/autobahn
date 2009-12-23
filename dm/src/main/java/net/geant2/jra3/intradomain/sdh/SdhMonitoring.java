/**
 * SdhMonitoring.java
 * 2007-08-30
 */

package net.geant2.jra3.intradomain.sdh;

import java.util.Map;
import java.util.Set;
import net.geant2.jra3.intradomain.AccessPoint;
import net.geant2.jra3.intradomain.ResourcesReservation;
import net.geant2.jra3.intradomain.IntradomainPath;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import net.geant2.jra3.dm2idm.Dm2Idm;
import net.geant2.jra3.dm2idm.Dm2IdmClient;


/**
 * Monitors an SDH topology for the status of an SDH path. 
 *  
 * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
 */
public class SdhMonitoring implements Runnable {

	/**
	 * @param args
	 */
	
	//Status of an SDH path
	private static int UNKNOWN=0;
	private static int UP=1;
	private static int DEGRADED=2;
	private static int DOWN=3;
  
	//Deamon parameters
	private  static String MDsleepTimePr =  "MDsleepTime";
	private int sleepTime;
	private Thread deamonThread = null;
	
	//SSH Status Server access parameters
    private  static String SdhStatusServerPr = "SdhStatusServer";
    private   String SdhStatusServer;
 
	private ResourcesReservation dm = null;
	private String idmAddress;
    /**
     * Constructor for the class
     * 
     */
	public SdhMonitoring(AccessPoint ap) 
	{
        SdhStatusServer = ap.getProperty(SdhStatusServerPr);
		idmAddress = ap.getProperty("idm.address");
        sleepTime = Integer.parseInt(ap.getProperty(MDsleepTimePr));
		dm = ap.getDomainManager();
	}
	 /**
     * Start the monitoring deamon
     * 
     */
	public boolean startMonitoring() 
	{
		try {
			deamonThread = new Thread(this);
			deamonThread.setDaemon(true);
			deamonThread.start();
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	 /**
     * Stop the monitoring deamon
     * 
     * 
     */
	public boolean stopMonitoring() 
	{
		try
		{
			deamonThread.stop();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}	
	}
	
	 /**
     * The monitoring procedure
     * 
     * 
     */
	public void run() {
		try {
            // Take an initial long nap to make sure initialization is OK
            Thread.sleep(sleepTime*3);
        }
        catch (Exception e) {
            System.err.println("SDH Monitoring Deamon's initial long sleep ("+sleepTime*3/1000+" sec.) was interrupted prematurely, will continue now");
            e.printStackTrace();
        }

    	while (true) 
    	{
    		try {
    			updateReservationsDBStatus();
    			Thread.sleep(sleepTime);
    		}
    		catch (InterruptedException x) {
    			System.err.println("SDH Monitoring Deamon's sleep ("+sleepTime/1000+" sec.) was interrupted prematurely, will continue now");
                x.printStackTrace();
    		}
            catch (Exception e) {
                System.err.println("Stopped SDH Monitoring Deamon due to exception:");
                e.printStackTrace();
                break;
            }
    	}
	
	  }
     
    /**
     * Check the status of an SDH path using SOAP call 
     * Here is an example XML message that will be sent to the SOAP server:
     * 
     * <?xml version="1.0" encoding="UTF-8"?>
     *  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
     *  <soapenv:Body>
     *      <checkSdhStatusNS:checkSdhPathStatus xmlns:checkSdhStatusNS="http://www.geant2.net/jra3/SdhMonitoring">
     *          <checkSdhStatusNS:path>pathname</checkSdhStatusNS:path>
     *      </checkSdhStatusNS:checkSdhInterfaceStatus>
     *  </soapenv:Body></soapenv:Envelope>
     * 
     * And here is an example response that we will get back:
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
    private int checkSdhPathStatusSOAP(String path)
    {
        String xmlString2 = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
            "<SOAP-ENV:Envelope \n" +
            "       xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" \n" +
            "       xmlns:tns=\"http://www.geant2.net/jra3/SdhMonitoring\">\n" +
            "   <SOAP-ENV:Body>\n" +
            "       <tns:CheckSdhPathStatus>\n"+
            "           <tns:pathName>" + path + "</tns:pathName>\n" +
            "       </tns:CheckSdhPathStatus>\n" +
            "   </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";
        
        try {
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage smsg =
                    mf.createMessage(new MimeHeaders(), new ByteArrayInputStream(xmlString2.getBytes()));
            
            // Add a dummy SOAPAction header (Axis requires SOAPAction header to be set!)
            smsg.getMimeHeaders().addHeader("SOAPAction",""); 
          
            SOAPConnection conn = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = conn.call(smsg, SdhStatusServer);

            // The XML structure is:
            // Body
            // |--checkSdhPathStatusResponse tag
            //    |--result tag
            //       |--result value
            org.w3c.dom.Node result_node = response.getSOAPBody().getFirstChild().getFirstChild().getFirstChild();
            if (result_node == null) {
                return UNKNOWN;
            }
            return new Integer(result_node.getNodeValue()).intValue();
        }
        catch (Exception ex) {
            System.err.println("SDH Monitoring: SOAP error");
            ex.printStackTrace();
        	return UNKNOWN;
        }
    }
	 /**
     * Update reservation status in database  
     * 
     */		
	public void updateReservationsDBStatus()
	{
		//Get all the reservation from the DomainManager
		Map<String, IntradomainPath> allres = dm.getAllResvMapping();
		Set<String> allKeys = allres.keySet();
		for(String s: allKeys)
		{
			String res_int = s; //Integer.parseInt(s);
			if(!saveReservationStatusDB(res_int,checkSdhPathStatusSOAP(s)))
				{
				System.err.println("SDH Monitoring: Failed to save reservation status");
				}
		}
	}
	private boolean saveReservationStatusDB(String res, int st)
	{
		// Call the function at the IDM through the Dm2Idm interface
        Dm2Idm idm = new Dm2IdmClient(idmAddress);
        return idm.saveReservationStatusDB(res, st);
	}
}