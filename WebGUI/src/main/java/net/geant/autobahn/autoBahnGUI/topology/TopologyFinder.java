package net.geant.autobahn.autoBahnGUI.topology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.geant.autobahn.administration.Neighbor;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.manager.InterDomainManager;
import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.InterfaceComponent;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Line;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Marker;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.reservation.Reservation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	public final static Log logger = LogFactory.getLog(TopologyFinder.class);
	
	/**
	 * Time after which the IDM is mark as not accessible
	 */
	public static final long DEFAULT_DOWN_TIME = 70000;
	
	public int numberOfIDMs = 0;
	
	/**
	 * Topology representation
	 */
	private Topology topology = new Topology();
	
	private Map<String,InterfaceComponent> map = new HashMap<String, InterfaceComponent>();
  	
	private Timer timer = new Timer();
	
	private Object timerLock = new Object();
	
	public TopologyFinder(){
		
		timer.schedule(new NoIDMAvailableTimeout(), DEFAULT_DOWN_TIME);
	}
	
	class NoIDMAvailableTimeout extends TimerTask {

		public void run() {
			
			manager.checkIDMavailability();
			createTopology();
		}	
	}
	/**
	 * Updates topology by analysing manager data
	 */
	public synchronized void updateTopology() {
		new Thread(new Runnable() {
            public void run() {
            	synchronized (topology) {

            		manager.checkIDMavailability();
            		
            		if(numberOfIDMs != manager.getNumberOfAvailableIDMs())
            			createTopology();
				}
            	synchronized (timerLock) {
            		timer.cancel();
            		timer = new Timer();
            		timer.schedule(new NoIDMAvailableTimeout(), DEFAULT_DOWN_TIME);
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
		top.getMarkers().removeAll();
		Marker marker = null;
		InterDomainManager idm = null;
		InterDomainManager startDomain=null;
		InterDomainManager endDomain=null;
		List<String> strings = new ArrayList<String>();
		List<String> idmsNames = manager.getAllInterdomainManagers();
		long currentTime = System.currentTimeMillis();
		
		for (int i = 0; i < idmsNames.size(); i++) {
			
			idm = manager.getInterDomainManager(idmsNames.get(i));
			Status status = idm.getStatus();
			
			if (status != null)
			{
				boolean isActive = currentTime-idm.getLastStatusUpdateInMillis() < manager.getTearDownTime();
				marker = createMarker (status.getDomain(),status.getLatitude(),status.getLongitude(),(isActive?Marker.DEFAULT_ICON_ACTIVE:Marker.DEFAULT_ICON_DEACTIVE), createHTMLNodeInfo(idm.getIdentifier(),status));			
				top.addMarker(marker);
			}
		}
	
		int state = 0;
		
		idm = manager.getInterDomainManager(domain);

		if (idm == null)
			return top;
		
		if (serviceId !=null){
			
			Reservation reservations = idm.getReservation(serviceId);
			Line line = null;
			Path path = reservations.getPath();
			List<Link> links= path.getLinks();
			
			int linkLenght = links.size();
			Link link =null;

				for (int j=0;j<linkLenght;j++){			
					
					link = links.get(j);
					
					if (link == null)
						continue;
						
					float startLatitude =0;
					float startLongitude =0;
					float endLatitude = 0;
					float endLongitude = 0;
						
					startDomain = manager.getInterDomainManager(link.getStartPort().getNode().getProvisioningDomain().getAdminDomain().getBodID());
					
					if(startDomain == null)
						continue;
						
					startLatitude = startDomain.getStatus().getLatitude();
					startLongitude = startDomain.getStatus().getLongitude();
						
					if(link.getEndPort().getNode().getProvisioningDomain().getAdminDomain().isClientDomain()){
						strings.add(link.getEndPort().getBodID());
						continue;
					}
						
					endDomain = manager.getInterDomainManager(link.getEndPort().getNode().getProvisioningDomain().getAdminDomain().getBodID());
					
					if(endDomain == null)
						continue;
						
					endLatitude = endDomain.getStatus().getLatitude();
					endLongitude = endDomain.getStatus().getLongitude();
		
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
					line.setColor(Line.getReservationLineColor(reservations.getState()));
					line.setTickness("5");
					line.setOblique(0);
					top.addLine(line);
					
					line = new Line();
					line.setStartLattitude(""+lattitude_midle);
					line.setStartLongitude(""+longitude_midle);
					line.setEndLattitude(""+endLatitude);
					line.setEndLongitude(""+endLongitude);
					line.setColor(Line.getReservationLineColor(reservations.getState()));
					line.setTickness("5");
					line.setOblique(0);
					top.addLine(line);
					
					}
					state = reservations.getState();
		}

		if(strings != null)
			top.addTopology(setInterfaceTopology(strings,state));
		
		return top;
	}
	
	public Topology setInterfaceTopology(List<String> strings, int state){
		
		Line line = null;
		Topology top = new Topology();
		Marker marker = null;
		if(map.size()>0){
			for (int i = 0; i < strings.size(); i++) {
				InterfaceComponent ic = map.get(strings.get(i));
			
				marker = createMarker(ic.getInterf(), ic.getEndLatitude(), ic.getEndLongitude(), Marker.DEFAULT_ICON_INTERFACE, createHTMLPortInfo(ic.getInterf()));
				top.addMarker(marker);
			
				line = new Line();
				line.setStartLattitude(""+ic.getStartLatitude());
				line.setStartLongitude(""+ic.getStartLongitude());
				line.setEndLattitude(""+ic.getEndLatitude());
				line.setEndLongitude(""+ic.getEndLongitude());
				line.setColor(Line.getReservationLineColor(state));
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
			 logger.info("manager = null ");
			return;
		}
		
		Map<String, List<InterfaceComponent>> interfaces = new HashMap<String, List<InterfaceComponent>>();		
		List<String> strings = new ArrayList<String>();
		InterfaceComponent ic = null;
		
		numberOfIDMs = manager.getNumberOfAvailableIDMs();
		
		List<String> idmsNames = manager.getAllInterdomainManagers();
		
		if (idmsNames == null){
			 logger.info("No IDMs available "+idmsNames);
			return;
		}
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
				
				Status statusNeighbor;
				
				for (int j=0;j<lengthN;j++){
			      
	             neighbourIdm = manager.getInterDomainManager(neighbors.get(j).getDomain());               
			 
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
									statusNeighbor.getLatitude(),statusNeighbor.getLongitude(), list.size(), isActive?9:23, list);
							interfaceComponents.add(ic);
					}
				}
			}
			interfaces.put(status.getDomain(),interfaceComponents);
		}
		

			if(interfaces != null)
				setInterfaceInMainTopology(interfaces);
	}


	public void setInterfaceInMainTopology(Map<String, List<InterfaceComponent>> interfaces){

		Line line = null;
		Marker marker = null;
		if(interfaces.size() > 0) {

			Set set = interfaces.entrySet();
			Iterator it = set.iterator();
				while(it.hasNext()) {
					Map.Entry me = (Map.Entry)it.next();
					List<InterfaceComponent> list = interfaces.get(me.getKey());
				
					if(list != null){	
						List<InterfaceComponent> components = setInterface(list);
						for (int i = 0; i < components.size(); i++) {
							
							marker = createMarker (components.get(i).getName(),components.get(i).getEndLatitude(),components.get(i).getEndLongitude(),
							Marker.DEFAULT_ICON_INTERFACE, createHTMLPortInfo(components.get(i).getInterf()));
							topology.addMarker(marker);
							
							line = new Line();
							line.setStartLattitude(""+list.get(0).getStartLatitude());
							line.setStartLongitude(""+list.get(0).getStartLongitude());
							line.setEndLattitude(""+components.get(i).getEndLatitude());
							line.setEndLongitude(""+components.get(i).getEndLongitude());
							line.setColor(Line.getReservationLineColor(list.get(0).getState()));
							line.setTickness("3");
							line.setOblique(0);
							topology.addLine(line);
							
						}
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
		if(doblist.size() > 0){
			
		double max = Collections.max(doblist);
		double min = Collections.min(doblist);
		double range = 0;
		double number = list.get(0).getNumberOfInterfaces();
		if( max - min <= 180){
			
			max = max + 10;
			min = min - 10;
			range = 360 - (max - min);
			range = range / (number+1);
			double biring = max + range;
		
			for (int i = 0; i < number; i++) {
				
				List<Double> ld = getDestinationPoint(list.get(0).getStartLatitude(),list.get(0).getStartLongitude(),biring,300);
				interfaceComponent = new InterfaceComponent(list.get(0).getName(),list.get(0).getStartLatitude(),list.get(0).getStartLongitude(),
						new Float(ld.get(0)), new Float(ld.get(1)),list.get(0).getList().get(i));
				map.put(list.get(0).getList().get(i), interfaceComponent);
				components.add(interfaceComponent);
				biring = biring + range;			
			}
		}
		else {
			max = max - 10;
			min = min + 10;
			range = max - min;
			range = range / (number + 1);
			double biring = min + range;
		
			for (int i = 0; i < number; i++) {
				
				List<Double> ld = getDestinationPoint(list.get(0).getStartLatitude(),list.get(0).getStartLongitude(),biring,300);
				interfaceComponent = new InterfaceComponent(list.get(0).getName(),list.get(0).getStartLatitude(),list.get(0).getStartLongitude(),
						new Float(ld.get(0)), new Float(ld.get(1)),list.get(0).getList().get(i));
				map.put(list.get(0).getList().get(i), interfaceComponent);
				components.add(interfaceComponent);
				biring = biring + range;
			}
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
		buffer.append ("<li><strong>Show services:  </strong>").append("<a href=\"reservations_select.htm?domain=").append(name).append("\">services</a>").append("</li>");
		buffer.append ("<li><strong>Show settings:  </strong>").append("<a href=\"settings_select.htm?domain=").append(name).append("\">info</a>").append("</li>");
		buffer.append ("<li><strong>Show logs:  </strong>").append("<a href=\"logs.htm?domain=").append(name).append("\">logs</a>").append("</li>");
		buffer.append("</div>");
		buffer.append("</ul>");	
		return buffer.toString();
		
	}
	private String createHTMLPortInfo(String name) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<h3  valign=\"middle\"><image src=\"").append(Marker.DEFAULT_ICON_INTERFACE).append("\"> Port information:</h3>");
		buffer.append("<br/><hr/>");
		buffer.append("<ul>");
		buffer.append("<div id=\"form\">");
		buffer.append ("<li><strong>Name:  </strong>").append(name).append("</li>");
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
