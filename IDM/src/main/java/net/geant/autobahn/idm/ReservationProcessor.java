package net.geant.autobahn.idm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.geant.autobahn.administration.Translator;
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
import net.geant.autobahn.reservation.dao.ReservationHistoryDAO;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class ReservationProcessor {

	private static final Logger log = Logger.getLogger(ReservationProcessor.class);
	
	private Map<String, BlockingQueue<AutobahnCommand>> events = new HashMap<String, BlockingQueue<AutobahnCommand>>();
	private Map<String, AutobahnReservation> reservations = new HashMap<String, AutobahnReservation>();
	private Map<String, String> previousDomains = new HashMap<String, String>();
	
	private ReservationDAO rdao = null;
	private ReservationHistoryDAO hdao = null;
	private TopologyMerge topologyMerge = new TopologyMerge();
	
	private Idm2Dm domainManager = null;
	private String domainID;
    private boolean restorationMode = false;
    
	public ReservationProcessor(String domainID, Idm2Dm domainManager) {
		this.domainManager = domainManager;
		this.domainID = domainID;
		this.rdao = HibernateIdmDAOFactory.getInstance().getReservationDAO();
		this.hdao = HibernateIdmDAOFactory.getInstance().getReservationHistoryDAO();
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

        AutobahnCommand command = new AutobahnCommand () {
            public void run() {
                rdao.update(res);
                res.run();

                hdao.update(Translator.convertHistory(res));
                
                // Delete fake reservation after processing
                if(res instanceof LastDomainReservation && res.isFake()) {
                	rdao.delete(res);
                }
            }

			@Override
			public String getInfo() {
				return "Run: " + res.getBodID();
			}
        };

        addReservation(res, command);
	}
	
	private void addReservation(AutobahnReservation res, AutobahnCommand command) {
		final String resID = res.getBodID();
		
		reservations.put(resID, res);

        BlockingQueue<AutobahnCommand> queue = new LinkedBlockingQueue<AutobahnCommand>();
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
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.recover();

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Recover: " + res.getBodID();
			}
        };
		
		addReservation(res, command);
	}
	
	public void reportSchedule(final String resID, final int msgCode,
			final String arguments, final boolean success,
			final GlobalConstraints global) {
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationScheduled(msgCode, arguments, success, global);

                hdao.update(Translator.convertHistory(res));
                
                // Delete if it's fake
                if(res.isFake())
                	rdao.delete(res);
            }

			@Override
			public String getInfo() {
				return "Report Schedule: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
    public void cancelReservation(final String resID) throws NoSuchReservationException {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.cancel();

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Cancel: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
    }
	
	public void reportCancellation(final String resID, final String message,
			final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationCancelled(message, success);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Report cancel: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void modifyReservation(final String resID, final Calendar startTime,
			final Calendar endTime) {
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
            	rdao.update(res);
                res.modify(startTime, endTime);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Modify: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void withdrawReservation(final String resID) throws NoSuchReservationException {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
            	rdao.update(res);
                res.withdraw();
            
                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Withdraw: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void reportWithdraw(final String resID, final String message,
			final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
			public void run() {
				rdao.update(res);
				res.reservationWithdrawn(message, success);

				hdao.update(Translator.convertHistory(res));
			}

			@Override
			public String getInfo() {
				return "Report withdraw: " + resID;
			}
		};

		BlockingQueue<AutobahnCommand> queue = events.get(resID);
		queue.add(new TransactionTask(command));
	}

	public void reportModify(final String resID, final Calendar startTime,
			final Calendar endTime, final String message, final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationModified(startTime, endTime, message, success);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Report modify: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void reportActive(final String resID, final String message,
			final boolean success) throws NoSuchReservationException {
		
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationActivated(message, success);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Report active: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void reportFinished(final String resID, final String message,
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
			
			AutobahnCommand command = new AutobahnCommand() {
	            public void run() {
	                rdao.update(res);
	                res.reservationFinished(message, success);

	                hdao.update(Translator.convertHistory(res));
	            }

				@Override
				public String getInfo() {
					return "Report finished: " + resID;
				}
	        };

	        BlockingQueue<AutobahnCommand> queue = events.get(resID);
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

	public void activate(final String resID, final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null) {
			log.info("Activate: Reservation " + resID + " already finished");
			return;
		}
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
        		res.activate(success);

        		hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Activate: " + resID;
			}
        };

        if(restorationMode) {
        	command.run();
        	return;
        }
        
        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void finish(final String resID, final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null) {
			log.info("Finish: Reservation " + resID + " already finished");
			return;
		}
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
            	rdao.update(res);
        		res.finish(success);
        		
        		hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Finish: " + resID;
			}
        };

        if(restorationMode) {
        	command.run();
        	return;
        }
        
        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	abstract class AutobahnCommand implements Runnable {
		public abstract String getInfo();
	}
	
	class TransactionTask extends AutobahnCommand {
		private AutobahnCommand command = null;
		
		TransactionTask(AutobahnCommand command) {
			this.command = command;
		}
		
		public void run() {
			HibernateUtil hbm = IdmHibernateUtil.getInstance();
			Transaction t = hbm.beginTransaction();
			
			log.info("Processor: command " + command.getInfo() + " start");
			command.run();
			log.info("Processor: command " + command.getInfo() + " end");
			
			if (!t.wasCommitted()) {
			    t.commit();
			}
            hbm.closeSession();
		}

		@Override
		public String getInfo() {
			return command.getInfo();
		}
	}
	
	class ReservationRunner implements Runnable, ReservationStatusListener {
		private String resID;
		private boolean end = false;
		
		public ReservationRunner(String resID) {
			this.resID = resID;
		}

		public void run() {
			BlockingQueue<AutobahnCommand> queue = events.get(resID);
			
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
	
	public void addStatusListenerToAllReservations(ReservationStatusListener listener) {
		for (AutobahnReservation res : reservations.values()) {
			if(!res.getStatusListeners().contains(listener)) {
				res.addStatusListener(listener);
			}
		}
	}
}
