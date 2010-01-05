/**
 * OspfLsaRouterLink.java
 * 
 * 2006-09-07
 */
package net.geant2.jra3.ospf.lsa;

import java.util.List;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * LSA router link type
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 *
 */
public class OspfLsaRouterLink extends OspfLsa {
    
    private String id;
    private String data;
    private List<Metric> metrics = new ArrayList<Metric>();
    
    class Metric {
        int type;
        int tos;
        int metric;
    }

    @Override
    protected void readSelfFromStream(DataInputStream input) throws IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @return Returns the data.
     */
    public String getData() {
        return data;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Returns the metrics.
     */
    public List<Metric> getMetrics() {
        return metrics;
    }
}
