/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;

import Bet.Bet;
import Individual.Subscriber;
import dbManager.DrawBetManager;
import exceptions.CompetitionException;

/**
 * Subclass of Bet.
 * Winning when a competition is a draw.
 * 
 * @author Remy
 */
public class DrawBet extends Bet {
	/**
	 * The competition associated to the bet
	 */
	private Competition competition = null;

	/**
	 * DrawBet constructor
	 * @param amount the number of token the subscriber wants to bet
	 * @param betOwner the subscriber that owns the bet
	 * @param competition the competition on which the bet is realized
	 */
	public DrawBet(long amount, Subscriber betOwner, Competition competition) {
		super(amount, betOwner);
		this.competition = competition;
		
		// add the bet to the competition list
		this.competition.addBet(this);
		
		try {
			DrawBetManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Saves the object to the database
	 */
	protected void save() {
		try {
			DrawBetManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	/**
	 * @return the competition on which the bet is realized
	 */
	public Competition getCompetition() {
		return this.competition;
	}

	/**
	 * Set the competition on which the bet should be realized
	 * @param the new competition
	 * @throws CompetitionException 
	 */
	public void setCompetition(Competition newCompetition) throws CompetitionException {
		//checking that the current and the new competitons are not already over
		this.competition.checkCompetitionNotOver();
		newCompetition.checkCompetitionNotOver();
		this.competition = newCompetition;
		
		save();
	}
	
	/**
	 * Cancel the bet and refund the owner
	 */
	@Override
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
		// remove the bet from the competition list
		this.competition.removeBet(this);
		try {
			DrawBetManager.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		super.cancel();
	}
	
	/**
	 * Returns a boolean that will tell you if the competition associated with the bet is already over or not
	 */
	@Override
	public boolean isOver() {
		return this.competition.isOver();
	}
	
	/**
	 * Returns a boolean that will tell you if the bet is won or not
	 */
	@Override
	public boolean isWon() {
		if (!this.competition.isSettled())
			return false;
		return this.competition.isDraw();
	}

}
