/**
 * 
 */
package net.geant.autobahn.idm;

import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.dao.ServiceDAO;

/**
 * @author Michal
 */

public class Main {

	public static void main(String[] args) throws Exception {
		
/*		AccessPoint.getInstance();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("q to quit");
		while (true) {
			
			String line = br.readLine();
			if (line.equals("q") || line.equals("quit"))
				break;
			
		}
*/
		IdmHibernateUtil.configure("localhost", "5432", "jra3_1", "jra3", "geant2");
		
		ServiceDAO sdao =  HibernateIdmDAOFactory.getInstance().getServiceDAO();
		
		List<Service> services = sdao.getActiveServices();
		
		System.out.println("Found: " + services.size());
		for(Service srv : services) {
			System.out.println(srv);
		}
	
	}
}
