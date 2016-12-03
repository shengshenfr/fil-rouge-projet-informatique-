/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

package Bet;

import Interface.Competitor;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

import Bet.Entry;
import Betting.Exceptions.BadParametersException;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Bet.
 * 
 * @author Rémy
 */
public class TestEntry {
	
	private Competition competition;
	private Competitor competitor;
	
	@Before
	public void init() throws BadParametersException {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		competition = new Competition("Competition", tomorrow);
		
		competitor = new Competitor() {
			 
		};
	}
	
	@Test
	public void testCreation() throws BadParametersException {
		Entry entry = new Entry(competition, competitor);
	}
	
	@Test(expected = BadParametersException.class)
	public void testCreationNoCompetition() throws BadParametersException {
		Entry entry = new Entry(null, competitor);
	}
	
	@Test(expected = BadParametersException.class)
	public void testCreationNoCompetitor() throws BadParametersException {
		Entry entry = new Entry(competition, null);
	}
	
}
