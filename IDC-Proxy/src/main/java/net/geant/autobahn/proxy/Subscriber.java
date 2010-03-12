package net.geant.autobahn.proxy;
/*
import java.io.Closeable;
import java.io.IOException;
import java.rmi.RemoteException;
import net.geant.autobahn.oscars.notify.OscarsNotifyClient;
import net.geant.autobahn.oscars.OscarsClient;

/**
 * Subscribes to the NotificationBroker and sends keep a live messages
 * every 1 hour
 * @author Michał
 *//*
public class Subscriber implements Runnable, Closeable {

    OscarsNotifyClient oscarsNotify;
    volatile boolean exit;
    Thread thread;
    String endPoint = "http://test-idc.internet2.edu:8080/axis2/services/";
    int notifyFrequency;
    
    OscarsClient oscars;
    
    public Subscriber(String endPoint, int notifyFrequency) {
    
        this.notifyFrequency = notifyFrequency;
        try {
            oscarsNotify = new OscarsNotifyClient(this.endPoint + "OSCARSNotify");
            oscars = new OscarsClient(this.endPoint + "OSCARS");
            
        } catch (Exception e) {
            
            System.out.println("Subscriber creation failed - " + e.getMessage());
            return;
        }
        thread = new Thread(this);
        thread.start();
    }
    
    public void run() {

        
        // register first as a publisher
        try {
            oscarsNotify.registerSubscriber();
           
        } catch (RemoteException e) { 
         
            System.out.println("registerSubscriber - " + e.getMessage());
        }
        
        do {
            
            /*
            try {
                
                oscars.Notify();
                
            } catch (RemoteException e) {
             
                System.out.println("notify failed - " + e.getMessage());
            }
            */
            // call registerSubscriber
            /*
            try {
                Thread.sleep(notifyFrequency);
            } catch (InterruptedException e) { }
            
        } while (!exit);
    }
    
    public void close() throws IOException {
                
        exit = true;
        oscarsNotify.destroyRegistration();
    }
}
*/