/*
 * UserDAO.java
 *
 * 2006-02-16
 */
package net.geant2.jra3.reservation.dao;

import net.geant2.jra3.dao.GenericDAO;
import net.geant2.jra3.reservation.User;

/**
 * <code>UserDAO</code> interface is used to access users data.
 * Contains methods for inserting, updating, retrieving and removing users. 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface UserDAO extends GenericDAO<User, String> {
    
    public User getByName(String name);
}
