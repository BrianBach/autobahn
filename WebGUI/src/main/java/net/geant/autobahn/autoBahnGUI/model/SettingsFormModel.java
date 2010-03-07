package net.geant.autobahn.autoBahnGUI.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.administration.KeyValue;

/**
 * Form model for showing setting of each AutoBAHN IDM
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class SettingsFormModel implements Serializable{
	
	/**
	 * List of registered JRA2 IDM
	 */
	private List<String> idms;
	/**
	 * Chosen IDM
	 */
	private String currentIdm;
	
	private Map<String, Object> prop;
	
	/**
	 * Error message if some communication error appear
	 */
	private String error;
	
	
	public List<String> getIdms() {
		return idms;
	}
	public void setIdms(List<String> idms) {
		this.idms = idms;
	}
	public String getCurrentIdm() {
		return currentIdm;
	}
	public void setCurrentIdm(String currentIdm) {
		this.currentIdm = currentIdm;
	}
	public List<KeyValue> getProperties() {
		Iterator<String> keys = prop.keySet().iterator();
		String key= null;
		String value = null;
		KeyValue v = null;
		List<KeyValue> values = new ArrayList<KeyValue>();
		while (keys.hasNext()){
			key = keys.next();
			value = (String)prop.get(key);
			if (value != null){
				v= new KeyValue();
				v.setKey(key);
				v.setValue(value);
				values.add(v);
			}
		}
		return values;
	}
	public void setProperties(List<KeyValue> properties) {
		prop = new HashMap<String, Object>();
		for (KeyValue v: properties){
			prop.put(v.getKey(), v.getValue());
		}
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setProp(Map<String, Object> prop) {
		this.prop = prop;
	}
	public Map<String, Object> getProp() {
		return prop;
	}
	
}
