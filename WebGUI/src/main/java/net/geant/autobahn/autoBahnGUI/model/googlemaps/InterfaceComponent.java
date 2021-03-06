package net.geant.autobahn.autoBahnGUI.model.googlemaps;

import java.util.ArrayList;
import java.util.List;

public class InterfaceComponent {

	private String name;
	private float startLatitude;
	private float startLongitude;
	private float endLatitude;
	private float endLongitude;
	private int numberOfInterfaces;
	private int state;
	private List<String> list = new ArrayList<String>();
	private String interf;

	public InterfaceComponent(String name, float startLatitude,
			float startLongitude, float endLatitude, float endLongitude,
			int numberOfInterfaces, int state, List<String> list) {
		this.name = name;
		this.startLatitude = startLatitude;
		this.startLongitude = startLongitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLongitude;
		this.numberOfInterfaces = numberOfInterfaces;
		this.state = state;
		this.list = list;
	}

	public InterfaceComponent(String name, float startLatitude,
			float startLongitude, float endLatitude, float endLongitude, String interf) {
		this.name = name;
		this.startLatitude = startLatitude;
		this.startLongitude = startLongitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLongitude;
		this.interf = interf;
	}
	
	public String getName() {
		return name;
	}

	public float getStartLatitude() {
		return startLatitude;
	}

	public float getStartLongitude() {
		return startLongitude;
	}

	public float getEndLatitude() {
		return endLatitude;
	}

	public float getEndLongitude() {
		return endLongitude;
	}

	public int getNumberOfInterfaces() {
		return numberOfInterfaces;
	}

	public int getState() {
		return state;
	}

	public List<String> getList() {
		return list;
	}

	public String getInterf() {
		return interf;
	}

}
