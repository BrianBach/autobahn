package net.geant2.jra3.intradomain.topology;

/**
 * Interface for topology marshaler
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface TopologyMarshaler {
	/**
	 *  Marshals topology object
	 * 
	 * @param topology Topology object
	 * @param path path to file where topology will be stored
	 */
	public void marshal (Topology topology, String path) throws CannotStoreFileException;
	/**
	 * UnMarshal topology object
	 * 
	 * @param path path where the topology is stored
	 * @return unmarshaled Topology object
	 */
	public Topology unmarshal ( String path) throws WrongFileFormatException;
}
