/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

package Bet;

import Interface.Competitor;import static org.junit.Assert.assertFalse;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Bet.Entry;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.MissingCompetitorException;
import Individual.Subscriber;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Bet.
 * 
 * @author Rémy
 */
public class TestBetSettle {
	
	private Competition competition;
	private Competitor competitorA, competitorB, competitorC, competitorD;
	private Entry entryA, entryB, entryC, entryD;
	private Subscriber subscriber;
	private Bet drawBet, winnerBet1, winnerBet2, podiumBet1, podiumBet2;
	
	@Before
	public void init() throws BadParametersException {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		competition = new Competition("Competition", tomorrow);
		
		competitorA = new TestCompetitor();
		competitorB = new TestCompetitor();
		competitorC = new TestCompetitor();
		competitorD = new TestCompetitor();
		
		entryA = new Entry(competition, competitorA);
		entryB = new Entry(competition, competitorB);
		entryC = new Entry(competition, competitorC);
		entryD = new Entry(competition, competitorD);
		
		subscriber = new Subscriber("balice", "Alice", "Bob", new GregorianCalendar(2016, 16, 16), 100);
		
		assert(competition.getBets().size() == 0);
		assert(competition.getEntries().size() == 4);
		
		drawBet = new DrawBet(1, subscriber, competition);
		winnerBet1 = new WinnerBet(2, subscriber, entryA);
		winnerBet2 = new WinnerBet(3, subscriber, entryB);
		podiumBet1 = new PodiumBet(4, subscriber, entryA, entryB, entryC);
		podiumBet2 = new PodiumBet(5, subscriber, entryB, entryA, entryD);
		
		assert(competition.getBets().size() == 5);
		
		assert(competition.getTotalToken() == 15);
	}
	
	@Test
	public void testSettleBetAllCompetitors() throws MissingCompetitorException, CompetitionException {
		List<Competitor> results = new LinkedList<Competitor>();
		results.add(competitorA);
		results.add(competitorB);
		results.add(competitorC);
		results.add(competitorD);
		competition.settle(results);
		
		assert(entryA.getRank() == Rank.FIRST);
		assert(entryB.getRank() == Rank.SECOND);
		assert(entryC.getRank() == Rank.THIRD);
		assert(entryD.getRank() == Rank.NOT_PODIUM);
		
		assert(!drawBet.isWon());
		assert(winnerBet1.isWon());
		assert(!winnerBet2.isWon());
		assert(podiumBet1.isWon());
		assert(!podiumBet2.isWon());

		assert(competition.getWinnerToken() == 6);
	}
	
	@Test
	public void testSettleBetPodium() throws MissingCompetitorException, CompetitionException {
		List<Competitor> results = new LinkedList<Competitor>();
		results.add(competitorA);
		results.add(competitorB);
		results.add(competitorC);
		competition.settle(results);
		
		assert(entryA.getRank() == Rank.FIRST);
		assert(entryB.getRank() == Rank.SECOND);
		assert(entryC.getRank() == Rank.THIRD);
		assert(entryD.getRank() == Rank.NOT_PODIUM);
		
		assert(!drawBet.isWon());
		assert(winnerBet1.isWon());
		assert(!winnerBet2.isWon());
		assert(podiumBet1.isWon());
		assert(!podiumBet2.isWon());
		
		assert(competition.getWinnerToken() == 6);
	}
	
	@Test
	public void testSettleBetPodium2() throws MissingCompetitorException, CompetitionException {
		List<Competitor> results = new LinkedList<Competitor>();
		results.add(competitorB);
		results.add(competitorA);
		results.add(competitorD);
		competition.settle(results);
		
		assert(entryB.getRank() == Rank.FIRST);
		assert(entryA.getRank() == Rank.SECOND);
		assert(entryD.getRank() == Rank.THIRD);
		assert(entryC.getRank() == Rank.NOT_PODIUM);
		
		assert(!drawBet.isWon());
		assert(!winnerBet1.isWon());
		assert(winnerBet2.isWon());
		assert(!podiumBet1.isWon());
		assert(podiumBet2.isWon());
		
		assert(competition.getWinnerToken() == 8);
	}
	
	@Test
	public void testSettleDraw() throws MissingCompetitorException, CompetitionException {
		competition.settleDraw();
		
		assert(competition.isDraw());
		
		assert(drawBet.isWon());
		assert(!winnerBet1.isWon());
		assert(!winnerBet2.isWon());
		assert(!podiumBet1.isWon());
		assert(!podiumBet2.isWon());
		
		assert(competition.getWinnerToken() == 1);
	}
	
}
