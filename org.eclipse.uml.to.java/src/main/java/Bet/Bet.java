/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Interface.Competitor;
import dbManager.CompetitionManager;
import dbManager.EntryManager;
import dbManager.SubscriberManager;

import java.sql.SQLException;

import Bet.Entry;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;
import exceptions.MissingCompetitionException;
import exceptions.MissingTokenException;
import exceptions.SubscriberException;
import Individual.Subscriber;

/**
 * This is an abstract class describing a betting on a competition by a subscriber.
 * 
 * @author Remy
 */
@SuppressWarnings("unused")
abstract public class Bet {
	/**
	 * The different types of bet available
	 */
	final public static int WINNER_BET = 0;
	final public static int PODIUM_BET = 1;
	final public static int DRAW_BET = 2;
	
	/**
	 * The number of token the subscriber wants to bet
	 */
	private long amount = 0L;

	/**
	 * The subscriber that initiated the bet
	 */
	private Subscriber betOwner;

	/**
	 * The index of the bet, used for database sync
	 */
	private int id = 0;

	/**
	 * The counter for the bet index
	 */
	private static int nextId = 1;

	/**
	 * Contructor
	 * @param amount the number of token that the subscriber wants to bet
	 * @param betOwner the subscriber that initiated the bet
	 */
	public Bet(long amount, Subscriber betOwner) {
		this(amount, betOwner, nextId++);
	}
	
	/**
	 * Constructor
	 * @param amount the number of token that the subscriber wants to bet
	 * @param betOwner the subscriber that initiated the bet
	 * @param id the index of the bet
	 */
	public Bet(long amount, Subscriber betOwner, int id) {
		this.amount = amount;
		this.betOwner = betOwner;
		this.id = id;
	}
	
	//TODO comments from here
	static public DrawBet createDrawBet(int id, String ownerName, long amount, String competitionName) throws BadParametersException, MissingCompetitionException, CompetitionException, SubscriberException, ExistingCompetitorException {
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
	
	static public WinnerBet createWinnerBet(int id,  long amount, int idWinner,String ownerName) throws BadParametersException, MissingCompetitionException, CompetitionException, SubscriberException, ExistingCompetitorException {
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
	
	static public PodiumBet createPodiumBet(int id, String ownerName, long amount, int idFirst, int idSecond, int idThird) throws BadParametersException, MissingCompetitionException, CompetitionException, SubscriberException, ExistingCompetitorException {
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
	 * @return the number of tokens bet by the owner 
	 */
	public long getAmount() {
		return this.amount;
	}

	/**
	 * Sets the number of tokens bet by the owner
	 * @param the new amount
	 */
	public void setAmount(long newAmount) {
		this.amount = newAmount;
	}

	/**
	 * Sets the value of the next bet index
	 * @param newBetNextId 
	 */
	public static void setBetNextId(int newBetNextId) {
		nextId = newBetNextId;
	}

	/**
	 * @return the index of the bet
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the index of the bet 
	 * @param the new index
	 */
	public void setId(int newId) {
		this.id = newId;
	}

	/**
	 * @return the subscriber that initiated the bet
	 */
	public Subscriber getBetOwner() {
		return betOwner;
	}
	
	/**
	 * Returns a boolean that will tell you if the competition associated with the bet is already over or not
	 */
	public abstract boolean isOver();

	/**
	 * Returns a boolean that will tell you if the bet is won or not
	 */
	public abstract boolean isWon();
	
	public void cancel() throws CompetitionException {
		try {
			this.betOwner.creditSubscriber(amount);
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Checks whether the competition associated with the bet is over yet or not
	 * @throws CompetitionException
	 */
	public void checkCompetitionNotOver() throws CompetitionException {
		if (this.isOver()) {
			throw new CompetitionException("Competition is already over!");
		}
	}

	/**
	 * Distributes the gain to the bet owner if the bet is won
	 * @param ratio the multiplicative factor for the gains
	 */
	public void settle(float ratio) {
		if (!isWon())
			return;
		
		try {
			this.betOwner.creditSubscriber((long)(this.amount * ratio));
		} catch (BadParametersException e) {
			e.printStackTrace();
		}
	}
}
