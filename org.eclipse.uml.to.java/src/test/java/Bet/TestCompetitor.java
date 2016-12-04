package Bet;

import Interface.Competitor;
import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;

public class TestCompetitor implements Competitor {

	@Override
	public boolean hasValidName() {
		return true;
	}

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public void addMember(Competitor member)
			throws ExistingCompetitorException, BadParametersException {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteMember(Competitor member)
			throws BadParametersException, ExistingCompetitorException {
		// TODO Auto-generated method stub
	}

}
