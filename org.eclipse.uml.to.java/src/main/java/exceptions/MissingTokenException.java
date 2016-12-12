package exceptions;

public class MissingTokenException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingTokenException(String s) {
		super(s);
	}
}