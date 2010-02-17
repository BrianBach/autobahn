package net.geant.autobahn.intradomain.pathfinder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * Class represents an edge between two graph nodes. They are connected through
 * the network link that have some network constraints.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="GraphEdge", namespace="net.geant.autobahn.intradomain.pathfinder", propOrder={
        "start", "end", "link", "pcons"
})
public class GraphEdge {

	private GraphNode start;
	private GraphNode end;
	private GenericLink link;
	private PathConstraints pcons;

	/**
	 * Creates an instance.
	 * 
	 * @param start Start node of the edge
	 * @param end End node of the edge
	 * @param link Connecting link
	 * @param pcons Network constraints for the link
	 */
	public GraphEdge(GraphNode start, GraphNode end, GenericLink link,
			PathConstraints pcons) {
		super();
		this.start = start;
		this.end = end;
		this.link = link;
		this.pcons = pcons;
	}

	/**
	 * @return start node of the edge
	 */
	public GraphNode getStartNode() {
		return start;
	}

	/**
	 * Sets the start node of the edge
	 * 
	 * @param start node
	 */
	public void setStartNode(GraphNode start) {
		this.start = start;
	}

	/**
	 * @return start node of the edge
	 */
	public GraphNode getEndNode() {
		return end;
	}

	/**
	 * Sets the end node of the edge
	 * 
	 * @param end node
	 */
	public void setEndNode(GraphNode end) {
		this.end = end;
	}

	/**
	 * @return link between nodes
	 */
	public GenericLink getLink() {
		return link;
	}

	/**
	 * Sets link between nodes
	 * 
	 * @param link
	 */
	public void setLink(GenericLink link) {
		this.link = link;
	}

	/**
	 * @return Network constraints for the link connecting nodes
	 */
	public PathConstraints getConstraints() {
		return pcons;
	}

	/**
	 * Sets the network constraints for the edge
	 * 
	 * @param pcons
	 */
	public void setConstraints(PathConstraints pcons) {
		this.pcons = pcons;
	}

	/**
	 * @return Capacity of the link
	 */
	public long getCapacity() {
		return getLink().getCapacity();
	}
	
	/**
	 * Indicates whether the edge connects nodes from different domains.
	 * 
	 * @return boolean
	 */
	public boolean isInterdomain() {
		return getLink().isInterdomain();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getLink() == null) ? 0 : getLink().hashCode());
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
		final GraphEdge other = (GraphEdge) obj;
		if (getLink() == null) {
			if (other.getLink() != null)
				return false;
		} else if (!getLink().equals(other.getLink()))
			return false;
		return true;
	}
}
