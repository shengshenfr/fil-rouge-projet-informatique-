/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

/**
 * Enumeration of the various rank for the entries
 * 
 * @author Remy
 */
public enum Rank {
	FIRST,
	SECOND,
	THIRD,
	NOT_PODIUM;

	/**
	 * Returns the rank associated with a given index
	 * @param i the given index
	 * @return
	 */
	public static Rank getRankIndex(int i) {
		switch(i) {
		case 0:
			return FIRST;
		case 1:
			return SECOND;
		case 2:
			return THIRD;
		default:
			return NOT_PODIUM;
		}
	}
	
	/**
	 * Returns the index associated with the given rank
	 * @param r the given rank
	 * @return
	 */
	public static int getIndex(Rank r) {
		switch(r) {
		case FIRST:
			return 0;
		case SECOND:
			return 1;
		case THIRD:
			return 2;
		default:
			return -1;
		}
	}
}
