package net.geant.autobahn.idcp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import org.apache.log4j.Logger;

public class SubscriptionManager
{
  	private static final Logger log = Logger.getLogger(SubscriptionManager.class);

	/**
	 * Used to schedule subscription and renewal requests.
	 */
	private final ScheduledExecutorService executor =
		Executors.newSingleThreadScheduledExecutor();

	/**
	 * Subscription or renewal tasks for all subscriptions.
	 *
	 * Since subscriptions at any time have either a subscription or
	 * renewal task, the key set of this map contain all subscriptions
	 * under the control of this SubscriptionManager.
	 */
	private final Map<SubscriptionInfo,Future<?>> tasks =
		new HashMap<SubscriptionInfo,Future<?>>();

	private long renewPeriod = MINUTES.toMillis(50);
	private long retryPeriod = MINUTES.toMillis(1);

	/**
	 * Lifetime to request for subscriptions.
	 */
	public synchronized long getRenewalPeriod()
	{
		return renewPeriod;
	}

	public synchronized void setRenewalPeriod(long period)
	{
		this.renewPeriod = period;
	}

	/**
	 * Period between subscription retries if subscription submission
	 * fails.
	 */
	public synchronized long getRetryPeriod()
	{
		return retryPeriod;
	}

	public synchronized void setRetryPeriod(long period)
	{
		this.retryPeriod = period;
	}

	/**
	 * Subscribe to IDCP notifications.
	 */
	public synchronized SubscriptionInfo
		subscribe(String notifierUrl, String producerUrl, String consumerUrl)
	{
		checkRunning();

		/* This assumes that this SubscriptionManager is the only
		 * component issuing subscriptions requests to the particular
		 * notication broker or IDC.
		 */
		if (isFirstSubscriptionFrom(notifierUrl)) {
			unsubscribeAll(notifierUrl);
		}

		SubscriptionInfo subscription =
			new SubscriptionInfo(consumerUrl, notifierUrl, producerUrl,
								 Idcp.generateSubscriptionId(),
								 null, Idcp.TOPIC_IDC);

		scheduleSubscription(subscription, 0);

        return subscription;
	}

	/**
	 * Unsubscribe from IDCP notifications.
	 */
	public synchronized void
		unsubscribe(SubscriptionInfo subscription) throws IdcpException
	{
		checkRunning();

		cancelTask(subscription);

		IdcpNotifyClient client = createClient(subscription);
		client.unsubscribe(subscription);
	}

	/**
	 * Unsubscribe from all IDCP notifications previously subscribed to.
	 */
	public synchronized void unsubscribeAll()
	{
		checkRunning();

		Collection<SubscriptionInfo> toUnsubscribe = getSubscriptions();
		for (SubscriptionInfo subscription: toUnsubscribe) {
            try {
      			unsubscribe(subscription);
            } catch (IdcpException e) {
    			log.info("Unsubscribe failed for " +
						 subscription + ": " + e.getMessage());
            }
		}
	}

	public synchronized Collection<SubscriptionInfo> getSubscriptions()
	{
		return new ArrayList(tasks.keySet());
	}

	/**
	 * Unsubscribe from all IDCP notifications and release the
	 * executor.
	 */
	public synchronized void shutdown()
	{
		checkRunning();
		unsubscribeAll();
		executor.shutdown();
	}

	/**
	 * True if shutdown has been called.
	 */
	public boolean isShutdown()
	{
		return executor.isShutdown();
	}

	private void checkRunning()
	{
		if (isShutdown()) {
			throw new IllegalStateException("Subscription manager has been shut down");
		}
	}

	private IdcpNotifyClient createClient(SubscriptionInfo subscription)
	{
		return createClient(subscription.getNotifierUrl());
	}

 	private IdcpNotifyClient createClient(String notifierUrl)
	{
		return new IdcpNotifyClient(notifierUrl);
	}

	private void scheduleSubscription(SubscriptionInfo subscription, long delay)
	{
		scheduleTask(subscription, new SubscribeTask(subscription), delay);
	}

	private void scheduleRenewal(SubscriptionInfo subscription)
	{
		long millisToTermination =
			subscription.getTermination().getTimeInMillis() -
			System.currentTimeMillis();
		long millisToRenew = Math.max(millisToTermination / 2, 30000);

		scheduleTask(subscription, new RenewTask(subscription), millisToRenew);
	}

	private synchronized void scheduleTask(SubscriptionInfo subscription,
										   Runnable task, long delay)
	{
		tasks.put(subscription, executor.schedule(task, delay, MILLISECONDS));
	}

	private synchronized void cancelTask(SubscriptionInfo subscription)
	{
		Future<?> task = tasks.remove(subscription);
		if (task != null) {
			task.cancel(true);
		}
	}

	private boolean isFirstSubscriptionFrom(String notifierUrl)
	{
		for (SubscriptionInfo subscription: tasks.keySet()) {
			if (subscription.getNotifierUrl().equals(notifierUrl)) {
				return false;
			}
		}
		return true;
	}

	private void unsubscribeAll(String notifierUrl)
	{
		try {
			createClient(notifierUrl).unsubscribeAll(notifierUrl);
		} catch (IdcpException e) {
			log.warn("Failed to unsubscribe from " + notifierUrl + ": " +
					 e.getMessage());
		} catch (Exception e) {
			log.error("Failed to unsubscribe from " + notifierUrl, e);
		}
	}

	private void subscribe(SubscriptionInfo subscription)
	{
		try {
			subscription.setTerminationTimeIn(renewPeriod);
			createClient(subscription).subscribe(subscription);
			scheduleRenewal(subscription);
		} catch (IdcpException e) {
			log.warn("Failed to subscribe " + subscription + ": " +
					 e.getMessage());
			scheduleSubscription(subscription, getRetryPeriod());
		} catch (Exception e) {
			log.error("Failed to subscribe " + subscription, e);
			scheduleSubscription(subscription, getRetryPeriod());
		}
	}

	private void renew(SubscriptionInfo subscription)
	{
		try {
			subscription.setTerminationTimeIn(renewPeriod);
			createClient(subscription).renew(subscription);
			scheduleRenewal(subscription);
		} catch (IdcpException e) {
			log.warn("Failed to renew subscription " + subscription + ": " +
					 e.getMessage());
			scheduleSubscription(subscription, getRetryPeriod());
		} catch (Exception e) {
			log.error("Failed to renew subscription " + subscription, e);
			scheduleSubscription(subscription, getRetryPeriod());
		}
	}

	private class SubscribeTask implements Runnable
	{
		private final SubscriptionInfo subscription;

		public SubscribeTask(SubscriptionInfo subscription) {
			this.subscription = subscription;
		}

        @Override
		public void run() {
			subscribe(subscription);
		}
	}

	private class RenewTask implements Runnable
	{
		private final SubscriptionInfo subscription;

		public RenewTask(SubscriptionInfo subscription) {
			this.subscription = subscription;
		}

        @Override
		public void run() {
			renew(subscription);
		}
	}
}