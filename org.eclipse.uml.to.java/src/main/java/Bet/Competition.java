/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

// Start of user code (user defined imports)

import java.util.Calendar;
// End of user code
import java.util.HashSet;

import Betting.Exceptions.BadParametersException;
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
	private Calendar closingDate = null;

	/**
	 * Description of the property competitors.
	 */
	private HashSet<Entry> entries = new HashSet<Entry>();

	/**
	 * Description of the property startingDate.
	 */
	private Calendar startingDate = null;
	
	private long totalToken = 0L;
	
	private long winnerToken = 0L;

	// Start of user code (user defined attributes for Competition)

	// End of user code

	/**
	 * The constructor.
	 * @throws BadParametersException 
	 */
	public Competition(String competitionName, Calendar closingDate) throws BadParametersException {
		if (competitionName == "") {
			throw new BadParametersException("Competition name cannot be empty!");
		}
		this.name = competitionName;
		
		if (closingDate.before(Calendar.getInstance())) {
			throw new BadParametersException("The closing date must be in the future!");
		}
		this.closingDate = closingDate; 
	}

	// Start of user code (user defined methods for Competition)

	public Competition(String competitionName, Calendar startingDate, Calendar closingDate) throws BadParametersException {
		this(competitionName, closingDate);
		this.startingDate = startingDate;
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
	public Calendar getClosingDate() {
		return this.closingDate;
	}

	/**
	 * Sets a value to attribute closingDate. 
	 * @param newClosingDate 
	 */
	public void setClosingDate(Calendar newClosingDate) {
		this.closingDate = newClosingDate;
	}


	/**
	 * Returns startingDate.
	 * @return startingDate 
	 */
	public Calendar getStartingDate() {
		return this.startingDate;
	}

	/**
	 * Sets a value to attribute startingDate. 
	 * @param newStartingDate 
	 */
	public void setStartingDate(Calendar newStartingDate) {
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

	public void settle(float ratio) {
		// TODO: do this
	}


}
