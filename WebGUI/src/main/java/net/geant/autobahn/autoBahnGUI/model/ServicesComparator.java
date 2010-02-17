package net.geant.autobahn.autoBahnGUI.model;

import java.util.Comparator;

import net.geant.autobahn.reservation.Service;

/**
 * Services comparator for sorting Services lists
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ServicesComparator  implements Comparator<Service>{
	/*
	 *   (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Service o1, Service o2) {
		Service service1=  (Service)o1;
		Service service2=  (Service)o2;
		String boid1=service1.getBodID();
		String boid2=service2.getBodID();
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
