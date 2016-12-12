/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;

import Bet.Bet;
import Individual.Subscriber;
import dbManager.WinnerBetManager;
import exceptions.CompetitionException;


/**
 * Description of WinnerBet.
 * 
 * @author Robin, R�my
 */
public class WinnerBet extends Bet {
	/**
	 * Description of the property entrys.
	 */
	public Entry winner = null;

	/**
	 * The constructor.
	 */
	public WinnerBet(long amount, Subscriber betOwner, Entry winner) {
		super(amount, betOwner);
		try {
			this.setWinner(winner);
		} catch (CompetitionException e1) {
		}

	}
	
	protected void save() {
		try {
			WinnerBetManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	public Entry getWinner() {
		return winner;
	}

	public void setWinner(Entry winner) throws CompetitionException {
		winner.removeBet(this);
		this.winner = winner;
		winner.addBet(this);
		save();
	}
	
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
	
	@Override
	public boolean isOver() {
		return this.winner.getCompetition().isOver();
	}
	
	@Override
	public boolean isWon() {
		if (this.winner.getCompetition().isSettled()==0)
			return false;
		
		if (this.winner.getCompetition().isDraw()==1)
			return false;
		
		return this.winner.getRank() == Rank.FIRST;
	}

}
