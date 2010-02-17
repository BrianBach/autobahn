package net.geant.autobahn.intradomain.sdh.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.dao.SdhDeviceDAO;

public class HibernateSdhDeviceDAO extends HibernateGenericDAO<SdhDevice, Long>
		implements SdhDeviceDAO {

	public HibernateSdhDeviceDAO(HibernateUtil hbm) {
		super(hbm);
	}

}
