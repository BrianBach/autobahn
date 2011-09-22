package net.geant.autobahn.lookup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LookupSampleClient {

    public static void main(String args[]) throws Exception {
        System.out.println("Give LS URL to connect to:");
        byte byteStr[] = new byte[1000];
        System.in.read(byteStr);
        String lsUrl = (new String(byteStr).trim());
        if (!isLSavailable(lsUrl)) {
            return;
        }

        LookupService lookup = new LookupService(lsUrl);

        try {
            System.out.println("\n---QueryAllFriendlyName():");
            ArrayList<String> friendlyNames = lookup.QueryAllFriendlyName();
            for (String s : friendlyNames) {
                System.out.println(s);
            }
            
            System.out.println("\n---QueryAllEdgePort():");
            System.out.println("Give startDomain parameter:");
            byte sdStr[] = new byte[50];
            System.in.read(sdStr);
            String sd = (new String(sdStr).trim());
            if (!(sd == null || sd.equals(""))) {
                ArrayList<String> edgePorts = lookup.QueryAllEdgePort(sd);
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
                ArrayList<String> edgePorts = lookup.QueryEdgePort(sdom, edom);
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
                System.out.println(lookup.QueryFriendlyName(ep));
            }
            
            System.out.println("\n---QueryIdmLocation():");
            System.out.println("Give idm parameter:");
            byte idmStr[] = new byte[50];
            System.in.read(idmStr);
            String idm = (new String(idmStr).trim());
            if (!(idm == null || idm.equals(""))) {
                System.out.println(lookup.QueryIdmLocation(idm));
            }
            
        } catch (LookupServiceException lse) {
            lse.printStackTrace();
        }

    }

    private static boolean isLSavailable(String ls) {
        if ((ls == null) || ls.equalsIgnoreCase("none") || ls.equals("")) {
            return false;
        }
        // Check if it is a proper URL
        try {
            new URL(ls);
        } catch (MalformedURLException e) {
            System.out.println(ls + " is not a proper URL for LS");
            return false;
        }
        return true;
    }

}
