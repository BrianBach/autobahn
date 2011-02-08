package net.geant.autobahn.network.dao;

import java.util.List;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.network.StatisticsEntry;

/**
 * DAO class used for operations on StatisticsEntry instances.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public interface StatisticsEntryDAO extends GenericDAO<StatisticsEntry, Integer> {

    public StatisticsEntry getByResId(String resId);

    public List<StatisticsEntry> getIntradomainEntries();
    
    public List<StatisticsEntry> getInterdomainEntries();
}
