/**
 * OspfLsaAsExternal.java
 * 
 * 2006-09-07
 */
package net.geant.autobahn.ospf.lsa;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * AS external LSA type
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class OspfLsaAsExternal extends OspfLsa {

    @Override
    protected void readSelfFromStream(DataInputStream input) throws IOException {
        
    }
}
