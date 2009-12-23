package net.geant2.jra3.topologyabstraction;

import java.util.List;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.converter.Stats;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;

/**
 * Interface describes messages that are being sent by other modules to the
 * Topology Abstraction module.
 * 
 * @author Kostas
 */
@WebService(targetNamespace = "http://topologyabstraction.jra3.geant2.net/", name = "TopologyAbstraction")
public interface TopologyAbstraction {

    /**
     * Abstracts internal part of the domain topology. That means creates
     * abstracted topology and generates identifiers for all network devices
     * inside the domain: nodes, ports, links that are not connected with other
     * domains.
     * 
     * @return stats Statistics of the network
     */
    @WebMethod
    @WebResult(name="stats")
    public Stats abstractInternalPartOfTopology();
    
    /**
     * Send the IntradomainTopology and the topologyType.
     * This method is called by the DM and is used to initialize the
     * Topology Abstraction module.
     * 
     * @param topology
     *            Intradomain Topology object
     * @param topologyType
     *            The string that described the topology type (ethernet, sdh).
     * @return 
     */
    @WebMethod
    @WebResult(name="topology")
    public void setIntradomainTopology(@WebParam(name="topology")IntradomainTopology topology, @WebParam(name="topologyType")String topologyType);

    /**
     * Generates abstract identifiers for edge links and network devices (ports,
     * nodes) that belong to the other domain.
     * 
     * @param idmAddress External source of abstracted identifiers (IDM)
     * @return list of abstract links
     */
    @WebMethod
    @WebResult(name="abstractLinks")
    public List<Link> abstractExternalPartOfTopology(@WebParam(name="idmAddress")String idmAddress); 

    /**
     * 
     * @return
     */
    @WebMethod
    @WebResult(name="abstractLinks")
    public List<Link> getAbstractLinks(); 

    
    /**
     * Return abstracted identifiers for the specified port, node that it
     * belongs to and link that is connected to the port.
     * 
     * @param portName
     *            Intradomain name of the port
     * @param linkBodId
     *            Abstracted identifier of the corresponding link assigned to it
     *            in the second domain.
     * @return Abstracted identifiers for network devices
     */
    @WebMethod
    @WebResult(name="identifiers")
    public LinkIdentifiers getIdentifiers(@WebParam(name = "portName")String portName, 
            @WebParam(name = "linkBodId")String linkBodId);

    /**
     * Returns GenericLink associated with specified interdomain Link. Method
     * can be used only for edge links, that connects with other domain.
     * 
     * @param l
     *            abstract Link
     * @return GenericLink that is associated with specified abstract link
     */
    @WebMethod
    @WebResult(name="genericLink")
    public GenericLink getEdgeLink(@WebParam(name="l")Link l);

    /**
     * Returns all edge links.
     * 
     * @return List of edge links
     */
    @WebMethod
    @WebResult(name="links")
    public Set<Link> getAllEdgeLinks();

}