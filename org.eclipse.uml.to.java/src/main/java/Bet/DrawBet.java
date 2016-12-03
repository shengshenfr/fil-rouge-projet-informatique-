/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Bet.Bet;
// Start of user code (user defined imports)
import Individual.Subscriber;

// End of user code

/**
 * Description of DrawBet.
 * 
 * @author Robin, Rémy
 */
public class DrawBet extends Bet {
	/**
	 * Description of the property competitions.
	 */
	private Competition competition = null;

	// Start of user code (user defined attributes for DrawBet)

	// End of user code

	/**
	 * The constructor.
	 */
	public DrawBet(long amount, Subscriber betOwner, Competition competition) {
		super(amount, betOwner);
		this.competition = competition;
	}

	// Start of user code (user defined methods for DrawBet)

	// End of user code
	/**
	 * Returns competitions.
	 * @return competitions 
	 */
	public Competition getCompetition() {
		return this.competition;
	}

	/**
	 * Sets a value to attribute competitions. 
	 * @param newCompetitions
	 */
	public void setCompetition(Competition newCompetition) {
		this.competition = newCompetition;
	}

}
