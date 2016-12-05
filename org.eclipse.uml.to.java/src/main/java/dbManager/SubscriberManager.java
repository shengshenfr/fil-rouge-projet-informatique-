/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Individual.Subscriber;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;
import exceptions.MissingCompetitionException;
import exceptions.SubscriberException;
import utils.DatabaseConnection;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of SubscriberManager.
 * 
 * @author Robin, Remy, Yuan, Shen, Bowen, Thomas
 */
public class SubscriberManager {
	// Start of user code (user defined attributes for SubscriberManager)

	// End of user code

	/**
	 * Store a subscriber in the database. This subscriber is not stored
     * yet, so his <code>id</code> value is <code>NULL</code>. Once the
     * subscriber is stored, the method returns the subscriber with the
     * <code>id</code> value setted.
     * 
     * @param subscriber the subscriber to be stored.
     * @return the subscriber with the updated value for the id.
     * @throws SQLException
	 */
	public static Subscriber persist(Subscriber subscriber) throws SQLException
	{
		// One step in this methods which must be managed in an atomic
	    // (unique) transaction:
		// persistance of a subscriber
		
	Connection c = DatabaseConnection.getConnection();
	try
	{
		c.setAutoCommit(false);
		PreparedStatement psPersist = c.prepareStatement("insert into subscribers(username,firstname,lastname,bornDate,balance) values (?,?,?,?,?)");
		
		//ajust valors of attributes
		psPersist.setString(1,  subscriber.getUsername());
		psPersist.setString(2,  subscriber.getFirstname());
		psPersist.setString(3, subscriber.getLastname());
		psPersist.setDate(4, convertJavaDateToSqlDate(subscriber.getBornDate().getTime()));
		psPersist.setLong(5, subscriber.getBalance());
		
		psPersist.executeUpdate();
		psPersist.close();
		
			
	}
	// exception that need to be raised
	catch (SQLException e)
	{
		try
		{
			
			c.rollback();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		c.setAutoCommit(true);
		throw e;
	}
	
	c.setAutoCommit(true);
	// closing of the connection
	c.close();
	
	return subscriber;
	}

	/**
	 * Function which convert a java.util.date to java.sql.date
	 * 
	 * @param date
	 * @return java.sql.date
	 */
	public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}


//-----------------------------------------------------------------------------
/**
 * Find a subscriber by his username.
 * 
 * @param username the username of the subscriber to retrieve.
 * @return the subscriber or null if the username does not exist in the database.
 * @throws SQLException
 */
	
public static Subscriber findByUsername(String username) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException
{
	 // 1 - Get a database connection from the class 'DatabaseConnection'
	Connection c = DatabaseConnection.getConnection();
	
	// 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
	PreparedStatement psSelect = c.prepareStatement("select * from subscribers where username=?");
	
	// 3 Supplying values for the prepared statement parameters (question marks).
	psSelect.setString(1, username);
	
	// 4 - Executing Prepared Statement object among the database.
    //     The return value is a Result Set containing the data.
	
	ResultSet resultSet = psSelect.executeQuery();
	
	// 5 - Retrieving values from the Result Set.
	Subscriber subscriber = null;
	while(resultSet.next())
	{
		// conversion of the Date bornDate to calendar using the last function
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
		try
		{
		subscriber = new Subscriber(resultSet.getString("username"),resultSet.getString("firstname"),
				resultSet.getString("lastname"), calendar, resultSet.getLong("balance"));
		}
		catch (BadParametersException e) {
			
			e.printStackTrace();
		}
		
	}
	
	 // 6 - Closing the Result Set
    resultSet.close();
    
    // 7 - Closing the Prepared Statement.
    psSelect.close();
    
    // 8 - Closing the database connection.
    c.close();
    
    return subscriber;
		
}

//-----------------------------------------------------------------------------
/**
 * Find a list of subscribers by a firstname.
 * 
 * @param username the firstname of the subscriber to retrieve.
 * @return the list of subscriber (which can be empty)
 * @throws SQLException
 * @throws BadParametersException
 */
public static List<Subscriber> findByFirstname(String firstname) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException
{
	 // 1 - Get a database connection from the class 'DatabaseConnection'
	Connection c = DatabaseConnection.getConnection();
	
	// 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
	PreparedStatement psSelect = c.prepareStatement("select * from subscribers where firstname=?");
	
	// 3 Supplying values for the prepared statement parameters (question marks).
	psSelect.setString(1, firstname);
	
	// 4 - Executing Prepared Statement object among the database.
    //     The return value is a Result Set containing the data.
	
	ResultSet resultSet = psSelect.executeQuery();
	// initialisation of the result
	List<Subscriber> subscribers = new ArrayList<Subscriber>();
	
	// 5 - Retrieving values from the Result Set.
	while(resultSet.next())
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
		try
		{
		subscribers.add(new Subscriber(resultSet.getString("username"),resultSet.getString("firstname"),
				resultSet.getString("lastname"), calendar, resultSet.getLong("balance")));
		}
		catch (BadParametersException e) {
			
			e.printStackTrace();
		}
		
	}
	
	 // 6 - Closing the Result Set
    resultSet.close();
    
    // 7 - Closing the Prepared Statement.
    psSelect.close();
    
    // 8 - Closing the database connection.
    c.close();
    
    return subscribers;
		
}

//-----------------------------------------------------------------------------
/**
 * Find a list of subscribers by a lastname.
 * 
 * @param username the lastname of the subscriber to retrieve.
 * @return the list of subscriber (which can be empty)
 * @throws SQLException
 * @throws BadParametersException
 */
public static List<Subscriber> findByLastname(String lastname) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException
{
	 // 1 - Get a database connection from the class 'DatabaseConnection'
	Connection c = DatabaseConnection.getConnection();
	
	// 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
	PreparedStatement psSelect = c.prepareStatement("select * from subscribers where lastname=?");
	
	// 3 Supplying values for the prepared statement parameters (question marks).
	psSelect.setString(1, lastname);
	
	// 4 - Executing Prepared Statement object among the database.
    //     The return value is a Result Set containing the data.
	
	ResultSet resultSet = psSelect.executeQuery();
	//initialisation of the result
	List<Subscriber> subscribers = new ArrayList<Subscriber>();
	
	// 5 - Retrieving values from the Result Set.
	while(resultSet.next())
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
		try
		{
		subscribers.add(new Subscriber(resultSet.getString("username"),resultSet.getString("firstname"),
				resultSet.getString("lastname"), calendar, resultSet.getLong("balance")));
		}
		catch (BadParametersException e) {
			
			e.printStackTrace();
		}
		
	}
	
	 // 6 - Closing the Result Set
    resultSet.close();
    
    // 7 - Closing the Prepared Statement.
    psSelect.close();
    
    // 8 - Closing the database connection.
    c.close();
    
    return subscribers;
		
}

//-----------------------------------------------------------------------------

/**
 * Find a list of subscribers by a bornDate
 * 
 * @param the bornDate of the subscriber to retrieve.
 * @return the list of subscriber (which can be empty)
 * @throws SQLException
 * @throws BadParametersException
 */
public static List<Subscriber> findByBornDate(Calendar bornDate) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException
{
	 // 1 - Get a database connection from the class 'DatabaseConnection'
	Connection c = DatabaseConnection.getConnection();
	
	// 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
	PreparedStatement psSelect = c.prepareStatement("select * from subscribers where bornDate=?");
	
	// 3 Supplying values for the prepared statement parameters (question marks).
	psSelect.setDate(1, convertJavaDateToSqlDate(bornDate.getTime()));
	
	// 4 - Executing Prepared Statement object among the database.
    //     The return value is a Result Set containing the data.
	
	ResultSet resultSet = psSelect.executeQuery();
	List<Subscriber> subscribers = new ArrayList<Subscriber>();
	
	// 5 - Retrieving values from the Result Set.
	while(resultSet.next())
	{
		// conversion of java.sql.Date bornDate to Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
		try
		{
		subscribers.add(new Subscriber(resultSet.getString("username"),resultSet.getString("firstname"),
				resultSet.getString("lastname"), calendar, resultSet.getLong("balance")));
		}
		catch (BadParametersException e) {
			
			e.printStackTrace();
		}
		
	}
	
	 // 6 - Closing the Result Set
    resultSet.close();
    
    // 7 - Closing the Prepared Statement.
    psSelect.close();
    
    // 8 - Closing the database connection.
    c.close();
    
    return subscribers;
		
}

//-----------------------------------------------------------------------------

/**
 * Find all the subscribers in the database.
 * 
 * @return subscribers
 * 			a list of all the subscribers
 * @throws SQLException
 * @throws CompetitionException 
 * @throws BadParametersException 
 * @throws SubscriberException 
 * @throws ExistingCompetitorException 
 */

public static List<Subscriber> findAll() throws SQLException,BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException
{
	// initialize the connection
	Connection c = DatabaseConnection.getConnection();
	// initialisation of the statement
    PreparedStatement psSelect = c.prepareStatement("select * from subscribers order by username");
    ResultSet resultSet = psSelect.executeQuery();
    List<Subscriber> subscribers = new ArrayList<Subscriber>();
    while(resultSet.next())
    {
    	
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
		try
		{
    	subscribers.add(new Subscriber(resultSet.getString("username"),
    								   resultSet.getString("firstname"),
    								   resultSet.getString("lastname"),
    								   calendar, resultSet.getLong("balance")));
    	}
    	catch (BadParametersException e) {
			
			e.printStackTrace();
    	}
    	
    }
    // closing the resultSet
    resultSet.close();
    
    // closing the psSelect
    psSelect.close();
    
    // closing the database connection
    c.close();
    
    // the result, all the subscribers in the database
    return subscribers;
}

//-----------------------------------------------------------------------------
/**
 * Update on the database the values from a subscriber.
 * 
 * @param subscriber the subscriber to be updated.
 * @throws SQLException
 */

public static void update(Subscriber subscriber) throws SQLException
{
  // 1 - Get a database connection from the class 'DatabaseConnection' 
  Connection c = DatabaseConnection.getConnection();

  // 2 - Creating a Prepared Statement with the SQL instruction.
  //     The parameters are represented by question marks. 
  
  PreparedStatement psUpdate = c.prepareStatement("update subscribers set firstname=?, lastname=?, bornDate=?, balance=?, where username=?");
  
  // 3 - Supplying values for the prepared statement parameters (question marks).
  
  psUpdate.setString(1,  subscriber.getFirstname());
  psUpdate.setString(2, subscriber.getLastname());
  psUpdate.setDate(3,  convertJavaDateToSqlDate(subscriber.getBornDate().getTime()));
  psUpdate.setLong(4, subscriber.getBalance());
  psUpdate.setString(5, subscriber.getUsername());
  

  psUpdate.executeUpdate();

  // 6 - Closing the Prepared Statement.
  
  psUpdate.close();

  // 7 - Closing the database connection.
  
  c.close();
}

//-----------------------------------------------------------------------------
/**
 * Delete from the database a specific subscriber.<br>
 * <i>Be careful: the delete functionality does not operate a delete
 * cascade on bets belonging to the subscriber.</i>
 * 
 * @param subscriber the subscriber to be deleted.
 * @throws SQLException
 * @throws MissingCompetitionException 
 * @throws ExistingCompetitorException 
 * @throws SubscriberException 
 * @throws CompetitionException 
 * @throws BadParametersException 
 */

public static void delete(Subscriber subscriber) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException, MissingCompetitionException
{
  Connection c = DatabaseConnection.getConnection();
  
  PreparedStatement deleteStmt = c.prepareStatement("delete from subscribers where username=?");
  deleteStmt.setString(1, subscriber.getUsername());
  
  //updating the statement
  deleteStmt.executeUpdate();
  
  //closing the statement
  deleteStmt.close();
  
  // deleting from the database all the bets of the subscriber
  // including podiumbet, winnerbet and drawbet.
  
  //winnerbet
  WinnerBetManager.deleteListWinnerBet(WinnerBetManager.findByOwner(subscriber.getUsername()));
  
  //podiumbet
  PodiumBetManager.deleteListPodiumBet(PodiumBetManager.findByOwner(subscriber.getUsername()));
  
  //drawbet
  DrawBetManager.deleteListDrawBet(DrawBetManager.findByOwner(subscriber.getUsername()));
  //closing the database connection
  c.close();
}


//-------------------------------------------------------------------------------

}

/*****************************************************************************************/
/*****************************************************************************************/
/****************************************END OF THE CODE**********************************/
/*****************************************************************************************/
/*****************************************************************************************/
