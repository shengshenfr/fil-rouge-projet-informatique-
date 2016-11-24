/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

// Start of user code (user defined imports)

// End of user code

/**
 * Description of BettingSoft.
 * 
 * @author Robin
 */
public class BettingSoft implements Betting {
	/**
	 * Description of the property unregisteredCompetitors.
	 */
	public HashSet<UnregisteredCompetitor> unregisteredCompetitors = new HashSet<UnregisteredCompetitor>();

	/**
	 * Description of the property managers.
	 */
	public Manager managers = null;

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
	public Manager getManagers() {
		return this.managers;
	}

	/**
	 * Sets a value to attribute managers. 
	 * @param newManagers 
	 */
	public void setManagers(Manager newManagers) {
		if (this.managers != null) {
			this.managers.set(null);
		}
		this.managers.set(this);
	}

	/**
	 * Returns subscribers.
	 * @return subscribers 
	 */
	public HashSet<Subscriber> getSubscribers() {
		return this.subscribers;
	}

}
