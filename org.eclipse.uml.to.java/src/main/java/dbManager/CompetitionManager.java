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
import java.util.List;

import Bet.Competition;
import exceptions.BadParametersException;
import exceptions.MissingCompetitionException;
import Individual.Subscriber;
import Interface.Competitor;
import utils.DatabaseConnection;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of CompetitionManager.
 * 
 * @author Robin
 */
@SuppressWarnings("unused")
public class CompetitionManager {
	// Start of user code (user defined attributes for CompetitionManager)

	// End of user code
	
	/**
	 * Store a competition in the database. This competition is not stored
     * yet, so his <code>id</code> value is <code>NULL</code>. Once the
     * competition is stored, the method returns the competition with the
     * <code>id</code> value setted.
     * 
     * @param competition the competition to be stored.
     * @return the competition with the updated value for the id.
     * @throws SQLException
	 */
	public static Competition persist(Competition competition) throws SQLException
	{
		// One steps in this methods which must be managed in an atomic
	    // (unique) transaction:
	    // insert the new competition;
		
		Connection c = DatabaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into Competition (name,closingDate, startingDate,setteled,isDraw,totalTokens) values(?,?,?,?,?,?)");
			psPersist.setString(1, competition.getName());
			psPersist.setDate(2, convertJavaDateToSqlDate(competition.getClosingCalendar().getTime()));
			psPersist.setDate(3, convertJavaDateToSqlDate(competition.getStartingCalendar().getTime()));
			psPersist.setInt(4, competition.isSettled());
			psPersist.setInt(5, competition.isDraw());
			psPersist.setLong(6, competition.getTotalToken());

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
		
		return competition;
	   
	}
	
	
	public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
	
	//----------------------------------------------------------------------
	
	
	/**
	 * Find a competition by his competitionName.
	 * 
	 * @param competitionName the competitionName of the competition to retrieve.
	 * @return the competition or null if the competitionName does not exist in the database.
	 * @throws SQLException
	 */
	public static Competition findBycompetitionName(String name) throws MissingCompetitionException
	{
		Competition competition = null;
		try {
			// 1 - Get a database connection from the class 'DatabaseConnection'
			Connection c = DatabaseConnection.getConnection();
		
			// 2 - Creating a Prepared Statement with the SQL instruction.
		    //     The parameters are represented by question marks. 
			PreparedStatement psSelect = c.prepareStatement("select * from competition where name=?");
			
			// 3 Supplying values for the prepared statement parameters (question marks).
			psSelect.setString(1, name);
			
			// 4 - Executing Prepared Statement object among the database.
		    //     The return value is a Result Set containing the data.
			
			ResultSet resultSet = psSelect.executeQuery();
			
			// 5 - Retrieving values from the Result Set.
			while(resultSet.next())
			{
				competition = Competition.createCompetition(
						resultSet.getString("name"),
						resultSet.getDate("closingDate"),
						resultSet.getDate("startingDate"),
						resultSet.getInt("setteled"),
						resultSet.getInt("isDraw"),
						resultSet.getLong("totalTokens")
				);
				
			}
			
			 // 6 - Closing the Result Set
		    resultSet.close();
		    
		    // 7 - Closing the Prepared Statement.
		    psSelect.close();
		    
		    // 8 - Closing the database connection.
		    c.close();
		} catch (SQLException e) {
		} catch (BadParametersException e) {
			e.printStackTrace();
		}
	    
	    return competition;
			
	}

	public static void delete(Competition competition) throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();
		
		PreparedStatement psUpdate = c.prepareStatement("delete from competition where name=?");
		psUpdate.setString(1, competition.getName());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
//------------------------------------------------------------------------

	public static List<Competition> findAll() {
		List<Competition> competitions = new ArrayList<Competition>();
		try {
			Connection c = DatabaseConnection.getConnection();
		
		    PreparedStatement psSelect = c.prepareStatement("select * from competition order by name");
		    ResultSet resultSet = psSelect.executeQuery();
		    while(resultSet.next())
		    {
		    	competitions.add(Competition.createCompetition(
						resultSet.getString("name"),
						resultSet.getDate("startingDate"),
						resultSet.getDate("closingDate"),
						resultSet.getInt("setteled"),
						resultSet.getInt("isDraw"),
						resultSet.getLong("totalTokens")
				));
		    	
		    }
		    resultSet.close();
		    
		    psSelect.close();
		    
		    c.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (BadParametersException e) {
			
			e.printStackTrace();
		}
	    
	    return competitions;		
		
	}
	
	//-----------------------------------------------------------------------
	
	public static void update(Competition competition) throws SQLException
	{
	  // 1 - Get a database connection from the class 'DatabaseConnection' 
	  Connection c = DatabaseConnection.getConnection();

	  // 2 - Creating a Prepared Statement with the SQL instruction.
	  //     The parameters are represented by question marks. 
	  
	  PreparedStatement psUpdate = c.prepareStatement("update competition set closingDate=?,startingDate=?, setteled=?, isDraw=?,totalTokens=?  where name=?");
	  
	  // 3 - Supplying values for the prepared statement parameters (question marks).
	  
	  psUpdate.setDate(1, convertJavaDateToSqlDate(competition.getClosingCalendar().getTime()));
	  psUpdate.setDate(2, convertJavaDateToSqlDate(competition.getStartingCalendar().getTime()));
	  psUpdate.setInt(3,  competition.isSettled());
	  psUpdate.setInt(4, competition.isDraw());
	  psUpdate.setLong(5, competition.getTotalToken());
	  psUpdate.setString(6, competition.getName());
	  
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


		
}
