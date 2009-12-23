package net.geant2.jra3.gui;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @author Michal
 */

@WebServiceClient(name = "GuiService", 
		targetNamespace = "http://gui.jra3.geant2.net/", wsdlLocation = "file:wsdl/gui.wsdl")
class GuiService {
	
	private final static QName SERVICE = new QName("http://gui.jra3.geant2.net/", "GuiService");
    private final static QName GuiPort = new QName("http://gui.jra3.geant2.net/", "GuiPort");
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
        return service.getPort(Gui.class);
    }
}
