package Individual;
import Individual.AbstractCompetitor;
import Interface.Competitor;
import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;

public class Player extends AbstractCompetitor {
	
	private String playerName;
	
	public Player(){
		super();
	}
	public Player(String firstName,String lastName, String bornDate){
		super(playerName);
	}
	
	public String getUserName(){
		return this.playerName;
	}
	@Override
	public boolean hasValidName() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addMember(Competitor member) throws ExistingCompetitorException, BadParametersException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteMember(Competitor member) throws BadParametersException, ExistingCompetitorException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean hasValidName(String teamName) {
		// TODO Auto-generated method stub
		return false;
	}

}