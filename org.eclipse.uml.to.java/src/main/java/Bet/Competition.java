/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

// Start of user code (user defined imports)

import java.util.Date;
// End of user code
import java.util.HashSet;

import Interface.Competitor;

/**
 * Description of Competition.
 * 
 * @author Robin
 */
public class Competition {
	/**
	 * Description of the property settled.
	 */
	public boolean settled = false;

	/**
	 * Description of the property isDraw.
	 */
	public boolean isDraw = false;

	/**
	 * Description of the property name.
	 */
	public String name = null;

	/**
	 * Description of the property closingDate.
	 */
	public Date closingDate = null;

	/**
	 * Description of the property competitors.
	 */
	public HashSet<Competitor> competitor1 = new HashSet<Competitor>();

	/**
	 * Description of the property competitors.
	 */
	public HashSet<Competitor> competitor = new HashSet<Competitor>();

	/**
	 * Description of the property startingDate.
	 */
	public Date startingDate = null;

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
	 * Returns name.
	 * @return name 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets a value to attribute name. 
	 * @param newName 
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Returns closingDate.
	 * @return closingDate 
	 */
	public Date getClosingDate() {
		return this.closingDate;
	}

	/**
	 * Sets a value to attribute closingDate. 
	 * @param newClosingDate 
	 */
	public void setClosingDate(Date newClosingDate) {
		this.closingDate = newClosingDate;
	}

	/**
	 * Returns competitors.
	 * @return competitors 
	 */
	public HashSet<Competitor> getCompetitors1() {
		return this.competitor1;
	}

	/**
	 * Returns competitors.
	 * @return competitors 
	 */
	public HashSet<Competitor> getCompetitors() {
		return this.competitor;
	}

	/**
	 * Returns startingDate.
	 * @return startingDate 
	 */
	public Date getStartingDate() {
		return this.startingDate;
	}

	/**
	 * Sets a value to attribute startingDate. 
	 * @param newStartingDate 
	 */
	public void setStartingDate(Date newStartingDate) {
		this.startingDate = newStartingDate;
	}

}
