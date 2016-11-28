package Betting.Exceptions;

public class ExistingSubscriberException extends Exception {
	private static final long serialVersionUID = 1L;

    public ExistingSubscriberException() {
        super();
     }
	public ExistingSubscriberException(String r) {
        super(r);
     } 
}