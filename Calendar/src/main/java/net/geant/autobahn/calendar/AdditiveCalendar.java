package net.geant.autobahn.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implementation of calendar based additive resources reservation module.
 * Allows reserving reserouces like capacity.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 */
public class AdditiveCalendar {

    /**
     * Preciseness of link reservations. Indicates minimal time unit that
     * seperates one reservation from another.
     */
    public static int PRECISENESS = Calendar.MILLISECOND;

    private SortedMap<Calendar, Long> usage = null;

    /**
     * Reserves given value in the given time period.
     * 
     * @param value Amount of a resource to be reserved 
     * @param start Start time of the period
     * @param end End of the period
     */
    public void addReservation(long value, Calendar start, Calendar end) {

    	if(usage == null)
    		usage = new TreeMap<Calendar, Long>();
    	
        // update end time
        usage.put(end, before(usage, end));

        // update start time
        usage.put(start, before(usage, start));

        // update all events between [startTime, endTime)
        SortedMap<Calendar, Long> between = usage.subMap(
                start, end);
        for (Calendar event : between.keySet()) {
            usage.put(event, usage.get(event) + value);
        }
        
    }

    /**
     * Releases given amount of resources in the given time period.
     * 
     * @param value Amount of a resource to be released 
     * @param start Start time of the period
     * @param end End of the period
     */
    public void removeReservation(long value, Calendar start, Calendar end) {
    	
        // Remove capacity reservations from calendar
        // update all events between [startTime, endTime)
		SortedMap<Calendar, Long> between = usage.subMap(start, end);

		for (Calendar event : between.keySet()) {
			usage.put(event, usage.get(event) - value);
		}

		SortedMap<Calendar, Long> temp = new TreeMap<Calendar, Long>();
		temp.putAll(usage);

		// eliminate empty events
		long last = 0;
		for (Calendar event : usage.keySet()) {
			long val = usage.get(event);
			if (val == last) {
				temp.remove(event);
			}

			last = val;
		}

		usage = temp;
    }

    /**
     * Returns maximum amount of a reserved resource in the given time.
     * 
     * @param startTime Start time of the period
     * @param endTime End of the period
     * @return maximum reserved
     */
    public long getMaxUsage(Calendar startTime, Calendar endTime) {

        // No reservation has been scheduled on the link
        if (usage == null)
            return 0;

        List<Long> usages = new ArrayList<Long>();

        // consider previous event
        usages.add(before(usage, startTime));

        usages.addAll(usage.subMap(startTime, endTime).values());

        return Collections.max(usages);
    }

    private long before(SortedMap<Calendar, Long> usage, Calendar event) {
        long before = 0;

        event.roll(PRECISENESS, true);

        SortedMap<Calendar, Long> eventsBefore = usage.headMap(event);
        if (!eventsBefore.isEmpty()) {
            before = usage.get(eventsBefore.lastKey());
        }

        event.roll(PRECISENESS, false);

        return before;
    }
    
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();

        for (Calendar event : usage.keySet()) {
           result.append(event.getTime() + " - " + usage.get(event) + "\n");
        }

        return result.toString();
    }
}
