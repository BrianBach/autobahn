package net.geant.autobahn.autoBahnGUI.webflow;

import net.geant.autobahn.useraccesspoint.Mode;
import org.springframework.binding.convert.converters.TwoWayConverter;
import org.springframework.util.StringUtils;

public class ReservationModeConverter implements TwoWayConverter{

	@Override
	public Object convertTargetToSourceClass(Object target, Class c)
			throws Exception {
		String value = (String) target;
		if (!c.equals(Mode.class))
			throw new IllegalArgumentException();
		if (StringUtils.hasText(value)) {
			return Mode.valueOf(value);
	    } else
	    	throw new IllegalArgumentException();
	}
	
	@Override
	public Object convertSourceToTargetClass(Object value, Class c)
			throws Exception {
		if (value == null)
			return Mode.VLAN;
		return value.toString();
	}
	
	@Override
	public Class getSourceClass() {
		// TODO Auto-generated method stub
		return Mode.class;
	}

	@Override
	public Class getTargetClass() {
		// TODO Auto-generated method stub
		return String.class;
	}





}
