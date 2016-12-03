/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Interface.Competitor;
import Bet.Entry;
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
	
	public Bet(long amount, Subscriber betOwner, int idBet) {
		this.amount = amount;
		this.betOwner = betOwner;
		this.id = idBet;
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

	public boolean isWon() {
		return false;
	}
}
