package net.geant.autobahn.autoBahnGUI.model.googlemaps;

/**
 * Class models line for the map topology
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class Line
{
	/**
	 * Color representing active link
	 */
	public static final String DEFAULT_COLOR_ACTIVE = "#00FF00";
    /**
     * Color repesenting deactive link
     */
	public static final String DEFAULT_COLOR_DEACTIVE = "#FF0000";
   
	/**
	 * Colors for representing reservation states
	 */
    public static final String reservationsStates[] = {
        "#000000", "#9EA5B3", "#9A64AC", "#ED31EB",
        "#338A83", "#4BCFC4", "#E3E27E", "#000000", "#39f906", "#000000",
         "#00FF00", "#088F15",
         "#000000","#000000","#000000","#000000","#000000","#000000","#000000","#000000","#000000",
         "#04530B", "#FFFC00","#FF0000"
    };
    /**
     * Geographical start line point latitude
     */
    private String startLattitude;
    /**
     * Geographical start line point longitude
     */
    private String startLongitude;
    /**
     * Geographical end line point latitude
     */
    private String endLattitude;
    /**
     * Geographical end line point longitude
     */
    private String endLongitude;
    /**
     * Line thickness
     */
    private String tickness;
    /**
     * Line color
     */
    private String color;
    /**
     * Indentifies the oblique level
     */
    int oblique;
    /**
     * Creates Line object
     */
    public Line(){}
    /**
     * Creates line object
     * 
     * @param startLattitude geographical start line point latitude
     * @param startLongitude geographical start line point longitude
     * @param endLattitude geographical end line point latitude
     * @param endLongitude geographical end line point longitude
     * @param tickness line thickness
     * @param color	line color
     * @param oblique line oblique level
     */
    public Line(String startLattitude, String startLongitude, String endLattitude, String endLongitude, String tickness, String color, int oblique)
    {
        this.startLattitude = startLattitude;
        this.startLongitude = startLongitude;
        this.endLattitude = endLattitude;
        this.endLongitude = endLongitude;
        this.tickness = tickness;
        this.color = color;
    }
    
    public int getOblique()
    {
        return oblique;
    }

    public void setOblique(int oblique)
    {
        this.oblique = oblique;
    }



    public String getStartLattitude()
    {
        return startLattitude;
    }

    public void setStartLattitude(String startLattitude)
    {
        this.startLattitude = startLattitude;
    }

    public String getStartLongitude()
    {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude)
    {
        this.startLongitude = startLongitude;
    }

    public String getEndLattitude()
    {
        return endLattitude;
    }

    public void setEndLattitude(String endLattitude)
    {
        this.endLattitude = endLattitude;
    }

    public String getEndLongitude()
    {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude)
    {
        this.endLongitude = endLongitude;
    }

    public String getTickness()
    {
        return tickness;
    }

    public void setTickness(String tickness)
    {
        this.tickness = tickness;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public static String getReservationLineColor(int state)
    {
        if(state < 0 || state > reservationsStates.length)
            return reservationsStates[0];
        else
            return reservationsStates[state];
    }
}
