/**
 * Ospf.java
 * 
 * 2006-09-05
 */
package net.geant2.jra3.ospf;

import java.io.IOException;
import net.geant2.jra3.network.Link;

/**
 * Interface that allows user sending requests to OSPF daemon
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public interface Ospf {
        
    /**
     * Establishes connection between user and OSPF deamon 
     * @param ospfAddress address of OSPF API
     * @param port port number
     * @throws IOException when connection was not successful
     */
    void connect(String ospfAddress, int port) throws IOException;
    
    /**
     * Tells whether connection to OSPF API is established
     * @return true if connected, otherwise false
     */
    boolean isConnected();
    
    /**
     * Closes connection to OSPF API
     * @throws OspfException
     * @throws IOException
     */
    void disconnect() throws IOException, OspfException;
    
    /**
     * Informs OSPF API daemon about events user wants to receive
     * @param mask bit mask describing events
     * @throws OspfException
     * @throws IOException
     */
    void registerEvents(int mask) throws IOException, OspfException;
        
    /**
     * Request to register opaque type
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @throws OspfException
     * @throws IOException
     */
    void registerOpaqueType(int lsaType, int opaqueType) throws 
                IOException, OspfException;
    
    /**
     * Request to unregister opaque type
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @throws OspfException
     * @throws IOException
     */
    void unregisterOpaqueType(int lsaType, int opaqueType) throws 
               IOException, OspfException;
    
    /**
     * Tells OSPF API deamon to send its database, user will get those data
     * asynchronously through OspfAsync
     * @throws OspfException
     * @throws IOException
     */
    void synchronizeLsdb() throws IOException, OspfException;
    
    /**
     * Allows user to send custom LSA opaque message
     * @param ifAddr interface address
     * @param areaId area id
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @param opaqueId opaque id
     * @param opaqueData user data
     * @throws OspfException
     * @throws IOException
     */
    void originateLsa(String ifAddr, String areaId, 
            int lsaType, int opaqueType, 
            int opaqueId, Object opaqueData) 
                    throws IOException, OspfException; 
    
    /**
     * Custom method for inserting Link objects in tlv format
     * @param ifAddr
     * @param areaId
     * @param lsaType
     * @param opaqueType
     * @param opaqueId
     * @param link
     * @throws IOException
     * @throws OspfException
     */
    void originateLink(String ifAddr, String areaId,
            int lsaType, int opaqueType,
            int opaqueId, Link link)
                    throws IOException, OspfException;
    
    /**
     * Orders to delete LSA opaque message
     * @param areaId area id
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @param opaqueId opaque id
     * @throws OspfException
     * @throws IOException
     */
    void deleteLsa(String areaId, int lsaType, 
            int opaqueType, int opaqueId) throws 
                    IOException, OspfException;
    
    /**
     * In case user wants to receive event notification, 
     * he must implement and register <code>OspfAsync</code> interface 
     * @param listener
     */
    void addOspfAsync(OspfAsync listener);
    
    /**
     * In case user is no longer intrested in receving events from OSFP deemon
     * @param listener
     */
    void removeOspfAsync(OspfAsync listener);
}
