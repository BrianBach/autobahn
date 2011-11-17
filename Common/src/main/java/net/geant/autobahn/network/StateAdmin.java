package net.geant.autobahn.network;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Describes administrative status values of a link.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StateAdmin", namespace="network.autobahn.geant.net", propOrder={
		"id", "desc"
})
public class StateAdmin implements Serializable {
	
	private int id = 0;
	private String desc = null;
	
	public static final StateAdmin UNKNOWN = new StateAdmin(1, "UNKNOWN");
	public static final StateAdmin NORMAL_OPERATION = new StateAdmin(2, "NORMAL_OPERATION");
	public static final StateAdmin MAINTENANCE = new StateAdmin(3, "MAINTENANCE");
	public static final StateAdmin TROUBLESHOOTING = new StateAdmin(4, "TROUBLESHOOTING");
	public static final StateAdmin UNDER_REPAIR = new StateAdmin(5, "UNDER_REPAIR");
	
	public static StateAdmin[] states = { UNKNOWN, NORMAL_OPERATION,
			MAINTENANCE, TROUBLESHOOTING, UNDER_REPAIR };
	
	public StateAdmin() {
	}
	
	public StateAdmin(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return desc;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	public static StateAdmin getState(int i) {
		for(StateAdmin state : states) {
			if(state.getId() == i)
				return state;
		}
		
		return UNKNOWN;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getId();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StateAdmin other = (StateAdmin) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}
}
