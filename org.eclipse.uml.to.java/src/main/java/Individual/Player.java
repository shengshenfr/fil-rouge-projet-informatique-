package Individual;
import Individual.AbstractCompetitor;
import Interface.Competitor;
import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;

public class Player extends AbstractCompetitor {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String bornDate;
	
	public Player(){
		super();
	}
	public Player(String userName,String firstName,String lastName, String bornDate){
		super(userName);
		this.firstName=firstName;
		this.lastName=lastName;
		this.bornDate=bornDate;
	}
	
	public String getUserName(){
		return this.userName;
	}
	public String getFirstName(){
		return this.firstName;
	}
	public String getlastName(){
		return this.lastName;
	}
	public String getbornDate(){
		return this.bornDate;
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