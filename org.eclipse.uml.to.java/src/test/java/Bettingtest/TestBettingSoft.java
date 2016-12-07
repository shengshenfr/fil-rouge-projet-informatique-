package Bettingtest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import Interface.*;
import Betting.BettingSoft;

import Individual.*;
import Bet.*;
import dbManager.*;
import exceptions.*;
import utils.*;

/**
 * 
 * @author shen sheng
 * 
 * 
 */
public class TestBettingSoft {
	//definir the attributs
	private static String MANAGER_PASSWORD = "root";	
	private static BettingSoft Bsport = new BettingSoft(); 
	
	private static Calendar yesterday, tomorrow;
	
	private Competition competition1,competition2;
	private Competitor  competitor3, competitor4,competitor5;
	private Subscriber subscriber;

	//Initializer 
	@BeforeClass
	public void init() throws BadParametersException, AuthentificationException, ExistingSubscriberException, SubscriberException, CompetitionException, ExistingCompetitionException, ExistingCompetitorException, SQLException{
		yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		Bsport.subscribe("sheng", "shen", "wocaojava", "11/11/1111", MANAGER_PASSWORD);
		Bsport.subscribe("chen", "long", "Jackie", "10/01/1970", MANAGER_PASSWORD);
		Bsport.subscribe("hehe", "what", "doubi", "01/01/2001", MANAGER_PASSWORD);
		
		Bsport.creditSubscriber("doubi",1000, MANAGER_PASSWORD);
		Bsport.creditSubscriber("Jackie",2000, MANAGER_PASSWORD);
		
		competition1 = new Competition("Competition 1", tomorrow);
	    competition2 = new Competition("Competition 2", yesterday);
		
		Bsport.createCompetitor("zhao", "yi", "01/11/1111", MANAGER_PASSWORD);
		Bsport.createCompetitor("qian", "er","02/11/1111", MANAGER_PASSWORD);
		Bsport.createCompetitor("sun", "san","03/11/1111", MANAGER_PASSWORD);
		Bsport.createCompetitor("li", "si","04/11/1111", MANAGER_PASSWORD);
		Bsport.createCompetitor("zhou", "wu","05/11/1111", MANAGER_PASSWORD);
		
		Bsport.createCompetitor("Team1", MANAGER_PASSWORD);
		Bsport.createCompetitor("Team2", MANAGER_PASSWORD);
		

		
		ArrayList<Competitor> competitor1 = new ArrayList<Competitor>();
		competitor1 = new ArrayList<Competitor>(Arrays.asList(
				(Competitor) PlayerManager.findByName("zhao")));
		Bsport.addCompetition("competition3", new MyCalendar(2017,12,5), competitor1, MANAGER_PASSWORD);
		
		Competitor competitor2 = (Competitor) PlayerManager.findByName("qian");
		Bsport.addCompetitor("competition1",competitor2,MANAGER_PASSWORD);
		
		competitor3 = PlayerManager.findByName("sun");
		competitor4 = PlayerManager.findByName("li");
		competitor5 = PlayerManager.findByName("zhou");
			
		
	}
	
	//testSubscriber
	public void testSubscriber() throws BadParametersException, AuthentificationException{
		for (List<String> s : Bsport.listSubscribers(MANAGER_PASSWORD)){
			System.out.println(s);
		}
	
	}
	
	//testUnsubscriber
	public void testUnsubscriber() throws AuthentificationException, ExistingSubscriberException{
		Bsport.unsubscribe("wocaojava", MANAGER_PASSWORD);
		
		for (List<String> s : Bsport.listSubscribers(MANAGER_PASSWORD)){
			System.out.println(s);
		}
		//assert(subscriber.getUsername()=="doubi");
	}
	
	//testCreditSubscriber
	public void testCreditSubscriber() throws SQLException, AuthentificationException, ExistingSubscriberException, SubscriberException, BadParametersException, CompetitionException, ExistingCompetitorException{
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		
		Bsport.creditSubscriber("doubi",200, MANAGER_PASSWORD);
		
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		//assert((SubscriberManager.findByUsername("wdoubi")).getBalance()==1200);
		
	}
	
	//testDebitSubscriber
	public void testDebitSubscriber() throws SQLException, AuthentificationException, ExistingSubscriberException, SubscriberException, BadParametersException, CompetitionException, ExistingCompetitorException{
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		
		Bsport.debitSubscriber("doubi",200, MANAGER_PASSWORD);
		
		System.out.println((SubscriberManager.findByUsername("wdoubi")).getBalance());
		//assert((SubscriberManager.findByUsername("wdoubi")).getBalance()==800);
	}
	
	//testListCompetitors
	public void testListCompetitors() throws ExistingCompetitionException, CompetitionException{
		for (List<String> s : Bsport.listCompetitors("competition1")){
			System.out.println(s);

		}
	}
	
	//testListCompetitions
	public void testListCompetitions(){
		for (List<String> comp : Bsport.listCompetitions()){
			
			System.out.print("#");
			
			for (String s : comp){
				System.out.println(s);
			}
			System.out.println("\n");
		}
		
	}

	//testBetOnWinner
	public void testBetOnWinner() throws SQLException, AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException, BadParametersException, ExistingCompetitorException{
		
		//subscriber 1 bet on "zhao"
		Subscriber subscriber1 = SubscriberManager.findByUsername("doubi");
		System.out.println(subscriber1.getUsername() +" has "+subscriber1.getBalance()+" tokens.");
		
		Bsport.betOnWinner(100, "competition1", PlayerManager.findByName("zhao"), subscriber1.getUsername(), subscriber1.getPassword());
		
		System.out.println(subscriber1.getUsername() +" has "+subscriber1.getBalance()+" tokens.");
		//subsciber2 bet on "zhao"
		Subscriber subscriber2 = SubscriberManager.findByUsername("Jackie");
		System.out.println(subscriber2.getUsername() +" has "+subscriber2.getBalance()+" tokens.");
		
		Bsport.betOnWinner(500, "competition1", PlayerManager.findByName("zhao"), subscriber2.getUsername(), subscriber2.getPassword());
		
		System.out.println(subscriber2.getUsername() +" has "+subscriber2.getBalance()+" tokens.");
		
		
		//check all the bet on winner
		for (String betCompetition : Bsport.consultBetsCompetition("competition1")){

			System.out.println(betCompetition);
		}
		//assert((SubscriberManager.findByUsername("wdoubi")).getBalance()==900);
		
	}
	
	//testSettleWinner
	public void testSettleWinner() throws AuthentificationException, ExistingCompetitionException, CompetitionException, SQLException, BadParametersException, SubscriberException, ExistingCompetitorException{
		Competitor winner = TeamManager.findByName("competition1");
		Bsport.settleWinner("competition1", winner, MANAGER_PASSWORD);
		for (Subscriber sub : SubscriberManager.findAll()){
			System.out.println(sub.getUsername()+" has now "+sub.getBalance()+" tokens.");
		}
	}
	
	//testBetOnPodium
	public void testBetOnPodium() throws SQLException, AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException, BadParametersException, ExistingCompetitorException{
		Subscriber subscriber1 = SubscriberManager.findByUsername("doubi");
		System.out.println(subscriber1.getUsername() +" has "+subscriber1.getBalance()+" tokens.");
		
		Bsport.betOnPodium(200, "competition2", competitor3, competitor4, competitor5 , subscriber1.getUsername(), subscriber1.getPassword());
		
		//assert((SubscriberManager.findByUsername("wdoubi")).getBalance()==800);
		
		Subscriber subscriber2 = SubscriberManager.findByUsername("Jackie");
		System.out.println(subscriber2.getUsername() +" has "+subscriber2.getBalance()+" tokens.");
		
		Bsport.betOnPodium(500, "competition2", competitor5, competitor4, competitor3 , subscriber2.getUsername(), subscriber2.getPassword());
		
	}
	
	//testSettlePodium
	public void testSettlePodium() throws AuthentificationException, ExistingCompetitionException, CompetitionException, SQLException, BadParametersException, SubscriberException, ExistingCompetitorException{
		
		Bsport.settlePodium("competition2",competitor3, competitor4, competitor5,subscriber.getPassword());
		for (Subscriber sub : SubscriberManager.findAll()){
			System.out.println(sub.getUsername()+" has now "+sub.getBalance()+" tokens.");
		}
	}
	
}
