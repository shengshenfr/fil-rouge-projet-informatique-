package Bettingtest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import Betting.BettingSoft;
import Interface.*;
import org.junit.Test;

import Interface.Competitor;
import exceptions.AuthentificationException;
import exceptions.BadParametersException;
import exceptions.CantBetException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitionException;
import exceptions.MissingTokenException;
import utils.MyCalendar;

public class addingcompetitiontest {
	public static void main(String[] args) throws BadParametersException, AuthentificationException, MissingTokenException, CantBetException, CompetitionException, ExistingCompetitionException{
		test();
		
	}
	@Test
	public static void test() throws AuthentificationException, BadParametersException, CompetitionException, ExistingCompetitionException {
		BettingSoft b=new BettingSoft();
		String MANAGER_PASSWORD = "root";
		b.createManager("bowenx",MANAGER_PASSWORD);
		Competitor c1=b.createCompetitor("yuan","yao","1994-05-02", MANAGER_PASSWORD);
		Competitor c2=b.createCompetitor("bowen","xue","1994-08-04", MANAGER_PASSWORD);
		Collection mycoll=new HashSet();
		mycoll.add(c1);
		mycoll.add(c2);
		Calendar c=new MyCalendar(1989,9,26);
		b.addCompetition("abccompetition", c ,mycoll ,
				MANAGER_PASSWORD);
	}

}
