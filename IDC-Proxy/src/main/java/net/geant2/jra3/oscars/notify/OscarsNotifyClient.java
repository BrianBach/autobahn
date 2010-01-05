package net.geant2.jra3.oscars.notify;

import java.rmi.RemoteException;
import net.es.oscars.oscars.Event;
import net.es.oscars.oscars.EventContent;
import net.es.oscars.oscars.PathInfo;
import net.es.oscars.oscars.ResDetails;
import net.geant2.jra3.main.ProxyServlet;
import net.geant2.jra3.proxy.ReservationInfo;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.*;
import org.oasis_open.docs.wsn.br_2.*;
import org.w3.www._2005._08.addressing.*;


/**
 *
 * @author Michał
 */
public class OscarsNotifyClient {

    private Logger log = Logger.getLogger(this.getClass());
    private String repo = ProxyServlet.getAppPath() + "/etc/security";
    private OSCARSNotifyStub stub;

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

    public OscarsNotifyClient(String endPoint) throws AxisFault {

        ConfigurationContext context = ConfigurationContextFactory.createConfigurationContextFromFileSystem(repo, null);
        stub = new OSCARSNotifyStub(context, endPoint);
        ServiceClient service = stub._getServiceClient();
        Options opts = service.getOptions();
        opts.setTimeOutInMilliSeconds(120000);
        service.setOptions(opts);
        stub._setServiceClient(service);
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
        try {
            omEvent = event.getOMElement(Event.MY_QNAME, omFactory);
        } catch (ADBException ex) {
            System.out.println("Notify axis internals - " + ex.getMessage());
        }
        msg.addExtraElement(omEvent);

        TopicExpressionType topicExpr = new TopicExpressionType();
        topicExpr.setString("idc:INFO");
        URI topicDialectUri = null;
        try {
            topicDialectUri = new URI("http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple");
        } catch (MalformedURIException ex) { }

        topicExpr.setDialect(topicDialectUri);

        //msgHolder.setSubscriptionReference(subRef);
        msgHolder.setTopic(topicExpr);
        //msgHolder.setProducerReference(prodRef);
        msgHolder.setMessage(msg);


        Notify notification = new Notify();
        notification.addNotificationMessage(msgHolder);
        stub.Notify(notification);
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
            URI uri = new URI();
            uri.setHost("hemp.man.poznan.pl");
            topic.setDialect(uri);
            topic.setString("IDC:INFO");
            register.addTopic(topic);
            EndpointReferenceType publisherRef = new EndpointReferenceType();
            AttributedURIType address = new AttributedURIType();
            uri = new URI();
            uri.setHost("hemp.man.poznan.pl");
            address.setAnyURI(uri);
            publisherRef.setAddress(address);
            register.setPublisherReference(publisherRef);



            resp = stub.RegisterPublisher(register);
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
            URI uri = new URI();
            uri.setHost("hemp.man.poznan.pl");
            address.setAnyURI(uri);
            publisherRef.setAddress(address);

            destroy.setPublisherRegistrationReference(publisherRef);


            resp = stub.DestroyRegistration(destroy);

        } catch (Exception e) {

            log.debug("destroyRegistration: " + e.getMessage());
            throw new RemoteException(e.getMessage());
        }

        log.debug("destroyRegistration.end");
    }
}
