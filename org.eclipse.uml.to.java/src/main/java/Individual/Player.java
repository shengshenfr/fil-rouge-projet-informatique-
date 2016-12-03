package Individual;
import Individual.abstract_Competitor;

public class Player extends abstract_Competitor {
	
	private String playerName;
	
	public Player(String playerName){
		this.playerName = playerName;
	}
	
	public String getPlayerName(){
		return this.playerName;
	}

}
