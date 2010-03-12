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
    //@WebServiceRef(wsdlLocation="http://150.140.8.14:8080/autobahn/proxy?wsdl")
    ProxyService service;
    public ProxyClient() {

        String endPoint = "http://150.140.8.14:8080/autobahn/proxy";//ProxyServlet.getProperties().getProperty("idm.address");
        if("none".equals(endPoint))
            return;
        System.out.println("CLIENT 1001");
        //QName serv = new QName("http://proxy.autobahn.geant.net/", "ProxyService");
        //URL baseUrl = null;
        //try {
          //  baseUrl = new URL("http://150.140.8.10/test/proxy/proxy.wsdl");
        //} catch (MalformedURLException e) {
            // TODO Auto-generated catch block
          //  e.printStackTrace();
        //}
        //System.out.println("url " + baseUrl);
        service = new ProxyService(endPoint);
        System.out.println("CLIENT 1011");
        rc = service.getProxyPort();
        System.out.println("CLIENT 1021");
        //int port = Integer.valueOf(ProxyServlet.getProperties().getProperty("idm.port"));
        //sock = new Socket(endPoint, port);

        //output = new DataOutputStream(sock.getOutputStream());
        //input = new DataInputStream(sock.getInputStream());
        //System.out.println("proxy client - " + endPoint + ":" + port);
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
    public void cancelReservation(String resID) throws IOException {//throws IOException {
        if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            rc.cancelReservation(resID);
           /* if (res==null) {
                res = new ArrayList<GenericLink>();
            }*/
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
            System.out.println("CLIENT 105");
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            List<Link> res = rc.getTopology();
            System.out.println("CLIENT 106");
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
           /* if (res==null) {
                res = new ArrayList<GenericLink>();
            }*/
        }
    }
    
    //private Socket sock;
    //private DataOutputStream output;
    //private DataInputStream input;
    /*
    public ProxyClient() throws IOException {

        String endPoint = ProxyServlet.getProperties().getProperty("idm.address");
        int port = Integer.valueOf(ProxyServlet.getProperties().getProperty("idm.port"));
        sock = new Socket(endPoint, port);

        output = new DataOutputStream(sock.getOutputStream());
        input = new DataInputStream(sock.getInputStream());
        System.out.println("proxy client - " + endPoint + ":" + port);
    }

    public ProxyClient(String endPoint, int port) throws IOException {

        sock = new Socket(endPoint, port);
        output = new DataOutputStream(sock.getOutputStream());
        input = new DataInputStream(sock.getInputStream());
        System.out.println("proxy client - " + endPoint + ":" + port);
    }*/

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#cancelReservation(java.lang.String)
     
    public void cancelReservation(String resID) throws IOException {

        output.writeInt(Proxy.PROXY_CANCEL_RESERVATION);
        //output.writeBytes(resID);
        output.writeUTF(resID);
        this.close();
    }*/

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#createReservation(net.geant.autobahn.proxy.ReservationInfo)
    
    public ReservationInfo createReservation(ReservationInfo resInfo) throws IOException {

        ReservationInfo resvInfo = null;
        output.writeInt(Proxy.PROXY_CREATE_RESEVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);

        ObjectInputStream ois = new ObjectInputStream(input);
        try {
            resvInfo = (ReservationInfo) ois.readObject();
        } catch (ClassNotFoundException e) {
        }
        oos.close();
        ois.close();
        this.close();
        return resvInfo;
    } */

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#getTopology()
     
    public List<Link> getTopology() throws IOException {

        List<Link> links = new ArrayList<Link>();

        output.writeInt(Proxy.PROXY_GET_TOPOLOGY);
        int numLinks = input.readInt();
        ObjectInputStream ois = new ObjectInputStream(input);
        for (int i = 0; i < numLinks; i++) {

            try {
                Link l = (Link) ois.readObject();
                links.add(l);
            } catch (ClassNotFoundException e) {
            }
        }
        ois.close();
        this.close();
        return links;
    }*/

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#listReservations()
    
    public List<ReservationInfo> listReservations() throws IOException {

        List<ReservationInfo> resInfo = new ArrayList<ReservationInfo>();
        output.writeInt(Proxy.PROXY_LIST_RESERVATIONS);
        int numRes = input.readInt();
        ObjectInputStream ois = new ObjectInputStream(input);
        for (int i = 0; i < numRes; i++) {
            try {
                ReservationInfo ri = (ReservationInfo) ois.readObject();
                resInfo.add(ri);
            } catch (ClassNotFoundException e) { }
        }
        ois.close();
        this.close();
        return resInfo;
    }*/

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#modifyReservation(net.geant.autobahn.proxy.ReservationInfo)
     
    public boolean modifyReservation(ReservationInfo resInfo) throws IOException {

        boolean result = false;
        output.writeInt(Proxy.PROXY_MODIFY_RESERVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);
        result = input.readBoolean();
        oos.close();
        this.close();
        return result;
    }*/

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#queryReservation(java.lang.String)
     
    public ReservationInfo queryReservation(String resID) throws IOException {

        ReservationInfo resInfo = null;
        output.writeInt(Proxy.PROXY_QUERY_RESERVATION);
        output.writeBytes(resID);

        ObjectInputStream ois = new ObjectInputStream(input);
        try {
            resInfo = (ReservationInfo) ois.readObject();
        } catch (ClassNotFoundException e) { }

        ois.close();
        this.close();
        return resInfo;
    }*/
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#Notify(net.geant.autobahn.proxy.ReservationInfo)
     
    public void Notify(ReservationInfo resInfo) throws IOException {
        
        output.writeInt(Proxy.PROXY_MODIFY_RESERVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);
        oos.close();
        this.close();
    }*/

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     
    public void close() throws IOException {

        if (output != null) {
            output.close();
        }
        if (input != null) {
            input.close();
        }
        if (sock != null) {
            sock.close();
        }
    }*/

    /*public static void main(String[] args) throws Exception {

        ProxyClient proxy = new ProxyClient("150.140.8.14", 8000);
        //proxy.cancelReservation("test reservation1");
        //List<Link> links = proxy.getTopology();
        //System.out.println("bod  - " + links.get(0).getBodID());
        ReservationInfo ri = new ReservationInfo();
        ri.setBodID("dfdsd");
        ri.setStartPort("10.11.32.6");
        ri.setEndPort("10.10.32.1");
        ReservationInfo r = proxy.createReservation(ri);
        System.out.println("from proxy - " + r.getBodID());

        System.out.println("program exit...");
    }*/
    
    /*private Server startServer() throws Exception {
        String port = "8080";
        final String prefix = "http://localhost:" + port + "/autobahn/";
        Server server = null;
        String sname = "proxyserviceimpl.service";
        String implName = "service.impl." + sname;
        Class impl = null;
        try {
            impl = Class.forName(implName);
        } catch (Exception e) {
            System.out.println("could not find class for " + sname);
            //continue;
        }
        
        Endpoint point = Endpoint.publish(prefix + sname, impl
                .newInstance());
               
        //manager probably is not needed
        return server;
    }*/
    
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

/*johnies
import java.io.Closeable;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;

import net.geant.autobahn.main.ProxyServlet;
import net.geant.autobahn.network.Link;

/**
 * @author Michal
 *
 *//*johnies
public class ProxyClient implements Closeable, Proxy {

    private Socket sock;
    private DataOutputStream output;
    private DataInputStream input;

    public ProxyClient() throws IOException {

        String endPoint = ProxyServlet.getProperties().getProperty("idm.address");
        int port = Integer.valueOf(ProxyServlet.getProperties().getProperty("idm.port"));
        sock = new Socket(endPoint, port);

        output = new DataOutputStream(sock.getOutputStream());
        input = new DataInputStream(sock.getInputStream());
        System.out.println("proxy client - " + endPoint + ":" + port);
    }

    public ProxyClient(String endPoint, int port) throws IOException {

        sock = new Socket(endPoint, port);
        output = new DataOutputStream(sock.getOutputStream());
        input = new DataInputStream(sock.getInputStream());
        System.out.println("proxy client - " + endPoint + ":" + port);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#cancelReservation(java.lang.String)
     *//*johnies
    public void cancelReservation(String resID) throws IOException {

        output.writeInt(Proxy.PROXY_CANCEL_RESERVATION);
        //output.writeBytes(resID);
        output.writeUTF(resID);
        this.close();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#createReservation(net.geant.autobahn.proxy.ReservationInfo)
     *//*johnies
    public ReservationInfo createReservation(ReservationInfo resInfo) throws IOException {

        ReservationInfo resvInfo = null;
        output.writeInt(Proxy.PROXY_CREATE_RESEVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);

        ObjectInputStream ois = new ObjectInputStream(input);
        try {
            resvInfo = (ReservationInfo) ois.readObject();
        } catch (ClassNotFoundException e) {
        }
        oos.close();
        ois.close();
        this.close();
        return resvInfo;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#getTopology()
     *//*johnies
    public List<Link> getTopology() throws IOException {

        List<Link> links = new ArrayList<Link>();

        output.writeInt(Proxy.PROXY_GET_TOPOLOGY);
        int numLinks = input.readInt();
        ObjectInputStream ois = new ObjectInputStream(input);
        for (int i = 0; i < numLinks; i++) {

            try {
                Link l = (Link) ois.readObject();
                links.add(l);
            } catch (ClassNotFoundException e) {
            }
        }
        ois.close();
        this.close();
        return links;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#listReservations()
     *//*johnies
    public List<ReservationInfo> listReservations() throws IOException {

        List<ReservationInfo> resInfo = new ArrayList<ReservationInfo>();
        output.writeInt(Proxy.PROXY_LIST_RESERVATIONS);
        int numRes = input.readInt();
        ObjectInputStream ois = new ObjectInputStream(input);
        for (int i = 0; i < numRes; i++) {
            try {
                ReservationInfo ri = (ReservationInfo) ois.readObject();
                resInfo.add(ri);
            } catch (ClassNotFoundException e) { }
        }
        ois.close();
        this.close();
        return resInfo;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#modifyReservation(net.geant.autobahn.proxy.ReservationInfo)
     *//*johnies
    public boolean modifyReservation(ReservationInfo resInfo) throws IOException {

        boolean result = false;
        output.writeInt(Proxy.PROXY_MODIFY_RESERVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);
        result = input.readBoolean();
        oos.close();
        this.close();
        return result;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#queryReservation(java.lang.String)
     *//*johnies
    public ReservationInfo queryReservation(String resID) throws IOException {

        ReservationInfo resInfo = null;
        output.writeInt(Proxy.PROXY_QUERY_RESERVATION);
        output.writeBytes(resID);

        ObjectInputStream ois = new ObjectInputStream(input);
        try {
            resInfo = (ReservationInfo) ois.readObject();
        } catch (ClassNotFoundException e) { }

        ois.close();
        this.close();
        return resInfo;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#Notify(net.geant.autobahn.proxy.ReservationInfo)
     *//*johnies
    public void Notify(ReservationInfo resInfo) throws IOException {
        
        output.writeInt(Proxy.PROXY_MODIFY_RESERVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);
        oos.close();
        this.close();
    }

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     *//*johnies
    public void close() throws IOException {

        if (output != null) {
            output.close();
        }
        if (input != null) {
            input.close();
        }
        if (sock != null) {
            sock.close();
        }
    }

    public static void main(String[] args) throws Exception {

        ProxyClient proxy = new ProxyClient("localhost", 4010);
        //proxy.cancelReservation("test reservation1");
        //List<Link> links = proxy.getTopology();
        //System.out.println("bod  - " + links.get(0).getBodID());
        ReservationInfo ri = new ReservationInfo();
        ri.setBodID("dfdsd");
        ReservationInfo r = proxy.createReservation(ri);
        System.out.println("from proxy - " + r.getBodID());

        System.out.println("program exit...");
    }


}*/
