package net.geant.autobahn.administration;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import net.geant.autobahn.administration.Administration;

public class RestartClient {

    private static final QName SERVICE_NAME = new QName("http://administration.autobahn.geant.net/", "AdministrationService");
    private final static QName AdministrationPort = new QName("http://administration.autobahn.geant.net/", "AdministrationPort");
    
    private Administration adm = null;
    
    public RestartClient(String target) {
        Service service = Service.create(SERVICE_NAME);
        service.addPort(AdministrationPort, SOAPBinding.SOAP11HTTP_BINDING, target);

        adm = service.getPort(AdministrationPort, Administration.class);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        System.out.println("Give IDM to connect to:");
        byte byteStr[] = new byte[50];
        System.in.read(byteStr);
        String idm = (new String(byteStr).trim());

        System.out.println("Give IDM port:");
        byteStr = new byte[50];
        System.in.read(byteStr);
        String port = (new String(byteStr).trim());
        
        System.out.println("Want to try and remove reservations?(y/n):");
        byteStr = new byte[50];
        System.in.read(byteStr);
        String removeStr = (new String(byteStr).trim());
        
        if (idm == null || idm.equals("")) {
            //idm = "109.105.111.62";
            idm = "150.140.8.13";
        }
        if (port == null || port.equals("")) {
            port = "8080";
        }
        boolean removeRsv = false;
        if (removeStr != null && removeStr.equals("y")) {
            removeRsv = true;
        }
        
        RestartClient instance = new RestartClient("http://"+idm+":"+port+"/autobahn/administration");
        System.out.println("Connecting to "+idm+":"+port);
        
        System.out.println("\n---getServices():");
        List<ServiceType> serv = instance.adm.getServices();
        if (serv != null) {
            for (int i=0; i<serv.size(); i++) {
                if (serv.get(i) != null) {
                    System.out.println(i+": "+serv.get(i).getBodID());
                }
            }
        }
        
        System.out.println("\n---calling handleTopologyChange():");
        instance.adm.handleTopologyChange(removeRsv);
        
        System.exit(0);
    }
    
    
}

