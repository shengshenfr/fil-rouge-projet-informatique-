package exceptions;

public class ExistingAbstractCompetitorException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingAbstractCompetitorException(String s) {
		super(s);
	}
}