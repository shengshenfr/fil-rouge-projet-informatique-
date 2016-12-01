/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Betting;

// Start of user code (user defined imports)
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);
			Competition comp = CompetitionManager.findByName(competition);
			//Check if the competition has not been in the system
			if (comp != null)
				throw new ExistingCompetitionException("Competition "+competition+" is already in the system");			

			//Check if the closing date is in the future
			Calendar today = new GregorianCalendar();
			if (closingDate == null) 
				throw new BadParametersException();
			if(closingDate.before(today))
				throw new CompetitionException ("The closedate should be after today ");

			
			//Create a competition
			Competition c = new Competition(competition, closingDate);	

			// Add competiton to SQL
			CompetitionManager.persist(c);
		}
		catch (SQLException e) {
			try {
				System.out.println("competition not added");
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
			
		}
		
	}

	@Override
	public void addCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			Competition c = CompetitionManager.findByName(competition);
			//check if the competition is existed
			if (c == null)
				throw new ExistingCompetitionException("Competition is not exist");

			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			if (c.getclosedate_calendar().before(today))
				throw new CompetitionException ("This competition is already close");			

		
			c.add_competitor(competitor);

			//Add to SQL
			CompetitionManager.addCompetitor(c, competitor);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	@Override
	public void authenticateMngr(String managerPwd) throws AuthentificationException {
		if (!managerPwd.equals(MANAGER_PASSWORD))
			throw new AuthentificationException("The manager's password is incorrect.");
	}

	@Override
	public void betOnPodium(long numberTokens, String competition, Competitor winner, Competitor second,
			Competitor third, String username, String pwdSubs) throws AuthentificationException, CompetitionException,
			ExistingCompetitionException, SubscriberException, BadParametersException {
		try{
			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber  username = "+ username + " is not exist");


			//find competition
			Competition c = CompetitionManager.findByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");

			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot add your bet");

			//check if the competitors participate in this competition
			if (!(c.getCompetitors().contains(winner)))
				throw new CompetitionException("The competitor 1 isn't in the competition!");
			if (!(c.getCompetitors().contains(second)))
				throw new CompetitionException("The competitor 2 isn't in the competition!");
			if (!(c.getCompetitors().contains(third)))
				throw new CompetitionException("The competitor 3 isn't in the competition!");
		
			//Check if the subscriber is not a competitor in this competition
			for (Competitor competitor : c.getCompetitors()) {
				//if the competitor is a player => check if the lastname of subscriber is equal with the name of competitor
				if (competitor instanceof Player){
					if (subscriber.equalsPlayer(competitor))
						throw new CompetitionException("You are a player in the competition " + competition + " so you can not bet in this competition");
				}
				//if the competitor is a team => check if the lastname of subscriber is equal with name of any competitor in this team
				else if (competitor instanceof Team){
					ArrayList<Competitor> competitors = CompetitorManager.findCompetitorinTeam(((Team) competitor).getTeamName());
					for (Competitor competitor_temp : competitors) {
						if (subscriber.equalsPlayer(competitor_temp))
							throw new CompetitionException("You are a player in the team " +((Team) competitor).getTeamName()+ " who participates in the competition "
									+ competition + " so you can not bet in this competition");
					}
				}
			}

			//All condition passed, create a bet
			Bet bet = new Bet(username,c.getName_competition(),numberTokens,winner,second,third);

			//Debit subscriber 
			subscriber.debit(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			BetManager.persist(bet);
		}
		catch ( ExistingSubscriberException | NotExistingCompetitorException| SQLException e){
			if (e instanceof ExistingSubscriberException)
				throw new AuthentificationException();
			if (e instanceof ExistingCompetitorException)
				throw new CompetitionException();
		}
	}

	@Override
	public void betOnWinner(long numberTokens, String competition, Competitor winner, String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException,
			BadParametersException {
		try{
			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");

			//find competition
			Competition c = CompetitionManager.findByName(competition);
			if (competition == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");

			//Check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot add your bet");

			//Check if the winner participates in this competition 
			if (!(c.getCompetitors().contains(winner)))
				throw new CompetitionException("The competitor isn't in the competition!");
			
			//Check if the subscriber is not a competitor in this competition
			for (Competitor competitor : c.getCompetitors()) {
				//if the competitor is a player => check if the lastname of subscriber is equal with the name of competitor
				if (competitor instanceof Player){
					if (subscriber.equalsPlayer(competitor))
						throw new CompetitionException("You are a player in the competition " + competition + " so you can not bet in this competition");
				}
				//if the competitor is a team => check if the lastname of subscriber is equal with name of any competitor in this team
				else if (competitor instanceof Team){
					ArrayList<Competitor> competitors = CompetitorManager.findCompetitorinTeam(((Team) competitor).getTeamName());
					for (Competitor competitor_temp : competitors) {
						if (subscriber.equalsPlayer(competitor_temp))
							throw new CompetitionException("You are a player in the team " +((Team) competitor).getTeamName()+ " who participates in the competition "
									+ competition + " so you can not bet in this competition");
					}
				}
			}

			//All condition passed, create a bet
			Bet bet = new Bet(username,c.getName_competition(),numberTokens,winner);

			//Debit subscriber 
			subscriber.debit(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			BetManager.persist(bet);
		}
		catch ( ExistingSubscriberException | NotExistingCompetitorException| SQLException e){
			if (e instanceof ExistingSubscriberException)
				throw new AuthentificationException();
			if (e instanceof ExistingCompetitorException)
				throw new CompetitionException();
		}
	}

	
	public void betOnDraw(long numberTokens, String competition,  String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException,
			BadParametersException {
		try{
			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");


			//find competition
			Competition c = CompetitionManager.findByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");

			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot add your bet");


			//check if the subscriber is not a competitor in this competition
			for (Competitor competitor : c.getCompetitors()) {
				//if the competitor is a player => check if the lastname of subscriber is equal with the name of competitor
				if (competitor instanceof Player){
					if (subscriber.equalsPlayer(competitor))
						throw new CompetitionException("You are a player in the competition " + competition + " so you can not bet in this competition");
				}
				//if the competitor is a team => check if the lastname of subscriber is equal with name of any competitor in this team
				else if (competitor instanceof Team){
					ArrayList<Competitor> competitors = CompetitorManager.findPlayerinTeam(((Team) competitor).getTeamName());
					for (Competitor competitor_temp : competitors) {
						if (subscriber.equalsPlayer(competitor_temp))
							throw new CompetitionException("You are a player in the team " +((Team) competitor).getTeamName()+ " who participates in the competition "
									+ competition + " so you can not bet in this competition");
					}
				}
			}

			//All condition passed, create a bet
			Bet bet = new Bet(username,c.getName_competition(),numberTokens);

			//Debit subscriber 
			subscriber.debit(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			BetManager.persist(bet);
		}
		catch ( ExistingSubscriberException | NotExistingCompetitorException| SQLException e){
			if (e instanceof ExistingSubscriberException)
				throw new AuthentificationException();
			if (e instanceof ExistingCompetitorException)
				throw new CompetitionException();
		}
	}
	@Override
	public void cancelCompetition(String competition, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		try{
			authenticateMngr(managerPwd);
			Competition c = CompetitionManager.findByName(competition);
			//check if the competition is existed
			if (c == null)
				throw new NotExistingCompetitionException("Competition " + competition + " is not exist.");
			
			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			if (c.getclosedate_calendar().before(today))
				throw new CompetitionException ("This competition is already close");
			
			ArrayList<Bet> bets = (ArrayList<Bet>) BetManager.findAllByCompetition(competition);
			
			for (Bet bet : bets) {
				this.creditSubscriber(bet.getUsername(), bet.getBalance(), managerPwd);
				BetManager.delete(bet);
			}

			// Delete competition in SQL
			CompetitionManager.deleteCompetition(c);
	}
	catch(BadParametersException | ExistingSubscriberException | NotExistingCompetitionException |SQLException | NotExistingCompetitorException  e){
		System.out.println(e);
	}
	}

	@Override
	public void changeSubsPwd(String username, String newPwd, String currentPwd)
			throws AuthentificationException, BadParametersException {
		try{
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			subscriber.change_subcriber_password(username, currentPwd, newPwd);
			SubscriberManager.update(subscriber);
		}
		catch (SQLException | SubscriberException e){
			System.out.println(e);
		}
	}

	@Override
	public ArrayList<String> consultBetsCompetition(String competition) throws ExistingCompetitionException {
		try{
			
			Competition c = CompetitionManager.findByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");
			//check if the competition is closed
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot get the bets list");

			list<Entry> entryBet = EntryManager.findAllByCompetition(competition);

			ArrayList<String> listEntryBets = new ArrayList<String>();
			
			for (int i=0;i<entryBet.size();i++) {
				listEntryBets.add(entryBet.get(i).toString());
			}
			
			return listEntryBets;
		}
		catch(BadParametersException |  SQLException | NotExistingCompetitorException | CompetitionException e){
			return null;
		}
	}

	@Override
	public ArrayList<Competitor> consultResultsCompetition(String competition) throws ExistingCompetitionException {
		
		ArrayList<Competitor> winners = new ArrayList<Competitor>();
		try{
			Competition c = CompetitionManager.findByName(competition);
			if (c == null){
				throw new ExistingCompetitionException("The competition doesn't exist!");
			}
		
			//check if the competition is closed
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot get the bets list");
		
			List<Competitor> competitors = CompetitorManager.findAllByCompetition(competition);

		
			winners.add(null);
			winners.add(null);
			winners.add(null);
		
			for (int i=0; i<competitors.size();i++){
				Competitor competitor = competitors.get(i);
				int rank=0;
				rank=c.getRankCompetitor(competitor);
				
				if (rank>0 && rank<4){
					winners.add(rank, competitor);
					if (winners.get(rank+1)==null)
						winners.remove(rank+1);
				}
			}
		}
	
		catch(SQLException | BadParametersException | CompetitionException | ExistingCompetitorException e){
			System.out.println(e);
		}
		return winners;
	}

	@Override
	public Competitor createCompetitor(String name, String managerPwd)
			throws AuthentificationException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			//check if the team has already been in the system or not
			if (CompetitorManager.findCompetitorByName(name) != null)
				throw new ExistingCompetitorException("Team " + name + " is already in the system");

			//Check team's name is valid or not is in the method hasValidName of the class CompetitiorEmplement

			//Create a team
			Team team = new Team(name);

			//Persist a team to bdd
			CompetitorManager.persist(team);
			return team;
		}
		catch(ExistingCompetitorException |SQLException e){
			return null;
		}
	}

	@Override
	public Competitor createCompetitor(String lastName, String firstName, String borndate, String managerPwd)
			throws AuthentificationException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);
			
			//check if the competitor has already been in the system or not 
			if (CompetitorManager.findCompetitorByName(lastName) != null)
				throw new ExistingCompetitorException("Team " + lastName + " is already in the system");

			//Check player's name is valid or not is in the method hasValidName of the class CompetitiorEmplement

			//Create competitor
			Player player = new Player(lastName, firstName, dateValide.change_date(borndate));

			CompetitorManager.persist(player);
			return player;
		}
		catch(ExistingCompetitorException | SQLException e){
			System.out.println(e);
			return null;
		}
	}

	@Override
	public void creditSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException, BadParametersException {
			try{
				// Authenticate manager
				authenticateMngr(managerPwd);

				//Check if the subscriber is existed
				Subscriber subscriber = SubscriberManager.findByUsername(username);
				if (subscriber == null)
					throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");

				//Credit subscriber
				subscriber.credit(numberTokens);

				//Update subscriber's information
				SubscriberManager.update(subscriber);
			}
			catch(SQLException |SubscriberException e){
				System.out.println(e);
			}
	}

	@Override
	public void debitSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException, SubscriberException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			//Check if the subscriber is existed
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");

			//Debit subscriber
			subscriber.debit(numberTokens);	

			//Update subscriber's information
			SubscriberManager.update(subscriber);
		}
		catch(SQLException e){
			System.out.println(e);
		}

	}

	@Override
	public void deleteBetsCompetition(String competition, String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException {
		try{

			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			Competition c = CompetitionManager.findByName(competition);
			//Check if the competition is exist
			if (c == null)
				throw new ExistingCompetitionException("Competition " + competition +" is not exist.");

			//Check if the old competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed");

			//Get bets of subscriber
			List<Bet> bets = BetManager.findByUsername(username);

			for (Bet bet : bets) {
				if (bet.get_competition() == c.getName_competition()){
					//recredit tokens to subscriber
					subscriber.credit(bet.getBalance());
					//delete bet form DB
					BetManager.delete(bet);
				}
			}

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);
		}
		catch (NotExistingCompetitorException | BadParametersException | SQLException | SubscriberException e){
			System.out.println(e);
		}
	}

	@Override
	public void deleteCompetition(String competition, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		try {
			
			// Authenticate manager
			authenticateMngr(managerPwd);
			//Check if the competition is exist
			Competition c = CompetitionManager.findByName(competition);

			if (c == null){
				throw new ExistingCompetitionException("The competition does not exist");
			}
		
			if (c.getclosedate_calendar().after(MyCalendar.getDate())){
				throw new CompetitionException("The competition is not finished yet");
			}

			BetManager.deleleAllBetsOnCompetition(c);

			CompetitionManager.delete(c);
			} 
		
		catch (SQLException | BadParametersException e){
			System.out.println(e);
			}
	}

	@Override
	public void deleteCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			Competition c = CompetitionManager.findByName(competition);
			//check if the competition is existed
			if (c == null)
				throw new ExistingCompetitionException("Competition is not exist");

			//Check if the competition is still open
			Calendar today = new GregorianCalendar();
			if (c.getclosedate_calendar().before(today))
				throw new CompetitionException ("This competition is already close");	

			//Check if the number of competitor left is more than 2 is in the method below
			c.deleteCompetitor(competitor);

			//Delete in SQL
			CompetitionManager.deleteCompetitor(c, competitor);
		}
		catch (BadParametersException | SQLException e){
			System.out.println(e);
		}
	}

	@Override
	public ArrayList<String> infosSubscriber(String username, String pwdSubs) throws AuthentificationException {
		try{
			//Authenticate subscriber is in subscriber.consult_user
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");

			return subscriber.consult_user(username, pwdSubs);
		}
		catch( SQLException | BadParametersException| SubscriberException |ExistingSubscriberException e) {
			return null;
		}
	}

	@Override
	public List<List<String>> listCompetitions() {
		try{
			return CompetitionManager.findAll();
		}
		catch (SQLException | BadParametersException| NotExistingCompetitorException | CompetitionException e){
			return null;
		}
	}

	@Override
	public Collection<Competition> listCompetitors(String competition)
			throws ExistingCompetitionException, CompetitionException {
		try{
			Competition c = CompetitionManager.findByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");
			//check if the competition is closed
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getclosedate_calendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot get the competitors list");

			return c.getCompetitors();
		}
		catch (BadParametersException | SQLException e){
			return null;
		}
	}

	@Override
	

	
	public List<List<String>> listSubscribers(String managerPwd) throws AuthentificationException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			// Create the list of subscribers			
			List<List<String>> result = new ArrayList<List<String>>();
			
			// find all subscriber from SQL
			List<Subscriber> subscriberlist = SubscriberManager.findAll();

			for (Subscriber s : subscriberlist) {
				List<String> subscriber_Data = new ArrayList<String>();
				subscriber_Data.add(s.getLastname());
				subscriber_Data.add(s.getFirstname());
				subscriber_Data.add(s.getUsername());
				subscriber_Data.add(""+s.getBornDate());

				result.add(subscriber_Data);
			}
			return result;
		}
		
		// catch (SQLException | BadParametersException | SubscriberException  e)
		catch (AuthentificationException  e){
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void settlePodium(String competition, Competitor winner, Competitor second, Competitor third,
			String managerPwd) throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			//Check if a competition is existed
			Competition c = CompetitionManager.findByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("The competition "+competition+" is not exist");

			//Check if the competition is closed
			Calendar today = new GregorianCalendar();
			if (c.getclosedate_calendar().after(today))
				throw new CompetitionException ("This competition still open");

			if (!(c.getCompetitors().contains(winner) || c.getCompetitors().contains(second) || c.getCompetitors().contains(third)))
				throw new CompetitionException("the competitors isn't in the competition!");

			
			
			//Find bet podium on this competition
			Collection<Bet> bets_competition = BetManager.findPodiumByCompetition(competition);
			Collection<Bet> bets_competition_podium = new ArrayList<Bet>();
			for (Bet bet : bets_competition) {
				if (bet.get_idBet().equals(Podium.idBet))
					bets_competition_podium.add(bet);
			}

			long sum_balance_winner = 0;
			long sum_balance = 0;

			// if bets_competition is empty => there are no bet on this competition => delete this competition
			if (bets_competition.isEmpty()){
				CompetitionManager.deleteCompetition(c);
			}
			else if (!bets_competition_podium.isEmpty()){
				for (Bet bet : bets_competition_podium) {
					sum_balance += bet.getBalance();
					if ((bet.getWinner().equals(winner))&& (bet.getSecond().equals(second)) && (bet.getThird().equals(third))){
						sum_balance_winner += bet.getBalance();
					}
				}

				if (sum_balance_winner == 0) { //there are no good bet podium => credit all subscriber
					for (Bet bet : bets_competition_podium) {
						this.creditSubscriber(bet.getUsername(), bet.getBalance(), managerPwd);
					}
				}
				else{
					for (Bet bet : bets_competition_podium) {
						if ((bet.getWinner().equals(winner))&& (bet.getSecond().equals(second)) && (bet.getThird().equals(third)))
							this.creditSubscriber(bet.getUsername(), (long) bet.getBalance()*sum_balance/sum_balance_winner, managerPwd);
					}
				}
				//Delete the bets in the DB
				for (Bet bet : bets_competition_podium) {
					BetManager.delete(bet);
				}
				if (BetManager.findPodiumByCompetition(competition).isEmpty())
					//if there are no more bet on this competition, delete this competition in the DB
					CompetitionManager.deleteCompetition(c);
			}
		} catch (ExistingSubscriberException |BadParametersException | SQLException | NotExistingCompetitorException e){
			System.out.println(e);
		}	
	}

	@Override
	public void settleWinner(String competition, Competitor winner, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			//Check if a competition is existed
			Competition c = CompetitionManager.findByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("The competition "+competition+" is not exist");

			//Check if the competition is closed
			Calendar today = new GregorianCalendar();
			if (c.getclosedate_calendar().after(today))
				throw new CompetitionException ("This competition still open");

			if (!(c.getCompetitors().contains(winner)))
				throw new CompetitionException("the competitors isn't in the competition!");

			
			//Find bet winner on this competition
			Collection<Bet> bets_competition = BetManager.findWinnerByCompetition(competition);
			Collection<Bet> bets_competition_winner = new ArrayList<Bet>();
			for (Bet bet : bets_competition) {
				if (bet.get_idBet().equals(Winner.idBet))
					bets_competition_winner.add(bet);
			}

			long sum_balance_winner = 0;
			long sum_balance = 0;

			if (bets_competition.isEmpty()){
				CompetitionManager.deleteCompetition(c);
			}
			else if (!bets_competition_winner.isEmpty()){
				for (Bet bet : bets_competition_winner) {
					sum_balance += bet.getBalance();
					if (bet.getWinner().equals((winner))){
						sum_balance_winner += bet.getBalance();
					}
				}

				if (sum_balance_winner == 0) { //there are no good bet podium => credit all subscriber
					for (Bet bet : bets_competition_winner) {
						this.creditSubscriber(bet.getUsername(), bet.getBalance(), managerPwd);
					}
				}
				else{ 
					for (Bet bet : bets_competition_winner) {
						if (bet.getWinner().equals((winner)))
							this.creditSubscriber(bet.getUsername(), (long) bet.getBalance()*sum_balance/sum_balance_winner, managerPwd);
					}
				}
				//Delete the bets in the DB
				for (Bet bet : bets_competition_winner) {
					BetManager.delete(bet);
				}
				if (BetManager.findWinnerByCompetition(competition).isEmpty())
					//if there are no more bet on this competition, delete this competition in the DB
					CompetitionManager.deleteCompetition(c);
			}
		}
		catch (ExistingSubscriberException |BadParametersException | SQLException | NotExistingCompetitorException e){
				System.out.println(e);
		}
	}

	@Override
	public String subscribe(String lastName, String firstName, String username, String borndate, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException, SubscriberException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			// Look if a subscriber with the same username already exists
			if (SubscriberManager.findByUsername(username) != null)
				throw new ExistingSubscriberException("A subscriber with the same username already exists");
				
			// Create the new subscriber
			// Check if the subscriber is over 18 years old or not is in the method setborndate of Subscriber 
			Subscriber s = new Subscriber(lastName,firstName,username,dateValide.change_date(borndate));

			// Add subscriber to SQL
			SubscriberManager.persist(s);

			return s.getPassword();

		}

		catch (SQLException  e){
			return null;
		}
	}

	@Override
	public long unsubscribe(String username, String managerPwd)
			throws AuthentificationException, ExistingSubscriberException {

		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			// check if a subscriber with the same username exists
			Subscriber s = SubscriberManager.findByUsername(username);
			if (s == null)
				throw new NotExistingSubscriberException("Subcriber " + username + " is not exist.");
			long number_balance=s.getBalance();

			// Delete subscriber from SQL
			SubscriberManager.delete(s);
			return number_balance;
		}
		
		catch (NotExistingSubscriberException | SQLException | BadParametersException | SubscriberException e){
			if (e instanceof NotExistingSubscriberException )
				throw new ExistingSubscriberException();
			return 0;
		}
	}
}
