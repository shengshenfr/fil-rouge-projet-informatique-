package exceptions;

public class ExistingCompetitionException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingCompetitionException(String s) {
		super(s);
	}
}