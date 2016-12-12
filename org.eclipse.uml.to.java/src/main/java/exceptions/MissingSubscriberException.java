package exceptions;

public class MissingSubscriberException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingSubscriberException(String s) {
		super(s);
	}
}