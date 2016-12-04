package exceptions;

public class MissingCompetitorException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingCompetitorException(String s) {
		super(s);
	}
}