/**
 * OspfLsaRouter.java
 * 
 * 2006-09-07
 */
package net.geant.autobahn.ospf.lsa;

import java.util.List;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * LSA router type
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class OspfLsaRouter extends OspfLsa {
    
    public static final int OSPF_LSA_ROUTER_BORDER = 0x01;
    public static final int OSPF_LSA_ROUTER_EXTERNAL = 0x02;
    public static final int OSPF_LSA_ROUTER_VIRTUAL = 0x04;
    public static final int OSPF_LSA_ROUTER_NT = 0x10;
    public static final int OSPF_LSA_ROUTER_SHORTCUT = 0x20;
    
    protected int flags;
    protected int zero;
    protected int numLinks;
    protected List<Link> links = new ArrayList<Link>();
    
    public class Link {
        public String id;
        public String data;
        public int type;
        public int tos;
        public int metric;
    }

    @Override
    protected void readSelfFromStream(DataInputStream input) throws IOException {
        
        flags = input.readUnsignedByte();
        zero = input.readUnsignedByte();
        numLinks = input.readUnsignedShort();
        
        for (int i=0; i<numLinks; i++) {
            Link l = new Link();
            l.id = readAddress(input);
            l.data = readAddress(input);
            l.type = input.readUnsignedByte();
            l.tos = input.readUnsignedByte();
            l.metric = input.readUnsignedShort();
            links.add(l);
        }
    }

    /**
     * @return Returns the flags.
     */
    public int getFlags() {
        return flags;
    }

    /**
     * @return Returns the links.
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * @return Returns the numLinks.
     */
    public int getNumLinks() {
        return numLinks;
    }

    /**
     * @return Returns the zero.
     */
    public int getZero() {
        return zero;
    }
    
    private String getRouterDesc() {
        String s = "unknown";
        if ((flags & OspfLsaRouter.OSPF_LSA_ROUTER_BORDER) == 1)
            s = "border ";
        if ((flags & OspfLsaRouter.OSPF_LSA_ROUTER_EXTERNAL) == 1)
            s += "external ";
        if ((flags & OspfLsaRouter.OSPF_LSA_ROUTER_NT) == 1)
            s += "nt ";
        if ((flags & OspfLsaRouter.OSPF_LSA_ROUTER_SHORTCUT) == 1)
            s += "shortcut ";
        if ((flags & OspfLsaRouter.OSPF_LSA_ROUTER_VIRTUAL) == 1)
            s += "virtual";
        
        return s;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.ospf.lsa.OspfLsa#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("\nrouter type: " + getRouterDesc());
        sb.append("\nrouter type: " + Integer.toHexString(flags));
        sb.append("\nlinks: " + numLinks);
        for (int i=0; i<numLinks; i++) {
            Link l = links.get(i);
            sb.append("\nid: " + l.id + ", data: " + l.data + 
               ", type: " + OspfLsa.getLinkType(l.type) + 
               ", tos: " + l.tos + ", metric: " + l.metric);
        }
        return sb.toString();
    }
}
