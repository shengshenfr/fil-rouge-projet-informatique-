/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Player.
 * 
 * @author Robin
 */
public abstract class Player {
	/**
	 * Description of the property playerfirstname.
	 */
	public String playerfirstname = "";

	/**
	 * Description of the property playerlastname.
	 */
	public String playerlastname = "";

	// Start of user code (user defined attributes for Player)

	// End of user code

	/**
	 * The constructor.
	 */
	public Player() {
		// Start of user code constructor for Player)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Player)

	// End of user code
	/**
	 * Returns playerfirstname.
	 * @return playerfirstname 
	 */
	public String getPlayerfirstname() {
		return this.playerfirstname;
	}

	/**
	 * Sets a value to attribute playerfirstname. 
	 * @param newPlayerfirstname 
	 */
	public void setPlayerfirstname(String newPlayerfirstname) {
		this.playerfirstname = newPlayerfirstname;
	}

	/**
	 * Returns playerlastname.
	 * @return playerlastname 
	 */
	public String getPlayerlastname() {
		return this.playerlastname;
	}

	/**
	 * Sets a value to attribute playerlastname. 
	 * @param newPlayerlastname 
	 */
	public void setPlayerlastname(String newPlayerlastname) {
		this.playerlastname = newPlayerlastname;
	}

}
