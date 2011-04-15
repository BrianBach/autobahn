package net.geant.autobahn.idm;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.geant.autobahn.reservation.ReservationStatusListener;

import org.apache.log4j.Logger;

/**
 * Helper class used to send mails to user with reservation progress information
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 */

public class MailSender implements ReservationStatusListener {

    private static Logger log = Logger.getLogger(MailSender.class);
    
    private Properties props = null;
    private boolean useMail;
    private String from;
    private String user;
    private String pass;
    private String host;
    private String port;
    
    private String recipient;
    
    public MailSender(String recipient) {
        this.recipient = recipient;
        initialize();
    }
    
    public MailSender(Properties mailProps, String recipient) {
        this.recipient = recipient;
        initialize(mailProps);
    }
    
    /**
     * Initializes <code>MailSender</code>
     *
     */
    public void initialize() {
        this.useMail = Boolean.parseBoolean(AccessPoint.getInstance().getProperty("mail.use"));
        this.from = AccessPoint.getInstance().getProperty("mail.address.from");
        this.user = AccessPoint.getInstance().getProperty("mail.user");
        this.pass = AccessPoint.getInstance().getProperty("mail.pass");
        this.host = AccessPoint.getInstance().getProperty("mail.smtp.host");
        this.port = AccessPoint.getInstance().getProperty("mail.smtp.port");

        props = new Properties();
        
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.port", port);
        //props.put("mail.mime.charset", "ISO-8859-2");
    }
    
    /**
     * Initializes <code>MailSender</code>
     *
     */
    public void initialize(Properties mailProps) {
        this.useMail = Boolean.parseBoolean(mailProps.getProperty("mail.use"));
        this.from = mailProps.getProperty("mail.address.from");
        this.user = mailProps.getProperty("mail.user");
        this.pass = mailProps.getProperty("mail.pass");
        this.host = mailProps.getProperty("mail.smtp.host");
        this.port = mailProps.getProperty("mail.smtp.port");

        props = new Properties();
        
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.port", port);
        //props.put("mail.mime.charset", "ISO-8859-2");
    }
    
    /**
     * Send email
     * @param recipient to whom email will be sent to
     * @param subject subject of the email
     * @param body body of the email
     */
    private void send(String subject, String body) {

        if(!useMail) {
            log.debug("Sending mail disabled");
            return;
        }

        Session session = Session.getDefaultInstance(props);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setText(body);
            
            Transport transport = session.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            
        } catch (MessagingException e) {
            log.error("Error sending mail: " + e.getMessage());
            log.debug("Error info: ", e);
        }
    }
    
    @Override
    public void reservationScheduled(String reservationId) {
        String subject = "[AutoBAHN domain " + AccessPoint.getInstance().getLocalDomain() + 
            "] Reservation " + reservationId + " is scheduled";
        String body = "AutoBAHN notification:\n" +
            "Reservation " + reservationId + " is scheduled";
        send(subject, body);
    }

    @Override
    public void reservationActive(String reservationId) {
        String subject = "[AutoBAHN domain " + AccessPoint.getInstance().getLocalDomain() + 
            "] Reservation " + reservationId + " is active";
        String body = "AutoBAHN notification:\n" +
            "Reservation " + reservationId + " is active";
        send(subject, body);
    }

    @Override
    public void reservationFinished(String reservationId) {
        String subject = "[AutoBAHN domain " + AccessPoint.getInstance().getLocalDomain() + 
            "] Reservation " + reservationId + " has finished";
        String body = "AutoBAHN notification:\n" +
            "Reservation " + reservationId + " has finished";
        send(subject, body);
    }

    @Override
    public void reservationProcessingFailed(String reservationId, String cause) {
        String subject = "[AutoBAHN domain " + AccessPoint.getInstance().getLocalDomain() + 
            "] Reservation " + reservationId + " failed";
        String body = "AutoBAHN notification:\n" +
            "Reservation " + reservationId + " failed.\n" +
            "Cause is: " + cause;
        send(subject, body);
    }

    @Override
    public void reservationCancelled(String reservationId) {
        String subject = "[AutoBAHN domain " + AccessPoint.getInstance().getLocalDomain() + 
            "] Reservation " + reservationId + " was cancelled";
        String body = "AutoBAHN notification:\n" +
            "Reservation " + reservationId + " was cancelled";
        send(subject, body);
    }

    @Override
    public void reservationModified(String reservationId, boolean success) {
        
    }

    public static void sendMail(String recipient, String subject, String body) {
        new MailSender(recipient).send(subject, body);
    }

    public static void sendMail(Properties mailProps, String recipient, String subject, String body) {
        new MailSender(mailProps, recipient).send(subject, body);
    }

    public static void main(String args[]) {
        // Send a sample mail
        String recipient = "stamos@cti.gr";
        String subject = "Test Autobahn";
        String body = "Test body";
        Properties myProps = new Properties();
        myProps.setProperty("mail.use", "true");
        myProps.setProperty("mail.address.from", "stamos@cti.gr");
        myProps.setProperty("mail.user", "stamos");
        myProps.setProperty("mail.pass", "xxxxxx");
        myProps.setProperty("mail.smtp.host", "hermes.cti.gr");
        myProps.setProperty("mail.smtp.port", "25");
        sendMail(myProps, recipient, subject, body);
    }
}