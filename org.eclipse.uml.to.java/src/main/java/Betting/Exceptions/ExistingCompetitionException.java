package Betting.Exceptions;

public class ExistingCompetitionException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExistingCompetitionException() {
        super();
     }
	public ExistingCompetitionException(String r) {
        super(r);
	}
}