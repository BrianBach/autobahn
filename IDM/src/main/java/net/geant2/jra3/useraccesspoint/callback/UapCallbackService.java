package net.geant2.jra3.useraccesspoint.callback;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

@WebServiceClient(name = "UapCallbackService", 
		targetNamespace = "http://callback.useraccesspoint.jra3.geant2.net/", wsdlLocation = "file:wsdl/uap_callback.wsdl")
public class UapCallbackService {
	
	private final static QName SERVICE = new QName("http://callback.useraccesspoint.jra3.geant2.net/", "UapCallbackService");
    private final static QName UapCallbackPort = new QName("http://callback.useraccesspoint.jra3.geant2.net/", "UapCallbackPort");
    private Service service;
	
	public UapCallbackService(String endPoint) {
		service = Service.create(SERVICE);
		service.addPort(UapCallbackPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}

	/**
	 * 
	 * @return
	 */
    @WebEndpoint(name = "UapCallbackPort")
    public UapCallback getUapCallbackPort() {
        return service.getPort(UapCallback.class);
    }
}
