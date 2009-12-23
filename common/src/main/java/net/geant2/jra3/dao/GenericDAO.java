package net.geant2.jra3.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic interface for DAO classes in the system. Example of the DAO design
 * pattern.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 * @param <T> Class to be managed by class
 * @param <PK> Class of primary keys
 */
public interface GenericDAO<T, PK extends Serializable> {

    /**
     * Saves a new instance in the database.
     * 
     * @param newInstance Instance to be saved
     * @return value assigned as the key for the instance
     */
    public PK create(T newInstance);

    /**
     * Fetches object idenitfier by the key.
     * 
     * @param id Primary key value
     * @return Object or null if no object exists for the given key
     */
    public T get(PK id);

    /**
     * Fetches all objects of the class from a database.
     * 
     * @return List of objects
     */
    public List<T> getAll();

	/**
	 * Saves the object state in the database. If the object is not present in
	 * the database a new record is created.
	 * 
	 * @param transientObject Object to save
	 */
    public void update(T transientObject);

    /**
     * Deletes given object from the database. 
     * 
     * @param persistentObject Object to be deleted
     */
    public void delete(T persistentObject);
    
    /**
     * Deletes all objects of the class from the database.
     */
    public void deleteAll();
}
