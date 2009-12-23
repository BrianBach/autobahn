/*
 * Pathfinding.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import java.util.Iterator;
import java.util.List;

import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.reservation.HomeDomainReservation;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Pathfinding extends HomeDomainState {

    public Pathfinding(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(HomeDomainReservation res) {
        super.run(res);
        
        List<Link> exclude = res.getExcludedLinks();

        Iterator<Path> paths = res.getPathFinder().findInterdomainPaths(res, exclude);
        res.setPaths(paths);
        
        if(paths != null && paths.hasNext()) {
            res.switchState(HomeDomainState.LOCAL_CHECK);
        } else {
            res.fail("No paths found");
        }
    }
}
