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
 * Description of DrawBet.
 * 
 * @author Robin, Rémy
 */
public class DrawBet extends Bet {
	/**
	 * Description of the property competitions.
	 */
	private Competition competition = null;

	/**
	 * The constructor.
	 */
	public DrawBet(long amount, Subscriber betOwner, Competition competition) {
		super(amount, betOwner);
		this.competition = competition;
		
		this.competition.addBet(this);
		
		try {
			DrawBetManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

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
	
	@Override
	public void cancel() throws CompetitionException {
		if (isOver()) {
			throw new CompetitionException("Competition is already over!");
		}
		
		this.competition.removeBet(this);
		try {
			DrawBetManager.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
		
		super.cancel();
	}
	
	@Override
	public boolean isOver() {
		return this.competition.isOver();
	}
	
	@Override
	public boolean isWon() {
		if (!this.competition.isSettled())
			return false;
		return this.competition.isDraw();
	}

}
