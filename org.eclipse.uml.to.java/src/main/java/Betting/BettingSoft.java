/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Betting;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of BettingSoft.
 * 
 * @author Robin
 */
public class BettingSoft {
	/**
	 * Description of the property unregisteredCompetitors.
	 */
	public HashSet<UnregisteredCompetitor> unregisteredCompetitors = new HashSet<UnregisteredCompetitor>();

	/**
	 * Description of the property managers.
	 */
	public HashSet<Manager> managers = new HashSet<Manager>();

	/**
	 * Description of the property competitors.
	 */
	public HashSet<Competitor> competitors = new HashSet<Competitor>();

	/**
	 * Description of the property subscribers.
	 */
	public HashSet<Subscriber> subscribers = new HashSet<Subscriber>();

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

}
