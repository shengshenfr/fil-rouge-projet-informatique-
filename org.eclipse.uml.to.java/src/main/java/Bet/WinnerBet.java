/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;

import Bet.Bet;
import Individual.Subscriber;
import dbManager.WinnerBetManager;
import exceptions.BadParametersException;
import exceptions.CompetitionException;


/**
 * Subclass of Bet.
 * This describes a Bet on a single entry assumed as the winner of the competition.
 * 
 * @author Remy
 */
public class WinnerBet extends Bet {
	/**
	 * The entry supposed to be the winner of the competition
	 */
	public Entry winner = null;

	/**
	 * The constructor
	 * @param amount the number of token the subscriber wants to bet
	 * @param betOwner the subscriber that initiated the bet
	 * @param winner the assumed winner of the competition
	 */
	public WinnerBet(long amount, Subscriber betOwner, Entry winner) {
		super(amount, betOwner);
		try {
			this.setWinner(winner);
		} catch (CompetitionException e1) {
		} catch (BadParametersException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Saves the object to the database
	 */
	protected void save() {
		try {
			WinnerBetManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	/**
	 * @return the winner assumed in this bet
	 */
	public Entry getWinner() {
		return winner;
	}

	/**
	 * Set the assumed winner of a competition
	 * @param winner new assumed 
	 * @throws CompetitionException
	 * @throws BadParametersException 
	 */
	public void setWinner(Entry winner) throws CompetitionException, BadParametersException {
		if (winner == null) {
			throw new BadParametersException("The new winner cannot be NULL!");
		}
		// checks that the competition is not over
		if (this.winner != null) {
			this.winner.getCompetition().checkCompetitionNotOver();
			// checks that the new winner is in the same competition as the previous one
			if (this.winner.getCompetition().equals(winner.getCompetition())) {
				throw new BadParametersException("The new winner should be in the same competition as the previous one.");
			}
		}

		winner.removeBet(this);
		this.winner = winner;
		winner.addBet(this);
		save();
	}
	
	/**
	 * Cancel the bet and refund the owner
	 */
	@Override
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
		winner.removeBet(this);

		try {
			WinnerBetManager.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
		
		super.cancel();
	}
	
	/**
	 * Returns a boolean that will tell you if the competition associated with the bet is already over or not
	 */
	@Override
	public boolean isOver() {
		return this.winner.getCompetition().isOver();
	}
	
	/**
	 * Returns a boolean that will tell you if the bet is won or not
	 */
	@Override
	public boolean isWon() {
		if (!this.winner.getCompetition().isSettled())
			return false;
		
		if (this.winner.getCompetition().isDraw())
			return false;
		
		return this.winner.getRank() == Rank.FIRST;
	}

}
