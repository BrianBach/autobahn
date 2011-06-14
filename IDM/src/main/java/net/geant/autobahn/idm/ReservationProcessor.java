package net.geant.autobahn.idm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.interdomain.Interdomain;
import net.geant.autobahn.interdomain.InterdomainClient;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.ExternalReservation;
import net.geant.autobahn.reservation.LastDomainReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;
import net.geant.autobahn.reservation.dao.ReservationDAO;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class ReservationProcessor {

	private static final Logger log = Logger.getLogger(ReservationProcessor.class);
	
	private Map<String, BlockingQueue<Runnable>> events = new HashMap<String, BlockingQueue<Runnable>>();
	private Map<String, AutobahnReservation> reservations = new HashMap<String, AutobahnReservation>();
	private Map<String, String> previousDomains = new HashMap<String, String>();
	
	private ReservationDAO rdao = null;
	private TopologyMerge topologyMerge = new TopologyMerge();
	
	private Idm2Dm domainManager = null;
	private String domainID;
    private boolean restorationMode = false;

	public ReservationProcessor(String domainID, Idm2Dm domainManager) {
		this.domainManager = domainManager;
		this.domainID = domainID;
		this.rdao = HibernateIdmDAOFactory.getInstance().getReservationDAO();
	}
	
	public void scheduleReservation(Reservation src) {
		final String resID = src.getBodID();

		AutobahnReservation res = reservations.get(resID); 
		if(res == null) {
			// When first time in an external domain
			res = AutobahnReservation.createReservation(src, domainID);
		} else {
			// Another attempt - another path
            res.setPath(src.getPath());
            res.setGlobalConstraints(src.getGlobalConstraints());
            res.gotoInitialState();
		}

		// Used to merge same objects - needed by Hibernate to work
		res = topologyMerge.merge(res);
		
		// Inject resources
		res.setResourcesReservation(domainManager);
		
		runReservation(res);
	}
	
	public void runReservation(final AutobahnReservation res) {

        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.run();
                
                // Delete fake reservation after processing
                if(res instanceof LastDomainReservation && res.isFake()) {
                	rdao.delete(res);
                }
            }
        };

        addReservation(res, command);
	}
	
	private void addReservation(AutobahnReservation res, Runnable command) {
		final String resID = res.getBodID();
		
		reservations.put(resID, res);

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        events.put(resID, queue);
        
        if(command != null)
        	queue.add(new TransactionTask(command));
        
        ReservationRunner runner = new ReservationRunner(resID);
        res.addStatusListener(runner);
        
        //ThreadExecutor.getInstance().execute(runner);
        new Thread(runner).start();
	}
	
	public void recoverReservation(final AutobahnReservation res) {

		// Inject resources
		res.setResourcesReservation(domainManager);
		res.setLocalDomainID(domainID);
		
        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.recover();
            }
        };
		
		addReservation(res, command);
	}
	
	public void reportSchedule(String resID, final int msgCode,
			final String arguments, final boolean success,
			final GlobalConstraints global) {
		final AutobahnReservation res = reservations.get(resID);

        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.reservationScheduled(msgCode, arguments, success, global);
                
                // Delete if it's fake
                if(res.isFake())
                	rdao.delete(res);
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
    public void cancelReservation(String resID) throws NoSuchReservationException {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.cancel();
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
    }
	
	public void reportCancellation(String resID, final String message,
			final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.reservationCancelled(message, success);
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void modifyReservation(String resID, final Calendar startTime,
			final Calendar endTime) {
		final AutobahnReservation res = reservations.get(resID);

        Runnable command = new Runnable() {
            public void run() {
            	rdao.update(res);
                
                res.modify(startTime, endTime);
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void withdrawReservation(String resID) throws NoSuchReservationException {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
        Runnable command = new Runnable() {
            public void run() {
            	rdao.update(res);
                
                res.withdraw();
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void reportWithdraw(String resID, final String message,
			final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);

		Runnable command = new Runnable() {
			public void run() {
				rdao.update(res);

				res.reservationWithdrawn(message, success);
			}
		};

		BlockingQueue<Runnable> queue = events.get(resID);
		queue.add(new TransactionTask(command));
	}

	public void reportModify(String resID, final Calendar startTime,
			final Calendar endTime, final String message, final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);

        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.reservationModified(startTime, endTime, message, success);
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void reportActive(String resID, final String message,
			final boolean success) throws NoSuchReservationException {
		
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
                
                res.reservationActivated(message, success);
            }
        };

        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void reportFinished(String resID, final String message,
			final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);
		
		if(res != null) {
			if (res instanceof ExternalReservation) {
				// Just forward the message
	        	Interdomain client;
                try {
                    client = new InterdomainClient(res.getPrevDomainAddress());
                    client.reportFinished(resID, message, success);
                } catch (Exception e) {
                    log.error(this + " reportFinished: " + e.getMessage(), e);
                }
					
				return;
			}
			
	        Runnable command = new Runnable() {
	            public void run() {
	                rdao.update(res);
	                
	                res.reservationFinished(message, success);
	            }
	        };

	        BlockingQueue<Runnable> queue = events.get(resID);
	        queue.add(new TransactionTask(command));
		} else {
			// Means that the reservation might be already finished in this domain
			String prevDomain = previousDomains.get(resID);
			
			if(prevDomain != null) {
	        	Interdomain client;
                try {
                    client = new InterdomainClient(prevDomain);
                    client.reportFinished(resID, message, success);
                } catch (Exception e) {
                    log.error(this + " reportFinished: " + e.getMessage(), e);
                }
			} else {
				log.info(resID + " Report finish received: Reservation not found in the processor.");
			}
		}
	}

	public void activate(String resID, final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null) {
			log.info("Activate: Reservation " + resID + " already finished");
			return;
		}
		
        Runnable command = new Runnable() {
            public void run() {
                rdao.update(res);
	                
        		res.activate(success);
            }
        };

        if(restorationMode) {
        	command.run();
        	return;
        }
        
        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void finish(String resID, final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null) {
			log.info("Finish: Reservation " + resID + " already finished");
			return;
		}
		
        Runnable command = new Runnable() {
            public void run() {
            	rdao.update(res);
                
        		res.finish(success);
            }
        };

        if(restorationMode) {
        	command.run();
        	return;
        }
        
        BlockingQueue<Runnable> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	class TransactionTask implements Runnable {
		private Runnable command = null;
		
		TransactionTask(Runnable command) {
			this.command = command;
		}
		
		public void run() {
			HibernateUtil hbm = IdmHibernateUtil.getInstance();
			Transaction t = hbm.beginTransaction();
			
			command.run();
			
			if (!t.wasCommitted()) {
			    t.commit();
			}
            hbm.closeSession();
		}
	}
	
	class ReservationRunner implements Runnable, ReservationStatusListener {
		private String resID;
		private boolean end = false;
		
		public ReservationRunner(String resID) {
			this.resID = resID;
		}

		public void run() {
			BlockingQueue<Runnable> queue = events.get(resID);
			
			Runnable command = null;
			try {
				while(!end && (command = queue.take()) != null) {
					command.run();
				}
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}

		public void reservationCancelled(String reservationId) {
			end = true;
			reservations.remove(reservationId);
		}

		public void reservationFinished(String reservationId) {
			end = true;
			AutobahnReservation res = reservations.remove(reservationId);
			
			if(res instanceof ExternalReservation)
				previousDomains.put(reservationId, res.getPrevDomainAddress());
		}

		public void reservationProcessingFailed(String reservationId,
				String cause) {
			end = true;
			reservations.remove(reservationId);
		}

		public void reservationScheduled(String reservationId) {
			// Do nothing
		}
		
		public void reservationActive(String reservationId) {
			// Do nothing
		}

		public void reservationModified(String reservationId, boolean success) {
			// Do nothing
		}
	}

	public void setRestorationMode(boolean mode) {
		this.restorationMode = mode;
	}
}
