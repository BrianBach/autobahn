package net.geant.autobahn.autoBahnGUI.model;

import java.util.Comparator;

public class MapKeySetComparator implements Comparator<Object> {


		public int compare(Object o1, Object o2) {
				
			int start1 = ((String) o1).indexOf("@");
	    	int end1 = ((String) o1).length();
	    	
	    	int start2 = ((String) o2).indexOf("@");
	    	int end2 = ((String) o2).length();
	    	
	    	String str1 = ((String) o1).substring(start1, end1);
	    	String str2 = ((String) o2).substring(start2, end2);
			
			int compare = ((String) str1).compareTo(str2);

			return (-1) * compare;
		}
}
