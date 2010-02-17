package net.geant.autobahn.administration;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.WebParam;
import java.util.List;

import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;


/**
 * @author Michal
 */

@WebService(targetNamespace = "http://administration.autobahn.geant.net/", name = "Administration")
public interface Administration {
	
	@WebResult(name="Properties")
	List<KeyValue> getProperties();
	
	void setProperties(@WebParam(name="properties")List<KeyValue> properties);
		
	@WebResult(name="Reservation")
	Reservation getReservation(@WebParam(name="resID")String resID);
	
	@WebResult(name="log")
	String getLog(@WebParam(name="all")boolean all);
		
	@WebResult(name="links")
	List<Link> getTopology();
	
	void setTopology(@WebParam(name="links")List<Link> links);
	
	@WebResult(name="serivces")
	List<Service> getServices();
	
	@WebResult(name="service")
	Service getService(String serviceId);
	
	@WebResult(name="status")
	Status getStatus();
	
	@WebMethod
	void cancelAllServices();
}
