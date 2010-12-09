package net.geant.autobahn.intradomain.topology;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.SdhPort;
import net.geant.autobahn.intradomain.sdh.StmLink;

/**
 * Class contains all SDH topology date created by the 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
@XStreamAlias("sdh-topology")
public class SDHTopology {
	/**
	 * List of created devices
	 */
	@XStreamImplicit
	ArrayList <SdhDevice> devices = new ArrayList<SdhDevice>();
	/**
	 * List of created ports
	 */
	@XStreamImplicit
	ArrayList <SdhPort> ports = new ArrayList<SdhPort>();
	/**
	 * List of created links
	 */
	@XStreamImplicit
	ArrayList <StmLink> links = new ArrayList<StmLink>();
		
	public void addSdhDevice (SdhDevice device){
		if (device!= null)
			devices.add(device);
	}
	public void addSdhPort (SdhPort port){
		if (port!= null)
			ports.add(port);
	}
	public void addStmLink (StmLink link){
		if (link!= null)
			links.add(link);
	}
	public void removeSdhDevice (SdhDevice device){
		if (device!= null)
			devices.remove(device);
	}
	public void removeSdhPort (SdhPort port){
		if (port!= null){
			ports.remove(port);
		}
	}
	public void removeStmLink (StmLink link){
		if (link!= null){
			links.remove(link);
		}
	}
	public ArrayList<SdhDevice> getDevices() {
		return devices;
	}
	public void setDevices(ArrayList<SdhDevice> devices) {
		this.devices = devices;
	}
	public ArrayList<SdhPort> getPorts() {
		return ports;
	}
	public void setPorts(ArrayList<SdhPort> ports) {
		this.ports = ports;
	}
	public ArrayList<StmLink> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<StmLink> links) {
		this.links = links;
	}
	
}
