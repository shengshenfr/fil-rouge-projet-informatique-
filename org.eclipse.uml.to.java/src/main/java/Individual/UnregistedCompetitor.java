package Individual;
import Individual.Player;

public class UnregistedCompetitor extends Player {

		private String unregistedSubscriberName;
		
		public UnregistedCompetitor(String name){
			super(name);
		}
		
		public String getUnregistedSubscriberName(){
			return this.unregistedSubscriberName;
		}
	
}
