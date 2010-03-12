package net.geant.autobahn.oscars.notify;

import java.rmi.RemoteException;
//import net.es.oscars.oscars.Event;
import net.es.oscars.oscars.EventContent;
import net.es.oscars.oscars.OSCARS;
import net.es.oscars.oscars.OSCARS_Service;
import net.es.oscars.oscars.PathInfo;
import net.es.oscars.oscars.ResDetails;
import net.geant.autobahn.proxy.ReservationInfo;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.*;
import org.oasis_open.docs.wsn.br_2.*;
//import org.w3.www._2005._08.addressing.*;
import net.es.oscars.oscars.OSCARSNotify;
import net.es.oscars.oscars.OSCARSNotify_Service;

/**
 *
 * @author Michał
 */
public class OscarsNotifyClient {

    // Added during CXF migration (formerly existing in net.es.oscars.oscars.Event class)
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
            "http://oscars.es.net/OSCARS",
            "event",
            "ns2");
    
    private Logger log = Logger.getLogger(this.getClass());
    private OSCARSNotify rc = null;

    String toOscarsReservationState(int resState) {

        String state = null;
        switch (resState) {

            case 5: // scheduled
                state = "PENDING";
                break;

            case 9: // activating
                state = "INSETUP";
                break;
                
            case 10: // active
                state = "ACTIVE";
                break;
        }
        return state;
    }

    String toOscarsEventState(int resState) {

        String state = null;
        switch (resState) {
            case 5: //scheduled
                state = "RESERVATION_CREATE_COMPLETED";
                break;
                
            case 9: // activating
                state = "DOWNSTREAM_PATH_SETUP_CONFIRMED";
                break;
                
            case 10: // active
                state = "PATH_SETUP_COMPLETED";
                break;
        }
        return state;
    }

    public OscarsNotifyClient(String endPoint) {
        
        if("none".equals(endPoint))
            return;
        
        OSCARSNotify_Service service = new OSCARSNotify_Service(/*TODO endPoint*/);
        rc = service.getOSCARSNotify();
    }

    public void Notify(ReservationInfo resInfo) throws RemoteException {
            
        // try to see if we should notify about this event
        String resState = toOscarsReservationState(resInfo.getState());
        if (resState == null)
            return;
        
        String eventState = toOscarsEventState(resInfo.getState());

        NotificationMessageHolderType msgHolder = new NotificationMessageHolderType();

        EventContent event = new EventContent();
        event.setId("event-666");
        event.setType(eventState);
        event.setTimestamp(System.currentTimeMillis() / 1000L);
        event.setUserLogin("autobahn");
        event.setErrorCode("AUTHN_FAILED");
        event.setErrorMessage("Identity cannot be determined.");

        ResDetails resDetails = new ResDetails();
        resDetails.setGlobalReservationId(resInfo.getBodID());
        resDetails.setLogin("autobahn");
        resDetails.setStatus(resState);
        resDetails.setStartTime(resInfo.getStartTime().getTimeInMillis());
        resDetails.setCreateTime(resInfo.getStartTime().getTimeInMillis());
        resDetails.setEndTime(resInfo.getEndTime().getTimeInMillis());
        resDetails.setBandwidth((int)resInfo.getCapacity());
        resDetails.setDescription(resInfo.getDescription());
        PathInfo pathInfo = new PathInfo();
        
        pathInfo.setPathSetupMode("timer-automatic");
        resDetails.setPathInfo(pathInfo);

        event.setResDetails(resDetails);

        MessageType msg = new MessageType();
        OMFactory omFactory = (OMFactory) OMAbstractFactory.getOMFactory();
        OMElement omEvent = null;
        /*
         TODO: Implement business logic (commented out during CXF migration)
        try {
            omEvent = event.getOMElement(this.MY_QNAME, omFactory);
        } catch (ADBException ex) {
            System.out.println("Notify axis internals - " + ex.getMessage());
        }
        msg.addExtraElement(omEvent);*/

        TopicExpressionType topicExpr = new TopicExpressionType();
        topicExpr.setValue("idc:INFO");
        topicExpr.setDialect("http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple");

        //msgHolder.setSubscriptionReference(subRef);
        msgHolder.setTopic(topicExpr);
        //msgHolder.setProducerReference(prodRef);
        msgHolder.setMessage(msg);


        Notify notification = new Notify();
        notification.getNotificationMessage().add(msgHolder);
        rc.notify(notification);
    }

    public void subscribe() throws RemoteException {

        log.debug("subscribe.begin");

        Subscribe subscribe = new Subscribe();

        //SubscribeResponse resp = stub.Subscribe(subscribe);



        log.debug("subscribe.end");
    }

    public void registerSubscriber() throws RemoteException {

        log.debug("registerSubscriber.begin");

        try {
            RegisterPublisherResponse resp;
            RegisterPublisher register = new RegisterPublisher();
            register.setDemand(true);
            TopicExpressionType topic = new TopicExpressionType();
            topic.setDialect("hemp.man.poznan.pl");
            topic.setValue("IDC:INFO");
            register.getTopic().add(topic);
            EndpointReferenceType publisherRef = new EndpointReferenceType();
            AttributedURIType address = new AttributedURIType();
            address.setValue("hemp.man.poznan.pl");
            publisherRef.setAddress(address);
            register.setPublisherReference(publisherRef);

            resp = rc.registerPublisher(register);
            EndpointReferenceType pubRef = resp.getPublisherRegistrationReference();
            EndpointReferenceType conRef = resp.getConsumerReference();

            System.out.println("Publisher Registration Manager: " + pubRef.getAddress());
            System.out.println("Publisher Registration Id: " +
                    pubRef.getReferenceParameters().getPublisherRegistrationId());
            System.out.println("Notification Consumer: " + conRef.getAddress());
        } catch (Exception e) {

            log.info("registerSubscriber: " + e.getMessage());
            throw new RemoteException(e.getMessage());
        }
        log.debug("registerSubscriber.end");
    }

    public void destroyRegistration() throws RemoteException {

       log.debug("destroyRegistration.begin");

        try {
            DestroyRegistrationResponse resp;
            DestroyRegistration destroy = new DestroyRegistration();
            EndpointReferenceType publisherRef = new EndpointReferenceType();
            AttributedURIType address = new AttributedURIType();
            address.setValue("hemp.man.poznan.pl");
            publisherRef.setAddress(address);

            destroy.setPublisherRegistrationReference(publisherRef);

            resp = rc.destroyRegistration(destroy);

        } catch (Exception e) {

            log.debug("destroyRegistration: " + e.getMessage());
            throw new RemoteException(e.getMessage());
        }

        log.debug("destroyRegistration.end");
    }
}
