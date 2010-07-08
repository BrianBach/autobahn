package net.geant.autobahn.gui;
import org.apache.log4j.Logger;

import net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier;

/**
 * Class is implementation of the GUI web service interface
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */

@javax.jws.WebService(name = "Gui", serviceName = "GuiService",
                      portName = "GuiPort",
                      targetNamespace = "http://gui.autobahn.geant.net/", 
                      wsdlLocation = "WEB-INF/wsdl/gui.wsdl" ,
		      endpointInterface = "net.geant.autobahn.gui.Gui")
                      
public class GuiImpl implements Gui {
	/**
	 * Logs information
	 */
	public Logger logger = Logger.getLogger(GuiImpl.class);
	/**
	 * Notifies the manager about events apear in GUI interface
	 */
    private ManagerNotifier notifier;
    
    public ManagerNotifier getNotifier() {
		return notifier;
	}

	public void setNotifier(ManagerNotifier notifier) {
		this.notifier = notifier;
	}

	/* (non-Javadoc)
     * @see net.geant.autobahn.gui.Gui#statusUpdated(java.lang.String  idm ,)net.geant.autobahn.administration.Status  status )*
     */
    public void statusUpdated(java.lang.String idm,net.geant.autobahn.administration.Status status) { 
        try {
        	notifier.statusUpdated(idm, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            ///throw new RuntimeException(ex);
        }
    }
    public void update(java.lang.String idm,net.geant.autobahn.gui.EventType event,java.util.List<net.geant.autobahn.administration.KeyValue> properties) { 
        try {
        	notifier.update(idm, event, properties);
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.gui.Gui#reservationChanged(java.lang.String  idm ,)java.lang.String  serviceId ,)java.lang.String  resID ,)net.geant.autobahn.gui.ReservationChangedType  state ,)java.lang.String  message )*
     */
    public void reservationChanged(java.lang.String idm,java.lang.String serviceId,java.lang.String resID,net.geant.autobahn.gui.ReservationChangedType state,java.lang.String message) { 
        try {
        	notifier.reservationChanged(idm, serviceId, resID, state, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
