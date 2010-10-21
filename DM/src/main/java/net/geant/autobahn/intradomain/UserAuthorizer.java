package net.geant.autobahn.intradomain;

import java.util.Set;

import org.apache.log4j.Logger;

import net.geant.autobahn.aai.*;
import net.geant.autobahn.reservation.ReservationParams;

public class UserAuthorizer {
    
    private final static Logger log = Logger.getLogger(UserAuthorizer.class);

    private ReservationParams resParams;

    public UserAuthorizer(ReservationParams resParams) {
        this.resParams = resParams;
    }
    
    private boolean checkCapacity() {
        long capacity = resParams.getCapacity();
        
        //TODO: Add some actual policy-checking
        return true;
    }

    private boolean checkDelay() {
        int maxDelay = resParams.getMaxDelay();
        
        //TODO: Add some actual policy-checking
        return true;
    }

    /**
     * Determine whether the user has the authority to request a reservation
     * with the supplied parameters
     * @throws AAIException
     */
    public void checkSimpleParameters() throws AAIException {
        log.debug("Checking whether the user has the authority to request this reservation...");
        
        // TODO: Add some actual policy-checking
        if (resParams.getAuthParameters() != null && 
                resParams.getAuthParameters().getIdentifier() != null &&
                resParams.getAuthParameters().getIdentifier().equals("maliciousUser")) {
            log.debug("This user can not request resources");
            throw new AAIException("This user can not request resources");
        }
        if (resParams.getAuthParameters() != null && 
                resParams.getAuthParameters().getProjectMembership() != null &&
                !resParams.getAuthParameters().getProjectMembership().equals("AutoBahn")) {
            log.debug("This user does not belong to authorized project");
            throw new AAIException("This user does not belong to authorized project");
        }

        if (!checkCapacity()) {
            log.debug("Capacity check failed");
            throw new AAIException("Capacity check failed");
        }
        if (!checkDelay()) {
            log.debug("Delay check failed");
            throw new AAIException("Delay check failed");
        }
    }

    /**
     * Filter paths based on authorization policies
     * @param paths
     * @return a subset of the supplied paths, containing only the ones that the
     * user is authorized to use
     * @throws AAIException
     */
    public Set<IntradomainPath> filterPaths(Set<IntradomainPath> paths)
            throws AAIException {
        log.debug("Filtering paths based on authorization policies...");

        if(paths.size()==0) {
            throw new AAIException("No paths");
        }
        return paths;
    }
}
