/**
 * 
 */
package net.geant.autobahn.intradomain.mpls.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.mpls.MplsLink;
import net.geant.autobahn.intradomain.mpls.dao.MplsLinkDAO;

/**
 * @author PCSS
 */
public class HibernateMplsLinkDAO extends HibernateGenericDAO<MplsLink, Long> implements MplsLinkDAO {
	
	public HibernateMplsLinkDAO(HibernateUtil hibernate) {
		
		super(hibernate);
	}
}
