/**
 * 
 */
package net.geant.autobahn.proxy;

//import java.io.Closeable;
import java.io.IOException;
//import java.io.DataOutputStream;
//import java.io.DataInputStream;
//import java.io.ObjectOutputStream;
//import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.mortbay.log.Log;
//import java.net.Socket;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.proxy.Proxy;
import net.geant.autobahn.proxy.ProxyService;

//JOHNIES
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Calendar;

//import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
//import net.geant2.jra3.constraints.PathConstraints;
//import net.geant2.jra3.intradomain.IntradomainPath;
//import net.geant2.jra3.intradomain.common.GenericLink;

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
        //int port = Integer.valueOf(AccessPoint.getInstance().getProperty("proxy.port"));
        //sock = new Socket(endPoint, port);
        //output = new DataOutputStream(sock.getOutputStream());
        //input = new DataInputStream(sock.getInputStream());
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
           /* if (res==null) {
                res = new ArrayList<GenericLink>();
            }*/
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
           /* if (res==null) {
                res = new ArrayList<GenericLink>();
            }*/
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
    
    /*
    //private Socket sock;
    //private DataOutputStream output;
    //private DataInputStream input;
    
    public ProxyClient() throws IOException {
        
        String endPoint = AccessPoint.getInstance().getProperty("proxy.address");
        int port = Integer.valueOf(AccessPoint.getInstance().getProperty("proxy.port"));
        sock = new Socket(endPoint, port);
        output = new DataOutputStream(sock.getOutputStream());
        input = new DataInputStream(sock.getInputStream());
    }
    
    public ProxyClient(String endPoint, int port) throws IOException {
        
        sock = new Socket(endPoint, port);
        output = new DataOutputStream(sock.getOutputStream());
        input = new DataInputStream(sock.getInputStream());
    }*/
    

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#cancelReservation(java.lang.String)
     
    public void cancelReservation(String resID) throws IOException {

        output.writeInt(Proxy.PROXY_CANCEL_RESERVATION);
        //output.writeBytes(resID);
        output.writeUTF(resID);
        this.close();
    }*/

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#createReservation(net.geant2.jra3.proxy.ReservationInfo)
    
    public ReservationInfo createReservation(ReservationInfo resInfo) throws IOException {
        
        output.writeInt(Proxy.PROXY_CREATE_RESEVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);

        ReservationInfo resvInfo = null;
        ObjectInputStream ois = new ObjectInputStream(input);
        try {
            resvInfo = (ReservationInfo)ois.readObject();
        } catch (ClassNotFoundException e) { }
        oos.close();
        ois.close();
        this.close();
        return resvInfo;
    } */

    /*
     * 
     
    public void Notify(ReservationInfo resInfo) throws IOException {
        output.writeInt(PROXY_NOTIFY_RESERVATION);
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(resInfo);

        oos.close();
        this.close();
    }*/
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#getTopology()
     
    public List<Link> getTopology() throws IOException {
        
        List<Link> links = new ArrayList<Link>();
        
        output.writeInt(Proxy.PROXY_GET_TOPOLOGY);
        int numLinks = input.readInt();
        ObjectInputStream ois = new ObjectInputStream(input);
        for (int i=0; i < numLinks; i++) {
            
            try {
                Link l = (Link)ois.readObject();
                links.add(l);
            } catch (ClassNotFoundException e) { }
        }
        ois.close();
        this.close();
        return links;
    }*/

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#listReservations()
    
    public List<ReservationInfo> listReservations() throws IOException {
        
        List<ReservationInfo> resInfo = new ArrayList<ReservationInfo>();
        output.writeInt(Proxy.PROXY_LIST_RESERVATIONS);
        int numRes = input.readInt();
        ObjectInputStream ois = new ObjectInputStream(input);
        for (int i=0; i < numRes; i++) {
            try {
                ReservationInfo ri = (ReservationInfo)ois.readObject();
                resInfo.add(ri);
            } catch (ClassNotFoundException e) { }
        }
        ois.close();
        this.close();
        return resInfo;
    } */

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#modifyReservation(net.geant2.jra3.proxy.ReservationInfo)
     
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
     * @see net.geant2.jra3.proxy.Proxy#queryReservation(java.lang.String)
     
    public ReservationInfo queryReservation(String resID) throws IOException {
        
        ReservationInfo resInfo = null;
        output.writeInt(Proxy.PROXY_QUERY_RESERVATION);
        output.writeBytes(resID);
        
        ObjectInputStream ois = new ObjectInputStream(input);
        try {
            resInfo = (ReservationInfo)ois.readObject();
        } catch (ClassNotFoundException e) { }
            
        ois.close();
        this.close();
        return resInfo;
    }*/

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     
    public void close() throws IOException {
        
        if (output != null)
            output.close();
        if (input != null)
            input.close();
        if (sock != null)
            sock.close();
    }*/
    
    /*public static void main(String[] args) throws Exception {
        
        ProxyClient proxy = new ProxyClient("localhost", 4000);
        proxy.cancelReservation("test reservation1");
        
        System.out.println("program exit...");
    }*/
}

/*johniesimport java.io.Closeable;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;

import net.geant.autobahn.idm.AccessPoint;
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
		
		String endPoint = AccessPoint.getInstance().getProperty("proxy.address");
		int port = Integer.valueOf(AccessPoint.getInstance().getProperty("proxy.port"));
		sock = new Socket(endPoint, port);
		output = new DataOutputStream(sock.getOutputStream());
		input = new DataInputStream(sock.getInputStream());
	}
	
	public ProxyClient(String endPoint, int port) throws IOException {
		
		sock = new Socket(endPoint, port);
		output = new DataOutputStream(sock.getOutputStream());
		input = new DataInputStream(sock.getInputStream());
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
		
		output.writeInt(Proxy.PROXY_CREATE_RESEVATION);
		ObjectOutputStream oos = new ObjectOutputStream(output);
		oos.writeObject(resInfo);

		ReservationInfo resvInfo = null;
		ObjectInputStream ois = new ObjectInputStream(input);
		try {
			resvInfo = (ReservationInfo)ois.readObject();
		} catch (ClassNotFoundException e) { }
		oos.close();
		ois.close();
		this.close();
		return resvInfo;
	}

	/**
	 * 
	 *//*johnies
	public void Notify(ReservationInfo resInfo) throws IOException {
		output.writeInt(PROXY_NOTIFY_RESERVATION);
		ObjectOutputStream oos = new ObjectOutputStream(output);
		oos.writeObject(resInfo);

		oos.close();
		this.close();
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.proxy.Proxy#getTopology()
	 *//*johnies
	public List<Link> getTopology() throws IOException {
		
		List<Link> links = new ArrayList<Link>();
		
		output.writeInt(Proxy.PROXY_GET_TOPOLOGY);
		int numLinks = input.readInt();
		ObjectInputStream ois = new ObjectInputStream(input);
		for (int i=0; i < numLinks; i++) {
			
			try {
				Link l = (Link)ois.readObject();
				links.add(l);
			} catch (ClassNotFoundException e) { }
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
		for (int i=0; i < numRes; i++) {
			try {
				ReservationInfo ri = (ReservationInfo)ois.readObject();
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
			resInfo = (ReservationInfo)ois.readObject();
		} catch (ClassNotFoundException e) { }
			
		ois.close();
		this.close();
		return resInfo;
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 *//*johnies
	public void close() throws IOException {
		
		if (output != null)
			output.close();
		if (input != null)
			input.close();
		if (sock != null)
			sock.close();
	}
	
	public static void main(String[] args) throws Exception {
		
		ProxyClient proxy = new ProxyClient("localhost", 4000);
		proxy.cancelReservation("test reservation1");
		
		System.out.println("program exit...");
	}
}*/
