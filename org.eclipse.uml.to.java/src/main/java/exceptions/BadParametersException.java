package exceptions;

public class BadParametersException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadParametersException(String s) {
		super(s);
	}
}