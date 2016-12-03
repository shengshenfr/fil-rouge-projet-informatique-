/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.HashSet;

import Betting.Exceptions.BadParametersException;
import Interface.Competitor;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Entry.
 * 
 * @author Robin
 */
public class Entry {
	/**
	 * Description of the property podiumBets.
	 */
	public HashSet<PodiumBet> podiumBets = new HashSet<PodiumBet>();

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

	/**
	 * The constructor.
	 * @throws BadParametersException 
	 */
	public Entry(Competition competition, Competitor competitor) throws BadParametersException {
		if (competition == null) {
			throw new BadParametersException("The competition cannot be null!");
		}
		this.competition = competition;

		if (competitor == null) {
			throw new BadParametersException("The competitor cannot be null!");
		}
		this.competitor = competitor;
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

}
