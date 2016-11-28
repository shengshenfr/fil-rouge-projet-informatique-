/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.HashSet;

import Bet.Bet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of PodiumBet.
 * 
 * @author Robin
 */
public class PodiumBet extends Bet {
	/**
	 * Description of the property entrys.
	 */
	public HashSet<Entry> entrys = new HashSet<Entry>();

	// Start of user code (user defined attributes for PodiumBet)

	// End of user code

	/**
	 * The constructor.
	 */
	public PodiumBet() {
		// Start of user code constructor for PodiumBet)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for PodiumBet)

	// End of user code
	/**
	 * Returns entrys.
	 * @return entrys 
	 */
	public HashSet<Entry> getEntrys() {
		return this.entrys;
	}

}
