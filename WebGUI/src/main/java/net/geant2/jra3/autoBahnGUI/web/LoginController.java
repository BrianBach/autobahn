package net.geant2.jra3.autoBahnGUI.web;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.geant2.jra3.autoBahnGUI.model.LoginAuthenticator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller use to handle login page form 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class LoginController extends SimpleFormController{
	/**
	 * Logs module information
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * Identifies the list of authenticators
	 */
	Map<String,String> authenticators= new HashMap<String, String>();
	
	/**
	 * Creates class
	 */
	public LoginController(){
		this.setSessionForm(true);
		this.setCommandName("returnIDParam");
		this.setCommandClass(LoginAuthenticator.class);
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
		if (command ==null || !command.getClass().equals(LoginAuthenticator.class))
			return showForm(request,response,errors);
		
		LoginAuthenticator authenticator = (LoginAuthenticator)command;
		String autenticatorUrl = URLDecoder.decode(authenticators.get((String)authenticator.getAuthenticator()), "UTF-8");
		String returnURL = URLDecoder.decode(request.getParameter("return"), "UTF-8");
		if (autenticatorUrl ==null)
			return showForm(request,response,errors);
		RedirectView redirectView = new RedirectView(returnURL);
		request.setAttribute("key", autenticatorUrl);
		return new ModelAndView(redirectView);
	}
	/**
	 * Custom handler for GET method form
	 * @param request current HTTP request
	 * @return model map
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authenticators", authenticators);
		return map;
	}
	
	public Map<String, String> getAuthenticators() {
		return authenticators;
	}
	public void setAuthenticators(Map<String, String> authenticators) {
		this.authenticators = authenticators;
	}
	
}
