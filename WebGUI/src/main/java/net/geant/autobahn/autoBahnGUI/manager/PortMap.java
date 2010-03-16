package net.geant.autobahn.autoBahnGUI.manager;

import java.io.Serializable;

/**
 * Holds a port identifier and its associated friendly name.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class PortMap implements Serializable {

    private String identifier;
    private String friendlyName;
    
    public PortMap(String identifier, String friendlyName) {
        super();
        this.identifier = identifier;
        this.friendlyName = friendlyName;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }
    
    /**
     * @param friendlyName the friendlyName to set
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
