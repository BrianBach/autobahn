package net.geant.autobahn.autoBahnGUI.webflow;

import java.util.Locale;

import net.geant.autobahn.autoBahnGUI.manager.ManagerException;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.webflow.execution.FlowExecutionException;
import org.springframework.webflow.execution.RequestContext;



/**
 * Exception translator use translating exception message to message shown on page
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ExceptionTranslator implements MessageSourceAware {
	
	private MessageSource messages;
	
	private Logger logger = Logger.getLogger(ExceptionTranslator.class);
	
	/**
     * Unravels the FlowExecutionException to find the AccessDeniedException
     * @param ex
     * @return
     */
    private ManagerException  findManagerExeception(FlowExecutionException ex) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause instanceof ManagerException) {
            	return (ManagerException) cause;
            }
            cause = cause.getCause();
        }
        
        return null;
    }
    private UserAccessPointException  findUserAccessPointException(FlowExecutionException ex) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause instanceof UserAccessPointException) {
            	return (UserAccessPointException) cause;
            }
            cause = cause.getCause();
        }
        
        return null;
    }
    
    
	public void translateException( RequestContext requestContext, Exception exception ) {
			logger.info("Translating exception");
			exception.printStackTrace();
			if (exception instanceof FlowExecutionException){
				ManagerException conf = findManagerExeception ((FlowExecutionException)exception);
				
				if (conf!= null){
					logger.info("FileSystemManagerException:"+conf.getMessage());
					requestContext.getFlowScope().put("error", messages.getMessage(ManagerException.name+"."+conf.getError(), null, Locale.getDefault()));
					return;
				}
				UserAccessPointException ue = findUserAccessPointException ((FlowExecutionException)exception);
				
				if (ue!= null){
					logger.info("FileSystemManagerException:"+ue.getMessage());
					requestContext.getFlowScope().put("error", messages.getMessage(ue.getMessage(), null, Locale.getDefault()));
					return;
				}
				
				logger.info(exception.getClass()+":"+exception.getMessage());
				requestContext.getFlowScope().put("error", messages.getMessage("errors.unknown", null, Locale.getDefault()));
				return;
			}else
			{
				logger.info(exception.getClass()+":"+exception.getMessage());
				requestContext.getFlowScope().put("error", messages.getMessage("errors.unknown", null, Locale.getDefault()));	
			}
	}

	public void setMessageSource(MessageSource messages) {
		this.messages= messages;
	}

}

