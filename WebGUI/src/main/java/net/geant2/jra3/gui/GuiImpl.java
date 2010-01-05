package net.geant2.jra3.gui;
import org.apache.log4j.Logger;
import net.geant2.jra3.autoBahnGUI.manager.ManagerNotifier;

/**
 * Class is implementation of the GUI web service interface
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */

@javax.jws.WebService(name = "Gui", serviceName = "GuiService",
                      portName = "GuiPort",
                      targetNamespace = "http://gui.jra3.geant2.net/", 
                      wsdlLocation = "file:wsdl/gui.wsdl" ,
		      endpointInterface = "net.geant2.jra3.gui.Gui")
                      
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
     * @see net.geant2.jra3.gui.Gui#statusUpdated(java.lang.String  idm ,)net.geant2.jra3.administration.Status  status )*
     */
    public void statusUpdated(java.lang.String idm,net.geant2.jra3.administration.Status status) { 
        try {
        	notifier.statusUpdated(idm, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            ///throw new RuntimeException(ex);
        }
    }
    public void update(java.lang.String idm,net.geant2.jra3.gui.EventType event,java.util.List<net.geant2.jra3.administration.KeyValue> properties) { 
        try {
        	notifier.update(idm, event, properties);
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.gui.Gui#reservationChanged(java.lang.String  idm ,)java.lang.String  serviceId ,)java.lang.String  resID ,)net.geant2.jra3.gui.ReservationChangedType  state ,)java.lang.String  message )*
     */
    public void reservationChanged(java.lang.String idm,java.lang.String serviceId,java.lang.String resID,net.geant2.jra3.gui.ReservationChangedType state,java.lang.String message) { 
        try {
        	notifier.reservationChanged(idm, serviceId, resID, state, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
