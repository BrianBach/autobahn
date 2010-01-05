package net.geant2.jra3.autoBahnGUI.manager;

public class ManagerException extends Exception {
	
	public static final int SERVICE_WITHOUT_RESERVATIONS=1;
	public static final int NO_SERVICE=2;
	public static final int UNKNOWN_MANAGER=3;
	
	public static final String name= "errors.manager";
	
	private int error;
	
	public ManagerException(int code, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.setError(code);
	}
	public ManagerException(int code,String arg0) {
		super(arg0);
		this.setError(code);
	}
	public ManagerException(int code, Throwable arg0) {
		super(arg0);
		this.setError(code);
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getError() {
		return error;
	}
	
	
}
