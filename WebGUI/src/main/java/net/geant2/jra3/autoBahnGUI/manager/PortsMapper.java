package net.geant2.jra3.autoBahnGUI.manager;


/**
 * Maps the virtual port name to real one and vice versa
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface PortsMapper {
	/**
	 * Converts the real name of the port to virtual one
	 * 
	 * @param port real name	
	 * @return  virtual name
	 */
	public String mapPort (String port);
	/**
	 * Converts the virtual name of the port to real one
	 * @param mapping virtual name
	 * @return real name
	 */
	public String demapPort (String mapping);
}
