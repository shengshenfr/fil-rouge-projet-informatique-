/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

package Bet;

import Interface.Competitor;

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
import exceptions.ExistingCompetitorException;
import exceptions.MissingCompetitorException;
import Individual.Subscriber;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Bet.
 * 
 * @author Rémy
 */
public class TestBet {
	
	private Competition competition;
	private Competitor competitorA, competitorB, competitorC, competitorD;
	private Entry entryA, entryB, entryC, entryD;
	private Subscriber subscriber;
	
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
	}
	
	@Test
	public void testBet() throws MissingCompetitorException {
		assert(competition.getBets().size() == 0);
		assert(competition.getEntries().size() == 4);
		
		Bet drawBet = new DrawBet(1, subscriber, competition);
		Bet winnerBet = new WinnerBet(2, subscriber, entryA);
		Bet podiumBet = new PodiumBet(3, subscriber, entryA, entryB, entryC);
		
		assert(competition.getBets().size() == 3);
		
		assert(competition.getTotalToken() == 6);
	}
	
}
