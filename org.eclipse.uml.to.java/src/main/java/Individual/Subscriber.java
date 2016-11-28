/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

import java.util.Date;
import java.util.HashSet;

import Bet.Bet;
import Individual.Player;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of Subscriber.
 * 
 * @author Robin
 */
public class Subscriber extends Player {
	/**
	 * Description of the property password.
	 */
	private String password = null;

	/**
	 * Description of the property balance.
	 */
	private long balance = 0L;

	/**
	 * Description of the property REGEX_USERNAME.
	 */
	private static String REGEX_USERNAME = null;

	/**
	 * Description of the property LONG_USERNAME.
	 */
	private static int LONG_USERNAME = 0;

	/**
	 * Description of the property bornDate.
	 */
	private Date bornDate = null;

	/**
	 * Description of the property username.
	 */
	private String username = null;

	/**
	 * Description of the property bets.
	 */
	public HashSet<Bet> bets = new HashSet<Bet>();

	/**
	 * Description of the property REGEX_NAME.
	 */
	private static String REGEX_NAME = null;

	// Start of user code (user defined attributes for Subscriber)

	// End of user code

	/**
	 * Description of the method authenticate.
	 * @param pwd 
	 */
	public void authenticate(String pwd) {
		// Start of user code for method authenticate
		// End of user code
	}

	/**
	 * Description of the method getBalance.
	 * @param username 
	 */
	public void getBalance(String username) {
		// Start of user code for method getBalance
		// End of user code
	}

	// Start of user code (user defined methods for Subscriber)

	// End of user code
	/**
	 * Returns password.
	 * @return password 
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets a value to attribute password. 
	 * @param newPassword 
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	/**
	 * Returns balance.
	 * @return balance 
	 */
	public long getBalance() {
		return this.balance;
	}

	/**
	 * Sets a value to attribute balance. 
	 * @param newBalance 
	 */
	public void setBalance(long newBalance) {
		this.balance = newBalance;
	}

	/**
	 * Returns REGEX_USERNAME.
	 * @return REGEX_USERNAME 
	 */
	public static String getREGEX_USERNAME() {
		return REGEX_USERNAME;
	}

	/**
	 * Sets a value to attribute REGEX_USERNAME. 
	 * @param newREGEX_USERNAME 
	 */
	public static void setREGEX_USERNAME(String newREGEX_USERNAME) {
		REGEX_USERNAME = newREGEX_USERNAME;
	}

	/**
	 * Returns LONG_USERNAME.
	 * @return LONG_USERNAME 
	 */
	public static int getLONG_USERNAME() {
		return LONG_USERNAME;
	}

	/**
	 * Sets a value to attribute LONG_USERNAME. 
	 * @param newLONG_USERNAME 
	 */
	public static void setLONG_USERNAME(int newLONG_USERNAME) {
		LONG_USERNAME = newLONG_USERNAME;
	}

	/**
	 * Returns bornDate.
	 * @return bornDate 
	 */
	public Date getBornDate() {
		return this.bornDate;
	}

	/**
	 * Sets a value to attribute bornDate. 
	 * @param newBornDate 
	 */
	public void setBornDate(Date newBornDate) {
		this.bornDate = newBornDate;
	}

	/**
	 * Returns username.
	 * @return username 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets a value to attribute username. 
	 * @param newUsername 
	 */
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	/**
	 * Returns bets.
	 * @return bets 
	 */
	public HashSet<Bet> getBets() {
		return this.bets;
	}

	/**
	 * Returns REGEX_NAME.
	 * @return REGEX_NAME 
	 */
	public static String getREGEX_NAME() {
		return REGEX_NAME;
	}

	/**
	 * Sets a value to attribute REGEX_NAME. 
	 * @param newREGEX_NAME 
	 */
	public static void setREGEX_NAME(String newREGEX_NAME) {
		REGEX_NAME = newREGEX_NAME;
	}

}
