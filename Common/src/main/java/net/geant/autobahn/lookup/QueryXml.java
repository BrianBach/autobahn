package net.geant.autobahn.lookup;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.geant.autobahn.resources.ResourcePath;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryXml extends XmlHandler {
    static public String xmlFileName = "etc/xml/basicQuery.xml";

    static private final String queryNamespaces = "declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; "
            + "declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; "
            + "declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; "
            + "declare namespace xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; "
            + "declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; "
            + "declare namespace lookup=\"ru6.cti.gr\"; ";

    static private final String search = " for $m in /nmwg:store/nmwg:data ";
    static private final String prefix = "$m/nmwg:metadata/perfsonar:subject/nmwgt:interface/lookup:";
    static private final String searchKeysPref = " for $j in /nmwg:store/nmwg:metadata let $a := ";
    static private final String searchKeysSuff = " return  data($m/@metadataIdRef) where $j/@id = $a ";

    private String returnString = "";
    private String queryString = "";

    private Boolean searchForKeys = false;

    static private Logger log = Logger.getLogger(QueryXml.class);

    public QueryXml() throws SAXException, IOException, ParserConfigurationException {
        super(new File(new ResourcePath().getFullPath(xmlFileName)));
    }

    public QueryXml(String interf) throws Exception {
        this();
        setReturnType(interf);
    }

    public QueryXml(String interf, String field, String value) throws Exception {
        this(interf);
        setWhere(field, value);
    }

    public void setReturnType(String s) throws Exception {
        if (searchForKeys) {
            throw new Exception("can not set custom return type whene searching for keys");
        }
        returnString = "return " + prefix + s;
    }

    public void setWhere(String field, String value) {
        queryString += "where " + query(field, value);
    }

    public void connectAnd(String field, String value) throws Exception {
        if (!queryString.startsWith("where")) {
            log.error("Ask 'and connection' without previous query");
            throw new LookupServiceException("Ask 'and connection' without previous query");
        }
        queryString += " and " + query(field, value);
    }

    public String getXml() throws Exception {
        NodeList l = doc.getElementsByTagName("xquery:subject");

        // should exist exactly 1 node
        if (l == null || l.getLength() != 1) {
            log.error("Error on xml sample file");
            throw new LookupServiceException("Error on xml sample file");
        }

        Node node = l.item(0);
        String xmlString = search + queryString;
        if (searchForKeys) {
            xmlString = searchKeysPref + xmlString + searchKeysSuff;
        }

        if (returnString.isEmpty()) {
            throw new LookupServiceException("Error no return type for query");
        }
        xmlString = queryNamespaces + xmlString + returnString;

        node.setTextContent(xmlString);
        return super.getXml();
    }

    private String query(String field, String value) {
        return prefix + field + " = \"" + value + "\" ";
    }

    void searchKeys() {
        searchForKeys = true;
        returnString = " return $j/perfsonar:subject/psservice:service/psservice:accessPoint ";
    }

}
