package exceptions;

public class CantBetException extends Exception {
	private static final long serialVersionUID = 1L;

	public CantBetException(String s) {
		super(s);
	}
}