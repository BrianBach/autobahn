package net.geant.autobahn.network.dao.hibernate;

import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.StatisticsEntry;
import net.geant.autobahn.network.dao.StatisticsEntryDAO;

import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class HibernateStatisticsEntryDAO extends HibernateGenericDAO<StatisticsEntry, Integer> implements
        StatisticsEntryDAO {

    public HibernateStatisticsEntryDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	public StatisticsEntry getByResId(String resId) {
        
        List<StatisticsEntry> statEntries = findByCriteria(Expression.eq("reservation_id", resId));
        
        if(statEntries == null || statEntries.size() < 1) {
            return null;
        }
        
        return statEntries.get(0);
    }

    public List<StatisticsEntry> getIntradomainEntries() {
        
        List<StatisticsEntry> statEntries = findByCriteria(Expression.eq("intradomain", true));

        if(statEntries == null || statEntries.size() < 1) {
            return null;
        }
        
        return statEntries;
    }

    public List<StatisticsEntry> getInterdomainEntries() {
        
        List<StatisticsEntry> statEntries = findByCriteria(Expression.eq("intradomain", false));

        if(statEntries == null || statEntries.size() < 1) {
            return null;
        }
        
        return statEntries;
    }

}
