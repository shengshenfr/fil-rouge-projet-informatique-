/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

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
	private EString password = null;

	/**
	 * Description of the property balance.
	 */
	private long balance = 0L;

	/**
	 * Description of the property REGEX_USERNAME.
	 */
	private static EString REGEX_USERNAME = null;

	/**
	 * Description of the property LONG_USERNAME.
	 */
	private static int LONG_USERNAME = 0;

	/**
	 * Description of the property bornDate.
	 */
	private EDate bornDate = null;

	/**
	 * Description of the property username.
	 */
	private EString username = null;

	/**
	 * Description of the property bets.
	 */
	public HashSet<Bet> bets = new HashSet<Bet>();

	/**
	 * Description of the property REGEX_NAME.
	 */
	private static EString REGEX_NAME = null;

	// Start of user code (user defined attributes for Subscriber)

	// End of user code

	/**
	 * Description of the method authenticate.
	 * @param pwd 
	 */
	public void authenticate(EString pwd) {
		// Start of user code for method authenticate
		// End of user code
	}

	/**
	 * Description of the method getBalance.
	 * @param username 
	 */
	public void getBalance(EString username) {
		// Start of user code for method getBalance
		// End of user code
	}

	// Start of user code (user defined methods for Subscriber)

	// End of user code
	/**
	 * Returns password.
	 * @return password 
	 */
	public EString getPassword() {
		return this.password;
	}

	/**
	 * Sets a value to attribute password. 
	 * @param newPassword 
	 */
	public void setPassword(EString newPassword) {
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
	public static EString getREGEX_USERNAME() {
		return REGEX_USERNAME;
	}

	/**
	 * Sets a value to attribute REGEX_USERNAME. 
	 * @param newREGEX_USERNAME 
	 */
	public static void setREGEX_USERNAME(EString newREGEX_USERNAME) {
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
	public EDate getBornDate() {
		return this.bornDate;
	}

	/**
	 * Sets a value to attribute bornDate. 
	 * @param newBornDate 
	 */
	public void setBornDate(EDate newBornDate) {
		this.bornDate = newBornDate;
	}

	/**
	 * Returns username.
	 * @return username 
	 */
	public EString getUsername() {
		return this.username;
	}

	/**
	 * Sets a value to attribute username. 
	 * @param newUsername 
	 */
	public void setUsername(EString newUsername) {
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
	public static EString getREGEX_NAME() {
		return REGEX_NAME;
	}

	/**
	 * Sets a value to attribute REGEX_NAME. 
	 * @param newREGEX_NAME 
	 */
	public static void setREGEX_NAME(EString newREGEX_NAME) {
		REGEX_NAME = newREGEX_NAME;
	}

}
