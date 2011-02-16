package net.geant.autobahn.tool.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="GenericLinkType", namespace="types.tool.autobahn.geant.net", propOrder={
        "startInterface", "endInterface", "direction"
})
public class GenericLinkType {
	private GenericInterfaceType startInterface;
	private GenericInterfaceType endInterface;
	private String direction;
	
	public GenericLinkType() {
		
	}

	/**
	 * @return the startInterface
	 */
	public GenericInterfaceType getStartInterface() {
		return startInterface;
	}

	/**
	 * @param startInterface the startInterface to set
	 */
	public void setStartInterface(GenericInterfaceType startInterface) {
		this.startInterface = startInterface;
	}

	/**
	 * @return the endInterface
	 */
	public GenericInterfaceType getEndInterface() {
		return endInterface;
	}

	/**
	 * @param endInterface the endInterface to set
	 */
	public void setEndInterface(GenericInterfaceType endInterface) {
		this.endInterface = endInterface;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
