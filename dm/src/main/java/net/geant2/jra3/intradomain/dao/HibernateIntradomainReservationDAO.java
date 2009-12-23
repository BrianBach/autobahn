package net.geant2.jra3.intradomain.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.IntradomainReservation;

/**
 * Hibernate implementation of the DAO.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateIntradomainReservationDAO extends
		HibernateGenericDAO<IntradomainReservation, String> implements
		IntradomainReservationDAO {

	public HibernateIntradomainReservationDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.dao.IntradomainReservationDAO#loadReservations()
	 */
	public Map<String, IntradomainReservation> loadReservations() {
		List<IntradomainReservation> all = this.getAll();
		
		Map<String, IntradomainReservation> result = new HashMap<String, IntradomainReservation>();
		
		for(IntradomainReservation res : all)
			result.put(res.getReservationId(), res);
		
		return result;
	}
}
