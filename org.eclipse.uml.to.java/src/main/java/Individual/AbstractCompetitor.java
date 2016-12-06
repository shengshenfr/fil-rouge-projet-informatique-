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
	private String abstractCompetitorName = null;

	// Start of user code (user defined attributes for AbstractCompetitor)

	// End of user code

	/**
	 * The constructor.
	 */
	public AbstractCompetitor(String name) {
		// Start of user code constructor for AbstractCompetitor)
		this.abstractCompetitorName=name;
		// End of user code
	}

	// Start of user code (user defined methods for AbstractCompetitor)

	public AbstractCompetitor() {
		// TODO Auto-generated constructor stub
	}

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

	public boolean hasValidName(String teamName) {
		// TODO Auto-generated method stub
		return false;
	}

}
