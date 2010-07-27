package net.geant.autobahn.useraccesspoint.textclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;

public class RequestParser {

	public ServiceRequest readServiceRequest(String filePath) throws IOException, IllegalStateException {
		
		InputStream is = this.getClass().getResourceAsStream(filePath);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
/*		File f = new File(filePath);
		if(!f.exists()) {
			f = new File("target/test-classes/" + filePath);
		}
		
		BufferedReader br = new BufferedReader(new FileReader(f)); */

		ServiceRequest sreq = new ServiceRequest();
		sreq.setUserName("text-client");
		
		String line = null;
		ReservationRequest rreq = null;
		
		try {
			while((line = br.readLine()) != null) {
				// new reservation
				if(line.contains("{")) {
					rreq = new ReservationRequest();
					continue;
				}
	
				// end of reservation description, add it to service
				if(line.contains("}")) {
			        rreq.setPriority(Priority.NORMAL);
			        rreq.setResiliency(Resiliency.NONE);
					
					sreq.getReservations().add(rreq);
					continue;
				}
				
				String[] contents = line.split("=");
				
				String field = contents[0].toLowerCase().trim();
				String value = contents[1].trim();
				
				if(field.equals("sport")) {
					rreq.setStartPort(value);
				} else if(field.equals("dport")) {
					rreq.setEndPort(value);
				} else if(field.equals("stime")) {
					rreq.setStartTime(parseDate(value));
				} else if(field.equals("etime")) {
					rreq.setEndTime(parseDate(value));
				} else if(field.equals("processnow")) {
					rreq.setProcessNow(Boolean.valueOf(value));
				} else if(field.equals("capacity")) {
					rreq.setCapacity(Long.valueOf(value));
				} else if(field.equals("desc")) {
					rreq.setDescription(value);
				}
			}
		} catch(Exception e) {
			throw new IllegalStateException("Wrong data format, cause: " + e.getMessage());
		}
		
		return sreq;
	}
	
	public static ServiceRequest createServiceRequest(String src, String dest, 
			long capacity, String stime, String etime, String desc, boolean now) {
		ServiceRequest sreq = new ServiceRequest();
		sreq.setUserName("test-client");
		
        sreq.getReservations().add(
				createReservationRequest(src, dest, capacity, stime, etime,	desc, now));
        
        return sreq;
	}
	
	public static ReservationRequest createReservationRequest(String src, String dest, 
			long capacity, String stime, String etime, String desc, boolean now) {
		ReservationRequest rreq = new ReservationRequest();
		
        rreq.setPriority(Priority.NORMAL);
        rreq.setResiliency(Resiliency.NONE);
		
		rreq.setStartPort(src);
		rreq.setEndPort(dest);
		try {
			rreq.setStartTime(parseDate(stime));
			rreq.setEndTime(parseDate(etime));
		} catch(Exception e) {
			System.out.println("Date Parsing error, " + e.getMessage());
			e.printStackTrace();
		}
		rreq.setCapacity(capacity);
		rreq.setDescription(desc);
		rreq.setProcessNow(now);
        
        return rreq;
	}

	private static Calendar parseDate(String value) throws ParseException,
			DatatypeConfigurationException {
		if(value.contains("min")) {
			GregorianCalendar now = (GregorianCalendar) Calendar.getInstance();
			now.add(Calendar.MINUTE, Integer.valueOf(value.replace("min", "")));
			return now;
		} else if(value.contains("sec")) {
			GregorianCalendar now = (GregorianCalendar) Calendar.getInstance();
			now.add(Calendar.SECOND, Integer.valueOf(value.replace("sec", "")));
			return now;
		}else {
			return cal(value);
		}
	}
	
    private static Calendar cal(String sdate) throws ParseException, 
    		DatatypeConfigurationException {
		Calendar result = Calendar.getInstance();

		return result;
	}
    
    private static XMLGregorianCalendar cal(GregorianCalendar cal)
			throws DatatypeConfigurationException {
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	}
}
