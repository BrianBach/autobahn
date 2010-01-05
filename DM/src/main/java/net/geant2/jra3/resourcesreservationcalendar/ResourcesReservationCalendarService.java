package net.geant2.jra3.resourcesreservationcalendar;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.WebServiceClient;

/**
 * Factory for creating web services client of ResourcesReservationCalendar service. 
 * 
 * @author Kostas
 */

@WebServiceClient(name = "resourcesreservationcalendar", 
        targetNamespace = "http://resourcesreservationcalendar.jra3.geant2.net/", 
        wsdlLocation = "file:wsdl/resourcesreservationcalendar.wsdl")
class ResourcesReservationCalendarService {
	private final static QName ResourcesReservationCalendarPort = new QName("http://resourcesreservationcalendar.jra3.geant2.net/", "ResourcesReservationCalendarPort");
    public final static QName SERVICE = new QName("http://resourcesreservationcalendar.jra3.geant2.net/", "ResourcesReservationCalendarService");
    private Service service;
    
    public ResourcesReservationCalendarService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(ResourcesReservationCalendarPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "ResourcesReservationCalendarPort")
    public ResourcesReservationCalendar getResourcesReservationCalendarPort() {
    	ResourcesReservationCalendar res = service.getPort(ResourcesReservationCalendar.class);
        
        WSSecurity.setClientTimeout(res);
        
        return res;
    }
    

}
