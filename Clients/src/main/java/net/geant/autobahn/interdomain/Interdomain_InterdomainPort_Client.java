
package net.geant.autobahn.interdomain;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 15 15:30:45 CET 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

public final class Interdomain_InterdomainPort_Client {

    private static final QName SERVICE_NAME = new QName("http://interdomain.autobahn.geant.net/", "InterdomainService");

    private Interdomain_InterdomainPort_Client() {
    }

    public static void main(String args[]) throws Exception {

        if (args.length == 0) { 
            System.out.println("please specify wsdl");
            System.exit(1); 
        }
        URL wsdlURL = null;
        File wsdlFile = new File(args[0]);
        try {
            if (wsdlFile.exists()) {
                wsdlURL = wsdlFile.toURL();
            } else {
                wsdlURL = new URL(args[0]);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
      
        InterdomainService ss = new InterdomainService(wsdlURL, SERVICE_NAME);
        Interdomain port = ss.getInterdomainPort();  
        
        {
        System.out.println("Invoking scheduleReservation...");
        net.geant.autobahn.reservation.Reservation _scheduleReservation_reservation = null;
        port.scheduleReservation(_scheduleReservation_reservation);


        }
        {
        System.out.println("Invoking reportFinished...");
        java.lang.String _reportFinished_resID = "";
        java.lang.String _reportFinished_message = "";
        boolean _reportFinished_success = false;
        port.reportFinished(_reportFinished_resID, _reportFinished_message, _reportFinished_success);


        }
        {
        System.out.println("Invoking reportSchedule...");
        java.lang.String _reportSchedule_resID = "";
        int _reportSchedule_code = 0;
        java.lang.String _reportSchedule_message = "";
        boolean _reportSchedule_success = false;
        net.geant.autobahn.constraints.GlobalConstraints _reportSchedule_global = null;
        port.reportSchedule(_reportSchedule_resID, _reportSchedule_code, _reportSchedule_message, _reportSchedule_success, _reportSchedule_global);


        }
        {
        System.out.println("Invoking reportCancellation...");
        java.lang.String _reportCancellation_resID = "";
        java.lang.String _reportCancellation_message = "";
        boolean _reportCancellation_success = false;
        port.reportCancellation(_reportCancellation_resID, _reportCancellation_message, _reportCancellation_success);


        }
        {
        System.out.println("Invoking cancelReservation...");
        java.lang.String _cancelReservation_resID = "";
        port.cancelReservation(_cancelReservation_resID);


        }
        {
        System.out.println("Invoking hello...");
        boolean _hello__return = port.hello();
        System.out.println("hello.result=" + _hello__return);


        }
        {
        System.out.println("Invoking reportActive...");
        java.lang.String _reportActive_resID = "";
        java.lang.String _reportActive_message = "";
        boolean _reportActive_success = false;
        port.reportActive(_reportActive_resID, _reportActive_message, _reportActive_success);


        }

        System.exit(0);
    }

}
