package Individual;
import java.sql.SQLException;
import java.util.ArrayList;
import Individual.AbstractCompetitor;
import dbManager.TeamManager;
import dbManager.PlayerTeamManager;
import exceptions.BadParametersException;
import exceptions.ExistingCompetitorException;
import Individual.Player;
import Interface.Competitor;

public class Team extends AbstractCompetitor{

	private String teamName;
	private ArrayList<Player> member;
	
	public Team(String name) throws SQLException{
		if(!hasValidName(name)){
			this.teamName = name;
			dbManager.TeamManager.persist(name);
		}
	}
// verifier if the team has exist. If not, add the new team in data base.
	
	@Override
		public void addMember(Player player){
		// TODO Auto-generated method stub
		 ArrayList<Team> list = dbManager.PlayerTeamManager.findByPlayer(player.getPlayerName());
		 if(list==null){
			 this.member.add(player);
			 dbManager.PlayerTeamManager.persist(this.teamName,player.getUserName());
		 }
	}

	@Override
	public void deleteMember(Player player) {
		// TODO Auto-generated method stub
		ArrayList<Team> list = dbManager.PlayerTeamManager.findByPlayer(player.getPlayerName());
		if(list!=null){
			this.member.remove(player);
			dbManager.PlayerTeamManager.delete(this.teamName,player.getPlayerName());
		}
	}

	@Override
	public boolean hasValidName(String teamName) throws SQLException {
		// TODO Auto-generated method stub
		
		Team team = dbManager.TeamManager.findByName(teamName);
		
		if(team == null){
			return true;
		}else
		return false;
	}
	
	public String getTeamName(){
		return this.teamName;
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
