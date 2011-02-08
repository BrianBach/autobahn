package net.geant.autobahn.framework.commands;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.network.StatisticsEntry;

/**
 * @author kostas
 *
 */
public class StatisticsCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {

	    List<StatisticsEntry> selist = new ArrayList<StatisticsEntry>();
		
		if (args.length > 1) {
			if ("setuptime".equals(args[1]) || "set-uptime".equals(args[1])
			        || "setup".equals(args[1]) || "set-up".equals(args[1])) {
			    if (args.length > 2) {
			        if ("average".equals(args[2])) {
			            if (args.length > 3) {
			                if ("intra".equals(args[3])) {
	                            selist = autobahn.getDm().getStatisticsIntra();
	                            return "Average set-up time for segments in this domain: " +
	                                calculateAverage(selist) + "msec";
			                } else if ("inter".equals(args[3])) {
                                selist = autobahn.getDm().getStatisticsInter();
                                return "Average set-up time for reservations from this domain: " +
                                    calculateAverage(selist) + "msec";
			                } else {
			                    return "Only inter or intra options supported";
			                }
			            } else {
			                // statistics setuptime average
    			            selist = autobahn.getDm().getStatistics();
                            return "Average set-up time for all reservations: " +
    			                calculateAverage(selist) + "msec";
			            }
			        } else if ("intra".equals(args[2])){
                        selist = autobahn.getDm().getStatisticsIntra();
			            
			        } else if ("inter".equals(args[2])) {
                        selist = autobahn.getDm().getStatisticsInter();
			            
			        } else {
			            // resId supplied
			            StatisticsEntry se = autobahn.getDm().getStatistics(args[2]);
			            if (se == null) {
			                return "No statistical data for reservation " + args[2] + " exist";
			            }
			            return "Set-up time for reservation " + args[2] + ": " + se.getSetup_time() + "msec";
			        }
			    } else {
                    // statistics setuptime
                    selist = autobahn.getDm().getStatistics();			        
			    }
			} else {
			    return "Only setuptime option supported";
			}
		} else {
		    return "Incomplete command";
		}

		if (selist == null) {
		    return "No data found";
		}
		
        StringBuffer sb = new StringBuffer();
		sb.append("Entries found: " + selist.size() + "\n");
		for(StatisticsEntry se : selist) {
			sb.append(se.getReservation_id() + ": " + se.getSetup_time() + "msec\n");
		}
		
		return sb.toString();
	}
	
	private long calculateAverage(List<StatisticsEntry> selist) {

	    if (selist == null || selist.size() < 1) {
	        return 0;
	    }
	    
	    long sum = 0;
        for(StatisticsEntry se : selist) {
            sum += se.getSetup_time();
        }
	    return sum/selist.size();
	}

	public String commandInfo() {
		return "Displays statistics from historical data.\n" +
				"\t'statistics setuptime' to display set-up times for\n" +
				"\t    all past reservations\n" +
                "\t'statistics setuptime ResID' to display set-up time for\n" +
                "\t    the reservation with Id \"ResID\"\n" +
                "\t'statistics setuptime intra' to display set-up times for\n" +
                "\t    all reservation segments in this domain\n" +
                "\t'statistics setuptime inter' to display times needed to\n" +
                "\t    completely set-up reservations (from request to activation)\n" +
                "\t    for all requests originating from this domain\n" +
                "\t'statistics setuptime average' to display average value\n" +
                "\t    of set-up times for all reservations\n" +
                "\t'statistics setuptime average intra' to display average time\n" +
                "\t    needed to set-up the segment of a reservation in this domain\n" +
                "\t'statistics setuptime average inter' to display average time\n" +
                "\t    needed to completely set-up reservations (from request to activation) originating from this domain\n";
	}
}
