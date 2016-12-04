/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Individual.Subscriber;
import utils.DatabaseConnection;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of SubscriberManager.
 * 
 * @author Robin
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
		// Two steps in this methods which must be managed in an atomic
	    // (unique) transaction:
	    //   1 - insert the new subscriber;
	    //   2 - once the insertion is OK, in order to set up the value
	    //       of the id, a request is done to get this value by
	    //       requesting the sequence (subscribers_id_seq) in the
	    //       database.
	Connection c = DatabaseConnection.getConnection();
	try
	{
		c.setAutoCommit(false);
		PreparedStatement psPersist = c.prepareStatement("insert into subscribers(username,firstname,lastname,bornDate,balance) values (?,?,?,?,?)");
		
		psPersist.setString(1,  subscriber.getUsername());
		psPersist.setString(2,  subscriber.getFirstname());
		psPersist.setString(3, subscriber.getLastname());
		psPersist.setDate(4, convertJavaDateToSqlDate(subscriber.getBornDate().getTime()));
		psPersist.setLong(5, subscriber.getBalance());
		
		psPersist.executeUpdate();
		psPersist.close();
		
			
	}
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
	c.close();
	
	return subscriber;
	}

	
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
public static Subscriber findByUsername(String username) throws SQLException
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
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
		subscriber = Subscriber(resultSet.getString("username"),resultSet.getString("firstname"),
				resultSet.getString("lastname"), calendar, resultSet.getLong("balance"));
		
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
 * Find all the subscribers in the database with their username (not their id like in the example)
 * 
 * @return
 * @throws SQLException
 */
public static List<Subscriber> findAll() throws SQLException
{
	Connection c = DatabaseConnection.getConnection();
    PreparedStatement psSelect = c.prepareStatement("select * from subscribers order by username");
    ResultSet resultSet = psSelect.executeQuery();
    List<Subscriber> subscribers = new ArrayList<Subscriber>();
    while(resultSet.next())
    {
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultSet.getDate("bornDate"));
    	subscribers.add(Subscriber(resultSet.getString("username"),
    								   resultSet.getString("firstname"),
    								   resultSet.getString("lastname"),
    								   calendar, resultSet.getLong("balance")));
    	
    }
    resultSet.close();
    
    psSelect.close();
    
    c.close();
    
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
  
//Executing the prepared statement object among the database.
  // If needed, a return value (int) can be obtained. It contains
  // how many rows of a table were updated.
  // int nbRows = psUpdate.executeUpdate();
  
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
 */

public static void delete(Subscriber subscriber) throws SQLException
{
  Connection c = DatabaseConnection.getConnection();
  PreparedStatement psUpdate = c.prepareStatement("delete from subscribers where username=?");
  psUpdate.setString(1, subscriber.getUsername());
  psUpdate.executeUpdate();
  psUpdate.close();
  c.close();
}
//-----------------------------------------------------------------------------
}
