package net.geant2.jra3.intradomain.builder;

/**
 * Exception thrown if some problem occure during storing file
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class CannotStoreFileException extends Exception {

	public CannotStoreFileException() {
		super();
	}

	public CannotStoreFileException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CannotStoreFileException(String message) {
		super(message);
		
	}

	public CannotStoreFileException(Throwable cause) {
		super(cause);
		
	}

}
