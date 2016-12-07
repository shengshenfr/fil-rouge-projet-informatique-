package Individualtest;

import static org.junit.Assert.*;

import org.junit.Test;

import Individual.Subscriber;
import exceptions.AuthentificationException;
import exceptions.BadParametersException;
import exceptions.CantBetException;
import exceptions.MissingTokenException;
import Individual.Player;
import utils.MyCalendar;

public class Playertest {
	public static void main(String[] args) throws BadParametersException{
		testConstructeur();
		
	}
	@Test
	public static void testConstructeur() throws BadParametersException{
		//test UnregisteredCompetitor.UnregisteredCompetitor///////////////
		Player uc= new Player("SYLVIE","POPO","puupu","19/06/95");
		assertTrue(uc.getUserName().equals("SYLVIE"));
        ////////////////////////////////////////////
	}


}
