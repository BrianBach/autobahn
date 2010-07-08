package net.geant.autobahn.autoBahnGUI.model;

import java.util.Comparator;

import net.geant.autobahn.administration.ServiceType;

/**
 * Services comparator for sorting Services lists
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ServicesComparator  implements Comparator<ServiceType>{
	/*
	 *   (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(ServiceType o1, ServiceType o2) {
		String boid1 = o1.getBodID();
		String boid2 = o2.getBodID();
		
		int comp= boid1.compareTo(boid2);
		if (comp>0)
			return -1;
		else 
		if (comp<0)
			return 1;
		else
			return 0;
	  }
}
