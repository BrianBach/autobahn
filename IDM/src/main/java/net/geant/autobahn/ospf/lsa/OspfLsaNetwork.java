/**
 * OspfLsaNetwork.java
 * 
 * 2006-09-07
 */
package net.geant.autobahn.ospf.lsa;

import java.util.List;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Network LSA type
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class OspfLsaNetwork extends OspfLsa {

    protected String mask;
    protected List<String> routers = new ArrayList<String>();
    
    @Override
    protected void readSelfFromStream(DataInputStream input) 
                    throws IOException {
        
        routers.clear();
        mask = readAddress(input);
        int num = (this.lsaLength - OspfLsa.OSPF_LSA_HEADER_SIZE) / 4;
        for (int i=0; i<num; i++) {
            routers.add(OspfLsa.readAddress(input));
        }
    }

    /**
     * @return Returns the mask.
     */
    public String getMask() {
        return mask;
    }

    /**
     * @return Returns the routers.
     */
    public List<String> getRouters() {
        return routers;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.ospf.lsa.OspfLsa#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("\nmask: " + mask);
        sb.append("\nrouters: " + routers.size());
        for (int i=0; i<routers.size(); i++)
            sb.append("\n" + routers.get(i));
        return sb.toString();
    }
}
