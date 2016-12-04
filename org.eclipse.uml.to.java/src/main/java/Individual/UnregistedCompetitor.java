package Individual;
import Individual.Player;

public class UnregistedCompetitor extends Player {

		private String unregistedSubscriberName;
		
		public UnregistedCompetitor(String name){
			this.unregistedSubscriberName=name;
			System.out.println("creation d'un UnregistedCompetitor: his name is:"+name);
		}
		
		public String getUnregistedSubscriberName(){
			return this.unregistedSubscriberName;
		}
	
}
