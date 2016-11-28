package Betting.Exceptions;

public class ExistingManagerException extends Exception {
	private static final long serialVersionUID = 1L;

    public ExistingManagerException() {
        super();
     }
	public ExistingManagerException(String r) {
        super(r);
     } 
}