/**
 * EthMonitoring.java
 * 2007-08-30
 */

package net.geant2.jra3.intradomain.ethernet;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.geant2.jra3.dao.DmDAOFactory;
import net.geant2.jra3.dao.hibernate.DmHibernateUtil;
import net.geant2.jra3.dao.hibernate.HibernateDmDAOFactory;
import net.geant2.jra3.dm2idm.Dm2Idm;
import net.geant2.jra3.dm2idm.Dm2IdmClient;
import net.geant2.jra3.intradomain.AccessPoint;
import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.ResourcesReservation;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
//import net.geant2.jra3.intradomain.converter.TopologyConverter;
import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.StateOper;
import net.geant2.jra3.topologyabstraction.TopologyAbstraction;
import net.geant2.jra3.topologyabstraction.TopologyAbstractionClient;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import net.geant2.jra3.constraints.ConstraintsNames;

/**
 * Monitors an Ethernet topology for the status of Ethernet links. 
 *  
 * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
 */
public class EthMonitoring implements Runnable {

	/**
	 * @param args
	 */
	
	//Status of an Ethernet link
	private static int UNKNOWN=0;
	private static int UP=1;
	private static int DEGRADED=2;
	private static int DOWN=3;
    
    //Access to database
    private Session session = null;
    
	//Deamon parameters
	private  static String MDsleepTimePr =  "MDsleepTime";
	private int sleepTime;
	private Thread deamonThread = null;
	
	//SSH access to server parameters
	private  static String DomainPr = "domain";
    private  static String EthernetStatusServerPr = "EthernetStatusServer";
    private   String Domain;
    private   String EthernetStatusServer;
    
    private   String idmAddress;
    
	private String taAddress = null;
	private List<SpanningTree> sTrees = null;
	private List<EthPhysicalPort> ethpp = null;
	private ResourcesReservation dm = null;
	
    /**
     * This class is used to store the status of an Ethernet link during the monitoring procedure of the various circuits 
     * 
     * 
     */
	private class Status {

		private int currentStatus;

        public Status()
        {
        	currentStatus = UNKNOWN;
        }
        
        public int getStatus()
        {
        	return currentStatus;
        }
        
        public void setStatus(int status)
        {
        	currentStatus = status;
        }
        
        public void addStatus(int status)
        {
        	if(status>currentStatus)
        	{
        		currentStatus=status;
        	}
        }
    }
    
    /**
     * Constructor for the class
     * 
     * @param EthCon: The Converter which represents the Ethernet Topology which will be monitored 
     */
	public EthMonitoring(AccessPoint ap, IntradomainTopology topology) 
	{
        EthernetStatusServer = ap.getProperty(EthernetStatusServerPr);
		sleepTime = Integer.parseInt(ap.getProperty(MDsleepTimePr));
		Domain = ap.getProperty(DomainPr);
		dm = ap.getDomainManager();
		taAddress = dm.getTopologyAbstractionAddress();
		
        sTrees = topology.getSpanningTrees();
        ethpp = topology.getEthPhysicalPorts();
        idmAddress = ap.getProperty("idm.address");
		this.setSession(DmHibernateUtil.getInstance().currentSession());
	}
	
	/**
     * Set session for database access
     * 
     * @param session: Database identifier 
     */
    private void setSession(Session session) {
        this.session = session;
    }
    
	/**
     * Start the monitoring deamon
     * 
     * @param EthCon: The Converter which represents the Ethernet Topology which will be monitored 
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
            System.err.println("Ethernet Monitoring Deamon's initial long sleep ("+sleepTime*3/1000+" sec.) was interrupted prematurely, will continue now");
            e.printStackTrace();
        }
        
    	while (true) 
    	{
    		try {
                // Make sure we have the current DB session
    		    this.setSession(DmHibernateUtil.getInstance().currentSession());             
    			updateReservationsDBStatus();
    			updateEdgeLinksDBStatus();
    			Thread.sleep(sleepTime);
    		}
    		catch (InterruptedException x) {
    			System.err.println("Ethernet Monitoring Deamon's sleep ("+sleepTime/1000+" sec.) was interrupted prematurely, will continue now");
                x.printStackTrace();
    		}
            catch (Exception e) {
                System.err.println("Stopped Ethernet Monitoring Deamon due to exception:");
                e.printStackTrace();
                break;
            }
    	}
	}
    
	/**
     * Returns the mapping of the a General link to an Ethernet links 
     * 
     * @param glink: The General link
     * 
     */		
	private EthLink getEthLinkFromGenericLink(GenericLink glink)
	{
		try
		{
			for (SpanningTree st : sTrees) 
			{
				if (glink==st.getEthLink().getGenericLink()) return st.getEthLink();
			}
		}
		catch (Exception e)
		{
			System.err.println("EthMonitoring: No EthernetTopology Converver as expected");
		}
		return null;
	}
    
	/**
     * Returns the Node name of an Generic Interface
     * Returns null if node was not found
     * 
     * @param intf: The Generic Interface  
     * 
     */	
	private String getNodeNameFromInt (GenericInterface intf)
	{
        Node n = intf.getNode();
        if (n!=null) {
            return n.getName();
        }
		return null;
	}
    
	/**
     * Returns the Ethernet Interface name from an Generic Interface  
     * Returns null if physical port not found
     * 
     * @param intf: The Generic Interface  
     * 
     */		
	private String getIntNameFromInt (GenericInterface intf)
	{
        EthPhysicalPort epp = getEthppFromInt(intf);
        if (epp!=null) {
            return epp.getInterfaceName();
        }
		return null;
	}
    
	/**
     * Returns the Ethernet Physical Port from an Generic Interface  
     * 
     * @param intf: The Generic Interface  
     * 
     */		
	private EthPhysicalPort getEthppFromInt (GenericInterface intf)
	{
		try
		{
			for (EthPhysicalPort eth : ethpp) 
			{
				if (intf==eth.getGenericInterface()) return eth;			
			}
		}
		catch (Exception e)
		{
			System.err.println("EthMonitoring: No Ethernet Physical Port Found as expected");
		}
		return null;
	}
  
    /**
     * Check the status of an Ethernet Interface using SOAP call 
     * Here is an example XML message that will be sent to the SOAP server:
     * 
     * <?xml version="1.0" encoding="UTF-8"?>
     *  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
     *  <soapenv:Body>
     *      <checkEthStatusNS:checkEthInterfaceStatus xmlns:checkEthStatusNS="http://www.geant2.net/jra3/EthMonitoring">
     *          <checkEthStatusNS:node>testn</checkEthStatusNS:node>
     *          <checkEthStatusNS:intf>testintf</checkEthStatusNS:intf>
     *          <checkEthStatusNS:vlan>1</checkEthStatusNS:vlan>
     *      </checkEthStatusNS:checkEthInterfaceStatus>
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
     * @param node: The node name  
     * @param intf: The interface name
     * @param vlan: The VLAN number
     */
    private int checkEthInterfaceStatusSOAP(String node, String intf, long vlan)
    {
        String xmlString2 = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
            "<SOAP-ENV:Envelope \n" +
            "       xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" \n" +
            "       xmlns:tns=\"http://www.geant2.net/jra3/EthMonitoring\">\n" +
            "   <SOAP-ENV:Body>\n" +
            "       <tns:checkEthInterfaceStatus>\n"+
            "           <tns:node>" + node + "</tns:node>\n" +
            "           <tns:intf>" + intf + "</tns:intf>\n" +
            "           <tns:vlan>" + vlan + "</tns:vlan>\n" +
            "       </tns:checkEthInterfaceStatus>\n" +
            "   </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";
        
        try {
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage smsg =
                    mf.createMessage(new MimeHeaders(), new ByteArrayInputStream(xmlString2.getBytes()));
            
            // Add a dummy SOAPAction header (Axis requires SOAPAction header to be set!)
            smsg.getMimeHeaders().addHeader("SOAPAction",""); 
          
            SOAPConnection conn = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = conn.call(smsg, EthernetStatusServer);

            // The XML structure is:
            // Body
            // |--checkEthInterfaceStatusResponse tag
            //    |--result tag
            //       |--result value
            org.w3c.dom.Node result_node = response.getSOAPBody().getFirstChild().getFirstChild().getFirstChild();
            if (result_node == null) {
                return UNKNOWN;
            }
            return new Integer(result_node.getNodeValue()).intValue();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return UNKNOWN;
        }
    }
	/**
     * Save status of a Link to the database   
     * 
     * @param link: Link
     * @param st: Link status  
     */		
	private boolean saveLinkStatusDB(Link link, int st)
	{
        
		Transaction t = session.beginTransaction();
		DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
		List<Link> tmplinks = daos.getLinkDAO().getAll();
		for(Link ln:tmplinks)
		{
			//Link found in the db
			if(ln.equals(link)) {
				ln.setOperationalState(new StateOper(st));
                ln.setTimestamp(new Date()); // needed for monitoring GUI
                session.update(ln);
				t.commit();
				return true;
			}	
		}
        link.setOperationalState(new StateOper(st));
        link.setTimestamp(new Date()); // needed for monitoring GUI
        session.save(link);
        t.commit();
		return true;
	}
   
    /**
     * Update status of all reservations  
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
            saveReservationStatusDB(res_int,checkReservationStatus(s));
		}
	}
	/**
     * Update reservation status in database  
     * 
     */	
	private boolean saveReservationStatusDB(String res, int st)
	{
        // Call the function at the IDM through the Dm2Idm interface
        Dm2Idm idm = new Dm2IdmClient(idmAddress);
        return idm.saveReservationStatusDB(res, st);
	}
    
	/**
     * Update status of all interdomain links  
     * 
     */		
	public void updateEdgeLinksDBStatus()
	{
        TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
		Set<Link> links  = ta.getAllEdgeLinks();
        for (Link ln: links)
		{
			if(ln.getStartDomainID().equals(Domain)||ln.getEndDomainID().equals(Domain))
				saveLinkStatusDB(ln, checkInterdomainLinkStatus(ln));
		}	
	}
	/**
     * Check the status of an inter-domain link in abstract topology   
     * @param link: The link  
     */		
	public int checkInterdomainLinkStatus(Link link)
	{
		Status status = new Status();
		if (link.isVirtual())
		{
			System.err.println("EthMonitoring: Error Link is Virtual as expected");
			return status.getStatus();
		}
		else
		{
            TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
			GenericLink glink = ta.getEdgeLink(link);
			status.addStatus(checkGenericLinkStatus(glink,-1));
		}
		return status.getStatus();
	}
    
	/**
     * Check the status of an intar-domain (virtual) link in abstract topology   
     * 
     * @param resID: The reservation ID  
     */		
	public int checkReservationStatus(String resID)
	{
		int vlan = -1;
		try
		{
			vlan = dm.getResvParams(resID).getPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue();
		}
		catch (Exception e)
		{
			System.out.println("EthMonitoring: I cannot export vlan from reservation");
		}
		Status status = new Status();
		List<GenericLink> gls = dm.getResvMapping(resID);

		for (GenericLink gl: gls)
		{
			if(!gl.isInterdomain())status.addStatus(checkGenericLinkStatus(gl, vlan));
		}
		return status.getStatus();
	}
	/**
     * Check the status of an Generic link    
     * 
     * @param glink: The Generic link  
     */	
	private int checkGenericLinkStatus(GenericLink glink, int vlan)
	{
		
		Status status = new Status();
		EthLink elink = getEthLinkFromGenericLink(glink);
		if(elink!=null)
		{
			status.addStatus(checkEthLinkStatus(elink, vlan));
		}
		else
		{
			status.addStatus(checkNoEthLinkStatus(glink, vlan));
		}
		return status.getStatus();
	}
    
	/**
     * Check the status of an non Ethernet link    
     * 
     * @param glink: The Generic link  
     */		
	private int checkNoEthLinkStatus(GenericLink glink, int vlan)
	{
		Status status = new Status();
		System.err.println("EthMonitoring: No ethernet Link as expected");
		return status.getStatus();
	}
    
	/**
     * Check the status of an  Ethernet link    
     * 
     * @param elink: The Ethernet link  
     */		
	private int checkEthLinkStatus(EthLink elink, int vlan)
	{
		Status status = new Status();
		GenericInterface sint = elink.getGenericLink().getStartInterface();
		GenericInterface eint = elink.getGenericLink().getEndInterface();
        
        if (sint==null) {
            System.err.println("EthMonitoring: Could not get start generic interface for link "+elink.getGenericLink().getLinkId());
            return UNKNOWN;
        }
        if (eint==null) {
            System.err.println("EthMonitoring: Could not get end generic interface for link "+elink.getGenericLink().getLinkId());
            return UNKNOWN;
        }

        //Check the status of each interface of Ethernet Link
		if(sint.getDomainId().equals(Domain))
		{
            String sintName = getIntNameFromInt(sint);
            if (sintName == null) {
                System.err.println("EthMonitoring: Could not check generic interface "+sint.getName()+", no ethernet physical interface found!");
                return UNKNOWN;
            }
            String sintNodeName = getNodeNameFromInt(sint);
            if (sintNodeName == null) {
                System.err.println("EthMonitoring: Could not check generic interface "+sint.getName()+", no node found!");
                return UNKNOWN;
            }
			status.addStatus(checkEthInterfaceStatusSOAP(sintNodeName, sintName, vlan));
		}
		if(eint.getDomainId().equals(Domain))
		{
            String eintName = getIntNameFromInt(eint);
            if (eintName == null) {
                System.err.println("EthMonitoring: Could not check generic interface "+eint.getName()+", no ethernet physical interface found!");
                return UNKNOWN;
            }
            String eintNodeName = getNodeNameFromInt(eint);
            if (eintNodeName == null) {
                System.err.println("EthMonitoring: Could not check generic interface "+eint.getName()+", no node found!");
                return UNKNOWN;
            }
			status.addStatus(checkEthInterfaceStatusSOAP(eintNodeName, eintName, vlan));
		}
		return status.getStatus();
	}
}