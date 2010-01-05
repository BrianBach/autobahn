package net.geant2.jra3.intradomain.sdh.dao.hibernate;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.sdh.StmLink;
import net.geant2.jra3.intradomain.sdh.dao.StmLinkDAO;

public class HibernateStmLinkDAO extends HibernateGenericDAO<StmLink, Long>
		implements StmLinkDAO {

	public HibernateStmLinkDAO(HibernateUtil hbm) {
		super(hbm);
	}

}
