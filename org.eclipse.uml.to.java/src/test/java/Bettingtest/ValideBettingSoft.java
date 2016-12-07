package Bettingtest;

import java.sql.SQLException;

import exceptions.*;


public class ValideBettingSoft {
	
	
	static TestBettingSoft test = new TestBettingSoft();
	private static String MANAGER_PASSWORD = "root";	
	
	
	public static void main(String[] args) throws BadParametersException, AuthentificationException, ExistingSubscriberException, SubscriberException, CompetitionException, ExistingCompetitionException, ExistingCompetitorException, SQLException {
		// TODO Auto-generated method stub
		test.init();
		test.testSubscriber();
		test.testUnsubscriber();
		test.testCreditSubscriber();
		test.testDebitSubscriber();
		test.testListCompetitors();
		test.testListCompetitions();
		test.testBetOnWinner();
		test.testSettleWinner();
		test.testBetOnPodium();
		test.testSettlePodium();
	}

}
