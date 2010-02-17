package net.geant.autobahn.autoBahnGUI.web;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import net.geant.autobahn.autoBahnGUI.model.googlemaps.CastorGoogleTopologyMarshaler;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.TopologyMarshaler;

import org.springframework.web.servlet.view.xslt.AbstractXsltView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class is responsible for creating XML view of JRA3 topology
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class MapXMLView extends AbstractXsltView {
	
	/**
	 * Custom handler for creating xml output
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return XML document src
	 */
	protected Source createXsltSource(Map model, String root,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		 TopologyMarshaler marshaler = new CastorGoogleTopologyMarshaler();
		 Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		 Element element = doc.createElement("root");
		 Topology topology= (Topology)model.get("topology");
		 if (topology!= null){
			 marshaler.topologyToDOMNode(element, topology);
			 doc.appendChild(element);
		 }
		 return new DOMSource (doc);
	}
	
}
