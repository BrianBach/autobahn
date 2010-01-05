
package net.geant2.jra3.gui;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Interface for the GUI web service interface
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
@WebService(targetNamespace = "http://gui.jra3.geant2.net/", name = "Gui")
public interface Gui {
	
    @ResponseWrapper(targetNamespace = "http://gui.jra3.geant2.net/", className = "net.geant2.jra3.gui.StatusUpdatedResponse", localName = "statusUpdatedResponse")
    @RequestWrapper(targetNamespace = "http://gui.jra3.geant2.net/", className = "net.geant2.jra3.gui.StatusUpdated", localName = "statusUpdated")
    @WebMethod
    public void statusUpdated(
        @WebParam(targetNamespace = "", name = "idm")
        java.lang.String idm,
        @WebParam(targetNamespace = "", name = "status")
        net.geant2.jra3.administration.Status status
        
    );

    @ResponseWrapper(targetNamespace = "http://gui.jra3.geant2.net/", className = "net.geant2.jra3.gui.UpdateResponse", localName = "updateResponse")
    @RequestWrapper(targetNamespace = "http://gui.jra3.geant2.net/", className = "net.geant2.jra3.gui.Update", localName = "update")
    @WebMethod
    public void update(
        @WebParam(targetNamespace = "", name = "idm")
        java.lang.String idm,
        @WebParam(targetNamespace = "", name = "event")
        net.geant2.jra3.gui.EventType event,
        @WebParam(targetNamespace = "", name = "properties")
        java.util.List<net.geant2.jra3.administration.KeyValue> properties
    );

    @ResponseWrapper(targetNamespace = "http://gui.jra3.geant2.net/", className = "net.geant2.jra3.gui.ReservationChangedResponse", localName = "reservationChangedResponse")
    @RequestWrapper(targetNamespace = "http://gui.jra3.geant2.net/", className = "net.geant2.jra3.gui.ReservationChanged", localName = "reservationChanged")
    @WebMethod
    public void reservationChanged(
        @WebParam(targetNamespace = "", name = "idm")
        java.lang.String idm,
        @WebParam(targetNamespace = "", name = "serviceId")
        java.lang.String serviceId,
        @WebParam(targetNamespace = "", name = "resID")
        java.lang.String resID,
        @WebParam(targetNamespace = "", name = "state")
        net.geant2.jra3.gui.ReservationChangedType state,
        @WebParam(targetNamespace = "", name = "message")
        java.lang.String message
    );
}
