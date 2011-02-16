/**
 * 
 */
package net.geant.autobahn.tool.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.ConstraintsNames;

/**
 * @author Jacek
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="Constraint", namespace="types.tool.autobahn.geant.net",
	propOrder={ "name", "value"
})
public class Constraint {

	private ConstraintsNames name;
	private String value;
	
	public Constraint() {
		
	}

	public Constraint(ConstraintsNames name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public ConstraintsNames getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(ConstraintsNames name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
