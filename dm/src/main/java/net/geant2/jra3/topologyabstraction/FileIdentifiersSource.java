/*
 * FileIdentifiersSource.java
 *
 * 2007-05-10
 */
package net.geant2.jra3.topologyabstraction;

import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.converter.PublicIdentifiersMapping;
import java.io.IOException;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class FileIdentifiersSource implements ExternalIdentifiersSource {

	private PublicIdentifiersMapping map = null;
	
	public FileIdentifiersSource(String path) throws IOException {
		this.map = new PublicIdentifiersMapping(path);
	}
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.ExternalIdentifiersSource#getIdentifiers(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String domain, String portName,
			String linkBodId) {
		
		String ids = map.getIdentifierFor(portName);
		//String ids = null;
		ids = ids.substring(1, ids.length() - 1);
		String[] aaa = ids.split(",");
		
		LinkIdentifiers result = new LinkIdentifiers();
		result.setNodeId(aaa[0]);
		result.setPortId(aaa[1]);
		result.setLinkId(aaa[2]);
		
		return result;
	}
}
