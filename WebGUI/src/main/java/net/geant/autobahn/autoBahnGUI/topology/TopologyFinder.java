package net.geant.autobahn.autoBahnGUI.topology;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.geant.autobahn.administration.Neighbor;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.manager.InterDomainManager;
import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Line;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Marker;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;

/**
 * Class creates topology model from IDM informahttp://www.pornhub.com/communitytion. 
 * Topology is updated by manager after statusUpdate appear
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class TopologyFinder implements TopologyFinderNotifier{
	
	/**
	 * Identifies manager module
	 */
	Manager manager;
	/**
	 * Logs informations
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Time after which the IDM is mark as not accessible
	 */
	public static final long DEFAULT_DOWN_TIME = 10000;
	
	/**
	 * Topology representation
	 */
	private Topology topology = new Topology();
  	
	/**
	 * Updates topology by analysing manager data
	 */
	public synchronized void updateTopology (){
		new Thread(new Runnable() {
            public void run() {
            	synchronized (topology) {
            		createTopology();
				}
            }
        }).start();
	}
	
	/**
	 * Get created topology
	 * @return topology data
	 */
	public Topology getGoogleTopology (){
		Topology top=new Topology();
		top = topology;
		return top;
	}
	/**
	 * Get created topology with service presentation
	 * 
	 * @param domain JRA2 IDM identifier 
	 * @param serviceId	service identifier
	 * @return topology data
	 */
	public Topology getGoogleTopology(String domain,String serviceId){
		Topology top=new Topology();
		top.addTopology (topology);
		top.getLines().removeAll();
		InterDomainManager idm = manager.getInterDomainManager(domain);
		if (idm==null)
			return top;
		if (serviceId !=null)
		{
			Service service= idm.getService(serviceId);
			if (service!= null){
				List<Reservation> reservations =service.getReservations();
				if (reservations==null || reservations.isEmpty())
					return top;
				InterDomainManager startDomain=null;
				InterDomainManager endDomain=null;
				
				Line line = null;
				
				int lenght = reservations.size();
				for (int i=0;i<lenght;i++){
					Path path = reservations.get(i).getPath();
					if (path == null)
						continue;
					List<Link> links= path.getLinks();
					if (links==null)
						continue;
					
					int linkLenght = links.size();
					Link link =null;
					for (int j=0;j<linkLenght;j++)
					{
						link = links.get(j);
						if (link == null)
							continue;
						float startLatitude =0;
						float startLongitude =0;
						float endLatitude = 0;
						float endLongitude = 0;
						startDomain= manager.getInterDomainManager(link.getStartPort().getNode().getProvisioningDomain().getAdminDomain().getBodID());
							if (startDomain== null){
								if (link.getStartPort().getBodID().equals("10.10.32.23")||link.getStartPort().getBodID().equals("10.10.32.22")){
									startLatitude=(float)40.722580;
									startLongitude=(float)-74.006310;
								}
								else
									continue;
							}else{
							startLatitude = startDomain.getStatus().getLatitude();
							startLongitude = startDomain.getStatus().getLongitude();
							}
							endDomain = manager.getInterDomainManager(link.getEndPort().getNode().getProvisioningDomain().getAdminDomain().getBodID());
							if (endDomain == null ){
								if (link.getEndPort().getBodID().equals("10.10.32.23")||link.getEndPort().getBodID().equals("10.10.32.22")){
									endLatitude=(float)40.722580;
									endLongitude=(float)-74.006310;
								}
								else
									continue;
								
							}else
							{
								endLatitude = endDomain.getStatus().getLatitude();
								endLongitude = endDomain.getStatus().getLongitude();
							}
					line = new Line();
					line.setStartLattitude(""+startLatitude);
					line.setStartLongitude(""+startLongitude);
				
					line.setEndLattitude(""+endLatitude);
					line.setEndLongitude(""+endLongitude);
					line.setColor(Line.getReservationLineColor(reservations.get(i).getIntState()));
					line.setTickness("8");
					line.setOblique(0);
					top.addLine(line);
					}
				}
			}	
		}
		return top;
	}
	/**
	 * Creates topology based on manager data
	 */
	private void createTopology() {
		topology.removeAll();
		//shouldAddAmerica=false;
		Marker marker = null;
		Line line = null;
		String name=null;
		if (manager==null){
			return;
		}
		List<String> idmsNames = manager.getAllInterdomainManagers();
		if (idmsNames == null)
			return;
		int length = idmsNames.size();
		InterDomainManager idm = null;
		InterDomainManager neighbourIdm=null;
		long currentTime = System.currentTimeMillis();
		for (int i=0;i<length;i++){
			idm = manager.getInterDomainManager(idmsNames.get(i));
			Status status = idm.getStatus();
			if (status != null)
			{
				boolean isActive = currentTime-idm.getLastStatusUpdateInMillis()<manager.getTearDownTime();
				marker = createMarker (name,status.getLatitude(),status.getLongitude(),(isActive?Marker.DEFAULT_ICON_ACTIVE:Marker.DEFAULT_ICON_DEACTIVE), createHTMLNodeInfo(idm.getIdentifier(),status));			
				topology.addMarker(marker);
				List<Neighbor> neighbors = status.getNeighbors();
				if (neighbors==null || neighbors.isEmpty())
					continue;				
				int lengthN = neighbors.size();
				
				// Read LS location from properties
		        Properties properties = new Properties();
		        try {
		            InputStream is = getClass().getClassLoader().getResourceAsStream(
		                        "../etc/webgui.properties");
		            properties.load(is);
		            is.close();
		            logger.debug(properties.size() + " properties loaded");
		        } catch (IOException e) {
		            logger.info("Could not load lookuphost properties: " + e.getMessage());
		        }
		        String host = properties.getProperty("lookuphost");
		        LookupService lookup = new LookupService(host);
		        
				Status statusNeighbor;
				for (int j=0;j<lengthN;j++){
					
				    String idmLocation = "";
				    if (host != null && host != "") {
    					try {
    						String temp = lookup.QueryIdmLocation(neighbors.get(j).getDomain());
    						idmLocation = temp.replace("\n",""); //Without escape characters included
    					} catch (LookupServiceException e) {
    						logger.info("Lookup query failed: " + e.getMessage());
    					}
			        }
					
			        if (idmLocation != null && !idmLocation.equals("")) {
			            // If the Lookup contained the required information, use it
	                    neighbourIdm = manager.getInterDomainManager(idmLocation);
			        }
			        else {
			            logger.debug("IDM location could not be obtained from Lookup, falling back to direct contact");
	                    neighbourIdm = manager.getInterDomainManager(neighbors.get(j).getDomain());
			        }
                    
					if (neighbourIdm==null)
						continue;
					statusNeighbor = neighbourIdm.getStatus();
					isActive = currentTime-neighbourIdm.getLastStatusUpdateInMillis()<manager.getTearDownTime();
					if (statusNeighbor!= null){
							
							Link link =neighbors.get(j).getLink();
							
							double lattitude_midle = (Math.abs(status.getLatitude()-statusNeighbor.getLatitude()))/2		+Math.min(status.getLatitude(),statusNeighbor.getLatitude());
							double longitude_midle = (Math.abs(status.getLongitude()-statusNeighbor.getLongitude()))/2+	Math.min(status.getLongitude(),statusNeighbor.getLongitude());		
							if (link == null)
								continue;
							marker = createMarker(link.getBodID(), lattitude_midle, longitude_midle, Marker.DEFAULT_INFO, createHTMLLinkInfo(link));
							topology.addMarker(marker);
							
							line = new Line();
							line.setStartLattitude(""+status.getLatitude());
							line.setStartLongitude(""+status.getLongitude());
						
							line.setEndLattitude(""+lattitude_midle);
							line.setEndLongitude(""+longitude_midle);
							line.setColor(Line.DEFAULT_COLOR_ACTIVE);
							line.setTickness("3");
							line.setOblique(0);
							topology.addLine(line);
							line = new Line();
							line.setStartLattitude(""+lattitude_midle);
							line.setStartLongitude(""+longitude_midle);
						
							line.setEndLattitude(""+statusNeighbor.getLatitude());
							line.setEndLongitude(""+statusNeighbor.getLongitude());
							line.setColor(isActive?Line.DEFAULT_COLOR_ACTIVE:Line.DEFAULT_COLOR_DEACTIVE);
							line.setTickness("3");
							line.setOblique(0);
							topology.addLine(line);
					}
				}
			}
				//topology.addMarker(new Marker ("40.722580", "-74.006310",prepareHTMLNodeInfo(),"New york",Marker.DEFAULT_ICON_NOT_MONITORED));
				//topology.addLine(new Line ("40.722580", "-74.006310","51.2953","0.055","3","#000000",0));
		}
	}
	/**
	 * Creates map marker
	 * @param name	name of the marker
	 * @param latitude	geographical latitude
	 * @param longitude geographical longitude
	 * @param icon	icon path
	 * @param html	text show in hint
	 * @return created marker
	 */
	private Marker createMarker (String name, double latitude,double longitude, String icon, String html ){
		Marker marker = new Marker();
		marker.setLabel(name);
		marker.setLatitude(""+latitude);
		marker.setLongitude(""+longitude);
		marker.setIcon(icon);
		marker.setHtml(html);
		return marker;
		
	}
	/**
	 * Creates the HTML info for Link
	 *  
	 * @param link Link object
	 * @return String with HTML info
	 */
	private String createHTMLLinkInfo (Link link){
		StringBuffer buffer= new StringBuffer();	
		buffer.append("<h3 valign=\"middle\" ><image src=\"").append(Marker.DEFAULT_INFO_BIG).append("\">Link information:</h3>");
		buffer.append("<br/><hr/>");
		buffer.append("<ul>");
		buffer.append("<div id=\"form\">");
		buffer.append ("<li><strong>Name:  </strong>").append(link.getBodID()).append("</li>");
		buffer.append ("<li><strong>Capacity:  </strong>").append(link.getCapacity()).append("</li>");
		buffer.append ("<li><strong>Delay:  </strong>").append(link.getDelay()).append("</li>");
		buffer.append ("<li><strong>Granularity:  </strong>").append(link.getGranularity()).append("</li>");
		buffer.append ("<li><strong>Manual cost:  </strong>").append(link.getManualCost()).append("</li>");
		buffer.append ("<li><strong>Bidirectional:  </strong>").append(link.isBidirectional()).append("</li>");
		buffer.append ("<li><strong>Min resource capacity:  </strong>").append(link.getMinResCapacity()).append("</li>");
		buffer.append ("<li><strong>Max resource capacity:  </strong>").append(link.getMaxResCapacity()).append("</li>");
		buffer.append ("<li><strong>Resilience:  </strong>").append(link.getResilience()).append("</li>");
		buffer.append ("<li><strong>Start port:  </strong>").append(link.getStartPort().getBodID()).append("</li>");
		buffer.append ("<li><strong>End port:  </strong>").append(link.getEndPort().getBodID()).append("</li>");
		buffer.append("</div>");
		buffer.append("</ul>");
		return buffer.toString();
	}
	/**
	 * Creates the HTML info for IDM Node 
	 *  
	 * @param name	name of the IDM
	 * @param state current IDM status
	 * @return String with HTML info
	 */
	private String createHTMLNodeInfo(String name,Status state) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<h3  valign=\"middle\"><image src=\"").append(Marker.DEFAULT_ICON_ACTIVE).append("\">Domain information:</h3>");
		buffer.append("<br/><hr/>");
		buffer.append("<ul>");
		buffer.append("<div id=\"form\">");
		buffer.append ("<li><strong>Name:  </strong>").append(state.getDomain()).append("</li>");
		buffer.append ("<li><strong>Latitude:  </strong>").append(state.getLatitude()).append("</li>");
		buffer.append ("<li><strong>Longitude:  </strong>").append(state.getLongitude()).append("</li>");
		buffer.append ("<li><strong>Show services:  </strong>").append("<a href=\"reservations.htm?domain=").append(name).append("\">services</a>").append("</li>");
		buffer.append ("<li><strong>Show settings:  </strong>").append("<a href=\"settings.htm?domain=").append(name).append("\">info</a>").append("</li>");
		buffer.append ("<li><strong>Show logs:  </strong>").append("<a href=\"logs.htm?domain=").append(name).append("\">logs</a>").append("</li>");
		buffer.append("</div>");
		buffer.append("</ul>");	
		return buffer.toString();
		
	}
	/**
	 * Creates the HTML info for other than JRA2 IDM Node 
	 *  
	 * @return String with HTML info
	 */
	private String prepareHTMLNodeInfo() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<h3  valign=\"middle\"><image src=\"").append(Marker.DEFAULT_ICON_NOT_MONITORED).append("\">Domain information:</h3>");
		buffer.append("<br/><hr/>");
		buffer.append("<ul>");
		buffer.append("<div id=\"form\">");
		buffer.append ("<li><strong>Name:  </strong>").append("grnet.gr").append("</li>");
		buffer.append ("<li><strong>Latitude:  </strong>").append("37.979741").append("</li>");
		buffer.append ("<li><strong>Longitude:  </strong>").append("23.715878").append("</li>");
		buffer.append("</div>");
		buffer.append("</ul>");	
		return buffer.toString();
		
	}
	
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
