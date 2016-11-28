/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Betting;

// Start of user code (user defined imports)
import java.sql.*;
import java.util.*;
import Interface.*;
import Betting.Manager;
import Individual.*;
import Bet.*;
import dbManager.*;
import Betting.Exceptions.*;
import utils.*;
// End of user code

/**
 * Description of BettingSoft.
 * 
 * @author Robin.
 */
public class BettingSoft implements Betting {

	
	private final String MANAGER_PASSWORD = "root";

	//-----------------------Methods------------------------------\\

	/**
	 * Constructor for bet Bettingsoft 
	 * @throws SQLException 
	 */
	public BettingSoft() throws BadParametersException{
		
	}

	//-----------------------Attributes------------------------------\\
	/**
	 * @uml.property  name="manager"
	 */
	public Manager manager=null;

	//-----------------------Methods------------------------------\\
	/**
	 *  create a manager
	 * @param username
	 *            the name of manager.
	 * @param managerPwd
	 *            the name of manager.
	 * @throws ExistingCompetitorException  
	 */
	public void createManager(String manager_username,String managerPwd) throws BadParametersException {
		// manager should exist
		if (manager!=null)
			throw new BadParametersException("manager is existed");
		 this.manager.setUsername(manager_username);
		 this.manager.setPassword(managerPwd);
	}

	@Override
	public void addCompetition(String competition, Calendar closingDate, Collection<Competitor> competitors,
			String managerPwd) throws AuthentificationException, BadParametersException, CompetitionException,
			ExistingCompetitionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException, BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void authenticateMngr(String managerPwd) throws AuthentificationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void betOnPodium(long numberTokens, String competition, Competitor winner, Competitor second,
			Competitor third, String username, String pwdSubs) throws AuthentificationException, CompetitionException,
			ExistingCompetitionException, SubscriberException, BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void betOnWinner(long numberTokens, String competition, Competitor winner, String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException,
			BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelCompetition(String competition, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeSubsPwd(String username, String newPwd, String currentPwd)
			throws AuthentificationException, BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> consultBetsCompetition(String competition) throws ExistingCompetitionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Competitor> consultResultsCompetition(String competition) throws ExistingCompetitionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Competitor createCompetitor(String name, String managerPwd)
			throws AuthentificationException, BadParametersException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Competitor createCompetitor(String lastName, String firstName, String borndate, String managerPwd)
			throws AuthentificationException, BadParametersException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creditSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException, BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debitSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException, SubscriberException, BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBetsCompetition(String competition, String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompetition(String competition, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> infosSubscriber(String username, String pwdSubs) throws AuthentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<String>> listCompetitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Competition> listCompetitors(String competition)
			throws ExistingCompetitionException, CompetitionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<String>> listSubscribers(String managerPwd) throws AuthentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settlePodium(String competition, Competitor winner, Competitor second, Competitor third,
			String managerPwd) throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void settleWinner(String competition, Competitor winner, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String subscribe(String lastName, String firstName, String username, String borndate, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException, SubscriberException, BadParametersException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long unsubscribe(String username, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException {
		// TODO Auto-generated method stub
		return 0;
	}


}
