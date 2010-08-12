package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Comparator;

import net.geant.autobahn.administration.ServiceType;

/**
 * Services comparator for sorting Services lists
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ServicesComparator  implements Comparator<ServiceType>, Serializable{
	/*
	 *   (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(ServiceType o1, ServiceType o2) {
		String str1 = o1.getBodID();
		String str2 = o2.getBodID();
		
		return str2.compareTo(str1);
	  }
}
