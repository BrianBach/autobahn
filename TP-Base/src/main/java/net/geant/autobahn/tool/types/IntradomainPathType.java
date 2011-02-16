/**
 * 
 */
package net.geant.autobahn.tool.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Jacek
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IntradomainPathType", namespace="types.tool.autobahn.geant.net", propOrder={
        "links", "constraints"
})
public class IntradomainPathType {
	private List<GenericLinkType> links = new ArrayList<GenericLinkType>();
	private List<ConstraintsType> constraints = new ArrayList<ConstraintsType>();

	/**
	 * Default constructor
	 */
	public IntradomainPathType() {
		
	}

	/**
	 * @return the links
	 */
	public List<GenericLinkType> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<GenericLinkType> links) {
		this.links = links;
	}

	/**
	 * @return the constraints
	 */
	public List<ConstraintsType> getConstraints() {
		return constraints;
	}

	/**
	 * @param constraints the constraints to set
	 */
	public void setConstraints(List<ConstraintsType> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * 
	 * @return
	 */
	public ConstraintsType getIngressConstraints() {
		if(constraints.size() < 1)
			return null;
		
		return constraints.get(0);
	}

	/**
	 * 
	 * @return
	 */
	public ConstraintsType getEgressConstraints() {
		if(constraints.size() < 1)
			return null;

		return constraints.get(constraints.size() - 1);
	}

	/**
	 * 
	 * @param glink
	 * @return
	 */
	public ConstraintsType getConstraints(GenericLinkType glink) {
		int index = links.indexOf(glink);
		if(index < 0)
			return null;
		
		return constraints.get(index);
	}
}
