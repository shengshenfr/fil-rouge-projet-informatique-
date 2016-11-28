package Betting.Exceptions;

public class BadParametersException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BadParametersException() {
        super();
     }
    public BadParametersException(String r) {
    	super(r);
     }
}
