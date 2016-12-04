package Bet;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import Interface.*;
import Betting.BettingSoft;
import Betting.Manager;
import Individual.*;
import Bet.*;
import dbManager.*;
import exceptions.*;
import utils.*;

/**
 * 
 * @author shen sheng
 * @param <Competitor>
 * 
 */
public class TestBettingSoft {
	
	private static String MANAGER_PASSWORD = "root";	
	private static BettingSoft Bsport = new BettingSoft(); 
	
	private static Calendar yesterday, tomorrow;
	
	@BeforeClass
	public static void init() {
		yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
	}
	

	public static void main(String[] args) throws AuthentificationException,
	ExistingSubscriberException, SubscriberException, 
	BadParametersException, SQLException, ExistingCompetitorException, CompetitionException, ExistingCompetitionException{
		
				
		System.out.println("\n***************************************************\n");
		
		//manager
		System.out.print("***********test create manager**********8");
		Bsport.createManager("filrouge", MANAGER_PASSWORD);
		
		
		System.out.println("\n***************************************************\n");
		
		//Subscribers
		System.out.print("***********test Suscriber**********8");
		Bsport.subscribe("sheng", "shen", "wocaojava", "11/11/1111", MANAGER_PASSWORD);
		System.out.print(".");
				
		Bsport.subscribe("hehe", "what", "doubi", "01/01/2001", MANAGER_PASSWORD);
		System.out.print(".");
		for (List<String> s : Bsport.listSubscribers(MANAGER_PASSWORD)){
			System.out.println(s);
		}
		
		
		System.out.println("\n***************************************************\n");
		
		
		System.out.print("***********test unSuscriber**********");
		Bsport.unsubscribe("wocaojava", MANAGER_PASSWORD);
		
		for (List<String> s : Bsport.listSubscribers(MANAGER_PASSWORD)){
			System.out.println(s);
		}
		
		System.out.println("\n***************************************************\n");
		
		System.out.print("***********test credit Subscriber**********");
		
		System.out.print("doubi balance is: ");
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		
		Bsport.creditSubscriber("doubi",1000, MANAGER_PASSWORD);
		
		
		System.out.println("\n***************************************************\n");
		
		
		System.out.print("***********test debit Subscriber**********");
		
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		
		Bsport.debitSubscriber("doubi",200, MANAGER_PASSWORD);
		
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		System.out.println("\n***************************************************\n");
		
		//competition
		System.out.print("***********test create competition**********");
		
		Competition competition1 = new Competition("Competition 1", tomorrow);
		
		Competition competition2 = new Competition("Competition 2", yesterday);
	
		for (List<String> s : Bsport.listCompetitions()){
			System.out.println(s);

		}
		System.out.println("\n***************************************************\n");
		//competitor
		System.out.print("test Creating competitors...");
		
		Bsport.createCompetitor("zhao", "1", "11/11/1111", MANAGER_PASSWORD);
		System.out.print(".");
		Bsport.createCompetitor("qian", "2","11/11/1111", MANAGER_PASSWORD);
		System.out.print(".");
		
		System.out.println("\n***************************************************\n");
		
		
		//teams
		System.out.print("test Creating teams...");
		Bsport.createCompetitor("Team1", MANAGER_PASSWORD);
		System.out.print(".");
		Bsport.createCompetitor("Team2", MANAGER_PASSWORD);
		System.out.print(".");
		
		System.out.println("\n***************************************************\n");
		
	
		//add competition
		System.out.print("test add competition...");
		ArrayList<Competitor> competitors = new ArrayList<Competitor>();
		competitors = new ArrayList<Competitor>(Arrays.asList(
				(Competitor) CompetitorManager.findCompetitorByName("qian")));
		Bsport.addCompetition("basket", new MyCalendar(2017,12,5), competitors, MANAGER_PASSWORD);
		
		System.out.println("Competitions:\n");
		
		for (List<String> comp : Bsport.listCompetitions()){
			
			System.out.print("#");
			
			for (String s : comp){
				System.out.println(s);
			}
			System.out.println("\n");
		}
		System.out.println("\n***************************************************\n");
		
		
		
		
		
		
		
	}
}