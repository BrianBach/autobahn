package net.geant.autobahn.reservation;

import net.geant.autobahn.administration.Translator;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Persists ServiceHistory and Service objects in the database
 * 
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 */
public class ServiceWrapper {
	
	private static final Logger log = Logger.getLogger(ServiceWrapper.class);

	public static void create(Service service, IdmDAOFactory daos) {
		HibernateUtil hbm = IdmHibernateUtil.getInstance();
		Transaction t = hbm.beginTransaction();
		
		log.debug("Reservation port (starting):"+service.getReservations().get(0).getStartPort());
		log.debug("Reservation port (ending):"+service.getReservations().get(0).getEndPort());
        daos.getServiceDAO().create(service);
        daos.getServiceHistoryDAO().create(Translator.convertHistory(service));
        
        t.commit();
        hbm.closeSession();
	}
}
