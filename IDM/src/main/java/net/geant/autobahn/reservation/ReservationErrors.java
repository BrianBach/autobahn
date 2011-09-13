/*
 * Errors.java
 *
 * 2006-12-19
 */
package net.geant.autobahn.reservation;

import java.util.Map;
import java.util.TreeMap;

import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.dao.LinkDAO;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class ReservationErrors {

    public static final int OK = 0;

    // domain errors
    public static final int WRONG_DOMAIN = 1;
    public static final int COMMUNICATION_ERROR = 2;
    public static final int LOCAL_COMMUNICATION_ERROR = 3;
    public static final int NEGATIVE_DELAY = 4;
    public static final int DOMAIN_RESERVATION_ERROR = 5;
    public static final int RESERVATION_NOTSUPPORTED = 6;
    
    // constraints errors
    public static final int CONSTRAINTS_NOT_CORRECT = 11;
    public static final int CONSTRAINTS_NOT_AGREED = 12;
    public static final int CONSTRAINTS_ALREADY_IN_USE = 13;

    // path errors
    public static final int PATH_CAPACITY_NOT_ENOUGH = 15;
    
    // link errors
    public static final int NOT_ENOUGH_CAPACITY = 21;

    private static Map<Integer, String> infos;

    static {
        infos = new TreeMap<Integer, String>();

        infos.put(OK, "<--OK-->");
        infos.put(WRONG_DOMAIN, "Wrong domain, path can not be established from domain");
        infos.put(DOMAIN_RESERVATION_ERROR, "Problem with reserving resources");
        infos.put(COMMUNICATION_ERROR, "Domain not responding");
        infos.put(LOCAL_COMMUNICATION_ERROR, "DM not responding in domain");
        infos.put(NEGATIVE_DELAY, "Attempt to make a reservation in the past");
        infos.put(CONSTRAINTS_NOT_CORRECT, "Constraints not correct on path");
        infos.put(NOT_ENOUGH_CAPACITY, "Not enough resources in the specified time period on link");
        infos.put(PATH_CAPACITY_NOT_ENOUGH, "Path can't guarantee enough capacity");
        infos.put(CONSTRAINTS_NOT_AGREED, "Global constraints not fulfilled on path");
        infos.put(CONSTRAINTS_ALREADY_IN_USE, "Calculated constraints are already in use");
        infos.put(RESERVATION_NOTSUPPORTED, "Reservations idcp->autobahn are not supported");
    }

    /**
     * Prepares error message from its code and arguments
     * 
     * @param code
     *            int error code
     * @param args
     *            String extra arguments
     * @return String error message
     */
    public static String getInfo(int code, String args) {
    	String tail = "";
    	
    	if(args != null && !"".equals(args))
    		tail = " <" + args + ">";
    		
    	
        return infos.get(code) + tail;
    }

    /**
     * Returns a link that caused the error. If the error was not caused by a
     * link, IllegalArgumentException is thrown.
     * 
     * @param code
     * @param args
     * @return
     */
    public static Link getLink(int code, String args) {
        if (code < 20 || code >= 30) {
        	// wrong error code - not link related
            return null;
        }

        LinkDAO ldao = HibernateIdmDAOFactory.getInstance().getLinkDAO();

        return ldao.getByBodID(args);
    }
}
