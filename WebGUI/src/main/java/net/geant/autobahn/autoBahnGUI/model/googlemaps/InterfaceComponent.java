package net.geant.autobahn.autoBahnGUI.model.googlemaps;

public class InterfaceComponent {

	private String name;
	private float startLatitude;
	private float startLongitude;
	private float endLatitude;
	private float endLongitude;
	private int numberOfInterfaces;
	private int state;

	public InterfaceComponent(String name, float startLatitude,
			float startLongitude, float endLatitude, float endLongitude,
			int numberOfInterfaces, int state) {
		this.name = name;
		this.startLatitude = startLatitude;
		this.startLongitude = startLongitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLongitude;
		this.numberOfInterfaces = numberOfInterfaces;
		this.state = state;
	}

	public InterfaceComponent(String name, float Latitude, float Longitude) {
		this.name = name;
		this.startLatitude = Latitude;
		this.startLongitude = Longitude;
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

}
