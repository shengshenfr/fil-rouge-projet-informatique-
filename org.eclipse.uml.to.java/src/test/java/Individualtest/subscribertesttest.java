package Individualtest;
import java.sql.SQLException;
import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.*;
import Individual.Subscriber;
import dbManager.SubscriberManager;
import exceptions.AuthentificationException;
import exceptions.BadParametersException;
import exceptions.CantBetException;
import exceptions.MissingTokenException;
import utils.MyCalendar;

public class subscribertesttest {
	public static void main(String[] args) throws BadParametersException, AuthentificationException, MissingTokenException, CantBetException, SQLException{
		testbdd();
		
	}
		public static void testConstructeur() throws BadParametersException{
		//test Subscriber.Subscriber///////////////
	    int LONG_PASSWORD = 8;
	    String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$";
		Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
		System.out.println(s.getUsername()+":Your password is "+ s.getPassword());
		assertTrue(s.getFirstname().equals("YAO"));
        assertTrue(s.getLastname().equals("YUAN"));
        assertTrue(s.getUsername().equals("SYLVIE"));
        assertTrue(s.getPassword().length() == LONG_PASSWORD);
        assertTrue(s.getPassword().matches(REGEX_PASSWORD));
        ////////////////////////////////////////////
		}
		// Check authentification//////////////////////
		//wrong password//
		@Test(expected = BadParametersException.class)
        public static void testauthentification() throws BadParametersException, AuthentificationException{
        Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        System.out.println(s.getUsername()+":Your password is "+ s.getPassword());
        String trypws="abc345gy";
        System.out.println("Try (abc345gy) like the password:");
        s.authenticate(trypws);
        }
		////////////////////////////////////////////
        // Check username//////////////////////
        ///null//
        @Test(expected = BadParametersException.class)
        public static void testusername() throws BadParametersException{
        Subscriber s= new Subscriber(null, "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        }
        ///not in good form//
        @Test(expected = BadParametersException.class)
        public static void testusernameform() throws BadParametersException{
        Subscriber s1= new Subscriber("*&1234", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        Subscriber s2= new Subscriber("*&123", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        }
        /////////////////////////////////////////
        
        // Check firstname//////////////////////
        ///null//
        @Test(expected = BadParametersException.class)
        public static void testfirstname() throws BadParametersException{
        Subscriber s= new Subscriber("SYLVIE", null, "YUAN",new MyCalendar(1994,5,2) , 0);
        }
        ///not in good form//
        @Test(expected = BadParametersException.class)
        public static void testfirstnameform() throws BadParametersException{
        Subscriber s= new Subscriber("SYLVIE", "YAO&*", "YUAN",new MyCalendar(1994,5,2) , 0);
        }
        /////////////////////////////////////////
        
        // Check lastname//////////////////////
        ///null//
        @Test(expected = BadParametersException.class)
        public static void testlastname() throws BadParametersException{
        Subscriber s= new Subscriber("SYLVIE", "YAO", null,new MyCalendar(1994,5,2) , 0);
        }
        ///not in good form//
        @Test(expected = BadParametersException.class)
        public static void testlastnameform() throws BadParametersException{
        Subscriber s= new Subscriber("SYLVIE", "YAO", "$%YUAN",new MyCalendar(1994,5,2) , 0);
        }
        /////////////////////////////////////////
        
        // Check new password//////////////////////
        ///null//
        @Test(expected = BadParametersException.class)
        public static void testpasswordNull() throws BadParametersException, AuthentificationException{
        Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        s.changeSubsPwd("SYLVIE", s.getPassword(), null);
        }
        //length is not 8
        @Test(expected = BadParametersException.class)
        public static void testpasswordLength() throws BadParametersException, AuthentificationException{
        Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        s.changeSubsPwd("SYLVIE", s.getPassword(), "12");
        }
        //not in good form
        @Test(expected = BadParametersException.class)
        public static void testpasswordForm() throws BadParametersException, AuthentificationException{
        Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        s.changeSubsPwd("SYLVIE", s.getPassword(), "%^&*%^&*");
        }
        /////////////////////////////////////////
        
        // Check debite and credit//////////////////////
        ///debite and credit//
        @Test
        public static void testAddTokens() throws BadParametersException, MissingTokenException{
        	Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        	s.creditSubscriber(10);
        	assertTrue((s.getBalance()==10));
        	s.debitSubscriber(5);
        	assertTrue((s.getBalance()==5));    
        }
        ///negative numberToken//
        @Test
        public static void testnegativeTokens() throws BadParametersException, MissingTokenException{
        	Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 0);
        	s.creditSubscriber(-10);   
        }
        ///not enough money for bet//
        @Test
        public static void testcantbet() throws BadParametersException, CantBetException{
        	Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 10);
        	s.checkBlance(20);   
        }
        /////////////////////////////////////////
      ///not enough money for bet//
        @Test
        public static void testbdd() throws BadParametersException, CantBetException, SQLException{
        	Subscriber s= new Subscriber("SYLVIE", "YAO", "YUAN",new MyCalendar(1994,5,2) , 10);
        	try{
        	SubscriberManager.persist(s);  
        	}catch(SQLException e){
        		System.out.println("SQL not match!");
        	};
        }
        /////////////////////////////////////////
}
