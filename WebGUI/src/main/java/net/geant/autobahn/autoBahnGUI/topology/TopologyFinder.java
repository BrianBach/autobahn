package net.geant.autobahn.autoBahnGUI.topology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.geant.autobahn.administration.Neighbor;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.manager.InterDomainManager;
import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.manager.PortMap;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.InterfaceComponent;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Line;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Marker;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.reservation.Reservation;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.util.PortMapper;

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
	@SuppressWarnings("unchecked")
	public Topology getGoogleTopology(String domain,String serviceId){
		
		Topology top=new Topology();
		top.addTopology (topology);
		top.getLines().removeAll();
		top.getMarkers().removeAll();
		Marker marker = null;
		InterDomainManager idm = null;
		List<String> idmsNames = manager.getAllInterdomainManagers();
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < idmsNames.size(); i++) {
			idm = manager.getInterDomainManager(idmsNames.get(i));
			Status status = idm.getStatus();
			
			if (status != null)
			{
				boolean isActive = currentTime-idm.getLastStatusUpdateInMillis()<manager.getTearDownTime();
				marker = createMarker (status.getDomain(),status.getLatitude(),status.getLongitude(),(isActive?Marker.DEFAULT_ICON_ACTIVE:Marker.DEFAULT_ICON_DEACTIVE), createHTMLNodeInfo(idm.getIdentifier(),status));			
				top.addMarker(marker);
			}
		}
		
		List<InterfaceComponent> components = new ArrayList<InterfaceComponent>();
		InterfaceComponent ic = null;
		InterDomainManager startDomain=null;
		InterDomainManager endDomain=null;
		List<String> strings = new ArrayList<String>();
		LookupService lookup = manager.getLookupServiceObject();
		
		try {
			String temp = lookup.QueryIdmLocation(domain);
			String replace = temp.replace("\n",""); //Without escape characters included
			idm = manager.getInterDomainManager(replace);
			
		} catch (LookupServiceException e) {
			logger.info("Lookup query failed: " + e.getMessage());
		}
		if (idm==null)
			return top;
		if (serviceId !=null)
		{
			ServiceType service = idm.getService(serviceId);
			if (service!= null){
				List<Reservation> reservations = new ArrayList<Reservation>(); 
				reservations.addAll(service.getReservations());
				
				if (reservations==null || reservations.isEmpty())
					return top;
	
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

						try {
							String temp = lookup.QueryIdmLocation(link.getStartPort().getNode().getProvisioningDomain().getAdminDomain().getBodID());
							String replace = temp.replace("\n",""); 
							startDomain = manager.getInterDomainManager(replace);
								
						} catch (LookupServiceException e) {
							logger.info("Lookup query failed: " + e.getMessage());
						}
	
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
							try {
								if(link.getEndPort().getNode().getProvisioningDomain().getAdminDomain().isClientDomain()){
									strings.add(startDomain.getStatus().getDomain());
									continue;
								}
								String temp = lookup.QueryIdmLocation(link.getEndPort().getNode().getProvisioningDomain().getAdminDomain().getBodID());
								String replace = temp.replace("\n",""); 
								endDomain = manager.getInterDomainManager(replace);
								
							} catch (LookupServiceException e) {
								logger.info("Lookup query failed: " + e.getMessage());
							}
		
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
		
					if(startLatitude == endLatitude && startLongitude == endLongitude)
						continue;
					
					double lattitude_midle = (Math.abs(startLatitude-endLatitude))/2+Math.min(startLatitude,endLatitude);
					double longitude_midle = (Math.abs(startLongitude-endLongitude))/2+	Math.min(startLongitude,endLongitude);		
					
					marker = createMarker(link.getBodID(), lattitude_midle, longitude_midle, Marker.DEFAULT_INFO, createHTMLLinkInfo(link));
					top.addMarker(marker);
							
					line = new Line();
					line.setStartLattitude(""+startLatitude);
					line.setStartLongitude(""+startLongitude);
					line.setEndLattitude(""+lattitude_midle);
					line.setEndLongitude(""+longitude_midle);
					line.setColor(Line.getReservationLineColor(reservations.get(i).getState()));
					line.setTickness("5");
					line.setOblique(0);
					top.addLine(line);
					
					line = new Line();
					line.setStartLattitude(""+lattitude_midle);
					line.setStartLongitude(""+longitude_midle);
					line.setEndLattitude(""+endLatitude);
					line.setEndLongitude(""+endLongitude);
					line.setColor(Line.getReservationLineColor(reservations.get(i).getState()));
					line.setTickness("5");
					line.setOblique(0);
					top.addLine(line);
					
					ic = new InterfaceComponent(startDomain.getStatus().getDomain(),startLatitude,startLongitude,endLatitude,endLongitude,
							1,reservations.get(i).getState());
					components.add(ic);
					
					ic = new InterfaceComponent(endDomain.getStatus().getDomain(),endLatitude,endLongitude,startLatitude,startLongitude,
							1,reservations.get(i).getState());
					components.add(ic);

					}
				}	
			}
		}
		if(components != null && strings != null)
			top.addTopology(setInterfaceInReservationTopology(components, strings));
		
		return top;
	}
	@SuppressWarnings("unchecked")
	public Topology setInterfaceInReservationTopology(List<InterfaceComponent> components, List<String> strings){
		
		Map<String, InterfaceComponent> interfaces = new HashMap<String, InterfaceComponent>();
		for (int i = 0; i < components.size(); i++) {
			if(strings.contains(components.get(i).getName()))
				interfaces.put(components.get(i).getName(), components.get(i));		
		}
	
		Line line = null;
		Topology top = new Topology();

		Marker marker = null;
		InterfaceComponent ic = null;	
		Set set = interfaces.entrySet();
		Iterator it = set.iterator();
			while(it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				ic = interfaces.get(me.getKey());
				if(ic!=null){		
					double biring = getBearingBetween2Points(ic.getStartLatitude(),ic.getStartLongitude(),ic.getEndLatitude(),ic.getEndLongitude());
					biring = biring + 180;
					List<Double> list = getDestinationPoint(ic.getStartLatitude(),ic.getStartLongitude(),biring, 300);
						
						marker = createMarker (ic.getName(),list.get(0),list.get(1),Marker.DEFAULT_ICON_INTERFACE, null);	
						top.addMarker(marker);
						
						line = new Line();
						line.setStartLattitude(""+ic.getStartLatitude());
						line.setStartLongitude(""+ic.getStartLongitude());
						line.setEndLattitude(""+list.get(0));
						line.setEndLongitude(""+list.get(1));
						line.setColor(Line.getReservationLineColor(ic.getState()));
						line.setTickness("3");
						line.setOblique(0);
						top.addLine(line);
				}		
			}
		return top;
	}

	/**
	 * Creates topology based on manager data
	 */
	private void createTopology() {
		
		topology.removeAll();
		Marker marker = null;
		Line line = null;
		String name=null;
		 List<String> list = null;
		if (manager==null){
			return;
		}
		Map<String, List<InterfaceComponent>> interfaces = new HashMap<String, List<InterfaceComponent>>();		
		List<String> strings = new ArrayList<String>();
		InterfaceComponent ic = null;
		List<String> idmsNames = manager.getAllInterdomainManagers();
		if (idmsNames == null)
			return;
		int length = idmsNames.size();
		InterDomainManager idm = null;
		InterDomainManager neighbourIdm=null;
		long currentTime = System.currentTimeMillis();
		for (int i=0;i<length;i++){
			idm = manager.getInterDomainManager(idmsNames.get(i));
			List<InterfaceComponent> interfaceComponents = new ArrayList<InterfaceComponent>();
			Status status = idm.getStatus();
			if (status != null)
			{			
				boolean isActive = currentTime-idm.getLastStatusUpdateInMillis()<manager.getTearDownTime();
				marker = createMarker (name,status.getLatitude(),status.getLongitude(),(isActive?Marker.DEFAULT_ICON_ACTIVE:Marker.DEFAULT_ICON_DEACTIVE), createHTMLNodeInfo(idm.getIdentifier(),status));			
				topology.addMarker(marker);
				
				strings.add(status.getDomain());
				
				List<Neighbor> neighbors = status.getNeighbors();
				if (neighbors==null || neighbors.isEmpty())
					continue;				
				int lengthN = neighbors.size();
		        
				LookupService lookup = manager.getLookupServiceObject();
				
				Status statusNeighbor;
				for (int j=0;j<lengthN;j++){
				
				    String idmLocation = "";
				    if (lookup != null) {
    					try {
    						String temp = lookup.QueryIdmLocation(neighbors.get(j).getDomain());
    						idmLocation = temp.replace("\n",""); //Without escape characters included
					
    					} catch (LookupServiceException e) {
    						logger.info("Lookup query failed: " + e.getMessage());
    					}
			        }
					
			        if (idmLocation != null && !idmLocation.equals("")) {
	                    neighbourIdm = manager.getInterDomainManager(idmLocation);               
			        }
			        else {
			            logger.debug("IDM location could not be obtained from Lookup, falling back to direct contact");
	                    neighbourIdm = manager.getInterDomainManager(neighbors.get(j).getDomain());
			        }
			        
					if (neighbourIdm==null)
						continue;
					
				statusNeighbor = neighbourIdm.getStatus();
				list = manager.getMappedInterDomainManagerPorts(idmsNames.get(i));
					isActive = currentTime-neighbourIdm.getLastStatusUpdateInMillis()<manager.getTearDownTime();
					if (statusNeighbor!= null){
							
							Link link =neighbors.get(j).getLink();
							
							double lattitude_midle = (Math.abs(status.getLatitude()-statusNeighbor.getLatitude()))/2+Math.min(status.getLatitude(),statusNeighbor.getLatitude());
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
							line.setTickness("5");
							line.setOblique(0);
							topology.addLine(line);
							line = new Line();
							line.setStartLattitude(""+lattitude_midle);
							line.setStartLongitude(""+longitude_midle);
						
							line.setEndLattitude(""+statusNeighbor.getLatitude());
							line.setEndLongitude(""+statusNeighbor.getLongitude());
							line.setColor(isActive?Line.DEFAULT_COLOR_ACTIVE:Line.DEFAULT_COLOR_DEACTIVE);
							line.setTickness("5");
							line.setOblique(0);
							topology.addLine(line);
							
							ic = new InterfaceComponent(status.getDomain(), status.getLatitude(), status.getLongitude(), 
									statusNeighbor.getLatitude(),statusNeighbor.getLongitude(), list.size(), isActive?9:23);
							interfaceComponents.add(ic);
					}
				}
			}
			interfaces.put(status.getDomain(),interfaceComponents);
		}
		if(interfaces != null)
			setInterfaceInMainTopology(interfaces);	
	}
	@SuppressWarnings("unchecked")
	public void setInterfaceInMainTopology(Map<String, List<InterfaceComponent>> interfaces){

		Line line = null;
		Marker marker = null;
		Set set = interfaces.entrySet();
		Iterator it = set.iterator();
			while(it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				List<InterfaceComponent> list = interfaces.get(me.getKey());
			
				if(list != null){	
					List<InterfaceComponent> components = setInterface(list);
					for (int i = 0; i < components.size(); i++) {
						
						marker = createMarker (components.get(i).getName(),components.get(i).getStartLatitude(),components.get(i).getStartLongitude(),
						Marker.DEFAULT_ICON_INTERFACE, null);
						topology.addMarker(marker);
						
						line = new Line();
						line.setStartLattitude(""+list.get(0).getStartLatitude());
						line.setStartLongitude(""+list.get(0).getStartLongitude());
						line.setEndLattitude(""+components.get(i).getStartLatitude());
						line.setEndLongitude(""+components.get(i).getStartLongitude());
						line.setColor(Line.getReservationLineColor(list.get(0).getState()));
						line.setTickness("3");
						line.setOblique(0);
						topology.addLine(line);
					}
				}
			}
	}
	public List<InterfaceComponent> setInterface(List<InterfaceComponent> list){
		List<InterfaceComponent> components = new ArrayList<InterfaceComponent>();
		InterfaceComponent interfaceComponent = null;
		List<Double> doblist= new ArrayList<Double>();
	
		for (int i = 0; i < list.size(); i++) {

			doblist.add(getBearingBetween2Points(list.get(i).getStartLatitude(),list.get(i).getStartLongitude(),
					list.get(i).getEndLatitude(),list.get(i).getEndLongitude()));
		}
	
		double max = Collections.max(doblist);
		double min = Collections.min(doblist);
		double range = 0;
		double number = list.get(0).getNumberOfInterfaces();
		if( max - min <= 180){
			//start from max
			max = max + 10;
			min = min - 10;
			range = 360 - (max - min);
			range = range / (number+1);
			double biring = max + range;
		
			for (int i = 0; i < number; i++) {
				
				List<Double> ld = getDestinationPoint(list.get(0).getStartLatitude(),list.get(0).getStartLongitude(),biring,300);
				interfaceComponent = new InterfaceComponent(list.get(0).getName(),new Float(ld.get(0)), new Float(ld.get(1)));
				components.add(interfaceComponent);
				biring = biring + range;			
			}
		}
		else {
			//start from min
			max = max - 10;
			min = min + 10;
			range = max - min;
			range = range / number;
			double biring = min + range;
		
			for (int i = 0; i < number; i++) {
				
				List<Double> ld = getDestinationPoint(list.get(0).getStartLatitude(),list.get(0).getStartLongitude(),biring,300);
				interfaceComponent = new InterfaceComponent(list.get(0).getName(),new Float(ld.get(0)), new Float(ld.get(1)));
				components.add(interfaceComponent);
				biring = biring + range;
			}
		}
		return components;
	}
	public Double getBearingBetween2Points(double lat1, double lon1, double lat2, double lon2){
		 
		 double Latitude1 = Math.toRadians(lat1);
		 double Latitude2 = Math.toRadians(lat2);
		 double dLongitude = Math.toRadians(lon2 - lon1);
		 
		 double y = Math.sin(dLongitude)*Math.cos(Latitude2);
		 double x = Math.cos(Latitude1)*Math.sin(Latitude2)-Math.sin(Latitude1)*Math.cos(Latitude2)*Math.cos(dLongitude);
		 double z = Math.atan2(y, x);
		 
		 return (Math.toDegrees(z)+360)%360;
	 }
	
	public List<Double> getDestinationPoint(double lat1, double lon1, double biring, double d){
		
		List<Double> list = new ArrayList<Double>();
		double R = 6371;
		double angle = d/R;
		double Latitude1 = Math.toRadians(lat1);
		double Longitude1 = Math.toRadians(lon1);
		double bir = Math.toRadians(biring);
		
		double lat2 = Math.asin(Math.sin(Latitude1)*Math.cos(angle)+
				Math.cos(Latitude1)*Math.sin(angle)*Math.cos(bir));

		double lon2 = Longitude1 + Math.atan2(Math.sin(bir)*Math.sin(angle)*Math.cos(Latitude1), 
				Math.cos(angle)-Math.sin(Latitude1)*Math.sin(lat2));
			list.add(Math.toDegrees(lat2));
			list.add(Math.toDegrees(lon2));
		return list;
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
