package net.geant.autobahn.intradomain;

import static org.junit.Assert.*;

import net.geant.autobahn.dao.hibernate.DmHibernateUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IntradomainTopologyTest {

    @Before
    public void setUp() throws Exception {
        
        // Configure database
        DmHibernateUtil.configure("150.140.8.13","5432","autobahn_2","jra3","kma9110");
        
    }

    @After
    public void tearDown() throws Exception {
    }

    //@Test
    public void testIntradomainTopology_Eth() {
        
        
        
        String CnisAddress = "http://150.140.141.3:8080/abs/Autobahn";
        String domainID = "http://150.140.8.15:8080/autobahn/dm2idm";
        String topologyType = "ethernet";
        
        IntradomainTopology topology_test_eth = new IntradomainTopology(CnisAddress,domainID,topologyType);
        
        int numNodes = topology_test_eth.getNodes().size();
        int numSptrees = topology_test_eth.getSpanningTrees().size();
        int numGlinks = topology_test_eth.getGenericLinks().size();
        //int numPorts = topology_test_eth.getEthPhysicalPorts().size();
        
        //assertEquals(numPorts,6);
        assertEquals(numSptrees,3);
        assertEquals(numNodes,4);
        assertEquals(numGlinks,3);
    }   
    
    
    public void testIntradomainTopology_sdh() {
        
        String CnisAddress = "http://150.140.8.15/abs/Autobahn";
        String domainID = "http://150.140.8.15:8080/autobahn/dm2idm";
        String topologyType = "sdh";
        
        IntradomainTopology topology_test_sdh = new IntradomainTopology(CnisAddress,domainID,topologyType);
        
        int numNodes = topology_test_sdh.getNodes().size();
        int numPorts = topology_test_sdh.getEthPhysicalPorts().size();
        int numSdhDevices = topology_test_sdh.getSdhDevices().size();

        assertEquals(numPorts,6);
        assertEquals(numNodes,4);
        assertEquals(numSdhDevices,2);
    }
}
