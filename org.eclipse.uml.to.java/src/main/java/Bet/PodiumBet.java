/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import Bet.Bet;
// Start of user code (user defined imports)
import Individual.Subscriber;

// End of user code

/**
 * Description of PodiumBet.
 * 
 * @author Robin, Rémy
 */
public class PodiumBet extends Bet {
	/**
	 * Description of the property entrys.
	 */
	public List<Entry> podium = new LinkedList<Entry>();

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
	public List<Entry> getPodium() {
		return this.podium;
	}
	
	public void setPodium(Entry first, Entry second, Entry third) {
		for(Entry entry : podium) {
			entry.removeBet(this);
		}

		podium.clear();
		podium.add(first);
		podium.add(second);
		podium.add(third);
		
		for(Entry entry : podium) {
			entry.addBet(this);
		}
	}
	
	@Override
	public boolean isWon() {
		if (!this.podium.get(0).getCompetition().isSettled())
			return false;
		
		if (this.podium.get(0).getCompetition().isDraw())
			return false;
	
		for(int i=0; i<podium.size(); i++) {
			if (this.podium.get(i).getRank() != Rank.getRankIndex(i)) {
				return false;
			}
		}
		
		return true;
	}

}
