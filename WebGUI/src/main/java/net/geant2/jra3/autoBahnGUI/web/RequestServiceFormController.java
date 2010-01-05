package net.geant2.jra3.autoBahnGUI.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.geant2.jra3.autoBahnGUI.manager.Manager;
import net.geant2.jra3.useraccesspoint.ReservationRequest;
import net.geant2.jra3.useraccesspoint.ServiceRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
/**
 * Controller is used for requesting services
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class RequestServiceFormController extends SimpleFormController {
	/**
	 * Identifies the manager module
	 */
	private Manager manager;	
	/**
	 * Identifies the chose JRA3 IDM name
	 */
	private String  selectedIDM;
	/**
	 * Logs information
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	public RequestServiceFormController(){
	    setSessionForm(true);
	    setBindOnNewForm(true);
	    setCommandName("service");
	    setCommandClass(ServiceRequest.class);
	}
	/**
	 * Custom handler for GET method form
	 * @param request current HTTP request
	 * @return model map
	 */
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String,Object> ();
		List<String> interDomainsManagers = manager.getAllInterdomainManagers();
		if (!interDomainsManagers.isEmpty())
			selectedIDM = interDomainsManagers.get(0);
		data.put("requestedIDM", selectedIDM);
		data.put("idms", interDomainsManagers);
		return data;
	}
	/**
	 * Custom handler for setting form command object
	 * @param request current HTTP request
	 * @return command object
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		ServiceRequest service = (ServiceRequest)session.getAttribute("service");
		if (service == null){
			service= new ServiceRequest();
			service.setUserEmail("test.user@test.domain");
			service.setUserName("testUser");
			if (selectedIDM==null){
				List<String> interDomainsManagers = manager.getAllInterdomainManagers();
				if (!interDomainsManagers.isEmpty())
					selectedIDM = interDomainsManagers.get(0);
			}
			service.setUserHomeDomain(selectedIDM);
		}
		return service;
	}
	/**
	 * Custom handler for form submission
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return a ModelAndView to render the response
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors){
		ModelAndView model = null;
		String action = ServletRequestUtils.getStringParameter(request,
				"action", null);
		ServiceRequest service = (ServiceRequest)command;
		if (action == null)
			try {
				return showForm(request, errors, "requestView");
			} catch (Exception e1) {
				logger.error (e1.getClass().getName()+":"+e1.getMessage());
			}
			if (action.equals("add")){
				List<String> domainPorts = manager.getInterDomainManagerPorts(service.getUserHomeDomain());
				if (domainPorts==null)
					try {
						errors.rejectValue("service.userHomeDomain", "service.userHomeDomain.error", null, "There is no way to contact with IDM, or client port list is empty.");
						return showForm(request, errors, "requestView");
					} catch (Exception e) {
						logger.error (e.getClass().getName()+":"+e.getMessage());
					}	
				List<String> mappedDomainPorts = new ArrayList <String>();
				String mappedPort = null;
				for (String port:domainPorts){
					mappedPort = manager.mapPort(port);
					if (mappedPort== null)
						mappedDomainPorts.add(mappedPort);
				}
				
				List<String> allPorts = manager.getAllPorts();
				if (allPorts==null)
					allPorts= domainPorts;
				List<String> mappedAllPorts = new ArrayList <String>();
				for (String port:allPorts){
					mappedAllPorts.add(manager.mapPort(port));
				}	
				model = new ModelAndView(new RedirectView("reservationForm.htm"));
				HttpSession session = request.getSession();
				session.setAttribute("idm", service.getUserHomeDomain());
				session.setAttribute("ports_all", mappedAllPorts);
				session.setAttribute("ports_domain", mappedDomainPorts);
				session.setAttribute("service", service);
				return model;
			}else
			if (action.equals("remove")) {
				int id = ServletRequestUtils.getIntParameter(request, "id", 0);
				if (id < service.getReservations().size()) {
					service.getReservations().remove(id);
					try {
						return showForm(request, errors, "requestView");
					} catch (Exception e) {
						logger.error (e.getClass().getName()+":"+e.getMessage());
					}
				}
			} else if (action.equals("update")) {
				logger.info("On action update");
				try {
					return showForm(request, errors, "requestView");
				} catch (Exception e) {logger.error (e.getClass().getName()+":"+e.getMessage());}
			}else if (action.equals("save")){
						if (!manager.checkUserAccessPointConnection(service.getUserHomeDomain()))
							try {
								return showForm(request, errors, "requestView");
							} catch (Exception e) {
								logger.error (e.getClass().getName()+":"+e.getMessage());
							}
						ModelAndView successView=null;
						
						if (service.getReservations().isEmpty()){
							try {
								return showForm(request, errors, "requestView");
							} catch (Exception e) {
								logger.error (e.getClass().getName()+":"+e.getMessage());
							}
						}
						demapPorts(service);
						
						String serviceId=null;
							try {
								serviceId = manager.submitServiceAtInterDomainManager(service.getUserHomeDomain(), service);
								request.getSession().removeAttribute("service");
								successView = new ModelAndView(new RedirectView("reservations.htm#"+serviceId+"?domain="+service.getUserHomeDomain()));
							} catch (Exception e) {
								logger.error (e.getClass().getName()+":"+e.getMessage());
								try {
									mapPorts(service);
									return showForm(request, errors, "requestView");
								} catch (Exception e1) {
									logger.error (e1.getClass().getName()+":"+e1.getMessage());
								}
							}
		
							return successView;
			}else{
				try {
					return showForm(request, errors, "requestView");
				} catch (Exception e1) {
					logger.error (e1.getClass().getName()+":"+e1.getMessage());
				}
			}
		return model;
	}

	public void demapPorts (ServiceRequest service){
		for(ReservationRequest reservation:service.getReservations()){
			reservation.setStartPort(manager.demapPort(reservation.getStartPort()));
			reservation.setEndPort(manager.demapPort(reservation.getEndPort()));
		}
	}
	
	public void mapPorts (ServiceRequest service){
		for(ReservationRequest reservation:service.getReservations()){
			reservation.setStartPort(manager.mapPort(reservation.getStartPort()));
			reservation.setEndPort(manager.mapPort(reservation.getEndPort()));
		}
	}
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getSelectedIDM() {
		return selectedIDM;
	}

	public void setSelectedIDM(String selectedIDM) {
		this.selectedIDM = selectedIDM;
	}
}
