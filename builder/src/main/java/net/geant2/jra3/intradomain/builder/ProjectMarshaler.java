package net.geant2.jra3.intradomain.builder;

/**
 * Interface for project marshaler
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface ProjectMarshaler {

		/**
		 * Marshals TopologyProject object 
		 * 
		 * @param	topology	TopologyProject object
		 * @param	path	path to file where topology should be store
		 * @throws CannotStoreFileException
		 */
		public void marshal (TopologyProject topology, String path) throws CannotStoreFileException;		
		/**
		 * Unmarshals TopologyProject object from file
		 * 
		 * @param path	path to file where information about TopologyProject are stored
		 * @return	unmarshaled TopologyProject object
		 * @throws WrongFileFormatException
		 */
		public TopologyProject unmarshal (String path) throws WrongFileFormatException;
		
}
