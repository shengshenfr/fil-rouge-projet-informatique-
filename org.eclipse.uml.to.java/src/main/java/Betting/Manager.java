/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Betting;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Manager.
 * 
 * @author Robin
 */
public class Manager {
	/**
	 * Description of the property username.
	 */
	public EString username = null;

	/**
	 * Description of the property password.
	 */
	public EString password = null;

	/**
	 * Description of the property REGEX_USERNAME.
	 */
	public static EString REGEX_USERNAME = null;

	/**
	 * Description of the property LONG_USERNAME.
	 */
	public static int LONG_USERNAME = 0;

	// Start of user code (user defined attributes for Manager)

	// End of user code

	/**
	 * The constructor.
	 */
	public Manager() {
		// Start of user code constructor for Manager)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Manager)

	// End of user code
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

}
