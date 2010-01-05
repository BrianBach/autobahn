package net.geant2.jra3.autoBahnGUI.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.geant2.jra3.reservation.Service;
import net.geant2.jra3.autoBahnGUI.manager.Manager;
import net.geant2.jra3.autoBahnGUI.model.ServicesComparator;
import net.geant2.jra3.autoBahnGUI.model.ServicesFormModel;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class SubmitedReservationsController extends SimpleFormController {
	/**
	 * Identifies the manager module
	 */
	Manager manager;
	/**
	 * Default constructor
	 */
	public SubmitedReservationsController(){
		setSessionForm(true);
		setCommandName("services");
		setCommandClass(ServicesFormModel.class);  
	}
	/**
	 * Custom handler for GET method form
	 * @param request current HTTP request
	 * @return model map
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object> ();
		data.put("reservationStates", manager.getReservationStates());
		data.put("serviceStates", manager.getServiceStates());
		data.put("priorities", manager.getReservationPriorities());
		
		return data;
	}
	/**
	 * Custom handler for setting form command object
	 * @param request current HTTP request
	 * @return command object
	 */
	protected Object formBackingObject(HttpServletRequest request){
		ServicesFormModel services = new ServicesFormModel();
		List <String>idms= manager.getAllInterdomainManagers();
		String domain = ServletRequestUtils.getStringParameter(request,"domain", null);
		
		String currentIdm;
		if (domain == null && !idms.isEmpty())
			currentIdm = idms.get(0);
		else
			currentIdm =domain;
		services.setIdms(idms);
		services.setCurrentIdm(currentIdm);
		List<Service> servicesList= manager.getServicesFromInterDomainManager(currentIdm);
		services.setServices(servicesList);
		services.setComparator(new ServicesComparator());
		return services;
	}
	/**
	 * Custom handler for form submission
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return a ModelAndView to render the response
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String action = ServletRequestUtils.getStringParameter(request,"action", null);
		ServicesFormModel services= (ServicesFormModel)command;
		
		if (action == null)
			return showForm(request, errors, "reservationsView");
		
		
		if (action.equals("change"))
		{	
			String current = services.getCurrentIdm();	
			List<Service> service= manager.getServicesFromInterDomainManager(current);
			if (service==null)
				services.setServices(new ArrayList<Service>());
			else
				services.setServices(service);
			return showForm(request, errors, "reservationsView");
		}else
		if (action.equals("remove")){
			String id = ServletRequestUtils.getStringParameter(request,
					"id", null);
			if (id != null && manager.checkUserAccessPointConnection(services.getCurrentIdm()))
			{
				Service service = manager.getServiceFromInterDomainManager(services.getCurrentIdm(), id);
				if (service != null){
					if (manager.checkUserAccessPointConnection(services.getCurrentIdm())){
						manager.cancelServiceInInterDomainManager(services.getCurrentIdm(), service.getBodID());
						return showForm(request, errors, "reservationsView");
					}else
					{
						try {
							errors.rejectValue("service.currentIDM", "service.cancel.error", null, "There is no way to contact with IDM.");
							return showForm(request, errors, "reservationsView");
						} catch (Exception e) {
						}
					}
				}
			}
		}else
		{
			
		}
		return super.onSubmit(request, response, command, errors);
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
