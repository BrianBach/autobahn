package net.geant.autobahn.proxy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
//import java.io.DataOutputStream;
//import java.io.DataInputStream;
//import java.io.ObjectOutputStream;
//import java.io.ObjectInputStream;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

//import org.apache.cxf.endpoint.Server;
//import java.net.Socket;
//import net.geant.autobahn.constraints.PathConstraints;
//import net.geant.autobahn.intradomain.IntradomainPath;
//import net.geant.autobahn.intradomain.common.GenericLink;
//import net.geant.autobahn.main.ProxyServlet;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.proxy.Proxy;
import net.geant.autobahn.proxy.ProxyService;

import javax.xml.ws.WebServiceRef;;

/**
 * @author Michal
 * @modified Johnies Zaoudis
 * 
 * Communicates with a Proxy instance through web services.
 * 
 */
public class ProxyClient implements Proxy {

    private Proxy rc = null;
    ProxyService service;
    
    public ProxyClient() {

        //TODO: Instead of hard-coded IDM read from Properties
        String endPoint = "http://150.140.8.14:8080/autobahn/proxy";//ProxyServlet.getProperties().getProperty("idm.address");
        if("none".equals(endPoint))
            return;
        service = new ProxyService(endPoint);
        rc = service.getProxyPort();
    }
    
    /**
     * Creates an instance sending requests to a given endpoint.
     * 
     * @param endPoint URL address of a Proxy
     */
    public ProxyClient(String endPoint) {
        if("none".equals(endPoint))
            return;
        
        ProxyService service = new ProxyService(endPoint);
        rc = service.getProxyPort();
        
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#cancelReservation()
     */
    public void cancelReservation(String resID) throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            rc.cancelReservation(resID);
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#createReservation(net.geant.autobahn.proxy.ReservationInfo)
     */
    public ReservationInfo createReservation(ReservationInfo resInfo) throws IOException {
        if(rc != null) {
            System.out.println("Inside create reservation");
            return rc.createReservation(resInfo);
        }
         return null;
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
     * @see net.geant.autobahn.proxy.Proxy#modifyReservation(net.geant.autobahn.proxy.ReservationInfo)
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
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#Notify(net.geant.autobahn.proxy.ReservationInfo)
     */
    public void Notify(ReservationInfo resInfo) throws IOException {//throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            rc.Notify(resInfo);
        }
    }
    
    public static void main(String[] args) throws Exception {

        ProxyClient proxy = new ProxyClient("http://150.140.8.14:8080/autobahn/proxy");
        //proxy.cancelReservation("test reservation1");
        //List<Link> links = proxy.getTopology();
        //System.out.println("bod  - " + links.get(0).getBodID());
        ReservationInfo ri = new ReservationInfo();
        ri.setBodID("dfdsd");
        ri.setStartPort("10.11.32.6");
        ri.setEndPort("10.10.32.1");
        //ReservationInfo r = proxy.createReservation(ri); 
        //System.out.println("from proxy - " + r.getBodID());
        proxy.getTopology();
        System.out.println("program exit...");
    }

}