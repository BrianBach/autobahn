package net.geant.autobahn.constraints;


import java.util.List;

import junit.framework.TestCase;
import net.geant.autobahn.reservation.ReservationParams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GlobalConstraintsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUserConstraints() {
		PathConstraints pcon1 = new PathConstraints();
		pcon1.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("100-200"));
		DomainConstraints dcon = new DomainConstraints();
		dcon.addPathConstraints(pcon1);

		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1", dcon);
	}
	
	@Test
	public void testOrBooleanConstraints() {
		BooleanConstraint c1 = new BooleanConstraint(true, "OR");
		BooleanConstraint c2 = new BooleanConstraint(false, "OR");
		
		BooleanConstraint c3 = c1.intersect(c2);
		
		TestCase.assertTrue(c3.getValue());
	}
	
	@Test
	public void testOrBooleanConstraints2() {
		BooleanConstraint c1 = new BooleanConstraint(false, "OR");
		BooleanConstraint c2 = new BooleanConstraint(false, "OR");
		
		PathConstraints pcon = new PathConstraints();
		pcon.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, c1);
		
		PathConstraints pcon2 = new PathConstraints();
		pcon2.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, c2);
		
		PathConstraints pcon3 = new PathConstraints();
		pcon3.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, new BooleanConstraint(true, "OR"));
		
		PathConstraints res = pcon.intersect(pcon2);
		TestCase.assertFalse(res.getBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION).getValue());
		
		res = res.intersect(pcon3);
		TestCase.assertTrue(res.getBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION).getValue());
	}
	
	@Test
	public void testGlobalConstraintsNoVlanTranslation() {
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-180", "100-800"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(false, "150-160"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(false, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		TestCase.assertNotNull(res);
		
		TestCase.assertEquals(150, res.getDomainConstraints("domain1-ingress").getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
		TestCase.assertEquals(150, res.getDomainConstraints("domain2-ingress").getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
		TestCase.assertEquals(150, res.getDomainConstraints("domain3-ingress").getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
	}
	
	@Test
	public void testGlobalConstraintsNoVlanTranslationUser() {
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-180", "100-800"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(false, "150-160"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("user-egress", produceDomainConstraints(false, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		TestCase.assertNotNull(res);
		
		TestCase.assertEquals(150, res.getDomainConstraints("domain1-ingress").getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
		TestCase.assertEquals(150, res.getDomainConstraints("domain2-ingress").getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
		TestCase.assertEquals(150, res.getDomainConstraints("domain3-ingress").getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
	}
	
	@Test
	public void testGlobalConstraintsVlanTranslationOffImpossible() {
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-180"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(false, "100-260"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(false, "250-260"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(false, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		TestCase.assertNull(res);
	}
	
	@Test
	public void testGlobalConstraintsVlanTranslationOnImpossible() {
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-180"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(false, "100-260"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(true, "250-260"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(false, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		TestCase.assertNull(res);
	}
	
	@Test
	public void testGlobalConstraintsVlanTranslationOnGood1() {
		System.out.println("CCC ---");
		
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-120"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(false, "100-260"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(true, "110-130"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "125-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(false, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		List<List<PathConstraints>> paths = gcon.findPossibilities();
		
		for(List<PathConstraints> pcons : paths) {
			List<List<PathConstraints>> segs = gcon.getSegmentsWithSingleVlan(pcons);

			TestCase.assertEquals(2, segs.size());
		}
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		TestCase.assertNotNull(res);
		TestCase.assertEquals("110-110", getVlan(res.getDomainConstraints("domain2-ingress")));
		TestCase.assertEquals("125-125", getVlan(res.getDomainConstraints("domain2-egress")));
	}
	
	@Test
	public void testGlobalConstraintsVlanTranslationOnNonVlanDomain() {
		System.out.println("CCC ---");
		
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-120"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(false, "100-260"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(true, "110-130"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "125-200"));
		gcon.addDomainConstraints("domain3-ingress", produceSDHDomainConstraints(7.0));
		gcon.addDomainConstraints("domain3-egress", produceSDHDomainConstraints(7.0));
		gcon.addDomainConstraints("domain4-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain4-egress", produceDomainConstraints(false, "100-200"));

		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		List<List<PathConstraints>> paths = gcon.findPossibilities();
		
		for(List<PathConstraints> pcons : paths) {
			List<List<PathConstraints>> segs = gcon.getSegmentsWithSingleVlan(pcons);

			TestCase.assertEquals(2, segs.size());
		}
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		System.out.println(res);
		
		TestCase.assertNotNull(res);
		TestCase.assertEquals("110-110", getVlan(res.getDomainConstraints("domain2-ingress")));
		TestCase.assertEquals("125-125", getVlan(res.getDomainConstraints("domain2-egress")));
	}
	
	@Test
	public void testGlobalConstraintsVlanTranslationAllOnGood() {
		
		System.out.println("AAA ---");
		
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(true, "100-120"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(true, "100-260"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(true, "110-130"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(true, "125-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(true, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(true, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		
		TestCase.assertNotNull(res);
		TestCase.assertEquals("110-110", getVlan(res.getDomainConstraints("domain1-ingress")));
		TestCase.assertEquals("110-110", getVlan(res.getDomainConstraints("domain2-ingress")));
		TestCase.assertEquals("125-125", getVlan(res.getDomainConstraints("domain2-egress")));
		TestCase.assertEquals("125-125", getVlan(res.getDomainConstraints("domain3-egress")));
	}
	
	@Test
	public void testGlobalConstraints() {
		System.out.println("Translation on - one domain");
		
		GlobalConstraints gcon = new GlobalConstraints();
		gcon.addDomainConstraints("domain1-ingress", produceDomainConstraints(false, "100-180", "100-800"));
		gcon.addDomainConstraints("domain1-egress", produceDomainConstraints(true, "100-200"));
		gcon.addDomainConstraints("domain2-ingress", produceDomainConstraints(true, "150-160"));
		gcon.addDomainConstraints("domain2-egress", produceDomainConstraints(false, "100-200"));
		gcon.addDomainConstraints("domain3-ingress", produceDomainConstraints(false, "111-190"));
		gcon.addDomainConstraints("domain3-egress", produceDomainConstraints(false, "100-200"));
		
		ReservationParams par = new ReservationParams();
		par.setCapacity(100);
		
		List<List<PathConstraints>> paths = gcon.findPossibilities();
		TestCase.assertEquals(2, paths.size());
		
		GlobalConstraints res = gcon.calculateConstraints(par);
		TestCase.assertNotNull(res);
		
		System.out.println(res);
	}

	private static String getVlan(DomainConstraints dcon) {
		return dcon.getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).toString();
	}
	
	private static DomainConstraints produceDomainConstraints(boolean vlanTranslation, String... vlans) {
		DomainConstraints dcon = new DomainConstraints();

		for(String vlan : vlans) {
			PathConstraints pcon1 = new PathConstraints();
			pcon1.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(vlan));
			
			BooleanConstraint bcon = new BooleanConstraint(vlanTranslation, "OR");
			pcon1.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, bcon);
			
			dcon.addPathConstraints(pcon1);
		}
		
		return dcon;
	}
	
	private static DomainConstraints produceSDHDomainConstraints(Double timeslot) {
		DomainConstraints dcon = new DomainConstraints();
		
		PathConstraints pcon = new PathConstraints();
		pcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, new MinValueConstraint(timeslot));
		dcon.addPathConstraints(pcon);
		
		return dcon;
	}
}
