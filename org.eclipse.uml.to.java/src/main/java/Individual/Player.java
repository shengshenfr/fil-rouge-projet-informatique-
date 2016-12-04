package Individual;
import Individual.AbstractCompetitor;

public class Player extends AbstractCompetitor {
	
	private String playerName;
	
	public Player(String playerName){
		super(playerName);
	}
	
	public String getPlayerName(){
		return this.playerName;
	}

}
