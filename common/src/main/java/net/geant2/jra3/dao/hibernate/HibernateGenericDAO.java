/**
 * HibernateGenericDAO.java
 *
 * 2007-01-19
 */
package net.geant2.jra3.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import net.geant2.jra3.dao.GenericDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

/**
 * Implementation of Generic DAO. Access to the database is done by Hibernate.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HibernateGenericDAO<T, PK extends Serializable> implements
        GenericDAO<T, PK> {

    private Class<T> persistentClass;
    private HibernateUtil hibernate;
    
    @SuppressWarnings("unchecked")
	public HibernateGenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates an instance that uses given HibernateUtil instance.
     * 
     * @param hibernate HibernateUtil instance
     */
    public HibernateGenericDAO(HibernateUtil hibernate) {
    	this();
    	this.hibernate = hibernate;
    }
    
    /**
     * @return class that this DAO is bound to.
     */
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.GenericDAO#create(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public PK create(T newInstance) {
        
        return (PK) getSession().save(newInstance);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.GenericDAO#get(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        return (T) getSession().get(getPersistentClass(), id);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.GenericDAO#getAll()
     */
    public List<T> getAll() {
        return findByCriteria();
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.GenericDAO#update(java.lang.Object)
     */
    public void update(T transientObject) {
        getSession().saveOrUpdate(transientObject);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.GenericDAO#delete(java.lang.Object)
     */
    public void delete(T persistentObject) {
        getSession().delete(persistentObject);
    }

	/* (non-Javadoc)
	 * @see net.geant2.jra3.dao.GenericDAO#deleteAll()
	 */
	public void deleteAll() {
		getSession().createQuery(
				"delete from " + persistentClass.getCanonicalName())
				.executeUpdate();
	}
    
	/**
	 * Finds objects matching given criterias.
	 * 
	 * @param criterion Collection of criterias
	 * @return List of objects
	 */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
        
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        
        return new ArrayList<T>(crit.list());
    }
    
    /**
     * @return current Hibernate session for this thread.
     */
    protected Session getSession() {
        return hibernate.currentSession();
    }
}
