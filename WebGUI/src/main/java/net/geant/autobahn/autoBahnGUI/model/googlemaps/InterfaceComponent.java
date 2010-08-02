package net.geant.autobahn.autoBahnGUI.model.googlemaps;

import java.util.List;

public class InterfaceComponent {

	private String name;
	private float startLatitude;
	private float startLongitude;
	private float endLatitude;
	private float endLongitude;
	private int numberOfInterfaces;
	private int state;
	private List<String> list;
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
			float startLongitude, float endLatitude, float endLongitude,
			int numberOfInterfaces, int state, String interf) {
		this.name = name;
		this.startLatitude = startLatitude;
		this.startLongitude = startLongitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLongitude;
		this.numberOfInterfaces = numberOfInterfaces;
		this.state = state;
		this.interf = interf;
	}

	public InterfaceComponent(String name, float Latitude, float Longitude, String interf ) {
		this.name = name;
		this.startLatitude = Latitude;
		this.startLongitude = Longitude;
		this.interf = interf;
	}
	public InterfaceComponent(){}
	
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
