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
import utils.MyCalendar;
import Interface.Competitor;
import dbManager.CompetitionManager;
import dbManager.EntryManager;

/**
 * Description of Competition.
 * 
 * @author Remy
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
//	 * Description of the property competitors.
//	 */
	private HashSet<Entry> entries = new HashSet<Entry>();
	
	private HashSet<DrawBet> drawBets = new HashSet<DrawBet>();

	/**
	 * Description of the property startingDate.
	 */
	private Calendar startingDate = null;
	
	private long totalToken = 0L;
	
	private List<Long> winnerToken = new LinkedList<Long>();

	/**
	 * The constructor.
	 * @throws BadParametersException 
	 */
	public Competition(String competitionName, Calendar closingDate) throws BadParametersException {
		if (competitionName == "") {
			throw new BadParametersException("Competition name cannot be empty!");
		}
		this.name = competitionName;
		
		if (closingDate.before(MyCalendar.getDate())) {
			throw new BadParametersException("The closing date must be in the future!");
		}
		this.closingDate = closingDate;
		this.startingDate = MyCalendar.getDate(); 
		
		for(int i = 0; i < 3; ++i) {
			this.winnerToken.add(0L);
		}
	}

	public Competition(String competitionName, Calendar startingDate, Calendar closingDate) throws BadParametersException {
		this(competitionName, closingDate);
		this.startingDate = startingDate;
	}
	
	public static Competition createCompetition(String competitionName, Date startingDate, Date closingDate, boolean settled, boolean draw, long totalTokens) throws BadParametersException {
		Calendar startingCalendar = MyCalendar.getDate();
		startingCalendar.setTimeInMillis(startingDate.getTime());
		Calendar closingCalendar = MyCalendar.getDate();
		closingCalendar.setTimeInMillis(startingDate.getTime());
		Competition competition = new Competition(competitionName, startingCalendar, closingCalendar);
		competition.setSettled(settled);
		competition.setDraw(draw);
		competition.totalToken = totalTokens;
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
		save();
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
		save();
		//TODO: check validity
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
		save();
		//TODO: Check posteriority
	}

	/**
	 * Returns startingDate.
	 * @return startingDate 
	 */
	public Calendar getStartingCalendar() {
		return this.startingDate;
	}
	

	/**
	 * Sets a value to attribute startingDate. 
	 * @param newStartingDate 
	 */
	public void setStartingDate(Calendar newStartingDate) {
		this.startingDate = newStartingDate;
		save();
		//TODO: check anteriority to closingDate
	}
	
	public boolean isOver() {
		return this.closingDate.after(MyCalendar.getDate());
	}

	public HashSet<Entry> getEntries() {
		return entries;
	}

	public long getTotalToken() {
		return totalToken;
	}
	
	public long getTotalToken(int betType) {
		return CompetitionManager.getTotalToken(betType);
	}

	public long getWinnerToken(int betType) {
		return winnerToken.get(betType);
	}
	
	public void addBet(DrawBet drawBet) {
		drawBets.add(drawBet);
	}
	
	public void removeBet(DrawBet drawBet) {
		drawBets.remove(drawBet);
	}
	
	public HashSet<Bet> getDrawBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		bets.addAll(drawBets);
		return bets;
	}
	
	public HashSet<Bet> getWinnerBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		for(Entry entry : entries) {
			bets.addAll(entry.getWinnerBets());
		}
		return bets;
	}
	
	public HashSet<Bet> getPodiumBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		for(Entry entry : entries) {
			bets.addAll(entry.getPodiumBets());
		}
		return bets;
	}
	
	public HashSet<Bet> getBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		bets.addAll(getDrawBets());

		for(Entry entry : entries) {
			bets.addAll(entry.getBets());
		}
		return bets;
	}
	
	private HashSet<Bet> getBets(int betType) {
		switch(betType) {
		case Bet.WINNER_BET:
			return this.getWinnerBets();
		case Bet.PODIUM_BET:
			return this.getPodiumBets();
		case Bet.DRAW_BET:
			return this.getDrawBets();
		}
		return null;
	}
	
	public void computeTotalToken() {
		this.totalToken = 0;
		for(int i = 0; i < 3; ++i) {
			this.totalToken += CompetitionManager.getTotalToken(i);
		}
	}
	
	public void computeWinnerToken() {
		this.winnerToken.clear();
		for(int i = 0; i < 3; ++i) {
			long tokens = 0;
			for(Bet bet : getBets(i)) {
				if (bet.isWon()) {
					tokens += bet.getAmount();
				}
			}
			this.winnerToken.add(tokens);
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
		try {
			CompetitionManager.delete(this);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void settleWinner(Competitor winner) throws MissingCompetitorException, CompetitionException {
		List<Competitor> competitors = new LinkedList<Competitor>();
		competitors.add(winner);
		settle(competitors);
		this.distributeGains(Bet.WINNER_BET);
	}
	
	public void settlePodium(Competitor first, Competitor second, Competitor third) throws MissingCompetitorException, CompetitionException {
		List<Competitor> competitors = new LinkedList<Competitor>();
		competitors.add(first);
		competitors.add(second);
		competitors.add(third);
		settle(competitors);
		this.distributeGains(Bet.PODIUM_BET);
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
	}
	
	public void settleDraw() throws CompetitionException {
		if (!this.isOver()) {
			throw new CompetitionException("Competition is not over yet!");
		}
		this.setDraw(true);
		
		this.settled = true;
		
		// Compute winner tokens
		this.computeWinnerToken();
		this.distributeGains(Bet.DRAW_BET);
		save();
	}

	private Entry getEntryFromCompetitor(Competitor competitor) {
		for(Entry entry : entries) {
			if (entry.getCompetitor() == competitor) {
				return entry;
			}
		}
		return null;
	}
	
	private void distributeGains(int betType) {
		long tokens;
		// if the competition is a draw we need to count all the tokens, not only the draw ones
		if (betType == Bet.DRAW_BET) {
			tokens = this.getTotalToken();
		}
		else {
			tokens = this.getTotalToken(betType);
		}
		if (tokens == 0)
			return;
		float ratio = tokens / getTotalToken(betType);
		for(Bet bet : getBets(betType)) {
			if (bet.isWon()) {
				bet.settle(ratio);
			}
		}
	}

	public void addEntry(Entry entry) {
		this.entries.add(entry);
	}
}
