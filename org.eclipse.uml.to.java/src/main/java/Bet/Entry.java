/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Entry.
 * 
 * @author Robin
 */
public class Entry {
	/**
	 * Description of the property enumerations.
	 */
	public Enumeration enumerations = null;

	/**
	 * Description of the property podiumBets.
	 */
	public HashSet<PodiumBet> podiumBets = new HashSet<PodiumBet>();

	/**
	 * Description of the property winnerBets.
	 */
	public HashSet<WinnerBet> winnerBets = new HashSet<WinnerBet>();

	// Start of user code (user defined attributes for Entry)

	// End of user code

	/**
	 * The constructor.
	 */
	public Entry() {
		// Start of user code constructor for Entry)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Entry)

	// End of user code
	/**
	 * Returns enumerations.
	 * @return enumerations 
	 */
	public Enumeration getEnumerations() {
		return this.enumerations;
	}

	/**
	 * Sets a value to attribute enumerations. 
	 * @param newEnumerations 
	 */
	public void setEnumerations(Enumeration newEnumerations) {
		this.enumerations = newEnumerations;
	}

	/**
	 * Returns podiumBets.
	 * @return podiumBets 
	 */
	public HashSet<PodiumBet> getPodiumBets() {
		return this.podiumBets;
	}

	/**
	 * Returns winnerBets.
	 * @return winnerBets 
	 */
	public HashSet<WinnerBet> getWinnerBets() {
		return this.winnerBets;
	}

}
