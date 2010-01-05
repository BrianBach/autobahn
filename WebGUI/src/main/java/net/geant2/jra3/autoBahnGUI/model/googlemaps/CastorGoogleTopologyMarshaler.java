package net.geant2.jra3.autoBahnGUI.model.googlemaps;

import java.io.IOException;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Class is responsible for marshaling Topology information into  XML file
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class CastorGoogleTopologyMarshaler implements TopologyMarshaler {

	/**
	 * Mapping strategy
	 */
	private Mapping mapping;
	/**
	 * Logs information
	 */
	private Logger logger = Logger.getLogger(CastorGoogleTopologyMarshaler.class);
	
	/**
	 * Creates object
	 */
	public CastorGoogleTopologyMarshaler(){
		InputSource mappingResource = new InputSource(getClass().getResourceAsStream("topologymapping.xml"));
		mapping = new Mapping();
        mapping.loadMapping(mappingResource);	
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.autoBahnGUI.model.googlemaps.TopologyMarshaler#topologyToString(net.geant2.jra3.autoBahnGUI.model.googlemaps.Topology)
	 */
	public String topologyToString(Topology topology) {
		String result=null;
		Marshaller marshaller;
		StringWriter writer = new StringWriter();
		try {
			marshaller = new Marshaller(writer);
			marshaller.setMapping(mapping);
	        marshaller.marshal(topology);
	        result = writer.toString();
		} catch (IOException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		} catch (MappingException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		} catch (MarshalException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		} catch (ValidationException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		}
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.autoBahnGUI.model.googlemaps.TopologyMarshaler#topologyToDOMNode(org.w3c.dom.Node, net.geant2.jra3.autoBahnGUI.model.googlemaps.Topology)
	 */
	public void topologyToDOMNode (Node node, Topology topology){
		Marshaller marshaller;
		if (node==null)
			node=null;
		try {
			marshaller = new Marshaller(node);
			marshaller.setMapping(mapping);
	        marshaller.marshal(topology);
		} catch (MappingException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		} catch (MarshalException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		} catch (ValidationException e) {
			logger.error(e.getClass().getName()+":"+e.getMessage());
		}
	}
}
