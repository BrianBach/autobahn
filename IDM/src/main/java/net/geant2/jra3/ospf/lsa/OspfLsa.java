/**
 * OspfLsa.java
 * 
 * 2006-09-07
 */
package net.geant2.jra3.ospf.lsa;

import net.geant2.jra3.ospf.OspfException;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Base class for LSA messages, reads LSA header message
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public abstract class OspfLsa {
    
    // lsa filter type
    public static final int FILTER_NON_SELF_ORIGINATED = 0;
    public static final int FILTER_SELF_ORIGNATED = 1;
    public static final int FILTER_ANY_ORIGIN = 2;
    
    // lsa type definition
    public static final int OSPF_LSA_TYPE_UNKNOWN = 0;
    public static final int OSPF_LSA_TYPE_ROUTER = 1;
    public static final int OSPF_LSA_TYPE_NETWORK = 2;
    public static final int OSPF_LSA_TYPE_SUMMARY = 3;
    public static final int OSPF_LSA_TYPE_ASBR_SUMMARY = 4;
    public static final int OSPF_LSA_TYPE_AS_EXTERNAL = 5;
    public static final int OSPF_LSA_TYPE_GROUP_MEMBER = 6;
    public static final int OSPF_LSA_TYPE_AS_NSSA = 7;
    public static final int OSPF_LSA_TYPE_EXTERNAL_ATTRIBUTES = 8;
    public static final int OSPF_LSA_TYPE_OPAQUE_LINK = 9;
    public static final int OSPF_LSA_TYPE_OPAQUE_AREA = 10;
    public static final int OSPF_LSA_TYPE_OPAQUE_AS = 11;
    
    // lsa link types
    public static final int OSPF_LSA_LINK_POINT2POINT = 1;
    public static final int OSPF_LSA_LINK_TRANSIT = 2;
    public static final int OSPF_LSA_LINK_STUB = 3;
    public static final int OSPF_LSA_LINK_VIRTUAL = 4;
    
    // lsa sizes
    public static final int OSPF_LSA_HEADER_SIZE = 20;
    public static final int OSPF_LSA_LINK_SIZE = 12;
    public static final int OSPF_LSA_MAX_SIZE = 1500;
    
    // link types desc
    private static String[] linkTypes = {"Point To Point", 
        "Transit", "Stub", "Virtual"};
    
    // header info
    protected int lsaAge;
    protected int lsaOptions;
    protected int lsaType;
    protected String lsaId;
    protected String lsaAdvRouter;
    protected int lsaSeqNum;
    protected int lsaChecksum;
    protected int lsaLength; 
    
    /**
     * All subclasses can read IPv4 address in the same way 
     * @param input input stream
     * @return IPv4 address
     * @throws IOException
     */
    protected static String readAddress(DataInputStream input) throws IOException {
        
        int[] b = new int[4];
        for (int i=0; i<4; i++)
            b[i] = input.readUnsignedByte();
            
        return b[0] + "." + b[1] + "." + b[2] + "." + b[3];
    }
    
    /**
     * Subclasses of OspfLsa class must provide their own body message reader
     * @param input
     * @throws IOException
     */
    protected abstract void readSelfFromStream(DataInputStream input) 
                    throws IOException;
    
    /**
     * Creates specific LSA type from input stream
     * @param input input stream
     * @return new LSA object
     * @throws IOException
     */
    public static OspfLsa createFromStream(DataInputStream input) throws 
                IOException, OspfException {
        
        int age = input.readUnsignedShort();
        int options = input.readUnsignedByte();
        int type = input.readUnsignedByte();
        String id = readAddress(input);
        String advRouter = readAddress(input);
        int seqNum = input.readInt();
        int checksum = input.readUnsignedShort();
        int length = input.readUnsignedShort();

        if (length > OspfLsa.OSPF_LSA_MAX_SIZE)
            throw new OspfException(length + " > " + OspfLsa.OSPF_LSA_MAX_SIZE);

        OspfLsa lsa = null;
        // create specific instance based on type
        switch (type) {
            
            // these are not supported
            case OspfLsa.OSPF_LSA_TYPE_UNKNOWN:
            case OspfLsa.OSPF_LSA_TYPE_GROUP_MEMBER:
            case OspfLsa.OSPF_LSA_TYPE_EXTERNAL_ATTRIBUTES:
                break;
            
            case OspfLsa.OSPF_LSA_TYPE_ROUTER:
                lsa = new OspfLsaRouter();
                break;
                
            case OspfLsa.OSPF_LSA_TYPE_NETWORK:
                lsa = new OspfLsaNetwork();
                break;
                
            case OspfLsa.OSPF_LSA_TYPE_SUMMARY:
                lsa = new OspfLsaSummary();
                break;
                
            case OspfLsa.OSPF_LSA_TYPE_AS_EXTERNAL:
                lsa = new OspfLsaAsExternal();
                break;
                
            case OspfLsa.OSPF_LSA_TYPE_OPAQUE_LINK:
            case OspfLsa.OSPF_LSA_TYPE_OPAQUE_AREA:
            case OspfLsa.OSPF_LSA_TYPE_OPAQUE_AS:
                lsa = new OspfLsaOpaque();
                break;
                
            default:
                break;
        }
        if (lsa == null)
            throw new OspfException("Lsa type not supported: " + type);
        
        lsa.lsaAge = age;
        lsa.lsaOptions = options;
        lsa.lsaType = type;
        lsa.lsaId = id;
        lsa.lsaAdvRouter = advRouter;
        lsa.lsaSeqNum = seqNum;
        lsa.lsaChecksum = checksum;
        lsa.lsaLength = length;
        
        lsa.readSelfFromStream(input);
        return lsa;
    }
        
    /**
     * @return Returns the lsaAdvRouter.
     */
    public String getLsaAdvRouter() {
        return lsaAdvRouter;
    }

    /**
     * @return Returns the lsaAge.
     */
    public int getLsaAge() {
        return lsaAge;
    }

    /**
     * @return Returns the lsaChecksum.
     */
    public int getLsaChecksum() {
        return lsaChecksum;
    }

    /**
     * @return Returns the lsaId.
     */
    public String getLsaId() {
        return lsaId;
    }

    /**
     * @return Returns the lsaLength.
     */
    public int getLsaLength() {
        return lsaLength;
    }

    /**
     * @return Returns the lsaOptions.
     */
    public int getLsaOptions() {
        return lsaOptions;
    }

    /**
     * @return Returns the lsaSeqNum.
     */
    public int getLsaSeqNum() {
        return lsaSeqNum;
    }

    /**
     * @return Returns the lsaType.
     */
    public int getLsaType() {
        return lsaType;
    }

    /**
     * Returns type of link
     * @param linkType type of link
     * @return String describing type of link
     */
    public static String getLinkType(int linkType) {
        if (linkType <= 0 || linkType > 4)
            return "Unknown";
        return linkTypes[linkType-1]; 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "id: " + lsaId + ", advRouter: " + lsaAdvRouter + 
            ", type: " + OspfLsa.getLinkType(lsaType)  + 
            ", age: " + lsaAge;
    }
}
