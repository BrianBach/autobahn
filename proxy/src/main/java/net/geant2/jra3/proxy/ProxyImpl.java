/**
 * 
 */
package net.geant2.jra3.proxy;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant2.jra3.main.ProxyServlet;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.oscars.OscarsClient;
import net.geant2.jra3.oscars.notify.OscarsNotifyClient;

/**
 * @author Michal
 *
 */
public class ProxyImpl implements Proxy {

    private static Map<String, String> vlans = new HashMap<String, String>();
    

    static {
        vlans.put("10.12.32.5", "3202"); //HEA
        vlans.put("10.13.32.4", "3204"); //PIO
        vlans.put("10.11.32.6", "3203"); //ATH
        vlans.put("10.11.32.7", "3208"); //CRE
        vlans.put("10.16.32.2", "3205"); //CAR
        vlans.put("10.14.32.2", "3207"); //GAR
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#cancelReservation(java.lang.String)
     */
    public void cancelReservation(String resID) throws IOException {

        System.out.println("cancelReservation: " + resID);
        OscarsClient oscars = new OscarsClient(ProxyServlet.getProperties().getProperty("oscars.address"));

        try {
            oscars.cancelReservation(resID);
        } catch (RemoteException e) {
            System.out.println("cancelReservation: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#createReservation(net.geant2.jra3.proxy.ReservationInfo)
     */
    public ReservationInfo createReservation(ReservationInfo resInfo)
            throws IOException {

        System.out.println("Sending create reservation - " + resInfo.getBodID());
        OscarsClient oscars = new OscarsClient(ProxyServlet.getProperties().getProperty("oscars.address"));

        String src = "";

        // Old Link
        if ("10.10.32.30".equals(resInfo.getEndPort())) {
            src = "urn:ogf:network:domain=dcn.internet2.edu:node=NEWY:port=S27135:link=10.100.80.197";
        } else if ("10.10.32.29".equals(resInfo.getEndPort())) {
            // New link
            src = "urn:ogf:network:domain=dcn.internet2.edu:node=NEWY:port=S27391:link=10.100.80.201";
        } else {
            System.out.println("Warn - wrong Internet2 endport");
        }

        String dest = "urn:ogf:network:domain=dcn.internet2.edu:node=LOSA:port=S27135:link=10.100.100.9";

        String vlan = vlans.get(resInfo.getStartPort());
        if (vlan == null) {
            System.out.println("Warn - no assigned vlan found");
            vlan = "3210";
        }

        return oscars.scheduleReservation(resInfo, src, dest, vlan);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#getTopology()
     */
    public List<Link> getTopology() throws IOException {

        System.out.println("getTopology");
        OscarsClient oscars = new OscarsClient(ProxyServlet.getProperties().getProperty("oscars.address"));
        try {
            return oscars.getTopology();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#listReservations()
     */
    public List<ReservationInfo> listReservations() throws IOException {

        System.out.println("listReservations");
        OscarsClient oscars = new OscarsClient(ProxyServlet.getProperties().getProperty("oscars.address"));
        try {
            return oscars.getReservationList();
        } catch (RemoteException e) {
            throw new IOException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#modifyReservation(net.geant2.jra3.proxy.ReservationInfo)
     */
    public boolean modifyReservation(ReservationInfo resInfo)
            throws IOException {
        
        System.out.println("modifyReservation: " + resInfo.getBodID());
        OscarsClient oscars = new OscarsClient(ProxyServlet.getProperties().getProperty("oscars.address"));
        try {
            // do not support for now
            throw new RemoteException("not implemented");
            
        } catch (RemoteException e) {
            System.out.println("cancelReservation: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#queryReservation(java.lang.String)
     */
    public ReservationInfo queryReservation(String resID) throws IOException {
        
        System.out.println("queryReservation: " + resID);
        OscarsClient oscars = new OscarsClient(ProxyServlet.getProperties().getProperty("oscars.address"));
        try {
            oscars.cancelReservation(resID);
        } catch (RemoteException e) {
            System.out.println("cancelReservation: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        return null;
    }

     /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#Notify(net.geant2.jra3.proxy.ReservationInfo)
     */
    public void Notify(ReservationInfo resInfo) throws IOException {
        
        // using OSCARSNotifyClient
        System.out.println("notify: " + resInfo.getBodID());
        OscarsNotifyClient oscars = new OscarsNotifyClient(ProxyServlet.getProperties().getProperty("oscars.address"));

        try {
            oscars.Notify(resInfo);
            
        } catch (RemoteException e) {
            System.out.println("cancelReservation: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
    }
}
