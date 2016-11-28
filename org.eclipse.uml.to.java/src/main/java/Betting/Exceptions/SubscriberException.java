package Betting.Exceptions;

public class SubscriberException extends Exception {
	private static final long serialVersionUID = 9214319826285152964L;

    public SubscriberException() {
        super();
     }
	public SubscriberException(String reason) {
        super(reason);
     } 
}