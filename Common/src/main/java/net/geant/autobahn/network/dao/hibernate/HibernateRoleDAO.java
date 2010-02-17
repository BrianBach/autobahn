package net.geant.autobahn.network.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Role;
import net.geant.autobahn.network.dao.RoleDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/*
 * RoleTreeDAO.java
 *
 * 2008-01-04
 */

/**
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class HibernateRoleDAO implements RoleDAO {

    public void create(Role instance) {
        getSession().save(instance);
    }

    public Role get(Node node, Link link) {
        List<Role> sts = findByCriteria(
                Expression.eq("node", node), 
                Expression.eq("link", link));

        if (sts == null || sts.size() < 1) {
            return null;
        }

        return sts.get(0);
    }

    public List<Role> getAll() {
        return findByCriteria();
    }

    public void update(Role transientObject) {
        getSession().saveOrUpdate(transientObject);
    }

    public void delete(Role persistentObject) {
        getSession().delete(persistentObject);
    }
    
    @SuppressWarnings("unchecked")
    private List<Role> findByCriteria(Criterion... criterion) {
        
        Criteria crit = getSession().createCriteria(Role.class);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        
        return new ArrayList<Role>(crit.list());
    }
    
    private Session getSession() {
        return null; //HibernateUtil.currentSession();
    }
}
