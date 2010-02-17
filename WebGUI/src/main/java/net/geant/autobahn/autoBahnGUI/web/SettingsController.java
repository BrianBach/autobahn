package net.geant.autobahn.autoBahnGUI.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


/**
 * Controller is used for getting setting information from IDMs
 *  
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class SettingsController extends SimpleFormController {
	/**
	 * Identifies the manager module
	 */
	Manager manager;
	/**
	 * Default constructor
	 */
	public SettingsController(){
		setSessionForm(true);
		setCommandName("settings");
		setCommandClass(SettingsFormModel.class);
	}
	
	/**
	 * Custom handler for setting form command object
	 * @param request current HTTP request
	 * @return command object
	 */
	protected Object formBackingObject(HttpServletRequest request){
		SettingsFormModel services = new SettingsFormModel();
		List <String> idms = manager.getAllInterdomainManagers();
		
		String domain = ServletRequestUtils.getStringParameter(request,"domain", null);
		String currentIdm="";
		if (domain == null && !idms.isEmpty()){
			currentIdm = idms.get(0);
		}
		else
			currentIdm =domain;
		services.setIdms(idms);
		services.setCurrentIdm(currentIdm);
		List<KeyValue> properties=null;
		properties = manager.getPropertiesForInterDomainManager(currentIdm);
		if (properties == null)
			services.setError("GUI was unable to contact with administration interface of: "+currentIdm);
		services.setProperties(properties);
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
		String action = ServletRequestUtils.getStringParameter(request,
				"action", null);
		
		if (action == null)
			return showForm(request, errors, "settingsView");
		if (action.equals("restart")){
			SettingsFormModel services= (SettingsFormModel)command;
			String current = services.getCurrentIdm();
			manager.setPropertiesForInterDomainManager(current, services.getProperties());
			return showForm(request, errors, "settingsView");
		}else
		if (action.equals("change"))
		{
			SettingsFormModel services= (SettingsFormModel)command;
			String current = services.getCurrentIdm();
			services.setProperties(manager.getPropertiesForInterDomainManager(current));
			return showForm(request, errors, "settingsView");
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
