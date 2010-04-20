package net.geant.autobahn.useraccesspoint.callback;

public class UapCallbackImpl implements UapCallback {

	public void reservationActive(String resId) {
		System.out.println("Reservation: " + resId + " Active");
	}

	public void reservationCancelled(String resId) {
		System.out.println("Reservation: " + resId + " Cancelled");
	}

	public void reservationFinished(String resId) {
		System.out.println("Reservation: " + resId + " Finished");
	}

	public void reservationModified(String resId, boolean success) {
		System.out.println("Reservation: " + resId + " Modified: " + success);
	}

	public void reservationProcessingFailed(String resId, String cause) {
		System.out.println("Reservation: " + resId + " Failed, cause:" + cause);
	}

	public void reservationScheduled(String resId) {
		System.out.println("Reservation: " + resId + " Scheduled");
	}

    public void domainUp(String address) {
        System.out.println("Domain up: " + address);
    }
}
