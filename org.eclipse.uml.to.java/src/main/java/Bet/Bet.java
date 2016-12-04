/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Interface.Competitor;
import dbManager.CompetitionManager;
import dbManager.CompetitorManager;
import dbManager.EntryManager;
import dbManager.SubscriberManager;

import java.sql.SQLException;

import Bet.Entry;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.MissingCompetitionException;
import exceptions.MissingTokenException;
import Individual.Subscriber;

/**
 * Description of Bet.
 * 
 * @author Robin, Rémy
 */
@SuppressWarnings("unused")
abstract public class Bet {
	/**
	 * Description of the property amount.
	 */
	private long amount = 0L;

	/**
	 * Description of the property subscribers.
	 */
	private Subscriber betOwner;

	/**
	 * Description of the property betNextId.
	 */
	private static int nextId = 1;

	/**
	 * Description of the property idBet.
	 */
	private int id = 0;

	/**
	 * Returns amount.
	 * @return amount 
	 */
	public long getAmount() {
		return this.amount;
	}

	public Bet(long amount, Subscriber betOwner) {
		this(amount, betOwner, nextId++);
	}
	
	public Bet(long amount, Subscriber betOwner, int id) {
		this.amount = amount;
		this.betOwner = betOwner;
		this.id = id;
	}
	
	static public DrawBet createDrawBet(int id, String ownerName, long amount, String competitionName) throws BadParametersException, MissingCompetitionException {
		Subscriber owner;
		try {
			owner = SubscriberManager.findByUsername(ownerName);
		} catch (SQLException e) {
			return null;
		}
		Competition competition = CompetitionManager.findBycompetitionName(competitionName);
		
		DrawBet bet = new DrawBet(amount, owner, competition);
		bet.setId(id);
		return bet;
	}
	
	static public WinnerBet createWinnerBet(int id, String ownerName, long amount, int idWinner) throws BadParametersException, MissingCompetitionException {
		Subscriber owner;
		try {
			owner = SubscriberManager.findByUsername(ownerName);
		} catch (SQLException e) {
			return null;
		}
		// TODO: add exception notExistingEntry...
		Entry winner = null;
		try {
			winner = EntryManager.findById(idWinner);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WinnerBet bet = new WinnerBet(amount, owner, winner);
		bet.setId(id);
		return bet;
	}
	
	static public PodiumBet createPodiumBet(int id, String ownerName, long amount, int idFirst, int idSecond, int idThird) throws BadParametersException, MissingCompetitionException {
		Subscriber owner;
		try {
			owner = SubscriberManager.findByUsername(ownerName);
		} catch (SQLException e) {
			return null;
		}
		// TODO: add exception notExistingEntry...
		Entry first = null, second = null, third = null;
		try {
			first = EntryManager.findById(idFirst);
			second = EntryManager.findById(idSecond);
			third = EntryManager.findById(idThird);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PodiumBet bet = new PodiumBet(amount, owner, first, second, third);
		bet.setId(id);
		return bet;
	}

	/**
	 * Sets a value to attribute amount. 
	 * @param newAmount 
	 */
	public void setAmount(long newAmount) {
		this.amount = newAmount;
	}


	/**
	 * Sets a value to attribute subscribers. 
	 * @param newSubscribers 
	 */
	public void setBetOwner(Subscriber betOwner) {
		this.betOwner = betOwner;
	}

	/**
	 * Returns betNextId.
	 * @return betNextId 
	 */
	public static int getBetNextId() {
		return nextId;
	}

	/**
	 * Sets a value to attribute betNextId. 
	 * @param newBetNextId 
	 */
	public static void setBetNextId(int newBetNextId) {
		nextId = newBetNextId;
	}

	/**
	 * Returns idBet.
	 * @return idBet 
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets a value to attribute idBet. 
	 * @param newIdBet 
	 */
	public void setId(int newId) {
		this.id = newId;
	}

	public Subscriber getBetOwner() {
		return betOwner;
	}
	
	public boolean isOver() {
		return false;
	}

	public boolean isWon() {
		return false;
	}
	
	public void cancel() throws CompetitionException {
		try {
			this.betOwner.creditSubscriber(amount);
		} catch (MissingTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void settle(float ratio) {
		if (!isWon())
			return;
		
		try {
			this.betOwner.creditSubscriber((long)(this.amount * ratio));
		} catch (MissingTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
