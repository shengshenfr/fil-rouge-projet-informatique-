/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;
import java.util.HashSet;

import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.MissingCompetitionException;
import Interface.Competitor;
import dbManager.CompetitionManager;
import dbManager.CompetitorManager;
import dbManager.EntryManager;

/**
 * Description of Entry.
 * 
 * @author Robin, Rémy
 */
public class Entry {
	private int id = 0;

	static private int nextId = 0;

	/**
	 * Description of the property podiumBets.
	 */
	private HashSet<PodiumBet> podiumBets = new HashSet<PodiumBet>();

	/**
	 * Description of the property enumerations.
	 */
	private Rank rank = null;

	/**
	 * Description of the property winnerBets.
	 */
	private HashSet<WinnerBet> winnerBets = new HashSet<WinnerBet>();

	private Competitor competitor = null;
	private Competition competition = null;

	public Entry(Competition competition, Competitor competitor, int id, Rank rank) throws BadParametersException {
		if (competition == null) {
			throw new BadParametersException("The competition cannot be null!");
		}
		this.competition = competition;

		if (competitor == null) {
			throw new BadParametersException("The competitor cannot be null!");
		}
		this.competitor = competitor;

		this.competition.addEntry(this);
		this.id = id;
		this.rank = rank;
		
		try {
			EntryManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	/**
	 * The constructor.
	 * 
	 * @throws BadParametersException
	 */
	public Entry(Competition competition, Competitor competitor) throws BadParametersException {
		this(competition, competitor, nextId++, null);
	}

	static public Entry createEntry(int id, String competitionName, String competitorName, int rank)
			throws BadParametersException, MissingCompetitionException {
		Competition competition = CompetitionManager.findBycompetitionName(competitionName);
		Competitor competitor = CompetitorManager.findCompetitorByName(competitorName);
		Rank rankObject = Rank.getRankIndex(rank);
		return new Entry(competition, competitor, id, rankObject);
	}
	
	protected void save() {
		try {
			EntryManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}
	
	public void cancel() throws CompetitionException {
		if (this.competition.isOver()) {
			throw new CompetitionException("Competition is already over!");
		}
		
		try {
			EntryManager.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
		
		// delete bets related to the entry
		for(Bet b : getBets()) {
			b.cancel();
		}
	}

	/**
	 * Returns podiumBets.
	 * 
	 * @return podiumBets
	 */
	public HashSet<PodiumBet> getPodiumBets() {
		return this.podiumBets;
	}

	/**
	 * Returns winnerBets.
	 * 
	 * @return winnerBets
	 */
	public HashSet<WinnerBet> getWinnerBets() {
		return this.winnerBets;
	}

	public HashSet<Bet> getBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		bets.addAll(podiumBets);
		bets.addAll(winnerBets);
		return bets;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public void addBet(PodiumBet podiumBet) {
		podiumBets.add(podiumBet);
	}

	public void removeBet(PodiumBet podiumBet) {
		podiumBets.remove(podiumBet);
	}

	public void addBet(WinnerBet winnerBet) {
		winnerBets.add(winnerBet);
	}

	public void removeBet(WinnerBet winnerBet) {
		winnerBets.remove(winnerBet);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
