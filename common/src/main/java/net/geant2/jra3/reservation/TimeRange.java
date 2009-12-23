/**
 * 
 */
package net.geant2.jra3.reservation;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Object keeping time period. Consists of start and end time.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="TimeRange", namespace="reservation.jra3.geant2.net", propOrder={
		"startTime", "endTime"
})
public class TimeRange {

	private Calendar startTime;
	private Calendar endTime;

	public TimeRange() {
		
	}
	
	/**
	 * Creates an instance representing given time period.
	 * 
	 * @param startTime Calendar start time
	 * @param endTime Calendar end time
	 */
	public TimeRange(Calendar startTime, Calendar endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * @return start time
	 */
	public Calendar getStartTime() {
		return startTime;
	}
	
	/**
	 * @param startTime Calendar start time to be set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @return end time
	 */
	public Calendar getEndTime() {
		return endTime;
	}
	
	/**
	 * @param endTime Calendar end time to be set
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
}
