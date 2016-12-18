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
import dbManager.EntryManager;
import dbManager.PlayerManager;

/**
 * An entry represents a participation of a competitor within a competition
 * 
 * @author Remy
 */
public class Entry {
	/**
	 * The counter of the entry index 
	 */
	static private int nextId = 0;

	/**
	 * The index of the entry
	 */
	private int id = 0;

	/**
	 * The podium bets associated with this entry
	 */
	private HashSet<PodiumBet> podiumBets = new HashSet<PodiumBet>();

	/**
	 * The winner bets associated with this entry
	 */
	private HashSet<WinnerBet> winnerBets = new HashSet<WinnerBet>();

	/**
	 * The rank obtained by the competitor once the results have been revealed.
	 */
	private Rank rank = null;

	/**
	 * The given competitor
	 */
	private Competitor competitor = null;
	
	/**
	 * The Competition in which the competitor participates
	 */
	private Competition competition = null;

	/**
	 * Constructor
	 * @param competition in which the competitor participates
	 * @param competitor the competitor
	 * @param id the index of the entry
	 * @param rank the final rank of this participation
	 * @throws BadParametersException
	 */
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

	}

	/**
	 * Constructor
	 * @param competition in which the competitor participates
	 * @param competitor the competitor
	 * @throws BadParametersException
	 */
	public Entry(Competition competition, Competitor competitor) throws BadParametersException {
		this(competition, competitor, ++nextId, Rank.NOT_PODIUM);
	}

	static public Entry createEntry(int id, String competitionName, String competitorName, int rank)
			throws BadParametersException, MissingCompetitionException, SQLException {
		Competition competition = CompetitionManager.findBycompetitionName(competitionName);
		Competitor competitor = PlayerManager.findByName(competitorName);
		Rank rankObject = Rank.getRankIndex(rank);
		return new Entry(competition, competitor, id, rankObject);
	}
	
	/**
	 * Saves the object to the database
	 */
	protected void save() {
		try {
			EntryManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether the competition associated with this entry is already over or not
	 * @throws CompetitionException
	 */
	public void checkCompetitionNotOver() throws CompetitionException {
		if (this.competition.isOver()) {
			throw new CompetitionException("Competition is already over!");
		}
	}
	
	/**
	 * Cancel the participation and all the bets associated
	 * @throws CompetitionException
	 */
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
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
	 * @return the podium bets done on this entry
	 */
	public HashSet<PodiumBet> getPodiumBets() {
		return this.podiumBets;
	}

	/**
	 * @return the winner bets done on this entry
	 */
	public HashSet<WinnerBet> getWinnerBets() {
		return this.winnerBets;
	}

	/**
	 * @return get all the bets done on this entry
	 */
	public HashSet<Bet> getBets() {
		HashSet<Bet> bets = new HashSet<Bet>();
		bets.addAll(podiumBets);
		bets.addAll(winnerBets);
		return bets;
	}

	/**
	 * @return the rank of the given entry
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Sets the rank of this entry
	 * @param rank the new rank
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * @return the competition where the competitor participates
	 */
	public Competition getCompetition() {
		return competition;
	}

	/**
	 * Sets the competition for this entry
	 * @param competition
	 * @throws CompetitionException
	 */
	public void setCompetition(Competition competition) throws CompetitionException {
		this.competition = competition;
	}

	/**
	 * @return the competitor concerned
	 */
	public Competitor getCompetitor() {
		return competitor;
	}

	/**
	 * Sets the competitor
	 * @param competitor
	 * @throws CompetitionException
	 */
	public void setCompetitor(Competitor competitor) throws CompetitionException {
		this.competitor = competitor;
	}

	/**
	 * Register the given podiumBet in the entry
	 * @param podiumBet
	 * @throws CompetitionException
	 */
	public void addBet(PodiumBet podiumBet) throws CompetitionException {
		podiumBets.add(podiumBet);
	}

	/**
	 * Unregister the given podiumBet from the entry
	 * @param podiumBet
	 * @throws CompetitionException
	 */
	public void removeBet(PodiumBet podiumBet) throws CompetitionException {
		podiumBets.remove(podiumBet);
	}

	/**
	 * Register the given winnerBet in the entry
	 * @param winnerBet
	 * @throws CompetitionException
	 */
	public void addBet(WinnerBet winnerBet) throws CompetitionException {
		winnerBets.add(winnerBet);
	}

	/**
	 * Unregister the given winnerBet from the entry
	 * @param podiumBet
	 * @throws CompetitionException
	 */
	public void removeBet(WinnerBet winnerBet) throws CompetitionException {
		winnerBets.remove(winnerBet);
	}

	/**
	 * @return the index of this entry
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the index of this entry
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
