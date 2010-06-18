/**
 * 
 */
package net.geant.autobahn.tool.pionier;

/**
 * @author Michal
 *
 */
public class XmrTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FoundryXMRConfigurator foundryXMRConfigurator = new FoundryXMRConfigurator();
		ReservationParameters rp = DemoReservation.GetReservationParametersSC07();
		/*
		 * creates vll
		 */
		//foundryXMRConfigurator.addReservation(rp);
		
		/*
		 * removes vll
		 */
		foundryXMRConfigurator.remReservation(rp);

	}
}
