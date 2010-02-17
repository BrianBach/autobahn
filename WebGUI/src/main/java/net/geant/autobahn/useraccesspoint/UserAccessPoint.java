
package net.geant.autobahn.useraccesspoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF (incubator) 2.0.4-incubator
 * Mon Mar 02 15:39:33 CET 2009
 * Generated source version: 2.0.4-incubator
 * 
 */

@WebService(targetNamespace = "http://useraccesspoint.autobahn.geant.net/", name = "UserAccessPoint")

public interface UserAccessPoint {

    @WebResult(name = "Possibility", targetNamespace = "")
    @RequestWrapper(localName = "checkReservationPossibility", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.CheckReservationPossibility")
    @ResponseWrapper(localName = "checkReservationPossibilityResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.CheckReservationPossibilityResponse")
    @WebMethod
    public boolean checkReservationPossibility(
        @WebParam(name = "request", targetNamespace = "")
        net.geant.autobahn.useraccesspoint.ReservationRequest request
    ) throws UserAccessPointException_Exception;

    @WebResult(name = "Ports", targetNamespace = "")
    @RequestWrapper(localName = "getDomainClientPorts", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetDomainClientPorts")
    @ResponseWrapper(localName = "getDomainClientPortsResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetDomainClientPortsResponse")
    @WebMethod
    public java.util.List<java.lang.String> getDomainClientPorts();

    @WebResult(name = "serviceID", targetNamespace = "")
    @RequestWrapper(localName = "submitService", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.SubmitService")
    @ResponseWrapper(localName = "submitServiceResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.SubmitServiceResponse")
    @WebMethod
    public java.lang.String submitService(
        @WebParam(name = "request", targetNamespace = "")
        net.geant.autobahn.useraccesspoint.ServiceRequest request
    ) throws UserAccessPointException_Exception;

    @WebResult(name = "Domains", targetNamespace = "")
    @RequestWrapper(localName = "getAllDomains", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetAllDomains")
    @ResponseWrapper(localName = "getAllDomainsResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetAllDomainsResponse")
    @WebMethod
    public java.util.List<java.lang.String> getAllDomains();

    @RequestWrapper(localName = "modifyReservation", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.ModifyReservation")
    @ResponseWrapper(localName = "modifyReservationResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.ModifyReservationResponse")
    @WebMethod
    public void modifyReservation(
        @WebParam(name = "request", targetNamespace = "")
        net.geant.autobahn.useraccesspoint.ModifyRequest request
    );

    @RequestWrapper(localName = "cancelService", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.CancelService")
    @ResponseWrapper(localName = "cancelServiceResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.CancelServiceResponse")
    @WebMethod
    public void cancelService(
        @WebParam(name = "serviceID", targetNamespace = "")
        java.lang.String serviceID
    ) throws UserAccessPointException_Exception;

    @WebResult(name = "Links", targetNamespace = "")
    @RequestWrapper(localName = "getAllLinks", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetAllLinks")
    @ResponseWrapper(localName = "getAllLinksResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetAllLinksResponse")
    @WebMethod
    public java.util.List<java.lang.String> getAllLinks();

    @WebResult(name = "Ports", targetNamespace = "")
    @RequestWrapper(localName = "getAllClientPorts", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetAllClientPorts")
    @ResponseWrapper(localName = "getAllClientPortsResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.GetAllClientPortsResponse")
    @WebMethod
    public java.util.List<java.lang.String> getAllClientPorts();

    @WebResult(name = "serviceID", targetNamespace = "")
    @RequestWrapper(localName = "submitServiceAndRegister", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.SubmitServiceAndRegister")
    @ResponseWrapper(localName = "submitServiceAndRegisterResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.SubmitServiceAndRegisterResponse")
    @WebMethod
    public java.lang.String submitServiceAndRegister(
        @WebParam(name = "request", targetNamespace = "")
        net.geant.autobahn.useraccesspoint.ServiceRequest request,
        @WebParam(name = "url", targetNamespace = "")
        java.lang.String url
    ) throws UserAccessPointException_Exception;

    @WebResult(name = "ServiceResponse", targetNamespace = "")
    @RequestWrapper(localName = "queryService", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.QueryService")
    @ResponseWrapper(localName = "queryServiceResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.QueryServiceResponse")
    @WebMethod
    public net.geant.autobahn.useraccesspoint.ServiceResponse queryService(
        @WebParam(name = "serviceID", targetNamespace = "")
        java.lang.String serviceID
    ) throws UserAccessPointException_Exception;

    @RequestWrapper(localName = "registerCallback", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.RegisterCallback")
    @ResponseWrapper(localName = "registerCallbackResponse", targetNamespace = "http://useraccesspoint.autobahn.geant.net/", className = "net.geant.autobahn.useraccesspoint.RegisterCallbackResponse")
    @WebMethod
    public void registerCallback(
        @WebParam(name = "serviceID", targetNamespace = "")
        java.lang.String serviceID,
        @WebParam(name = "url", targetNamespace = "")
        java.lang.String url
    );
}