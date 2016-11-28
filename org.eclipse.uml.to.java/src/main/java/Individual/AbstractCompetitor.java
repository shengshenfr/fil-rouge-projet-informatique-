/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

import Interface.Competitor;

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
	private EString abstractCompetitorName = null;

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
	public EString getAbstractCompetitorName() {
		return this.abstractCompetitorName;
	}

	/**
	 * Sets a value to attribute abstractCompetitorName. 
	 * @param newAbstractCompetitorName 
	 */
	public void setAbstractCompetitorName(EString newAbstractCompetitorName) {
		this.abstractCompetitorName = newAbstractCompetitorName;
	}

}
