/**
 * 
 */
package net.geant.autobahn.intradomain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * Class represents sequence of network links - intradomain path. Holds a
 * sequence of chosen links and network constraints corresponding with each of
 * them (such as available vlans or timeslots).
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IntradomainPath", namespace="net.geant.autobahn.intradomain.IntradomainPath", propOrder={
        "gLinks", "pcons", "capacity"
})
public class IntradomainPath implements Comparable<IntradomainPath> {

	@XmlTransient
	private long pathId;
	
	private List<GenericLink> gLinks = new ArrayList<GenericLink>();
	private Map<GenericLink, PathConstraints> pcons = new HashMap<GenericLink, PathConstraints>();
	private long capacity;
	
	/**
	 * Deafult constructor
	 */
	public IntradomainPath() {
		
	}
	
	/**
	 * @return the identifier of the path
	 */
	public long getPathId() {
		return pathId;
	}

	/**
	 * @param pathId the path identifier to set
	 */
	public void setPathId(long pathId) {
		this.pathId = pathId;
	}

	/**
	 * @return List of generic links that the path contains
	 */
	public List<GenericLink> getLinks() {
		return gLinks;
	}

	/**
	 * @param links the list of generic links to set
	 */
	public void setLinks(List<GenericLink> links) {
		gLinks = links;
	}

	/**
	 * @return Capacity of the path (in bps)
	 */
	public long getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	/**
	 * Returns the contraints corresponding with the given generic link.
	 * 
	 * @param glink Generic link of the path
	 * @return PathConstraints object with network constraints for given link 
	 */
	public PathConstraints getConstraints(GenericLink glink) {
		return pcons.get(glink);
	}

	/**
	 * Adds a generic link to the path and assigns constraints to it.
	 * 
	 * @param glink Generic Link object
	 * @param pcon PathConstraints object - constraints
	 */
	public void addGenericLink(GenericLink glink, PathConstraints pcon) {
		gLinks.add(glink);
		if(pcon != null)
			pcons.put(glink, pcon);
	}

	/**
	 * 
	 * @return
	 */
	public PathConstraints getIngressConstraints() {
		if(pcons.size() < 1)
			return null;
		
		return pcons.get(gLinks.get(0));
	}

	/**
	 * 
	 * @return
	 */
	public PathConstraints getEgressConstraints() {
		if(pcons.size() < 1)
			return null;

		return pcons.get(gLinks.get(gLinks.size() - 1));
	}
	
	/**
	 * 
	 * @return
	 */
	public GenericLink getFirstLink() {
		if(gLinks.size() < 1)
			return null;

		return gLinks.get(0);
		
	}

	/**
	 * 
	 * @return
	 */
	public GenericLink getLastLink() {
		if(gLinks.size() < 1)
			return null;

		return gLinks.get(gLinks.size() - 1);
		
	}
	
	/**
	 * 
	 * @param gl
	 * @param pcon
	 */
	public void setPathConstraints(GenericLink gl, PathConstraints pcon) {
		if(pcon != null)
			pcons.put(gl, pcon);
	}
	
	/**
	 * Returns merged constraints for the whole path - that means intersection
	 * of the constraints of all links of the path.
	 * 
	 * @return PathConstraints object
	 */
	public PathConstraints getMergedConstraints() {
		
		if(pcons.size() < 1)
			return null;
		
		PathConstraints pcon = new PathConstraints();
		
		for(GenericLink gl : gLinks) {
			if(pcon == null)
				return null;
			
			pcon = pcon.intersect(pcons.get(gl));
		}
		
		return pcon;
	}
	
	/**
	 * Checks whether the path contains any of generic link from the given collection.
	 * 
	 * @param excluded Collection of generic links
	 * @return boolean result
	 */
	public boolean containsAny(Collection<GenericLink> excluded) {
		for(GenericLink gl : excluded) {
			if(gLinks.contains(gl))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return getLinks().size();
	}
	
	public String getInfo() {
		StringBuffer res = new StringBuffer();
		res.append("Links:\n");
		for(GenericLink glink : getLinks()) {
			res.append("   " + glink + "; " + pcons.get(glink) + "\n");
		}
		
		res.delete(res.lastIndexOf("\n"), res.length());
		
		return res.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("Links: ");
		for(GenericLink glink : getLinks()) {
			res.append(glink + ", ");
		}
		
		if(res.lastIndexOf(", ") >= 0)
			res.delete(res.lastIndexOf(", "), res.length());
		
		return res.toString();
	}

	public int compareTo(IntradomainPath o) {
		return this.gLinks.size() - o.gLinks.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gLinks == null) ? 0 : gLinks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final IntradomainPath other = (IntradomainPath) obj;
		if (gLinks == null) {
			if (other.gLinks != null)
				return false;
		} else if (!gLinks.equals(other.gLinks))
			return false;
		return true;
	}
}