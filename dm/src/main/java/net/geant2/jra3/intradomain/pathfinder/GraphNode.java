/**
 * GraphNode.java
 * 2007-04-02
 */
package net.geant2.jra3.intradomain.pathfinder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;

/**
 * This class serves as a node in a graph. It is used during finding paths
 * between edge nodes. Each node contains also set of edges linking it with
 * other nodes of the network.
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="GraphNode", namespace="net.geant2.jra3.intradomain.pathfinder", propOrder={
        "internalNode", "edges", "isEdgeNode"
})
public class GraphNode {
    
    private Node internalNode;
    private Set<GraphEdge> edges = new HashSet<GraphEdge>();
    private boolean isEdgeNode;
    
    public GraphNode(Node node) {
        this.internalNode = node;
    }
    
    /**
     * Adds an edge to the node object.
     * 
     * @param edge
     */
    public void addEdge(GraphEdge edge) {
    	edges.add(edge);
    }
    
    /**
     * Adds and edge the the destination node.
     * 
     * @param dest Destination node
     * @param glink Generic link that connects with the dest node
     * @param pcon Network constraints for this link
     */
    public void addEdge(GraphNode dest, GenericLink glink, PathConstraints pcon) {
    	GraphEdge edge = new GraphEdge(this, dest, glink, pcon);
    	edges.add(edge);
    }
    
    /**
     * Returns the edges of the node.
     * 
     * @return collection of edges
     */
    public Collection<GraphEdge> getEdges() {
    	return edges;
    }
    
    /**
     * @return the internalNode
     */
    public Node getInternalNode() {
        return internalNode;
    }

    /**
     * @return the isEdgeNode
     */
    public boolean isEdgeNode() {
        return isEdgeNode;
    }

    /**
     * @param isEdgeNode the isEdgeNode to set
     */
    public void setEdgeNode(boolean isEdgeNode) {
        this.isEdgeNode = isEdgeNode;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((internalNode == null) ? 0 : internalNode.hashCode());
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
		final GraphNode other = (GraphNode) obj;
		if (getInternalNode() == null) {
			if (other.getInternalNode() != null)
				return false;
		} else if (!getInternalNode().equals(other.getInternalNode()))
			return false;
		return true;
	}
}
