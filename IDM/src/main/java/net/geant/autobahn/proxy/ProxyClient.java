/**
 * 
 */
package net.geant.autobahn.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mortbay.log.Log;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.proxy.Proxy;
import net.geant.autobahn.proxy.ProxyService;

/**
 * @author Michal
 * @modified Johnies Zaoudis
 * 
 * Communicates with a Proxy instance through web services.
 * 
 */
public class ProxyClient implements Proxy {
    
    private Proxy rc = null;
    
    public ProxyClient() throws IOException {
        
        String endPoint = AccessPoint.getInstance().getProperty("proxy.address");
        if("none".equals(endPoint))
            return;
        
        ProxyService service = new ProxyService(endPoint);
        rc = service.getProxyPort();
    }
    
    /**
     * Creates an instance sending requests to a given endpoint.
     * 
     * @param endPoint URL address of a IDM
     */
    public ProxyClient(String endPoint) {
        if("none".equals(endPoint))
            return;
        
        ProxyService service = new ProxyService(endPoint);
        rc = service.getProxyPort();
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#cancelReservation(java.lang.String)
     */
    public void cancelReservation(String resID) throws IOException {//throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            rc.cancelReservation(resID);
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#createReservation(net.geant2.jra3.proxy.ReservationInfo)
     */
    public ReservationInfo createReservation(ReservationInfo resInfo) throws IOException {
        if(rc != null) {
            Log.debug("HI 400");
            return rc.createReservation(resInfo);
        }
         return null;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#Notify(net.geant2.jra3.proxy.ReservationInfo)
     */
    public void Notify(ReservationInfo resInfo) throws IOException {//throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            rc.Notify(resInfo);
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#getTopology()
     */
    public List<Link> getTopology() throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            List<Link> res = rc.getTopology();
            if (res==null) {
                res = new ArrayList<Link>();
            }
            return res;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#listReservations()
     */
    public List<ReservationInfo> listReservations() throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            List<ReservationInfo> res = rc.listReservations();
            if (res==null) {
                res = new ArrayList<ReservationInfo>();
            }
            return res;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#modifyReservation(net.geant2.jra3.proxy.ReservationInfo)
     */
    public boolean modifyReservation(ReservationInfo resInfo) throws IOException {
        if(rc != null)
            return rc.modifyReservation(resInfo);
        
         return false;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#queryReservation(java.lang.String)
     */
    public ReservationInfo queryReservation(String resID) throws IOException {
        if(rc != null)
            return rc.queryReservation(resID);
        
         return null;
    }
    
}