package net.geant.autobahn.autoBahnGUI.model.googlemaps;

/**
 * Class in rsponsible 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class Topology {
	/**
	 * Lines in topology
	 */
	LinesContainer lines = new LinesContainer();
	/**
	 * Markers in topology
	 */
	MarkersContainer markers = new MarkersContainer();
	
	public LinesContainer getLines() {
		return lines;
	}
	public void setLines(LinesContainer lines) {
		this.lines = lines;
	}
	public MarkersContainer getMarkers() {
		return markers;
	}
	public void setMarkers(MarkersContainer markers) {
		this.markers = markers;
	}
	public void addLine (Line line){
		lines.add(line);
	}
	public void addMarker (Marker marker){
		markers.add(marker);
	}
	public void addTopology (Topology topology){
		lines.add(topology.getLines());
		markers.add(topology.getMarkers());
	}
	public void removeAll(){
		lines.removeAll();
		markers.removeAll();
	}
}

