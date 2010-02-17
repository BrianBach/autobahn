package net.geant.autobahn.intradomain.sdh.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.sdh.StmLink;
import net.geant.autobahn.intradomain.sdh.dao.StmLinkDAO;

public class HibernateStmLinkDAO extends HibernateGenericDAO<StmLink, Long>
		implements StmLinkDAO {

	public HibernateStmLinkDAO(HibernateUtil hbm) {
		super(hbm);
	}

}
