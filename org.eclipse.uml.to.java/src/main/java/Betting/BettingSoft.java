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
	public managerBetting manager=null;

	//-----------------------Methods------------------------------\\
	/**
	 *  create a manager
	 * @param username
	 *            the name of manager.
	 * @param managerPwd
	 *            the name of manager.
	 * @throws ExistingCompetitorException  
	 */
	public void createManager(String manager_username) throws BadParametersException {
		// manager should exist
		if (manager!=null)
			throw new BadParametersException("manager is existed");
		else{
			manager= new managerBetting(manager_username,MANAGER_PASSWORD);
		}
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
			EntryManager.persist(new Entry(c, competitor));
		}
		catch(SQLException|MissingCompetitionException e){
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
		
			PodiumBet podium_bet = new PodiumBet(numberTokens,subscriber,new Entry(c,winner),new Entry(c,second),new Entry(c,third));
			//Debit subscriber 
			subscriber.debitSubscriber(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			PodiumBetManager.persist(podium_bet);
		}
		catch ( ExistingSubscriberException | SQLException |MissingCompetitionException |MissingTokenException e){
			System.out.println(e);
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			WinnerBet winner_bet = new WinnerBet(numberTokens,subscriber,new Entry(c,winner));

			//Debit subscriber 
			subscriber.debitSubscriber(numberTokens);

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);

			//Persist the bet
			WinnerBetManager.persist(winner_bet);
		}
		catch (  SQLException|MissingCompetitionException|MissingTokenException  e){
			System.out.println(e);
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void cancelCompetition(String competition, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException, ExistingSubscriberException {
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
				throw new CompetitionException ("This competition has already begun,can't cancel it");
			//delete three types bets and update subscriber in SQL
			List<WinnerBet> betOwner = WinnerBetManager.findAllWinnerBetsByCompetition(c);
			
			for (WinnerBet bet : betOwner) {
				Subscriber s=SubscriberManager.findByUsername(bet.getBetOwner().getUsername());
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				WinnerBetManager.delete(bet);
			}
			List<PodiumBet> betOwnerpodium = PodiumBetManager.findAllPodiumBetsByCompetition(c);
			
			for (PodiumBet bet : betOwnerpodium) {
				Subscriber s=SubscriberManager.findByUsername(bet.getBetOwner().getUsername());
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				PodiumBetManager.delete(bet);
			}
			List<DrawBet> betOwnerdraw = DrawBetManager.findAllDrawBetsByCompetition(c);
			
			for (DrawBet bet : betOwnerdraw) {
				Subscriber s=SubscriberManager.findByUsername(bet.getBetOwner().getUsername());
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				DrawBetManager.delete(bet);
			}

			// Delete competition in SQL
			CompetitionManager.delete(c);
	}
	catch(SQLException |   MissingCompetitionException| BadParametersException e){
		System.out.println(e);
	} catch (SubscriberException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExistingCompetitorException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
		} catch (CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SubscriberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			if (today.before(closingdate))
				throw new CompetitionException("The competition is not start, you cannot get the bets list");

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
		ArrayList<Competitor> winners = new ArrayList<Competitor>();
	
		try{
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null){
				throw new ExistingCompetitionException("The competition doesn't exist!");
			}
		
			//check if the competition is closed
			Calendar today = new GregorianCalendar();
			Calendar closingdate = c.getClosingCalendar();
			if (today.before(closingdate))
				throw new CompetitionException("The competition is not start, you cannot get the competitor list");
			
			
			
			List<Entry> entrylist = EntryManager.findAllByCompetition(competition);
			for (Entry entry : entrylist){
				Competitor competitor = entry.getCompetitor();
				Rank rank = entry.getRank();
				int r = Rank.getIndex(rank);
				winners.add(r,competitor);
			}
			
			
		}
	
		catch(SQLException | MissingCompetitionException | CompetitionException e){
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
			if (TeamManager.findByName(name) != null)
				throw new ExistingCompetitorException("Team " + name + " is already in the system");

			//Check team's name is valid or not is in the method hasValidName of the class CompetitiorEmplement

			//Create a team
			Team team = new Team(name);

			//Persist a team to bdd
			TeamManager.persist(team.getTeamName());
			
			return (Competitor)team;
		}
		catch(ExistingCompetitorException e){
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Competitor createCompetitor(String lastName, String firstName, String borndate, String managerPwd)
			throws AuthentificationException, BadParametersException {
		try{
			// Authenticate manager
			authenticateMngr(managerPwd);
			String userName=firstName+lastName;
			//check if the competitor has already been in the system or not 
			if (PlayerManager.findByName(userName) != null)
				throw new ExistingCompetitorException("Team " +userName+  " is already in the system");

			//Check player's name is valid or not is in the method hasValidName of the class CompetitiorEmplement

			//Create competitor
			Player player = new Player(userName,firstName,lastName, borndate);

			PlayerManager.persist(player);
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
			} catch (CompetitionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SubscriberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExistingCompetitorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		} catch (CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			//delete three types bets and update subscriber in SQL
			List<WinnerBet> betOwner = WinnerBetManager.findAllWinnerBetsByCompetition(c);
			
			for (WinnerBet bet : betOwner) {
				Subscriber s=SubscriberManager.findByUsername(username);
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				WinnerBetManager.delete(bet);
			}
			List<PodiumBet> betOwnerpodium = PodiumBetManager.findAllPodiumBetsByCompetition(c);
			
			for (PodiumBet bet : betOwnerpodium) {
				Subscriber s=SubscriberManager.findByUsername(username);
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				PodiumBetManager.delete(bet);
			}
			List<DrawBet> betOwnerdraw = DrawBetManager.findAllDrawBetsByCompetition(c);
			
			for (DrawBet bet : betOwnerdraw) {
				Subscriber s=SubscriberManager.findByUsername(username);
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				DrawBetManager.delete(bet);
			}

			//Update subscriber's information in the database
			SubscriberManager.update(subscriber);
		}
		catch (MissingCompetitionException|  BadParametersException | SQLException|  SubscriberException| ExistingCompetitorException e){
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
				throw new CompetitionException("The competition is not start yet,you can just cancel it");
			}

			//delete three types bets and update subscriber in SQL
			List<WinnerBet> betOwner = WinnerBetManager.findAllWinnerBetsByCompetition(c);
			
			for (WinnerBet bet : betOwner) {
				Subscriber s=SubscriberManager.findByUsername(bet.getBetOwner().getUsername());
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				WinnerBetManager.delete(bet);
			}
			List<PodiumBet> betOwnerpodium = PodiumBetManager.findAllPodiumBetsByCompetition(c);
			
			for (PodiumBet bet : betOwnerpodium) {
				Subscriber s=SubscriberManager.findByUsername(bet.getBetOwner().getUsername());
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				PodiumBetManager.delete(bet);
			}
			List<DrawBet> betOwnerdraw = DrawBetManager.findAllDrawBetsByCompetition(c);
			
			for (DrawBet bet : betOwnerdraw) {
				Subscriber s=SubscriberManager.findByUsername(bet.getBetOwner().getUsername());
				s.creditSubscriber(bet.getAmount());
				SubscriberManager.update(s);
				DrawBetManager.delete(bet);
			}
			CompetitionManager.delete(c);
			} 
		
		catch (SQLException | MissingCompetitionException|BadParametersException|SubscriberException|ExistingCompetitorException  e){
			System.out.println(e);
		
		}
	}

	@Override
	public void deleteCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException, BadParametersException {
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
				throw new CompetitionException ("This competition is already start");	

			//delete the Entry correspondent this competition and this competitor
			List<Entry> entry=EntryManager.findAllByCompetition(competition);
			for(Entry en:entry){
				if(en.getCompetitor().equals(competitor)){
					EntryManager.delete(en);
				}
			}
	
			//Delete in SQL
		}
		catch (SQLException|MissingCompetitionException e){
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
		
			
			List<WinnerBet> listWinnerBets= WinnerBetManager.findByOwner(username);
			for (int i=0; i<listWinnerBets.size();i++) {
				WinnerBet bet = listWinnerBets.get(i);
				infos.add(bet.toString());
			}
			List<PodiumBet> listPodiumBets= PodiumBetManager.findByOwner(username);
			for (int i=0; i<listPodiumBets.size();i++) {
				PodiumBet bet = listPodiumBets.get(i);
				infos.add(bet.toString());
			}
			List<DrawBet> listDrawBets= DrawBetManager.findByOwner(username);
			for (int i=0; i<listDrawBets.size();i++) {
				DrawBet bet = listDrawBets.get(i);
				infos.add(bet.toString());
			}
			
			return infos;
		}
		
		
		catch( SQLException | ExistingSubscriberException | BadParametersException|CompetitionException | SubscriberException |ExistingCompetitorException |MissingCompetitionException e) {
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
								
				List<WinnerBet> betsList = WinnerBetManager.findAllWinnerBetsByCompetition(c);
				for (WinnerBet bet : betsList) {
					infoCompetition.add(bet.toString());
				}
				List<Entry> entrylist = EntryManager.findAllByCompetition(c.getName());			
				
				for (Entry entry : entrylist) {
					List<String> entry_data = new ArrayList<String>();
					entry_data.add(entry.getCompetitor().toString());
					infoCompetition.addAll(entry_data);
				}
				
				collectionCompetition.add(infoCompetition);
			}
		}
		catch (SQLException | BadParametersException |  CompetitionException|  SubscriberException|  ExistingCompetitorException e){
			return null;
		}
		return collectionCompetition;
	}

	@Override
	public List<List<String>> listCompetitors(String competition)
			throws ExistingCompetitionException, CompetitionException {
		
		
		List<List<String>> collectionCompetitor = new ArrayList<List<String>>();
		List<Competitor> competitors;
		try{
			Competition c = CompetitionManager.findBycompetitionName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition "+ competition + " is not exist");
			
			List<Entry> entrylist = EntryManager.findAllByCompetition(competition);	
			
			for (Entry entry : entrylist) {
				
				Competitor competitor = entry.getCompetitor();
				competitors.add(competitor);
			}
			
			List<String> infoCompetitor = new ArrayList<String>();
			for (int i=0; i<competitors.size();i++){
				Competitor comp = competitors.get(i);
				if (comp instanceof Player) {
					infoCompetitor.add(((Player) comp).getUserName());
					infoCompetitor.add(((Player) comp).getFirstName());
					infoCompetitor.add(((Player) comp).getBornDate().toString());
				} else if (comp instanceof Team) {
					
					infoCompetitor.add(((Team) competitors).getTeamName());
				}
				
				
				collectionCompetitor.add(infoCompetitor);
			}
			
		
		}
		catch (BadParametersException | SQLException e){
			e.printStackTrace();
		}
		
		return collectionCompetitor;
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
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SubscriberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExistingCompetitorException e) {
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
				throw new CompetitionException ("This competition is not open");

			if (!(EntryManager.existCompetitorInCompetition(c,(AbstractCompetitor)winner) || EntryManager.existCompetitorInCompetition(c,(AbstractCompetitor)second) || EntryManager.existCompetitorInCompetition(c,(AbstractCompetitor)third)))
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
		} catch (CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
			//delete all bets related to this subscriber
			List<WinnerBet> betOwner = WinnerBetManager.findByOwner(username);
			
			for (WinnerBet bet : betOwner) {
				WinnerBetManager.delete(bet);
			}
			List<PodiumBet> betOwnerpodium = PodiumBetManager.findByOwner(username);
			
			for (PodiumBet bet : betOwnerpodium) {
				PodiumBetManager.delete(bet);
			}
			List<DrawBet> betOwnerdraw = DrawBetManager.findByOwner(username);
			
			for (DrawBet bet : betOwnerdraw) {
				DrawBetManager.delete(bet);
			}
			// Delete subscriber from SQL
			SubscriberManager.delete(s);
		
			return number_balance;
			
	
		}
		
		catch (MissingSubscriberException | SQLException e){
		
			return 0;
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SubscriberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingCompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
