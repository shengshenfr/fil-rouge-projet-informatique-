/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;
import java.sql.*;
import java.util.*;
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
		PreparedStatement psPersist = c.prepareStatement("insert into subscribers()
	}
	}

	// Start of user code (user defined methods for SubscriberManager)

	// End of user code

}
