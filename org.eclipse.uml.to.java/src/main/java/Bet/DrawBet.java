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
	
	protected void save() {
		try {
			DrawBetManager.update(this);
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
	 * @throws CompetitionException 
	 */
	public void setCompetition(Competition newCompetition) throws CompetitionException {
		this.competition = newCompetition;
		
		save();
	}
	
	@Override
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
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
		if (this.competition.isSettled()==0)
			return false;
		return this.competition.isDraw()==1;
	}

}
