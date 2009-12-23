/*
 * SpanningTreeDAO.java
 *
 * 2007-03-29
 */
package net.geant2.jra3.intradomain.ethernet.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.intradomain.ethernet.Vlan;
import net.geant2.jra3.intradomain.ethernet.dao.SpanningTreeDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 * Hibernate implementation of the DAO class.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateSpanningTreeDAO implements SpanningTreeDAO {

	private HibernateUtil hibernate;
	
	public HibernateSpanningTreeDAO(HibernateUtil hibernate) {
		this.hibernate = hibernate;
	}
	
    public void create(SpanningTree instance) {
        getSession().save(instance);
    }

    public SpanningTree get(EthLink link, Vlan vlan) {
        List<SpanningTree> sts = findByCriteria(
                Expression.eq("ethLink", link), 
                Expression.eq("vlan", vlan));

        if (sts == null || sts.size() < 1) {
            return null;
        }

        return sts.get(0);
    }

    public List<SpanningTree> getAll() {
        return findByCriteria();
    }

    public void update(SpanningTree transientObject) {
        getSession().saveOrUpdate(transientObject);
    }

    public void delete(SpanningTree persistentObject) {
        getSession().delete(persistentObject);
    }
    
    @SuppressWarnings("unchecked")
    private List<SpanningTree> findByCriteria(Criterion... criterion) {
        
        Criteria crit = getSession().createCriteria(SpanningTree.class);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        
        return new ArrayList<SpanningTree>(crit.list());
    }
    
    private Session getSession() {
        return hibernate.currentSession();
    }

	public void deleteAll() {
		getSession()
				.createQuery("delete from " + SpanningTree.class.getCanonicalName())
				.executeUpdate();
	}
}
