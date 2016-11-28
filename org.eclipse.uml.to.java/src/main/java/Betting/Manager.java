/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Betting;
import Betting.BettingSoft;
import Betting.Exceptions.AuthentificationException;
import Betting.Exceptions.BadParametersException;

/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Manager.
 * 
 * @author Robin
 */
public class Manager {
	/**
	 * Description of the property LONG_USERNAME.
	 */
	public int LONG_USERNAME = 4;

	/**
	 * Description of the property REGEX_USERNAME.
	 */
	public String REGEX_USERNAME = new String("[a-zA-Z0-9]*");

	/**
	 * Description of the property username.
	 */
	public String username;

	/**
	 * Description of the property password.
	 */
	public String password;


	/**
	 * The constructor.
	 */
	public Manager(String username, String password) {
		// Start of user code constructor for Manager)
		this.username = username;
		this.password = password;
		// End of user code
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
	 * @param username 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

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
	 * @return 
	 */
	public String setPassword(String newPassword) {
		return this.password = newPassword;
	}
	
	public BettingSoft Bsport;
	public void changeManagerPwd(String currentPwd,String newPwd) 
			throws BadParametersException, AuthentificationException {

		Bsport.authenticateMngr(currentPwd);
		if (newPwd.equals(currentPwd))
			throw new BadParametersException("New password is the same with old password");
		this.setPassword(newPwd);
	}	

}

