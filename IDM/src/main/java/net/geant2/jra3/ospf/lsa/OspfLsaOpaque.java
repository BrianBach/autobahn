/**
 * OspfOpaque.java
 * 
 * 2006-09-07
 */
package net.geant2.jra3.ospf.lsa;

import java.io.DataInputStream;
import java.io.IOException;

import net.geant2.jra3.ospf.LinkSerializer;

/**
 * Represents user's custom Opaque type. User's data should be 
 * no longer than 1500 bytes (including header)
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class OspfLsaOpaque extends OspfLsa {
    
    public static final int LSID_OPAQUE_TYPE_MASK = 0xFF000000;
    public static final int LSID_OPAQUE_ID_MASK = 0x00FFFFFF;
    
    // some known opaque types
    public static final int OPAQUE_TYPE_WILDCARD = 0;
    public static final int OPAQUE_TYPE_TRAFFIC_ENGINEERING_LSA = 1;
    public static final int OPAQUE_TYPE_GRACE_LSA = 3;
    
    // custom opaque user data
    private Object data = null;

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.lsa.OspfLsa#readSelfFromStream(java.io.DataInput)
     */
    @Override
    protected void readSelfFromStream(DataInputStream input) throws IOException {
        
        try {
            // try to get opaque that was inserted with java
            // serialization mechanism
            //ObjectInputStream ois = new ObjectInputStream(input);
            //data = ois.readObject();
        	data = LinkSerializer.deserializeLink(input);
        } catch (IOException e) {
            if ("invalid stream header".compareTo(e.getMessage()) == 0) {
                try {
                    // try to get opaque in TLV format
                	data = LinkSerializer.deserializeLink(input);
                } catch (IOException e1) {
                    throw e1;
                }
            } 
        } catch (Exception e2) {
            throw new IOException("readSelfFromStream: " + e2.getMessage());
        } 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    /**
     * Gets opaque type out of lsid
     * @param lsid combined opaque type and id
     * @return opaque type
     */
    public static int getOpaqueType(int lsid) {
        return (lsid & LSID_OPAQUE_TYPE_MASK) >> 24;
    }
    
    /**
     * Gets opaque id out of lsid
     * @param lsid combined opaque type and id
     * @return opaque id
     */
    public static int getOpaqueId(int lsid) {
        return lsid & LSID_OPAQUE_ID_MASK;
    }
    
    /**
     * Combines opaque type and id
     * @param type opaque type
     * @param id opaque id
     * @return combined opaque type and id
     */
    public static int setOpaqueLsid(int type, int id) {
        return ((((byte)type << 24) & LSID_OPAQUE_TYPE_MASK) | 
                (id & LSID_OPAQUE_ID_MASK));
    }
    
    /**
     * Determines whether given lsa type is opaque
     * @param lsa LSA type
     * @return true if LSA type belongs to opaque type, otherwise false
     */
    public static boolean isOpaqueLsa(int lsa) {
        
        return lsa == OspfLsa.OSPF_LSA_TYPE_OPAQUE_LINK ||
               lsa == OspfLsa.OSPF_LSA_TYPE_OPAQUE_AREA ||
               lsa == OspfLsa.OSPF_LSA_TYPE_OPAQUE_AS;
    }
}
