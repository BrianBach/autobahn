package net.geant.autobahn.tool.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.ConstraintsNames;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="ConstraintsType", namespace="types.tool.autobahn.geant.net",
	propOrder={ "constraints"
})
public class ConstraintsType {

	private List<Constraint> constraints = new ArrayList<Constraint>();
	
	public ConstraintsType() {
		
	}

	/**
	 * @return the constraints
	 */
	public List<Constraint> getConstraints() {
		return constraints;
	}

	/**
	 * @param constraints the constraints to set
	 */
	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Constraint getConstraintForName(ConstraintsNames name) {
		for(Constraint c : constraints) {
			if(c.getName().equals(name))
				return c;
		}
		
		return null;
	}
}
