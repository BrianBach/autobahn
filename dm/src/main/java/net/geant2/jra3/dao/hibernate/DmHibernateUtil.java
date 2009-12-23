package net.geant2.jra3.dao.hibernate;

/**
 * Helper class for managing hibernate sessions in the DM module. Singleton
 * implementation.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class DmHibernateUtil extends HibernateUtil {

	private static DmHibernateUtil instance = null;
	
	private DmHibernateUtil() {
	}
	
	/**
	 * Configures the factory to use given database credentials.
	 * 
     * @param host String database host
     * @param port String database port
     * @param dbName String database name
     * @param user String database user
     * @param pass String database password
	 */
	public static void configure(String host, String port, String dbName,
			String user, String pass) {
	
		if(instance == null) {
			instance = new DmHibernateUtil();
			instance.configure("hibernate-dm.cfg.xml", host, port, dbName, user, pass);
		}
	}
	
	/**
	 * Returns the only instance of the class - singleton pattern.
	 * 
	 * @return
	 */
	public static DmHibernateUtil getInstance() {
		if(instance == null)
			throw new IllegalStateException("SessionFactory not initialized - call configure");
		
		return instance;
	}

	@Override
	protected String getInfo() {
		return "DM";
	}
}
