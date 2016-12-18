/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;
import Interface.Competitor;

// End of user code

/**
 * Description of AbstractCompetitor.
 * 
 * @author Robin
 */
public class AbstractCompetitor implements Competitor {
	/**
	 * Description of the property abstractCompetitorName.
	 */
	private String abstractCompetitorName = null;
    private static int LONG_NAME = 6;
    private static String REGEX_NAME = "^[0-9A-Za-z]{6}$";

	// Start of user code (user defined attributes for AbstractCompetitor)

	// End of user code

	/**
	 * The constructor.
	 * @throws BadParametersException 
	 */
	public AbstractCompetitor(String name) throws BadParametersException {
		// Start of user code constructor for AbstractCompetitor)
		
        if(abstractCompetitorName==null){
            throw new BadParametersException("can't have null name!!!");
        }
        if(!abstractCompetitorName.matches(REGEX_NAME)){
            throw new BadParametersException("REGEX_NAME Wrong!");
        }
        if(abstractCompetitorName.length()!=6){
        	throw new BadParametersException("Length of name is Wrong!");
        }
        this.abstractCompetitorName=name;
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

	public boolean hasValidName(String name) {
		// TODO Auto-generated method stub
		 if(!name.matches(REGEX_NAME)){
	            return false;
	        }
		return true;
	}

	@Override
	public boolean hasValidName() {
		// TODO Auto-generated method stub
		 if(!abstractCompetitorName.matches(REGEX_NAME)){
	            return false;
	        }
		return true;
	}

	@Override
	public void addMember(Competitor member)
			throws ExistingCompetitorException, BadParametersException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMember(Competitor member) throws BadParametersException,
			ExistingCompetitorException {
		// TODO Auto-generated method stub
		
	}

}
