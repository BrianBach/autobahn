package net.geant.autobahn.intradomain.topology;


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
