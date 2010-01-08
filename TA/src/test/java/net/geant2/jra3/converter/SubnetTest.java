package net.geant2.jra3.converter;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubnetTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalArgumentException.class)
	public void testWrongDataFormat() {
		Subnet subnet = new Subnet("-- a.b.cxxx");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmpty() {
		Subnet subnet = new Subnet("");
	}
	
	@Test
	public void testIllegalMask() {
		Subnet subnet = new Subnet("192.168.1.10/33");
	}
	
	@Test
	public void testIllegalIpNumber() {
		Subnet subnet = new Subnet("10.300.1.1/24");
	}
	
	@Test
	public void testIllegalIpNumber2() {
		Subnet subnet = new Subnet("10.300.1.1.6/24");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testBasicSubnet() {
		Subnet subnet = new Subnet("192.168.1.10/24");
		
		for(int i = 10; i <= 255; i++) {
			TestCase.assertEquals("192.168.1." + i, subnet.nextValue());
		}
		
		TestCase.assertEquals(false, subnet.hasMoreValues());
		
		subnet.nextValue();
	}
}
