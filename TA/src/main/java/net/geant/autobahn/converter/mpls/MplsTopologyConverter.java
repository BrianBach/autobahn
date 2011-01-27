/**
 * 
 */
package net.geant.autobahn.converter.mpls;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.converter.GenericTopologyConverter;
import net.geant.autobahn.converter.InternalIdentifiersSource;
import net.geant.autobahn.converter.PublicIdentifiersMapping;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.mpls.MplsLink;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;

/**
 * Converter for MPLS domains
 * @author PCSS
 */
public class MplsTopologyConverter extends GenericTopologyConverter {
	
	private List<MplsLink> links;
	
	public MplsTopologyConverter(IntradomainPathfinder pathfinder, InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		
		super(pathfinder, internal, mapping, lookuphost);
	}
	
	public MplsTopologyConverter(IntradomainTopology topology, IntradomainPathfinder pathfinder, InternalIdentifiersSource internal, 
			PublicIdentifiersMapping mapping, String lookuphost) {
		
		super(pathfinder, internal, mapping, lookuphost);
		this.setTopology(topology.getMplsLinks(), topology.getNodes());
	}
	
	public MplsTopologyConverter(IntradomainTopology topology, IntradomainPathfinder pathfinder, InternalIdentifiersSource internal, PublicIdentifiersMapping mapping) {
		
		super(pathfinder, internal, mapping);
		this.setTopology(topology.getMplsLinks(), topology.getNodes());
	}

	public void setTopology(List<MplsLink> links, List<Node> nodes) {
		
		this.nodes = nodes;
		
		if (links != null) {
			this.links = links;
			this.genericLinks = new ArrayList<GenericLink>();
		
			for (MplsLink ml : links)
				this.genericLinks.add(ml.getLink());
		}
	}
}
