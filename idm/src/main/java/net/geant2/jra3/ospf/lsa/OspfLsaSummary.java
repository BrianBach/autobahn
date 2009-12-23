/**
 * OspfLsaSummary.java
 * 
 * 2006-09-07
 */
package net.geant2.jra3.ospf.lsa;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Summary LSA type
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class OspfLsaSummary extends OspfLsa {
    
    private String mask;
    private int tos;
    private int[] metric = new int[3];
    
    @Override
    protected void readSelfFromStream(DataInputStream input) throws IOException {
        
        mask = readAddress(input);
        tos = input.readUnsignedByte();
        for (int i=0; i<3; i++)
            metric[i] = input.readUnsignedByte();
    }

    /**
     * @return Returns the mask.
     */
    public String getMask() {
        return mask;
    }

    /**
     * @return Returns the metric.
     */
    public int[] getMetric() {
        return metric;
    }

    /**
     * @return Returns the tos.
     */
    public int getTos() {
        return tos;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.ospf.lsa.OspfLsa#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("\nmask: " + mask + ", tos: " + tos);
        return sb.toString();
    }
}
