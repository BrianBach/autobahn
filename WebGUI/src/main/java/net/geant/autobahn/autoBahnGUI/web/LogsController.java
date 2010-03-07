package net.geant.autobahn.autoBahnGUI.web;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller is used for showing logs gather by different AutoBAHN IDMs
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class LogsController extends SimpleFormController {
	/**
	 * Identifies the GUI manager module
	 */
	Manager manager;

	public LogsController(){
		setCommandName("logs");
		setSessionForm(true);
		setCommandClass(LogsFormModel.class);  
	}
	/**
	 * Custom handler for setting form command object
	 * @param request current HTTP request
	 * @return command object
	 */
	protected Object formBackingObject(HttpServletRequest request){
		LogsFormModel logs = new LogsFormModel();
		List <String> idms= manager.getAllInterdomainManagers();	
		String domain = ServletRequestUtils.getStringParameter(request,"domain", null);
		String currentIdm="";
		if (domain == null){
			if (!idms.isEmpty())
				currentIdm=idms.get(0);
		}
		else
			currentIdm =domain;
		logs.setIdms(idms);
		logs.setCurrentIdm(currentIdm);
		String log=null;
		if (manager!=null)
			log = manager.getLogsInterDomainManager(currentIdm,true, true);
		if (log == null)
			logs.setError("GUI was unable to contact with administration intreface of: "+currentIdm);
		logs.setLogs(log);
		return logs;
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
			return showForm(request, errors, "logsView");
		if (action.equals("change"))
		{
			LogsFormModel services= (LogsFormModel)command;
			String current = services.getCurrentIdm();
			String log=null;
			if (manager!=null)
				log = manager.getLogsInterDomainManager(current, true, true);
			services.setLogs(log);

			if (log == null)
				services.setError("GUI was unable to contact with administration intreface of: "+current);
			return showForm(request, errors, "logsView");
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
