/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Bet.Bet;
import Individual.Subscriber;
import dbManager.PodiumBetManager;
import exceptions.BadParametersException;
import exceptions.CompetitionException;

/**
 * Subclass of Bet.
 * This describes a Bet on a podium (three ordered entries) assumed as the winners of the competition.
 * 
 * @author Remy
 */
public class PodiumBet extends Bet {
	/**
	 * The ordered podium assumed by this bet
	 */
	public List<Entry> podium = new LinkedList<Entry>();

	/**
	 * 
	 * @param amount the number of token the subscriber wants to bet
	 * @param betOwner the subscribed that initiated the bet
	 * @param first the entry supposed to be first
	 * @param second the entry supposed to be second
	 * @param third the entry supposed to be third
	 */
	public PodiumBet(long amount, Subscriber betOwner, Entry first, Entry second, Entry third) {
		super(amount, betOwner);
		try {
			this.setPodium(first, second, third);
		} catch (CompetitionException e1) {
		} catch (BadParametersException e) {
			e.printStackTrace();
		}
		
		try {
			PodiumBetManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the object to the database
	 */
	protected void save() {
		try {
			PodiumBetManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	/**
	 * Returns the assumed podium of the bet
	 * @return 
	 */
	public List<Entry> getPodium() {
		return this.podium;
	}
	
	
	/**
	 * Set the assumed podium
	 * @param first the entry supposed to be first
	 * @param second the entry supposed to be second
	 * @param third the entry supposed to be third
	 * @throws CompetitionException
	 * @throws BadParametersException 
	 */
	public void setPodium(Entry first, Entry second, Entry third) throws CompetitionException, BadParametersException {
		if (first == null || second == null || third == null) {
			throw new BadParametersException("The new entries cannot be NULL!");
		}
		// checks that the competition is not over
		if (this.podium.size() > 0) {
			this.podium.get(0).getCompetition().checkCompetitionNotOver();
			// checks that the new winners are in the same competition as the previous ones
			if (this.podium.get(0).getCompetition().equals(first.getCompetition())) {
				throw new BadParametersException("The new winner should be in the same competition as the previous one.");
			}
		}
		
		// checks that all the entries are in the same 
		if (first.getCompetition() != second.getCompetition() || second.getCompetition() != third.getCompetition()) {
			throw new BadParametersException("All the new entries must be in the same competition");
		}
		
		for(Entry entry : podium) {
			entry.removeBet(this);
		}

		podium.clear();
		podium.add(first);
		podium.add(second);
		podium.add(third);
		
		for(Entry entry : podium) {
			entry.addBet(this);
		}
		
		save();
	}
	
	/**
	 * Cancel the bet and refund the owner
	 */
	@Override
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
		for(Entry entry : podium) {
			entry.removeBet(this);
		}

		try {
			PodiumBetManager.delete(this);
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
		return this.podium.get(0).getCompetition().isOver();
	}
	
	/**
	 * Returns a boolean that will tell you if the bet is won or not
	 */
	@Override
	public boolean isWon() {
		if (!this.podium.get(0).getCompetition().isSettled())
			return false;
		
		if (this.podium.get(0).getCompetition().isDraw())
			return false;
	
		for(int i=0; i<podium.size(); i++) {
			if (this.podium.get(i).getRank() != Rank.getRankIndex(i)) {
				return false;
			}
		}
		
		return true;
	}

}
