package net.geant2.jra3.resourcesreservationcalendar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.WebServiceClient;

import net.geant2.jra3.edugain.WSSecurity;

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

		try {
			WSSecurity.configureEndpoint(res);
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
        
        return res;
    }
    

}
