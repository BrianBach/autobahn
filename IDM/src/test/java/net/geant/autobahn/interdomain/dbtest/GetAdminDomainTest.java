/**
 * 
 */
package net.geant.autobahn.interdomain.dbtest;


import junit.framework.TestCase;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImplDFSAnotherTest;
import net.geant.autobahn.interdomain.pathfinder.Topology;
import net.geant.autobahn.interdomain.pathfinder.TopologyImpl;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;

import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacek
 *
 */
public class GetAdminDomainTest {

    //
    //      [n1_1]            [n2_1]
    //          \               /
    //           \             /
    //           [n3_1]---[n3_2]
    //            /   \      |  
    //           /     \     | 
    //          /       \    |
    //         /         [n3_3]
    //        /             |
    //     [n4_1]           |
    //                      |
    //                   [n5_1]
    //                     |
    //                     |
    //                   [n5_2]---n[6_1]
    //
	
	private IdmDAOFactory daos = null;
	private Topology topo = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		IdmHibernateUtil.configure("localhost", "5432", "autobahn_1", "autobahn", "auto123");
		
		InterdomainPathfinderImplDFSAnotherTest test1 = new InterdomainPathfinderImplDFSAnotherTest();
		test1.setUp();
		
		this.topo = test1.getStraightTopology();
		
		Topology dbTopo = new TopologyImpl();
		
		for(Link l : topo.getLinks()) {
			dbTopo.insertLink(l);
		}
		
		daos = HibernateIdmDAOFactory.getInstance();
		
		System.out.println("--- End of Setup ");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("--- Tear down");
		
		IdmHibernateUtil hbm = IdmHibernateUtil.getInstance();
		
		Transaction t = hbm.beginTransaction();
		daos.getLinkDAO().deleteAll();
		daos.getPortDAO().deleteAll();
		daos.getIDMNodeDAO().deleteAll();
		daos.getProvisioningDomainDAO().deleteAll();
		daos.getAdminDomainDAO().deleteAll();
		t.commit();
		
		hbm.closeSession();
	}
	
	@Test
	public void test1() {
		
        // init neighbors
        AdminDomain admin = daos.getAdminDomainDAO().getByBodID("1");
        
        TestCase.assertNotNull(admin);
        
        Link l1 = daos.getLinkDAO().getByBodID("l1_3");
        TestCase.assertNotNull(admin);
	}

}
