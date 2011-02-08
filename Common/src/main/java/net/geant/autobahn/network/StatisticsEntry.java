package net.geant.autobahn.network;

/**
 * A StatisticsEntry class keeps some statistical information.
 * - setup time for a call from DM to the TP (intradomain)
 * - total time from request submission to activation (interdomain)
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 * 
 */
public class StatisticsEntry {

    private int id;
    private String reservation_id;
    private boolean intradomain;
    private long setup_time;
    
    /**
     * Default constructor
     */
    public StatisticsEntry() {
        super();
    }

    /**
     * @param reservation_id
     * @param intradomain
     * @param setup_time
     */
    public StatisticsEntry(String reservation_id, boolean intradomain, long setup_time) {
        this();
        this.reservation_id = reservation_id;
        this.intradomain = intradomain;
        this.setup_time = setup_time;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the reservation_id
     */
    public String getReservation_id() {
        return reservation_id;
    }
    
    /**
     * @param reservation_id the reservation_id to set
     */
    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }
    
    /**
     * @return the intradomain
     */
    public boolean isIntradomain() {
        return intradomain;
    }
    
    /**
     * @param intradomain the intradomain to set
     */
    public void setIntradomain(boolean intradomain) {
        this.intradomain = intradomain;
    }
    
    /**
     * @return the setup_time
     */
    public long getSetup_time() {
        return setup_time;
    }
    
    /**
     * @param setup_time the setup_time to set
     */
    public void setSetup_time(long setup_time) {
        this.setup_time = setup_time;
    }
}
