package net.geant2.jra3.idm2dm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import net.geant2.jra3.edugain.WSSecurity;

/**
 * @author Michal
 */

@WebServiceClient(name = "Idm2DmService", targetNamespace = "http://idm2dm.jra3.geant2.net/", wsdlLocation = "file:wsdl/idm2dm.wsdl")
public class Idm2DmService {

	private final static int TIMEOUT = 1000 * 60 * 5;
    private final static QName SERVICE = new QName("http://idm2dm.jra3.geant2.net/", "Idm2DmService");
    private final static QName Idm2DmPort = new QName("http://idm2dm.jra3.geant2.net/", "Idm2DmPort");
    private Service service;
    
	static public String EDUGAIN_PROPERTIES = "etc/edugain/edugain.properties";
	static public final String PROPERTY_ACTIVATED = "net.geant2.jra3.edugain.activated";
	static public String activatedStr;
    
    public Idm2DmService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(Idm2DmPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns Idm2DmPort
     */
    @WebEndpoint(name = "Idm2DmPort")
    public Idm2Dm getIdm2DmPort() {
    	Idm2Dm port = service.getPort(Idm2Dm.class); 
    	WSSecurity.setClientTimeout(port);
    	
		try {
			WSSecurity.configureEndpoint(port);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
        return port;
    }

}
