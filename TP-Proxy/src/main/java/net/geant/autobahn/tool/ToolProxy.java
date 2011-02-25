package net.geant.autobahn.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.ReservationParamsType;

public class ToolProxy implements Tool {

    private static ToolProxy instance;
    private static final Logger log = Logger.getLogger(ToolProxy.class);
    Properties props = null;
    net.geant2.jra3.tool.ToolClient tool = null;
    
    public ToolProxy() {
        Properties props = new Properties();
        try {
            InputStream is =
                getClass().getClassLoader().getResourceAsStream("etc/toolproxy.properties");
            props.load(is);
            is.close();
            log.info(props.size() + " properties loaded");
        } catch (IOException e) {
            log.info("Could not load app.properties: " + e.getMessage());
            return;
        }
        
        String endpoint = props.getProperty("tool.address");
        
        tool = new net.geant2.jra3.tool.ToolClient(endpoint);
    }
    
    /**
     * @param props
     */
    public ToolProxy(Properties props) {
        this.props = props;
    }

    /**
     * Returns an instance of ToolProxy. Singleton.
     * @return
     */
    public synchronized static ToolProxy getInstance() {
        
        if (instance == null) {
            try {
                instance = new ToolProxy();
            } catch (Exception e) {
                instance.log.error("Error while creating ToolProxy", e);
            }
        }
        return instance;
    }

    /**
     * Returns an instance of ToolProxy. Singleton.
     * The instance will be initialized with the provided properties.
     * @param props
     * @return
     */
    public synchronized static ToolProxy getInstance(Properties props) {
        
        if (instance == null) {
            try {
                instance = new ToolProxy(props);
            } catch (Exception e) {
                instance.log.error("Error while creating ToolProxy", e);
            }
        }
        return instance;
    }
    
	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, RequestException,
			SystemException, ResourceNotFoundException {
		
		log.info("addReservation.begin");
		
		if (tool == null) {
		    log.error("TP endpoint address is not available, no operation can be performed.");
		    return;
		}
		
		// Send to TP using translator
		try {
            tool.addReservation(resID, Converter.convert(path), Converter.convert(params, path));
        } catch (net.geant2.jra3.tool.RequestException_Exception e) {
            e.printStackTrace();
        } catch (net.geant2.jra3.tool.SystemException_Exception e) {
            e.printStackTrace();
        } catch (net.geant2.jra3.tool.ResourceNotFoundException_Exception e) {
            e.printStackTrace();
        } catch (net.geant2.jra3.tool.AAIException_Exception e) {
            e.printStackTrace();
        }
		
		System.out.println("addReservation.end");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#removeReservation(java.lang.String)
	 */
	public void removeReservation(String resID, IntradomainPathType ipath,
			ReservationParamsType params) throws AAIException,
			RequestException, SystemException, ReservationNotFoundException {
		
		log.info("removeReservation.begin");
		
        if (tool == null) {
            log.error("TP endpoint address is not available, no operation can be performed.");
            return;
        }
        
        // Send to TP using translator
        try {
            tool.removeReservation(resID, Converter.convert(ipath), Converter.convert(params, ipath));
        } catch (net.geant2.jra3.tool.RequestException_Exception e) {
            e.printStackTrace();
        } catch (net.geant2.jra3.tool.SystemException_Exception e) {
            e.printStackTrace();
        } catch (net.geant2.jra3.tool.ReservationNotFoundException_Exception e) {
            e.printStackTrace();
        } catch (net.geant2.jra3.tool.AAIException_Exception e) {
            e.printStackTrace();
        }
		
		System.out.println("removeReservation.end");
	}

	public static void main(String args[]) {

	    // Dummy client to trigger Spring cxf endpoint initialization
        new net.geant2.jra3.tool.ToolClient("http://localhost");
	    
	    log.info("Tool Proxy started");
	    while (true);
	    
	}
}
