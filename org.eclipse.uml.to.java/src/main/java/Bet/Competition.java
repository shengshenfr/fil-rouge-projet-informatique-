/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.MissingCompetitorException;
import Interface.Competitor;
import dbManager.CompetitionManager;
import dbManager.CompetitorManager;
import dbManager.EntryManager;

/**
 * Description of Competition.
 * 
 * @author Robin, Rémy
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

		try {
			CompetitionManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	public Competition(String competitionName, Calendar startingDate, Calendar closingDate) throws BadParametersException {
		this(competitionName, closingDate);
		this.startingDate = startingDate;
	}
	
	public static Competition createCompetition(String competitionName, Date startingDate, Date closingDate, boolean settled, boolean draw) throws BadParametersException {
		Calendar startingCalendar = new GregorianCalendar();
		startingCalendar.setTimeInMillis(startingDate.getTime());
		Calendar closingCalendar = new GregorianCalendar();
		closingCalendar.setTimeInMillis(startingDate.getTime());
		//TODO : persist competition & entries
		Competition competition = new Competition(competitionName, startingCalendar, closingCalendar);
		competition.setSettled(settled);
		competition.setDraw(draw);
		return competition;
	}
	
	protected void save() {
		try {
			CompetitionManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
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
	public Calendar getClosingCalendar() {
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
	public Calendar getStartingCalendar() {
		return this.startingDate;
	}
	
	public Date getStartingDate() {
		return new Date(getStartingCalendar().getTimeInMillis());
	}
	
	public Date getClosingDate() {
		return new Date(getClosingCalendar().getTimeInMillis());
	}

	/**
	 * Sets a value to attribute startingDate. 
	 * @param newStartingDate 
	 */
	public void setStartingDate(Calendar newStartingDate) {
		this.startingDate = newStartingDate;
	}
	
	public boolean isOver() {
		return this.closingDate.after(Calendar.getInstance());
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
			if (bet.isWon()) {
				winnerToken += bet.getAmount();
			}
		}
	}
	
	public void checkCompetitionNotOver() throws CompetitionException {
		if (this.isOver()) {
			throw new CompetitionException("Competition is already over!");
		}
	}
	
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
		try {
			CompetitionManager.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
		
		// delete entries related to competition
		for(Entry e : getEntries()) {
			e.cancel();
		}
	}

	public void settle(List<Competitor> competitors) throws MissingCompetitorException, CompetitionException {
		if (!this.isOver()) {
			throw new CompetitionException("Competition is not over yet!");
		}
		for(int i=0; i<competitors.size(); i++) {
			Competitor competitor = competitors.get(i);
			Entry entry = this.getEntryFromCompetitor(competitor);
			if (entry == null) {
				throw new MissingCompetitorException("This competitor does not exist in this competition!");
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
		this.distributeGains();
	}
	
	public void settleDraw() throws CompetitionException {
		if (!this.isOver()) {
			throw new CompetitionException("Competition is not over yet!");
		}
		this.setDraw(true);
		
		this.settled = true;
		
		// Compute winner tokens
		this.computeWinnerToken();
		this.distributeGains();
	}

	private Entry getEntryFromCompetitor(Competitor competitor) {
		for(Entry entry : entries) {
			if (entry.getCompetitor() == competitor) {
				return entry;
			}
		}
		return null;
	}
	
	private void distributeGains() {
		if (getTotalToken() == 0)
			return;
		float ratio = getWinnerToken() / getTotalToken();
		for(Bet bet : getBets()) {
			if (bet.isWon()) {
				bet.settle(ratio);
			}
		}
	}

	public void addEntry(Entry entry) {
		this.entries.add(entry);
	}
}
