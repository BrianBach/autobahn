package net.geant.autobahn.useraccesspoint.callback;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://callback.useraccesspoint.autobahn.geant.net/", 
		name = "UapCallback")
public interface UapCallback {
	
    public void reservationScheduled(@WebParam(name="resId")String reservationId);
    
    public void reservationActive(@WebParam(name="resId")String reservationId);
    
    public void reservationFinished(@WebParam(name="resId")String reservationId);
    
    public void reservationProcessingFailed(@WebParam(name="resId")String reservationId, 
    		@WebParam(name="cause")String cause);

    public void reservationCancelled(@WebParam(name="resId")String reservationId);
    
    public void reservationModified(@WebParam(name="resId")String reservationId, 
    		@WebParam(name="success")boolean success);
    
    public void domainUp(String address);
}
