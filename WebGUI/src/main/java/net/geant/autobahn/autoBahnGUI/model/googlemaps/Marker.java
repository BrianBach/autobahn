package net.geant.autobahn.autoBahnGUI.model.googlemaps;

/**
 * Class models marker for the map topology 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class Marker {
	/**
	 * Default icon types paths
	 */
	public static final String DEFAULT_ICON_ACTIVE ="/autobahn-gui/images/autobahnMarker.png";
	public static final String DEFAULT_ICON_DEACTIVE ="/autobahn-gui/images/autobahnMarker-not-activ.png";
	public static final String DEFAULT_INFO = "/autobahn-gui/images/autobahnMarker-info.png";
	public static final String DEFAULT_INFO_BIG = "/autobahn-gui/images/autobahnMarker-info-big.png";
	public static final String DEFAULT_ICON_NOT_MONITORED = "/autobahn-gui/images/autobahnMarker-not-monitored.png";
	public static final String DEFAULT_ICON_INTERFACE ="/autobahn-gui/images/autobahnMarker-reservation-small.png";
	/**
     * Geographical latitude
     */
	String latitude;
	/**
     * Geographical longitude
     */
	String longitude;
	/**
     * HTML info
     */
	String html;
	/**
     * Label
     */
	String label;
	/**
     * Icon path
     */
	String icon;
	/**
     * Text
     */
	String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Marker() {
		this.latitude = "";
		this.longitude = "";
		this.html = "";
		this.label = "";
		this.icon = "";
	}
	public Marker(String latitude, String longitude, String html, String label,
			String icon) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.html = html;
		this.label = label;
		this.icon = icon;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
