/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Bet;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Enumeration.
 * 
 * @author Robin, R�my
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
}
