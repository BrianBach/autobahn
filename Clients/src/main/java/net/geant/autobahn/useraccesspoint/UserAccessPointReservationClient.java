package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

public class UserAccessPointReservationClient {

    public static final String DEFAULT_IDM = "62.40.120.202:8080";
    public static final int DEFAULT_DURATION = 1;
    public static final long DEFAULT_CAPACITY = 1000;
    public static final String DEFAULT_DESCRIPTION = "res1";
    public static final String DEFAULT_USER = "user1";
    
    private static final QName SERVICE_NAME = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointService");
    private final static QName UserAccessPointPort = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointPort");
    
    private UserAccessPoint uap = null;
    
    public UserAccessPointReservationClient(String target) {
        Service service = Service.create(SERVICE_NAME);
        service.addPort(UserAccessPointPort, SOAPBinding.SOAP11HTTP_BINDING, target);

        uap = service.getPort(UserAccessPointPort, UserAccessPoint.class);
    }
    
    public static void main(String args[]) throws Exception {
        Options opt = new Options();
        opt.addOption("h", false, "Print help for this application");
        opt.addOption("i", true, "IP:port to submit reservation to - Default " + DEFAULT_IDM);
        opt.addOption("s", true, "Start port");
        opt.addOption("e", true, "End port");
        opt.addOption("d", true, "Duration in hours - Default " + DEFAULT_DURATION + " hour");
        opt.addOption("c", true, "Capacity in bps - Default " + DEFAULT_CAPACITY + " bps");

        BasicParser parser = new BasicParser();
        CommandLine cl = parser.parse(opt, args);
        
        String sport = null;
        String eport = null;
        
        if(cl.hasOption('h')) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("clientWrapper [OPTION] ...", opt);
        }
        else {
            sport = cl.getOptionValue("s");
            if (sport==null) {
                System.out.println("You have to specify a start port");
                System.exit(0);
            }
            
            eport = cl.getOptionValue("e");
            if (eport==null) {
                System.out.println("You have to specify an end port");
                System.exit(0);
            }
            
            int dur = DEFAULT_DURATION;
            if (cl.hasOption('d')) {
                String duration = cl.getOptionValue("d");
                try {
                    dur = new Integer(duration);
                } catch (NumberFormatException ne) {}
            }
            
            long cap = DEFAULT_CAPACITY;
            if (cl.hasOption('c')) {
                String capacity = cl.getOptionValue("c");
                try {
                    cap = new Long(capacity);
                } catch (NumberFormatException ne) {}
            }
            
            String idm = DEFAULT_IDM;
            if (cl.hasOption('i')) {
                idm = cl.getOptionValue("i");
            }            
            
            UserAccessPointReservationClient instance = 
                new UserAccessPointReservationClient("http://" + idm + "/autobahn/uap");

            ServiceRequest sreq = new ServiceRequest();
            sreq.setUserName(DEFAULT_USER);

            ReservationRequest r1 = new ReservationRequest();
            r1.setCapacity(cap);
            r1.setDescription(DEFAULT_DESCRIPTION);
            r1.setStartPort(new PortType(sport));
            r1.setEndPort(new PortType(eport));
            
            GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
            GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
            end.add(Calendar.HOUR, dur);

            r1.setStartTime(start);
            r1.setProcessNow(true);
            r1.setEndTime(end);
            
            r1.setMaxDelay(2);
            r1.setPriority(Priority.NORMAL);
            r1.setResiliency(Resiliency.NONE);

            sreq.getReservations().add(r1);

            System.out.println("ServiceID:\n" + instance.uap.submitService(sreq));
        }

        System.exit(0);
    }
}