package exceptions;

public class MissingEntryException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingEntryException(String s) {
		super(s);
	}
}