/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;

import Bet.Bet;
import Individual.Subscriber;
import dbManager.PodiumBetManager;
import dbManager.WinnerBetManager;
import exceptions.CompetitionException;


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

	/**
	 * The constructor.
	 */
	public WinnerBet(long amount, Subscriber betOwner, Entry winner) {
		super(amount, betOwner);
		this.setWinner(winner);
		
		try {
			WinnerBetManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
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
	public void cancel() throws CompetitionException {
		if (isOver()) {
			throw new CompetitionException("Competition is already over!");
		}
		
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
		if (!this.winner.getCompetition().isSettled())
			return false;
		
		if (this.winner.getCompetition().isDraw())
			return false;
		
		return this.winner.getRank() == Rank.FIRST;
	}

}
