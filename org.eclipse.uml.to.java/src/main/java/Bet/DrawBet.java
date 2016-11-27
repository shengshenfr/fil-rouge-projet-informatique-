/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Bet.Bet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of DrawBet.
 * 
 * @author Robin
 */
public class DrawBet extends Bet {
	/**
	 * Description of the property competitions.
	 */
	public Competition competitions = null;

	// Start of user code (user defined attributes for DrawBet)

	// End of user code

	/**
	 * The constructor.
	 */
	public DrawBet() {
		// Start of user code constructor for DrawBet)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for DrawBet)

	// End of user code
	/**
	 * Returns competitions.
	 * @return competitions 
	 */
	public Competition getCompetitions() {
		return this.competitions;
	}

	/**
	 * Sets a value to attribute competitions. 
	 * @param newCompetitions 
	 */
	public void setCompetitions(Competition newCompetitions) {
		this.competitions = newCompetitions;
	}

}
