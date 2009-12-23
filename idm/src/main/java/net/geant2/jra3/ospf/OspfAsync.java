/**
 * OspfAsync.java
 * 
 * 2006-09-05
 */
package net.geant2.jra3.ospf;

import net.geant2.jra3.ospf.lsa.OspfLsa;

/**
 * It should be implemented by users in order to get notifications from
 * OSPF daemon
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public interface OspfAsync {
    
    /**
     * Confirms that ospf daemon is ready for futher requests
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @param addr address
     */
    void readyNotify(int lsaType, int opaqueType, String addr);
    
    /**
     * New network interface showed up
     * @param ifAddr interface address
     * @param areaId area id
     */
    void newInterface(String ifAddr, String areaId);
    
    /**
     * Some network interface went down
     * @param ifAddr interface address
     */
    void delInterface(String ifAddr);
    
    /**
     * Some network interface has changed its status
     * @param ifAddr interface address
     * @param areaId area id
     * @param status status
     */
    void ismChange(String ifAddr, String areaId, int status);
    
    /**
     * @param ifAddr interface address
     * @param nbrAddr neighbor address
     * @param routerId router id
     * @param status status
     */
    void nsmChange(String ifAddr, String nbrAddr, String routerId, int status);
    
    /**
     * New LSA message has arrived
     * @param ifAddr interface address
     * @param areaId area id
     * @param selfOrigin self originate
     * @param lsa
     */
    void updateNotify(String ifAddr, String areaId, int selfOrigin, OspfLsa lsa);
    
    /**
     * Informs about LSA message deletion
     * @param ifAddr interface address
     * @param areaId area id
     * @param selfOrigin self originate
     * @param lsa LSA message
     */
    void deleteNotify(String ifAddr, String areaId, int selfOrigin, OspfLsa lsa);
}
