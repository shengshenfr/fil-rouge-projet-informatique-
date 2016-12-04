/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Enumeration.
 * 
 * @author Robin, Rémy
 */
public enum Rank {
	FIRST,
	SECOND,
	THIRD,
	NOT_PODIUM;

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
