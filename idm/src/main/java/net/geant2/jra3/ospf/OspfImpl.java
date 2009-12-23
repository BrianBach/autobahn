/**
 * OspfImpl.java
 * 
 * 2006-09-05
 */
package net.geant2.jra3.ospf;

import java.util.List;
import java.util.ArrayList;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.IOException;

import net.geant2.jra3.network.Link;
import org.apache.log4j.Logger;

/**
 * Implements <code>Ospf</code> interface
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

final public class OspfImpl implements Ospf {
    
	private final Logger log = Logger.getLogger(OspfImpl.class);
    public static final int OSPF_API_PORT = 2607;
    private List<OspfAsync> listeners = new ArrayList<OspfAsync>();
    private Socket syncSocket;
    private Socket asyncSocket;
    private SyncMessage syncMsg;
    private AsyncMessage asyncMsg;
    
    public OspfImpl() {
        
    }
    
    public OspfImpl(OspfAsync async) {
        listeners.add(async);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#connect(java.lang.String, int)
     */
    public void connect(String ospfAddress, int port) throws IOException {
        
    	log.info("Using ports for OSPF communication "+port+" and "+ port+1);
        ServerSocket listen = new ServerSocket(port + 1);
        syncSocket = new Socket();
        syncSocket.setReuseAddress(true);
        syncSocket.bind(new InetSocketAddress(port));
        try {
            syncSocket.connect(new InetSocketAddress(ospfAddress, OSPF_API_PORT));
        } catch (IOException e) {
            syncSocket = null;
            listen.close();
            throw e;
        }
        asyncSocket = listen.accept();
        listen.close();
        syncMsg = new SyncMessage(syncSocket.getInputStream(),
                syncSocket.getOutputStream());
        asyncMsg = new AsyncMessage(this, asyncSocket.getInputStream(), listeners);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#disconnect()
     */
    public void disconnect() throws IOException {
        
        if (!syncSocket.isConnected())
            return;
        
        if(asyncSocket.isConnected())
        	asyncMsg.close();
        asyncMsg = null;
        syncSocket.close();
        syncSocket = null;
        asyncSocket.close();
        asyncSocket = null;
        syncMsg = null;
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#isConnected()
     */
    public boolean isConnected() {
        return ((syncSocket != null && syncSocket.isConnected()) && 
                (asyncSocket != null && asyncSocket.isConnected()));
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#registerOpaqueType(int, int)
     */
    public void registerOpaqueType(int lsaType, int opaqueType) throws 
                        IOException, OspfException {
        syncMsg.writeRegisterOpaque(lsaType, opaqueType);        
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#unregisterOpaqueType(int, int)
     */
    public void unregisterOpaqueType(int lsaType, int opaqueType) throws 
                        IOException, OspfException {
        syncMsg.writeUnregisterOpaque(lsaType, opaqueType); 
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#registerEvents(int)
     */
    public void registerEvents(int mask) throws 
                        IOException, OspfException {
        syncMsg.writeEvents(mask);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#synchronizeLsdb()
     */
    public void synchronizeLsdb() throws IOException, OspfException {
        syncMsg.writeSyncLsdb();
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#originateLsa(java.lang.String, java.lang.String, int, int, int)
     */
    public void originateLsa(String ifAddr, String areaId, int lsaType,
            int opaqueType, int opaqueId, Object opaqueData) 
                    throws IOException, OspfException {
        
        syncMsg.writeLsaOrginate(ifAddr, areaId, lsaType, 
                    opaqueType, opaqueId, opaqueData);
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#originateLink(java.lang.String, java.lang.String, int, int, int, net.geant2.jra3.network.Link)
     */
    public void originateLink(String ifAddr, String areaId,
            int lsaType, int opaqueType, int opaqueId, 
            Link link) throws IOException, OspfException {
        
        syncMsg.writeLsaOrginate(ifAddr, areaId, lsaType, 
                            opaqueType, opaqueId, link);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#deleteLsa(java.lang.String, int, int, int)
     */
    public void deleteLsa(String areaId, int lsaType, int opaqueType,
            int opaqueId) throws IOException, OspfException {
        
        syncMsg.writeLsaDelete(areaId, lsaType, 
                    opaqueType, opaqueId);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#addOspfAsync(net.geant2.jra3.pathfinder.ospf.OspfAsync)
     */
    public void addOspfAsync(OspfAsync listener) {
        listeners.add(listener);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.Ospf#removeOspfAsync(net.geant2.jra3.pathfinder.ospf.OspfAsync)
     */
    public void removeOspfAsync(OspfAsync listener) {
        listeners.remove(listener);
    }
}
