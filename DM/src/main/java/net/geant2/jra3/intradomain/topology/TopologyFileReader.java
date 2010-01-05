package net.geant2.jra3.intradomain.topology;

import java.util.List;

import net.geant2.jra3.dao.DmDAOFactory;
import net.geant2.jra3.dao.hibernate.DmHibernateUtil;
import net.geant2.jra3.dao.hibernate.HibernateDmDAOFactory;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.dao.GenericInterfaceDAO;
import net.geant2.jra3.intradomain.common.dao.GenericLinkDAO;
import net.geant2.jra3.intradomain.common.dao.NodeDAO;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.intradomain.ethernet.dao.EthLinkDAO;
import net.geant2.jra3.intradomain.ethernet.dao.SpanningTreeDAO;
import net.geant2.jra3.intradomain.ethernet.dao.VlanDAO;

import org.hibernate.Transaction;

/**
 * Class reads the topology from the file, clear the information about old
 * topology from the database and save the new one. Class accepts XML output
 * files of Autobahn Topology Builder.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class TopologyFileReader {

	private List<SpanningTree> strees = null;

	/**
	 * Creates instance and reads file and saves new topology to the database.
	 * 
	 * @param filePath path to the file with topology
	 */
	public TopologyFileReader(String filePath) {
		if(filePath.equals("none"))
			return;
		
		readFile(filePath);
	}
	
    private void clearIntradomain() {
    	DmDAOFactory daos = HibernateDmDAOFactory.getInstance();

    	SpanningTreeDAO st_dao = daos.getSpanningTreeDAO();
    	EthLinkDAO eth_dao = daos.getEthLinkDAO();
    	GenericLinkDAO gldao = daos.getGenericLinkDAO();
    	VlanDAO vldao = daos.getVlanDAO();
    	GenericInterfaceDAO gifdao = daos.getGenericInterfaceDAO();
    	NodeDAO ndao = daos.getNodeDAO();
    	
    	st_dao.deleteAll();
    	eth_dao.deleteAll();
    	gldao.deleteAll();
    	vldao.deleteAll();
    	gifdao.deleteAll();
    	ndao.deleteAll();
    }
	
	private void readFile(String path) {
        TopologyMarshaler src = new XstreemTopologyMalshaler();
        Topology topo;
        
		try {
			topo = src.unmarshal(path);
			
	        strees = topo.getEthernetTopology().getSpanningTrees();
		} catch (WrongFileFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void saveTopology() {
		if(strees != null && strees.size() > 0) {
			
	        Transaction t = DmHibernateUtil.getInstance().beginTransaction();
	        
			clearIntradomain();
			saveSpanningTrees(strees);
			
	        t.commit();
		}
	}
	
	private void saveSpanningTrees(List<SpanningTree> strees) {
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        GenericLinkDAO gldao = daos.getGenericLinkDAO();
        EthLinkDAO eldao = daos.getEthLinkDAO();
        VlanDAO vldao = daos.getVlanDAO();
        SpanningTreeDAO stdao = daos.getSpanningTreeDAO();
        
        for(SpanningTree st : strees) {
        	GenericLink gl = st.getEthLink().getGenericLink();
        	
        	String endDomain = gl.getEndInterface().getDomainId();
        	if(endDomain != null && endDomain.contains("client-domain"))
        		gl.getEndInterface().setClientPort(true);
        	
        	gldao.create(gl);
        	eldao.create(st.getEthLink());
        	vldao.create(st.getVlan());
        	stdao.create(st);
        }
	}
}
