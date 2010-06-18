package net.geant.autobahn.tool;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.5
 * Fri Jun 18 15:05:42 CEST 2010
 * Generated source version: 2.2.5
 * 
 */
 
@WebService(targetNamespace = "http://tool.autobahn.geant.net/", name = "Tool")
@XmlSeeAlso({net.geant.autobahn.constraints.ObjectFactory.class,ObjectFactory.class,net.geant.autobahn.intradomain.common.ObjectFactory.class,net.geant.autobahn.reservation.ObjectFactory.class})
public interface Tool {

    @RequestWrapper(localName = "addReservation", targetNamespace = "http://tool.autobahn.geant.net/", className = "net.geant.autobahn.tool.AddReservation")
    @ResponseWrapper(localName = "addReservationResponse", targetNamespace = "http://tool.autobahn.geant.net/", className = "net.geant.autobahn.tool.AddReservationResponse")
    @WebMethod
    public void addReservation(
        @WebParam(name = "resID", targetNamespace = "")
        java.lang.String resID,
        @WebParam(name = "links", targetNamespace = "")
        java.util.List<net.geant.autobahn.intradomain.common.GenericLink> links,
        @WebParam(name = "params", targetNamespace = "")
        net.geant.autobahn.reservation.ReservationParams params
    ) throws ResourceNotFoundException_Exception, RequestException_Exception, AAIException_Exception, SystemException_Exception;

    @RequestWrapper(localName = "removeReservation", targetNamespace = "http://tool.autobahn.geant.net/", className = "net.geant.autobahn.tool.RemoveReservation")
    @ResponseWrapper(localName = "removeReservationResponse", targetNamespace = "http://tool.autobahn.geant.net/", className = "net.geant.autobahn.tool.RemoveReservationResponse")
    @WebMethod
    public void removeReservation(
        @WebParam(name = "resID", targetNamespace = "")
        java.lang.String resID,
        @WebParam(name = "links", targetNamespace = "")
        java.util.List<net.geant.autobahn.intradomain.common.GenericLink> links,
        @WebParam(name = "params", targetNamespace = "")
        net.geant.autobahn.reservation.ReservationParams params
    ) throws ReservationNotFoundException_Exception, RequestException_Exception, AAIException_Exception, SystemException_Exception;
}
