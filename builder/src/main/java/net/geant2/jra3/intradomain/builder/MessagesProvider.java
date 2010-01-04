package net.geant2.jra3.intradomain.builder;
import java.util.ResourceBundle;

/**
 * i18n Message provider adapter for ResourceBundle 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class MessagesProvider {
	/**
	 * Messages file prefix
	 */
	private static String bundlePath = "messages";
	/**
	 * Resource bundle
	 */
	private  ResourceBundle bundle;;
	/**
	 * Use in singelton mode
	 */
	private static MessagesProvider messageProvider;
	/**
	 * Creates MessageProvider object
	 */
	public MessagesProvider() {
		bundle = ResourceBundle.getBundle(bundlePath);
	}
	/**
	 * Gets singelton instance of MessageProvider
	 * @return MessageProvider object
	 */
	public static MessagesProvider getInstance(){
		if (messageProvider==null)
			messageProvider= new MessagesProvider();
		return messageProvider;
	}
	/**
	 * Get message with provided name
	 * @param name name of the message
	 * @return valid message or "" if not found
	 */
	public static String getMessage(String name){
		if (getInstance().getBundle()==null)
			return "";
		return getInstance().getBundle().getString(name);
	}
	/**
	 * Gets prefix to bundle file location
	 * @return prefix to bundles file location
	 */
	public static String getBundlePath() {
		return bundlePath;
	}
	/**
	 * Gets prefix to bundle file location
	 * @return prefix to bundles file location
	 */
	public static void setBundlePath(String bundlePath) {
		MessagesProvider.bundlePath = bundlePath;
	}
	protected ResourceBundle getBundle() {
		return bundle;
	}
	protected void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
}

