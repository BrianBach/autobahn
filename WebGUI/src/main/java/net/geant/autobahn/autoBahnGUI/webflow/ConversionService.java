package net.geant.autobahn.autoBahnGUI.webflow;

import org.apache.log4j.Logger;
import org.springframework.binding.convert.converters.TwoWayConverter;
import org.springframework.binding.convert.service.DefaultConversionService;
import java.util.Map;

public class ConversionService extends DefaultConversionService {
	
	Map<String, TwoWayConverter> converters;

	Logger logger = Logger.getLogger(ConversionService.class);
	
	public Map<String, TwoWayConverter> getConverters() {
		return converters;
	}
	
	public void setConverters(Map<String, TwoWayConverter> converters) {
		this.converters = converters;
	}
	public void initConverters(){
		if (this.converters!= null || this.converters.isEmpty())
			return;
		for (String name: converters.keySet()){
			logger.info (name+":"+converters.get(name).getSourceClass());
			addConverter(name, converters.get(name));
		}
	}

	@Override
	protected void addDefaultConverters() {
		// TODO Auto-generated method stub
		super.addDefaultConverters();
		addConverter("calendar", new CalendarConverter());
		addConverter("reservation-priority", new ReservationPriorityConverter());
		addConverter("reservation-resiliency", new ReservationResiliencyConverter());
		
	}
	
}
