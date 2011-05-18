package net.geant.autobahn.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.xpath.XPathException;

import org.apache.log4j.Logger;

import net.geant.autobahn.edugain.WSSecurity;

/**
 * @author Michal
 */

@WebServiceClient(name = "GuiService", 
		targetNamespace = "http://gui.autobahn.geant.net/", wsdlLocation = "file:wsdl/gui.wsdl")
class GuiService {
	
	private final static QName SERVICE = new QName("http://gui.autobahn.geant.net/", "GuiService");
    private final static QName GuiPort = new QName("http://gui.autobahn.geant.net/", "GuiPort");
    private Service service;
	private final static Logger log = Logger.getLogger(GuiService.class);

	
	public GuiService(String endPoint) {
		service = Service.create(SERVICE);
		service.addPort(GuiPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}
	
    /**
     * 
     * @return
     *     returns Idm2DmPort
     */
    @WebEndpoint(name = "GuiPort")
    public Gui getGuiPort() {
        
    	Gui port = service.getPort(Gui.class);
    	
        WSSecurity gui = null;
        try {
            gui = new WSSecurity("etc/security");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        gui.setClientTimeout(port);
        
        try {
            gui.configureEndpoint(port);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            log.error("I/O exception : " + e.getMessage());
        } catch (Exception e) {
            log.error("General exception: " + e.getMessage());
        } catch (Throwable e) {
            log.error("Exception: " + e.getMessage());
        }   
        
		return port;
    }
}
