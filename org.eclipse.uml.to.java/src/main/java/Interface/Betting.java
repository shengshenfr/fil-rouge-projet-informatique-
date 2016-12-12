package Interface;
import java.util.*;

import Bet.*;
import exceptions.*;

/**
 * 
 * @author prou + mallet + segarra <br>
 * <br>
 *         This interface declares all methods that should be provided by a
 *         betting software. <br>
 * 
 */

public interface Betting {
	
	/**
	 * add a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param closingDate
	 *            last date to bet.
	 * @param competitors
	 *            the collection of competitors for the competition.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if a competition with the same name already exists.
	 * @throws CompetitionException
	 *             raised if closing date is in the past (competition closed);
	 *             there are less than two competitors; two or more competitors
	 *             are the same (firstname, lastname, borndate).
	 * @throws BadParametersException
	 *             raised if name of competition is invalid; list of competitors
	 *             not instantiated; (firstname, lastname, borndate or name if a
	 *             team competitor) of one or more of the competitors is
	 *             invalid.
	 */
	public void addCompetition(String competition, Calendar closingDate, Collection<Competitor> competitors, String managerPwd )
			throws AuthentificationException, 
			BadParametersException,
			CompetitionException,
			ExistingCompetitionException;
	
	/**
	 * add a competitor to a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param competitor
	 *            infos about the competitor.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if the closing date of the competition is in the past
	 *             (competition closed).
	 * @throws ExistingCompetitorException
	 *             raised if the competitor is already registered for the
	 *             competition.
	 * @throws BadParametersException
	 *             raised if the (firstname, lastname, borndate or name if team
	 *             competitors) of the competitor is invalid.
	 */
	public void addCompetitor(String competition, Competitor competitor, String managerPwd)
			throws AuthentificationException,
			ExistingCompetitionException, 
			CompetitionException,
			ExistingCompetitorException, 
			BadParametersException;
	
	/**
	 * authenticate manager.
	 * 
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the manager's password is incorrect.
	 */	
	public void authenticateMngr(String managerPwd)
			throws AuthentificationException;

	/**
	 * bet on podium <br>
	 * The number of tokens of the subscriber is debited.
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * @param numberTokens
	 *            number of tokens to bet.
	 * @param competition
	 *            the name of the competition.
	 * @param winner
	 *            winner to bet.
	 * @param second
	 *            second place.
	 * @param third
	 *            third place.
	 * 
	 * @throws AuthentificationException
	 *             raised if (username, password) does not exist.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if there is no competitor with name a_winner, a_second
	 *             or a_third for the competition; competition is closed
	 *             (closing date is in the past); the player is a competitor of
	 *             the competition.
	 * @throws SubscriberException
	 *             raised if subscriber has not enough tokens.
	 * @throws BadParametersException
	 *             raised if number of tokens less than 0.
	 */
	public void betOnPodium(long numberTokens, String competition, Competitor winner, Competitor second, Competitor third, String username, String pwdSubs)
			throws AuthentificationException, 
			CompetitionException,
			ExistingCompetitionException, 
			SubscriberException,
			BadParametersException;
	
	/**
	 * bet a winner for a competition <br>
	 * The number of tokens of the subscriber is debited.
	 * 
	 * @param numberTokens
	 *            number of tokens to bet.
	 * @param competition
	 *            name of the competition.
	 * @param winner
	 *            competitor to bet (winner).
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if (username, password) does not exist.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if there is no competitor a_winner for the
	 *             competition; competition is closed (closing date is in the
	 *             past); the player is a competitor of the competition.
	 * @throws SubscriberException
	 *             raised if subscriber has not enough tokens.
	 * @throws BadParametersException
	 *             raised if number of tokens less than 0.
	 * 
	 */	
	public void betOnWinner(long numberTokens, String competition, Competitor winner, String username, String pwdSubs)
			throws AuthentificationException,
			CompetitionException, 
			ExistingCompetitionException,
			SubscriberException, 
			BadParametersException;
			
	/**
	* cancel a competition.
	* 
	* @param competition
	*            the name of the competition.
	* @param managerPwd
	*            the manager's password.
	* 
	* @throws AuthentificationException
	*             raised if the the manager's password is incorrect.
	* @throws ExistingCompetitionException
	*             raised if the competition does not exist.
	* @throws CompetitionException
	*             raised if the closing date is in the past (competition closed).          
	 * @throws ExistingSubscriberException 
	*/			
	public void cancelCompetition(String competition,String managerPwd)
			throws AuthentificationException, 
			ExistingCompetitionException,
			CompetitionException, ExistingSubscriberException;
	
	/**
	 * change subscriber's password.
	 * 
	 * @param username
	 *            username of the subscriber.
	 * @param newPwd
	 *            the new subscriber password.
	 * @param currentPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if (username, password) does not exist.
	 * 
	 * @throws BadParametersException
	 *             raised if the new password is invalid.
	 */
	public void changeSubsPwd(String username, String newPwd, String currentPwd)
			throws AuthentificationException, 
			BadParametersException;

	/**
	 * consult bets on a competition.
	 * 
	 * @param competition
	 *            competition's name.
	 * 
	 * @throws ExistingCompetitionException
	 *             raised if it does not exist a competition of the name
	 *             a_competition.
	 * 
	 * @return a list of String containing the bets for the competition.
	 */
	public ArrayList<String> consultBetsCompetition(String competition)
			throws ExistingCompetitionException;
	
	/**
	 * consult results of a closed competition.
	 * 
	 * @param competition
	 *            competition's name.
	 * 
	 * @throws ExistingCompetitionException
	 *             raised if it does not exist a competition of the name
	 *             a_competition.
	 * 
	 * @return the list of competitors that won the competition.
	 */	
	public ArrayList<Competitor> consultResultsCompetition(String competition)
			throws ExistingCompetitionException;
	
	/**
	 * create competitor (team) instance. If the competitor is already
	 * registered, the existing instance is returned. The instance is not
	 * persisted.
	 * 
	 * @param name
	 *            the name of the team.
	 * @param managerPwd
	 *            the manager's password.
	 *            
	 * @return Competitor instance.
	 * 
	 * @throws AuthentificationException
	 *             raised if the manager's password is incorrect.
	 * @throws BadParametersException
	 *             raised if name is invalid.
	 */	
	public Competitor createCompetitor(String name, String managerPwd)
			throws AuthentificationException,
			BadParametersException;
	
	/**
	 * create a competitor (person) instance. If the competitor is already
	 * registered, the existing instance is returned. The instance is not
	 * persisted.
	 * 
	 * @param lastName
	 *            the last name of the competitor.
	 * @param firstName
	 *            the first name of the competitor.
	 * @param borndate
	 *            the borndate of the competitor.
	 * @param managerPwd
	 *            the manager's password.
	 * @return Competitor instance.
	 * 
	 * @throws AuthentificationException
	 *             raised if the manager's password is incorrect.
	 * @throws BadParametersException
	 *             raised if last name, first name or borndate are invalid.
	 */	
	public Competitor createCompetitor(String lastName, String firstName, String borndate, String managerPwd)
			throws AuthentificationException, 
			BadParametersException;
	
	/**
	 * credit number of tokens of a subscriber.
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param numberTokens
	 *            number of tokens to credit.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if the subscriber (username) is not registered.
	 * @throws BadParametersException
	 *             raised if number of tokens is less than (or equals to) 0.
	 */	
	public void creditSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthentificationException, 
			ExistingSubscriberException,
			BadParametersException;
	
	/**
	 * debit a subscriber account with a number of tokens.
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param numberTokens
	 *            number of tokens to debit.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if the subscriber (username) is not registered.
	 * @throws SubscriberException
	 *             raised if number of tokens not enough.
	 * @throws BadParametersException
	 *             raised if number of tokens is less than (or equals to) 0.
	 * 
	 */	
	public void debitSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthentificationException, 
			ExistingSubscriberException,
			SubscriberException,
			BadParametersException;
	
	/**
	 * delete all bets made by a subscriber on a competition.<br>
	 * subscriber's account is credited with a number of tokens corresponding to
	 * the bets made by the subscriber for the competition.
	 * 
	 * @param competition
	 *            competition's name.
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if (username, password) does not exist.
	 * @throws CompetitionException
	 *             raised if closed competition (closing date is in the past).
	 * @throws ExistingCompetitionException
	 *             raised if there is no competition a_competition.
	 */
	public void deleteBetsCompetition(String competition, String username, String pwdSubs)
			throws AuthentificationException,
			CompetitionException, 
			ExistingCompetitionException;

	/**
	 * delete a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitorException
	 *            raised if the closing date is in the futur (competition opened).
	 *         
	 */
	public void deleteCompetition(String competition, String managerPwd)
			throws AuthentificationException,
			ExistingCompetitionException, 
			CompetitionException;
	
	/**
	 * delete a competitor for a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param competitor
	 *            infos about the competitor.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if the closing date of the competition is in the past
	 *             (competition closed) ; the number of remaining competitors is
	 *             2 before deleting.
	 * @throws ExistingCompetitorException
	 *             raised if the competitor is not registered for the
	 *             competition.
	 * @throws BadParametersException 
	 */
	public void deleteCompetitor(String competition,Competitor competitor, String managerPwd)
			throws AuthentificationException,
			ExistingCompetitionException, 
			CompetitionException,
			ExistingCompetitorException, BadParametersException;
	
	/**
	 * consult informations about a subscriber
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * 
	 * @return list of String with:
	 *         <ul>
	 *         <li>subscriber's lastname</li>
	 *         <li>subscriber's firstname</li>
	 *         <li>subscriber's borndate</li>
	 *         <li>subscriber's username</li>
	 *         <li>number of tokens</li>
	 *         <li>tokens betted</li>
	 *         <li>list of current bets</li>
	 *         </ul>
	 * <br> 
	 * All the current bets of the subscriber.
	 * 
	 * @throws AuthentificationException
	 *             raised if (username, password) does not exist.     
	 */	
	public ArrayList<String> infosSubscriber(String username, String pwdSubs)
			throws AuthentificationException;
	
	/**
	 * list competitions.
	 * 
	 * @return a collection of competitions represent a competition data:
	 *         <ul>
	 *         <li>competition's name</li>
	 *         <li>competition's closing date</li>
	 *         <li>competition's competitors</li>
	 *         </ul>
	 */	
	public List<List<String>> listCompetitions();

	/**
	 * list competitors.
	 * 
	 * @param competition
	 *            competition's name.
	 * 
	 * @return a collection of competitors for a competition. For each person
	 *         competitor
	 *         <ul>
	 *         <li>competitor's firstname</li>
	 *         <li>competitor's lastname</li>
	 *         <li>competitor's borndate</li>
	 *         </ul>
	 *         For each team competitor <li>competitor's name</li> </ul>
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if competition closed.
	 */
	public List<List<String>> listCompetitors(String competition)
			throws ExistingCompetitionException,
			CompetitionException;
	
	/**
	 * list subscribers.
	 * 
	 * @param managerPwd
	 *            the manager's password.
	 * @return a list of list of strings:
	 *         <ul>
	 *         <li>subscriber's lastname</li>
	 *         <li>subscriber's firstname</li>
	 *         <li>subscriber's born date</li>
	 *         <li>subscriber's username</li>
	 *         </ul> 
	 * @throws AuthentificationException
	 *             raised if the manager's password is incorrect.
	 */
	public List<List<String>> listSubscribers(String managerPwd)
			throws AuthentificationException;
	
	/**
	 * settle bets on podium. <br>
	 * Each subscriber betting on this competition with the right podium is
	 * credited with a number of tokens equals to: <br>
	 * (number of tokens betted * total tokens betted for the competition) /
	 * total number of tokens betted for the podium <br>
	 * If no subscriber bets on the right podium, the tokens betted are credited
	 * to subscribers betting on the competition according to the number of
	 * tokens they betted. The competition is then deleted if no more bets exist
	 * for the competition.<br>
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param winner
	 *            the winner.
	 * @param second
	 *            the second.
	 * @param third
	 *            the third.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if two competitors in the podium are the same; no
	 *             competitor (firstname, lastname, borndate or name for teams)
	 *             a_winner, a_second or a_third for the competition;
	 *             competition still opened
	 */	
	public void settlePodium(String competition, Competitor winner, Competitor second, Competitor third, String managerPwd)
			throws AuthentificationException, 
			ExistingCompetitionException,
			CompetitionException;

	/**
	 * settle bets on winner. <br>
	 * Each subscriber betting on this competition with winner a_winner is
	 * credited with a number of tokens equals to: <br>
	 * (number of tokens betted * total tokens betted for the competition) /
	 * total number of tokens betted for the winner <br>
	 * If no subscriber bets on the right competitor (the real winner), the
	 * tokens betted are credited to subscribers betting on the competition
	 * according to the number of tokens they betted. The competition is then
	 * deleted if no more bets exist for the competition.<br>
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param winner
	 *            competitor winner.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthentificationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if there is no competitor a_winner for the
	 *             competition; competition still opened.
	 */	
	public void settleWinner(String competition, Competitor winner, String managerPwd)
			throws AuthentificationException, 
			ExistingCompetitionException,
			CompetitionException;
	
	/**
	 * register a subscriber (person).
	 * 
	 * @param lastName
	 *            the last name of the subscriber.
	 * @param firstName
	 *            the first name of the subscriber.
	 * @param username
	 *            the username of the subscriber.
	 * @param borndate
	 *            the borndate of the subscriber.
	 * @param managerPwd
	 *            the manager's password.
	 *            
	 * @return password for the new subscriber.
	 * 
	 * @throws AuthentificationException
	 *             raised if the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if a subscriber exists with the same username.
	 * @throws SubscriberException
	 *             raised if subscriber is minor.
	 * @throws BadParametersException
	 *             raised if last name, first name, username or borndate 
	 *             are invalid or not instantiated.         
	 * 
	 */
	public String subscribe(String lastName, String firstName, String username, String borndate, String managerPwd)
			throws AuthentificationException,
			ExistingSubscriberException, 
			SubscriberException,
			BadParametersException;
	
	/**
	 * delete a subscriber. His currents bets are canceled. He looses betted tokens.
	 * 
	 * @param username
	 *            the username of the subscriber.
	 * @param managerPwd
	 *            the manager's password.
	 * @return number of tokens remaining in the subscriber's account
	 * 
	 * @throws AuthentificationException
	 *             raised if the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if username is not registered.
	 * 
	 */
	public long unsubscribe(String username, String managerPwd)
			throws AuthentificationException, 
			ExistingSubscriberException;

}
