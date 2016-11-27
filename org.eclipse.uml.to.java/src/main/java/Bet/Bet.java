/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Bet.
 * 
 * @author Robin
 */
public class Bet {
	/**
	 * Description of the property amount.
	 */
	public long amount = 0L;

	/**
	 * Description of the property subscribers.
	 */
	public Subscriber subscribers = null;

	/**
	 * Description of the property betNextId.
	 */
	public static int betNextId = 0;

	/**
	 * Description of the property idBet.
	 */
	public int idBet = 0;

	// Start of user code (user defined attributes for Bet)

	// End of user code

	/**
	 * The constructor.
	 */
	public Bet() {
		// Start of user code constructor for Bet)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Bet)

	// End of user code
	/**
	 * Returns amount.
	 * @return amount 
	 */
	public long getAmount() {
		return this.amount;
	}

	/**
	 * Sets a value to attribute amount. 
	 * @param newAmount 
	 */
	public void setAmount(long newAmount) {
		this.amount = newAmount;
	}

	/**
	 * Returns subscribers.
	 * @return subscribers 
	 */
	public Subscriber getSubscribers() {
		return this.subscribers;
	}

	/**
	 * Sets a value to attribute subscribers. 
	 * @param newSubscribers 
	 */
	public void setSubscribers(Subscriber newSubscribers) {
		this.subscribers = newSubscribers;
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

}
