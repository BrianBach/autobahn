package net.geant.autobahn.administration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Simple property
 *
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="KeyValue", namespace="administration.autobahn.geant.net", propOrder={
		"key", "value"
})
public class KeyValue {
	
	private String key;
	private String value;
	
	public KeyValue() { }
	
	public KeyValue(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
