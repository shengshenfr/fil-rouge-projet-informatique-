/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import Interface.Competitor;
import Bet.Entry;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Bet.
 * 
 * @author Robin
 */
@SuppressWarnings("unused")
public class Bet {
	/**
	 * Description of the property amount.
	 */
	public long amount = 0L;

	/**
	 * Description of the property subscribers.
	 */
	private String betOwner ;

	/**
	 * Description of the property betNextId.
	 */
	public static int betNextId = 1;

	/**
	 * Description of the property idBet.
	 */
	public Integer idBet = 0;

	private int idEntry;
	private int idEntry2;
	private int idEntry3;
	private String competitionName;

	

	// Start of user code (user defined attributes for Bet)

	// End of user code

	/**
	 * The constructor.
	 */
	//bet on winner without the idBet
	public Bet(String betOwner , long amount , int idEntry) {
		// Start of user code constructor for Bet Winner !!!
		this.idBet = null;
		this.betOwner = betOwner;
		this.amount = amount;
		this.idEntry = idEntry;
		// End of user code
	}
	
	//bet on winner with the idBet
	public Bet(Integer idBet, String betOwner , long amount , int idEntry) {
		// Start of user code constructor for Bet Winner !!!
		this.idBet = idBet;
		this.betOwner = betOwner;
		this.amount = amount;
		this.idEntry = idEntry;
		// End of user code
	}
	
	
	//bet on podium without the idBet
	public Bet(String betOwner , long amount, int idEntry, int idEntry2, int idEntry3) {
		// Start of user code constructor for Bet Podium !!!
		this.idBet = null;
		this.betOwner = betOwner;
		this.amount = amount;
		this.idEntry = idEntry;
		this.idEntry2 = idEntry2;
		this.idEntry3 = idEntry3;
		// End of user code
	}
	
	
	//bet on podium with the idBet
	public Bet(Integer idBet, String betOwner , long amount, int idEntry, int idEntry2, int idEntry3) {
		// Start of user code constructor for Bet Podium !!!
		this.idBet = idBet;
		this.betOwner = betOwner;
		this.amount = amount;
		this.idEntry = idEntry;
		this.idEntry2 = idEntry2;
		this.idEntry3 = idEntry3;
		// End of user code
	}
	
	
	//bet on draw without the idBet
	public Bet(String betOwner, long amount, String competitionName) {
		this.idBet = null;
		this.amount = amount;
		this.betOwner = betOwner;
		this.competitionName = competitionName;
	}
	
	//bet on draw with the idBet
	public Bet(Integer idBet,String betOwner,  long amount, String competitionName) {
		this.idBet = idBet;
		this.amount = amount;
		this.betOwner = betOwner;
		this.competitionName =competitionName;
	}
	

	// Start of user code (user defined methods for Bet)
//bet on podium


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
	 * Sets a value to attribute subscribers. 
	 * @param newSubscribers 
	 */
	public void setBetOwner(String betOwner) {
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


	public Object getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIdEntry() {
		return idEntry;
	}

	public void setIdEntry(int idEntry) {
		this.idEntry = idEntry;
	}

	public int getIdEntry2() {
		return idEntry2;
	}

	public void setIdEntry2(int idEntry2) {
		this.idEntry2 = idEntry2;
	}

	public int getIdEntry3() {
		return idEntry3;
	}

	public void setIdEntry3(int idEntry3) {
		this.idEntry3 = idEntry3;
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}


	public String getBetOwner() {
		return betOwner;
	}
	
	
}
