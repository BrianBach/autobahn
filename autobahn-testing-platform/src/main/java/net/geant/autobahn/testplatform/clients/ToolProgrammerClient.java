package net.geant.autobahn.testplatform.clients;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.tool.mock.Result;
import net.geant.autobahn.tool.mock.ToolBehaviour;
import net.geant.autobahn.tool.mock.ToolProgrammer;

public class ToolProgrammerClient {

	private Service service;
	
    public final static QName SERVICE = new QName("http://mock.tool.autobahn.geant.net/", "ToolProgrammerService");
    public final static QName ToolProgrammerPort = new QName("http://mock.tool.autobahn.geant.net/", "ToolProgrammerPort");

    public ToolProgrammerClient(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(ToolProgrammerPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns DmAdministration
     */
    @WebEndpoint(name = "ToolProgrammerPort")
    public ToolProgrammer getToolProgrammerPort() {
        return service.getPort(ToolProgrammer.class);
    }

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length < 3) {
			System.out.println("Wrong usage");
			return;
		}
			
		ToolProgrammerClient tpclient = new ToolProgrammerClient(args[0]);
		
		ToolBehaviour beh = null;
		
		if("REQ_EX".equals(args[1])) {
			beh = new ToolBehaviour(Result.REQ_EX, Integer.valueOf(args[2]));
		} else if("SYS_EX".equals(args[1])){
			beh = new ToolBehaviour(Result.SYS_EX, Integer.valueOf(args[2]));
		} else if("OK".equals(args[1])) {
			beh = new ToolBehaviour(Result.OK, Integer.valueOf(args[2]));
		} else {
			System.out.println("Wrong usage");
			return;
		}
		
		if(beh != null) {
			tpclient.getToolProgrammerPort().setAddBehaviour(beh);
		}
	}

}
