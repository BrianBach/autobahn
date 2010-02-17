package net.geant.autobahn.autoBahnGUI.webflow;

import net.geant.autobahn.useraccesspoint.Resiliency;

import org.springframework.binding.convert.converters.TwoWayConverter;
import org.springframework.util.StringUtils;

public class ReservationResiliencyConverter implements TwoWayConverter {

	public Object convertTargetToSourceClass(Object target, Class c)
			throws Exception {
		
		String value = (String) target;
		if (!c.equals(Resiliency.class))
			throw new IllegalArgumentException();
		if (StringUtils.hasText(value)) {
			return Resiliency.valueOf(value);
	    } else
	    	throw new IllegalArgumentException();
	}

	public Object convertSourceToTargetClass(Object value, Class c)
			throws Exception {
		if (value == null)
			return Resiliency.NONE;
		return value.toString(); 
	}

	public Class getSourceClass() {
		return Resiliency.class;
	}

	public Class getTargetClass() {
		return String.class;
	}

}
