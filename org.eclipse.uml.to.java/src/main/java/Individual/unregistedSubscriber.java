package Individual;
import Individual.Player;

public class unregistedSubscriber extends Player {

		private String unregistedSubscriberName;
		
		public unregistedSubscriber(String name){
			super(name);
		}
		
		public String getUnregistedSubscriberName(){
			return this.unregistedSubscriberName;
		}
	
}
