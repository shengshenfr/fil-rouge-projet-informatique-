/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

import Individual.AbstractCompetitor;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of Player.
 * 
 * @author Robin
 */
public abstract class Player extends AbstractCompetitor {
	/**
	 * Description of the property playerFirstName.
	 */
	private EString playerFirstName = null;

	/**
	 * Description of the property playerLastName.
	 */
	private EString playerLastName = null;

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
	 * Returns playerFirstName.
	 * @return playerFirstName 
	 */
	public EString getPlayerFirstName() {
		return this.playerFirstName;
	}

	/**
	 * Sets a value to attribute playerFirstName. 
	 * @param newPlayerFirstName 
	 */
	public void setPlayerFirstName(EString newPlayerFirstName) {
		this.playerFirstName = newPlayerFirstName;
	}

	/**
	 * Returns playerLastName.
	 * @return playerLastName 
	 */
	public EString getPlayerLastName() {
		return this.playerLastName;
	}

	/**
	 * Sets a value to attribute playerLastName. 
	 * @param newPlayerLastName 
	 */
	public void setPlayerLastName(EString newPlayerLastName) {
		this.playerLastName = newPlayerLastName;
	}

}
