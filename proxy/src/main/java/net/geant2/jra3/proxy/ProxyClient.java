package net.geant2.jra3.proxy;

import java.io.Closeable;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
import net.geant2.jra3.main.ProxyServlet;
import net.geant2.jra3.network.Link;

/**
 * @author Michal
 *
 */
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
     * @see net.geant2.jra3.proxy.Proxy#cancelReservation(java.lang.String)
     */
    public void cancelReservation(String resID) throws IOException {

        output.writeInt(Proxy.PROXY_CANCEL_RESERVATION);
        //output.writeBytes(resID);
        output.writeUTF(resID);
        this.close();
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#createReservation(net.geant2.jra3.proxy.ReservationInfo)
     */
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
     * @see net.geant2.jra3.proxy.Proxy#getTopology()
     */
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
     * @see net.geant2.jra3.proxy.Proxy#listReservations()
     */
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
     * @see net.geant2.jra3.proxy.Proxy#modifyReservation(net.geant2.jra3.proxy.ReservationInfo)
     */
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
     * @see net.geant2.jra3.proxy.Proxy#queryReservation(java.lang.String)
     */
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
     * @see net.geant2.jra3.proxy.Proxy#Notify(net.geant2.jra3.proxy.ReservationInfo)
     */
    public void Notify(ReservationInfo resInfo) throws IOException {
        
        output.writeInt(Proxy.PROXY_MODIFY_RESERVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);
        oos.close();
        this.close();
    }

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     */
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


}
