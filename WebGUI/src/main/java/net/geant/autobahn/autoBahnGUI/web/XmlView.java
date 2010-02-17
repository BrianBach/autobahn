package net.geant.autobahn.autoBahnGUI.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.geant.autobahn.autoBahnGUI.model.googlemaps.CastorGoogleTopologyMarshaler;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.TopologyMarshaler;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlView extends AbstractUrlBasedView
{
  public XmlView(){}
  
  public String getContentType()
  {
    return "text/xml";
  }
  
@Override
protected void renderMergedOutputModel(Map model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	  
	  Logger.getLogger("XMLView").info("Is on rendering view");
	  TopologyMarshaler marshaler = new CastorGoogleTopologyMarshaler();
	  Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	  Element element = doc.createElement("root");
	  Topology topology= (Topology)model.get("topology");
	  if (topology!= null){
			 marshaler.topologyToDOMNode(element, topology);
			 doc.appendChild(element);
	  }
	  
	  Source source = new DOMSource(doc);
      StringWriter stringWriter = new StringWriter();
      Result result = new StreamResult(stringWriter);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.transform(source, result);
      String xmlAsString =stringWriter.getBuffer().toString();
      response.setContentType("text/xml");
	  response.setContentLength(xmlAsString.length());
	  PrintWriter out = new PrintWriter(response.getOutputStream());
	    out.print(xmlAsString);
	    out.flush();
	    out.close();
}
}