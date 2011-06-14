package net.geant.autobahn.calendar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.geant.autobahn.constraints.BooleanConstraint;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;

import org.apache.log4j.Logger;

/**
 * Resources reservation for SDH domains implementation. Timeslots for network
 * links are the reserved resource.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class SdhConstraintsReservationCalendar implements
		ConstraintsReservationCalendar {

	private final Logger log = Logger.getLogger(SdhConstraintsReservationCalendar.class);
	
	private Map<GenericLink, AdditiveCalendar> timeslotCalendars = new HashMap<GenericLink, AdditiveCalendar>();

	private ConstraintsReservationCalendar vlanCalendar = new EthConstraintsReservationCalendar();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.geant.autobahn.intradomain.calendar.ConstraintsReservationCalendar#checkConstraints(net.geant.autobahn.intradomain.pathfinder.IntradomainPath,
	 *      java.util.Calendar, java.util.Calendar)
	 */
	public IntradomainPath getConstraints(IntradomainPath ipath,
			Calendar startTime, Calendar endTime) {
		
		IntradomainPath spath = vlanCalendar.getConstraints(ipath.getSimplifiedPath(), startTime, endTime);
		
		if(spath == null) {
			return null;
		}
		
		IntradomainPath res = new IntradomainPath();
		
		for(GenericLink glink : ipath.getLinks()) {
			AdditiveCalendar calendar = timeslotCalendars.get(glink);

			PathConstraints pcon = ipath.getConstraints(glink);
			
			MinValueConstraint timeslots = pcon.getMinValueConstraint(
					ConstraintsNames.TIMESLOTS);

			System.out.println("Constraint for link: " + glink + " " + timeslots);
			
			if(calendar == null) {
				res.addGenericLink(glink, pcon);
				continue;
			}
			
			double total = timeslots.getValue();
			double usage = calendar.getMaxUsage(startTime, endTime);
			
			if(total - usage > 0) {
				MinValueConstraint av_timeslots = new MinValueConstraint(total - usage);
				
				PathConstraints resPCon = new PathConstraints();
				resPCon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, av_timeslots);
				
				res.addGenericLink(glink, resPCon);
			} else {
				log.info("Usage too big (" + total + ") (" + usage + " for link " + glink);
				return null;
			}
		}

		// Update the result with VLANS information
		for(GenericLink gl : spath.getLinks()) {
			PathConstraints pcon = res.getConstraints(gl);

			RangeConstraint vlans = spath.getConstraints(gl).getRangeConstraint(ConstraintsNames.VLANS);
			if(vlans != null) {
				pcon.addRangeConstraint(ConstraintsNames.VLANS, vlans);
			}
			
			BooleanConstraint trans = spath.getConstraints(gl).getBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION);
			if(trans != null) {
				pcon.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, trans);
			}
		}
		
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.geant.autobahn.intradomain.calendar.ConstraintsReservationCalendar#releaseResources(java.util.List,
	 *      net.geant.autobahn.constraints.PathConstraints, java.util.Calendar,
	 *      java.util.Calendar)
	 */
	public void releaseResources(IntradomainPath path, Calendar start, Calendar end) {
		
		vlanCalendar.releaseResources(path.getSimplifiedPath(), start, end);
		
		for(GenericLink gl : path.getLinks()) {
			PathConstraints pcon = path.getConstraints(gl);
			MinValueConstraint timeslots = pcon.getMinValueConstraint(ConstraintsNames.TIMESLOTS);
			
			if(timeslots == null) {
				log.warn("SDH domain without TIMESLOT constraint!");
				return;
			}
			
			long count = Math.round(timeslots.getValue());

			AdditiveCalendar calendar = timeslotCalendars.get(gl);
			
			if(calendar == null)
				continue;

			calendar.removeReservation(count, start, end);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.geant.autobahn.intradomain.calendar.ConstraintsReservationCalendar#reserveResources(java.util.List,
	 *      net.geant.autobahn.constraints.PathConstraints, java.util.Calendar,
	 *      java.util.Calendar)
	 */
	public void reserveResources(IntradomainPath path, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {
		
		vlanCalendar.reserveResources(path.getSimplifiedPath(), start, end);
		
		for(GenericLink gl : path.getLinks()) {
			PathConstraints pcon = path.getConstraints(gl);
			MinValueConstraint timeslots = pcon.getMinValueConstraint(ConstraintsNames.TIMESLOTS);
		
			if(timeslots == null) {
				log.warn("SDH domain without TIMESLOT constraint!");
				return;
			}
			
			long count = Math.round(timeslots.getValue());
			
			AdditiveCalendar calendar = timeslotCalendars.get(gl);
				
			if(calendar == null) {
				calendar = new AdditiveCalendar();
				timeslotCalendars.put(gl, calendar);
			}
	
			log.info("Adding: " + count + " TIMESLOTS to calendar");
			
			calendar.addReservation(count, start, end);
		}
	}

}
