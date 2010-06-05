package net.geant.autobahn.aai;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://aai.autobahn.geant.net/", name = "Authorization")
public interface Authorization {

    @WebMethod
    @WebResult(name="response")
    public Response reserveInterdomainPath(
            @WebParam(name="path") net.geant.autobahn.network.Path p, 
            @WebParam(name="user") net.geant.autobahn.reservation.User u);
    
    @WebMethod
    @WebResult(name="response")
    public Response reserveIntradomainPath(
            @WebParam(name="path") net.geant.autobahn.intradomain.IntradomainPath p, 
            @WebParam(name="user") net.geant.autobahn.reservation.User u);

    @WebMethod
    @WebResult(name="response")
    public Response monitorPath(
            @WebParam(name="path") net.geant.autobahn.network.Path p, 
            @WebParam(name="user") net.geant.autobahn.reservation.User u);

    @WebMethod
    @WebResult(name="response")
    public Response cancelReservation(
            @WebParam(name="reservation") String r, 
            @WebParam(name="user") net.geant.autobahn.reservation.User u);

    @WebMethod
    @WebResult(name="response")
    public Response modifyReservation(
            @WebParam(name="reservation") String r, 
            @WebParam(name="user") net.geant.autobahn.reservation.User u);

    @WebMethod
    @WebResult(name="response")
    public Response configureSystem(
            @WebParam(name="user") net.geant.autobahn.reservation.User u);

    @WebMethod
    @WebResult(name="response")
    public Response viewSystemLogs(
            @WebParam(name="user") net.geant.autobahn.reservation.User u);

    @WebMethod
    @WebResult(name="response")
    public Response modifyACL(
            @WebParam(name="user") net.geant.autobahn.reservation.User u);
}
