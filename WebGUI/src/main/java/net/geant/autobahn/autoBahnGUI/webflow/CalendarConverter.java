package net.geant.autobahn.autoBahnGUI.webflow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;

import org.springframework.binding.convert.converters.TwoWayConverter;
import org.springframework.util.StringUtils;

public class CalendarConverter implements TwoWayConverter {

	private String format = "yyyy-MM-dd'T'HH:mm:ss";
	
	private SimpleDateFormat dateFormat;
	
	public CalendarConverter () {
		dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);		
	}
	
	public Object convertTargetToSourceClass(Object target, Class c)
			throws Exception {	
		String dateInString = (String) target;
		if (!c.equals(Calendar.class))
			throw new IllegalArgumentException();
		if (StringUtils.hasText(dateInString)){
			try{
				this.dateFormat.parse(dateInString);
			} catch (ParseException ex) {
		    	   throw new IllegalArgumentException();  
		    } 
		
		Calendar calendar= DatatypeFactory.newInstance().newXMLGregorianCalendar(dateInString).toGregorianCalendar();
		return calendar;    
	        
	 }else
		 throw new IllegalArgumentException();
	}

	public Object convertSourceToTargetClass(Object calendar, Class c)
			throws Exception {
		Calendar value = (Calendar) calendar;
		if (value == null) {
			GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
		}
		return value.getTime().toString();

	}

	public Class getSourceClass() {
		return Calendar.class;
	}

	public Class getTargetClass() {
		return String.class;
	}
}
