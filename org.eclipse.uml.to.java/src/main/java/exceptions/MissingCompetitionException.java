package exceptions;

public class MissingCompetitionException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingCompetitionException(String s) {
		super(s);
	}
}