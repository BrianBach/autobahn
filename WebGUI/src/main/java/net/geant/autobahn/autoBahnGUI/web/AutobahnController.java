package net.geant.autobahn.autoBahnGUI.web;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.io.BufferedReader;
import java.io.IOException;

import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.StatisticsType;
import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Line;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.model.StatisticsFormModel;
import net.geant.autobahn.autoBahnGUI.topology.TopologyFinder;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Main  controller for Autobahn client portal 
 *
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */

@Controller
public class AutobahnController {

    /**
     * Identifies the GUI manager module
     */
    @Autowired
    Manager  manager;

    /**
     * Identities topology finder module
     */
    @Autowired
    TopologyFinder topologyFinder;

    /**
     * Logs information
     */
    protected final Log logger = LogFactory.getLog(getClass());

    public AutobahnController(){
        logger.info("Creating controller");
    }

    @RequestMapping("/notfound.htm")
    public void notFound (){

    }

    /**
     * Custom handler for home.
     *
     * @param model model for response params
     */
    @RequestMapping("/home.htm")
    public void homeHandler(){
        Logger.getLogger("autoBAHN controler").info("In homeHandler");
    }

    /**
     * Custom handler for login.
     *
     * @param model model for response params
     */
    @RequestMapping("/login.htm")
    public void homeLogin() {
        Logger.getLogger("autoBAHN controler").info("In login");
    }

    /**
     * Custom handler for login error.
     *
     * @param model model for response params
     */
    @RequestMapping("/login_error.htm")
    public void homeLoginError(Map<String, Object> model) {
        Logger.getLogger("autoBAHN controler").info("In login error");
        model.put("login_error", 1);
    }

    @RequestMapping("/secure/noIDMRegistered.htm")
    public void noIdms(){
        Logger.getLogger("autoBAHN controler").info("In noIdms");
    }

    /**
     * Custom handler for map view.
     * @param request current HTTP request
     * @param response current HTTP response
     * @return a ModelAndView to render the response
     */
    @RequestMapping("/secure/services-map.htm")
    public void mapHandler (@RequestParam String  service,@RequestParam String  domain, Map<String, Object> model){

    	logger.info("Requesting map with params");
        String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
        String[] linkStatusName = {"Up","Down"};
        model.put("linkColors",linkStatusColor);
        model.put("linkStates",linkStatusName);
        Map<String,String> services = manager.getServicesForAllInterDomainManagers();
        model.put ("services", services);
        if (service!=null && service.length()>0){
            model.put("reservationLinkColors",Line.reservationsStates);
            model.put("reservationStates",manager.getReservationStates());
        }
    }

    @RequestMapping("/secure/services-list.htm")
    public void mapServicesListHandler ( Map<String, Object> model){
        Map<String, String> services = manager.getServicesForAllInterDomainManagers();
        model.put ("services", services);
    }

    /*public ModelAndView handleMap(HttpServletRequest request, HttpServletResponse response) throws ServletException {
         ModelAndView modelAndView = new ModelAndView(reservationsMapView);
         String service=(String)request.getParameter("service");
         String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
         String[] linkStatusName = {"Up","Down"};
         modelAndView.addObject("linkColors",linkStatusColor);
         modelAndView.addObject("linkStates",linkStatusName);
         List<Service> services = manager.getServicesForAllInterDomainManagers();
         modelAndView.addObject ("services", services);
         if (service!=null){
             modelAndView.addObject("reservationLinkColors",Line.reservationsStates);
             modelAndView.addObject("reservationStates",manager.getReservationStates());
         }
         return modelAndView;
     }*/
    /**
     * Custom handler for getting map topology in XML format
     * @param request current HTTP request
     * @param response current HTTP response
     * @return a ModelAndView to render the response
     */
    /*@RequestMapping("/secure/services-map.htm")
     public void mapHandlerXML (@RequestParam String domain, @RequestParam String  service, Map<String, Object> model){
         Topology topology=null;
         if (service !=null)
             topology=topologyFinder.getGoogleTopology(domain,service);
         else
             topology=topologyFinder.getGoogleTopology();
         model.put("topology", topology);
     }*/

    @RequestMapping("/secure/topology.xml")
    public void handleTopologyXML(@RequestParam String service,@RequestParam String domain, Map<String, Object> model){
        logger.debug("handle topology xml");
        Topology topology=null;
        if (service !=null && service.length()>0){
            topology=topologyFinder.getGoogleTopology(domain,service);}
        else
            topology=topologyFinder.getGoogleTopology();
        model.put("topology", topology);
    }

    @RequestMapping("/secure/restartAll.htm")
    public void handleTopologyChange(@RequestParam String domain) {
        logger.info("All IDMs will be restarted due to topology change at " + domain);
        manager.handleTopologyChange(domain, true);
    }

    @RequestMapping("/secure/servicesforidm.htm")
    public void handleServicesForIdm(@RequestParam String currentIdm,Map<String, Object> model){
        ServicesFormModel services = null;
        String[] reservationStates = null;

        logger.debug("getting services for idm, currentIdm:" + currentIdm);
        if (currentIdm != null) {
            services = manager.getSubmitedServicesInIDM(currentIdm);
            reservationStates = manager.getReservationStates();
        }
        //logger.debug("services:"+services);
        //logger.debug("reservationStates:"+reservationStates);
        //logger.debug("services.getServices():"+services.getServices());
        model.put("services", services);
        model.put("reservationStates", reservationStates);
    }

    @RequestMapping("/secure/settings.htm")
    public void handleSettingsChange(@RequestParam String currentIdm, Map<String, Object> model){

        Logger.getLogger("autoBAHN controler").info("handle settings change");
        List<KeyValue> properties=null;
        if (currentIdm  !=null){
            SettingsFormModel fm=manager.getSettingsForInterDomainManager(currentIdm);
            properties=fm.getProperties();

        }
        model.put("settings", properties);
        //Logger.getLogger("autoBAHN controler").info("PROPERTIES!?"+properties);
    }

    //no jsp because json request returns false
    @RequestMapping("/secure/settings_save.htm")
    public void handleSettingsChange(HttpServletRequest request, HttpServletResponse response){

        try {
            Logger.getLogger("autoBAHN controler").info("handle settings set change");
            //read json request
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line + "\n");
                line = reader.readLine();
            }
            reader.close();
            String data = sb.toString();

            JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(data);
            LinkedHashSet propkeys=new LinkedHashSet(jsonObject.keySet());

            //Logger.getLogger("autoBAHN controler").info(data);
            //parse
            List<KeyValue> properties=new ArrayList<KeyValue>();
            String currentIDm=null;

            for (Object entry:propkeys)
            {
                String key=entry.toString();
                String value=jsonObject.getString(key);
                Logger.getLogger("autoBAHN controler").info("prop "+key+" val "+value);
                if (key.equals("currentIdm") )
                {
                    currentIDm=value;
                } else {
                    properties.add(new KeyValue(key,value));
                }

            }

/*
            data=data.substring(1,data.length()-2);

            String[] entries=data.split(",");
            for (int i=0;i<entries.length;i++)
            {
                String entry=entries[i];
                //Logger.getLogger("autoBAHN controler").info("entry is "+entry);
                String[] keyvalue=entry.split(":");
                if (keyvalue.length==2)
                {

                    if (keyvalue[0].equals("currentIdm") )
                    {
                        currentIDm=keyvalue[1];
                    } else {
                        properties.add(new KeyValue(keyvalue[0],keyvalue[1]));
                    }
                }
            }*/

            //saving
            response.setContentType("text/x-json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            JSONObject jsonRes;
            if (currentIDm!=null)
            {
                manager.setPropertiesForInterDomainManager(currentIDm,properties);
                jsonRes = new JSONObject()
                              .element( "result", "success" );
                //response.getWriter().write("{success:true}");
                Logger.getLogger("autoBAHN controler").info("success");
            }else {
                jsonRes = new JSONObject()
                              .element( "result", "success" );
                //response.getWriter().write("{error:true}");
                Logger.getLogger("autoBAHN controler").info("error");
            }
             jsonRes.write(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //no jsp because json request returns false
    @SuppressWarnings("rawtypes")
	@RequestMapping("/secure/logs_request.htm")
    public void handleLogsRequest(HttpServletRequest request, HttpServletResponse response){

        try {
            Logger.getLogger("autoBAHN controler").info("handle log change");
            
            StringBuilder sb = new StringBuilder();
            
            @SuppressWarnings("unchecked")
			java.util.Enumeration<String> paramEnum = request.getParameterNames();
			
            	while(paramEnum.hasMoreElements())
            	{
            		String paramName = (String)paramEnum.nextElement();
            		sb.append(paramName + "\n");
            	}
		
            	String data = sb.toString();
            	JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(data);
            	
            	@SuppressWarnings("unchecked")
            	LinkedHashSet propkeys=new LinkedHashSet(jsonObject.keySet());

            Logger.getLogger("autoBAHN controler").info(data);

            String currentIDm=null;

            for (Object entry:propkeys)
            {
                String key=entry.toString();
                String value=jsonObject.getString(key);
                Logger.getLogger("autoBAHN controler").info("prop "+key+" val "+value);
                if (key.equals("currentIdm") )
                {
                    currentIDm=value;
                }

            }

            //saving
            response.setContentType("text/x-json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            JSONObject jsonRes;
            if (currentIDm!=null)
            {
                String log=manager.getLogsInterDomainManager(currentIDm,true,true);
                jsonRes = new JSONObject().element("result", log);
                //response.getWriter().write("{success:true}");
                Logger.getLogger("autoBAHN controler").info("success");
            }else {
                jsonRes = new JSONObject().element("result", "error");
                //response.getWriter().write("{error:true}");
                Logger.getLogger("autoBAHN controler").info("error");
            }
             jsonRes.write(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

     //commenting
     Logger.getLogger("autoBAHN controler").info("Logs request");
    }

    @RequestMapping("/secure/statistics.htm")
    public void handleStatisticsChange(@RequestParam String currentIdm, Map<String, Object> model){

        Logger.getLogger("autoBAHN controler").info("handle statistics change");
        StatisticsType st = null;
        if (currentIdm != null) {
            StatisticsFormModel fm = manager.getStatisticsForInterDomainManager(currentIdm);
            st = fm.getStatistics();
        }
        model.put("statistics", st);
    }
    
    public void doNothing()
    {

    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public TopologyFinder getTopologyFinder() {
        return topologyFinder;
    }

    public void setTopologyFinder(TopologyFinder topologyFinder) {
        this.topologyFinder = topologyFinder;
    }

}
