package net.geant.autobahn.main;

import java.io.IOException;
import java.io.FileInputStream;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.geant.autobahn.proxy.ProxyImpl;
import net.geant.autobahn.proxy.ProxyReceiver;
import net.geant.autobahn.proxy.Subscriber;

import org.apache.log4j.Logger;
import java.util.Properties;

/**
 * @author Michal
 *
 */
public class ProxyServlet implements Servlet {

    private static Logger log = Logger.getLogger(ProxyServlet.class);
    private ServletConfig servletConfig;
    private static String appPath;
    private static Properties properties;
    private ProxyReceiver proxy;
    private Subscriber subscriber;

    public static String getAppPath() {

        return appPath;
                
    }

    public static Properties getProperties() {

        return properties;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#destroy()
     */
    public void destroy() {

        try {
            if (proxy != null) {
                proxy.close();
            //cxfServlet.destroy();
            }
            if (subscriber != null)
                subscriber.close();
        } catch (IOException e) { } 
        log.info("PROXY destroyed");
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#getServletConfig()
     */
    public ServletConfig getServletConfig() {

        return servletConfig;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#getServletInfo()
     */
    public String getServletInfo() {

        return "PROXY";
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */
    public void init(ServletConfig arg0) throws ServletException {

        this.servletConfig = arg0;
        appPath = this.servletConfig.getServletContext().getRealPath("WEB-INF/classes");

        properties = new Properties();
        try {
            properties.load(new FileInputStream(appPath + "/etc/proxy.properties"));

            proxy = new ProxyReceiver();
            int port = Integer.parseInt(properties.getProperty("proxy.listen.port"));
            proxy.init(port, new ProxyImpl());
            
            // init subscriber
            //subscriber = new Subscriber("", 3600 * 1000);

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        // init proxy server
        //Object impl = new ProxyImpl();
        //String endPoint = properties.getProperty("proxy.address");

        //cxfServlet = new CXFServlet();
        //cxfServlet.init(this.servletConfig);

        //cxfServlet.loadBus(arg0);
        //cxfServlet.createServletController(this.servletConfig);

        //Bus bus = cxfServlet.getBus();
        //BusFactory.setDefaultBus(bus);

        //log.info("publishing - " + endPoint);
        //Endpoint.create(new ProxyImpl());
        //Endpoint.publish(endPoint, impl);
        //log.info("cxfire published");
        
        

        log.info("===== PROXY initialized - " + appPath + "=====");
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    public void service(ServletRequest arg0, ServletResponse arg1)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}
