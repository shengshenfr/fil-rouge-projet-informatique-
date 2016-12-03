/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bet.Competition;
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
			PreparedStatement psPersist = c.prepareStatement("insert into Competitions(competitionName, startingDate,closingDate,settled,isDraw) values(?,?,?,?,?)");
			psPersist.setString(1, competition.getName());
			psPersist.setDate(2, competition.getStartingDate());
			psPersist.setDate(3, competition.getClosingDate());
			psPersist.setBoolean(4, competition.getSettled());
			psPersist.setBoolean(5, competition.getIsDraw());
			
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
	
	//----------------------------------------------------------------------
	
	
	/**
	 * Find a competition by his competitionName.
	 * 
	 * @param competitionName the competitionName of the competition to retrieve.
	 * @return the competition or null if the competitionName does not exist in the database.
	 * @throws SQLException
	 */
	public static Competition findBycompetitionName(String competitionName) throws SQLException
	{
		 // 1 - Get a database connection from the class 'DatabaseConnection'
		Connection c = DatabaseConnection.getConnection();
		
		// 2 - Creating a Prepared Statement with the SQL instruction.
	    //     The parameters are represented by question marks. 
		PreparedStatement psSelect = c.prepareStatement("select * from competitions where competitionName=?");
		
		// 3 Supplying values for the prepared statement parameters (question marks).
		psSelect.setString(1, competitionName);
		
		// 4 - Executing Prepared Statement object among the database.
	    //     The return value is a Result Set containing the data.
		
		ResultSet resultSet = psSelect.executeQuery();
		
		// 5 - Retrieving values from the Result Set.
		Competition competition = null;
		while(resultSet.next())
		{
			competition = new Competition(resultSet.getString("competitionName"),resultSet.getDate("startingDate"),
					resultSet.getDate("closingDate"), resultSet.getBoolean("settled"), resultSet.getBoolean("isDraw"));
			
		}
		
		 // 6 - Closing the Result Set
	    resultSet.close();
	    
	    // 7 - Closing the Prepared Statement.
	    psSelect.close();
	    
	    // 8 - Closing the database connection.
	    c.close();
	    
	    return competition;
			
	}

	public static void delete(Competition competition) throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();
		
		PreparedStatement psUpdate = c.prepareStatement("delete from competitions where competitionName=?");
		psUpdate.setString(1, competition.getName());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
//------------------------------------------------------------------------

	public static List<Competition> findAll() throws SQLException {
		Connection c = DatabaseConnection.getConnection();
	    PreparedStatement psSelect = c.prepareStatement("select * from competitions order by competitionName");
	    ResultSet resultSet = psSelect.executeQuery();
	    List<Competition> competitions = new ArrayList<Competition>();
	    while(resultSet.next())
	    {
	    	competitions.add(new Competition(resultSet.getString("competitionName"),
	    								   resultSet.getDate("startingDate"),
	    								   resultSet.getDate("closingDate"),
	    								   resultSet.getBoolean("settled"),
	    								   resultSet.getBoolean("isDraw")));
	    	
	    }
	    resultSet.close();
	    
	    psSelect.close();
	    
	    c.close();
	    
	    return competitions;		
		
	}
	
	//-----------------------------------------------------------------------
	
	public static void update(Competition competition) throws SQLException
	{
	  // 1 - Get a database connection from the class 'DatabaseConnection' 
	  Connection c = DatabaseConnection.getConnection();

	  // 2 - Creating a Prepared Statement with the SQL instruction.
	  //     The parameters are represented by question marks. 
	  
	  PreparedStatement psUpdate = c.prepareStatement("update competitions set startingDate=?, set closingDate=?,set settled=?, set isDraw=?, where competitionName=?");
	  
	  // 3 - Supplying values for the prepared statement parameters (question marks).
	  
	  psUpdate.setDate(1,  competition.getStartingDate());
	  psUpdate.setDate(2, competition.getClosingDate());
	  psUpdate.setBoolean(3,  competition.getSettled());
	  psUpdate.setBoolean(4, competition.getIsDraw());
	  psUpdate.setString(5, competition.getName());
	  
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


	//fonction a mettre dans entryManager
		public static void addCompetitor(Competition c, Competitor competitor) {
			// TODO Auto-generated method stub
			
		}



	//fonction a mettre dans entryManager
		public static void deleteCompetitor(Competition c, Competitor competitor) {
			// TODO Auto-generated method stub
			
		}
		
		
}
