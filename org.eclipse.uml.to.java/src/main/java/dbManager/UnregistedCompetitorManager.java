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

import Individual.UnregistedCompetitor;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;
import utils.DatabaseConnection;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of CompetitorManager.
 * 
 * @author Robin
 */
public class CompetitorManager {
	// Start of user code (user defined attributes for CompetitorManager)

	// End of user code

	/**
	 * The constructor.
	 */
	
//------------------------------------------------------------------------------
	
	public static UnregistedCompetitor persist(UnregistedCompetitor competitor) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		try
		{
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into competitors (competitorName) values (?) ");
			psPersist.setString(1,  competitor.getUnregistedSubscriberName());
			
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
		
		return competitor;
	}
			
		
	//-----------------------------------------------------------------------------------------
		
		public static void update(UnregistedCompetitor competitor) throws SQLException
		{
		  // 1 - Get a database connection from the class 'DatabaseConnection' 
		  Connection c = DatabaseConnection.getConnection();

		  // 2 - Creating a Prepared Statement with the SQL instruction.
		  //     The parameters are represented by question marks. 
		  
		  PreparedStatement psUpdate = c.prepareStatement("update competitors set competitorName=?, where competitorName=?");
		  
		  // 3 - Supplying values for the prepared statement parameters (question marks).
		  
		  psUpdate.setString(1,  competitor.getUnregistedSubscriberName());
		 
		  
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
		
	//------------------------------------------------------------------------------------
		
		public static void delete(UnregistedCompetitor competitor) throws SQLException, BadParametersException, CompetitionException, ExistingCompetitorException
		{
		  Connection c = DatabaseConnection.getConnection();
		  
		  PreparedStatement psUpdate = c.prepareStatement("delete * from where competitorName=?");
		  
		  psUpdate.setString(1, competitor.getUnregistedSubscriberName());
		  psUpdate.executeUpdate();
		  psUpdate.close();
		  c.close();
		  
		}
		
	//------------------------------------------------------------------------------------
		
		public static List<UnregistedCompetitor> findAll() throws SQLException
		{
			Connection c = DatabaseConnection.getConnection();
			
		    PreparedStatement psSelect = c.prepareStatement("select * from competitors order by competitorName");
		    ResultSet resultSet = psSelect.executeQuery();
		    List<UnregistedCompetitor> competitors = new ArrayList<UnregistedCompetitor>();
		    while(resultSet.next())
		    {
		    	
				try
				{
		    	competitors.add(new UnregistedCompetitor(resultSet.getString("competitorName")));
		    	}
		    	catch (SQLException e) {
					
					e.printStackTrace();
		    	}
		    	
		    }
		    resultSet.close();
		    
		    psSelect.close();
		    
		    c.close();
		    
		    return competitors;
		}
		
	//-----------------------------------------------------------------------------------------------------
		
		public static UnregistedCompetitor findByName (String name)
				throws SQLException {
			
			Connection c = DatabaseConnection.getConnection();	
			PreparedStatement psSelect = c
					.prepareStatement("select * from competitors where competitorName = ?" );
			
			psSelect.setString(1, name);
			
			ResultSet resultSet = psSelect.executeQuery();
			
			UnregistedCompetitor competitor = null;
			
			while (resultSet.next()) {
				
				competitor = new UnregistedCompetitor(name);
			}		

			resultSet.close();
			psSelect.close();
			
			c.close();
			return competitor;
			
			
		}

		

	
	
	
//-------------------------------------------------------------------------------
	//---------------------------------------------------------------------------


	// Start of user code (user defined methods for CompetitorManager)

	// End of user code

}