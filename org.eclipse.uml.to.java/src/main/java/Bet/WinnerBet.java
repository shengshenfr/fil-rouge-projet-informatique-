/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Bet.Bet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of WinnerBet.
 * 
 * @author Robin
 */
public class WinnerBet extends Bet {
	/**
	 * Description of the property entrys.
	 */
	public Entry entrys = null;

	// Start of user code (user defined attributes for WinnerBet)

	// End of user code

	/**
	 * The constructor.
	 */
	public WinnerBet() {
		// Start of user code constructor for WinnerBet)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for WinnerBet)

	// End of user code
	/**
	 * Returns entrys.
	 * @return entrys 
	 */
	public Entry getEntrys() {
		return this.entrys;
	}

	/**
	 * Sets a value to attribute entrys. 
	 * @param newEntrys 
	 */
	public void setEntrys(Entry newEntrys) {
		this.entrys = newEntrys;
	}

}
