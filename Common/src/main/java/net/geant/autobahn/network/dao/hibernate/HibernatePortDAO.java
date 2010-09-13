/**
 * HibernatePortDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.network.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.StateOper;
import net.geant.autobahn.network.dao.PortDAO;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernatePortDAO extends HibernateGenericDAO<Port, String> implements
        PortDAO {

    public HibernatePortDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	public Port getByBodID(String bodID) {
        List<Port> ports = findByCriteria(Expression.eq("bodID", bodID));
        
        if(ports == null || ports.size() < 1) {
            return null;
        }
        
        return ports.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Port> getClientPorts() {
        
        Query q = getSession().createQuery("select l.endPort from Link l where " +
       		"l.operationalState=:oper_state AND " +
            "l.endPort.node.provisioningDomain.adminDomain.clientDomain=true");

        q.setEntity("oper_state", StateOper.UP);
        
        return q.list();
    }

    @SuppressWarnings("unchecked")
	public List<Port> getDomainClientPorts(String domainID) {
    	
        Query q = getSession().createQuery("select l.endPort from Link l where " +
        		"l.operationalState=:oper_state AND " +
        		"l.endPort.node.provisioningDomain.adminDomain.clientDomain=true AND " +
        		"l.startPort.node.provisioningDomain.adminDomain.bodID=:sdomain");
        
        q.setEntity("oper_state", StateOper.UP);
        q.setString("sdomain", domainID);
        
        return q.list();
	}
    
    @SuppressWarnings("unchecked")
    public List<Port> getIdcpPorts() {
        
        Query q = getSession().createQuery("select l.endPort from Link l where " +
            "l.operationalState=:oper_state AND " +
            "l.endPort.node.provisioningDomain.adminDomain.idcpServer is not null AND " +
            "l.endPort.node.provisioningDomain.adminDomain.idcpServer is not empty");

        q.setEntity("oper_state", StateOper.UP);
        
        return q.list();
    }

}
