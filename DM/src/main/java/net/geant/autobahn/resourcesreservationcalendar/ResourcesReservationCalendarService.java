package net.geant.autobahn.resourcesreservationcalendar;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.WebServiceClient;
import javax.xml.xpath.XPathException;

import org.apache.log4j.Logger;

import net.geant.autobahn.edugain.WSSecurity;

/**
 * Factory for creating web services client of ResourcesReservationCalendar service. 
 * 
 * @author Kostas
 */

@WebServiceClient(name = "resourcesreservationcalendar", 
        targetNamespace = "http://resourcesreservationcalendar.autobahn.geant.net/", 
        wsdlLocation = "file:wsdl/resourcesreservationcalendar.wsdl")
class ResourcesReservationCalendarService {
	private final static QName ResourcesReservationCalendarPort = new QName("http://resourcesreservationcalendar.autobahn.geant.net/", "ResourcesReservationCalendarPort");
    public final static QName SERVICE = new QName("http://resourcesreservationcalendar.autobahn.geant.net/", "ResourcesReservationCalendarService");
    private Service service;
	private final static Logger log = Logger.getLogger(ResourcesReservationCalendarService.class);

    
    public ResourcesReservationCalendarService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(ResourcesReservationCalendarPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "ResourcesReservationCalendarPort")
    public ResourcesReservationCalendar getResourcesReservationCalendarPort() {
    	ResourcesReservationCalendar res = service.getPort(ResourcesReservationCalendar.class);

        WSSecurity calendar = null;
        try {
            calendar = new WSSecurity("etc/security");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        calendar.setClientTimeout(res);
        
        try {
            calendar.configureEndpoint(res);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            log.error("I/O exception : " + e.getMessage());
        } catch (Exception e) {
            log.error("General exception: " + e.getMessage());
        } catch (Throwable e) {
            log.error("Exception: " + e.getMessage());
        }   	
        
        return res;
    }
    

}
