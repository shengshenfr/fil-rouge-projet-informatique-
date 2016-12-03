/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Interface.Competitor;
import Bet.Entry;
import Individual.Subscriber;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Bet.
 * 
 * @author Robin, Rémy
 */
@SuppressWarnings("unused")
public class Bet {
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
	private static int betNextId = 1;

	/**
	 * Description of the property idBet.
	 */
	private int idBet = 0;

	// Start of user code (user defined attributes for Bet)

	// End of user code

	/**
	 * The constructor.
	 */
	
	

	// End of user code
	/**
	 * Returns amount.
	 * @return amount 
	 */
	public long getAmount() {
		return this.amount;
	}

	public Bet(long amount, Subscriber betOwner) {
		this.amount = amount;
		this.betOwner = betOwner;
		this.idBet = betNextId++;
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
		return betNextId;
	}

	/**
	 * Sets a value to attribute betNextId. 
	 * @param newBetNextId 
	 */
	public static void setBetNextId(int newBetNextId) {
		betNextId = newBetNextId;
	}

	/**
	 * Returns idBet.
	 * @return idBet 
	 */
	public int getIdBet() {
		return this.idBet;
	}

	/**
	 * Sets a value to attribute idBet. 
	 * @param newIdBet 
	 */
	public void setIdBet(int newIdBet) {
		this.idBet = newIdBet;
	}

	public Subscriber getBetOwner() {
		return betOwner;
	}
	
	
}
