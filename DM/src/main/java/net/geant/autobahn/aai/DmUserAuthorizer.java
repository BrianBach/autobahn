package net.geant.autobahn.aai;

import java.util.Set;

import org.apache.log4j.Logger;

import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.reservation.ReservationParams;

public class DmUserAuthorizer {
    
    private final static Logger log = Logger.getLogger(DmUserAuthorizer.class);

    private ReservationParams resParams;
    private Set<IntradomainPath> paths;

    public DmUserAuthorizer() {
    }
        
    public void authorize() {
        log.debug("Authorization granted");
    }

    public ReservationParams getResParams() {
        return resParams;
    }

    public void setResParams(ReservationParams resParams) {
        this.resParams = resParams;
    }
    
    public Set<IntradomainPath> getPaths() {
        return paths;
    }

    public void setPaths(Set<IntradomainPath> paths) {
        this.paths = paths;
    }
}
