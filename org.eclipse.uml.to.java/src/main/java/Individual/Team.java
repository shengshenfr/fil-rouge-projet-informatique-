package Individual;
import java.util.ArrayList;
import Individual.AbstractCompetitor;
import dbManager.TeamManager;
import Individual.Player;

public class Team extends AbstractCompetitor{

	private String teamName;
	private ArrayList<Player> member;
	
	public Team(String name){
		if(!hasValidName(name)){
			this.teamName = name;
			dbManager.TeamManager.persist(name);
		}
	}
// verifier if the team has exist. If not, add the new team in data base.
	
	@Override
		public void addMember(Player player){
		// TODO Auto-generated method stub
		 list<Player_Team> list = manager.Player_TeamsManager.findByPlayer(player.getName());
		 if(list==null){
			 this.member.add(player);
			 manager.Player_TeamsManager.persist(this.teamName,player.getName());
		 }
	}

	@Override
	public void deleteMember(Player player) {
		// TODO Auto-generated method stub
		list<Player_Team> list = manager.Player_TeamsManager.findByPlayer(player.getName());
		if(list!=null){
			this.member.remove(player);
			manager.Player_TeamsManager.delete(this.teamName,player.getName());
		}
	}

	@Override
	public boolean hasValidName(String teamName) {
		// TODO Auto-generated method stub
		
		Team team = manager.TeamsManager.findByTeam(teamName);
		
		if(team == null){
			return true;
		}else
		return false;
	}
	
	public String getTeamName(){
		return this.teamName;
	}
	
}
