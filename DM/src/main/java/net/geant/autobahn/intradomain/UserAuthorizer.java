package net.geant.autobahn.intradomain;

import java.util.Set;

import net.geant.autobahn.aai.*;
import net.geant.autobahn.reservation.ReservationParams;

public class UserAuthorizer {
    private ReservationParams resParams;

    public UserAuthorizer(ReservationParams resParams) {
        this.resParams = resParams;
    }
    
    private boolean checkCapacity() {
        long capacity = resParams.getCapacity();
        return true;
    }

    private boolean checkDelay() {
        int maxDelay = resParams.getMaxDelay();
        return true;
    }

    
    public void checkSimpleParameters() throws AAIException {
        if (!checkCapacity())
            throw new AAIException("Capacity check failed");
        if (!checkDelay())
            throw new AAIException("Delay check failed");
    }

    public Set<IntradomainPath> filterPaths(Set<IntradomainPath> paths)
            throws AAIException {
        if(paths.size()==0) throw new AAIException("No paths");
        return paths;
    }
}
