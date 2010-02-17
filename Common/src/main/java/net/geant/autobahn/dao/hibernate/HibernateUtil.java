package net.geant.autobahn.dao.hibernate;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper class for configuring and obtaining database connections via
 * Hibernate.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public abstract class HibernateUtil {

	static private Logger log = Logger.getLogger(HibernateUtil.class);
	
    protected SessionFactory sessionFactory = null;
    protected final ThreadLocal<Session> session = new ThreadLocal<Session>();
    
    /**
     * Reconfigures database connections. After calling the method, new
     * Hibernate sessions are created with these properties.
     * 
     * @param host String database host
     * @param port String database port
     * @param dbName String database name
     * @param user String database user
     * @param pass String database password
     */
    protected void configure(String filename, String host, String port, 
    		String dbName, String user, String pass) {
        try {
            
            Document doc = readConfigurationXML(filename);
            
            NodeList nodes = doc.getElementsByTagName("property");
            
            for(int i = 0; i < nodes.getLength(); i++) {
                Node node = (Node) nodes.item(i);
                NamedNodeMap attrs = node.getAttributes();
                
                String name = attrs.getNamedItem("name").getTextContent();

                if("connection.url".equals(name)) {
                    node.setTextContent("jdbc:postgresql://" + host + ":" + port + "/" + dbName);
                } else if("connection.username".equals(name)) {
                    node.setTextContent(user);
                } else if("connection.password".equals(name)) {
                    node.setTextContent(pass);
                }
            }
            
            Configuration conf = new Configuration();
            conf.setEntityResolver(new MyEntityResolver());
            conf.configure(doc);
            
            // Create the SessionFactory
            sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

	/**
     * Returns current session. If no session has been created, the method
     * creates a new one using recent configuration and binds it to current thread.
     * 
     * @return Session Hibernate session
     * @throws HibernateException
     *             when a database related problem occurs
     */
    public Session currentSession() throws HibernateException {
        if(sessionFactory == null)
            throw new IllegalStateException("Session factory not initialized, call configure"); 
        
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null || !s.isOpen()) {
            //log.debug("___NEW SESSION - " + getInfo());

            s = sessionFactory.openSession();
            session.set(s);
        }
        
        return s;
    }

    /**
     * Closes the current session
     * 
     * @throws HibernateException
     */
    public void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        session.set(null);
        if (s != null) {
            //log.debug("___CLOSE SESSION - " + getInfo());
            s.close();
        }
    }
    
    /**
     * Starts a new transaction for current session.
     * 
     * @return Transaction created transaction
     */
    public Transaction beginTransaction() {
    	return currentSession().beginTransaction();
    }
    
    /**
     * @return information about HibernateUtil
     */
    protected abstract String getInfo();
    
    private static Document readConfigurationXML(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // factory.setValidating(true);
            // factory.setNamespaceAware(true);

            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = HibernateUtil.class.getClassLoader().getResourceAsStream(fileName);
            
            builder.setEntityResolver(new MyEntityResolver());
            Document doc = builder.parse(is);
            
            return doc;
        } catch (Exception ex) {
        	log.error("Problem while parsing xml file", ex);
            ex.printStackTrace();
        }
        
        return null;
    }
}
