/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import Betting.Exceptions.BadParametersException;
import Betting.Exceptions.NotExistingCompetitorException;
import Interface.Competitor;

/**
 * Description of Competition.
 * 
 * @author Robin, Rémy
 */
public class Competition {
	/**
	 * Description of the property settled.
	 */
	private boolean settled = false;

	/**
	 * Description of the property isDraw.
	 */
	private boolean draw = false;

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
	
	private HashSet<DrawBet> drawBets = new HashSet<DrawBet>();

	/**
	 * Description of the property startingDate.
	 */
	private Calendar startingDate = null;
	
	private long totalToken = 0L;
	
	private long winnerToken = 0L;

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

	public Competition(String competitionName, Calendar startingDate, Calendar closingDate) throws BadParametersException {
		this(competitionName, closingDate);
		this.startingDate = startingDate;
	}

	/**
	 * Returns settled.
	 * @return settled 
	 */
	public boolean isSettled() {
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
	public boolean isDraw() {
		return this.draw;
	}

	/**
	 * Sets a value to attribute isDraw. 
	 * @param newIsDraw 
	 */
	public void setDraw(boolean newIsDraw) {
		this.draw = newIsDraw;
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
		return this.isSettled();
	}

	public HashSet<Entry> getEntries() {
		return entries;
	}

	public Collection<Competition> consultBets(){
		return null; // TODO
	}

	public long getTotalToken() {
		this.computeTotalToken();
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
	
	public void addBet(DrawBet drawBet) {
		drawBets.add(drawBet);
	}
	
	public void removeBet(DrawBet drawBet) {
		drawBets.remove(drawBet);
	}
	
	public HashSet<DrawBet> getDrawBets() {
		return drawBets;
	}
	
	public HashSet<Bet> getBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		bets.addAll(getDrawBets());

		for(Entry entry : entries) {
			bets.addAll(entry.getBets());
		}
		return bets;
	}
	
	public void computeTotalToken() {
		totalToken = 0;
		for(Bet bet : getBets()) {
			totalToken += bet.getAmount();
		}
	}
	
	public void computeWinnerToken() {
		winnerToken = 0;
		for(Bet bet : getBets()) {
			System.out.println(bet.isWon());
			if (bet.isWon()) {
				winnerToken += bet.getAmount();
			}
		}
	}

	public void settle(List<Competitor> competitors) throws NotExistingCompetitorException {
		for(int i=0; i<competitors.size(); i++) {
			Competitor competitor = competitors.get(i);
			Entry entry = this.getEntryFromCompetitor(competitor);
			if (entry == null) {
				throw new NotExistingCompetitorException("This competitor does not exist in this competition!");
			}

			entry.setRank(Rank.getRankIndex(i));
		}
		
		// Setting to NOT_PODIUM others
		for(Entry entry : entries) {
			if (entry.getRank() == null) {
				entry.setRank(Rank.NOT_PODIUM);
			}
		}
		
		this.settled = true;
		
		// Compute winner tokens
				this.computeWinnerToken();
	}
	
	public void settleDraw() {
		this.setDraw(true);
		
		this.settled = true;
		
		// Compute winner tokens
		this.computeWinnerToken();
	}

	private Entry getEntryFromCompetitor(Competitor competitor) {
		for(Entry entry : entries) {
			if (entry.getCompetitor() == competitor) {
				return entry;
			}
		}
		return null;
	}

	public void addEntry(Entry entry) {
		this.entries.add(entry);
	}
}
