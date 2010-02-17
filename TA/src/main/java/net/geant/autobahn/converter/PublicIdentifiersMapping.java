package net.geant.autobahn.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class implements a mapping between the actual and the public identifiers of
 * the edge ports of the domain. For some reasons administrators may not want to
 * express confidential network devices identifiers, this class helps to hide it
 * in a form of friendly name.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class PublicIdentifiersMapping {

    private Map<String, String> ids = new HashMap<String, String>();
    
    /**
     * Creates a mapping from a file.
     * 
     * @param path String path to the mapping file.
     * @throws IOException when reading of the file failed
     */
    public PublicIdentifiersMapping(String path) throws IOException {
    	BufferedReader br = new BufferedReader(
    			new FileReader(new File(path)));
    	
    	String line = null;
    	while((line = br.readLine()) != null) {
    		// Skip empty lines and comments
    		if(line.startsWith("#") || line.trim().equals(""))
    			continue;
    		
    		String[] content = line.split("=");
    		
    		if(content.length == 2) {
    			ids.put(content[0], content[1]);
    			// save also inverted mapping
    			ids.put(content[1], content[0]);
    		}
    	}
    }

    /**
     * Returns the real identifier of the port, for specified firendly name.
     * 
     * @param id String firendly name of the port
     * @return String real identifier
     */
    public String getIdentifierFor(String id) {
        return ids.get(id);
    }
}
