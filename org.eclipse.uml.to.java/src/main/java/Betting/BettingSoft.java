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
import exceptions.*;
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
	public BettingSoft() {
		
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
			Competition comp = CompetitionManager.findBycompetitionName(competition);
			//Check if the competition has not been in the system
			if (comp != null)
				throw new ExistingCompetitionException("Competition "+competition+" is already in the system");			

			//Check if the closing date is in the future
			Calendar today = new GregorianCalendar();
			if (closingDate == null) 
				throw new BadParametersException("The closedate can't be null");
			if(closingDate.before(today))
				throw new CompetitionException ("The closedate should be after today ");

			
			//Create a competition
			Competition c = new Competition(competition, closingDate);	

			// Add competiton to SQL
			CompetitionManager.persist(c);
		}
		catch (SQLException| MissingCompetitionException e) {
			System.out.println("competition not added");
			
		}
		
	}

	@Override
	public void addCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);

			Competition c = CompetitionManager.findBycompetitionName(competition);
			//check if the competition is existed
			if (c == null)
				throw new ExistingCompetitionException("Competition is not exist");

			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (closingdate.before(today))
				throw new CompetitionException ("This competition is already close");			

		
	
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
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");

			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot add your bet");

			//check if the competitors participate in this competition
			if (!(!EntryManager.findAllByCompetition(competition).contains(winner)))
				throw new CompetitionException("The competitor 1 isn't in the competition!");
			if (!(!EntryManager.findAllByCompetition(competition).contains(second)))
				throw new CompetitionException("The competitor 2 isn't in the competition!");
			if (!(!EntryManager.findAllByCompetition(competition).contains(third)))
				throw new CompetitionException("The competitor 3 isn't in the competition!");
		
		

			//All condition passed, create a bet
		
			PodiumBet podium_bet = new PodiumBet(numberTokens,subscriber,(Entry)winner,(Entry)second,(Entry)third);
			//Debit subscriber 
			subscriber.debitSubscriber(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			PodiumBetManager.persist(podium_bet);
		}
		catch ( ExistingSubscriberException | SQLException |MissingCompetitionException |MissingTokenException e){
			System.out.println(e);
		}
	}

	@Override
	public void betOnWinner(long numberTokens, String competition, Competitor winner, String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException,
			BadParametersException{
		try{
			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			subscriber.authenticate(pwdSubs);
			
			//find competition
			Competition c = CompetitionManager.findBycompetitionName(competition);

			//Check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot add your bet");

			//Check if the winner participates in this competition 
			//List<Entry> entry = EntryManager.findAllByCompetition(competition);
			if (!EntryManager.findAllByCompetition(competition).contains(winner))
				throw new CompetitionException("The competitor isn't in the competition!");
			
			//check if subscriber has enough money
			if (numberTokens>subscriber.getBalance())
				throw new BadParametersException("you do not have enough money");
			
			//All condition passed, create a winner bet
			WinnerBet winner_bet = new WinnerBet(numberTokens,subscriber,(Entry)winner);

			//Debit subscriber 
			subscriber.debitSubscriber(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			WinnerBetManager.persist(winner_bet);
		}
		catch (  SQLException|MissingCompetitionException|MissingTokenException  e){
			System.out.println(e);
		}
	}

	
	public void betOnDraw(long numberTokens, String competition,  String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException, SubscriberException,
			BadParametersException, MissingTokenException {
		try{
			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");


			//find competition
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");

			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot add your bet");


		
			//All condition passed, create a bet
			
			DrawBet draw_bet = new DrawBet(numberTokens,subscriber,c);
			//Debit subscriber 
			subscriber.debitSubscriber(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			DrawBetManager.persist(draw_bet);
		}
		catch ( ExistingSubscriberException | SQLException|MissingCompetitionException e){
			System.out.println(e);
		}
	}
	@Override
	public void cancelCompetition(String competition, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException {
		try{
			authenticateMngr(managerPwd);
			Competition c = CompetitionManager.findBycompetitionName(competition);
			//check if the competition is existed
			if (c == null)
				throw new MissingCompetitionException("Competition " + competition + " is not exist.");
			
			//check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (closingdate.before(today))
				throw new CompetitionException ("This competition is already close");
			
			List<WinnerBet> betOwner = BetManager.findbetOwnerByCompetition(c);
			
			for (WinnerBet bet : betOwner) {
				SubscriberManager.update(bet.getBetOwner(), bet.getAmount(), managerPwd);
				BetManager.delete(bet);
			}

			// Delete competition in SQL
			CompetitionManager.delete(c);
	}
	catch(SQLException |   MissingCompetitionException| ExistingSubscriberException|  BadParametersException e){
		System.out.println(e);
	}
	}

	@Override
	public void changeSubsPwd(String username, String newPwd, String currentPwd)
			throws AuthentificationException, BadParametersException {
		try{
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			subscriber.changeSubsPwd(username, currentPwd, newPwd);
			SubscriberManager.update(subscriber);
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}

	@Override
	public ArrayList<String> consultBetsCompetition(String competition) throws ExistingCompetitionException {
		try{
			
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");
			//check if the competition is closed
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot get the bets list");

			List<Entry> entryBet = EntryManager.findAllByCompetition(competition);

			ArrayList<String> listEntryBets = new ArrayList<String>();
			
			for (int i=0;i<entryBet.size();i++) {
				listEntryBets.add(entryBet.get(i).toString());
			}
			
			return listEntryBets;
		}
		catch(  CompetitionException|   MissingCompetitionException| SQLException e){
			return null;
		}
	}

	@Override
	public ArrayList<Competitor> consultResultsCompetition(String competition) throws ExistingCompetitionException {
		
	
		try{
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null){
				throw new ExistingCompetitionException("The competition doesn't exist!");
			}
		
			//check if the competition is closed
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed, you cannot get the bets list");
			
			
			List<Entry> winners = EntryManager.findAllByCompetition(competition);

			return winners;
		
		}
	
		catch(SQLException | BadParametersException | CompetitionException | ExistingCompetitorException e){
			System.out.println(e);
		}

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
		catch(ExistingCompetitorException e){
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
				subscriber.creditSubscriber(numberTokens);

				//Update subscriber's information
				SubscriberManager.update(subscriber);
			}
			catch(SQLException e){
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
			subscriber.debitSubscriber(numberTokens);	

			//Update subscriber's information
			SubscriberManager.update(subscriber);
		}
		catch(SQLException|MissingTokenException e){
			System.out.println(e);
		}

	}

	@Override
	public void deleteBetsCompetition(String competition, String username, String pwdSubs)
			throws AuthentificationException, CompetitionException, ExistingCompetitionException {
		try{

			//Authenticate subscriber
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			Competition c = CompetitionManager.findBycompetitionName(competition);
			//Check if the competition is exist
			if (c == null)
				throw new ExistingCompetitionException("Competition " + competition +" is not exist.");

			//Check if the old competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.after(closingdate))
				throw new CompetitionException("The competition is closed");

			//Get bets of subscriber
			List<WinnerBet> betwinner = BetManager.findByOwner(username);
			
			for (WinnerBet bet : betwinner) {
				
					//recredit tokens to subscriber
					subscriber.creditSubscriber(bet.getAmount());
					//delete bet form DB
					BetManager.delete(bet);
				
			}

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);
		}
		catch (MissingCompetitionException|  BadParametersException | SQLException e){
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
			Competition c = CompetitionManager.findBycompetitionName(competition);

			if (c == null){
				throw new ExistingCompetitionException("The competition does not exist");
			}
			Calendar closingdate = c.getClosingCalendar();
			if (closingdate.after(MyCalendar.getDate())){
				throw new CompetitionException("The competition is not finished yet");
			}

			BetManager.deleleAllWinnerBetsOnCompetition(c);

			CompetitionManager.delete(c);
			} 
		
		catch (SQLException | MissingCompetitionException e){
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

			Competition c = CompetitionManager.findBycompetitionName(competition);
			//check if the competition is existed
			if (c == null)
				throw new ExistingCompetitionException("Competition is not exist");

			//Check if the competition is still open
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (closingdate.before(today))
				throw new CompetitionException ("This competition is already close");	

	

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
			ArrayList<String> infos = new ArrayList<String>();
			//Authenticate subscriber is in subscriber.consult_user
			Subscriber subscriber = SubscriberManager.findByUsername(username);
			if (subscriber == null)
				throw new ExistingSubscriberException("Subscriber username = "+ username + " is not exist");
			subscriber.authenticate(pwdSubs);
			infos.add(subscriber.getLastname());
			infos.add(subscriber.getFirstname());
			infos.add(subscriber.getBornDate().toString());
			infos.add(Long.toString(subscriber.getBalance()));
			infos.add(username);
		
			
			List<WinnerBet> listBets= BetManager.findByOwner(username);
			
			

		
			for (int i=0; i<listBets.size();i++) {
				WinnerBet bet = listBets.get(i);
				infos.add(bet.toString());
			}
			return infos;
		}
		
		
		catch( SQLException | ExistingSubscriberException e) {
			return null;
		}
	}

	@Override
	public List<List<String>> listCompetitions() {
		List<List<String>> collectionCompetition = new ArrayList<List<String>>();
		try{
			List<Competition>  competitions = CompetitionManager.findAll();
			
			
			for (int i=0; i<competitions.size();i++){
				Competition c = competitions.get(i);
				
				List<String> infoCompetition = new ArrayList<String>();
				
				infoCompetition.add(c.getName());
				infoCompetition.add(c.getClosingCalendar().toString());
								
				List<WinnerBet> betsList = BetManager.findAllSimpleBetsByCompetition(c);
				for (WinnerBet bet : betsList) {
					infoCompetition.add(bet.toString());
				}
								
				ArrayList<Competitor> competitors = c.getCompetitors();
				for (Competitor comptr : competitors) {
					infoCompetition.add(comptr.toString());
				}
				
				collectionCompetition.add(infoCompetition);
			}
		}
		catch (SQLException | BadParametersException e){
			return null;
		}
		return collectionCompetition;
	}

	@Override
	public List<List<String>> listCompetitors(String competition)
			throws ExistingCompetitionException, CompetitionException {

		try{
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");
		List<Entry> competitors = EntryManager.findCompetitorByCompetition(competition);

			return competitors;
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
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("The competition "+competition+" is not exist");

			//Check if the competition is closed
			Calendar today = new GregorianCalendar();
			if (c.getClosingCalendar().after(today))
				throw new CompetitionException ("This competition still open");

			if (!(c.getCompetitors().contains(winner) || c.getCompetitors().contains(second) || c.getCompetitors().contains(third)))
				throw new CompetitionException("the competitors isn't in the competition!");

			
			
			//Find bet podium on this competition
			List<PodiumBet> bets_competition_podium = PodiumBetManager.findAllPodiumBetsByCompetition(c);
		

			long sum_balance_winner = 0;
			long sum_balance = 0;

			// if bets_competition is empty => there are no bet on this competition => delete this competition
			if (bets_competition_podium.isEmpty()){
				CompetitionManager.delete(c);
			}
			else if (!bets_competition_podium.isEmpty()){
				for (Bet bet : bets_competition_podium) {
					sum_balance += bet.getAmount();
					if ((bet.getWinner().equals(winner))&& (bet.getSecond().equals(second)) && (bet.getThird().equals(third))){
						sum_balance_winner += bet.getAmount();
					}
				}

				if (sum_balance_winner == 0) { //there are no good bet podium => credit all subscriber
					for (Bet bet : bets_competition_podium) {
						Subscriber.creditSubscriber(bet.getUsername(), bet.getAmount(), managerPwd);
					}
				}
				else{
					for (Bet bet : bets_competition_podium) {
						if ((bet.getWinner().equals(winner))&& (bet.getSecond().equals(second)) && (bet.getThird().equals(third)))
							Subscriber.creditSubscriber(bet.getUsername(), (long) bet.getAmount()*sum_balance/sum_balance_winner, managerPwd);
					}
				}
				//Delete the bets in the DB
			
					PodiumBetManager.deleleAllPodiumBetsOnCompetition(c);
		
					CompetitionManager.delete(c);
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
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("The competition "+competition+" is not exist");

			//Check if the competition is closed
			Calendar today = new GregorianCalendar();
			if (c.getClosingCalendar().after(today))
				throw new CompetitionException ("This competition still open");

			if (!(c.getCompetitors().contains(winner)))
				throw new CompetitionException("the competitors isn't in the competition!");

			
			//Find bet winner on this competition
			
			
			List<WinnerBet> bets_competition_winner = WinnerBetManager.findAllWinnerBetsByCompetition(c);
			
			
			long sum_balance_winner = 0;
			long sum_balance = 0;

			if (bets_competition_winner.isEmpty()){
				CompetitionManager.delete(c);
			}
			else if (!bets_competition_winner.isEmpty()){
				for (Bet bet : bets_competition_winner) {
					sum_balance += bet.getAmount();
					if (bet.getWinner().equals((winner))){
						sum_balance_winner += bet.getAmount();
					}
				}

				if (sum_balance_winner == 0) { //there are no good bet podium => credit all subscriber
					for (Bet bet : bets_competition_winner) {
						Subscriber.creditSubscriber(bet.getUsername(), bet.getAmount(), managerPwd);
					}
				}
				else{ 
					for (Bet bet : bets_competition_winner) {
						if (bet.getWinner().equals((winner)))
							Subscriver.creditSubscriber(bet.getUsername(), (long) bet.getAmount()*sum_balance/sum_balance_winner, managerPwd);
					}
				}
				//Delete the bets in the DB
			
					WinnerBetManager.deleleAllWinnerBetsOnCompetition(c);

					CompetitionManager.delete(c);
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
			Subscriber s = new Subscriber(lastName,firstName,username,dateValide.change_date(borndate), 0);
			

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
				throw new MissingSubscriberException("Subcriber " + username + " is not exist.");
			long number_balance=s.getBalance();

			// Delete subscriber from SQL
			SubscriberManager.delete(s);
		
			return number_balance;
			
			
			
	
		}
		
		catch (MissingSubscriberException | SQLException e){
		
			return 0;
		}
	}
}
