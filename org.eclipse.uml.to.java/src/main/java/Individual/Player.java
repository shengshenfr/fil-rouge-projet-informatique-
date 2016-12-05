package Individual;
import Individual.AbstractCompetitor;
import Interface.Competitor;
import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;
import utils.MyCalendar;
import utils.dateValide;

public class Player extends AbstractCompetitor {
	
	private String firstName;
	private String lastName;
	private MyCalendar borndate;
	public Player(){
	
	}
	
	public Player(String firstName2){
		this.firstName=firstName2;
	}
	
	public Player(String lastName2, String firstName2, MyCalendar myCalendar){
		this.firstName=firstName2;
		this.lastName=lastName2;
		this.borndate=myCalendar;
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

}