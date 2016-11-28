package Betting.Exceptions;

public class NotExistingCompetitionException extends Exception {
	private static final long serialVersionUID = 1L;

    public NotExistingCompetitionException() {
        super();
     }
	public NotExistingCompetitionException(String r) {
        super(r);
     } 
}