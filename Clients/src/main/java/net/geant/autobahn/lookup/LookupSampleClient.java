package net.geant.autobahn.lookup;

import java.util.ArrayList;

import net.geant.autobahn.resources.ResourcePath;

public class LookupSampleClient {

    public static void main(String args[]) throws Exception {
        //Set paths for Lookup Client
        ResourcePath resource = new ResourcePath(false);
        QueryXml.xmlFileName = "src/main/resources/etc/xml/basicQuery.xml";
        
        System.out.println("Give LS URL to connect to:");
        byte byteStr[] = new byte[1000];
        System.in.read(byteStr);
        String lsUrl = (new String(byteStr).trim());
        if (!LookupService.isLSavailable(lsUrl)) {
            return;
        }

        LookupService lookup = new LookupService(lsUrl);

        try {
            System.out.println("\n---QueryAllFriendlyName():");
            ArrayList<String> friendlyNames = lookup.queryAllFriendlyName();
            for (String s : friendlyNames) {
                System.out.println(s);
            }
            
            System.out.println("\n---QueryAllEdgePort():");
            System.out.println("Give startDomain parameter:");
            byte sdStr[] = new byte[50];
            System.in.read(sdStr);
            String sd = (new String(sdStr).trim());
            if (!(sd == null || sd.equals(""))) {
                ArrayList<String> edgePorts = lookup.queryAllEdgePort(sd);
                for (String s : edgePorts) {
                    System.out.println(s);
                }
            }
            
            System.out.println("\n---QueryEdgePort():");
            System.out.println("Give startDomain parameter:");
            byte sdomStr[] = new byte[50];
            System.in.read(sdomStr);
            String sdom = (new String(sdomStr).trim());
            System.out.println("Give endDomain parameter:");
            byte edomStr[] = new byte[50];
            System.in.read(edomStr);
            String edom = (new String(edomStr).trim());
            if (!(sdom == null || sdom.equals("") || edom == null || edom.equals(""))) {
                ArrayList<String> edgePorts = lookup.queryEdgePort(sdom, edom);
                for (String s : edgePorts) {
                    System.out.println(s);
                }
            }
            
            System.out.println("\n---QueryFriendlyName():");
            System.out.println("Give endpoint parameter:");
            byte epStr[] = new byte[50];
            System.in.read(epStr);
            String ep = (new String(epStr).trim());
            if (!(ep == null || ep.equals(""))) {
                System.out.println(lookup.queryFriendlyName(ep));
            }
            
            System.out.println("\n---QueryIdmLocation():");
            System.out.println("Give idm parameter:");
            byte idmStr[] = new byte[50];
            System.in.read(idmStr);
            String idm = (new String(idmStr).trim());
            if (!(idm == null || idm.equals(""))) {
                System.out.println(lookup.queryIdmLocation(idm));
            }
            
        } catch (LookupServiceException lse) {
            lse.printStackTrace();
        }

    }
}
