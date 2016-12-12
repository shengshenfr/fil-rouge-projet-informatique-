package Interface;
/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

import Individual.*;
import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Competitor.
 * 
 * @author Robin
 */
public interface Competitor {
	boolean hasValidName();
	
	void addMember(Competitor member) throws ExistingCompetitorException,
		BadParametersException;
	
	void deleteMember(Competitor member) throws BadParametersException,
	ExistingCompetitorException;

}
