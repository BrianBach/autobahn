package net.geant2.jra3.autoBahnGUI.webflow;

import net.geant2.jra3.useraccesspoint.Priority;
import org.springframework.binding.convert.converters.TwoWayConverter;
import org.springframework.util.StringUtils;

public class ReservationPriorityConverter implements TwoWayConverter {

	public Object convertTargetToSourceClass(Object target, Class c)
			throws Exception {
		
		String value = (String) target;
		if (!c.equals(Priority.class))
			throw new IllegalArgumentException();
		if (StringUtils.hasText(value)) {
			return Priority.valueOf(value);
	    } else
	    	throw new IllegalArgumentException();
	}

	public Object convertSourceToTargetClass(Object value, Class c)
			throws Exception {
		if (value == null)
			return Priority.NORMAL;
		return value.toString(); 
	}

	public Class getSourceClass() {
		return Priority.class;
	}

	public Class getTargetClass() {
		return String.class;
	}

}
