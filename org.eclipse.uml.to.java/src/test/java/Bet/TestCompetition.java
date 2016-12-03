/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

package Bet;

import Interface.Competitor;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
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
public class TestCompetition {
	
	private static Calendar yesterday, tomorrow;
	
	@BeforeClass
	public static void init() {
		yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
	}
	
	@Test
	public void testCreation() throws BadParametersException {
		Competition competition = new Competition("Competition 1", tomorrow);
	}
	
	@Test(expected = BadParametersException.class)
	public void testCreationPastDate() throws BadParametersException {
		Competition competition = new Competition("Competition 2", yesterday);
	}
	
	@Test(expected = BadParametersException.class)
	public void testCreationEmptyName() throws BadParametersException {
		Competition competition = new Competition("", tomorrow);
	}
	
}
