package net.geant2.jra3.autoBahnGUI.web;

import java.beans.PropertyEditorSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.geant2.jra3.useraccesspoint.Priority;

/**
 * Property editor for bean {@link Priority}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class PriorityPropopertyEditor extends PropertyEditorSupport {
	
	protected final Log logger = LogFactory.getLog("Priority Editor");
	
	/**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
	public String getAsText() {
		Priority priority = (Priority)getValue();
		if (priority!=null)
			return priority.toString();
		else
			return "";
	}
	 /**
     * @see java.beans.PropertyEditorSupport#setAsText(String)
     */
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null){
			Priority priority = Priority.valueOf(text);
			setValue(priority);
		}else
			setValue(Priority.NORMAL);
		
	}
}
