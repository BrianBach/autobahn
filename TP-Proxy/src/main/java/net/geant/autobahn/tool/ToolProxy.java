package net.geant.autobahn.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.ReservationParamsType;

@WebService(name = "Tool", serviceName = "ToolService",
        portName = "ToolPort",
        targetNamespace = "http://tool.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.tool.Tool")
public class ToolProxy implements Tool {

    private static final Logger log = Logger.getLogger(ToolProxy.class);
    Properties props = null;
    
	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, RequestException,
			SystemException, ResourceNotFoundException {
		
		log.info("addReservation.begin");
		
		// Send to TP using translator
		net.geant2.jra3.tool.ToolClient tool = new net.geant2.jra3.tool.ToolClient(getEndpoint());
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
		
        // Send to TP using translator
        net.geant2.jra3.tool.ToolClient tool = new net.geant2.jra3.tool.ToolClient(getEndpoint());
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

	private String getEndpoint() {
	    if (props == null) {
            try {
                InputStream is =
                    getClass().getClassLoader().getResourceAsStream("etc/toolproxy.properties");
                props.load(is);
                is.close();
                System.out.println(props.size() + " properties loaded");
            } catch (IOException e) {
                System.out.println("Could not load app.properties: " + e.getMessage());
                return null;
            }
	    }
        return props.getProperty("tool.address");
	}
	
	public static void main(String args[]) {

	    // Dummy client to trigger Spring cxf endpoint initialization
        new net.geant2.jra3.tool.ToolClient("http://localhost");
	    
	    log.info("Tool Proxy started");
	    while (true);
	    
	}
}
