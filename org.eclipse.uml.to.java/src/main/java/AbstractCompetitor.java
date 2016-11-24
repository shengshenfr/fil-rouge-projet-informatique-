/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

// Start of user code (user defined imports)

// End of user code

/**
 * Description of AbstractCompetitor.
 * 
 * @author Robin
 */
public abstract class AbstractCompetitor implements Competitor {
	/**
	 * Description of the property abstractCompetitorName.
	 */
	public String abstractCompetitorName = "";

	// Start of user code (user defined attributes for AbstractCompetitor)

	// End of user code

	/**
	 * The constructor.
	 */
	public AbstractCompetitor() {
		// Start of user code constructor for AbstractCompetitor)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for AbstractCompetitor)

	// End of user code
	/**
	 * Returns abstractCompetitorName.
	 * @return abstractCompetitorName 
	 */
	public String getAbstractCompetitorName() {
		return this.abstractCompetitorName;
	}

	/**
	 * Sets a value to attribute abstractCompetitorName. 
	 * @param newAbstractCompetitorName 
	 */
	public void setAbstractCompetitorName(String newAbstractCompetitorName) {
		this.abstractCompetitorName = newAbstractCompetitorName;
	}

}
