/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

// Start of user code (user defined imports)

import java.sql.Date;
// End of user code
import java.util.HashSet;

import Interface.Competitor;

/**
 * Description of Competition.
 * 
 * @author Robin
 */
@SuppressWarnings("unused")
public class Competition {
	/**
	 * Description of the property settled.
	 */
	private boolean settled = false;

	/**
	 * Description of the property isDraw.
	 */
	private boolean isDraw = false;

	/**
	 * Description of the property name.
	 */
	private String name = null;

	/**
	 * Description of the property closingDate.
	 */
	private Date closingDate = null;

	/**
	 * Description of the property competitors.
	 */
	private HashSet<Competitor> competitor = new HashSet<Competitor>();

	/**
	 * Description of the property startingDate.
	 */
	private Date startingDate = null;
	
	private long totalToken = 0L;
	
	private long winnerToken = 0L;

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

	public Competition(String competition, Calendar closingDate) {
		// TODO Auto-generated constructor stub
	}

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
	
	public boolean isOver() {
		return this.getSettled();
	}

	public Calendar getclosedate_calendar() {
		// TODO Auto-generated method stub
		return null;
	}

	public void add_competitor(Competitor competitor2) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Competition> getCompetitors(){

		return getCompetitors();
	}
	



	public String getName_competition() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRankCompetitor(Competitor competitor2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void deleteCompetitor(Competitor competitor2) {
		// TODO Auto-generated method stub
		
	}

	public long getTotalToken() {
		return totalToken;
	}

	public void setTotalToken(long totalToken) {
		this.totalToken = totalToken;
	}

	public long getWinnerToken() {
		return winnerToken;
	}

	public void setWinnerToken(long winnerToken) {
		this.winnerToken = winnerToken;
	}

	public HashSet<Competitor> getCompetitor() {
		return competitor;
	}

	public void setCompetitor(HashSet<Competitor> competitor) {
		this.competitor = competitor;
	}


}
