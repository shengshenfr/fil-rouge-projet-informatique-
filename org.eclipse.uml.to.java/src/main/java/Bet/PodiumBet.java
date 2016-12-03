/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.HashSet;

import Bet.Bet;
// Start of user code (user defined imports)
import Individual.Subscriber;

// End of user code

/**
 * Description of PodiumBet.
 * 
 * @author Robin
 */
public class PodiumBet extends Bet {
	/**
	 * Description of the property entrys.
	 */
	public HashSet<Entry> podium = new HashSet<Entry>();

	/**
	 * The constructor.
	 */
	public PodiumBet(long amount, Subscriber betOwner, Entry first, Entry second, Entry third) {
		super(amount, betOwner);
		this.setPodium(first, second, third);
	}

	/**
	 * Returns entrys.
	 * @return entrys 
	 */
	public HashSet<Entry> getPodium() {
		return this.podium;
	}
	
	public void setPodium(Entry first, Entry second, Entry third) {
		podium.clear();
		podium.add(first);
		podium.add(second);
		podium.add(third);
	}

}
