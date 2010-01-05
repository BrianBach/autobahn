package net.geant2.jra3.intradomain.sdh.dao.hibernate;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.sdh.SdhDevice;
import net.geant2.jra3.intradomain.sdh.dao.SdhDeviceDAO;

public class HibernateSdhDeviceDAO extends HibernateGenericDAO<SdhDevice, Long>
		implements SdhDeviceDAO {

	public HibernateSdhDeviceDAO(HibernateUtil hbm) {
		super(hbm);
	}

}
