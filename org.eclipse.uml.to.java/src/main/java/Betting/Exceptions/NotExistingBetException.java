package Betting.Exceptions;

public class NotExistingBetException extends Exception {
	private static final long serialVersionUID = 1L;

    public NotExistingBetException() {
        super();
     }
	public NotExistingBetException(String r) {
        super(r);
     } 
}
