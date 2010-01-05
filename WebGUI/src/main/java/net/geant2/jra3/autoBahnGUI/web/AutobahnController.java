package net.geant2.jra3.autoBahnGUI.web;
import java.util.List;
import java.util.Map;
import net.geant2.jra3.autoBahnGUI.manager.Manager;
import net.geant2.jra3.autoBahnGUI.model.googlemaps.Line;
import net.geant2.jra3.autoBahnGUI.model.googlemaps.Topology;
import net.geant2.jra3.autoBahnGUI.topology.TopologyFinder;
import net.geant2.jra3.reservation.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Main  controller for Authobahn client portal 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */

@Controller
public class AutobahnController {

	/**
	 * Identifies the GUI manager module
	 */
	@Autowired
	Manager  manager;
	/**
	 * Identies topology finder module
	 */
	@Autowired
	TopologyFinder topologyFinder;
	/**
	 * Logs information
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	public AutobahnController(){
		Logger.getLogger("Creating controller").info("Creating controller");
	}
	@RequestMapping("/notfound.htm")
	public void notFound (){
		
	}
	
	/**
	 * Custom handler for home.
	 * 
	 * @param model model for response params
	 */
	@RequestMapping("/home.htm")
	public void homeHandler(){
		Logger.getLogger("Kupa w pory").info("Kupa w pory");
	}
	
	/**
	 * Custom handler for home.
	 * 
	 * @param model model for response params
	 */
	@RequestMapping("/login.htm")
	public void homeLogin(){
		Logger.getLogger("Kupa w pory").info("Kupa w pory");
	}
	
	@RequestMapping("/secure/tools.htm")
	public void homeTools(){
		Logger.getLogger("Kupa w pory").info("In tools");
	}
	
	@RequestMapping("/secure/noJRA3IDMRegistered.htm")
	public void noIdms(){
		Logger.getLogger("Kupa w pory").info("In tools");
	}
	
	/**
	 * Custom handler for map view.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return a ModelAndView to render the response
	 */
	@RequestMapping("/secure/services-map.htm")
    public void mapHandler (@RequestParam String  service,@RequestParam String  domain, Map<String, Object> model){
		logger.info("Requesting map with params");
		String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
		String[] linkStatusName = {"Up","Down"};
		model.put("linkColors",linkStatusColor);
		model.put("linkStates",linkStatusName);
		List<Service> services = manager.getServicesForAllInterDomainManagers();
		model.put ("services", services);
		if (service!=null && service.length()>0){
			model.put("reservationLinkColors",Line.reservationsStates);
			model.put("reservationStates",manager.getReservationStates());
		}
	}
	/*
	@RequestMapping("/secure/services-map.htm")
    public void mapHandler ( Map<String, Object> model){
		logger.info("Requesting map without params");
		String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
		String[] linkStatusName = {"Up","Down"};
		model.put("linkColors",linkStatusColor);
		model.put("linkStates",linkStatusName);
		List<Service> services = manager.getServicesForAllInterDomainManagers();
		model.put ("services", services);
	}*/
	@RequestMapping("/secure/services-list.htm")
    public void mapServicesListHandler ( Map<String, Object> model){
		List<Service> services = manager.getServicesForAllInterDomainManagers();
		model.put ("services", services);
	}

	/*public ModelAndView handleMap(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ModelAndView modelAndView = new ModelAndView(reservationsMapView);
		String service=(String)request.getParameter("service");
		String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
		String[] linkStatusName = {"Up","Down"};
		modelAndView.addObject("linkColors",linkStatusColor);
		modelAndView.addObject("linkStates",linkStatusName);
		List<Service> services = manager.getServicesForAllInterDomainManagers();
		modelAndView.addObject ("services", services);
		if (service!=null){
			modelAndView.addObject("reservationLinkColors",Line.reservationsStates);
			modelAndView.addObject("reservationStates",manager.getReservationStates());
		}
		return modelAndView;
	}*/
	/**
	 * Custom handler for getting map topology in XML format
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return a ModelAndView to render the response
	 */
	/*@RequestMapping("/secure/services-map.htm")
	public void mapHandlerXML (@RequestParam String domain, @RequestParam String  service, Map<String, Object> model){
		Topology topology=null;
		if (service !=null)
			topology=topologyFinder.getGoogleTopology(domain,service);
		else	
			topology=topologyFinder.getGoogleTopology();
		model.put("topology", topology);
	}*/	
	
	/*
	@RequestMapping("/secure/topology.xml")
	public void handleTopologyXML(Map<String, Object> model){
		Logger.getLogger("handle topology xml");
		Topology topology=null;
		topology=topologyFinder.getGoogleTopology();
		model.put("topology", topology);
	}*/
	@RequestMapping("/secure/topology.xml")
	public void handleTopologyXML(@RequestParam String service,@RequestParam String domain, Map<String, Object> model){
		Logger.getLogger("handle topology xml");
		Topology topology=null;
		if (service !=null && service.length()>0)
			topology=topologyFinder.getGoogleTopology(domain,service);
		else	
			topology=topologyFinder.getGoogleTopology();
		model.put("topology", topology);
	}

	@RequestMapping("/secure/servicesforidm.htm")
	public void handleServicesForIdm(@RequestParam String link, @RequestParam String currentIdm,Map<String, Object> model){
		Logger.getLogger("getting services for idm");
		List<Service> services=null;
		String[] reservationStates=null;
		if (currentIdm  !=null){
			services =manager.getServicesFromInterDomainManager(currentIdm);
			reservationStates=manager.getReservationStates();
		}
		model.put("link", link);
		model.put("serv", services);
		model.put("reservationStates", reservationStates);
	}
	
	/*
	public ModelAndView handleIdmsTopology(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ModelAndView modelAndView = new ModelAndView(idmsTopologyView);
		return modelAndView;
	}
	
	public ModelAndView handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ModelAndView modelAndView = new ModelAndView(loginView);
		return modelAndView;
	}
	public ModelAndView handleUser (HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ModelAndView view = new ModelAndView (userView);
		return view;
	}
	public ModelAndView handleServices (HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ModelAndView view = new ModelAndView (servicesView);
		List<Service> services = manager.getServicesForAllInterDomainManagers();
		view.addObject("services",services);
		return view;
	}
	public ModelAndView handleTools (HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ModelAndView view = new ModelAndView (toolsView);
		return view;
	}*/
	
	public Manager getManager() {
		return manager;
	}


	public void setManager(Manager manager) {
		this.manager = manager;
	}


	public TopologyFinder getTopologyFinder() {
		return topologyFinder;
	}


	public void setTopologyFinder(TopologyFinder topologyFinder) {
		this.topologyFinder = topologyFinder;
	}
	
	
}
