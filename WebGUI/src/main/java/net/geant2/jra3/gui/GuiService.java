
package net.geant2.jra3.gui;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import net.geant2.jra3.gui.Gui;

/**
 * This class was generated by Apache CXF (incubator) 2.0.4-incubator
 * Mon Mar 02 13:20:38 CET 2009
 * Generated source version: 2.0.4-incubator
 * 
 */

@WebServiceClient(name = "GuiService", targetNamespace = "http://gui.jra3.geant2.net/", wsdlLocation = "file:wsdl/gui.wsdl")
public class GuiService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://gui.jra3.geant2.net/", "GuiService");
    public final static QName GuiPort = new QName("http://gui.jra3.geant2.net/", "GuiPort");
    static {
        URL url = null;
        try {
            url = new URL("file:wsdl/gui.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:wsdl/gui.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GuiService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GuiService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GuiService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns GuiPort
     */
    @WebEndpoint(name = "GuiPort")
    public Gui getGuiPort() {
        return (Gui)super.getPort(GuiPort, Gui.class);
    }

}
