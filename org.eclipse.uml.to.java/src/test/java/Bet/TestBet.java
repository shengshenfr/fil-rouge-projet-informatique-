/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

package Bet;

import Interface.Competitor;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import Bet.Entry;
import Betting.Exceptions.BadParametersException;
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
	private Competitor competitorA, competitorB, competitorC;
	private Entry entryA, entryB, entryC;
	private Subscriber subscriber;
	
	@Before
	public void init() throws BadParametersException {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		competition = new Competition("Competition", tomorrow);
		
		competitorA = new Competitor() {};
		competitorB = new Competitor() {};
		competitorC = new Competitor() {};
		
		entryA = new Entry(competition, competitorA);
		entryB = new Entry(competition, competitorB);
		entryC = new Entry(competition, competitorC);
		
		subscriber = new Subscriber("abob", "Alice", "Bob", new Date(2016, 16, 16), 100);
	}
	
	@Test
	public void testBet() {
		Bet drawBet = new DrawBet(1, subscriber, competition);
		Bet winnerBet = new WinnerBet(1, subscriber, entryA);
		Bet podiumBet = new PodiumBet(1, subscriber, entryA, entryB, entryC);
		
		assert(competition.getTotalToken() == 3);
	}
	
}
