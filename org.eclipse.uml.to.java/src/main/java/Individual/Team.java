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
	
	public Team(String name){
		if(name==null){
			try {
				throw new BadParametersException("can't have null name!!!");
			} catch (BadParametersException e) {
				e.printStackTrace();
			}
		}
		this.teamName = name;
	}
// verifier if the team has exist. If not, add the new team in data base.
	
	public void addMember(Player player,Team team) throws SQLException{
		// TODO Auto-generated method stub
		 ArrayList<Team> list = dbManager.PlayerTeamManager.findByPlayer(player.getUserName());
		 if(list==null){
			 this.member.add(player);
			 dbManager.PlayerTeamManager.persist(player,team);
		 }
	}

	
	public void deleteMember(Player player,Team team) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Team> list = dbManager.PlayerTeamManager.findByPlayer(player.getUserName());
		if(list!=null){
			this.member.remove(player);
			dbManager.PlayerTeamManager.delete(player,team);
		}
	}

	@Override
	public boolean hasValidName(String teamName) {
		
		Team team;
		try {
			team = dbManager.TeamManager.findByName(teamName);
			if(team == null){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getTeamName(){
		return this.teamName;
	}


	
}
