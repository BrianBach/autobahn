package net.geant.autobahn.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

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
