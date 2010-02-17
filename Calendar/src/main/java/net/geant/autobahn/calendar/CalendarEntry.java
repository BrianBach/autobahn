package net.geant.autobahn.calendar;

import java.util.Calendar;

/**
 * Class holding the reservation of discrete resource for a time period.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class CalendarEntry {
    private Calendar start;
    private Calendar end;
    private int vlan = 0;
    
    /**
     * Creates an instance.
     * 
     * @param start Start time
     * @param end End time
     * @param vlan identifier to be reserved
     */
    public CalendarEntry(Calendar start, Calendar end, int vlan) {
        this.start = start;
        this.end = end;
        this.vlan = vlan;
    }
    
    /**
     * 
     * @return End of the time period.
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * 
     * @return Start time of the time period
     */
    public Calendar getStart() {
        return start;
    }

    /**
     * 
     * @return Vlan number used in this calendar entry
     */
    public int getVlan() {
        return vlan;
    }

    /**
     * Checks if it overlaps with given time period.
     * 
     * @param s2 Start time
     * @param e2 End time
     * @return true if calendar entry overlaps
     */
    public boolean overlaps(Calendar s2, Calendar e2) {
        return (s2.compareTo(start) >= 0 && s2.compareTo(end) <= 0) ||
        (e2.compareTo(start) >= 0 && s2.compareTo(start) <= 0);
    }
    
    /**
     * Checks if it oerlaps with another calendar entry.
     * 
     * @param entry2 Calendar entry instance
     * @return true if calendar entry overlaps
     */
    public boolean overlaps(CalendarEntry entry2) {
        Calendar s2 = entry2.start;
        Calendar e2 = entry2.end;

        return overlaps(s2, e2);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        if(this == obj)
            return true;
        
        if(!(obj instanceof CalendarEntry))
            return false;
        
        CalendarEntry e2 = (CalendarEntry) obj;
        
        return start.equals(e2.start) && end.equals(e2.end) && vlan == e2.vlan;
    }

    @Override
    public int hashCode() {
        return start.hashCode() ^ end.hashCode() ^ vlan;
    }

    @Override
    public String toString() {
        return start.getTime() + " - " + end.getTime() + ": " + vlan;
    }
}
