package net.geant.autobahn.intradomain.converter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Statistics returned by TopologyConverter.abstractInternalPartOfTopology(), used for profiling
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Stats", namespace="converter.intradomain.autobahn.geant.net", propOrder={
        "numNodes", "numEdgeNodes", "numLinks", "numPaths", "calcTime"
})
public class Stats {
    
    public int numNodes; // number of input nodes
    public int numEdgeNodes; // number of output edge nodes
    public int numLinks; // number of input links (spanning trees)
    public int numPaths; // number of output paths (found by GraphSearch)
    public float calcTime; // time it took to calculate paths
    
    public Stats(int numNodes, int numEdgeNodes, int numLinks, 
                 int numPaths, float calcTime) {
        
        this.numNodes = numNodes;
        this.numEdgeNodes = numEdgeNodes;
        this.numLinks = numLinks;
        this.numPaths = numPaths;
        this.calcTime = calcTime;
    }
    
    public Stats() { }
}
