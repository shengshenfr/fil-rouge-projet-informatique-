/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Betting;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of BettingSoft.
 * 
 * @author Robin.
 */
public class BettingSoft {
	/**
	 * Description of the property competitorManagers.
	 */
	public CompetitorManager competitorManagers = null;

	/**
	 * Description of the property unregisteredCompetitors.
	 */
	public HashSet<UnregisteredCompetitor> unregisteredCompetitors = new HashSet<UnregisteredCompetitor>();

	/**
	 * Description of the property managers.
	 */
	public HashSet<Manager> managers = new HashSet<Manager>();

	/**
	 * Description of the property class1s.
	 */
	public SubscriberManager class1s = null;

	/**
	 * Description of the property competitors.
	 */
	public HashSet<Competitor> competitors = new HashSet<Competitor>();

	/**
	 * Description of the property subscribers.
	 */
	public HashSet<Subscriber> subscribers = new HashSet<Subscriber>();

	/**
	 * Description of the property teamManagers.
	 */
	public TeamManager teamManagers = null;

	/**
	 * Description of the property entryManagers.
	 */
	public HashSet<EntryManager> entryManagers = new HashSet<EntryManager>();

	/**
	 * Description of the property class5s.
	 */
	public CompetitionManager class5s = null;

	/**
	 * Description of the property class3s.
	 */
	public BetManager class3s = null;

	// Start of user code (user defined attributes for BettingSoft)

	// End of user code

	/**
	 * The constructor.
	 */
	public BettingSoft() {
		// Start of user code constructor for BettingSoft)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for BettingSoft)

	// End of user code
	/**
	 * Returns competitorManagers.
	 * @return competitorManagers 
	 */
	public CompetitorManager getCompetitorManagers() {
		return this.competitorManagers;
	}

	/**
	 * Sets a value to attribute competitorManagers. 
	 * @param newCompetitorManagers 
	 */
	public void setCompetitorManagers(CompetitorManager newCompetitorManagers) {
		if (this.competitorManagers != null) {
			this.competitorManagers.set(null);
		}
		this.competitorManagers.set(this);
	}

	/**
	 * Returns unregisteredCompetitors.
	 * @return unregisteredCompetitors 
	 */
	public HashSet<UnregisteredCompetitor> getUnregisteredCompetitors() {
		return this.unregisteredCompetitors;
	}

	/**
	 * Returns managers.
	 * @return managers 
	 */
	public HashSet<Manager> getManagers() {
		return this.managers;
	}

	/**
	 * Returns class1s.
	 * @return class1s 
	 */
	public SubscriberManager getClass1s() {
		return this.class1s;
	}

	/**
	 * Sets a value to attribute class1s. 
	 * @param newClass1s 
	 */
	public void setClass1s(SubscriberManager newClass1s) {
		if (this.class1s != null) {
			this.class1s.set(null);
		}
		this.class1s.set(this);
	}

	/**
	 * Returns competitors.
	 * @return competitors 
	 */
	public HashSet<Competitor> getCompetitors() {
		return this.competitors;
	}

	/**
	 * Returns subscribers.
	 * @return subscribers 
	 */
	public HashSet<Subscriber> getSubscribers() {
		return this.subscribers;
	}

	/**
	 * Returns teamManagers.
	 * @return teamManagers 
	 */
	public TeamManager getTeamManagers() {
		return this.teamManagers;
	}

	/**
	 * Sets a value to attribute teamManagers. 
	 * @param newTeamManagers 
	 */
	public void setTeamManagers(TeamManager newTeamManagers) {
		if (this.teamManagers != null) {
			this.teamManagers.set(null);
		}
		this.teamManagers.set(this);
	}

	/**
	 * Returns entryManagers.
	 * @return entryManagers 
	 */
	public HashSet<EntryManager> getEntryManagers() {
		return this.entryManagers;
	}

	/**
	 * Returns class5s.
	 * @return class5s 
	 */
	public CompetitionManager getClass5s() {
		return this.class5s;
	}

	/**
	 * Sets a value to attribute class5s. 
	 * @param newClass5s 
	 */
	public void setClass5s(CompetitionManager newClass5s) {
		if (this.class5s != null) {
			this.class5s.set(null);
		}
		this.class5s.set(this);
	}

	/**
	 * Returns class3s.
	 * @return class3s 
	 */
	public BetManager getClass3s() {
		return this.class3s;
	}

	/**
	 * Sets a value to attribute class3s. 
	 * @param newClass3s 
	 */
	public void setClass3s(BetManager newClass3s) {
		if (this.class3s != null) {
			this.class3s.set(null);
		}
		this.class3s.set(this);
	}

}
