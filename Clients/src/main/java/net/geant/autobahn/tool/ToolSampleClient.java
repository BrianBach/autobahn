package net.geant.autobahn.tool;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.tool.types.Constraint;
import net.geant.autobahn.tool.types.ConstraintsType;
import net.geant.autobahn.tool.types.GenericInterfaceType;
import net.geant.autobahn.tool.types.GenericLinkType;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.NodeType;
import net.geant.autobahn.tool.types.ReservationParamsType;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;

public final class ToolSampleClient {

    private static final QName SERVICE_NAME = new QName(
            "http://tool.autobahn.geant.net/", "ToolService");
    private ToolService service;

    private ToolSampleClient(String endPoint) {
        service = new ToolService(endPoint);
    }

    public static void main(String args[]) throws Exception {

        LogUtils.setLoggerClass(Log4jLogger.class);

        String tp = readStr("Give TP to connect to:", "localhost");
        String port = readStr("Give TP port:", "9090");

        ToolSampleClient cli = new ToolSampleClient("http://" + tp + ":" + port
                + "/autobahn/uap");
        System.out.println("Connecting to " + tp + ":" + port);

        String choice = readStr("Add or remove?(y to add)", "n");

        if (choice.equals("y")) {
            cli.addReservation();
        } else {
            cli.removeReservation();
        }

        System.exit(0);
    }

    private void removeReservation() throws AAIException, SystemException,
            ReservationNotFoundException, RequestException, IOException {
        Tool port = service.getToolPort();

        String resID = readStr("Give res to remove:", "res1");
        port.removeReservation(resID, null, null);
    }

    private void addReservation() throws ParseException,
            DatatypeConfigurationException, AAIException, SystemException,
            RequestException, ResourceNotFoundException, IOException {
        Tool port = service.getToolPort();

        ReservationParamsType params = new ReservationParamsType();
        params.setCapacity(new Long(readStr("Capacity (bps)?", "1000000")));
        params.setMaxDelay(new Integer(readStr("Delay (ms)?", "0")));
        
        Calendar startcal = Calendar.getInstance();
        params.setStartTime(startcal);
        Calendar endcal = Calendar.getInstance();
        endcal.add(Calendar.HOUR_OF_DAY, 3);
        params.setEndTime(endcal);

        String vlanStr = readStr("VLAN?", "100");
        String vlanTransStr = readStr("VLAN translation support (true/false)?", "false");

        List<GenericLinkType> links = new ArrayList<GenericLinkType>();
        List<ConstraintsType> pathConstraints = new ArrayList<ConstraintsType>();
        String moreLinks;
        do {
            System.out.println("Building res path...");
            NodeType startNode = new NodeType();
            startNode.setName(readStr("Start Node name:", "node1"));
            startNode.setVlanTranslationSupport(new Boolean(vlanTransStr));
            GenericInterfaceType startInterface = new GenericInterfaceType();
            startInterface.setNode(startNode);
            startInterface.setName(readStr("Start if name:", "if1"));

            NodeType endNode = new NodeType();
            endNode.setName(readStr("End Node name:", "node1"));
            endNode.setVlanTranslationSupport(new Boolean(vlanTransStr));
            GenericInterfaceType endInterface = new GenericInterfaceType();
            endInterface.setNode(endNode);
            endInterface.setName(readStr("End if name:", "if1"));

            GenericLinkType glink = new GenericLinkType();
            glink.setStartInterface(startInterface);
            glink.setEndInterface(endInterface);
            links.add(glink);

            ConstraintsType constraintsType = new ConstraintsType();
            List<Constraint> constraints = new ArrayList<Constraint>();
            Constraint vlans = new Constraint();
            vlans.setName(ConstraintsNames.VLANS);
            vlans.setValue(vlanStr);
            constraints.add(vlans);
            Constraint vlanTrans = new Constraint();
            vlanTrans.setName(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION);
            vlanTrans.setValue(vlanTransStr);
            constraints.add(vlanTrans);
            constraintsType.setConstraints(constraints);
            pathConstraints.add(constraintsType);

            moreLinks = readStr("Add link? (y/n)", "n");
        } while (moreLinks.equals("y"));

        IntradomainPathType ipath = new IntradomainPathType();
        ipath.setLinks(links);
        ipath.setConstraints(pathConstraints);

        port.addReservation(readStr("Give resID:", "res1"), ipath, params);
    }

    /**
     * Reads String from standard input
     * 
     * @param message - Prompt message to display to the user
     * @param def - Value to use if user presses return
     * @return - The string from the user
     * @throws IOException
     */
    public static String readStr(String message, String def) throws IOException {
        System.out.println(message);
        byte byteStr[] = new byte[50];
        System.in.read(byteStr);
        String str = (new String(byteStr).trim());

        if (str == null || str.equals("")) {
            return def;
        } else {
            return str;
        }
    }
}
