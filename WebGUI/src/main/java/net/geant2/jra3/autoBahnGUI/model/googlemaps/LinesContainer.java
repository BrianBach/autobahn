package net.geant2.jra3.autoBahnGUI.model.googlemaps;

import java.util.ArrayList;
import java.util.List;

/**
 * List container for Line objects 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class LinesContainer {
	/**
	 * Added lines
	 */
	private List<Line> lines = new ArrayList<Line>();
	
	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public void add(Line line) {
	        this.lines.add(line);
	}

	public void add(LinesContainer linesContainer) {
		lines.addAll(linesContainer.getLines());	
	}

	public void removeAll() {
		lines.clear();
	}
}
