package Individualtest;

import static org.junit.Assert.*;

import org.junit.Test;

import Individual.Subscriber;
import exceptions.AuthentificationException;
import exceptions.BadParametersException;
import exceptions.CantBetException;
import exceptions.MissingTokenException;
import utils.MyCalendar;
import Individual.UnregistedCompetitor;
public class UnregisteredCompetitortest {
	public static void main(String[] args) throws BadParametersException{
		testConstructeur();
		
	}
	@Test
	public static void testConstructeur() throws BadParametersException{
		//test UnregisteredCompetitor.UnregisteredCompetitor///////////////
		UnregistedCompetitor uc= new UnregistedCompetitor("SYLVIE");
		assertTrue(uc.getUnregistedSubscriberName().equals("SYLVIE"));
        ////////////////////////////////////////////
	}


}
