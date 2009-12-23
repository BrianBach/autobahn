/**
 * 
 */
package net.geant2.jra3.proxy;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import net.geant2.jra3.network.Link;

/**
 * @author Michal
 *
 */
public class ProxyReceiver implements Closeable, Runnable {
	private ServerSocket listen;
	private boolean running;
	private Proxy listener;
	
	public void init(int port, Proxy listener) throws IOException {
		
		this.listener = listener;
		listen = new ServerSocket(port);
		running = true;
		new Thread(this).start();
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	public void close() {
		
		if (listen != null && !listen.isClosed()) {
			try {
				listen.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			running = false;
		}
	}
	
	private void readProxy(Socket s) throws IOException {
		
		DataInputStream input = new DataInputStream(s.getInputStream());
		DataOutputStream output = new DataOutputStream(s.getOutputStream());
		// read method id
		int op = input.readInt();
		System.out.println("method - " + op);
		
		switch (op)  {
		
			case Proxy.PROXY_CANCEL_RESERVATION:
				
				String resID = input.readUTF();
				System.out.println("cancel - " + resID);
				listener.cancelReservation(resID);
			break;
		
			case Proxy.PROXY_CREATE_RESEVATION:
				
				ReservationInfo resInfo = null;
				ObjectInputStream ois = null;
				try {
					ois = new ObjectInputStream(input);
					resInfo = (ReservationInfo)ois.readObject();
				} catch (ClassNotFoundException e) { }
				ReservationInfo rInfo = listener.createReservation(resInfo);
				ObjectOutputStream oos = new ObjectOutputStream(output);
				oos.writeObject(rInfo);
				if(ois != null)
					ois.close();
				oos.close();
				break;
				
			case Proxy.PROXY_GET_TOPOLOGY:
				
				System.out.println("topology.begin");
				List<Link> links = listener.getTopology();
				System.out.println("write numLinks - " + links.size());
				output.writeInt(links.size());
				if (links.size() > 0) {
					ObjectOutputStream oos1 = new ObjectOutputStream(output);
					for (Link l : links) 
						oos1.writeObject(l);
					oos1.close();
				}
				System.out.println("topology.end");
				break;
				
			case Proxy.PROXY_LIST_RESERVATIONS:

				List<ReservationInfo> resv = listener.listReservations();
				output.writeInt(resv.size());
				ObjectOutputStream oos2 = new ObjectOutputStream(output);
				for (ReservationInfo ri : resv)
					oos2.writeObject(ri);
				oos2.close();
				break;
				
			case Proxy.PROXY_MODIFY_RESERVATION:
				
				try {
					ObjectInputStream ois1 = new ObjectInputStream(input);
				 	ReservationInfo ri = (ReservationInfo)ois1.readObject();
				 	ois1.close();
					boolean res = listener.modifyReservation(ri);
					output.writeBoolean(res);
				} catch (ClassNotFoundException e) { }
				break;
				
			case Proxy.PROXY_QUERY_RESERVATION:
				
				String rid = input.readUTF();
				ReservationInfo rinfo = listener.queryReservation(rid);
				ObjectOutputStream oos3 = new ObjectOutputStream(output);
				oos3.writeObject(rinfo);
				oos3.close();
				break;
		
			case Proxy.PROXY_NOTIFY_RESERVATION:
				try {
					ObjectInputStream ois1 = new ObjectInputStream(input);
				 	ReservationInfo ri = (ReservationInfo)ois1.readObject();
				 	listener.Notify(ri);
				 	ois1.close();
				} catch (ClassNotFoundException e) { }
				break;
				
			default:
				throw new IOException("unknown method id - " + op);
		}
		input.close();
		output.close();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		while (running) {
			
			try {
				System.out.println("waiting for request");
				Socket sock = listen.accept();
				
				readProxy(sock);
				sock.close();
			} catch (IOException e) {
				System.out.println("run ex - " + e.getMessage());
			}
		}
	}
}

