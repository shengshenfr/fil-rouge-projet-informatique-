package Subscribertest;
import java.util.Calendar;
import static org.junit.Assert.*;
import Individual.Subscriber;
import exceptions.BadParametersException;
import utils.MyCalendar;

public class subscribertesttest {
	public static void main(String[] args) throws BadParametersException{
		
		//test Subscriber.Subscriber///////////////
	    int LONG_PASSWORD = 8;
	    String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$";
		Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
		System.out.println(s.getUsername()+":Your password is"+ s.getPassword());
		assertTrue(s.getFirstname().equals("YAO"));
        assertTrue(s.getLastname().equals("YUAN"));
        assertTrue(s.getUsername().equals("SYLVIE"));
        assertTrue(s.getPassword().length() == LONG_PASSWORD);
        assertTrue(s.getPassword().matches(REGEX_PASSWORD));
        ////////////////////////////////////////////
        
        
        
	}
}
