/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

import java.util.HashSet;

import Individual.AbstractCompetitor;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of Team.
 * 
 * @author Robin
 */
public class Team extends AbstractCompetitor {
	/**
	 * Description of the property teamName.
	 */
	private String teamName = null;

	/**
	 * Description of the property players.
	 */
	public HashSet<Player> players = new HashSet<Player>();

	// Start of user code (user defined attributes for Team)

	// End of user code

	/**
	 * The constructor.
	 */
	public Team() {
		// Start of user code constructor for Team)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Team)

	public Team(String name) {
		// TODO Auto-generated constructor stub
	}

	// End of user code
	/**
	 * Returns teamName.
	 * @return teamName 
	 */
	public String getTeamName() {
		return this.teamName;
	}

	/**
	 * Sets a value to attribute teamName. 
	 * @param newTeamName 
	 */
	public void setTeamName(String newTeamName) {
		this.teamName = newTeamName;
	}

	/**
	 * Returns players.
	 * @return players 
	 */
	public HashSet<Player> getPlayers() {
		return this.players;
	}



}
