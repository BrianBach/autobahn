package net.geant.autobahn.autoBahnGUI.model.googlemaps;

import java.util.ArrayList;
import java.util.List;

public class MarkersContainer {
	private List<Marker> markers = new ArrayList<Marker>();


	public void add(Marker marker) {
	        this.markers.add(marker);
	}

	public List<Marker> getMarkers() {
		return markers;
	}


	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}

	public void add(MarkersContainer markersContainer) {
		markers.addAll(markersContainer.getMarkers());	
	}

	public void removeAll() {
		markers.clear();
	}
}
