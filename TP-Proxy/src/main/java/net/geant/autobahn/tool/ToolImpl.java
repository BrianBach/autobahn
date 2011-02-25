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
public class ToolImpl implements Tool {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, RequestException,
			SystemException, ResourceNotFoundException {
        ToolProxy.getInstance().addReservation(resID, path, params);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#removeReservation(java.lang.String)
	 */
	public void removeReservation(String resID, IntradomainPathType ipath,
			ReservationParamsType params) throws AAIException,
			RequestException, SystemException, ReservationNotFoundException {
        ToolProxy.getInstance().removeReservation(resID, ipath, params);
	}
}
