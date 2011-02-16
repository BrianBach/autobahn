package net.geant.autobahn.intradomain.pathfinder;

import java.util.Collection;
import java.util.List;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;

/**
 * Interface for intradomain pathfinders. Describes methods for searching paths
 * or single path inside the domain, using some constraints. It is possible to
 * search for the paths from an edge node to another edge node, or a specified
 * edge link to another.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public interface IntradomainPathfinder {

	/**
	 * Searches for intradomain paths starting with the given start link, ending
	 * with the given end link. Path should quarantee requested capacity and not
	 * contain excluded links. Searching should be ended when the given number
	 * of paths has been found.
	 * 
	 * @param start
	 *            Starting link of the path
	 * @param end
	 *            Last link of the path
	 * @param capacity
	 *            Requested capacity
	 * @param excluded
	 *            List of links that the path should not contain
	 * @param limit
	 *            Number of paths after which the search is stopped
	 * @param userVlanId
	 *            User-required VLAN for the reservation (0 if not supplied)
     * @param mtu
     *            User-required Mtu size for the reservation (0 if not supplied)
	 * @return List of intradomain paths found 
	 */
	public List<IntradomainPath> findPaths(IntradomainPath pathSkeleton,
			long capacity, Collection<GenericLink> excluded, int limit, int mtu);

	/**
	 * Searches for all intradomain paths between the given start node and the
	 * given node. Path should not contain excluded links. Searching should be
	 * ended when the given number of paths has been found.
	 * 
	 * @param start Start node of the path
	 * @param end End node of the path
	 * @param excluded
	 *            List of links that the path should not contain
	 * @param limit
	 *            Number of paths after which the search is stopped
	 * @return List of intradomain paths found 
	 */
	public List<IntradomainPath> findPaths(Node start, Node end,
			Collection<GenericLink> excluded, int limit);

	/**
	 * Searches for an intradomain path with the given constraints, starting
	 * with the given start link, ending with the given end link. Path should
	 * quarantee requested capacity and not contain excluded links.
	 * 
	 * @param start
	 *            Starting link of the path
	 * @param end
	 *            Last link of the path
	 * @param capacity
	 *            Requested capacity
	 * @param pcon
	 *            Requested path constraints
	 * @param excluded
	 *            List of links that the path should not contain
	 * @param userVlanId
	 *            User-required VLAN for the reservation (0 if not supplied)
     * @param mtu
     *            User-required Mtu size for the reservation (0 if not supplied)
	 * @return IntradomainPath that matches given conditions, or null if not
	 *         found
	 */
	public IntradomainPath findPath(IntradomainPath pathSkeleton,
			long capacity, Collection<GenericLink> excluded, int mtu);
}
