/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Bet.Bet;
// Start of user code (user defined imports)
import Individual.Subscriber;
import dbManager.PodiumBetManager;
import exceptions.CompetitionException;

// End of user code

/**
 * Description of PodiumBet.
 * 
 * @author Remy
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
		try {
			this.setPodium(first, second, third);
		} catch (CompetitionException e1) {
		}
		
		try {
			PodiumBetManager.persist(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}
	
	protected void save() {
		try {
			PodiumBetManager.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
	}

	/**
	 * Returns entrys.
	 * @return entrys 
	 */
	public List<Entry> getPodium() {
		return this.podium;
	}
	
	public void setPodium(Entry first, Entry second, Entry third) throws CompetitionException {
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
		
		save();
	}
	
	@Override
	public void cancel() throws CompetitionException {
		checkCompetitionNotOver();
		
		for(Entry entry : podium) {
			entry.removeBet(this);
		}

		try {
			PodiumBetManager.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: raise Exception
		}
		
		super.cancel();
	}
	
	@Override
	public boolean isOver() {
		return this.podium.get(0).getCompetition().isOver();
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
