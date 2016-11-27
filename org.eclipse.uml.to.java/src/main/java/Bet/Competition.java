/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Competition.
 * 
 * @author Robin
 */
public class Competition {
	/**
	 * Description of the property startingDate.
	 */
	public EDate startingDate = null;

	/**
	 * Description of the property isDraw.
	 */
	public boolean isDraw = false;

	/**
	 * Description of the property closingDate.
	 */
	public EDate closingDate = null;

	/**
	 * Description of the property settled.
	 */
	public boolean settled = false;

	/**
	 * Description of the property competitors.
	 */
	public HashSet<Competitor> competitors = new HashSet<Competitor>();

	/**
	 * Description of the property competitors.
	 */
	public HashSet<Competitor> competitors = new HashSet<Competitor>();

	/**
	 * Description of the property name.
	 */
	public EString name = null;

	// Start of user code (user defined attributes for Competition)

	// End of user code

	/**
	 * The constructor.
	 */
	public Competition() {
		// Start of user code constructor for Competition)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Competition)

	// End of user code
	/**
	 * Returns startingDate.
	 * @return startingDate 
	 */
	public EDate getStartingDate() {
		return this.startingDate;
	}

	/**
	 * Sets a value to attribute startingDate. 
	 * @param newStartingDate 
	 */
	public void setStartingDate(EDate newStartingDate) {
		this.startingDate = newStartingDate;
	}

	/**
	 * Returns isDraw.
	 * @return isDraw 
	 */
	public boolean getIsDraw() {
		return this.isDraw;
	}

	/**
	 * Sets a value to attribute isDraw. 
	 * @param newIsDraw 
	 */
	public void setIsDraw(boolean newIsDraw) {
		this.isDraw = newIsDraw;
	}

	/**
	 * Returns closingDate.
	 * @return closingDate 
	 */
	public EDate getClosingDate() {
		return this.closingDate;
	}

	/**
	 * Sets a value to attribute closingDate. 
	 * @param newClosingDate 
	 */
	public void setClosingDate(EDate newClosingDate) {
		this.closingDate = newClosingDate;
	}

	/**
	 * Returns settled.
	 * @return settled 
	 */
	public boolean getSettled() {
		return this.settled;
	}

	/**
	 * Sets a value to attribute settled. 
	 * @param newSettled 
	 */
	public void setSettled(boolean newSettled) {
		this.settled = newSettled;
	}

	/**
	 * Returns competitors.
	 * @return competitors 
	 */
	public HashSet<Competitor> getCompetitors() {
		return this.competitors;
	}

	/**
	 * Returns competitors.
	 * @return competitors 
	 */
	public HashSet<Competitor> getCompetitors() {
		return this.competitors;
	}

	/**
	 * Returns name.
	 * @return name 
	 */
	public EString getName() {
		return this.name;
	}

	/**
	 * Sets a value to attribute name. 
	 * @param newName 
	 */
	public void setName(EString newName) {
		this.name = newName;
	}

}
