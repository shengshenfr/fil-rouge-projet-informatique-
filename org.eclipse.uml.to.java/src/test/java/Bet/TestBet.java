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
	private Competitor competitor;
	private Entry entry;
	private Subscriber subscriber;
	
	@Before
	public void init() throws BadParametersException {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		competition = new Competition("Competition", tomorrow);
		
		competitor = new Competitor() {
			 
		};
		
		entry = new Entry(competition, competitor);
		
		subscriber = new Subscriber("abob", "Alice", "Bob", new Date(2016, 16, 16), 100);
	}
	
	@Test
	public void testBet() {
		DrawBet drawBet = new DrawBet(10, subscriber, competition);
	}
	
}
