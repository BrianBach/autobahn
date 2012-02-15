/*
 * Port.java
 *
 * 2006-08-30
 */
package net.geant.autobahn.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Port class represents a physical or abstract interface on a node.
 * 
 * Regular port represents real network interface, with proper address, while an
 * abstract port is used to represent aggregated complexed intra-domain topology
 * in inter-domain communication. As there is no need to expose information
 * concerning intra-domain topology, intra-domain paths are translated into
 * abstract links connecting two abstract ports.
 * 
 * After establishing a path, abstract ports and links are translated back into
 * intra-domain specific topology and reservation is being processed further at
 * intra-domain level.
 * 
 * Each Node object of a AdminDomain contains an abstract Port.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Port", namespace="network.autobahn.geant.net", propOrder={
		"bodID", "description", "technology", "bundled", "node"
})
public class Port implements Serializable, Comparable<Port> {

    private static final long serialVersionUID = 572057231833651705L;

    private String bodID;
    
    private String description;
    private String technology;
    private boolean bundled;
    
    private Node node;
    
    /**
     * Default constructor
     */
    public Port() {
        super();
    }

    /**
     * Creates the Port object, assigned to the specific Node
     * 
     * @param bodAddress
     *            String address used by Autobahn system
     * @param technology
     *            String technology of the port {Fibre | WDM | SDH | SONET |
     *            Ethernet | IP}
     * @param bundled
     *            boolean whether the port is to be bundled
     * @param node
     *            Node node object that the port belongs to
     * @param description
     *            String user-friendly description of the port. If no description is
     *            supplied, the bodAddress will be used
     */
    public Port(String bodAddress, String technology, boolean bundled, Node node, String description) {
        this();
        this.bodID = bodAddress;
        this.technology = technology;
        this.bundled = bundled;
        this.node = node;
        this.description = (description != null) ? description : bodAddress;
    }

    /**
     * Creates the Port object, assigned to the specific Node
     * 
     * @param bodAddress
     *            String address used by Autobahn system
     * @param technology
     *            String technology of the port {Fibre | WDM | SDH | SONET |
     *            Ethernet | IP}
     * @param bundled
     *            boolean whether the port is to be bundled
     * @param node
     *            Node node object that the port belongs to
     */
    public Port(String bodAddress, String technology, boolean bundled, Node node) {
        this(bodAddress, technology, bundled, node, null);
    }

    /**
     * The description of the port.
     * 
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the port.
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The address that is used to access the port by Autobahn's modules
     * 
     * @return Returns the bodAddress.
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * Sets the address that is used to access the port by Autobahn's modules
     * 
     * @param bodAddress The bodAddress to set.
     */
    public void setBodID(String bodAddress) {
        this.bodID = bodAddress;
    }

    /**
     * It states whether this is a "bundled" port or not, that is a port that
     * comprises at least two physical or virtual ports. 
     * 
     * @return boolean true if the port os bundled, false - otherwise.
     */
    public boolean isBundled() {
        return bundled;
    }

    /**
     * Sets whether this is a "bundled" port or not, that is a port that
     * comprises at least two physical or virtual ports. 
     * 
     * @param bundled The bundled to set.
     */
    public void setBundled(boolean bundled) {
        this.bundled = bundled;
    }

    /**
     * Returns the Node that the Port belongs to
     * 
     * @return Returns the node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Sets the Node that the Port is to belong to
     * 
     * @param node The node to set.
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Technology of the port. It identifies the networking technology that the
     * port uses.
     * 
     * @return Returns the technology.
     */
    public String getTechnology() {
        return technology;
    }

    /**
     * Sets a technology of the port. It identifies the networking technology
     * that the port uses. Possible variants are: {Fibre | WDM | SDH | SONET |
     * Ethernet | IP}
     * 
     * @param technology
     *            The technology to set.
     */
    public void setTechnology(String technology) {
        this.technology = technology;
    }
    
    /**
     * Returns identifier of AdminDomain that the port belongs to
     * 
     * @return String identifier of AdminDomain
     */
    public String getAdminDomainID() {
        return node.getAdminDomainID();
    }
    
    /**
     * Indicates whether the port is a client port, or not
     * 
     * @return boolean true if the port is a client port, false otherwise
     */
    public boolean isClientPort() {
        return node.isClientNode();
    }
    
    /**
     * Indicates whether the port is an IDCP port, or not
     * 
     * @return boolean true if the port is an IDCP port, false otherwise
     */
    public boolean isIdcpPort() {
        return node.isIdcpNode();
    }
    
    /**
     * Indicates whether the port is an abstract port
     * 
     * @return boolean true if the port is an abstract port, false otherwise
     */
/*    public boolean isAbstract() {
        return "abstract".equals(address);
    }*/
    
    /**
     * Indicates whether the port belongs to the same domain that port2 does
     * 
     * @return boolean true if the port belongs to the same domain that port2,
     *         false otherwise
     */
    public boolean sameDomain(Port port2) {
        return node.sameDomain(port2.getNode());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || !(obj instanceof Port)) {
            return false;
        }
        
        Port p2 = (Port) obj;
        
        return getBodID().equals(p2.getBodID());
    }

    @Override
    public int hashCode() {
        return getBodID().hashCode();
    }

    @Override
    public String toString() {
        return bodID;
    }

    @Override
    public int compareTo(Port p2) {
        if (p2 == null) {
            return 1;
        }
        if (this.getAdminDomainID() == null) {
            return -1;
        }
        if (this.getAdminDomainID().compareTo(p2.getAdminDomainID()) != 0) {
            return this.getAdminDomainID().compareTo(p2.getAdminDomainID());
        } else {
            if (this.getBodID() == null) {
                return -1;
            }
            return this.getBodID().compareTo(p2.getBodID());
        }
    }
}
