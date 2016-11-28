package Betting.Exceptions;

public class NotExistingSubscriberException extends Exception {
	private static final long serialVersionUID = 1L;

    public NotExistingSubscriberException() {
        super();
     }
	public NotExistingSubscriberException(String reason) {
        super(reason);
     } 
}