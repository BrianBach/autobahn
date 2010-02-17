package net.geant.autobahn.autoBahnGUI.web;

import java.beans.PropertyEditorSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.geant.autobahn.useraccesspoint.Resiliency;

/**
 * Property editor for bean {@link Resiliency}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ResieliencyPropertyEditor extends PropertyEditorSupport {
	/**
	 * Logs informations
	 */
	protected final Log logger = LogFactory.getLog("Resiliency Editor");
	 /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
	public String getAsText() {
		Resiliency resiliency = (Resiliency)getValue();
		if (resiliency !=null){
			return resiliency.toString();
		}
		else
			return "NONE";
	}
	 /**
     * @see java.beans.PropertyEditorSupport#setAsText(String)
     */
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null){
			
			Resiliency resiliency = Resiliency.valueOf(text);
			setValue(resiliency);
		}else
			setValue (Resiliency.NONE);
	}

}
