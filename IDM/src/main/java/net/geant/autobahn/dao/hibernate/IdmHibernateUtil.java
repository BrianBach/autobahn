package net.geant.autobahn.dao.hibernate;

import org.hibernate.Transaction;

import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.LinkType;
import net.geant.autobahn.network.StateAdmin;
import net.geant.autobahn.network.StateOper;

public class IdmHibernateUtil extends HibernateUtil {

	private static IdmHibernateUtil instance = null;
	
	private IdmHibernateUtil() {
	}
	
	public static void configure(String host, String port, String dbName,
			String user, String pass) {
	
		if(instance == null) {
			instance = new IdmHibernateUtil();
			instance.configure("hibernate-idm.cfg.xml", host, port, dbName, user, pass);
		}
	}
	
	public static IdmHibernateUtil getInstance() {
		if(instance == null)
			throw new IllegalStateException("SessionFactory not initialized - call configure");
		
		return instance;
	}

	@Override
	protected String getInfo() {
		return "IDM";
	}
	
}
