/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Bet.Bet;
// Start of user code (user defined imports)
import Individual.Subscriber;

// End of user code

/**
 * Description of WinnerBet.
 * 
 * @author Robin, Rémy
 */
public class WinnerBet extends Bet {
	/**
	 * Description of the property entrys.
	 */
	public Entry winner = null;

	// Start of user code (user defined attributes for WinnerBet)

	// End of user code

	/**
	 * The constructor.
	 */
	public WinnerBet(long amount, Subscriber betOwner, Entry winner) {
		super(amount, betOwner);
		this.setWinner(winner);
	}

	public Entry getWinner() {
		return winner;
	}

	public void setWinner(Entry winner) {
		winner.removeBet(this);
		this.winner = winner;
		winner.addBet(this);
	}
	
	@Override
	public boolean isWon() {
		if (!this.winner.getCompetition().isSettled())
			return false;
		
		if (this.winner.getCompetition().isDraw())
			return false;
		
		return this.winner.getRank() == Rank.FIRST;
	}

}
